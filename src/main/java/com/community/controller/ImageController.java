package com.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class ImageController {

    @GetMapping("/resources/images/**")
    public void serveImage(HttpServletRequest request, HttpServletResponse response) {
        try {
            String requestPath = request.getRequestURI();
            System.out.println("ImageController - Request URI: " + requestPath);
            
            // 컨텍스트 경로 제거 (있는 경우)
            String contextPath = request.getContextPath();
            if (contextPath != null && !contextPath.isEmpty() && requestPath.startsWith(contextPath)) {
                requestPath = requestPath.substring(contextPath.length());
            }
            
            // /resources/images/파일명 형태로 요청이 옴
            int resourcesIndex = requestPath.indexOf("/resources/images/");
            if (resourcesIndex == -1) {
                System.out.println("ImageController - No /resources/images/ found in path: " + requestPath);
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            
            // 파일명 추출
            String fileName = requestPath.substring(resourcesIndex + "/resources/images/".length());
            if (fileName == null || fileName.isEmpty()) {
                System.out.println("ImageController - Empty filename");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            
            System.out.println("ImageController - Filename: " + fileName);
            
            ServletContext servletContext = request.getServletContext();
            String webappPath = servletContext.getRealPath("/");
            System.out.println("ImageController - Webapp path: " + webappPath);
            
            if (webappPath == null) {
                System.err.println("ImageController - Webapp path is null, cannot serve image");
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return;
            }
            
            // 실제 파일 경로 구성
            // webappPath는 이미 끝에 / 또는 \ 가 있을 수 있음
            String fullPath;
            if (webappPath.endsWith(File.separator) || webappPath.endsWith("/") || webappPath.endsWith("\\")) {
                fullPath = webappPath + "resources/images/" + fileName;
            } else {
                fullPath = webappPath + File.separator + "resources/images/" + fileName;
            }
            
            // 경로 구분자 정규화 (Windows에서 \ 사용)
            fullPath = fullPath.replace('/', File.separatorChar).replace('\\', File.separatorChar);
            System.out.println("ImageController - Full path: " + fullPath);
            
            Path filePath = Paths.get(fullPath);
            
            if (!Files.exists(filePath)) {
                System.err.println("ImageController - File not found: " + fullPath);
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            
            if (!Files.isRegularFile(filePath)) {
                System.err.println("ImageController - Path is not a regular file: " + fullPath);
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            
            System.out.println("ImageController - File found, serving: " + fullPath);
            
            // 파일 확장자로 MIME 타입 결정
            String lowerFileName = fileName.toLowerCase();
            String contentType = "image/jpeg"; // 기본값
            if (lowerFileName.endsWith(".png")) {
                contentType = "image/png";
            } else if (lowerFileName.endsWith(".gif")) {
                contentType = "image/gif";
            } else if (lowerFileName.endsWith(".webp")) {
                contentType = "image/webp";
            } else if (lowerFileName.endsWith(".bmp")) {
                contentType = "image/bmp";
            } else if (lowerFileName.endsWith(".jpg") || lowerFileName.endsWith(".jpeg")) {
                contentType = "image/jpeg";
            }
            
            response.setContentType(contentType);
            // Tomcat 7 호환성을 위해 setContentLength 사용 (int 범위 내에서)
            long fileSize = Files.size(filePath);
            if (fileSize > Integer.MAX_VALUE) {
                // 파일이 너무 크면 Content-Length 헤더를 설정하지 않음
                System.err.println("ImageController - File too large for Content-Length header: " + fileSize);
            } else {
                response.setContentLength((int) fileSize);
            }
            
            // 캐시 설정 (선택사항)
            response.setHeader("Cache-Control", "public, max-age=31536000");
            
            // 파일 읽기 및 전송
            try (FileInputStream fis = new FileInputStream(filePath.toFile());
                 OutputStream os = response.getOutputStream()) {
                
                byte[] buffer = new byte[8192]; // 버퍼 크기 증가
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
                os.flush();
                System.out.println("ImageController - Image served successfully: " + fileName);
            }
        } catch (IOException e) {
            // 로그를 남기고 500 에러 반환
            System.err.println("ImageController - IOException: " + e.getMessage());
            e.printStackTrace();
            if (!response.isCommitted()) {
                try {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                } catch (Exception ex) {
                    // 이미 커밋된 경우 무시
                }
            }
        } catch (Exception e) {
            // 기타 예외 처리
            System.err.println("ImageController - Exception: " + e.getMessage());
            e.printStackTrace();
            if (!response.isCommitted()) {
                try {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                } catch (Exception ex) {
                    // 이미 커밋된 경우 무시
                }
            }
        }
    }
}

