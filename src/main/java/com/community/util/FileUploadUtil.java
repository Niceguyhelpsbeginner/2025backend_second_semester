package com.community.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class FileUploadUtil {
    
    private static final String RESOURCES_IMAGE_DIR = "resources/images/";
    
    public static String saveFile(MultipartFile file, String webappPath) throws IOException {
        if (file == null || file.isEmpty()) {
            return null;
        }
        
        // 파일 확장자 확인
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.isEmpty()) {
            return null;
        }
        
        String extension = "";
        int lastDotIndex = originalFilename.lastIndexOf('.');
        if (lastDotIndex > 0) {
            extension = originalFilename.substring(lastDotIndex);
        }
        
        // 이미지 파일만 허용
        String lowerExtension = extension.toLowerCase();
        if (!lowerExtension.matches("\\.(jpg|jpeg|png|gif|webp|bmp)$")) {
            throw new IllegalArgumentException("이미지 파일만 업로드 가능합니다.");
        }
        
        // 고유한 파일명 생성
        String uniqueFilename = UUID.randomUUID().toString() + extension;
        
        // resources/images 디렉토리 경로 생성
        String resourcesPath = webappPath + RESOURCES_IMAGE_DIR;
        Path resourcesDir = Paths.get(resourcesPath);
        if (!Files.exists(resourcesDir)) {
            Files.createDirectories(resourcesDir);
        }
        
        // 파일 저장
        Path filePath = resourcesDir.resolve(uniqueFilename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        
        // 웹에서 접근 가능한 경로 반환 (/resources/images/파일명)
        return "/" + RESOURCES_IMAGE_DIR + uniqueFilename;
    }
    
    public static void deleteFile(String imageUrl, String webappPath) {
        if (imageUrl == null || imageUrl.isEmpty()) {
            return;
        }
        
        try {
            // 웹 경로에서 실제 파일 경로로 변환
            // /resources/images/파일명 -> resources/images/파일명
            String relativePath = imageUrl.startsWith("/") ? imageUrl.substring(1) : imageUrl;
            Path path = Paths.get(webappPath, relativePath);
            
            if (Files.exists(path)) {
                Files.delete(path);
            }
        } catch (IOException e) {
            // 파일 삭제 실패는 무시
            e.printStackTrace();
        }
    }
}




