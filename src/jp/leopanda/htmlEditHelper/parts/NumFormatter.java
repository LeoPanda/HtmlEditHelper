package jp.leopanda.htmlEditHelper.parts;

/**
 * 数値文字列整形ツール
 * @author LeoPanda
 *
 */
public class NumFormatter {
  int decimalLength;// 小数点以下の桁数

  public NumFormatter(int decimalLength) {
    this.decimalLength = decimalLength;
  }

  /**
   * 浮動小数点数値の文字列課
   * @param value float 対象の浮動小数点変数
   * @return 小数点付き文字列
   */
  public String getString(float value) {
    decimalLength = (int) Math.pow(10, decimalLength);
    String retString;
    int integerPart = (int) value;
    if (value == integerPart) {
      retString = String.valueOf(integerPart);
    } else {
      int decimalPart = (int) ((value - integerPart) * decimalLength);
      float formatedValue = integerPart + ((float) decimalPart / decimalLength);
      retString = String.valueOf(formatedValue);
    }
    return retString;
  }

}
