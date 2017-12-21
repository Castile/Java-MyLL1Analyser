package cn.zhuhongliang.LL1_SyntacticAnalyzer;

import cn.zhuhongliang.LL1_SyntacticAnalyzer.util.FileUtils;
import cn.zhuhongliang.LL1_SyntacticAnalyzer.util.PrintUtils;

/**
 * 
 * @author 朱宏梁
 * 2017年12月18日 17:56:28
 *  Main: 主程序
 *
 */
public class MainApp {
	
    public static void main(String[] args) throws Exception {

        //读取文法对象-----> LL1文法对象的反序列化
    	Grammar gs = FileUtils.fileToLL1("LL1Grammar_2.txt");
    
    	/**
    	 * 打印First、Follow、Select集合
    	 */
    	PrintUtils.printFirst(gs.getFirstMap());
    	PrintUtils.printFollow(gs.getFollowMap());
    	PrintUtils.printSelect(gs.getSelectMap());
    	
        // 创建一个分析器
        Analyzer analyzer = new Analyzer();
        analyzer.setLl1Gs(gs);
        analyzer.setStartChar('S');
        
        String TokenStr = FileUtils.userInputToken();
      
       //设置句子
        analyzer.setStr(TokenStr+"#");
        gs.genAnalyzeTable();
       
        analyzer.analyze();
      
        System.out.println("");
    }
}
