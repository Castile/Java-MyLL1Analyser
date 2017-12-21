package cn.zhuhongliang.LL1_SyntacticAnalyzer.util;

import java.util.List;
import java.util.TreeSet;

/**
 * LL1�ķ���һЩ������
 * 1.��First��
 * 2.��Follow��
 * 3.��select��
 * 4.��Ԥ�������
 * 
 * 2017��12��19�� 08:51:11
 * @author �����
 *
 */
public interface GrammarOperator {
	
	/**
	 * ��ȡ���ս�������ս����
	 * 
	 * @param gsArray
	 * @param nvSet �����ս��
	 * @param ntSet ���ս��
	 */
	public void getNvNt();
	
	
	/**
	 * ��ʼ�����ʽ����
	 */
	public void initExpressionMaps();
	
	

	/**
	 * ��ȡFirst��
	 */
	public void getFirst();
	
	/**
	 * ����First����
	 * 
	 * @param itemSet first���Ľ������
	 * @param charItem
	 * @param itemitemChar
	 * @return
	 */
	public boolean calcFirst(TreeSet<Character> itemSet, Character charItem, char itemitemChar);
	
	/**
	 * ��ȡFollow����
	 */
	public void getFollow();
	

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
			TreeSet<Character> itemSet);
	/**
	 * ��ȡSelect����
	 */
	public void getSelect();
	
	/**
	 * ����Ԥ�������
	 */
	public void genAnalyzeTable()throws Exception;
	
	
	
}
