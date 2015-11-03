/**
 * 
 */
package com.yfsy.gems.util;

/**
 * @author Administrator
 *
 */
public class StringUtil {

	public static String[] split(String source ,String flag){
		String[] denString = null ;
		
		if(source != null && source.length()>0){
			source = source.substring((source.indexOf(flag)+1));
			//System.out.println(source);
			denString = source.split(flag);
			//System.out.println(denString.length);
		}
		return denString ;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	   String source = "$36$67$89";
	   String[] denString = split(source,"$");
	   for(int i = 0 ; i< denString.length;i++){
		   System.out.println(denString[i]);
	   }
	}

}
