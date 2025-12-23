-- 테스트용 회원 데이터
INSERT INTO users (username, password, email, nickname) VALUES
('admin', 'admin123', 'admin@example.com', '관리자'),
('user1', 'user123', 'user1@example.com', '사용자1'),
('user2', 'user123', 'user2@example.com', '사용자2');

-- 테스트용 게시글 데이터
INSERT INTO posts (user_id, title, content, view_count) VALUES
(1, '커뮤니티에 오신 것을 환영합니다!', '이곳은 커뮤니티 사이트입니다. 자유롭게 글을 작성해주세요.', 10),
(2, '첫 번째 게시글입니다', '안녕하세요! 첫 번째 게시글을 작성했습니다.', 5),
(3, 'Spring MVC에 대해', 'Spring MVC 프레임워크를 사용하여 이 사이트를 만들었습니다.', 8);

-- 테스트용 댓글 데이터
INSERT INTO comments (post_id, user_id, content) VALUES
(1, 2, '환영합니다!'),
(1, 3, '좋은 커뮤니티네요.'),
(2, 1, '첫 게시글 축하합니다!');













