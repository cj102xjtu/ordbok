package com.googlecode.ordbok3.translationData;


public class SwedishWord extends Word
{
	static String ksWordId = "sv";
	
	static
	{
		WordBuilder.instance().registerWord(ksWordId, new SwedishWord());
	}
	
	@Override
	public void translateToChinese()
	{
		ChineseTranslator translator = ChineseTranslator.instance();
		String sTranslateResult = translator.translateToChinese("look");
		                
	}

	@Override
    public Word createWord()
    {
	    return new SwedishWord();
    }
}
