package jp.leopanda.htmlEditHelper.parts;

import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;

/**
 * HTMLソースを整形する
 * 
 * @author LeoPanda
 *
 */
public class SourceFormatter {
  private String source;

  public SourceFormatter(String source) {
    this.source = source;
  }

  /**
   * 整形後のソースを取り出す
   * 
   * @return String 整形後のソース
   */
  public String getFormattedSource() {
    return this.source;
  }

  /**
   * 対象のDIVとDIVの間にある<br />
   * を削除する
   * 
   * @return SourceFormatter
   */
  public SourceFormatter omitBr() {
    String regex = "</div>[\t\n]*((<br />)[\t\n]*)*<div";
    source = source.replaceAll(regex, "</div><div");
    return this;
  }

  /**
   * 空白の対象DIVを削除
   * 
   * @param targetClass 対象DIVのクラス名
   * @return SourceFormatter
   */
  public SourceFormatter omitWhiteDiv(String targetClass) {
    String regex = getDivRegex(targetClass) + "([\t\n]*((<br />)[\t\n]*)*</div>)";
    source = source.replaceAll(regex, "");
    return this;
  }

  /**
   * 画像の表示ソースの表示サイズを変更する
   * 
   * @param size int 変更後の表示サイズ
   * @return SourceFormatter
   */
  public SourceFormatter setImgSourceSize(int size) {
    String regex = "(<img.*src=.*)(/s[0-9]{3}/)(.*/>)";
    RegExp regExp = RegExp.compile(regex, "gm");
    regExp.exec(source);
    source = regExp.replace(source, "$1/s" + String.valueOf(size) + "/$3");
    return this;
  }

  /**
   * 連続する複数行のテキストタグを１つのタグに集約する（不要なタグをタブ文字に置き換える）
   * 
   * @param targetClass 対象DIVのクラス名
   * @return SourceFormatter
   */
  public SourceFormatter compressTextDiv(String targetClass) {
    String regex = getDivRegexSnipet(targetClass) + "\">" + "([^<]*)(</div>)";
    regex = regex + "[\n\t ]*" + regex;
    RegExp regExp = RegExp.compile(regex, "gm");
    MatchResult matcher = regExp.exec(source);
    while (matcher != null) {
      source = regExp.replace(source, "$1\">$2\t$5$6");
      matcher = regExp.exec(source);
    }
    return this;
  }

  /**
   * タブ文字を＜BR＞タグに置き換える
   * 
   * @return SourceFormatter
   */
  public SourceFormatter replaceTab2Br() {
    source = source.replaceAll("\t", "<br />");
    return this;
  }

  // 指定classのDIVタグ正規表現パターンを得る
  private String getDivRegex(String targetClass) {
    return "(<div *class=\"" + targetClass + "\" [^>]*>)";
  }

  // 指定classDIVタグから行末の">)"を除いた正規表現パターンを得る
  private String getDivRegexSnipet(String targetCalss) {
    String divRegex = getDivRegex(targetCalss);
    return divRegex.substring(0, divRegex.length() - 2) + ")";
  }

}
