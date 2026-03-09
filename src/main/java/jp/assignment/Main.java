package jp.assignment;

import java.io.*;
import java.util.Scanner;

/**
 * タブで列が区切られたデータ(TSV)を読み込み、第1正規化または逆変換を行うメインクラス(エントリーポイント)。
 * ユーザーから指定された実行モード(1または2)と入力ファイルを基にデータを変換し、その結果を別のファイルへ出力。
 * * 1. 第1正規化:
 * 1つのセル内に区切り文字(":")で含まれる複数の値を、それぞれ独立した行へ展開します。
 * 2. 逆変換:
 * 共通のキーを持つ複数行のデータを、区切り文字(":")を用いて1行のデータに集約します。
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== TSVデータの第1正規化または逆変換を行う ===");
        System.out.println("1: 第1正規化 (複数値を複数行に展開する)");
        System.out.println("2: 逆変換 (複数行を1行に集約する)");
        System.out.print("実行するモード（1または2）を選択してください　> ");
        String mode = scanner.nextLine();

        if (!"1".equals(mode) && !"2".equals(mode)) {
            System.out.println("エラー: 1または2を選択してください。処理を中断します。");
            return; // mainメソッドを抜けて終了
        }

        System.out.print("変換対象のTSVファイル名を指定してください (入力例: input1.tsv) > ");
        String inputFileName = scanner.nextLine();
        System.out.print("出力ファイル名を指定してください (入力例: output1.tsv) > ");
        String outputFileName = scanner.nextLine();

        FirstNormalize service = new FirstNormalize("\t", ":");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFileName), "UTF-8"));
             PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFileName), "UTF-8")))) {

            if ("1".equals(mode)) {
                //変換対象のTSVファイルを第1正規化 (複数値を複数行に展開)し、別ファイルに出力
                service.executeNormalize(reader, writer);
                System.out.println("第1正規化 (複数値を複数行に展開)したファイルを出力しました");
            } else {
                //変換対象のTSVファイルを逆変換 (複数行を1行に集約)し、別ファイルに出力
                service.executeDenormalize(reader, writer);
                System.out.println("逆変換 (複数行を1行に集約)したファイルを出力しました");
            }

        } catch (IOException e) {
            System.err.println("エラー: " + e.getMessage());
        }
    }
}