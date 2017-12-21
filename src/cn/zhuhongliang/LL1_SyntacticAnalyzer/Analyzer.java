package cn.zhuhongliang.LL1_SyntacticAnalyzer;

import java.util.ArrayList;
import java.util.Stack;

import cn.zhuhongliang.LL1_SyntacticAnalyzer.AnalyzeProduce;

import cn.zhuhongliang.LL1_SyntacticAnalyzer.util.TextUtils;

/**
 * 
 * @author �����
 * 2017��12��18�� 17:57:14
 * @function ���ӷ����� 
 */

public class Analyzer {

    public Analyzer() {
        super();
        analyzeStatck = new Stack<Character>();
        // ��������ջ
        analyzeStatck.push('#');
    }

    private ArrayList<AnalyzeProduce> analyzeProduces;  //�������̵ļ���

    /**
     * LL��1���ķ�
     */
    private Grammar ll1Gs;

    public Grammar getLl1Gs() {
        return ll1Gs;
    }

    public void setLl1Gs(Grammar ll1Gs) {
        this.ll1Gs = ll1Gs;
    }

    /**
     * ��ʼ��
     */
    private Character startChar;

    /**
     * ����ջ
     */
    private Stack<Character> analyzeStatck;
    /**
     * ʣ�����봮
     */
    private String str;
    /**
     * �Ƶ����ò�����ƥ��
     */
    private String useExp;

    public ArrayList<AnalyzeProduce> getAnalyzeProduces() {
        return analyzeProduces;
    }

    public void setAnalyzeProduces(ArrayList<AnalyzeProduce> analyzeProduces) {
        this.analyzeProduces = analyzeProduces;
    }

    public Character getStartChar() {
        return startChar;
    }

    public void setStartChar(Character startChar) {
        this.startChar = startChar;
    }

    public Stack<Character> getAnalyzeStatck() {
        return analyzeStatck;
    }

    public void setAnalyzeStatck(Stack<Character> analyzeStatck) {
        this.analyzeStatck = analyzeStatck;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public String getUseExp() {
        return useExp;
    }

    public void setUseExp(String useExp) {
        this.useExp = useExp;
    }

    /**
     * ����
     */
    public void analyze() {
    	
    	System.out.println("\n\n");
        System.out.println("***********************��������****************************");
        analyzeProduces = new ArrayList<AnalyzeProduce>();

        // ��ʼ����ջ
        analyzeStatck.push(startChar);
        System.out.println("��ʼ��:" + startChar);
        System.out.println("����" + "\t\t\t\t\t" + "����ջ" + "\t\t\t\t\t" + "�����ַ���" + "\t\t\t\t\t"
                + "���ò���ʽ");
        int index = 0;
        // ��ʼ����
        // while (analyzeStatck.peek() != '#' && str.charAt(0) != '#') {
        while (!analyzeStatck.empty()) {
            index++;
            if (analyzeStatck.peek() != str.charAt(0)) {
                // �����������ҵ��������ʽ
                String nowUseExpStr = TextUtils.findUseExp(ll1Gs.getSelectMap(), analyzeStatck.peek(), str.charAt(0));
                System.out.println(index + "\t\t\t\t\t" + analyzeStatck.toString() + "\t\t\t\t\t" + str + "\t\t\t\t\t"
                        + analyzeStatck.peek() + "->" + nowUseExpStr);
                AnalyzeProduce produce = new AnalyzeProduce();
                produce.setIndex(index);
                produce.setAnalyzeStackStr(analyzeStatck.toString());
                produce.setStr(str);
                if (null == nowUseExpStr) {
                    produce.setUseExpStr("�޷�ƥ��!");              
                } else {
                    produce.setUseExpStr(analyzeStatck.peek() + "->" + nowUseExpStr);
                }
                analyzeProduces.add(produce);
                // ��֮ǰ�ķ���ջ�е�ջ����ջ
                analyzeStatck.pop();
                // ��Ҫ�õ��ı��ʽ��ջ,������ջ
                if (null != nowUseExpStr && nowUseExpStr.charAt(0) != '��') {
                    for (int j = nowUseExpStr.length() - 1; j >= 0; j--) {
                        char currentChar = nowUseExpStr.charAt(j);
                        analyzeStatck.push(currentChar);
                    }
                }
                continue;
            }
            // �������ƥ��,����ջ��ջ����ȥ��һλ
            if (analyzeStatck.peek() == str.charAt(0)) {
                System.out.println(index + "\t\t\t\t\t" + analyzeStatck.toString() + "\t\t\t\t\t" + str + "\t\t\t\t\t" + "��"
                        + str.charAt(0) + "��ƥ��");
                AnalyzeProduce produce = new AnalyzeProduce();
                produce.setIndex(index);
                produce.setAnalyzeStackStr(analyzeStatck.toString());
                produce.setStr(str);
                produce.setUseExpStr("��" + str.charAt(0) + "��ƥ��");
                analyzeProduces.add(produce);
                analyzeStatck.pop();
                str = str.substring(1);
                continue;
            }
        }
		System.out.println("");
        System.out.println("�����ɹ���");
    }

}