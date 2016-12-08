package jp.leopanda.htmlEditHelper.enums;

import jp.leopanda.htmlEditHelper.client.IndexIframeHelper;
import jp.leopanda.htmlEditHelper.client.PhotoLayout;
import jp.leopanda.htmlEditHelper.client.SlideShow;
import jp.leopanda.htmlEditHelper.client.SyntaxHilighterHelper;
import jp.leopanda.htmlEditHelper.parts.FunctionPanelBase;

/**
 * タブパネル内入力パネル定義
 */
public enum Panels {
  IndexIframeHelper(new IndexIframeHelper(), "索引用iframe生成", PreviewType.PANEL),
  SyntaxHilighterHelper(new SyntaxHilighterHelper(), "SyntaxHilighter", PreviewType.WINDOW),
  SlideShow(new SlideShow(), "スライドショー", PreviewType.WINDOW),
  PhotoLayout(new PhotoLayout(), "写真のレイアウト配置", PreviewType.PANEL);
  public FunctionPanelBase panel;
  private String title;
  private PreviewType previewType;

  Panels(FunctionPanelBase panel, String title, PreviewType previewType) {
    this.panel = panel;
    this.title = title;
    this.previewType = previewType;
  }

  public FunctionPanelBase getPanel() {
    return this.panel;
  }

  public String getTitle() {
    return this.title;
  }

  public PreviewType getPreviewType() {
    return this.previewType;
  }
}
