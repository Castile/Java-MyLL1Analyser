package cn.zhuhongliang.LL1_SyntacticAnalyzer.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;


/**
 * �ַ�������
 * 2017��12��18�� 17:58:29
 * @author �����
 *
 */
public class TextUtils {
    /**
     * 
     ** �Է��ս����β�ĵ�һ����ʽ ��B->aA,=Follow(B)
     * ��Follow(B) ��ӵ� Follow��A����
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
     * �Է��ս����β�ĵڶ�����ʽ ������aBb,b=��
     * ���磺A->aBa
     * ��Follow(A) ��ӵ� Follow��B����
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
            if (arrayList.contains("��")) {
                
                return true;
            }
        }
        return false;

    }

    /**
     * �Ƿ�������ֵ��ַ��������ս����������ս��
     * (2)Ab,=First(b)-��,ֱ������ս��
     *
     * @param itemCharStr
     * @param a
     * @return
     */										//���ս������	//ABT(����ʽ�Ҳ�)		����ķ��ս�� 			
    public static boolean containsAb(TreeSet<Character> ntSet, String itemCharStr, Character a) {
        String aStr = a.toString();
        if (itemCharStr.contains(aStr)) {
            int aIndex = itemCharStr.indexOf(aStr);
            String findStr;  //���ҡ����ս����������ս���� �������ַ�������ʵ�����ս��������ŵ��ַ��Ƿ�Ϊ�ս����
            try {
                findStr = itemCharStr.substring(aIndex + 1, aIndex + 2);
            } catch (Exception e) {
                return false;  //��ȡʧ�ܣ���˵��û���������ַ���
            }
            if (ntSet.contains(findStr.charAt(0))) { //���ս�����������ҵ���
                return true;
            } else {
                return false;
            }
        } else { //û�������ķ��ս������Ȼ��û�С����ս����������ս���� �������ַ�����ֱ�ӷ���false
            return false;
        }
    }

    /**
     * @function �Ƿ�������ֵ��ַ������������ս������һ���
     * (3).AB,=First(b)-��
     *
     * @param itemCharStr
     * @param a
     * @return
     */									//���ս������	    //ABT(����ʽ�Ҳ�)	     	����ķ��ս�� 			
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
            if (nvSet.contains(findStr.charAt(0))) { // �ڷ��ս�������ҵ���
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * @function ��ȡA����ַ���
     * ����� "���ս����������ս��" ������ʽ���ַ������������ս����ӵ�������ս����Follow�����У�
     * ������������Ҫ��ȡ����ս��
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
     * �Ƿ�Ϊ�ſ�ʼ��
     *
     * @param selectExp
     * @return
     */
    public static boolean isEmptyStart(String selectExp) {
        char charAt = selectExp.charAt(0);
        if (charAt == '��') {
            return true;
        }
        return false;
    }

    /**
     * �Ƿ����ս����ʼ��
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
     * �Ƿ��Ƿ��ս����ʼ��
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
     * ���Ҳ���ʽ:
     * �˺����Ĺ��ܾ�����select�����в��ҵ��˷��ս��������ǰ�ַ���ʱ��ѡ���ĸ�����ʽ 
     * @param selectMap
     * 			�ķ�G�� ��ѡ���ϣ�select ���ϣ�    
     * @param peek
     *            ��ǰNv
     * @param charAt
     *            ��ǰ�ַ�
     * @return
     */                                       //�ķ�G�� ��ѡ���ϣ�select ���ϣ�                                                        // ��ǰ�ķ��ս��     // ��ǰ�ַ�
    public static String findUseExp(TreeMap<Character, HashMap<String, TreeSet<Character>>> selectMap, Character peek,  char charAt) {
    	/**
    	 * �˺����Ĺ��ܾ�����select�����в��ҵ��˷��ս��������ǰ�ַ���ʱ��ѡ���ĸ�����ʽ 
    	 */
    	
        try {
        	//��ȡ�˷��ս����Ӧ�Ĳ���ʽ�Ķ�Ӧ��select����
            HashMap<String, TreeSet<Character>> hashMap = selectMap.get(peek);
            Set<String> keySet = hashMap.keySet();  // ��ȡ���еĲ���ʽ
            for (String useExp : keySet) { //����ÿ������ʽ
                TreeSet<Character> treeSet = hashMap.get(useExp); //��ȡ�������ʽselect����������ַ���
                if (treeSet.contains(charAt)) { // �����������ַ�
                    return useExp;  // ���ص�ǰ�Ĳ���ʽ
                }
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}