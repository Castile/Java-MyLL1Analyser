package cn.zhuhongliang.LL1_SyntacticAnalyzer.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.TreeSet;

public class PrintUtils {
	
	/**
	 * 打印此文法的First集合
	 * 
	 */


	public static void printFirst(HashMap<Character, TreeSet<Character>> firstMap){
		System.out.println("First集合：");
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		Iterator<Entry<Character, TreeSet<Character>>> iter = firstMap.entrySet().iterator();
		while(iter.hasNext()){
			Entry<Character, TreeSet<Character>> entry = iter.next();
			Character key = (Character) entry.getKey();
			TreeSet<Character> set = (TreeSet<Character>) entry.getValue();
			String first="";
			for(Character value : set){
				first+=value.toString()+",";
			}
			System.out.println("First("+key+")={"+first+"}");
		}
		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
	}

	/**
	 * 打印Follow集合
	 * @param followMap
	 */
	public static void printFollow(HashMap<Character, TreeSet<Character>> followMap){
		System.out.println("Follow集合：");
	    System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		Iterator<Entry<Character, TreeSet<Character>>> iter = followMap.entrySet().iterator();
		while(iter.hasNext()){
			Entry<Character, TreeSet<Character>> entry = iter.next();
			Character key = (Character) entry.getKey();
			TreeSet<Character> set = (TreeSet<Character>) entry.getValue();
			String follow="";
			for(Character value : set){
				follow+=value.toString()+",";
			}
			System.out.println("Follow("+key+")={"+follow+"}");
		}
		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
	}
	
	
	/**
	 * 打印select集合
	 * @param selectMap
	 */
	public static void printSelect(TreeMap<Character, HashMap<String, TreeSet<Character>>> selectMap){
		System.out.println("Select集合：");
	    System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
	    Iterator<Entry<Character, HashMap<String, TreeSet<Character>>>> iter = selectMap.entrySet().iterator();
	    while(iter.hasNext()){
	    	Entry<Character, HashMap<String, TreeSet<Character>>> entry = iter.next();
	    	Character key = entry.getKey();
	    	HashMap<String, TreeSet<Character>> hashMap = entry.getValue();
	    	Iterator<Entry<String, TreeSet<Character>>> it = hashMap.entrySet().iterator();
	    	while(it.hasNext()){
	    		Entry<String, TreeSet<Character>> selectEntry = it.next();
	    		String ExpStr = selectEntry.getKey();
	    		TreeSet<Character> selectSet = (TreeSet<Character>)selectEntry.getValue();
	    		String select ="";
	    		for(Character value : selectSet){
	    			select+=value.toString()+",";
	    		}
	    		
	    		System.out.println("Select("+""+key+"->"+ExpStr+")={"+select+"}");
	    	}
	    	
	    }
	    
	    System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
	}
	
}