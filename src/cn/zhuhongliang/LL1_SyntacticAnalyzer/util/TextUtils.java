package cn.zhuhongliang.LL1_SyntacticAnalyzer.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;


/**
 * 字符工具类
 * 2017年12月18日 17:58:29
 * @author 朱宏梁
 *
 */
public class TextUtils {
    /**
     * 
     ** 以非终结符结尾的第一种形式 ：B->aA,=Follow(B)
     * 把Follow(B) 添加到 Follow（A）中
     * @param nvSet
     * @param itemCharStr
     * @param a
     * @param expressionMap
     * @return
     */
    public static boolean containsbA(TreeSet<Character> nvSet, String itemCharStr, Character a,
                                     HashMap<Character, ArrayList<String>> expressionMap) {
        String aStr = a.toString();
        String lastStr = itemCharStr.substring(itemCharStr.length() - 1);
        if (lastStr.equals(aStr)) {
            return true;
        }
        return false;

    }

    /**
     * 以非终结符结尾的第二种形式 ：形如aBb,b=空
     * 比如：A->aBa
     * 把Follow(A) 添加到 Follow（B）中
     * @param nvSet
     * @param itemCharStr
     * @param a
     * @param expressionMap
     * @return
     */
    public static boolean containsbAbIsNull(TreeSet<Character> nvSet, String itemCharStr, Character a,
                                            HashMap<Character, ArrayList<String>> expressionMap) {
        String aStr = a.toString();
        if (containsAB(nvSet, itemCharStr, a)) {
            Character alastChar = getAlastChar(itemCharStr, a);
            
            ArrayList<String> arrayList = expressionMap.get(alastChar);
            if (arrayList.contains("ε")) {
                
                return true;
            }
        }
        return false;

    }

    /**
     * 是否包含这种的字符串：非终结符后面跟着终结符
     * (2)Ab,=First(b)-ε,直接添加终结符
     *
     * @param itemCharStr
     * @param a
     * @return
     */										//非终结符集合	//ABT(产生式右部)		待查的非终结符 			
    public static boolean containsAb(TreeSet<Character> ntSet, String itemCharStr, Character a) {
        String aStr = a.toString();
        if (itemCharStr.contains(aStr)) {
            int aIndex = itemCharStr.indexOf(aStr);
            String findStr;  //查找“非终结符后面跟着终结符” 这样的字符串（其实是找终结符后面跟着的字符是否为终结符）
            try {
                findStr = itemCharStr.substring(aIndex + 1, aIndex + 2);
            } catch (Exception e) {
                return false;  //截取失败，则说明没有这样的字符串
            }
            if (ntSet.contains(findStr.charAt(0))) { //在终结符集合里面找到了
                return true;
            } else {
                return false;
            }
        } else { //没有这样的非终结符，自然就没有“非终结符后面跟着终结符” 这样的字符串，直接返回false
            return false;
        }
    }

    /**
     * @function 是否包含这种的字符串：两个非终结符连在一起的
     * (3).AB,=First(b)-ε
     *
     * @param itemCharStr
     * @param a
     * @return
     */									//非终结符集合	    //ABT(产生式右部)	     	待查的非终结符 			
    public static boolean containsAB(TreeSet<Character> nvSet, String itemCharStr, Character a) {
        String aStr = a.toString();
        if (itemCharStr.contains(aStr)) {
            int aIndex = itemCharStr.indexOf(aStr);
            String findStr;
            try {
                findStr = itemCharStr.substring(aIndex + 1, aIndex + 2);
            } catch (Exception e) {
                return false;
            }
            if (nvSet.contains(findStr.charAt(0))) { // 在非终结符里面找到了
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * @function 获取A后的字符：
     * 如果是 "非终结符后面跟着终结符" 这种形式的字符串，则把这个终结符添加到这个非终结符的Follow集合中，
     * 所以在这里需要获取这个终结符
     * @param itemCharStr
     * @param a
     * @return
     */
    public static Character getAlastChar(String itemCharStr, Character a) {
        String aStr = a.toString();
        if (itemCharStr.contains(aStr)) {
            int aIndex = itemCharStr.indexOf(aStr);
            String findStr = "";
            try {
                findStr = itemCharStr.substring(aIndex + 1, aIndex + 2);
            } catch (Exception e) {
                return null;
            }
            return findStr.charAt(0);
        }
        return null;
    }

    /**
     * 是否为ε开始的
     *
     * @param selectExp
     * @return
     */
    public static boolean isEmptyStart(String selectExp) {
        char charAt = selectExp.charAt(0);
        if (charAt == 'ε') {
            return true;
        }
        return false;
    }

    /**
     * 是否是终结符开始的
     *
     * @param ntSet
     * @param selectExp
     * @return
     */
    public static boolean isNtStart(TreeSet<Character> ntSet, String selectExp) {
        char charAt = selectExp.charAt(0);
        if (ntSet.contains(charAt)) {
            return true;
        }
        return false;
    }

    /**
     * 是否是非终结符开始的
     *
     * @param nvSet
     * @param selectExp
     * @return
     */
    public static boolean isNvStart(TreeSet<Character> nvSet, String selectExp) {
        char charAt = selectExp.charAt(0);
        if (nvSet.contains(charAt)) {
            return true;
        }
        return false;
    }

    /**
     * 查找产生式:
     * 此函数的功能就是在select集合中查找当此非终结符遇到当前字符的时候选用哪个产生式 
     * @param selectMap
     * 			文法G的 可选集合（select 集合）    
     * @param peek
     *            当前Nv
     * @param charAt
     *            当前字符
     * @return
     */                                       //文法G的 可选集合（select 集合）                                                        // 当前的非终结符     // 当前字符
    public static String findUseExp(TreeMap<Character, HashMap<String, TreeSet<Character>>> selectMap, Character peek,  char charAt) {
    	/**
    	 * 此函数的功能就是在select集合中查找当此非终结符遇到当前字符的时候选用哪个产生式 
    	 */
    	
        try {
        	//获取此非终结符对应的产生式的对应的select集合
            HashMap<String, TreeSet<Character>> hashMap = selectMap.get(peek);
            Set<String> keySet = hashMap.keySet();  // 获取所有的产生式
            for (String useExp : keySet) { //遍历每个产生式
                TreeSet<Character> treeSet = hashMap.get(useExp); //获取这个产生式select集合里面的字符集
                if (treeSet.contains(charAt)) { // 如果包含这个字符
                    return useExp;  // 返回当前的产生式
                }
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}