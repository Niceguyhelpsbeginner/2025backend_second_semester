<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="../layout/header.jsp" %>

<style>
    .post-view-container {
        max-width: 900px;
        margin: 3rem auto;
    }
    .post-header {
        background: white;
        padding: 2rem;
        border-radius: 8px;
        margin-bottom: 1rem;
        box-shadow: 0 2px 4px rgba(0,0,0,0.1);
    }
    .post-title {
        font-size: 2rem;
        font-weight: bold;
        margin-bottom: 1rem;
        color: #2c3e50;
    }
    .post-meta {
        display: flex;
        gap: 2rem;
        color: #7f8c8d;
        padding-top: 1rem;
        border-top: 1px solid #eee;
    }
    .post-content {
        background: white;
        padding: 2rem;
        border-radius: 8px;
        margin-bottom: 1rem;
        box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        min-height: 200px;
        white-space: pre-wrap;
        line-height: 1.8;
        color: #333;
    }
    .post-actions {
        background: white;
        padding: 1rem 2rem;
        border-radius: 8px;
        margin-bottom: 1rem;
        box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        display: flex;
        justify-content: flex-end;
        gap: 1rem;
    }
    .comments-section {
        background: white;
        padding: 2rem;
        border-radius: 8px;
        box-shadow: 0 2px 4px rgba(0,0,0,0.1);
    }
    .comments-section h3 {
        margin-bottom: 1.5rem;
        color: #2c3e50;
    }
    .comment-form {
        margin-bottom: 2rem;
        padding-bottom: 2rem;
        border-bottom: 1px solid #eee;
    }
    .comment-form textarea {
        width: 100%;
        padding: 0.75rem;
        border: 1px solid #ddd;
        border-radius: 4px;
        min-height: 100px;
        resize: vertical;
    }
    .comment-item {
        padding: 1rem;
        border-bottom: 1px solid #eee;
    }
    .comment-item:last-child {
        border-bottom: none;
    }
    .comment-header {
        display: flex;
        justify-content: space-between;
        margin-bottom: 0.5rem;
    }
    .comment-author {
        font-weight: bold;
        color: #2c3e50;
    }
    .comment-date {
        color: #7f8c8d;
        font-size: 0.9rem;
    }
    .comment-content {
        color: #555;
        margin-bottom: 0.5rem;
    }
    .comment-actions {
        display: flex;
        gap: 0.5rem;
    }
    .comment-actions a {
        color: #7f8c8d;
        text-decoration: none;
        font-size: 0.9rem;
    }
    .comment-actions a:hover {
        color: #3498db;
    }
    .game-code-section {
        background: white;
        padding: 2rem;
        border-radius: 8px;
        margin-bottom: 1rem;
        box-shadow: 0 2px 4px rgba(0,0,0,0.1);
    }
    .game-code-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 1rem;
    }
    .game-code-header h3 {
        margin: 0;
        color: #2c3e50;
    }
    .code-toggle-btn {
        background-color: #3498db;
        color: white;
        border: none;
        padding: 0.5rem 1rem;
        border-radius: 4px;
        cursor: pointer;
        font-size: 0.9rem;
        transition: background-color 0.3s;
    }
    .code-toggle-btn:hover {
        background-color: #2980b9;
    }
    .game-code-container {
        display: none;
        margin-top: 1rem;
    }
    .game-code-container.show {
        display: block;
    }
    .game-code-content {
        background: #282c34;
        color: #abb2bf;
        padding: 1.5rem;
        border-radius: 4px;
        overflow-x: auto;
        font-family: 'Consolas', 'Monaco', 'Courier New', monospace;
        font-size: 0.9rem;
        line-height: 1.6;
        max-height: 600px;
        overflow-y: auto;
        white-space: pre-wrap;
        word-wrap: break-word;
    }
    .copy-btn {
        background-color: #27ae60;
        color: white;
        border: none;
        padding: 0.5rem 1rem;
        border-radius: 4px;
        cursor: pointer;
        font-size: 0.9rem;
        margin-top: 0.5rem;
        transition: background-color 0.3s;
    }
    .copy-btn:hover {
        background-color: #229954;
    }
</style>

<div class="post-view-container">
    <div class="post-header">
        <div class="post-title">${post.title}</div>
        <div class="post-meta">
            <span>작성자: ${post.nickname}</span>
            <span>조회수: ${post.viewCount}</span>
            <span>작성일: <fmt:formatDate value="${post.createdAtDate}" pattern="yyyy-MM-dd HH:mm"/></span>
        </div>
    </div>

    <c:if test="${not empty post.imageUrl}">
        <div style="margin-bottom: 2rem; border-radius: 8px; overflow: hidden; box-shadow: 0 4px 8px rgba(0,0,0,0.1);">
            <img src="${post.imageUrl}" alt="${post.title}" style="width: 100%; height: auto; display: block; max-height: 600px; object-fit: contain; background: #f5f5f5;" onerror="this.style.display='none'">
        </div>
    </c:if>

    <div class="post-content">${post.content}</div>

    <c:if test="${not empty post.gameCode}">
        <div style="text-align: center; margin: 2rem 0;">
            <a href="/post/${post.postId}/play" class="btn" style="background-color: #27ae60; font-size: 1.2rem; padding: 1rem 2rem;" target="_blank">🎮 게임 실행하기</a>
        </div>
        
        <div class="game-code-section">
            <div class="game-code-header">
                <h3>📄 게임 소스코드</h3>
                <button class="code-toggle-btn" onclick="toggleGameCode()">코드 보기</button>
            </div>
            <div id="gameCodeContainer" class="game-code-container">
                <pre id="gameCodeContent" class="game-code-content"><c:out value="${post.gameCode}"/></pre>
                <button class="copy-btn" onclick="copyGameCode()">📋 코드 복사</button>
            </div>
        </div>
    </c:if>

    <c:if test="${sessionScope.user != null && sessionScope.user.userId == post.userId}">
        <div class="post-actions">
            <a href="/post/${post.postId}/edit" class="btn">수정</a>
            <form method="post" action="/post/${post.postId}/delete" style="display: inline;">
                <button type="submit" class="btn btn-danger" onclick="return confirm('정말 삭제하시겠습니까?')">삭제</button>
            </form>
        </div>
    </c:if>

    <div class="comments-section">
        <h3>댓글</h3>
        
        <c:if test="${sessionScope.user != null}">
            <div class="comment-form">
                <form method="post" action="/comment/write">
                    <input type="hidden" name="postId" value="${post.postId}">
                    <textarea name="content" placeholder="댓글을 입력하세요..." required></textarea>
                    <div style="margin-top: 0.5rem; text-align: right;">
                        <button type="submit" class="btn">댓글 작성</button>
                    </div>
                </form>
            </div>
        </c:if>

        <c:forEach var="comment" items="${comments}">
            <div class="comment-item">
                <div class="comment-header">
                    <span class="comment-author">${comment.nickname}</span>
                    <span class="comment-date">
                        <fmt:formatDate value="${comment.createdAtDate}" pattern="yyyy-MM-dd HH:mm"/>
                    </span>
                </div>
                <div class="comment-content">${comment.content}</div>
                <c:if test="${sessionScope.user != null && sessionScope.user.userId == comment.userId}">
                    <div class="comment-actions">
                        <form method="post" action="/comment/${comment.commentId}/delete" style="display: inline;">
                            <input type="hidden" name="postId" value="${post.postId}">
                            <button type="submit" style="background: none; border: none; color: #e74c3c; cursor: pointer; font-size: 0.9rem;" onclick="return confirm('댓글을 삭제하시겠습니까?')">삭제</button>
                        </form>
                    </div>
                </c:if>
            </div>
        </c:forEach>

        <c:if test="${empty comments}">
            <div style="text-align: center; padding: 2rem; color: #7f8c8d;">
                아직 댓글이 없습니다.
            </div>
        </c:if>
    </div>

    <div style="text-align: center; margin-top: 2rem;">
        <a href="/post/list" class="btn">목록으로</a>
    </div>
</div>

<script>
    function toggleGameCode() {
        const container = document.getElementById('gameCodeContainer');
        const btn = document.querySelector('.code-toggle-btn');
        
        if (container.classList.contains('show')) {
            container.classList.remove('show');
            btn.textContent = '코드 보기';
        } else {
            container.classList.add('show');
            btn.textContent = '코드 숨기기';
        }
    }
    
    function copyGameCode() {
        const codeContent = document.getElementById('gameCodeContent').textContent;
        const textarea = document.createElement('textarea');
        textarea.value = codeContent;
        textarea.style.position = 'fixed';
        textarea.style.opacity = '0';
        document.body.appendChild(textarea);
        textarea.select();
        
        try {
            document.execCommand('copy');
            const copyBtn = document.querySelector('.copy-btn');
            const originalText = copyBtn.textContent;
            copyBtn.textContent = '✓ 복사됨!';
            copyBtn.style.backgroundColor = '#27ae60';
            
            setTimeout(function() {
                copyBtn.textContent = originalText;
                copyBtn.style.backgroundColor = '#27ae60';
            }, 2000);
        } catch (err) {
            alert('코드 복사에 실패했습니다. 직접 선택하여 복사해주세요.');
        }
        
        document.body.removeChild(textarea);
    }
</script>

<%@ include file="../layout/footer.jsp" %>

