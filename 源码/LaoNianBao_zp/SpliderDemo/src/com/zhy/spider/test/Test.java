package com.zhy.spider.test;

import java.util.List;

import com.zhy.spider.bean.LinkTypeData;
import com.zhy.spider.core.ExtractService;
import com.zhy.spider.rule.Rule;

public class Test
{
	@org.junit.Test
	public static void getDatasMovie()
	{
		Rule rule = new Rule("http://www.1905.com/",  
                new String[] { "name" }, new String[] { "" },  
                null, -1, Rule.GET); 
		List<LinkTypeData> extracts = ExtractService.extract(rule);
		printf4(extracts);
	}
	public static void printf4(List<LinkTypeData> datas)
	{
		//打印出所爬信息
         for (LinkTypeData data : datas)
  		{
        	 if((data.getLinkText().indexOf("《")!=-1||data.getLinkText().indexOf("》")!=-1
 				)&&data.getLinkText().indexOf("网")==-1)
			{
				System.out.println(data.getLinkText());
				System.out.println(data.getLinkHref());
				System.out.println("-----------------------------");
			}
		}

	}
	@org.junit.Test
	public static void getDatasMusic()
	{
		Rule rule = new Rule("http://music.baidu.com/",  
                new String[] { "name" }, new String[] { "" },  
                null, -1, Rule.GET); 
		List<LinkTypeData> extracts = ExtractService.extract(rule);
		printf3(extracts);
	}
	public static void printf3(List<LinkTypeData> datas)
	{
		//打印出所爬信息
         for (LinkTypeData data : datas)
  		{
			if(data.getLinkText().indexOf("-")!=-1
				&&data.getLinkText().indexOf("网")==-1)
		{
			System.out.println(data.getLinkText());
			System.out.println("http://music.baidu.com" + data.getLinkHref().substring(0, 15));
			System.out.println("-----------------------------");
			}
		}

	}
	@org.junit.Test
	public static void getDatasByCssQuery()
	{
		Rule rule = new Rule("http://news.cctv.com/",  
                new String[] { "name" }, new String[] { "" },  
                null, -1, Rule.GET);
		List<LinkTypeData> extracts = ExtractService.extract(rule);
		
		printf2(extracts);
	}
	public static void printf2(List<LinkTypeData> datas)
	{
		//打印出所爬信息
		for (LinkTypeData data : datas)
		{
			if((data.getLinkText().indexOf("国")!=-1||data.getLinkText().indexOf("药")!=-1
					||data.getLinkText().indexOf("互联网")!=-1||data.getLinkText().indexOf("居民")!=-1
					||data.getLinkText().indexOf("领导")!=-1||data.getLinkText().indexOf("梦")!=-1
				)&&data.getLinkText().indexOf("网")==-1 && data.getLinkHref().indexOf(".shtml")!=-1)
			{
			System.out.println(data.getLinkText());
			System.out.println(data.getLinkHref());
			System.out.println("-----------------------------");
			}
		}

	}
	@org.junit.Test  
	public static void getDatasHealth()  
	{  
	    Rule rule = new Rule("http://health.china.com/",  
	            new String[] { "name" }, new String[] { "" },  
	            null, -1, Rule.GET);  
	    List<LinkTypeData> extracts = ExtractService.extract(rule);  
	    printf1(extracts);  
	} 
	public static void printf1(List<LinkTypeData> datas)
	{
		//打印出所爬信息
		for (LinkTypeData data : datas)
		{
			if((data.getLinkText().indexOf("养生")!=-1||data.getLinkText().indexOf("补钙")!=-1||data.getLinkText().indexOf("养胃")!=-1
				||data.getLinkText().indexOf("食物")!=-1||data.getLinkText().indexOf("健康")!=-1||data.getLinkText().indexOf("睡前")!=-1
				||data.getLinkText().indexOf("么")!=-1||data.getLinkText().indexOf("胖")!=-1||data.getLinkText().indexOf("不宜")!=-1
				||data.getLinkText().indexOf("药")!=-1||data.getLinkText().indexOf("医疗")!=-1
				)&&data.getLinkText().indexOf("网")==-1&&data.getLinkText().indexOf("堂")==-1&&data.getLinkHref().indexOf(".html")!=-1)
			{
			System.out.println(data.getLinkText());
			System.out.println("http://health.china.com" + data.getLinkHref());
			System.out.println("-----------------------------");
			}
		}

	}
	public static void main(String[] args) {
		//getDatasHealth();	
		getDatasByCssQuery();
		//getDatasMusic();
		//getDatasMovie();
	}
}

