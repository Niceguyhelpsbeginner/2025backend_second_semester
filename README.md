# ゲームコミュニティサイト

Spring MVCフレームワークを使用したJava Webアプリケーションです。ユーザーが作成したゲームコードを共有し、実行できるコミュニティプラットフォームです。

## 📋 プロジェクト概要

### 開発の動機と背景

コンピューターに最初に興味を持ったきっかけは、多くの人と同じようにゲームでした。小学校のコンピューター室でFlashゲームを楽しんでいた純粋な喜びが、やがてコンピューターソフトウェア学科を卒業するきっかけとなりました。

ある時、情熱が冷めてしまった自分を見て「私は何のためにコンピューターを勉強したのか」と自問したことがあります。今振り返ると、あの時純粋に感じた楽しさを他の人と共有し、より良いサービスを世の中に提供する人になるためにコンピューターを勉強していたのだと思います。

そこで、純粋にコンピューターを好きだった幼い頃の自分に、そして私のように純粋にコンピューターを好きな人たちに、あの時の純粋さを贈りたいと思い、幼い頃コンピューター室で楽しんだゲームを共有できるコミュニティサイトを開発することにしました。

### プロジェクトの目的

- **ゲームコードの共有**: ユーザーが作成したHTML/CSS/JavaScriptゲームコードを共有できるプラットフォームの提供
- **コミュニティ形成**: ゲーム開発に興味を持つ人々が交流できる場の創出
- **技術学習**: Webアプリケーション開発の基本スキルを実践的に学習

## 🎯 主な機能

### 1. ユーザー管理機能
- **ユーザー登録**: 新規ユーザー登録（バリデーション機能付き）
<img width="627" height="781" alt="image" src="https://github.com/user-attachments/assets/cf73c87e-03d9-4d1b-b8fc-cd3983aac9b3" />
- **ログイン/ログアウト**: セッション管理による認証機能
<img width="506" height="543" alt="image" src="https://github.com/user-attachments/assets/0dfb4478-2226-4eed-a030-2e478cdd7de5" />
- **プロフィール表示**: ユーザー情報の確認
<img width="766" height="610" alt="image" src="https://github.com/user-attachments/assets/0e72ce37-e318-42b7-aa5a-0a54fbe28faf" />

### 2. 掲示板機能
- **投稿作成**: タイトル、本文、ゲームコード、画像の投稿
<img width="664" height="776" alt="image" src="https://github.com/user-attachments/assets/cf7a8ada-8818-43ad-a01b-53560f785d26" />

- **投稿一覧表示**: グリッドレイアウトによる視覚的な一覧表示
<img width="1901" height="846" alt="image" src="https://github.com/user-attachments/assets/22d10741-9220-42b4-85c3-be8a962c73d0" />

- **投稿詳細表示**: 投稿内容、ゲーム実行、ソースコード表示
<img width="582" height="854" alt="image" src="https://github.com/user-attachments/assets/eb8c440b-54a2-4514-92da-4b3d8cc75808" />

- **投稿編集・削除**: 作成者のみが編集・削除可能
<img width="532" height="789" alt="image" src="https://github.com/user-attachments/assets/b50fcb41-5706-40ca-8e58-9cdbb1aae39a" />

### 3. コメント機能
- **コメント投稿**: 投稿に対するコメント機能
<img width="570" height="374" alt="image" src="https://github.com/user-attachments/assets/513500ea-860b-41f6-a07e-2cdb523c96c1" />
- **コメント削除**: 作成者のみが編集・削除可能
<img width="1134" height="648" alt="image" src="https://github.com/user-attachments/assets/7aba4577-8449-4223-ab22-d7d520abd8c2" />

### 4. ゲーム実行機能（独自機能）
- **iframe sandboxによる安全な実行環境**: ユーザーが投稿したHTML/CSS/JavaScriptコードを安全に実行
- **ソースコード表示**: 投稿されたゲームコードを閲覧可能
- **動的ロード**: JavaScriptによる動的なゲームコードの読み込み
<img width="1919" height="1006" alt="image" src="https://github.com/user-attachments/assets/14089332-e595-425b-9575-bde3f1c04895" />

### 5. 画像アップロード機能
<img width="1005" height="180" alt="image" src="https://github.com/user-attachments/assets/44bf06a7-faab-495e-8d91-85b1139d9527" />
- **画像アップロード**: マルチパートファイルアップロード処理
- **サムネイル表示**: 投稿一覧での画像サムネイル表示
- **画像削除**: 投稿編集時の画像削除機能

## 🛠️ 使用技術・スキル

### プログラミング言語
- **Java 11**: メインの開発言語
- **JavaScript**: フロントエンドの動的処理
- **SQL**: データベース操作

### フレームワーク・ライブラリ
- **Spring Framework 5.3.21**: 
  - Spring MVC（Web層）
  - Spring JDBC（データアクセス層）
  - Spring Transaction（トランザクション管理）
- **JSP/JSTL**: ビュー層の実装

### データベース
- **MariaDB**: リレーショナルデータベース
  - ユーザー情報、投稿、コメントの管理

### ビルドツール・サーバー
- **Maven 3.6以上**: プロジェクトのビルドと依存関係管理
- **Apache Tomcat 7**: アプリケーションサーバー

### その他の技術
- **Apache Commons FileUpload**: ファイルアップロード処理
- **Jackson**: JSON処理
- **SLF4J + Logback**: ロギング

## 💡 こだわったポイント・技術的な挑戦

### 1. MVCアーキテクチャの実装
- **明確な責務分離**: Controller、Service、DAO層を明確に分離し、保守性の高いコード構造を実現
- **トランザクション管理**: Springのトランザクション管理機能を活用し、データ整合性を保証

### 2. セキュリティ対策
- **iframe sandbox属性の活用**: ユーザーが投稿したゲームコードを安全に実行するため、iframeのsandbox属性を使用
  - `allow-scripts`: JavaScript実行を許可
  - `allow-forms`: フォーム送信を許可
  - `allow-popups`: ポップアップを許可
  - `allow-modals`: モーダルダイアログを許可
  - `allow-same-origin`は意図的に除外し、セキュリティを強化

### 3. 画像アップロード機能の実装
- **ファイル検証**: 画像ファイルのみを許可（JPG, PNG, GIF, WebP, BMP）
- **UUIDによる一意なファイル名生成**: ファイル名の衝突を防止
- **動的画像配信**: ImageControllerによる画像の動的配信
- **Tomcat 7互換性**: `setContentLengthLong`メソッドが使用できないため、`setContentLength`を使用するなど、サーバー互換性を考慮

### 4. ゲームコード実行機能の実装
- **完全なHTMLドキュメントのサポート**: ユーザーが投稿した完全なHTMLドキュメントをiframe内で実行
- **動的ロード**: JavaScriptによる安全なコードの動的読み込み
- **ソースコード表示**: 投稿されたゲームコードを閲覧可能にする機能

### 5. ユーザー体験の向上
- **レスポンシブデザイン**: モバイルデバイスでも快適に閲覧可能
- **視覚的なUI**: モダンなデザインと直感的な操作
- **エラーハンドリング**: 適切なエラーメッセージの表示

## 📁 プロジェクト構造

```
backend_project/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/community/
│   │   │       ├── controller/    # コントローラー層（リクエスト処理）
│   │   │       │   ├── HomeController.java
│   │   │       │   ├── UserController.java
│   │   │       │   ├── PostController.java
│   │   │       │   ├── CommentController.java
│   │   │       │   └── ImageController.java
│   │   │       ├── service/       # サービス層（ビジネスロジック）
│   │   │       │   ├── UserService.java
│   │   │       │   ├── PostService.java
│   │   │       │   └── CommentService.java
│   │   │       ├── dao/          # データアクセス層（DAO）
│   │   │       │   ├── UserDao.java
│   │   │       │   ├── PostDao.java
│   │   │       │   └── CommentDao.java
│   │   │       ├── model/        # モデルクラス（エンティティ）
│   │   │       │   ├── User.java
│   │   │       │   ├── Post.java
│   │   │       │   └── Comment.java
│   │   │       └── util/         # ユーティリティクラス
│   │   │           └── FileUploadUtil.java
│   │   ├── resources/
│   │   │   ├── schema.sql        # データベーススキーマ
│   │   │   └── data.sql          # 初期データ
│   │   └── webapp/
│   │       ├── WEB-INF/
│   │       │   ├── web.xml       # Webアプリケーション設定
│   │       │   ├── spring/
│   │       │   │   ├── root-context.xml      # Springルートコンテキスト
│   │       │   │   └── appServlet/
│   │       │   │       └── servlet-context.xml  # Spring MVC設定
│   │       │   └── views/        # JSPビューファイル
│   │       │       ├── home.jsp
│   │       │       ├── layout/
│   │       │       ├── post/
│   │       │       └── user/
│   │       └── resources/        # 静的リソース（画像など）
│   └── test/
└── pom.xml                        # Maven設定ファイル
```

## 🚀 実行方法

### 前提条件
- JDK 11以上
- Maven 3.6以上
- MariaDB（データベース）
- Apache Tomcat 7以上

### STS(Spring Tool Suite)での実行方法

#### 1. プロジェクトのインポート
1. STSを起動
2. `File` → `Import` → `Maven` → `Existing Maven Projects`
3. プロジェクトルートディレクトリを選択
4. `Maven` → `Update Project...`で依存関係をダウンロード

#### 2. データベース設定
```sql
CREATE DATABASE communitydb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

#### 3. サーバー設定
1. `Servers`タブでTomcatサーバーを追加
2. プロジェクトをサーバーに追加
3. サーバーを起動

#### 4. ブラウザでアクセス
```
http://localhost:8080
```

### コマンドラインでの実行
```bash
# Windows
run.bat

# PowerShell
run.ps1

# または直接Mavenコマンド
mvn clean package tomcat7:run
```

## 📊 データベース設計

### データベース作成

```sql
-- データベース作成
CREATE DATABASE communitydb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- データベース使用
USE communitydb;
```

### テーブル構成

#### 1. usersテーブル（ユーザー情報）

```sql
CREATE TABLE IF NOT EXISTS users (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL,
    nickname VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

**カラム説明**:
- `user_id`: ユーザーID（主キー、自動増分）
- `username`: ユーザー名（一意制約）
- `password`: パスワード
- `email`: メールアドレス
- `nickname`: ニックネーム
- `created_at`: 作成日時
- `updated_at`: 更新日時

#### 2. postsテーブル（投稿情報）

```sql
CREATE TABLE IF NOT EXISTS posts (
    post_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    title VARCHAR(200) NOT NULL,
    content TEXT NOT NULL,
    game_code TEXT,
    image_url VARCHAR(500),
    view_count INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);
```

**カラム説明**:
- `post_id`: 投稿ID（主キー、自動増分）
- `user_id`: ユーザーID（外部キー、usersテーブル参照）
- `title`: 投稿タイトル
- `content`: 投稿本文
- `game_code`: ゲームコード（HTML/CSS/JavaScript）
- `image_url`: 画像URL（サムネイル用）
- `view_count`: 閲覧数（デフォルト: 0）
- `created_at`: 作成日時
- `updated_at`: 更新日時

#### 3. commentsテーブル（コメント情報）

```sql
CREATE TABLE IF NOT EXISTS comments (
    comment_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    post_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (post_id) REFERENCES posts(post_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);
```

**カラム説明**:
- `comment_id`: コメントID（主キー、自動増分）
- `post_id`: 投稿ID（外部キー、postsテーブル参照）
- `user_id`: ユーザーID（外部キー、usersテーブル参照）
- `content`: コメント内容
- `created_at`: 作成日時
- `updated_at`: 更新日時

### インデックス作成

```sql
-- パフォーマンス向上のためのインデックス
CREATE INDEX idx_posts_user_id ON posts(user_id);
CREATE INDEX idx_posts_created_at ON posts(created_at DESC);
CREATE INDEX idx_comments_post_id ON comments(post_id);
CREATE INDEX idx_comments_user_id ON comments(user_id);
```

### ER図（テーブル関係）

```
users (1) ──────< (N) posts
                      │
                      │ (1)
                      │
                      │
                      └───────< (N) comments
                      │
users (1) ────────────┘
```

**リレーションシップ**:
- `users` と `posts`: 1対多（1人のユーザーが複数の投稿を作成可能）
- `posts` と `comments`: 1対多（1つの投稿に複数のコメントが可能）
- `users` と `comments`: 1対多（1人のユーザーが複数のコメントを投稿可能）

### 外部キー制約

- **CASCADE削除**: ユーザーが削除されると、そのユーザーの投稿とコメントも自動的に削除される
- **データ整合性**: 外部キー制約により、存在しないユーザーIDや投稿IDへの参照を防止

### サンプルデータ（テスト用）

```sql
-- テスト用ユーザーデータ
INSERT INTO users (username, password, email, nickname) VALUES
('admin', 'admin123', 'admin@example.com', '管理者'),
('user1', 'user123', 'user1@example.com', 'ユーザー1'),
('user2', 'user123', 'user2@example.com', 'ユーザー2');

-- テスト用投稿データ
INSERT INTO posts (user_id, title, content, view_count) VALUES
(1, 'コミュニティへようこそ！', 'こちらはコミュニティサイトです。自由に投稿してください。', 10),
(2, '最初の投稿です', 'こんにちは！最初の投稿を書きました。', 5),
(3, 'Spring MVCについて', 'Spring MVCフレームワークを使用してこのサイトを作成しました。', 8);

-- テスト用コメントデータ
INSERT INTO comments (post_id, user_id, content) VALUES
(1, 2, 'ようこそ！'),
(1, 3, '良いコミュニティですね。'),
(2, 1, '最初の投稿おめでとうございます！');
```

## 🎓 学習したスキル・技術

### Webアプリケーション開発の基本スキル
- ✅ **ログイン機能の実装**: セッション管理による認証機能
- ✅ **フォーム処理**: POST/GETリクエストの処理、バリデーション
- ✅ **データベース機能**: JDBCを使用したCRUD操作
- ✅ **セキュリティ機能**: iframe sandboxによる安全なコード実行
- ✅ **ファイルアップロード**: マルチパートファイルの処理
- ✅ **テンプレート機能**: JSPによる動的コンテンツ生成

### アーキテクチャ・設計パターン
- ✅ **MVCパターン**: 責務の明確な分離
- ✅ **DAOパターン**: データアクセス層の抽象化
- ✅ **サービス層パターン**: ビジネスロジックの集約

## 🔧 開発で苦労した点・解決方法

### 1. 画像サムネイル機能の実装
**問題**: 画像が正しく表示されない（500エラー）
**解決方法**: 
- `servlet-context.xml`の誤った`mvc:resources`設定を削除
- `ImageController`による動的画像配信を実装
- Tomcat 7互換性のため`setContentLengthLong`を`setContentLength`に変更

### 2. iframe sandboxのセキュリティ警告
**問題**: `allow-scripts`と`allow-same-origin`の組み合わせによるセキュリティ警告
**解決方法**: 
- `allow-same-origin`を削除し、セキュリティを強化
- ゲームコードが外部リソースにアクセスしない前提で実装

### 3. 完全なHTMLドキュメントの実行
**問題**: ユーザーが投稿した完全なHTMLドキュメントがiframe内で正しく実行されない
**解決方法**: 
- JavaScriptによる動的なコード読み込みを実装
- hidden textareaを使用して安全にコードを渡す方法を採用

## 📝 今後の改善予定

- [ ] パスワードのハッシュ化（現在は平文保存）
- [ ] ページネーション機能の追加
- [ ] 検索機能の実装
- [ ] いいね機能の追加
- [ ] ユーザー間のフォロー機能

## 📄 ライセンス

このプロジェクトは教育目的で作成されました。

## 👤 開発者

このプロジェクトは、Webアプリケーション開発の基本スキルを学習するために作成されました。

---

**ポートフォリオとしての位置づけ**: 
このプロジェクトは、実務経験はありませんが、Webアプリケーション開発に必要な基本スキル（ログイン機能、フォーム処理、データベース操作、セキュリティ対策など）を有していることを示すために作成しました。現在のWebシステムで広く使用されている技術を実践的に学習し、独自の機能（ゲームコード実行機能）も追加することで、技術力と創造性をアピールできる作品となっています。
