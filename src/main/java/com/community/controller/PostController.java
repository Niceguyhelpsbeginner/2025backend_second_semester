package com.community.controller;

import com.community.model.Post;
import com.community.model.User;
import com.community.service.CommentService;
import com.community.service.PostService;
import com.community.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

@Controller
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ServletContext servletContext;

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("posts", postService.getAllPosts());
        return "post/list";
    }

    @GetMapping("/write")
    public String writeForm(HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/user/login";
        }
        return "post/write";
    }

    @PostMapping("/write")
    public String write(@RequestParam String title,
                       @RequestParam String content,
                       @RequestParam(required = false) String gameCode,
                       @RequestParam(required = false) MultipartFile imageFile,
                       HttpServletRequest request,
                       HttpServletResponse response,
                       HttpSession session,
                       RedirectAttributes redirectAttributes) throws UnsupportedEncodingException {
        System.out.println("=== PostController.write() 시작 ===");
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html; charset=UTF-8");
            
            System.out.println("PostController.write - title: " + title);
            System.out.println("PostController.write - content: " + content);
            System.out.println("PostController.write - gameCode: " + gameCode);
            System.out.println("PostController.write - imageFile: " + (imageFile != null ? imageFile.getOriginalFilename() : "null"));
            
            User user = (User) session.getAttribute("user");
            if (user == null) {
                System.out.println("PostController.write - 사용자 없음, 로그인 페이지로 리다이렉트");
                return "redirect:/user/login";
            }
            
            System.out.println("PostController.write - userId: " + user.getUserId());

            Post post = new Post(user.getUserId(), title, content);
            post.setGameCode(gameCode);
            
            // 이미지 파일 업로드 처리
            if (imageFile != null && !imageFile.isEmpty()) {
                System.out.println("PostController.write - 이미지 파일 업로드 시작");
                try {
                    String basePath = servletContext.getRealPath("/");
                    System.out.println("PostController.write - basePath: " + basePath);
                    String imageUrl = FileUploadUtil.saveFile(imageFile, basePath);
                    System.out.println("PostController.write - imageUrl: " + imageUrl);
                    post.setImageUrl(imageUrl);
                } catch (IOException e) {
                    System.err.println("PostController.write - 이미지 업로드 IOException: " + e.getMessage());
                    e.printStackTrace();
                    redirectAttributes.addFlashAttribute("error", "이미지 업로드 중 오류가 발생했습니다: " + e.getMessage());
                    return "redirect:/post/write";
                } catch (IllegalArgumentException e) {
                    System.err.println("PostController.write - 이미지 업로드 IllegalArgumentException: " + e.getMessage());
                    e.printStackTrace();
                    redirectAttributes.addFlashAttribute("error", e.getMessage());
                    return "redirect:/post/write";
                }
            } else {
                System.out.println("PostController.write - 이미지 파일 없음");
            }
            
            System.out.println("PostController.write - postService.createPost() 호출 전");
            postService.createPost(post);
            System.out.println("PostController.write - postService.createPost() 호출 완료");
            
            redirectAttributes.addFlashAttribute("message", "게시글이 작성되었습니다.");
            System.out.println("PostController.write - 리다이렉트: /post/list");
            return "redirect:/post/list";
        } catch (Exception e) {
            System.err.println("PostController.write - 예외 발생: " + e.getMessage());
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "게시글 작성 중 오류가 발생했습니다: " + e.getMessage());
            return "redirect:/post/write";
        }
    }

    @GetMapping("/{postId}")
    public String view(@PathVariable Long postId, Model model, HttpSession session) {
        Optional<Post> post = postService.getPost(postId);
        if (post.isPresent()) {
            model.addAttribute("post", post.get());
            model.addAttribute("user", session.getAttribute("user"));
            model.addAttribute("comments", commentService.getCommentsByPostId(postId));
            return "post/view";
        } else {
            return "redirect:/post/list";
        }
    }

    @GetMapping("/{postId}/edit")
    public String editForm(@PathVariable Long postId, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/user/login";
        }

        Optional<Post> post = postService.getPost(postId);
        if (post.isPresent() && post.get().getUserId().equals(user.getUserId())) {
            model.addAttribute("post", post.get());
            return "post/edit";
        } else {
            return "redirect:/post/list";
        }
    }

    @PostMapping("/{postId}/edit")
    public String edit(@PathVariable Long postId,
                      @RequestParam String title,
                      @RequestParam String content,
                      @RequestParam(required = false) String gameCode,
                      @RequestParam(required = false) MultipartFile imageFile,
                      @RequestParam(required = false) String deleteImage,
                      HttpServletRequest request,
                      HttpServletResponse response,
                      HttpSession session,
                      RedirectAttributes redirectAttributes) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/user/login";
        }

        Optional<Post> postOpt = postService.getPost(postId);
        if (postOpt.isPresent() && postOpt.get().getUserId().equals(user.getUserId())) {
            Post post = postOpt.get();
            post.setTitle(title);
            post.setContent(content);
            post.setGameCode(gameCode);
            
            // 이미지 삭제 요청 처리
            if ("true".equals(deleteImage)) {
                String basePath = servletContext.getRealPath("/");
                FileUploadUtil.deleteFile(post.getImageUrl(), basePath);
                post.setImageUrl(null);
            }
            
            // 새 이미지 파일 업로드 처리
            if (imageFile != null && !imageFile.isEmpty()) {
                try {
                    String basePath = servletContext.getRealPath("/");
                    // 기존 이미지 삭제
                    if (post.getImageUrl() != null) {
                        FileUploadUtil.deleteFile(post.getImageUrl(), basePath);
                    }
                    // 새 이미지 저장
                    String imageUrl = FileUploadUtil.saveFile(imageFile, basePath);
                    post.setImageUrl(imageUrl);
                } catch (IOException e) {
                    redirectAttributes.addFlashAttribute("error", "이미지 업로드 중 오류가 발생했습니다: " + e.getMessage());
                    return "redirect:/post/" + postId + "/edit";
                } catch (IllegalArgumentException e) {
                    redirectAttributes.addFlashAttribute("error", e.getMessage());
                    return "redirect:/post/" + postId + "/edit";
                }
            }
            
            postService.updatePost(post);
            redirectAttributes.addFlashAttribute("message", "게시글이 수정되었습니다.");
            return "redirect:/post/" + postId;
        } else {
            return "redirect:/post/list";
        }
    }

    @GetMapping("/{postId}/play")
    public String play(@PathVariable Long postId, Model model) {
        Optional<Post> post = postService.getPost(postId);
        if (post.isPresent() && post.get().getGameCode() != null && !post.get().getGameCode().trim().isEmpty()) {
            model.addAttribute("post", post.get());
            return "post/play";
        } else {
            return "redirect:/post/" + postId;
        }
    }

    @PostMapping("/{postId}/delete")
    public String delete(@PathVariable Long postId,
                        HttpSession session,
                        RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/user/login";
        }

        Optional<Post> post = postService.getPost(postId);
        if (post.isPresent() && post.get().getUserId().equals(user.getUserId())) {
            postService.deletePost(postId);
            redirectAttributes.addFlashAttribute("message", "게시글이 삭제되었습니다.");
        }
        return "redirect:/post/list";
    }
}

