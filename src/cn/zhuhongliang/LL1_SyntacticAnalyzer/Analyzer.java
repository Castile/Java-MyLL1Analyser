package cn.zhuhongliang.LL1_SyntacticAnalyzer;

import java.util.ArrayList;
import java.util.Stack;

import cn.zhuhongliang.LL1_SyntacticAnalyzer.AnalyzeProduce;

import cn.zhuhongliang.LL1_SyntacticAnalyzer.util.TextUtils;

/**
 * 
 * @author 朱宏梁
 * 2017年12月18日 17:57:14
 * @function 句子分析器 
 */

public class Analyzer {

    public Analyzer() {
        super();
        analyzeStatck = new Stack<Character>();
        // 结束符进栈
        analyzeStatck.push('#');
    }

    private ArrayList<AnalyzeProduce> analyzeProduces;  //分析过程的集合

    /**
     * LL（1）文法
     */
    private Grammar ll1Gs;

    public Grammar getLl1Gs() {
        return ll1Gs;
    }

    public void setLl1Gs(Grammar ll1Gs) {
        this.ll1Gs = ll1Gs;
    }

    /**
     * 开始符
     */
    private Character startChar;

    /**
     * 分析栈
     */
    private Stack<Character> analyzeStatck;
    /**
     * 剩余输入串
     */
    private String str;
    /**
     * 推导所用产生或匹配
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
     * 分析
     */
    public void analyze() {
    	
    	System.out.println("\n\n");
        System.out.println("***********************分析过程****************************");
        analyzeProduces = new ArrayList<AnalyzeProduce>();

        // 开始符进栈
        analyzeStatck.push(startChar);
        System.out.println("开始符:" + startChar);
        System.out.println("步骤" + "\t\t\t\t\t" + "分析栈" + "\t\t\t\t\t" + "余留字符串" + "\t\t\t\t\t"
                + "所用产生式");
        int index = 0;
        // 开始分析
        // while (analyzeStatck.peek() != '#' && str.charAt(0) != '#') {
        while (!analyzeStatck.empty()) {
            index++;
            if (analyzeStatck.peek() != str.charAt(0)) {
                // 到分析表中找到这个产生式
                String nowUseExpStr = TextUtils.findUseExp(ll1Gs.getSelectMap(), analyzeStatck.peek(), str.charAt(0));
                System.out.println(index + "\t\t\t\t\t" + analyzeStatck.toString() + "\t\t\t\t\t" + str + "\t\t\t\t\t"
                        + analyzeStatck.peek() + "->" + nowUseExpStr);
                AnalyzeProduce produce = new AnalyzeProduce();
                produce.setIndex(index);
                produce.setAnalyzeStackStr(analyzeStatck.toString());
                produce.setStr(str);
                if (null == nowUseExpStr) {
                    produce.setUseExpStr("无法匹配!");              
                } else {
                    produce.setUseExpStr(analyzeStatck.peek() + "->" + nowUseExpStr);
                }
                analyzeProduces.add(produce);
                // 将之前的分析栈中的栈顶出栈
                analyzeStatck.pop();
                // 将要用到的表达式入栈,反序入栈
                if (null != nowUseExpStr && nowUseExpStr.charAt(0) != 'ε') {
                    for (int j = nowUseExpStr.length() - 1; j >= 0; j--) {
                        char currentChar = nowUseExpStr.charAt(j);
                        analyzeStatck.push(currentChar);
                    }
                }
                continue;
            }
            // 如果可以匹配,分析栈出栈，串去掉一位
            if (analyzeStatck.peek() == str.charAt(0)) {
                System.out.println(index + "\t\t\t\t\t" + analyzeStatck.toString() + "\t\t\t\t\t" + str + "\t\t\t\t\t" + "“"
                        + str.charAt(0) + "”匹配");
                AnalyzeProduce produce = new AnalyzeProduce();
                produce.setIndex(index);
                produce.setAnalyzeStackStr(analyzeStatck.toString());
                produce.setStr(str);
                produce.setUseExpStr("“" + str.charAt(0) + "”匹配");
                analyzeProduces.add(produce);
                analyzeStatck.pop();
                str = str.substring(1);
                continue;
            }
        }
		System.out.println("");
        System.out.println("分析成功！");
    }

}