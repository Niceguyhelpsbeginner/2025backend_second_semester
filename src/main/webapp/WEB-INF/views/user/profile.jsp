<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../layout/header.jsp" %>

<style>
    .profile-container {
        max-width: 600px;
        margin: 3rem auto;
        background: white;
        padding: 2rem;
        border-radius: 8px;
        box-shadow: 0 2px 8px rgba(0,0,0,0.1);
    }
    .profile-container h2 {
        margin-bottom: 2rem;
        color: #2c3e50;
    }
    .profile-info {
        margin-bottom: 1.5rem;
    }
    .profile-info label {
        display: block;
        margin-bottom: 0.5rem;
        color: #555;
        font-weight: 500;
    }
    .profile-info span {
        display: block;
        padding: 0.75rem;
        background-color: #f8f9fa;
        border-radius: 4px;
        color: #2c3e50;
    }
</style>

<div class="profile-container">
    <h2>프로필</h2>
    <div class="profile-info">
        <label>아이디</label>
        <span>${user.username}</span>
    </div>
    <div class="profile-info">
        <label>이메일</label>
        <span>${user.email}</span>
    </div>
    <div class="profile-info">
        <label>닉네임</label>
        <span>${user.nickname}</span>
    </div>
    <div style="margin-top: 2rem;">
        <a href="/post/list" class="btn">게시판으로 이동</a>
    </div>
</div>

<%@ include file="../layout/footer.jsp" %>













