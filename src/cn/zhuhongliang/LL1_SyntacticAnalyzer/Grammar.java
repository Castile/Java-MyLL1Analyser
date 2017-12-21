package cn.zhuhongliang.LL1_SyntacticAnalyzer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import cn.zhuhongliang.LL1_SyntacticAnalyzer.util.GrammarOperator;
import cn.zhuhongliang.LL1_SyntacticAnalyzer.util.TextUtils;


/**
 *  LL1语法
 * @author 朱宏梁
 *
 */
public class Grammar implements Serializable,GrammarOperator{


	private static final long serialVersionUID = 1L;

	public Grammar() {
		super();
		gsArray = new ArrayList<String>();
		nvSet = new TreeSet<Character>();
		ntSet = new TreeSet<Character>();
		firstMap = new HashMap<Character, TreeSet<Character>>();
		followMap = new HashMap<Character, TreeSet<Character>>();
		
								//非终结符            对应的产生式的    select集合
		selectMap = new TreeMap<Character, HashMap<String, TreeSet<Character>>>();
	}

	/**
	 * 预测分析表
	 */
	private String[][] analyzeTable;

	/**
	 * LL（1）文法产生集合
	 */
	private ArrayList<String> gsArray;
	/**
	 * 表达式集合
	 */
	private HashMap<Character, ArrayList<String>> expressionMap;
	/**
	 * 开始符
	 */
	private Character s;
	/**
	 * Vn非终结符集合
	 */
	private TreeSet<Character> nvSet;
	/**
	 * Vt终结符集合
	 */
	private TreeSet<Character> ntSet;
	/**
	 * First集合
	 */
	private HashMap<Character, TreeSet<Character>> firstMap;
	/**
	 * Follow集合
	 */
	private HashMap<Character, TreeSet<Character>> followMap;

	/**
	 * Select集合
	 */
	private TreeMap<Character, HashMap<String, TreeSet<Character>>> selectMap;

	/**
	 * 获得预测分析表
	 * @return
	 */
	public String[][] getAnalyzeTable() {
		return analyzeTable;
	}

	/**
	 * 设置预测分析表
	 * @param analyzeTable
	 */
	public void setAnalyzeTable(String[][] analyzeTable) {
		this.analyzeTable = analyzeTable;
	}

	public TreeMap<Character, HashMap<String, TreeSet<Character>>> getSelectMap() {
		return selectMap;
	}

	public void setSelectMap(TreeMap<Character, HashMap<String, TreeSet<Character>>> selectMap) {
		this.selectMap = selectMap;
	}

	public HashMap<Character, TreeSet<Character>> getFirstMap() {
		return firstMap;
	}

	public void setFirstMap(HashMap<Character, TreeSet<Character>> firstMap) {
		this.firstMap = firstMap;
	}

	public HashMap<Character, TreeSet<Character>> getFollowMap() {
		return followMap;
	}

	public void setFollowMap(HashMap<Character, TreeSet<Character>> followMap) {
		this.followMap = followMap;
	}

	public HashMap<Character, ArrayList<String>> getExpressionMap() {
		return expressionMap;
	}

	public void setExpressionMap(HashMap<Character, ArrayList<String>> expressionMap) {
		this.expressionMap = expressionMap;
	}

	public ArrayList<String> getGsArray() {
		return gsArray;
	}

	public void setGsArray(ArrayList<String> gsArray) {
		this.gsArray = gsArray;
	}

	public Character getS() {
		return s;
	}

	public void setS(Character s) {
		this.s = s;
	}

	public TreeSet<Character> getNvSet() {
		return nvSet;
	}

	public void setNvSet(TreeSet<Character> nvSet) {
		this.nvSet = nvSet;
	}

	public TreeSet<Character> getNtSet() {
		return ntSet;
	}

	public void setNtSet(TreeSet<Character> ntSet) {
		this.ntSet = ntSet;
	}

	/**
	 * 获取非终结符集与终结符集
	 * 
	 * @param gsArray
	 * @param nvSet ：非终结符
	 * @param ntSet ：终结符
	 */
	public void getNvNt() {
		for (String gsItem : gsArray) {
			String[] nvNtItem = gsItem.split("->");
			String charItemStr = nvNtItem[0];
			char charItem = charItemStr.charAt(0);
			// nv在左边
			nvSet.add(charItem);
		}
		for (String gsItem : gsArray) {
			String[] nvNtItem = gsItem.split("->");
			// nt在右边
			String nvItemStr = nvNtItem[1];
			// 遍历每一个字
			for (int i = 0; i < nvItemStr.length(); i++) {
				char charItem = nvItemStr.charAt(i);
				if (!nvSet.contains(charItem)) {//如果非终结符集合里面没有这个字符就表明此字符为终结符
					ntSet.add(charItem);
				}
			}
		}
	}

	/**
	 * 初始化表达式集合
	 */
	public void initExpressionMaps() {
		expressionMap = new HashMap<Character, ArrayList<String>>();
		for (String gsItem : gsArray) {
			String[] nvNtItem = gsItem.split("->");
			String charItemStr = nvNtItem[0];   // 左部
			String charItemRightStr = nvNtItem[1];  // 右部
			char charItem = charItemStr.charAt(0);  // 取左部的第一个字符（非终结符）
			if (!expressionMap.containsKey(charItem)) { //判断这个非终结符是否已经在表达式集合中有这条记录了
				//没有这条记录，则要创建一个存放该非终结符产生式的集合
				ArrayList<String> expArr = new ArrayList<String>(); // 这个非终结符的产生式集合
				expArr.add(charItemRightStr);  //添加右部
				expressionMap.put(charItem, expArr); // 加入这个非终结符的表达式集合中
			} else {
				//如果已经存在这样的记录，则直接取出产生式的右部加进去即可
				
				//先获取这个集合
				ArrayList<String> expArr = expressionMap.get(charItem); //取出此字符对应的产生式右部分集合
				//添加
				expArr.add(charItemRightStr);
				//加入总的表达式集合
				expressionMap.put(charItem, expArr);
			}
		}
	}

	/**
	 * 获取First集
	 */
	public void getFirst() {
		// 遍历所有非终结符（Nv）,求出它们的First集合
		Iterator<Character> iterator = nvSet.iterator(); //获取迭代器
		while (iterator.hasNext()) {
			Character charItem = iterator.next();
			ArrayList<String> arrayList = expressionMap.get(charItem); //得到该非终结符的产生式右部
			for (String itemStr : arrayList) {
				boolean shouldBreak = false;
				// Y1Y2Y3...Yk
				for (int i = 0; i < itemStr.length(); i++) {
					char itemitemChar = itemStr.charAt(0);
					TreeSet<Character> itemSet = firstMap.get(charItem);
					if (null == itemSet) {
						itemSet = new TreeSet<Character>();
					}
					shouldBreak = calcFirst(itemSet, charItem, itemitemChar);
					if (shouldBreak) {
						break;
					}
				}
			}
		}
	}

	/**
	 * 计算First函数
	 * 
	 * @param itemSet first集的结果集合
	 * @param charItem
	 * @param itemitemChar
	 * @return
	 */
	public boolean calcFirst(TreeSet<Character> itemSet, Character charItem, char itemitemChar) {
		// get ago
		// TreeSet<Character> itemSet = new TreeSet<Character>();
		// 将它的每一位和Nt判断下
		// 是终结符或空串,就停止，并将它加到FirstMap中
		if (itemitemChar == 'ε' || ntSet.contains(itemitemChar)) {
			itemSet.add(itemitemChar);
			firstMap.put(charItem, itemSet);
			// break;
			return true;
		} else if (nvSet.contains(itemitemChar)) {// 这一位是一个非终结符
			ArrayList<String> arrayList = expressionMap.get(itemitemChar);
			for (int i = 0; i < arrayList.size(); i++) {
				String string = arrayList.get(i);
				char tempChar = string.charAt(0);
				calcFirst(itemSet, charItem, tempChar);
			}
		}
		return true;
	}

	/**
	 * 获取Follow集合
	 */
	public void getFollow() {
		//初始化
		for (Character tempKey : nvSet) {
			TreeSet<Character> tempSet = new TreeSet<Character>();
			followMap.put(tempKey, tempSet);
		}
		// 遍历所有Nv,求出它们的First集合
		Iterator<Character> iterator = nvSet.descendingIterator(); //返回在此 set 元素上按降序进行迭代的迭代器。
	
		while (iterator.hasNext()) {
			Character charItem = iterator.next(); // 取得一个非终结符
			Set<Character> keySet = expressionMap.keySet();// 返回此映射中所包含的键的 Set 视图。取得表达式集合的键
			for (Character keyCharItem : keySet) { // 循环遍历每一个键
				ArrayList<String> charItemArray = expressionMap.get(keyCharItem); //取出这个键所对应的表达式集合
				for (String itemCharStr : charItemArray) { // 然后循环遍历这个表达式集合
					TreeSet<Character> itemSet = followMap.get(charItem); //取出这个非终结符对应的Follow集合
					calcFollow(charItem, charItem, keyCharItem, itemCharStr, itemSet); //计算Follow集合
				}
			}
		}
	}

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
			TreeSet<Character> itemSet) {
	
		// （1）如果是开始符号S（开始符)，加入#
		if (charItem.equals(s)) {
			itemSet.add('#');
			followMap.put(putCharItem, itemSet);
		
		}
		// (2)形如B—>Ab这种形式，则 First（b）∈Follow（A）
		if (TextUtils.containsAb(ntSet, itemCharStr, charItem)) {
			Character alastChar = TextUtils.getAlastChar(itemCharStr, charItem);
			itemSet.add(alastChar);
			followMap.put(putCharItem, itemSet);
			// return;
		}
		// (3).2AB,=First(B)-ε,=First(B)-ε，添加first集合
		if (TextUtils.containsAB(nvSet, itemCharStr, charItem)) {
			Character alastChar = TextUtils.getAlastChar(itemCharStr, charItem); //取出A后面的字符
			TreeSet<Character> treeSet = firstMap.get(alastChar); //求出这个字符的First集合
			itemSet.addAll(treeSet);
			if (treeSet.contains('ε')) {
				itemSet.add('#');
			}
			itemSet.remove('ε');
			followMap.put(putCharItem, itemSet);
			
			/**
			 * 形如" B—>bAC,但是C—>ε"  这种形式，则 Follow(B) ∈Follow（A） 
			 */
			
			if (TextUtils.containsbAbIsNull(nvSet, itemCharStr, charItem, expressionMap)) {
				char tempChar = TextUtils.getAlastChar(itemCharStr, charItem);
				
				if (!keyCharItem.equals(charItem)) {
					Set<Character> keySet = expressionMap.keySet();
					for (Character keyCharItems : keySet) {
						ArrayList<String> charItemArray = expressionMap.get(keyCharItems);
						for (String itemCharStrs : charItemArray) {
							calcFollow(putCharItem, keyCharItem, keyCharItems, itemCharStrs, itemSet); //递归调用
						}
					}
				}
			}
		}
		// (3)B->aA,=Follow(B),添加followB
		if (TextUtils.containsbA(nvSet, itemCharStr, charItem, expressionMap)) {
			if (!keyCharItem.equals(charItem)) {
				Set<Character> keySet = expressionMap.keySet();
				for (Character keyCharItems : keySet) {
					ArrayList<String> charItemArray = expressionMap.get(keyCharItems);
					for (String itemCharStrs : charItemArray) {
						calcFollow(putCharItem, keyCharItem, keyCharItems, itemCharStrs, itemSet);
					}
				}
			}
		}
	}

	/**
	 * 获取Select集合
	 */
	public void getSelect() {
		// 遍历每一个表达式
		Set<Character> keySet = expressionMap.keySet();
		for (Character selectKey : keySet) {
			ArrayList<String> arrayList = expressionMap.get(selectKey);
			// 每一个表达式
			HashMap<String, TreeSet<Character>> selectItemMap = new HashMap<String, TreeSet<Character>>();
			for (String selectExp : arrayList) {
				/**
				 * 存放select结果的集合
				 */
				TreeSet<Character> selectSet = new TreeSet<Character>();
				// set里存放的数据分3种情况,由selectExp决定
				// 1.A->ε,=follow(A)
				if (TextUtils.isEmptyStart(selectExp)) {
					selectSet = followMap.get(selectKey);
					selectSet.remove('ε');
					selectItemMap.put(selectExp, selectSet);
				}
				// 2.Nt开始,=Nt
				// 终结符开始  不可能为空
				if (TextUtils.isNtStart(ntSet, selectExp)) {
					selectSet.add(selectExp.charAt(0));
					selectSet.remove('ε');
					selectItemMap.put(selectExp, selectSet);
				}
				// 3.Nv开始，=first(Nv) 
				if (TextUtils.isNvStart(nvSet, selectExp)) {
					selectSet = firstMap.get(selectKey);
					selectSet.remove('ε');
					selectItemMap.put(selectExp, selectSet);
				}
				selectMap.put(selectKey, selectItemMap);
			}
		}
	}

	/**
	 * 生成预测分析表
	 */
	public void genAnalyzeTable() throws Exception {
		Object[] ntArray = ntSet.toArray();
		Object[] nvArray = nvSet.toArray();
		// 预测分析表初始化
		analyzeTable = new String[nvArray.length + 1][ntArray.length + 1];
		System.out.println("\n\n");
		System.out.println("******************************************************文法的LL(1)分析表**********************************************************************");
		System.out.println("_______________________________________________________________________________________________________________________________________");
		// 输出一个占位符
		System.out.print("Nv/Nt" + "\t\t");
		analyzeTable[0][0] = "Nv/Nt";
		
		// 初始化首行
		for (int i = 0; i < ntArray.length; i++) {
			if (ntArray[i].equals('ε')) {
				ntArray[i] = '#';
			}
			System.out.print(ntArray[i] + "\t\t");
			analyzeTable[0][i + 1] = ntArray[i] + "";
		}
		System.out.println();
		System.out.println("----------------------------------------------------------------------------------------------------------------------------------------");
		
		for (int i = 0; i < nvArray.length; i++) {
			// 首列初始化
			System.out.print(nvArray[i] + "\t\t");
			analyzeTable[i + 1][0] = nvArray[i] + "";
			for (int j = 0; j < ntArray.length; j++) {
				String findUseExp = TextUtils.findUseExp(selectMap, Character.valueOf((Character) nvArray[i]),
						Character.valueOf((Character) ntArray[j]));
				if (null == findUseExp) {
					System.out.print("\t\t");
					analyzeTable[i + 1][j + 1] = "";
				} else {
					System.out.print(nvArray[i] + "->" + findUseExp + "\t\t");
					analyzeTable[i + 1][j + 1] = nvArray[i] + "->" + findUseExp;
				}
			}
			System.out.println();
			System.out.println("----------------------------------------------------------------------------------------------------------------------------------------");
			
			
		}
		System.out.println("***************************************************************************************************************************************");
	}
}

