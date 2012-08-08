package com.googlecode.ordbok3.translationData;


import java.util.ArrayList;
import java.util.List;

import com.googlecode.ordbok3.log.OrdbokLog;


public class SwedishWord extends Word
{
	// register SwedishWord class
	static String ksWordId = "sv";
	static
	{
		WordBuilder.instance().registerWord(ksWordId, new SwedishWord());
	}
	
	@Override
    public Word createWord()
    {
	    return new SwedishWord();
    }
	
	static private String LOG_TAG = "SwedishWord";
	private List<String> o_ChineseTranslationList = new ArrayList<String>();

	public List<String> getChineseTranslationList()
    {
    	return o_ChineseTranslationList;
    }


	@Override
    public void submitTranslatíonDate(TranslatorInterface ATranslator)
    {
		// test code.
//		ATranslator.initialTranslation();
//		ATranslator.addEngWordToTranslate("watch:see");
//		ATranslator.addEngWordToTranslate("test");
//		ATranslator.addEngWordToTranslate("Strange");
//		ATranslator.addEngWordToTranslate("the committee will look into the tax regulations.");
//		ATranslator.addEngWordToTranslate("watch TV.");	
		
		// add all English word in o_TranslationList to translator. 
		for (String sEnglishWord : o_TranslationList)
        {
	        ATranslator.addEngWordToTranslate(sEnglishWord);
        }
		
		// add all English example to translator
		for (Example example : o_ExampleList)
        {
	        ATranslator.addEngWordToTranslate(example.getTranslationExample());
        }
    }
	
	@Override
    public void getTranslateResultFromTranslator(TranslatorInterface ATranslator)
    {
		
		// get the translation of translation list, which contain the English translation of 
		// this Swedish word
	    for (String sEnglishWord : o_TranslationList)
        {
	        String sChineseWord = ATranslator.getChTranslationForEng(sEnglishWord);
	        if(sChineseWord != null)
	        {
	        	setChineseTranslationList(sChineseWord);	        	
	        }
	        else
	        {
	        	OrdbokLog.e(LOG_TAG, "Can not find the translation for " + sEnglishWord);
	        }
        }
	    
	    // get the translation of the example, which contain the English translation of 
	    // example sentence.
	    for (Example example : o_ExampleList)
        {
	        String sChineseSentence = ATranslator.getChTranslationForEng(example.getTranslationExample());
	        if(sChineseSentence != null)
	        {
	        	example.setChineseTranslationExample(sChineseSentence);
	        }
	        else
	        {
	        	OrdbokLog.e(LOG_TAG, "Can not find the translation for " + example.getTranslationExample());
	        }
        }
	        
    }
	
	private void setChineseTranslationList(String sAChinese)
	{
		this.o_ChineseTranslationList.add(sAChinese);
	}
}
