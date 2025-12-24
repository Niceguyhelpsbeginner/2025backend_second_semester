const fs = require('fs');
const puppeteer = require('puppeteer');
const MarkdownIt = require('markdown-it');
const md = new MarkdownIt({
    html: true,
    linkify: true,
    typographer: true
});

// 설정 파일 읽기
const config = JSON.parse(fs.readFileSync('pdf-config.json', 'utf8'));
const stylesheet = fs.readFileSync(config.stylesheet, 'utf8');

// HTML 템플릿
const htmlTemplate = `<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ゲームコミュニティサイト - ポートフォリオ</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+JP:wght@400;500;700&display=swap" rel="stylesheet">
    <style>
        ${stylesheet}
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
                // GitHub에서 보이는 것과 동일한 비율 유지
                return `<img src="${src}" alt="${alt}" style="max-width: 100%; height: auto;" />`;
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
        page.setDefaultNavigationTimeout(120000); // 120초로 증가
        
        await page.setContent(fullHtml, { waitUntil: 'domcontentloaded' });
        
        // 이미지 로딩 대기
        console.log('이미지 로딩 대기 중...');
        try {
            await page.evaluateHandle(() => {
                return Promise.all(
                    Array.from(document.images).map(img => {
                        if (img.complete) return Promise.resolve();
                        return new Promise((resolve) => {
                            img.onload = resolve;
                            img.onerror = resolve;
                            setTimeout(resolve, 20000); // 최대 20초 대기
                        });
                    })
                );
            });
        } catch (e) {
            console.log('일부 이미지 로딩 실패, 계속 진행합니다...');
        }
        
        // 추가 대기 시간
        await page.waitForTimeout(3000);
        
        // PDF 생성 (설정 파일의 옵션 사용)
        await page.pdf({
            path: 'README.pdf',
            format: config.pdf_options.format,
            printBackground: config.pdf_options.printBackground,
            preferCSSPageSize: config.pdf_options.preferCSSPageSize,
            margin: config.pdf_options.margin
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
