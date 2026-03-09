package jp.assignment;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.Optional;
import java.util.Arrays;

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

        // 入力値をMode列挙型に変換。不正な入力の場合はOptionalが空になる
        Optional<Mode> mode = Mode.fromString(scanner.nextLine());
        if (mode.isEmpty()) {
            System.out.println("エラー: 1または2を選択してください。");
            return;
        }

        System.out.print("変換対象のTSVファイル名を指定してください (入力例: input1.tsv) > ");
        String inputFileName = scanner.nextLine();
        System.out.print("出力ファイル名を指定してください (入力例: output1.tsv) > ");
        String outputFileName = scanner.nextLine();

        FirstNormalize service = new FirstNormalize("\t", ":");

        //【修正】PrintWriterからBufferedWriterに修正
        try (BufferedReader reader = Files.newBufferedReader(Path.of(inputFileName), StandardCharsets.UTF_8);
             BufferedWriter writer = Files.newBufferedWriter(Path.of(outputFileName), StandardCharsets.UTF_8)) {

            //【修正】if文の条件分岐からswitch文を使用した分岐に修正
            switch (mode.get()) {
                case NORMALIZE -> {
                    service.executeNormalize(reader, writer);
                    System.out.println("第1正規化 (複数値を複数行に展開)したファイルを出力しました");
                }
                case DENORMALIZE -> {
                    service.executeDenormalize(reader, writer);
                    System.out.println("逆変換 (複数行を1行に集約)したファイルを出力しました");
                }
            }

        } catch (IOException e) {
            System.err.println("エラー: " + e.getMessage());
        }
    }

    /**
     * 実行モードを管理する列挙型。
     */
    public enum Mode {
        NORMALIZE("1"),
        DENORMALIZE("2");

        private final String value;

        Mode(String value) {
            this.value = value;
        }

        // 入力された文字列から対応するModeを返すメソッド(一致しない場合は、空を返す)
        public static Optional<Mode> fromString(String input) {
            return Arrays.stream(Mode.values())
                    .filter(m -> m.value.equals(input))
                    .findFirst();
        }
    }
}