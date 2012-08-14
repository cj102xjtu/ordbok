package com.googlecode.ordbok3.translationData;


public class SentenceComposite
{
	private String o_sOriginalSentence;
	private String o_sTranslationSentence;
	private String o_sChineseTranslationSentence;
	
	public String getChineseTranslationSentence()
    {
    	return o_sChineseTranslationSentence;
    }

	public void setChineseTranslationSentence(String sAChineseTranslationSentence)
    {
    	this.o_sChineseTranslationSentence = sAChineseTranslationSentence;
    }

	public SentenceComposite(String sAOrignalSentence)
	{
		o_sOriginalSentence = sAOrignalSentence;
	}

	public String getOriginalSentence()
    {
    	return o_sOriginalSentence;
    }
	public void setOriginalSentence(String sAOriginalSentence)
    {
    	this.o_sOriginalSentence = sAOriginalSentence;
    }
	public String getTranslationSentence()
    {
    	return o_sTranslationSentence;
    }
	public void setTranslationSentence(String sATranslationSentence)
    {
    	this.o_sTranslationSentence = sATranslationSentence;
    }
	
}