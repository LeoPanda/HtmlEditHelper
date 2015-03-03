package jp.leopanda.htmlEditHelper.client;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.XMLParser;

import jp.leopanda.htmlEditHelper.enums.Error;
import jp.leopanda.htmlEditHelper.filedParts.ListBoxField;
import jp.leopanda.htmlEditHelper.filedParts.TextBoxField;
import jp.leopanda.htmlEditHelper.panelParts.PanelBase;
import jp.leopanda.htmlEditHelper.validate.NumericValidator;
import jp.leopanda.htmlEditHelper.validate.RequiredValidator;
import jp.leopanda.htmlEditHelper.validate.ValidateBase;

/**
 * Blogger用索引をiframeに埋め込むHTMLを生成する
 * 
 * @author LeoPanda
 *
 */
class IndexIframeHelper extends PanelBase {
  private final String APP_NAME = "http://2.optimal-spark-439.appspot.com/"; // iframeに入れるアプリ名
  private final int FRAME_MARGINE = 11; // iframeの上部マージン
  private final int MULTIPLI = 127; // iframeの高さ１行あたりの乗算定数
  private final int MIN_LIST_VAL = 1; // 数値リストボックスの最小値
  private final int MAX_WIDTH = 6; // １行の最大コマ数
  private final int MAX_IFRAME_HEIGHT = 5;// iframeの最大行数
  // バリデータ
  private RequiredValidator isRequired = new RequiredValidator();
  private NumericValidator isNumeric = new NumericValidator();
  // フィールド
  private TextBoxField title = new TextBoxField("title", "タイトル名:", null);
  private TextBoxField caterogy = new TextBoxField("category", "カテゴリ:", null);
  private TextBoxField queryString = new TextBoxField("queryString", "検索文字列:", null);
  private ListBoxField numOfWidth = new ListBoxField("numOfWidth", "１行の最大コマ数:", null,
      setNumValueList(MAX_WIDTH));
  private TextBoxField iFrameWidth = new TextBoxField("iFrameWidth", "iframe横幅:",
      new ValidateBase[] { isRequired, isNumeric });
  private ListBoxField iFrameHight = new ListBoxField("iFrameHight", "iframe高さ:", null,
      setNumValueList(MAX_IFRAME_HEIGHT));

  /*
   * コンストラクタ
   */
  public IndexIframeHelper() {
    super();
    setFieldMap();
    setPanel();
    // 初期値セット
    numOfWidth.setText("5");
    iFrameWidth.setText("800");
    iFrameHight.setText("2");
  }

  /*
   * フィールドマップの作成
   */
  private void setFieldMap() {
    fieldMap.add(title);
    fieldMap.add(caterogy);
    fieldMap.add(queryString);
    fieldMap.add(numOfWidth);
    fieldMap.add(iFrameHight);
    fieldMap.add(iFrameWidth);
  }

  /*
   * 入力パネルの構成
   */
  private void setPanel() {
    this.add(title);
    this.add(caterogy);
    this.add(queryString);
    this.add(numOfWidth);
    this.add(iFrameHight);
    this.add(iFrameWidth);
  }

  /*
   * 数値リストボックス用値リストの作成
   */
  private Map<String, String> setNumValueList(int max) {
    Map<String, String> valueList = new HashMap<String, String>();
    for (int i = MIN_LIST_VAL; i <= max; i++) {
      valueList.put(String.valueOf(i), String.valueOf(i));
    }
    return valueList;
  }

  /*
   * 入力チェック
   */
  public boolean validateFields() {
    // 個別項目のチェック
    if (!super.validateFields()) {
      return false;
    }
    // 複合チェック
    if (!isRequired.validate(queryString.getText()) && !isRequired.validate(caterogy.getText())) {
      caterogy.setErr(Error.LEAST_ONE, Error.LEAST_ONE.getMsg());
      queryString.setErr(Error.LEAST_ONE, Error.LEAST_ONE.getMsg());
      caterogy.popError();
      return false;
    }
    return true;
  }

  /*
   * HTMLを生成する
   */
  public String getGeneratedHtml() {
    String html = "";
    if (title.getText().length() > 0) {
      html += getTitleDom();
    }
    html += getIframeDom();
    return html;
  }

  /*
   * タイトルのHTMLを生成する
   */
  private String getTitleDom() {
    Map<String, String> attributes = new HashMap<String, String>();
    attributes.put("style", "font-size: 80%; font-weight: 600;");
    return getDomString("div", attributes);
  }

  /*
   * iframeのHTMLを生成する
   */
  private String getIframeDom() {
    Map<String, String> attributes = new HashMap<String, String>();
    attributes.put("src", getAppURL());
    attributes.put("style", getIframeStyle());
    return getDomString("iframe", attributes);
  }

  /*
   * iframeに埋め込むアプリのURLをパラメータ付きで生成する
   */
  private String getAppURL() {
    boolean hasCategory = caterogy.getText().length() > 0;
    boolean hasQuery = queryString.getText().length() > 0;

    String url = APP_NAME + "?";
    if (hasCategory) {
      url += "category=" + caterogy.getText();
    }
    url += (hasCategory && hasQuery) ? "&" : "";
    if (hasQuery) {
      url += "q=" + queryString.getText();
    }
    url += "&maxWidth=" + numOfWidth.getText();
    return url;
  }

  /*
   * iframe用のスタイルを生成する
   */
  private String getIframeStyle() {
    return "border: none;" + "width: " + iFrameWidth.getText() + "px;" + "height:"
        + getIframeHeight(iFrameHight.getText()) + "px;";
  }

  /*
   * iframeの高さを計算する
   */
  private String getIframeHeight(String height) {
    return String.valueOf(FRAME_MARGINE + Integer.parseInt(height) * MULTIPLI);
  }

  /*
   * HTML Dom elementを生成する。
   */
  private String getDomString(String elementName, Map<String, String> attributes) {
    Element element = XMLParser.createDocument().createElement(elementName);
    for (Map.Entry<String, String> attribute : attributes.entrySet()) {
      element.setAttribute(attribute.getKey(), attribute.getValue());
    }
    return element.toString().replaceAll("&amp;", "&");
  }

  @Override
  public String getExstraHtml() {
    // TODO 自動生成されたメソッド・スタブ
    return null;
  }

}