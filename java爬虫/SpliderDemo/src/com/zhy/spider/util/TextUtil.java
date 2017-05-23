package com.zhy.spider.util;

public class TextUtil
{
	public static  boolean isEmpty(String str)
	{
		if(str == null || str.trim().length() == 0)
		{
			return true ;
		}
		return false ;
	}
}
