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
 *  LL1�﷨
 * @author �����
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
		
								//���ս��            ��Ӧ�Ĳ���ʽ��    select����
		selectMap = new TreeMap<Character, HashMap<String, TreeSet<Character>>>();
	}

	/**
	 * Ԥ�������
	 */
	private String[][] analyzeTable;

	/**
	 * LL��1���ķ���������
	 */
	private ArrayList<String> gsArray;
	/**
	 * ���ʽ����
	 */
	private HashMap<Character, ArrayList<String>> expressionMap;
	/**
	 * ��ʼ��
	 */
	private Character s;
	/**
	 * Vn���ս������
	 */
	private TreeSet<Character> nvSet;
	/**
	 * Vt�ս������
	 */
	private TreeSet<Character> ntSet;
	/**
	 * First����
	 */
	private HashMap<Character, TreeSet<Character>> firstMap;
	/**
	 * Follow����
	 */
	private HashMap<Character, TreeSet<Character>> followMap;

	/**
	 * Select����
	 */
	private TreeMap<Character, HashMap<String, TreeSet<Character>>> selectMap;

	/**
	 * ���Ԥ�������
	 * @return
	 */
	public String[][] getAnalyzeTable() {
		return analyzeTable;
	}

	/**
	 * ����Ԥ�������
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
	 * ��ȡ���ս�������ս����
	 * 
	 * @param gsArray
	 * @param nvSet �����ս��
	 * @param ntSet ���ս��
	 */
	public void getNvNt() {
		for (String gsItem : gsArray) {
			String[] nvNtItem = gsItem.split("->");
			String charItemStr = nvNtItem[0];
			char charItem = charItemStr.charAt(0);
			// nv�����
			nvSet.add(charItem);
		}
		for (String gsItem : gsArray) {
			String[] nvNtItem = gsItem.split("->");
			// nt���ұ�
			String nvItemStr = nvNtItem[1];
			// ����ÿһ����
			for (int i = 0; i < nvItemStr.length(); i++) {
				char charItem = nvItemStr.charAt(i);
				if (!nvSet.contains(charItem)) {//������ս����������û������ַ��ͱ������ַ�Ϊ�ս��
					ntSet.add(charItem);
				}
			}
		}
	}

	/**
	 * ��ʼ�����ʽ����
	 */
	public void initExpressionMaps() {
		expressionMap = new HashMap<Character, ArrayList<String>>();
		for (String gsItem : gsArray) {
			String[] nvNtItem = gsItem.split("->");
			String charItemStr = nvNtItem[0];   // ��
			String charItemRightStr = nvNtItem[1];  // �Ҳ�
			char charItem = charItemStr.charAt(0);  // ȡ�󲿵ĵ�һ���ַ������ս����
			if (!expressionMap.containsKey(charItem)) { //�ж�������ս���Ƿ��Ѿ��ڱ��ʽ��������������¼��
				//û��������¼����Ҫ����һ����Ÿ÷��ս������ʽ�ļ���
				ArrayList<String> expArr = new ArrayList<String>(); // ������ս���Ĳ���ʽ����
				expArr.add(charItemRightStr);  //����Ҳ�
				expressionMap.put(charItem, expArr); // ����������ս���ı��ʽ������
			} else {
				//����Ѿ����������ļ�¼����ֱ��ȡ������ʽ���Ҳ��ӽ�ȥ����
				
				//�Ȼ�ȡ�������
				ArrayList<String> expArr = expressionMap.get(charItem); //ȡ�����ַ���Ӧ�Ĳ���ʽ�Ҳ��ּ���
				//���
				expArr.add(charItemRightStr);
				//�����ܵı��ʽ����
				expressionMap.put(charItem, expArr);
			}
		}
	}

	/**
	 * ��ȡFirst��
	 */
	public void getFirst() {
		// �������з��ս����Nv��,������ǵ�First����
		Iterator<Character> iterator = nvSet.iterator(); //��ȡ������
		while (iterator.hasNext()) {
			Character charItem = iterator.next();
			ArrayList<String> arrayList = expressionMap.get(charItem); //�õ��÷��ս���Ĳ���ʽ�Ҳ�
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
	 * ����First����
	 * 
	 * @param itemSet first���Ľ������
	 * @param charItem
	 * @param itemitemChar
	 * @return
	 */
	public boolean calcFirst(TreeSet<Character> itemSet, Character charItem, char itemitemChar) {
		// get ago
		// TreeSet<Character> itemSet = new TreeSet<Character>();
		// ������ÿһλ��Nt�ж���
		// ���ս����մ�,��ֹͣ���������ӵ�FirstMap��
		if (itemitemChar == '��' || ntSet.contains(itemitemChar)) {
			itemSet.add(itemitemChar);
			firstMap.put(charItem, itemSet);
			// break;
			return true;
		} else if (nvSet.contains(itemitemChar)) {// ��һλ��һ�����ս��
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
	 * ��ȡFollow����
	 */
	public void getFollow() {
		//��ʼ��
		for (Character tempKey : nvSet) {
			TreeSet<Character> tempSet = new TreeSet<Character>();
			followMap.put(tempKey, tempSet);
		}
		// ��������Nv,������ǵ�First����
		Iterator<Character> iterator = nvSet.descendingIterator(); //�����ڴ� set Ԫ���ϰ�������е����ĵ�������
	
		while (iterator.hasNext()) {
			Character charItem = iterator.next(); // ȡ��һ�����ս��
			Set<Character> keySet = expressionMap.keySet();// ���ش�ӳ�����������ļ��� Set ��ͼ��ȡ�ñ��ʽ���ϵļ�
			for (Character keyCharItem : keySet) { // ѭ������ÿһ����
				ArrayList<String> charItemArray = expressionMap.get(keyCharItem); //ȡ�����������Ӧ�ı��ʽ����
				for (String itemCharStr : charItemArray) { // Ȼ��ѭ������������ʽ����
					TreeSet<Character> itemSet = followMap.get(charItem); //ȡ��������ս����Ӧ��Follow����
					calcFollow(charItem, charItem, keyCharItem, itemCharStr, itemSet); //����Follow����
				}
			}
		}
	}

	/**
	 * ����Follow��
	 * 
	 * @param putCharItem
	 *            ���ڲ�ѯitem
	 * @param charItem
	 *            ����item
	 * @param keyCharItem
	 *            �ڵ���
	 * @param itemCharStr
	 *            ���ż�
	 * @param itemSet
	 *            �������
	 */
	public void calcFollow(Character putCharItem, Character charItem, Character keyCharItem, String itemCharStr,
			TreeSet<Character> itemSet) {
	
		// ��1������ǿ�ʼ����S����ʼ��)������#
		if (charItem.equals(s)) {
			itemSet.add('#');
			followMap.put(putCharItem, itemSet);
		
		}
		// (2)����B��>Ab������ʽ���� First��b����Follow��A��
		if (TextUtils.containsAb(ntSet, itemCharStr, charItem)) {
			Character alastChar = TextUtils.getAlastChar(itemCharStr, charItem);
			itemSet.add(alastChar);
			followMap.put(putCharItem, itemSet);
			// return;
		}
		// (3).2AB,=First(B)-��,=First(B)-�ţ����first����
		if (TextUtils.containsAB(nvSet, itemCharStr, charItem)) {
			Character alastChar = TextUtils.getAlastChar(itemCharStr, charItem); //ȡ��A������ַ�
			TreeSet<Character> treeSet = firstMap.get(alastChar); //�������ַ���First����
			itemSet.addAll(treeSet);
			if (treeSet.contains('��')) {
				itemSet.add('#');
			}
			itemSet.remove('��');
			followMap.put(putCharItem, itemSet);
			
			/**
			 * ����" B��>bAC,����C��>��"  ������ʽ���� Follow(B) ��Follow��A�� 
			 */
			
			if (TextUtils.containsbAbIsNull(nvSet, itemCharStr, charItem, expressionMap)) {
				char tempChar = TextUtils.getAlastChar(itemCharStr, charItem);
				
				if (!keyCharItem.equals(charItem)) {
					Set<Character> keySet = expressionMap.keySet();
					for (Character keyCharItems : keySet) {
						ArrayList<String> charItemArray = expressionMap.get(keyCharItems);
						for (String itemCharStrs : charItemArray) {
							calcFollow(putCharItem, keyCharItem, keyCharItems, itemCharStrs, itemSet); //�ݹ����
						}
					}
				}
			}
		}
		// (3)B->aA,=Follow(B),���followB
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
	 * ��ȡSelect����
	 */
	public void getSelect() {
		// ����ÿһ�����ʽ
		Set<Character> keySet = expressionMap.keySet();
		for (Character selectKey : keySet) {
			ArrayList<String> arrayList = expressionMap.get(selectKey);
			// ÿһ�����ʽ
			HashMap<String, TreeSet<Character>> selectItemMap = new HashMap<String, TreeSet<Character>>();
			for (String selectExp : arrayList) {
				/**
				 * ���select����ļ���
				 */
				TreeSet<Character> selectSet = new TreeSet<Character>();
				// set���ŵ����ݷ�3�����,��selectExp����
				// 1.A->��,=follow(A)
				if (TextUtils.isEmptyStart(selectExp)) {
					selectSet = followMap.get(selectKey);
					selectSet.remove('��');
					selectItemMap.put(selectExp, selectSet);
				}
				// 2.Nt��ʼ,=Nt
				// �ս����ʼ  ������Ϊ��
				if (TextUtils.isNtStart(ntSet, selectExp)) {
					selectSet.add(selectExp.charAt(0));
					selectSet.remove('��');
					selectItemMap.put(selectExp, selectSet);
				}
				// 3.Nv��ʼ��=first(Nv) 
				if (TextUtils.isNvStart(nvSet, selectExp)) {
					selectSet = firstMap.get(selectKey);
					selectSet.remove('��');
					selectItemMap.put(selectExp, selectSet);
				}
				selectMap.put(selectKey, selectItemMap);
			}
		}
	}

	/**
	 * ����Ԥ�������
	 */
	public void genAnalyzeTable() throws Exception {
		Object[] ntArray = ntSet.toArray();
		Object[] nvArray = nvSet.toArray();
		// Ԥ��������ʼ��
		analyzeTable = new String[nvArray.length + 1][ntArray.length + 1];
		System.out.println("\n\n");
		System.out.println("******************************************************�ķ���LL(1)������**********************************************************************");
		System.out.println("_______________________________________________________________________________________________________________________________________");
		// ���һ��ռλ��
		System.out.print("Nv/Nt" + "\t\t");
		analyzeTable[0][0] = "Nv/Nt";
		
		// ��ʼ������
		for (int i = 0; i < ntArray.length; i++) {
			if (ntArray[i].equals('��')) {
				ntArray[i] = '#';
			}
			System.out.print(ntArray[i] + "\t\t");
			analyzeTable[0][i + 1] = ntArray[i] + "";
		}
		System.out.println();
		System.out.println("----------------------------------------------------------------------------------------------------------------------------------------");
		
		for (int i = 0; i < nvArray.length; i++) {
			// ���г�ʼ��
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

