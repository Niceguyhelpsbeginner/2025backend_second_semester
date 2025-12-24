package com.community.service;

import com.community.dao.CommentDao;
import com.community.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CommentService {

    @Autowired
    private CommentDao commentDao;

    public void createComment(Comment comment) {
        commentDao.save(comment);
    }

    public Optional<Comment> getComment(Long commentId) {
        return commentDao.findById(commentId);
    }

    public List<Comment> getCommentsByPostId(Long postId) {
        return commentDao.findByPostId(postId);
    }

    public List<Comment> getCommentsByUserId(Long userId) {
        return commentDao.findByUserId(userId);
    }

    public void updateComment(Comment comment) {
        commentDao.update(comment);
    }

    public void deleteComment(Long commentId) {
        commentDao.delete(commentId);
    }

    public int getCommentCountByPostId(Long postId) {
        return commentDao.countByPostId(postId);
    }
}














