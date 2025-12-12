<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${post.title} - 게임 실행</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #1a1a1a;
            color: white;
        }
        .game-header {
            background-color: #2c3e50;
            padding: 1rem 2rem;
            display: flex;
            justify-content: space-between;
            align-items: center;
            box-shadow: 0 2px 4px rgba(0,0,0,0.3);
        }
        .game-header h1 {
            font-size: 1.5rem;
            color: white;
        }
        .game-header a {
            color: white;
            text-decoration: none;
            padding: 0.5rem 1rem;
            background-color: #3498db;
            border-radius: 4px;
            transition: background-color 0.3s;
        }
        .game-header a:hover {
            background-color: #2980b9;
        }
        .game-container {
            width: 100%;
            height: calc(100vh - 80px);
            border: none;
            background: white;
        }
        .game-wrapper {
            width: 100%;
            height: 100%;
            position: relative;
        }
        iframe {
            width: 100%;
            height: 100%;
            border: none;
            background: white;
        }
        .loading {
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            color: #2c3e50;
            font-size: 1.2rem;
        }
    </style>
</head>
<body>
    <div class="game-header">
        <h1>🎮 ${post.title}</h1>
        <a href="/post/${post.postId}">게시글로 돌아가기</a>
    </div>
    <div class="game-wrapper">
        <div class="loading">게임을 로딩 중입니다...</div>
        <iframe id="gameFrame" class="game-container" sandbox="allow-scripts allow-forms allow-popups allow-modals"></iframe>
    </div>
    
    <c:if test="${not empty post.gameCode}">
        <textarea id="gameCodeData" style="display:none;"><c:out value="${post.gameCode}" escapeXml="false"/></textarea>
    </c:if>
    
    <script>
        window.addEventListener('load', function() {
            const loading = document.querySelector('.loading');
            const iframe = document.getElementById('gameFrame');
            
            <c:choose>
                <c:when test="${not empty post.gameCode}">
                    // 게임 코드를 안전하게 가져오기
                    const gameCodeElement = document.getElementById('gameCodeData');
                    let gameCode = '';
                    
                    if (gameCodeElement) {
                        gameCode = gameCodeElement.value || gameCodeElement.textContent || gameCodeElement.innerText;
                    }
                    
                    if (gameCode) {
                        try {
                            const iframeDoc = iframe.contentDocument || iframe.contentWindow.document;
                            
                            // 게임 코드가 완전한 HTML 문서인지 확인
                            const trimmedCode = gameCode.trim();
                            if (trimmedCode.toLowerCase().startsWith('<!doctype') || 
                                trimmedCode.toLowerCase().startsWith('<html')) {
                                // 완전한 HTML 문서인 경우 그대로 사용
                                iframeDoc.open();
                                iframeDoc.write(gameCode);
                                iframeDoc.close();
                            } else {
                                // 부분 HTML인 경우 body에 넣기
                                iframeDoc.open();
                                iframeDoc.write('<!DOCTYPE html><html><head><meta charset="UTF-8"></head><body>' 
                                    + gameCode +'</body></html>');
                                iframeDoc.close();
                            }
                            
                            // 로딩 완료 처리
                            setTimeout(function() {
                                if (loading) {
                                    loading.style.display = 'none';
                                }
                            }, 500);
                        } catch (e) {
                            console.error('게임 로드 오류:', e);
                            // srcdoc 방식으로 폴백 시도
                            try {
                                iframe.srcdoc = gameCode;
                            } catch (e2) {
                                console.error('srcdoc 폴백도 실패:', e2);
                                if (loading) {
                                    loading.textContent = '게임을 로드할 수 없습니다.';
                                }
                            }
                        }
                    } else {
                        if (loading) {
                            loading.textContent = '게임 코드가 없습니다.';
                        }
                    }
                </c:when>
                <c:otherwise>
                    // 게임 코드가 없는 경우
                    iframe.srcdoc = '<html><body style="display: flex; justify-content: center; align-items: center; height: 100vh; font-family: Arial;"><h1 style="color: #999;">게임 코드가 없습니다.</h1></body></html>';
                    if (loading) {
                        loading.style.display = 'none';
                    }
                </c:otherwise>
            </c:choose>
            
            iframe.addEventListener('load', function() {
                if (loading) {
                    loading.style.display = 'none';
                }
            });
            
            // 3초 후에도 로딩이 안 끝나면 숨김
            setTimeout(function() {
                if (loading) {
                    loading.style.display = 'none';
                }
            }, 3000);
        });
    </script>
</body>
</html>

