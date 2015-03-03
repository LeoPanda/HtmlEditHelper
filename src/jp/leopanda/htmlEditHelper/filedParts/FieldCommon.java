package jp.leopanda.htmlEditHelper.filedParts;

import jp.leopanda.htmlEditHelper.enums.Error;

/**
 * 入力フィールド共通インターフェース
 * 
 * @author LeoPanda
 *
 */
public interface FieldCommon {
  /*
   * 入力値のgetter
   */
  String getText();

  /*
   * 初期値のsetter
   */
  void setText(String text);

  /*
   * エラーをセットする
   */
  void setErr(Error err, String errMsg);

  /*
   * 正当性をチェックする
   */
  boolean validate();

  /*
   * エラー値を取得する
   */
  Error getErr();

  /*
   * エラーメッセージを取得する
   */
  String getErrMsg();

  /*
   * エラーを通知する
   */
  void popError();

  /*
   * フィールドのクローンを作成する
   */
  Object clone();

}
