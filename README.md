# Hatoberry API

シンプルなメッセージ投稿・取得 API と、投稿イベントの WebSocket 配信を備えた Spring Boot アプリケーションです。

## 事前準備（Install）

- JDK 17 が必要です
- Gradle Wrapper を使用するため、追加インストールは不要です

## 実行方法（ローカル）

```
./gradlew bootRunLocal
```

`bootRunLocal` は `application-local.properties` を読み込みます。

## 設計概念と構造

このプロジェクトは Spring Boot をベースに、責務分離を意識した構成で実装されています。主に以下の設計概念が使われています。

### 1. レイヤードアーキテクチャ（Controller / Service / Repository）

- **Controller**: HTTP リクエストを受け、DTO に変換して Service に委譲
- **Service**: 業務ロジックの中核。ドメインとポートを利用
- **Repository**: 永続化の詳細を隠蔽（Spring Data JPA）

### 2. ドメインモデル中心の設計

- ドメインは「業務ルールの中心」で、データとルールを一体として表現
- 例: メッセージが空なら作成できない、などのルールをエンティティ側で保証
- API の入出力は DTO に切り出し、ドメインと分離して変更影響を小さくする

### 3. ポート／アダプタ

- **ポート**: Service から外部連携を呼ぶためのインタフェース（抽象）
- **アダプタ**: ポートを実装し、具体的な外部手段（例: WebSocket）につなぐ実装
- 本プロジェクトでは外部通知（WebSocket）にこの考え方を適用

## パッケージ構成（主なもの）

- `message.controller`: HTTP の入口
- `message.service`: ユースケース（アプリケーションサービス）
- `message.domain`: ドメインモデル
- `message.repository`: 永続化
- `message.dto`: API 入出力 DTO
- `message.port`: 外部連携の抽象
- `message.adapter.websocket`: WebSocket 配信アダプタ（Port 実装）
- `common.websocket`: 共通 WebSocket 基盤（接続・ハンドラ）
- `common.config`: 共通設定


## API 仕様（概要）

### メッセージ投稿

- Method: `POST`
- Path: `/messages`
- Body:
	```json
	{ "content": "hello" }
	```
- Response: `200 OK`

### メッセージ一覧取得

- Method: `GET`
- Path: `/messages`
- Response:
	```json
	[
		{ "id": 1, "content": "hello", "postedAt": "2026-02-07T12:34:56Z" }
	]
	```

## WebSocket 仕様（概要）

- Path: `/ws`
- サーバからの通知内容は `MessageResponse` と同形の JSON です。

接続例（JavaScript）:

```javascript
const ws = new WebSocket("ws://localhost:9090/ws");
ws.onmessage = (e) => console.log(JSON.parse(e.data));
```
