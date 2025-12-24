const fs = require('fs');
const puppeteer = require('puppeteer');
const MarkdownIt = require('markdown-it');
const md = new MarkdownIt({
    html: true,
    linkify: true,
    typographer: true
});

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
            font-size: 100%;
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
        
        a:hover {
            text-decoration: underline;
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

async function generatePDF() {
    try {
        console.log('README.md 파일 읽는 중...');
        const readmeContent = fs.readFileSync('README.md', 'utf8');
        
        console.log('마크다운을 HTML로 변환 중...');
        // 마크다운을 HTML로 변환
        let htmlContent = md.render(readmeContent);
        
        // 이미지 태그의 width/height 속성을 style로 변환하여 비율 유지
        htmlContent = htmlContent.replace(/<img width="(\d+)" height="(\d+)" alt="([^"]*)" src="([^"]*)" \/>/g, 
            function(match, width, height, alt, src) {
                const aspectRatio = (parseInt(height) / parseInt(width) * 100).toFixed(2);
                return `<img src="${src}" alt="${alt}" style="max-width: 100%; height: auto; aspect-ratio: ${width}/${height};" />`;
            });
        
        const fullHtml = htmlTemplate.replace('{CONTENT}', htmlContent);
        
        // HTML 파일 저장 (참고용)
        fs.writeFileSync('README.html', fullHtml, 'utf8');
        console.log('HTML 파일 생성 완료: README.html');
        
        console.log('PDF 생성 중... (이미지 로딩에 시간이 걸릴 수 있습니다)');
        const browser = await puppeteer.launch({
            headless: true,
            args: ['--no-sandbox', '--disable-setuid-sandbox']
        });
        
        const page = await browser.newPage();
        page.setDefaultNavigationTimeout(60000); // 60초로 증가
        
        await page.setContent(fullHtml, { waitUntil: 'domcontentloaded' });
        
        // 이미지 로딩 대기 (더 긴 타임아웃)
        console.log('이미지 로딩 대기 중...');
        try {
            await page.evaluateHandle(() => {
                return Promise.all(
                    Array.from(document.images).map(img => {
                        if (img.complete) return Promise.resolve();
                        return new Promise((resolve) => {
                            img.onload = resolve;
                            img.onerror = resolve; // 에러가 나도 계속 진행
                            setTimeout(resolve, 15000); // 최대 15초 대기
                        });
                    })
                );
            });
        } catch (e) {
            console.log('일부 이미지 로딩 실패, 계속 진행합니다...');
        }
        
        // 추가 대기 시간
        await page.waitForTimeout(2000);
        
        await page.pdf({
            path: 'README.pdf',
            format: 'A4',
            printBackground: true,
            margin: {
                top: '20mm',
                right: '20mm',
                bottom: '20mm',
                left: '20mm'
            },
            preferCSSPageSize: true
        });
        
        await browser.close();
        console.log('\n✅ PDF 파일이 생성되었습니다: README.pdf');
        console.log('이미지 비율이 GitHub에서 보이는 것과 동일하게 유지되었습니다.');
        
    } catch (error) {
        console.error('오류 발생:', error.message);
        console.log('\n대안: 브라우저에서 README.html을 열고 인쇄 → PDF로 저장하세요.');
        console.log('이 방법이 더 정확한 레이아웃을 제공할 수 있습니다.');
    }
}

generatePDF();

