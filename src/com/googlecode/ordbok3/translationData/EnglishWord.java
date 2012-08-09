package com.googlecode.ordbok3.translationData;

import com.googlecode.ordbok3.log.OrdbokLog;


public class EnglishWord extends Word
{
	private static final String LOG_TAG = "EnglishWord";
	static String ksWordId = "en";
	
	static
	{
		WordBuilder.instance().registerWord(ksWordId, new EnglishWord());
	}

	@Override
    public Word createWord()
    {
	    return new EnglishWord();
    }

	private String o_sChineseWordValue = "";
	
	public String getChineseWordValue()
    {
    	return o_sChineseWordValue;
    }

	@Override
    public void submitTranslatíonDate(TranslatorInterface ATranslator)
    {
	    // add word value to translator
		ATranslator.addEngWordToTranslate(o_sWordValue);
		
		// add English example to translator
		for (SentenceComposite example : o_ExampleList)
        {
	        ATranslator.addEngWordToTranslate(example.getOriginalSentence());
        }
	    
    }

	@Override
    public void getTranslateResultFromTranslator(TranslatorInterface ATranslator)
    {
		// get ChineseWordValue from  translator
		String sChineseWord = ATranslator.getChTranslationForEng(o_sWordValue);
		if (sChineseWord != null)
		{
			o_sChineseWordValue = sChineseWord;
		} else
		{
			OrdbokLog.e(LOG_TAG, "Can not find the translation for "
			        + o_sWordValue);
		}
		
		// get Chinese example from translator
		for (SentenceComposite example : o_ExampleList)
        {
			String sChineseSentence = ATranslator.getChTranslationForEng(example.getOriginalSentence());
	        if(sChineseSentence != null)
	        {
	        	example.setChineseTranslationSentence(sChineseSentence);
	        }
	        else
	        {
	        	OrdbokLog.e(LOG_TAG, "Can not find the translation for " + example.getTranslationSentence());
	        }
        }
	    
    }

}
