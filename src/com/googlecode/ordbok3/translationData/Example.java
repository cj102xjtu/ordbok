package com.googlecode.ordbok3.translationData;

public class Example
{
	private String o_sOriginalExample;
	private String o_sTranslationExample;
	
	public Example(String sAOrignalExample)
	{
		o_sOriginalExample = sAOrignalExample;
	}

	public String getOriginalExample()
    {
    	return o_sOriginalExample;
    }
	public void setOriginalExample(String sAOriginal)
    {
    	this.o_sOriginalExample = sAOriginal;
    }
	public String getTranslationExample()
    {
    	return o_sTranslationExample;
    }
	public void setTranslationExample(String sATranslation)
    {
    	this.o_sTranslationExample = sATranslation;
    }
	
}