package com.community.dao;

import com.community.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class PostDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Post> postRowMapper = new RowMapper<Post>() {
        @Override
        public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
            Post post = new Post();
            post.setPostId(rs.getLong("post_id"));
            post.setUserId(rs.getLong("user_id"));
            post.setTitle(rs.getString("title"));
            post.setContent(rs.getString("content"));
            post.setGameCode(rs.getString("game_code"));
            post.setImageUrl(rs.getString("image_url"));
            post.setViewCount(rs.getInt("view_count"));
            if (rs.getTimestamp("created_at") != null) {
                post.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            }
            if (rs.getTimestamp("updated_at") != null) {
                post.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
            }
            // 조인된 필드
            try {
                post.setUsername(rs.getString("username"));
                post.setNickname(rs.getString("nickname"));
            } catch (SQLException e) {
                // 조인되지 않은 경우 무시
            }
            return post;
        }
    };

    public void save(Post post) {
        try {
            String sql = "INSERT INTO posts (user_id, title, content, game_code, image_url) VALUES (?, ?, ?, ?, ?)";
            System.out.println("PostDao.save - userId: " + post.getUserId());
            System.out.println("PostDao.save - title: " + post.getTitle());
            System.out.println("PostDao.save - content: " + post.getContent());
            System.out.println("PostDao.save - gameCode: " + post.getGameCode());
            System.out.println("PostDao.save - imageUrl: " + post.getImageUrl());
            int rowsAffected = jdbcTemplate.update(sql, post.getUserId(), post.getTitle(), post.getContent(), post.getGameCode(), post.getImageUrl());
            System.out.println("PostDao.save - Rows affected: " + rowsAffected);
        } catch (Exception e) {
            System.err.println("PostDao.save - Error: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public Optional<Post> findById(Long postId) {
        String sql = "SELECT p.*, u.username, u.nickname FROM posts p " +
                     "JOIN users u ON p.user_id = u.user_id WHERE p.post_id = ?";
        try {
            Post post = jdbcTemplate.queryForObject(sql, postRowMapper, postId);
            return Optional.ofNullable(post);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public List<Post> findAll() {
        String sql = "SELECT p.*, u.username, u.nickname FROM posts p " +
                     "JOIN users u ON p.user_id = u.user_id " +
                     "ORDER BY p.created_at DESC";
        return jdbcTemplate.query(sql, postRowMapper);
    }

    public List<Post> findByUserId(Long userId) {
        String sql = "SELECT p.*, u.username, u.nickname FROM posts p " +
                     "JOIN users u ON p.user_id = u.user_id " +
                     "WHERE p.user_id = ? ORDER BY p.created_at DESC";
        return jdbcTemplate.query(sql, postRowMapper, userId);
    }

    public void update(Post post) {
        String sql = "UPDATE posts SET title = ?, content = ?, game_code = ?, image_url = ? WHERE post_id = ?";
        jdbcTemplate.update(sql, post.getTitle(), post.getContent(), post.getGameCode(), post.getImageUrl(), post.getPostId());
    }

    public void delete(Long postId) {
        String sql = "DELETE FROM posts WHERE post_id = ?";
        jdbcTemplate.update(sql, postId);
    }

    public void incrementViewCount(Long postId) {
        String sql = "UPDATE posts SET view_count = view_count + 1 WHERE post_id = ?";
        jdbcTemplate.update(sql, postId);
    }

    public int count() {
        String sql = "SELECT COUNT(*) FROM posts";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }
}


