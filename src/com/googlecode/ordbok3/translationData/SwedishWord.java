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
		translator.initialTranslation();
		translator.addEngWordToTranslate("watch:see");
		translator.addEngWordToTranslate("test");
		translator.addEngWordToTranslate("Strange");
		translator.addEngWordToTranslate("the committee will look into the tax regulations.");
		translator.addEngWordToTranslate("watch TV.");
		translator.submitEngWordToTranslationServer();
	}

	@Override
    public Word createWord()
    {
	    return new SwedishWord();
    }
}
