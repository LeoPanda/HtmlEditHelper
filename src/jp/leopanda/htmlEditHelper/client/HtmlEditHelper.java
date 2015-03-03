package jp.leopanda.htmlEditHelper.client;

import jp.leopanda.htmlEditHelper.panelParts.PanelBase;

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

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class HtmlEditHelper implements EntryPoint {
  GeneratedHTMLArea generatedHTMLArea = new GeneratedHTMLArea(""); // 生成されたHTMLを表示するエリア
  TabPanel tabPanel; // 機能選択タブパネル

  /*
   * タブパネル内入力パネル定義
   */
  private enum Panels {
    IndexIframeHelper(new IndexIframeHelper(), 
        "索引用iframe生成", PreviewType.Panel), 
    SyntaxHilighterHelper(new SyntaxHilighterHelper(), 
        "SyntaxHilighter", PreviewType.Window), 
    SlideShow(new SlideShow(), 
        "スライドショー", PreviewType.Window);
    private PanelBase panel;
    private String title;
    private PreviewType previewType;

    Panels(PanelBase panel, String title, PreviewType previewType) {
      this.panel = panel;
      this.title = title;
      this.previewType = previewType;
    }

    public PanelBase getPanel() {
      return this.panel;
    }

    public String getTitle() {
      return this.title;
    }

    public PreviewType getPreviewType() {
      return this.previewType;
    }
  }

  /*
   * プレビュー表示方法の種類
   */
  private enum PreviewType {
    Panel, Window;
  }

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
      leftOuter.add(new GenerateHTMLButton(tabPanel));
      leftOuter.add(generatedHTMLArea); // HTML生成エリア
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
    if (previewType == PreviewType.Panel) {
      new PopPreviewWindow(generatedHTMLArea.getText()).show();
    } else if (previewType == PreviewType.Window) {
      openPreviewWin(generatedHTMLArea.getText(), 
          Panels.values()[index].getPanel().getExstraHtml());
    }
  }

  /*
   * プレビューパネル
   */
  private class PopPreviewWindow extends PopupPanel {
    public PopPreviewWindow(String html) {
      super(true, true);
      this.setPopupPosition(620, 150);
      this.setWidth("680px");
      this.addStyleName("preview");
      this.setWidget(new HTML(html));
    }
  }

  /*
   * プレビューウィンドウ
   */
  private static native void openPreviewWin(String html, String exstraHtml) /*-{
   var newWin = $wnd.open('','newWin','height=800,width=640,left=620,top=150');
   var head = '<html dir="ltr" xmlns="http://www.w3.org/1999/xhtml" ';
   head +='xmlns:b="http://www.google.com/2005/gml/b" ';
   head +='xmlns:data="http://www.google.com/2005/gml/data" ';
   head +='xmlns:expr="http://www.google.com/2005/gml/expr" ';
   head +='xmlns:fb="http://www.facebook.com/2008/fbml">';
   head +='<head>' + '<meta http-equiv="content-type" content="text/html; charset=UTF-8">';
   head +='</head><body>';
   newWin.document.write(head);
   newWin.document.write(html);
   if(exstraHtml != null){
   newWin.document.write(exstraHtml);
   }
   newWin.document.write('</body></html>');
   }-*/;

  /*
   * 生HTMLを表示するパネル
   */
  private class GeneratedHTMLArea extends TextArea {
    public GeneratedHTMLArea(String text) {
      this.setText(text);
      this.addStyleName("generatedHTMLArea");
    }
  }

  /*
   * HTML生成ボタン
   */
  private class GenerateHTMLButton extends Button {
    public GenerateHTMLButton(final TabPanel tabPanel) {
      this.setText("HTML生成");
      this.addClickHandler(new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {
          // タブパネル内入力パネルの入力妥当性をチェックしHTMLを生成する。
          int index = tabPanel.getTabBar().getSelectedTab();
          PanelBase panel = Panels.values()[index].panel;
          if (panel.validateFields()) {
            generatedHTMLArea.setText(panel.getGeneratedHtml());
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
