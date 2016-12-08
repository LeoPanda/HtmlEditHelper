package jp.leopanda.htmlEditHelper.parts;

import jp.leopanda.panelFrame.panelParts.PanelBase;

/**
 * iFrame内 機能パネルの共通機能定義
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
   * テスト実行windowに生成ソースを表示した後、実行スクリプトなどの追加で表示したいHTMLがある場合はこのメソッドをオーバーライドして記述してください
   */
  public String getExstraHtml() {
    return null;
  }

}
