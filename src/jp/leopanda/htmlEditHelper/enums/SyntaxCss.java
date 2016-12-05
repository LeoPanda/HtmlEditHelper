package jp.leopanda.htmlEditHelper.enums;

import jp.leopanda.panelFrame.filedParts.ListElement;

/**
 * SyntaxHilighter用CSSスタイル
 * 
 * @author LeoPanda
 *
 */
public enum SyntaxCss implements ListElement {
  DEFAULT("標準", "Default.css"), DJANGO("Django", "Django.css"), ECLIPSE("Eclipse", "Eclipse.css"),
  EMACS("Emacs", "Emacs.css"), FADETOGRAY("FadeToGray", "FadeToGray.css"),
  MDULTR("MDUltra", "MDUltra.css"), MIDNIGHT("Midnight", "Midnight.css"),
  RDARK("RDark", "RDark.css");

  private String name;
  private String keyword;

  SyntaxCss(String name, String keyword) {
    this.name = name;
    this.keyword = keyword;

  }

  @Override
  public String getName() {
    return this.name;
  }

  public String getKeyword() {
    return this.keyword;
  }

}
