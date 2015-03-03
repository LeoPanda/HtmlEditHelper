package jp.leopanda.htmlEditHelper.filedParts;

import java.util.EventListener;

/**
 * フィールド内イベントのリスナー
 * 
 * @author LeoPanda
 *
 */
public interface EventAction extends EventListener {
  public void onValueChange();

}
