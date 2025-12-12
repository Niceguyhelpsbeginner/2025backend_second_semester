<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../layout/header.jsp" %>

<style>
    .write-container {
        max-width: 800px;
        margin: 3rem auto;
        background: white;
        padding: 2rem;
        border-radius: 8px;
        box-shadow: 0 2px 8px rgba(0,0,0,0.1);
    }
    .write-container h2 {
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
    .form-group input[type="text"] {
        width: 100%;
        padding: 0.75rem;
        border: 1px solid #ddd;
        border-radius: 4px;
        font-size: 1rem;
    }
    .form-group textarea {
        width: 100%;
        padding: 0.75rem;
        border: 1px solid #ddd;
        border-radius: 4px;
        font-size: 1rem;
        min-height: 300px;
        resize: vertical;
    }
    .form-group input:focus,
    .form-group textarea:focus {
        outline: none;
        border-color: #3498db;
    }
    .button-group {
        display: flex;
        gap: 1rem;
        justify-content: flex-end;
        margin-top: 2rem;
    }
</style>

<div class="write-container">
    <h2>글쓰기</h2>
    <form method="post" action="/post/write" enctype="multipart/form-data">
        <div class="form-group">
            <label for="title">제목</label>
            <input type="text" id="title" name="title" required>
        </div>
        <div class="form-group">
            <label for="content">내용</label>
            <textarea id="content" name="content" required></textarea>
        </div>
        <div class="form-group">
            <label for="imageFile">썸네일 이미지</label>
            <input type="file" id="imageFile" name="imageFile" accept="image/*">
            <small style="color: #7f8c8d; display: block; margin-top: 0.5rem;">이미지 파일을 업로드하면 게시글 목록에 썸네일로 표시됩니다. (JPG, PNG, GIF, 최대 10MB)</small>
            <div id="imagePreview" style="margin-top: 1rem; display: none;">
                <img id="previewImg" src="" alt="미리보기" style="max-width: 300px; max-height: 200px; border-radius: 4px; border: 1px solid #ddd;">
            </div>
        </div>
        <div class="form-group">
            <label for="gameCode">게임 코드 (HTML/CSS/JavaScript)</label>
            <textarea id="gameCode" name="gameCode" placeholder="HTML, CSS, JavaScript 코드를 입력하세요. 예: &lt;html&gt;&lt;body&gt;&lt;h1&gt;게임&lt;/h1&gt;&lt;script&gt;alert('Hello!');&lt;/script&gt;&lt;/body&gt;&lt;/html&gt;" style="font-family: monospace; min-height: 400px;"></textarea>
            <small style="color: #7f8c8d; display: block; margin-top: 0.5rem;">웹 게임 코드를 입력하면 게임 실행 버튼이 표시됩니다.</small>
        </div>
        <div class="button-group">
            <a href="/post/list" class="btn" style="background-color: #95a5a6;">취소</a>
            <button type="submit" class="btn">작성</button>
        </div>
    </form>
</div>

<script>
    document.getElementById('imageFile').addEventListener('change', function(e) {
        const file = e.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function(e) {
                document.getElementById('previewImg').src = e.target.result;
                document.getElementById('imagePreview').style.display = 'block';
            };
            reader.readAsDataURL(file);
        } else {
            document.getElementById('imagePreview').style.display = 'none';
        }
    });
</script>

<%@ include file="../layout/footer.jsp" %>


