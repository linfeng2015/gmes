package com.yfsy.gems.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lyf
 * 验证email 地址的合法性
 *
 */
public class CheckEmail {

	public static boolean isEmail(String emailStr){
		boolean isMatched = false ;
		//电子邮件  
		if(emailStr == null){
			return isMatched;
		}else{
			String check = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$"; 
			Pattern regex = Pattern.compile(check);  
			Matcher matcher = regex.matcher(emailStr);  
			isMatched = matcher.matches();  
		}
		return isMatched ;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("48446824@qq.com.cn:"+isEmail("48446824@qq.com.cn"));
		System.out.println("qz_lyfeng@qq.com.cn:"+isEmail("qz_lyfeng@qq.com.cn"));
		System.out.println("testqq.com.cn@:"+isEmail("testqq.com.cn@"));
	}
	

}
