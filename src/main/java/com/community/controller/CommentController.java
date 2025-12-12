package com.community.controller;

import com.community.model.Comment;
import com.community.model.User;
import com.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/write")
    public String write(@RequestParam Long postId,
                       @RequestParam String content,
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

        Comment comment = new Comment(postId, user.getUserId(), content);
        commentService.createComment(comment);
        redirectAttributes.addFlashAttribute("message", "댓글이 작성되었습니다.");
        return "redirect:/post/" + postId;
    }

    @PostMapping("/{commentId}/edit")
    public String edit(@PathVariable Long commentId,
                      @RequestParam Long postId,
                      @RequestParam String content,
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

        commentService.getComment(commentId).ifPresent(comment -> {
            if (comment.getUserId().equals(user.getUserId())) {
                comment.setContent(content);
                commentService.updateComment(comment);
            }
        });

        redirectAttributes.addFlashAttribute("message", "댓글이 수정되었습니다.");
        return "redirect:/post/" + postId;
    }

    @PostMapping("/{commentId}/delete")
    public String delete(@PathVariable Long commentId,
                        @RequestParam Long postId,
                        HttpSession session,
                        RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/user/login";
        }

        commentService.getComment(commentId).ifPresent(comment -> {
            if (comment.getUserId().equals(user.getUserId())) {
                commentService.deleteComment(commentId);
            }
        });

        redirectAttributes.addFlashAttribute("message", "댓글이 삭제되었습니다.");
        return "redirect:/post/" + postId;
    }
}


