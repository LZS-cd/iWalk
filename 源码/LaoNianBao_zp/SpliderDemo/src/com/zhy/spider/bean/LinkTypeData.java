package com.zhy.spider.bean;  
  
public class LinkTypeData  
{  
    private int id;  
    private String linkHref;  
    private String linkText;  
    private String summary;   
	private String content;
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getLinkHref()
	{
		return linkHref;
	}
	public void setLinkHref(String linkHref)
	{
		this.linkHref = linkHref;
	}
	public String getLinkText()
	{
		return linkText;
	}
	public void setLinkText(String linkText)
	{
		this.linkText = linkText;
	}
	public String getSummary()
	{
		return summary;
	}
	public void setSummary(String summary)
	{
		this.summary = summary;
	}
	public String getContent()
	{
		return content;
	}
	public void setContent(String content)
	{
		this.content = content;
	}
	@Override
	public String toString()
	{
		return "LinkTypeData [id=" + id + ", linkHref=" + linkHref
				+ ", linkText=" + linkText + ", summary=" + summary
				+ ", content=" + content + "]";
	}
	
	

}
