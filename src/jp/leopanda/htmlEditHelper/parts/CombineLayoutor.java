package jp.leopanda.htmlEditHelper.parts;

/**
 * 縦型＋横レイアウトへDivタグ配列を書き換える
 * 
 * @author LeoPanda
 *
 */
public class CombineLayoutor {
  private DivTag[] divTags;
  private LayoutVariables variables;
  private int standardIndex;// 基準となる縦型イメージのインデックス位置を指定する

  /**
   * コンストラクタ
   * 
   * @param divTags DivTag[] DIVタグ配列
   * @param variables LayoutVariables 入力変数セット
   * @param standardIndex 基準となる縦型イメージを含むDIVのインデックス位置
   */
  public CombineLayoutor(DivTag[] divTags, LayoutVariables variables, int standardIndex) {
    this.divTags = divTags;
    this.variables = variables;
    this.standardIndex = standardIndex;
  }

  /**
  /* レイアウト整形を実施する
   * 
   * @return DIVタグ配列
   */
  public DivTag[] doLayout() {
    float totalReAspects = getTotalReAspects(standardIndex);
    float standardAspect = divTags[standardIndex].getAspectRatio();
    float layoutHeight = getlayoutHeight(totalReAspects, standardAspect);
    float satelliteWidth = standardAspect * layoutHeight;
    for (int i = 0; i < divTags.length; i++) {
      divTags = setSquareSize(layoutHeight, satelliteWidth, i);
      divTags = setIndents(i);
    }
    return divTags;
  }

  // 横レイアウトのアスペクト逆数合計を取得する
  private float getTotalReAspects(int standardIndex) {
    float totalReAspects = 0;
    for (int i = 0; i < divTags.length; i++) {
      if (i != standardIndex) {
        totalReAspects += 1 / divTags[i].getAspectRatio();
      }
    }
    return totalReAspects;
  }

  // レイアウト領域の高さを取得する
  private float getlayoutHeight(float totalReAspects, float standardAspect) {
    return ((variables.layoutWidth - variables.indentX) * totalReAspects
        + (divTags.length - 2) * variables.indentY) / (1 + standardAspect * totalReAspects);
  }

  // 各DIVの縦幅と横幅をセットする
  private DivTag[] setSquareSize(float layoutHeight, float satelliteWidth, int index) {
    if (index == standardIndex) {
      divTags[index].setHeight(layoutHeight);
      divTags[index].setWidth(satelliteWidth);
    } else {
      divTags[index].setHeight(getVerticalHeight(satelliteWidth, index));
      divTags[index].setWidth(variables.layoutWidth - satelliteWidth);
    }
    return divTags;
  }

  // 縦並びDIVの縦幅を得る
  private int getVerticalHeight(float satelliteWidth, int index) {
    return (int) ((variables.layoutWidth - satelliteWidth - variables.indentX)
        / divTags[index].getAspectRatio());
  }

  // DIVタグ間のインデントをセットする
  private DivTag[] setIndents(int index) {
    if (standardIndex == 0) {
      divTags = setLeftStandardedIndents(standardIndex, index);
    } else {
      divTags = setRightStandardedIndents(standardIndex, index);
    }
    return divTags;
  }

  // 左側基準のレイアウト時、縦横インデントと画像移動分の縦マージンをセットする
  private DivTag[] setLeftStandardedIndents(int standardIndex, int index) {
    float transLateX = divTags[standardIndex].getWidth() + variables.indentX;
    if (index == standardIndex) {
      divTags[index].setMarginBottom(-1 * divTags[standardIndex].getHeight());
    } else {
      divTags[index].setMarginBottom(variables.indentY);
      divTags[index].setTranslateX(transLateX);
    }
    return divTags;
  }

  // 右側基準のレイアウト時、縦横インデントと画像移動分の縦マージンをセットする
  private DivTag[] setRightStandardedIndents(int standardIndex, int index) {
    if (index == standardIndex) {
      divTags[index].setTranslateX(divTags[0].getWidth() + variables.indentX);
      divTags[index].setTranslateY(-1 * divTags[index].getHeight() - variables.indentY);
      divTags[index].setMarginBottom(-1 * divTags[index].getHeight());
    } else {
      divTags[index].setMarginBottom(variables.indentY);
    }
    return divTags;
  }
}
