package jp.assignment.service;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.IOException;

/**
 * 第1正規化または逆変換の機能を実装しているクラス。
 */
public class FirstNormalizationService {

    private final String fieldDelimiter;
    private final String collectionDelimiter;

    /**
     * 区切り文字を指定してサービスを初期化。
     * @param fieldDelimiter   フィールド区切り文字
     * @param collectionDelimiter 複数要素の区切り文字
     */
    public  FirstNormalizationService(String fieldDelimiter, String collectionDelimiter) {
        this.fieldDelimiter = fieldDelimiter;
        this.collectionDelimiter = collectionDelimiter;
    }

    /**
     * 第1正規化 (複数値を複数行に展開する) を実行。
     * 入力データを区切り文字ごとに分割し、複数要素を展開して正規化された形式で出力。
     * @param reader 入力データストリーム
     * @param writer 出力データストリーム
     * @throws IOException 入出力エラー時に発生
     */
    public void executeNormalize(BufferedReader reader, PrintWriter writer) throws IOException {
        String line;
        // データの読み込み
        while ((line = reader.readLine()) != null) {
            if (line.trim().isEmpty()) continue;    // 空行はスキップ

            // 1. フィールド区切り文字ごとに分割
            // 例: "banana:cherry\tfruit" -> ["banana:cherry", "fruit"]
            String[] columns = line.split(fieldDelimiter, -1);  // 末尾の空セルを考慮し、limit引数に -1 を指定して分割

            // 2. さらに複数要素の区切り文字ごとに分割し、各要素をリストに格納
            // 例: ["banana:cherry", "fruit"] -> [ ["banana", "cherry"], ["fruit"] ]
            List<String[]> multiColumnValueList = new ArrayList<>();
            for (String column : columns) {
                multiColumnValueList.add(column.split(collectionDelimiter, -1));
            }

            // 3. 格納したリストを元に、正規化された行を出力
            outputNormalizedRow(multiColumnValueList, 0, new ArrayList<>(), writer);
        }
    }

    /**
     * 複数カラムに含まれる値の全組み合わせを再帰的に生成し、区切り文字で連結して出力。
     * @param multiColumnValueList    各カラムの値を格納したリスト
     * @param columnIndex   処理対象カラムのインデックス
     * @param outputValueList 出力用のリスト
     * @param writer 出力データストリーム
     */
    private void outputNormalizedRow(List<String[]> multiColumnValueList, int columnIndex, List<String> outputValueList, PrintWriter writer) {
        // カラムのインデックスがリストサイズと同様の場合、抽出したリストを複数要素の区切り文字で連結（第1正規化）し、出力
        if (columnIndex == multiColumnValueList.size()) {
            writer.println(String.join(fieldDelimiter, outputValueList));
            return;
        }

        // カラムリストに含まれる各要素ごとに、outputNormalizedRowを再帰的に呼び出す
        for (String value : multiColumnValueList.get(columnIndex)) {
            outputValueList.add(value); //出力するための要素を追加（カラムの抽出）
            outputNormalizedRow(multiColumnValueList, columnIndex + 1, outputValueList, writer);    //columnIndexを1増やし、再帰呼び出し
            outputValueList.remove(outputValueList.size() - 1); //出力し終えたので、最後に抽出した要素を削除して状態を戻す
        }
    }

    /**
     * 逆変換（同じキーを持つ値を集約）を実行。
     * 入力データを読み込み、同じキーを持つ値を集約して出力する。
     * @param reader 入力データストリーム
     * @param writer 出力データストリーム
     * @throws IOException 入出力エラー時に発生
     */
    public void executeDenormalize(BufferedReader reader, PrintWriter writer) throws IOException {
        // キーの昇順を維持するため TreeMap を使用
        Map<String, List<String>> groups = new TreeMap<>();
        String line;

        // データの読み込み
        while ((line = reader.readLine()) != null) {
            if (line.trim().isEmpty()) continue;
            String[] columns = line.split(fieldDelimiter, -1);  // 末尾の空セルを考慮し、limit引数に -1 を指定して分割

            // キー(1列目)と値(2列目)が存在する場合のみグループへ追加
            if (columns.length >= 2) {
                // キーごとに値をリストへまとめる処理
                groups.computeIfAbsent(columns[0], k -> new ArrayList<>()).add(columns[1]);
            }
        }

        // 集約した結果をフォーマットして出力
        for (Map.Entry<String, List<String>> entry : groups.entrySet()) {
            // 値のリストを複数要素の区切り文字で連結
            String joinedValues = String.join(collectionDelimiter, entry.getValue());

            // キー + フィールド区切り文字 + 連結された値 を出力
            writer.println(entry.getKey() + fieldDelimiter + joinedValues);
        }
    }
}
