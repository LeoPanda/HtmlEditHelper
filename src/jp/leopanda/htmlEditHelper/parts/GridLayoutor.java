package jp.leopanda.htmlEditHelper.parts;

/**
 * グリッドタイプのレイアウトへDivタグ配列を書き換える
 * 
 * @author LeoPanda
 *
 */
public class GridLayoutor {
  private DivTag[] divTags;
  private LayoutVariables variables;

  /**
   * コンストラクタ
   * 
   * @param divTags 変更対象のDIVタグ配列
   * @param variables 画面から入力されたパラメータ変数
   */
  public GridLayoutor(DivTag[] divTags, LayoutVariables variables) {
    this.divTags = divTags;
    this.variables = variables;
  }

  /**
   * レイアウト整形を実施する
   * 
   * @return 整形後のDiv諸元配列
   */
  public DivTag[] doLayout() {
    for (int rowIndex = 0; rowIndex < divTags.length; rowIndex += variables.maxCols) {
      float tatalAspects = getTotalAspects(rowIndex);
      float layoutHeight = getLayoutHeight(tatalAspects, rowIndex);
      setSquareSize(layoutHeight, rowIndex);
      setIndent(rowIndex);
    }
    return divTags;
  }

  // 一行分のアスペクト比合計を得る
  private float getTotalAspects(int rowIndex) {
    return new RowLoop() {
      @Override
      public float cellProcess(int cellIndex) {
        return divTags[cellIndex].getAspectRatio();
      }
    }.summarry(rowIndex);
  }

  // レイアウト一行分の高さを得る
  private float getLayoutHeight(float totalAspects, int rowIndex) {
    return (variables.layoutWidth - variables.indentX * getNumIndent(rowIndex)) / totalAspects;
  }

  // レイアウト一行に横インデントが何箇所挿入されるかを調べる
  private int getNumIndent(int rowIndex) {
    return (int) new RowLoop() {
      @Override
      public float cellProcess(int cellIndex) {
        return 1;
      }
    }.summarry(rowIndex);
  }

  // レイアウト一行分の各DIVの高さと幅を設定する
  private void setSquareSize(float layoutHeight, int rowIndex) {
    new RowLoop(layoutHeight) {
      @Override
      public float cellProcess(int cellIndex) {
        divTags[cellIndex].setHeight(inputConstant);
        divTags[cellIndex].setWidth(inputConstant * divTags[cellIndex].getAspectRatio());
        return 0;
      }
    }.summarry(rowIndex);
  }

  // レイアウト一行分の縦横インデントをセットする
  private void setIndent(int rowIndex) {
    new RowLoop() {
      @Override
      public float cellProcess(int cellIndex) {
        if (cellIndex % variables.maxCols < variables.maxCols - 1) {
          divTags[cellIndex].setMarginBottom(-1 * divTags[cellIndex].getHeight());
        } else {
          divTags[cellIndex].setMarginBottom(variables.indentY);
        }
        divTags[cellIndex].setTranslateX(summrizer);
        return divTags[cellIndex].getWidth() + variables.indentX;
      }
    }.summarry(rowIndex);
    divTags[divTags.length - 1].setMarginBottom(0);
  }

  // レイアウト１行分のループ処理
  private abstract class RowLoop {
    protected float summrizer = 0;// ループ処理後ののサマリー値
    protected float inputConstant = 0;// 入力定数

    /**
     * 単純ループ用コンストラクタ
     */
    RowLoop() {}

    /**
     * 入力定数付きループ用コンストラクタ
     */
    RowLoop(float inputConstant) {
      this.inputConstant = inputConstant;
    }

    /**
     * レイアウト１行分のループ処理を行いサマリー値を返す
     * 
     * @param rowIndex divTags配列のレイアウト行開始インデックス
     * @return サマリー結果
     */
    public float summarry(int rowIndex) {
      for (int cellIndex = rowIndex; cellIndex < rowIndex + variables.maxCols
          && cellIndex < divTags.length; cellIndex++) {
        summrizer += cellProcess(cellIndex);
      }
      return summrizer;
    }

    /**
     * セル毎の処理を記述する
     * 
     * @param cellIndex divTag配列の処理対象セル位置
     * @return サマリーに加算するセル単体の計算値
     */
    public abstract float cellProcess(int cellIndex);
  }

}
