package com.community.dao;

import com.community.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class CommentDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Comment> commentRowMapper = new RowMapper<Comment>() {
        @Override
        public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
            Comment comment = new Comment();
            comment.setCommentId(rs.getLong("comment_id"));
            comment.setPostId(rs.getLong("post_id"));
            comment.setUserId(rs.getLong("user_id"));
            comment.setContent(rs.getString("content"));
            if (rs.getTimestamp("created_at") != null) {
                comment.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            }
            if (rs.getTimestamp("updated_at") != null) {
                comment.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
            }
            // 조인된 필드
            try {
                comment.setUsername(rs.getString("username"));
                comment.setNickname(rs.getString("nickname"));
            } catch (SQLException e) {
                // 조인되지 않은 경우 무시
            }
            return comment;
        }
    };

    public void save(Comment comment) {
        String sql = "INSERT INTO comments (post_id, user_id, content) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, comment.getPostId(), comment.getUserId(), comment.getContent());
    }

    public Optional<Comment> findById(Long commentId) {
        String sql = "SELECT c.*, u.username, u.nickname FROM comments c " +
                     "JOIN users u ON c.user_id = u.user_id WHERE c.comment_id = ?";
        try {
            Comment comment = jdbcTemplate.queryForObject(sql, commentRowMapper, commentId);
            return Optional.ofNullable(comment);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public List<Comment> findByPostId(Long postId) {
        String sql = "SELECT c.*, u.username, u.nickname FROM comments c " +
                     "JOIN users u ON c.user_id = u.user_id " +
                     "WHERE c.post_id = ? ORDER BY c.created_at ASC";
        return jdbcTemplate.query(sql, commentRowMapper, postId);
    }

    public List<Comment> findByUserId(Long userId) {
        String sql = "SELECT c.*, u.username, u.nickname FROM comments c " +
                     "JOIN users u ON c.user_id = u.user_id " +
                     "WHERE c.user_id = ? ORDER BY c.created_at DESC";
        return jdbcTemplate.query(sql, commentRowMapper, userId);
    }

    public void update(Comment comment) {
        String sql = "UPDATE comments SET content = ? WHERE comment_id = ?";
        jdbcTemplate.update(sql, comment.getContent(), comment.getCommentId());
    }

    public void delete(Long commentId) {
        String sql = "DELETE FROM comments WHERE comment_id = ?";
        jdbcTemplate.update(sql, commentId);
    }

    public int countByPostId(Long postId) {
        String sql = "SELECT COUNT(*) FROM comments WHERE post_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, postId);
    }
}













