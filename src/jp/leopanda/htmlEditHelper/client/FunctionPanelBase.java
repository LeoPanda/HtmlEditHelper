package jp.leopanda.htmlEditHelper.client;

import jp.leopanda.panelFrame.panelParts.PanelBase;

/**
 * iFrame内　機能パネルの共通機能定義
 * 
 * @author LeoPanda
 *
 */
public abstract class FunctionPanelBase extends PanelBase {
 
  /*
   * HTMLを生成する
   */
  public abstract String getGeneratedHtml();

  /*
   * 開いたwindowに実行スクリプトなどの追加HTMLがある場合は ここへ記述する
   */
  public abstract String getExstraHtml();

}