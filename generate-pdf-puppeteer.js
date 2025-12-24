const fs = require('fs');
const puppeteer = require('puppeteer');

// GitHub 스타일의 HTML 템플릿
const htmlTemplate = `<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ゲームコミュニティサイト - ポートフォリオ</title>
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Noto+Sans+JP:wght@400;500;700&display=swap');
        
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", "Noto Sans", Helvetica, Arial, sans-serif, "Apple Color Emoji", "Segoe UI Emoji";
            font-size: 16px;
            line-height: 1.6;
            color: #24292f;
            background-color: #ffffff;
            padding: 40px;
            max-width: 1012px;
            margin: 0 auto;
        }
        
        h1 {
            font-size: 2em;
            font-weight: 600;
            margin-bottom: 0.5em;
            padding-bottom: 0.3em;
            border-bottom: 1px solid #d0d7de;
        }
        
        h2 {
            font-size: 1.5em;
            font-weight: 600;
            margin-top: 24px;
            margin-bottom: 16px;
            padding-bottom: 0.3em;
            border-bottom: 1px solid #d0d7de;
        }
        
        h3 {
            font-size: 1.25em;
            font-weight: 600;
            margin-top: 24px;
            margin-bottom: 16px;
        }
        
        h4 {
            font-size: 1em;
            font-weight: 600;
            margin-top: 16px;
            margin-bottom: 8px;
        }
        
        p {
            margin-bottom: 16px;
        }
        
        ul, ol {
            margin-bottom: 16px;
            padding-left: 2em;
        }
        
        li {
            margin-bottom: 8px;
        }
        
        code {
            font-family: "SFMono-Regular", Consolas, "Liberation Mono", Menlo, Courier, monospace;
            font-size: 85%;
            background-color: rgba(175, 184, 193, 0.2);
            padding: 0.2em 0.4em;
            border-radius: 3px;
        }
        
        pre {
            font-family: "SFMono-Regular", Consolas, "Liberation Mono", Menlo, Courier, monospace;
            font-size: 85%;
            background-color: #f6f8fa;
            border: 1px solid #d0d7de;
            border-radius: 6px;
            padding: 16px;
            overflow: auto;
            margin-bottom: 16px;
            white-space: pre-wrap;
            word-wrap: break-word;
        }
        
        pre code {
            background-color: transparent;
            padding: 0;
        }
        
        img {
            max-width: 100%;
            height: auto;
            border: 1px solid #d0d7de;
            border-radius: 6px;
            margin: 16px 0;
            display: block;
        }
        
        blockquote {
            padding: 0 1em;
            color: #656d76;
            border-left: 0.25em solid #d0d7de;
            margin-bottom: 16px;
        }
        
        table {
            border-collapse: collapse;
            width: 100%;
            margin-bottom: 16px;
        }
        
        table th,
        table td {
            border: 1px solid #d0d7de;
            padding: 6px 13px;
        }
        
        table th {
            background-color: #f6f8fa;
            font-weight: 600;
        }
        
        a {
            color: #0969da;
            text-decoration: none;
        }
        
        hr {
            height: 0.25em;
            padding: 0;
            margin: 24px 0;
            background-color: #d0d7de;
            border: 0;
        }
        
        @media print {
            body {
                padding: 20px;
            }
            
            img {
                page-break-inside: avoid;
                max-width: 100%;
            }
            
            h1, h2, h3 {
                page-break-after: avoid;
            }
        }
    </style>
</head>
<body>
    {CONTENT}
</body>
</html>`;

// 마크다운을 HTML로 변환 (간단한 변환)
function markdownToHtml(markdown) {
    let html = markdown;
    
    // 헤더 변환
    html = html.replace(/^#### (.*$)/gim, '<h4>$1</h4>');
    html = html.replace(/^### (.*$)/gim, '<h3>$1</h3>');
    html = html.replace(/^## (.*$)/gim, '<h2>$1</h2>');
    html = html.replace(/^# (.*$)/gim, '<h1>$1</h1>');
    
    // 이미지는 그대로 유지 (크기 속성 포함)
    
    // 코드 블록 변환
    html = html.replace(/```(\w+)?\n([\s\S]*?)```/g, function(match, lang, code) {
        return '<pre><code>' + escapeHtml(code.trim()) + '</code></pre>';
    });
    
    // 인라인 코드 변환
    html = html.replace(/`([^`]+)`/g, '<code>$1</code>');
    
    // 볼드 변환
    html = html.replace(/\*\*(.+?)\*\*/g, '<strong>$1</strong>');
    
    // 리스트 변환 (간단한 버전)
    const lines = html.split('\n');
    let inList = false;
    let result = [];
    
    for (let i = 0; i < lines.length; i++) {
        const line = lines[i];
        if (line.match(/^[-*] (.+)$/)) {
            if (!inList) {
                result.push('<ul>');
                inList = true;
            }
            result.push('<li>' + line.replace(/^[-*] (.+)$/, '$1') + '</li>');
        } else {
            if (inList) {
                result.push('</ul>');
                inList = false;
            }
            if (line.trim()) {
                result.push('<p>' + line + '</p>');
            } else {
                result.push('<br>');
            }
        }
    }
    
    if (inList) {
        result.push('</ul>');
    }
    
    html = result.join('\n');
    
    // 빈 <p> 태그 제거
    html = html.replace(/<p>\s*<\/p>/g, '');
    
    return html;
}

function escapeHtml(text) {
    const map = {
        '&': '&amp;',
        '<': '&lt;',
        '>': '&gt;',
        '"': '&quot;',
        "'": '&#039;'
    };
    return text.replace(/[&<>"']/g, m => map[m]);
}

async function generatePDF() {
    try {
        // README.md 읽기
        const readmeContent = fs.readFileSync('README.md', 'utf8');
        
        // 마크다운을 HTML로 변환
        const htmlContent = markdownToHtml(readmeContent);
        const fullHtml = htmlTemplate.replace('{CONTENT}', htmlContent);
        
        // HTML 파일 저장
        fs.writeFileSync('README.html', fullHtml, 'utf8');
        console.log('HTML 파일 생성 완료');
        
        // Puppeteer로 PDF 생성
        const browser = await puppeteer.launch({
            headless: true,
            args: ['--no-sandbox', '--disable-setuid-sandbox']
        });
        
        const page = await browser.newPage();
        await page.setContent(fullHtml, { waitUntil: 'networkidle0' });
        
        // 이미지 로딩 대기
        await page.evaluateHandle(() => {
            return Promise.all(
                Array.from(document.images).map(img => {
                    if (img.complete) return Promise.resolve();
                    return new Promise((resolve, reject) => {
                        img.onload = resolve;
                        img.onerror = reject;
                        setTimeout(resolve, 5000); // 타임아웃
                    });
                })
            );
        });
        
        await page.pdf({
            path: 'README.pdf',
            format: 'A4',
            printBackground: true,
            margin: {
                top: '20mm',
                right: '20mm',
                bottom: '20mm',
                left: '20mm'
            }
        });
        
        await browser.close();
        console.log('PDF 파일이 생성되었습니다: README.pdf');
        
    } catch (error) {
        console.error('오류 발생:', error.message);
        console.log('\n대안: 브라우저에서 README.html을 열고 인쇄 → PDF로 저장하세요.');
    }
}

generatePDF();


