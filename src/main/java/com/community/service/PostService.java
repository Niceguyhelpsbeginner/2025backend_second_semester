package com.community.service;

import com.community.dao.PostDao;
import com.community.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PostService {

    @Autowired
    private PostDao postDao;

    public void createPost(Post post) {
    	System.out.println("post 작성완료");
    	System.out.println("게시물:" + post.getTitle());
    	System.out.println("이미지 경로:" + post.getImageUrl());
        postDao.save(post);
    }

    public Optional<Post> getPost(Long postId) {
        Optional<Post> post = postDao.findById(postId);
        if (post.isPresent()) {
            postDao.incrementViewCount(postId);
            post = postDao.findById(postId); // 조회수 업데이트 후 다시 조회
        }
        return post;
    }

    public List<Post> getAllPosts() {
        return postDao.findAll();
    }

    public List<Post> getPostsByUserId(Long userId) {
        return postDao.findByUserId(userId);
    }

    public void updatePost(Post post) {
        postDao.update(post);
    }

    public void deletePost(Long postId) {
        postDao.delete(postId);
    }

    public int getTotalPostCount() {
        return postDao.count();
    }
}














