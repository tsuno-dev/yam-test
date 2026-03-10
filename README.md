
# 第1正規化とその逆

TSV（タブ区切り）形式のデータを読み込み、「第1正規化」およびその「逆変換」を行うツールです。

## 1. 概要

本プログラムは、以下の2つの機能を提供します。

* **第1正規化**: 1つのセル内に区切り文字(":")で含まれる複数の値を、それぞれ独立した行へ展開します。

* **逆変換**: 共通のキーを持つ複数行のデータを、区切り文字(":")を用いて1行のデータに集約します。

## 2. 仕様の変更
上記の概要に記載した第1正規化では、（例: 18:00:00）のような文字列を含めると、それぞれ("18"、"00"、"00")と複数値にカウントされる懸念があるため、以下の仕様を追加

* **エスケープ文字として区切り文字の手前にバックスラッシュが付与（"\\:"）されている場合、複数値としてカウントしません。（例: 18\\:00\\:00）**

## 3. 動作環境

**Java**: Java 17

* **ビルドツール**: Maven

## 4. プロジェクト構造

```text
.
├── pom.xml
├── .gitignore
├── input1.tsv（第1正規化テスト用TSVファイル）
├── input2.tsv（逆変換テスト用TSVファイル）
├── input3.tsv（エスケープ文字付きテスト用TSVファイル）
└── src
    └── main
        └── java
            └── jp
                └── assignment
                    └──Main.java  (メイン・入出力制御)
                    └──FirstNormalize.java  (第1正規化または逆変換の機能を実装)

```

## 5. 環境情報

リポジトリをクローンした直後は main ブランチにいます。最新の開発ソースコードは main ブランチで管理してますが、開発中のブランチに切り替えたい場合は、以下のコマンドでブランチを切り替えてください。
```bash
# リポジトリのクローン
git clone [リポジトリのURL]
cd [クローンしたディレクトリ名]

# 開発中のxxxブランチに切り替えたい場合
git checkout ブランチ名（xxx）

```

## 6. ビルド方法

プロジェクトのルートディレクトリ（`pom.xml` がある場所）で以下のコマンドを実行してください。

```bash
mvn clean package

```

## 7. 実行方法

本プログラムは、実行後に標準入力から「実行モード」と「処理対象のデータ」を入力します。
動作確認用のサンプルとして、プロジェクトのルートディレクトリに input.tsv を配置しています。

### 起動コマンド

```bash
java -cp target/classes jp.assignment.main.TsvFirstNormalFormConverter

```

### 実行手順

1. プログラムを起動するとモード選択の入力待ちになります 。

2. **第1正規化**を行う場合は `1`、**逆変換**を行う場合は `2` と入力し、Enterを押してください。

3. 変換対象のTSVファイル名を指定してください (入力例: input1.tsv)

4. 出力ファイル名を指定してください (入力例: output1.tsv)

5. 「第1正規化」または「逆変換」されたTSVデータが出力されます。

## 8. 実行キャプチャ
 **第1正規化**
 
 * 通常
 
<img width="568" height="184" alt="スクリーンショット 2026-03-10 10 08 10" src="https://github.com/user-attachments/assets/da456841-859c-4af7-a7e5-2e04ff5a7e99" />

<img width="645" height="221" alt="スクリーンショット 2026-03-10 10 10 58" src="https://github.com/user-attachments/assets/d92da5f6-794b-4f6b-b483-315b0a94c352" />

* エスケープ付き、空行あり

<img width="569" height="190" alt="スクリーンショット 2026-03-10 10 09 03" src="https://github.com/user-attachments/assets/c065b14c-49dd-4f49-9f2e-bf27af50a61e" />

<img width="643" height="250" alt="スクリーンショット 2026-03-10 10 12 02" src="https://github.com/user-attachments/assets/7aeb2eef-bf88-4f71-9a57-7af4db9a1fae" />

**逆変換**

<img width="571" height="183" alt="スクリーンショット 2026-03-10 10 13 08" src="https://github.com/user-attachments/assets/29fa4672-ddf3-48bb-bd7c-72f18d9015a1" />

<img width="645" height="216" alt="スクリーンショット 2026-03-10 10 14 15" src="https://github.com/user-attachments/assets/8a65aca8-8681-4a5b-9e86-60a5cd99d731" />

