package jp.leopanda.htmlEditHelper.panelParts;

import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * 入力パネルの共通機能定義
 * 
 * @author LeoPanda
 *
 */
public abstract class PanelBase extends VerticalPanel {
  private final String DEFAULT_STYLE = "PanelBase"; // デフォルトのCSSスタイル名
  private final int fieldMapStep = 100; // フィールドマップindexの刻み幅
  /*
   * 入力フィールドコレクションマップ
   */
  protected FieldMap fieldMap = new FieldMap(fieldMapStep);

  /*
   * デフォルトのコンストラクタ
   */
  protected PanelBase() {
    this.addStyleName(DEFAULT_STYLE);
  }

  /*
   * 入力チェック 複合チェックが必要な場合は実装クラスで上書きしてください。
   */
  public boolean validateFields() {
    return fieldMap.validate();
  }

  /*
   * HTMLを生成する
   */
  public abstract String getGeneratedHtml();

  /*
   * 開いたwindowに実行スクリプトなどの追加HTMLがある場合は ここへ記述する
   */
  public abstract String getExstraHtml();

}