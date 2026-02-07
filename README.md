# Hatoberry API

## 事前準備（Install）

- JDK 17 が必要です
- Gradle Wrapper を使用するため、追加インストールは不要です

## 実行方法（ローカル）
```
./gradlew bootRunLocal
```

## 設計概念

- **レイヤードアーキテクチャ**（Controller–Service–Domain/Repository）を基本にし、責務の分離と変更影響の限定を重視しています。
- **ドメインイベント**を導入し、業務上の重要な事実をイベントとして明示化しています。イベントの発行と購読を分離することで拡張性とテスト容易性を高めます。
- **DTO分離**は「APIの入出力専用の型（DTO）」と「業務ルールを持つドメインモデル」を分けることです。たとえばAPIの項目名や形式を変更しても、ドメインモデルの変更を最小限にできます。DTO↔Domainの変換はController（または専用Mapper）で行い、ServiceはDTOに依存しない方針です。

## パッケージ構成（主なもの）

- `chat.controller`: HTTP/WSの入口。リクエスト受け取りとレスポンス返却
- `chat.service`: ユースケース（アプリケーションサービス）
- `chat.domain`: ドメインモデル
- `chat.domain.message`: メッセージに関するモデル／リポジトリ
- `chat.dto`: API入出力DTO
- `chat.event`: イベント定義
- `chat.event.listener`: イベント購読（副作用・通知など）
- `chat.websocket`: WebSocketの構成・ハンドラ
- `common.config`: 共通設定

## テストと拡張の考え方

- コントローラは薄く保ち、ユースケースは `service` に集約
- イベントは `event` に集約し、購読処理は `event.listener` に分離
- 依存はなるべくインターフェース越しにし、ユニットテストで差し替え可能に
