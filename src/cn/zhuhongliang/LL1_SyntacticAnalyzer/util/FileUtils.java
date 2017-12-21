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
 * �ļ���ȡ�Ĺ�����
 * 2017��12��20�� 18:38:41
 * @author �����
 *
 */
public class FileUtils {
	
	/**
	 * ��ȡ�ļ��е�LL1�ķ�����ʽ
	 * @param filein �ļ���������
	 * @return �ķ�����ʽ����
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
		//�ر���Դ
		br.close();
		
		return gsArray;
	}
	
	/**
	 * LL1�ķ��������л�����  
	 * @param gs
	 */
	public static void outLL1ToFile(Grammar gs,String fileName){
		 //�������л�����  
		FileOutputStream fos = null ;
		ObjectOutputStream oos = null ;
		try {
			 File file = new File("H:\\�ҵ��ļ�\\workspace\\MyLL1Analyser_v3\\LL1Grammar\\"+fileName);  
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
	 * LL1�ķ��������л�����  
	 * @param gs
	 */
	public static Grammar fileToLL1(String fileName){
		 //Student�������л�����  
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		
		Grammar gs = null;
		try {
			File file = new File("H:\\�ҵ��ļ�\\workspace\\MyLL1Analyser_v3\\LL1Grammar\\"+fileName);  
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
	 * ���ļ��ж�ȡ�����ַ���
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
	 * �û��Լ�����Ҫ�������ַ���
	 * @return
	 */
	public static String userInputToken(){
		//����Ҫ�����ľ���
	     Scanner scanner = new Scanner(System.in);
	     System.out.println("������Ҫ�����ľ��ӣ�");
	     String TokenStr  = scanner.next();
    
	     return TokenStr;
	}
	


}
