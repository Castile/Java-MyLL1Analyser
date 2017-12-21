package cn.zhuhongliang.LL1_SyntacticAnalyzer.util;

import java.util.List;
import java.util.TreeSet;

/**
 * LL1文法的一些操作：
 * 1.求First集
 * 2.求Follow集
 * 3.求select集
 * 4.求预测分析表
 * 
 * 2017年12月19日 08:51:11
 * @author 朱宏梁
 *
 */
public interface GrammarOperator {
	
	/**
	 * 获取非终结符集与终结符集
	 * 
	 * @param gsArray
	 * @param nvSet ：非终结符
	 * @param ntSet ：终结符
	 */
	public void getNvNt();
	
	
	/**
	 * 初始化表达式集合
	 */
	public void initExpressionMaps();
	
	

	/**
	 * 获取First集
	 */
	public void getFirst();
	
	/**
	 * 计算First函数
	 * 
	 * @param itemSet first集的结果集合
	 * @param charItem
	 * @param itemitemChar
	 * @return
	 */
	public boolean calcFirst(TreeSet<Character> itemSet, Character charItem, char itemitemChar);
	
	/**
	 * 获取Follow集合
	 */
	public void getFollow();
	

	/**
	 * 计算Follow集
	 * 
	 * @param putCharItem
	 *            正在查询item
	 * @param charItem
	 *            待找item
	 * @param keyCharItem
	 *            节点名
	 * @param itemCharStr
	 *            符号集
	 * @param itemSet
	 *            结果集合
	 */
	public void calcFollow(Character putCharItem, Character charItem, Character keyCharItem, String itemCharStr,
			TreeSet<Character> itemSet);
	/**
	 * 获取Select集合
	 */
	public void getSelect();
	
	/**
	 * 生成预测分析表
	 */
	public void genAnalyzeTable()throws Exception;
	
	
	
}
