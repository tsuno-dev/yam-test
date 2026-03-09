
# 第1正規化とその逆

TSV（タブ区切り）形式のデータを読み込み、「第1正規化」およびその「逆変換」を行うツールです。

## 1. 概要

本プログラムは、以下の2つの機能を提供します。

* **第1正規化**: 1つのセル内に区切り文字(":")で含まれる複数の値を、それぞれ独立した行へ展開します。

* **逆変換**: 共通のキーを持つ複数行のデータを、区切り文字(":")を用いて1行のデータに集約します。

## 2. 動作環境

**Java**: Java 17 (OpenJDK 11以上の環境で動作可能) 

* **ビルドツール**: Maven

## 3. プロジェクト構造

```text
.
├── pom.xml
├── .gitignore
├── input1.tsv（第1正規化テスト用TSVファイル）
├── input2.tsv（逆変換テスト用TSVファイル）
└── src
    └── main
        └── java
            └── jp
                └── assignment
                    └──Main.java  (メイン・入出力制御)
                    └──FirstNormalize.java  (第1正規化または逆変換の機能を実装)

```

## 4. 環境情報

リポジトリをクローンした直後は main ブランチにいます。最新の開発ソースコードは develop ブランチで管理しているため、以下のコマンドでブランチを切り替えてください。
```bash
# リポジトリのクローン
git clone [リポジトリのURL]
cd [クローンしたディレクトリ名]

# developブランチへの切り替え
git checkout develop

```

## 5. ビルド方法

プロジェクトのルートディレクトリ（`pom.xml` がある場所）で以下のコマンドを実行してください。

```bash
mvn clean package

```

## 6. 実行方法

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

## 7. 実行キャプチャ
 **第1正規化**

<img width="569" height="180" alt="スクリーンショット 2026-02-27 16 39 06" src="https://github.com/user-attachments/assets/564f88eb-c22c-4053-bd9e-014ad2a825d2" />
 
<img width="645" height="200" alt="スクリーンショット 2026-02-27 16 31 31" src="https://github.com/user-attachments/assets/d154cf0c-e675-4327-9825-657412137006" />

**逆変換**

<img width="571" height="184" alt="スクリーンショット 2026-02-27 16 40 15" src="https://github.com/user-attachments/assets/d7bb901c-ca0a-421d-a804-6dd80655c09f" />

<img width="645" height="219" alt="スクリーンショット 2026-02-27 16 34 19" src="https://github.com/user-attachments/assets/ded6e8e9-1008-401a-9854-7a5729a8e040" />
