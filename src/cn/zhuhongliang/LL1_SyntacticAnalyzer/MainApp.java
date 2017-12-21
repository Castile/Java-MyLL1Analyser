package cn.zhuhongliang.LL1_SyntacticAnalyzer;

import cn.zhuhongliang.LL1_SyntacticAnalyzer.util.FileUtils;
import cn.zhuhongliang.LL1_SyntacticAnalyzer.util.PrintUtils;

/**
 * 
 * @author �����
 * 2017��12��18�� 17:56:28
 *  Main: ������
 *
 */
public class MainApp {
	
    public static void main(String[] args) throws Exception {

        //��ȡ�ķ�����-----> LL1�ķ�����ķ����л�
    	Grammar gs = FileUtils.fileToLL1("LL1Grammar_2.txt");
    
    	/**
    	 * ��ӡFirst��Follow��Select����
    	 */
    	PrintUtils.printFirst(gs.getFirstMap());
    	PrintUtils.printFollow(gs.getFollowMap());
    	PrintUtils.printSelect(gs.getSelectMap());
    	
        // ����һ��������
        Analyzer analyzer = new Analyzer();
        analyzer.setLl1Gs(gs);
        analyzer.setStartChar('S');
        
        String TokenStr = FileUtils.userInputToken();
      
       //���þ���
        analyzer.setStr(TokenStr+"#");
        gs.genAnalyzeTable();
       
        analyzer.analyze();
      
        System.out.println("");
    }
}
