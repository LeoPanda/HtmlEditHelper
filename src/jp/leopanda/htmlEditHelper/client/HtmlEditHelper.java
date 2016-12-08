package jp.leopanda.htmlEditHelper.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

import jp.leopanda.htmlEditHelper.enums.Panels;
import jp.leopanda.htmlEditHelper.enums.PreviewType;
import jp.leopanda.htmlEditHelper.parts.FunctionPanelBase;
import jp.leopanda.htmlEditHelper.resources.TextResources;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class HtmlEditHelper implements EntryPoint {
  GeneratedHtmlArea generatedHtmlArea = new GeneratedHtmlArea(""); // 生成されたHTMLを表示するエリア
  TabPanel tabPanel; // 機能選択タブパネル

  @Override
  /*
   * メインモジュール
   */
  public void onModuleLoad() {
    RootPanel.get().add(new OuterPanel());
  }

  /*
   * 最外枠パネル
   */
  private class OuterPanel extends HorizontalPanel {
    public OuterPanel() {
      this.addStyleName("outerPanel");
      // 入力エリア
      VerticalPanel leftOuter = new VerticalPanel();
      tabPanel = getTabPanel(); // 入力機能切替え用タブパネル
      leftOuter.add(tabPanel);
      leftOuter.add(new GenerateHtmlButton(tabPanel));
      leftOuter.add(generatedHtmlArea); // HTML生成エリア
      leftOuter.add(new RestPreviewButton()); // プレビューエリア更新ボタン
      this.add(leftOuter);
    }

    // 入力機能切り替用タブパネルの作成
    private TabPanel getTabPanel() {
      TabPanel tabPanel = new TabPanel();
      for (Panels panel : Panels.values()) {
        tabPanel.add(panel.getPanel(), panel.getTitle());
      }
      tabPanel.selectTab(0);
      return tabPanel;
    }
  }

  /*
   * プレビューを表示する
   */
  private void showPreview() {
    int index = tabPanel.getTabBar().getSelectedTab();
    PreviewType previewType = Panels.values()[index].getPreviewType();
    if (previewType == PreviewType.PANEL) {
      new PopPreviewPanel(generatedHtmlArea.getText()).show();
    } else if (previewType == PreviewType.WINDOW) {
      openPreviewWindow(generatedHtmlArea.getText(),
          Panels.values()[index].getPanel().getExstraHtml());
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
      this.addStyleName("preview");
      this.setWidget(new HTML(html));
    }
  }

  /*
   * プレビューウィンドウを開く
   */
  private void openPreviewWindow(String generatedSource, String extraHtml) {
    openWindow(TextResources.INSTANCE.previewHead().getText() + generatedSource + extraHtml
        + "</body></html>");
  }

  /*
   * サブウィンドウを開く
   */
  private static native void openWindow(String html) /*-{
        var newWin = $wnd.open('', 'newWin',
                'height=800,width=640,left=620,top=150');
        newWin.document.write(html);
  }-*/;

  /*
   * 生HTMLを表示するパネル
   */
  private class GeneratedHtmlArea extends TextArea {
    public GeneratedHtmlArea(String text) {
      this.setText(text);
      this.addStyleName("generatedHTMLArea");
    }
  }

  /*
   * HTML生成ボタン
   */
  private class GenerateHtmlButton extends Button {
    public GenerateHtmlButton(final TabPanel tabPanel) {
      this.setText("HTML生成");
      this.addClickHandler(new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {
          // タブパネル内入力パネルの入力妥当性をチェックしHTMLを生成する。
          int index = tabPanel.getTabBar().getSelectedTab();
          FunctionPanelBase panel = Panels.values()[index].panel;
          if (panel.validateFields()) {
            generatedHtmlArea.setText(panel.getGeneratedHtml());
            showPreview();
          }
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
