package cn.zhuhongliang.LL1_SyntacticAnalyzer.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

import cn.zhuhongliang.LL1_SyntacticAnalyzer.Grammar;

/**
 * 文件读取的工具类
 * 2017年12月20日 18:38:41
 * @author 朱宏梁
 *
 */
public class FileUtils {
	
	/**
	 * 读取文件中的LL1文法产生式
	 * @param filein 文件的输入流
	 * @return 文法产生式集合
	 * @throws Exception
	 */
	public static  ArrayList<String> ReadLL1(InputStream filein) throws Exception {
		ArrayList<String> gsArray = new ArrayList<String>();
		
		InputStreamReader reader = new InputStreamReader(filein);
		
		BufferedReader br = new BufferedReader(reader);
		
		String ExpStr = null;
		while((ExpStr = br.readLine())!=null){
			gsArray.add(ExpStr);
		}
		//关闭资源
		br.close();
		
		return gsArray;
	}
	
	/**
	 * LL1文法对象序列化过程  
	 * @param gs
	 */
	public static void outLL1ToFile(Grammar gs,String fileName){
		 //对象序列化过程  
		FileOutputStream fos = null ;
		ObjectOutputStream oos = null ;
		try {
			 File file = new File("H:\\我的文件\\workspace\\MyLL1Analyser_v3\\LL1Grammar\\"+fileName);  
			 if(!file.exists()){
				 file.createNewFile();
			 }
			fos = new FileOutputStream(file);
			oos = new ObjectOutputStream(fos);  
			   oos.writeObject(gs);  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				oos.flush();
				oos.close();  
				fos.close(); 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
			
		}
	 
		
	}
	
	/**
	 * LL1文法对象反序列化过程  
	 * @param gs
	 */
	public static Grammar fileToLL1(String fileName){
		 //Student对象反序列化过程  
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		
		Grammar gs = null;
		try {
			File file = new File("H:\\我的文件\\workspace\\MyLL1Analyser_v3\\LL1Grammar\\"+fileName);  
			fis = new FileInputStream(file);  
			ois  = new ObjectInputStream(fis);  
			gs = (Grammar) ois.readObject();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				ois.close();
				fis.close();  
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		}
		return gs;
		   
		   
	}
	
	/**
	 * 从文件中读取分析字符串
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public static String readToken(InputStream token) throws Exception{
		InputStreamReader reader = new InputStreamReader(token);
		
		BufferedReader br = new BufferedReader(reader);
		
		int len=0;
		String tokenStr = null;
		char buf[] = new char[1024];
		while((len=br.read(buf)) !=-1){
			
			tokenStr = new String(buf, 0, len);
			
		}
		
		return tokenStr;
	}

	/**
	 * 用户自己输入要分析的字符串
	 * @return
	 */
	public static String userInputToken(){
		//输入要分析的句子
	     Scanner scanner = new Scanner(System.in);
	     System.out.println("请输入要分析的句子：");
	     String TokenStr  = scanner.next();
    
	     return TokenStr;
	}
	


}
