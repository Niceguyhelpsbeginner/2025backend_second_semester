<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="layout/header.jsp" %>
<style>
    .welcome-section {
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        color: white;
        padding: 3rem;
        border-radius: 8px;
        margin-bottom: 2rem;
        text-align: center;
    }
    .welcome-section h1 {
        font-size: 2.5rem;
        margin-bottom: 1rem;
    }
    .write-btn {
        text-align: right;
        margin-bottom: 2rem;
    }
    .posts-grid {
        display: grid;
        grid-template-columns: repeat(4, 1fr);
        gap: 1.5rem;
        margin-bottom: 2rem;
    }
    @media (max-width: 1200px) {
        .posts-grid {
            grid-template-columns: repeat(3, 1fr);
        }
    }
    @media (max-width: 900px) {
        .posts-grid {
            grid-template-columns: repeat(2, 1fr);
        }
    }
    @media (max-width: 600px) {
        .posts-grid {
            grid-template-columns: 1fr;
        }
    }
    .post-card {
        background: white;
        border-radius: 12px;
        overflow: hidden;
        box-shadow: 0 2px 8px rgba(0,0,0,0.1);
        transition: transform 0.2s, box-shadow 0.2s;
        cursor: pointer;
        display: flex;
        flex-direction: column;
    }
    .post-card:hover {
        transform: translateY(-4px);
        box-shadow: 0 8px 16px rgba(0,0,0,0.15);
    }
    .post-thumbnail {
        width: 100%;
        height: 200px;
        object-fit: cover;
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        display: flex;
        align-items: center;
        justify-content: center;
        color: white;
        font-size: 3rem;
    }
    .post-thumbnail img {
        width: 100%;
        height: 100%;
        object-fit: cover;
    }
    .post-info {
        padding: 1rem;
        flex-grow: 1;
        display: flex;
        flex-direction: column;
    }
    .post-title {
        font-size: 1rem;
        font-weight: 600;
        margin-bottom: 0.5rem;
        color: #2c3e50;
        line-height: 1.4;
        display: -webkit-box;
        -webkit-line-clamp: 2;
        -webkit-box-orient: vertical;
        overflow: hidden;
    }
    .post-title a {
        color: #2c3e50;
        text-decoration: none;
    }
    .post-title a:hover {
        color: #3498db;
    }
    .post-meta {
        color: #7f8c8d;
        font-size: 0.85rem;
        margin-top: auto;
        padding-top: 0.5rem;
    }
    .post-author {
        display: flex;
        align-items: center;
        gap: 0.5rem;
        margin-bottom: 0.25rem;
    }
    .post-stats {
        display: flex;
        gap: 1rem;
        color: #95a5a6;
    }
</style>

<div class="welcome-section">
    <h1>김성진의 게임엔젤에 오신 것을 환영합니다!</h1>
    <p>자유롭게 글을 작성하고 소통하세요.</p>
</div>

<div class="write-btn">
    <c:if test="${sessionScope.user != null}">
        <a href="/post/write" class="btn">글쓰기</a>
    </c:if>
</div>

<div class="posts-grid">
    <c:forEach var="post" items="${posts}">
        <div class="post-card" onclick="window.location.href='/post/${post.postId}'">
            <div class="post-thumbnail">
                <c:choose>
                    <c:when test="${not empty post.imageUrl}">
                        <img src="${post.imageUrl}" alt="${post.title}" onerror="this.parentElement.innerHTML='🎮'">
                    </c:when>
                    <c:otherwise>
                        🎮
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="post-info">
                <div class="post-title">
                    <a href="/post/${post.postId}">${post.title}</a>
                </div>
                <div class="post-meta">
                    <div class="post-author">
                        <span>${post.nickname}</span>
                    </div>
                    <div class="post-stats">
                        <span>조회수 ${post.viewCount}</span>
                        <span>•</span>
                        <span><fmt:formatDate value="${post.createdAtDate}" pattern="MM-dd"/></span>
                    </div>
                </div>
            </div>
        </div>
    </c:forEach>
</div>

<c:if test="${empty posts}">
    <div style="text-align: center; padding: 3rem; background: white; border-radius: 8px;">
        <p>아직 작성된 게시글이 없습니다.</p>
        <c:if test="${sessionScope.user != null}">
            <a href="/post/write" class="btn" style="margin-top: 1rem;">첫 게시글 작성하기</a>
        </c:if>
    </div>
</c:if>

<%@ include file="layout/footer.jsp" %>

