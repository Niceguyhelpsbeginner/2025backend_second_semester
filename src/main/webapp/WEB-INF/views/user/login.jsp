<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../layout/header.jsp" %>

<style>
    .login-container {
        max-width: 400px;
        margin: 3rem auto;
        background: white;
        padding: 2rem;
        border-radius: 8px;
        box-shadow: 0 2px 8px rgba(0,0,0,0.1);
    }
    .login-container h2 {
        text-align: center;
        margin-bottom: 2rem;
        color: #2c3e50;
    }
    .form-group {
        margin-bottom: 1.5rem;
    }
    .form-group label {
        display: block;
        margin-bottom: 0.5rem;
        color: #555;
        font-weight: 500;
    }
    .form-group input {
        width: 100%;
        padding: 0.75rem;
        border: 1px solid #ddd;
        border-radius: 4px;
        font-size: 1rem;
    }
    .form-group input:focus {
        outline: none;
        border-color: #3498db;
    }
    .btn-block {
        width: 100%;
        padding: 0.75rem;
        font-size: 1rem;
    }
    .register-link {
        text-align: center;
        margin-top: 1.5rem;
        color: #7f8c8d;
    }
    .register-link a {
        color: #3498db;
        text-decoration: none;
    }
</style>

<div class="login-container">
    <h2>로그인</h2>
    <form method="post" action="/user/login">
        <div class="form-group">
            <label for="username">아이디</label>
            <input type="text" id="username" name="username" required>
        </div>
        <div class="form-group">
            <label for="password">비밀번호</label>
            <input type="password" id="password" name="password" required>
        </div>
        <button type="submit" class="btn btn-block">로그인</button>
    </form>
    <div class="register-link">
        계정이 없으신가요? <a href="/user/register">회원가입</a>
    </div>
</div>

<%@ include file="../layout/footer.jsp" %>














