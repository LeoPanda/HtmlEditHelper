package jp.leopanda.htmlEditHelper.panelParts;

import java.util.Map;
import java.util.TreeMap;

import jp.leopanda.htmlEditHelper.filedParts.FieldCommon;

/**
 * 入力パネルに搭載するフィールドを
 *　一括で内部処理するために使用するコレクションマップ
 *
 * @author LeoPanda
 *
 */
public class FieldMap {
	//indexの刻み幅
	private int step;	
	/*
	 * フィールドマップ
	 * <index,フィールド> 
	 * indexの昇順でvalidateを走査する
	 */
	private Map<Integer, FieldCommon> fieldMap
				= new TreeMap<Integer, FieldCommon>();
	/*
	 * コンストラクタ
	 */
	public FieldMap(int step) {
		this.step = step;
	}
	/*
	 * フィールドの追加
	 * @param field フィールド
	 * @param 戻り値 フィールドマップ上の挿入位置
	 */
	public int add(FieldCommon field){
		int nextIndex = getNextIndex();
		fieldMap.put(nextIndex, field);
		return nextIndex;
	}
	/*
	 * フィールドの削除
	 * @param index 削除するフィールドの位置
	 */
	public void remove(int index){
		fieldMap.remove(index);
	}
	/*
	 * フィールドの挿入
	 * @param index フィールドマップの挿入位置
	 * @param field 挿入するフィールド
	 */
	public void insert(int index,FieldCommon field){
		fieldMap.put(index, field);
	}
	/*
	 * フィールドの取得
	 */
	public FieldCommon getField(int index){
		return fieldMap.get(index);
	}
	/*
	 * 刻み幅の取得
	 */
	public int getStep(){
		return this.step;
	}
	/*
	 * フィールドの正当性検査
	 */
	public boolean validate(){
		for(Map.Entry<Integer, FieldCommon> fieldSet:fieldMap.entrySet()){
			 if(!fieldSet.getValue().validate()){
				 fieldSet.getValue().popError();
				 return false;
			 }
		}
		return true;
	}
	/*
	 * フィールドマップの挿入位置を取得する
	 */
	private int getNextIndex(){
		int index = 0;
		for(Map.Entry<Integer, FieldCommon> fieldSet:fieldMap.entrySet()){
			index = fieldSet.getKey();
		}
		return (new Integer(index/step) * step) + step;
	}
	
}
