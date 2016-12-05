package jp.leopanda.htmlEditHelper.parts;

import jp.leopanda.htmlEditHelper.enums.Error;

/**
 * エラーイベントリスナー
 * @author LeoPanda
 *
 */
public interface ErrorListener {
  void onError(Error error,String text);
}
