package com.googlecode.ordbok3.translationData;


public class EnglishWord extends Word
{
	static String ksWordId = "en";
	
	static
	{
		WordBuilder.instance().registerWord(ksWordId, new EnglishWord());
	}
	
	@Override
    public void translateToChinese()
    {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public Word createWord()
    {
	    return new EnglishWord();
    }

}
