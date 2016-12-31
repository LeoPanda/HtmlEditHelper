package jp.leopanda.htmlEditHelper.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

import jp.leopanda.htmlEditHelper.enums.Panels;
import jp.leopanda.htmlEditHelper.parts.FunctionPanelBase;
import jp.leopanda.htmlEditHelper.resources.TextResources;

/**
 * メインパネル
 * 
 * @author LeoPanda
 *
 */
public class MainPanel extends VerticalPanel {
  GeneratedHtmlArea generatedHtmlArea; // 生成されたHTMLを表示するエリア
  FunctionPanel functionPanel; // 機能選択タブパネル
  JavaScriptObject previewWindow;

  /**
   * コンストラクタ
   */
  public MainPanel() {
    this.generatedHtmlArea = new GeneratedHtmlArea("");
    this.functionPanel = new FunctionPanel();
    this.addStyleName("mainPanel");
    this.add(functionPanel);
    this.add(new GenerateHtmlButton());
    this.add(generatedHtmlArea);
    this.add(new RestPreviewButton());
  }

  /*
   * 選択されたタブパネルの機能を発動しHTMLを生成する
   */
  private void generateHtml() {
    FunctionPanelBase selectedPanel = functionPanel.getSelectedPanel().panel;
    if (selectedPanel.validateFields()) {
      generatedHtmlArea.setText(selectedPanel.getGeneratedHtml());
      showPreview();
    }
  }

  /*
   * プレビューウィンドウを開く
   */
  private void showPreview() {
    Panels selectedPanel = functionPanel.getSelectedPanel();
    switch (selectedPanel.getPreviewType()) {
      case PANEL:
        new PopPreviewPanel(generatedHtmlArea.getText()).show();
        break;
      case WINDOW:
        openPreviewWindow(generatedHtmlArea.getText());
        break;
      default:
        break;
    }
  }

  /*
   * プレビューウィンドウを開く
   */
  private void openPreviewWindow(String generatedSource) {
    if (previewWindow != null) {
      closeWindow(previewWindow);
    }
    previewWindow = openWindow(
        TextResources.INSTANCE.previewHead().getText() + generatedSource + "</body></html>");
  }

  /*
   * JavaScriptウィンドウを開く
   */
  private static native JavaScriptObject openWindow(String html) /*-{
        var newWin = $wnd.open('', 'newWin',
                'height=800,width=640,left=620,top=150');
        newWin.document.write(html);
        return newWin;
  }-*/;

  /*
   * JavaScriptウィンドゥを閉じる
   */
  private static native void closeWindow(JavaScriptObject openedWin) /*-{
        openedWin.close();
  }-*/;

  /*
   * 機能選択タブパネル
   */
  private class FunctionPanel extends TabPanel {
    FunctionPanel() {
      for (Panels panel : Panels.values()) {
        this.add(panel.getPanel(), panel.getTitle());
      }
      this.selectTab(0);
    }

    Panels getSelectedPanel() {
      return Panels.values()[this.getTabBar().getSelectedTab()];
    }
  }

  /*
   * プレビューパネル
   */
  private class PopPreviewPanel extends PopupPanel {
    public PopPreviewPanel(String html) {
      super(true, true);
      this.setPopupPosition(620, 150);
      this.setWidth("680px");
      this.setWidget(new HTML(html));
    }
  }

  /*
   * 生HTMLを表示するパネル
   */
  private class GeneratedHtmlArea extends TextArea {
    public GeneratedHtmlArea(String text) {
      this.setText(text);
    }
  }

  /*
   * HTML生成ボタン
   */
  private class GenerateHtmlButton extends Button {
    public GenerateHtmlButton() {
      this.setText("HTML生成");
      this.addClickHandler(new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {
          generateHtml();
        }
      });
    }
  }

  /*
   * HTML表示エリアの情報をプレビューエリアへ転送するボタン
   */
  private class RestPreviewButton extends Button {
    public RestPreviewButton() {
      this.setText("プレビュー更新");
      this.addClickHandler(new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {
          showPreview();
        }
      });
    }
  }

}
