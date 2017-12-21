package cn.zhuhongliang.LL1_SyntacticAnalyzer;

import java.io.Serializable;

/**
 * 
 * @author 朱宏梁
 * 2017年12月18日 17:55:42
 * @function 分析过程
 *
 */
public class AnalyzeProduce implements Serializable{
    private static final long serialVersionUID = 10L;
    private Integer index;  //步骤
    private String analyzeStackStr;  //分析栈
    private String str; // 余留字符串
    private String useExpStr; // 所用字符串

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getAnalyzeStackStr() {
        return analyzeStackStr;
    }

    public void setAnalyzeStackStr(String analyzeStackStr) {
        this.analyzeStackStr = analyzeStackStr;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public String getUseExpStr() {
        return useExpStr;
    }

    public void setUseExpStr(String useExpStr) {
        this.useExpStr = useExpStr;
    }

}