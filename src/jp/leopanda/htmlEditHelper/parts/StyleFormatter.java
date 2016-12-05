package jp.leopanda.htmlEditHelper.parts;

/**
 * スタイルアトリビュートを生成する
 * @author LeoPanda
 *
 */
public class StyleFormatter {
  
  NumFormatter formatter = new NumFormatter(1); //数値フォーマッター　小数点以下１桁にセット
  private static final String COMMA = ",";
  private static final String DELIMITER = ";";
  private static final String STYLE_PREFIX = "clear: both; text-defaultIndent: left;";
  private static final String SIZE_UNIT = "px";// divのサイズ単位

  /**
   * DIVタグを読み込みスタイルアトリビュートを生成する
   * @param divTag DivTag
   * @return スタイルアトリビュート
   */
  public String getStyleAttributes(DivTag divTag) {
    String widthElement = getStyleElement(AttributeName.WIDTH.text, divTag.getWidth());
    String heightElement = getStyleElement(AttributeName.HEIGHT.text, divTag.getHeight());
    String marginLeftElement =
        getStyleElement(AttributeName.MARGINLEFT.text, divTag.getMarginLeft());
    String marginBottomElement =
        getStyleElement(AttributeName.MARGINBOTTOM.text, divTag.getMarginBottom());
    String transformElement = getTransformElement(divTag);
    return STYLE_PREFIX + widthElement + heightElement + transformElement + marginLeftElement
        + marginBottomElement;
  }

  // style属性の各要素を設定する
  private String getStyleElement(String elementName, float value) {
    return value == 0 ? ""
        : elementName + ":" + formatter.getString(value) + SIZE_UNIT + DELIMITER;
  }

  // styleのトランスフォームエレメントを設定する
  private String getTransformElement(DivTag divTag) {
    if (divTag.getTranslateX() + divTag.getTranslateY() == 0) {
      return "";
    }
    String transform =
        "transform:translate(" + formatter.getString(divTag.getTranslateX()) + SIZE_UNIT + COMMA
            + formatter.getString(divTag.getTranslateY()) + SIZE_UNIT + ")" + DELIMITER;
    return transform;
  }



}
