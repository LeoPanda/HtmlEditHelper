package jp.leopanda.htmlEditHelper.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;

import jp.leopanda.htmlEditHelper.parts.FunctionPanelBase;
import jp.leopanda.htmlEditHelper.resources.TextResources;
import jp.leopanda.panelFrame.filedParts.TextAreaField;
import jp.leopanda.panelFrame.validate.RequiredValidator;
import jp.leopanda.panelFrame.validate.ValidateBase;

/**
 * ブログ画像をスライドショーにまとめる
 * 
 * @author LeoPanda
 *
 */
public class SlideShow extends FunctionPanelBase {
  // 固定文字列
  private static final String HOST_URL = GWT.getHostPageBaseURL();
  // バリデータ
  private RequiredValidator isRequired = new RequiredValidator();
  // フィールド
  private TextAreaField imageHtml =
      new TextAreaField("画像ソースHTML:", new ValidateBase[] {isRequired});

  /**
   * コンストラクタ
   */
  public SlideShow() {
    super();
    setFieldMap();
    setPanel();
  }

  /*
   * フィールドマップの作成
   */
  private void setFieldMap() {
    fieldMap.add(imageHtml);
  }

  /*
   * 入力パネルの構成
   */
  private void setPanel() {
    this.add(imageHtml);
  }

  /*
   * HTMLの生成
   */
  @Override
  public String getGeneratedHtml() {
    return getImgTag(imageHtml.getText()) + getButtonHtml() + getJavaScriptUrl() + getOnLoadHtml();
  }

  /*
   * ソースのHTMLから画像表示タグを抽出しDIVに整形する
   */
  private String getImgTag(String source) {
    String html = "<div id=\"slideShowImages\">" + "\n";
    String regex = "(<a href[^>]*><img[^>]*></a>)";
    RegExp regExp = RegExp.compile(regex, "gm");
    MatchResult matcher = regExp.exec(source);
    while (matcher != null) {
      html += matcher.getGroup(0);
      matcher = regExp.exec(source);
    }
    return html + "</div>" + "\n";
  }

  /*
   * 停止/再開ボタンHTMLを作成する
   */
  private String getButtonHtml() {
    return TextResources.INSTANCE.slideShowButton().getText();

  }

  /*
   * スクリプトタイプ指定部を生成する
   */
  private String getJavaScriptUrl() {
    String html = "<script type=\"text/javascript\" src=\"" + HOST_URL + "js/";
    html += "slideShow.js" + "\" charset=\"UTF-8\"></script>" + "\n";
    return html;
  }

  /*
   * ロード後実行部を作成する
   */
  private String getOnLoadHtml() {
    return TextResources.INSTANCE.slideShowScript().getText();
  }

}
