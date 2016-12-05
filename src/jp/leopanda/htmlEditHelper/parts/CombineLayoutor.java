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

  public CombineLayoutor(DivTag[] divTags, LayoutVariables variables, int standardIndex) {
    this.divTags = divTags;
    this.variables = variables;
    this.standardIndex = standardIndex;
  }

  // レイアウト整形を実施する
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
    return ((variables.layoutWidth - variables.xIndent) * totalReAspects
        + (divTags.length - 2) * variables.yIndent) / (1 + standardAspect * totalReAspects);
  }

  // 各DIVの縦幅と横幅をセットする
  private DivTag[] setSquareSize(float layoutHeight, float satelliteWidth, int i) {
    if (i == standardIndex) {
      divTags[i].setHeight(layoutHeight);
      divTags[i].setWidth(satelliteWidth);
    } else {
      divTags[i].setHeight(getVerticalHeight(satelliteWidth, i));
      divTags[i].setWidth(variables.layoutWidth - satelliteWidth);
    }
    return divTags;
  }

  // 縦並びDIVの縦幅を得る
  private int getVerticalHeight(float satelliteWidth, int i) {
    return (int) ((variables.layoutWidth - satelliteWidth - variables.xIndent)
        / divTags[i].getAspectRatio());
  }

  // DIVタグ間のインデントをセットする
  private DivTag[] setIndents(int i) {
    if (standardIndex == 0) {
      divTags = setLeftStandardedIndents(standardIndex, i);
    } else {
      divTags = setRightStandardedIndents(standardIndex, i);
    }
    return divTags;
  }

  // 左側基準のレイアウト時、縦横インデントと画像移動分の縦マージンをセットする
  private DivTag[] setLeftStandardedIndents(int standardIndex, int i) {
    float transLateX = divTags[standardIndex].getWidth() + variables.xIndent;
    if (i == standardIndex) {
      divTags[i].setMarginBottom(-1 * divTags[standardIndex].getHeight());
    } else {
      divTags[i].setMarginBottom(variables.yIndent);
      divTags[i].setTranslateX(transLateX);
    }
    return divTags;
  }

  // 右側基準のレイアウト時、縦横インデントと画像移動分の縦マージンをセットする
  private DivTag[] setRightStandardedIndents(int standardIndex, int i) {
    if (i == standardIndex) {
      divTags[i].setTranslateX(divTags[0].getWidth() + variables.xIndent);
      divTags[i].setTranslateY(-1 * divTags[i].getHeight()-variables.yIndent);
      divTags[i].setMarginBottom(-1 * divTags[i].getHeight());
    } else {
      divTags[i].setMarginBottom(variables.yIndent);
    }
    return divTags;
  }
}
