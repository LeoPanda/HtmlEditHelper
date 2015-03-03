package jp.leopanda.htmlEditHelper.panelParts;

import java.util.ArrayList;
import java.util.List;

import jp.leopanda.htmlEditHelper.filedParts.FieldGroup;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * 入力フィールドを可変数化するラッパー
 * 
 * @author LeoPanda
 */
public class IncrementalWrapper extends VerticalPanel {
  private List<ClonePanel> panelList = new ArrayList<ClonePanel>(); // 可変数パネルのリスト
  private FieldMap fieldMap; // 呼び出し元のフィールドマップコレクション
  private int fieldMapInitialIndex = 0; // フィールドグループの最初の挿入位置

  /**
   * コンストラクタ
   *     
   */
  public IncrementalWrapper(FieldGroup fieldGroup, FieldMap fieldMap) {
    this.fieldMap = fieldMap;
    this.fieldMapInitialIndex = fieldMap.add(fieldGroup);
    this.add(new ClonePanel(fieldGroup));
  }

  /*
   * フィールドグループを追加する
   */
  private void addField() {
    int latestPanel = panelList.size() - 1;
    this.add(new ClonePanel(panelList.get(latestPanel).getFieldGroup().clone()));
  }

  /*
   * フィールドグループを削除する
   */
  private void removeField() {
    int latestPanel = panelList.size() - 1;
    if (latestPanel == 1) {
      panelList.get(latestPanel - 1).getPlusButton().setVisible(true);
      panelList.get(latestPanel - 1).getMinusButton().setVisible(false);
    }
    if (latestPanel > 1) {
      panelList.get(latestPanel - 1).getPlusButton().setVisible(true);
      panelList.get(latestPanel - 1).getMinusButton().setVisible(true);
    }
    int retreaveIndex = panelList.get(latestPanel).getFieldMapIndex();
    fieldMap.remove(retreaveIndex);
    panelList.remove(latestPanel);
    this.remove(latestPanel);
  }

  /*
   * フィールド追加ボタン
   */
  private class PlusButton extends Button {
    public PlusButton() {
      this.setText("+");
      this.addClickHandler(new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {
          addField();
        }
      });
    }
  }

  /*
   * フィールド削除ボタン
   */
  private class MinusButton extends Button {
    public MinusButton() {
      this.setText("-");
      this.addClickHandler(new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {
          removeField();
        }
      });
    }
  }

  /**
   * 値のgetter
   */
  public List<String> getTextList() {
    List<String> valueList = new ArrayList<String>();
    for (ClonePanel clonePanel : panelList) {
      valueList.add(fieldMap.getField(clonePanel.getFieldMapIndex()).getText());
    }
    return valueList;
  }

  /*
   * 可変数化パネルの定義クラス
   */
  private class ClonePanel extends HorizontalPanel {
    private FieldGroup fieldGroup; // フィールドグループ
    private int fieldMapIndex; // フィールドマップ上クローングループの挿入位置
    private PlusButton plusButton = new PlusButton(); // 追加ボタン
    private MinusButton minusButton = new MinusButton();// 削除ボタン

    // コンストラクタ
    public ClonePanel(FieldGroup fieldGroup) {
      this.fieldGroup = fieldGroup;
      this.add(fieldGroup.getGroupPanel());
      this.add(plusButton);
      this.add(minusButton);

      int latestPanel = panelList.size(); // パネルリストのサイズ
      fieldMapIndex = insertToFieldMap(fieldGroup, latestPanel);
      setButtonsVisible(latestPanel);
      panelList.add(this);
    }

    /*
     * フィールドマップへクローンフィールドグループを追加する
     * 
     * @param fieldGroup 追加するフィールドグループ
     * 
     * @param latestPanel パネルグループのサイズ
     * 
     * @param 戻り値 フィールドマップへの挿入位置
     */
    private int insertToFieldMap(FieldGroup fieldGroup, int latestPanel) {
      int fieldMapIndex = 0;
      if (latestPanel == 0) {
        return fieldMapInitialIndex;
      } else {
        fieldMapIndex = fieldMapInitialIndex + panelList.size();
        fieldMap.insert(fieldMapIndex, fieldGroup);
      }
      return fieldMapIndex;
    }

    /*
     * プラスボタン/マイナスボタンの表示設定
     */
    private void setButtonsVisible(int latestPanel) {
      if (latestPanel == 0) {
        // パネルリストの１件のみならマイナスボタンを非表示
        minusButton.setVisible(false);
      } else {
        // 前行のパネルのボタンを非表示
        panelList.get(latestPanel - 1).getPlusButton().setVisible(false);
        panelList.get(latestPanel - 1).getMinusButton().setVisible(false);
      }
    }

    // fieldMapIndex getter
    public int getFieldMapIndex() {
      return this.fieldMapIndex;
    }

    // field group getter
    public FieldGroup getFieldGroup() {
      return fieldGroup;
    }

    // plus Button getter
    public PlusButton getPlusButton() {
      return plusButton;
    }

    // minus button getter
    public MinusButton getMinusButton() {
      return minusButton;
    }
  }
}
