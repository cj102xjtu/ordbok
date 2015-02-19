package com.googlecode.ordbok3.translationData;


import java.util.ArrayList;
import java.util.List;

import com.googlecode.ordbok3.log.OrdbokLog;


public class SwedishWord extends Word
{
	private static final long serialVersionUID = 7774758050011701825L;

	protected SwedishWord()
    {
		super();
    }

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
		// add all English word in o_TranslationList to translator. 
		for (String sEnglishWord : o_TranslationList)
        {
	        ATranslator.addEngWordToTranslate(sEnglishWord);
        }
		
		// add all English example to translator
		for (SentenceComposite example : o_ExampleList)
        {
	        ATranslator.addEngWordToTranslate(example.getTranslationSentence());
        }
		
		// add all English compound to translator
		for (SentenceComposite compound : o_CompoundList)
        {
	        ATranslator.addEngWordToTranslate(compound.getTranslationSentence());
        }
		
		// add all English idiom to translator
		for (SentenceComposite idiom : o_IdiomList)
        {
	        ATranslator.addEngWordToTranslate(idiom.getTranslationSentence());
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
	    for (SentenceComposite example : o_ExampleList)
        {
	        String sChineseSentence = ATranslator.getChTranslationForEng(example.getTranslationSentence());
	        if(sChineseSentence != null)
	        {
	        	example.setChineseTranslationSentence(sChineseSentence);
	        }
	        else
	        {
	        	OrdbokLog.e(LOG_TAG, "Can not find the translation for " + example.getTranslationSentence());
	        }
        }
	    
	    // get the translation of the compound, which contain the English translation of 
	    // compound sentence.
	    for (SentenceComposite compound : o_CompoundList)
        {
	        String sChineseSentence = ATranslator.getChTranslationForEng(compound.getTranslationSentence());
	        if(sChineseSentence != null)
	        {
	        	compound.setChineseTranslationSentence(sChineseSentence);
	        }
	        else
	        {
	        	OrdbokLog.e(LOG_TAG, "Can not find the translation for " + compound.getTranslationSentence());
	        }
        }
	    
	    // get the translation of the idiom, which contain the English translation of 
	    // idiom sentence.
	    for (SentenceComposite idiom : o_IdiomList)
        {
	        String sChineseSentence = ATranslator.getChTranslationForEng(idiom.getTranslationSentence());
	        if(sChineseSentence != null)
	        {
	        	idiom.setChineseTranslationSentence(sChineseSentence);
	        }
	        else
	        {
	        	OrdbokLog.e(LOG_TAG, "Can not find the translation for " + idiom.getTranslationSentence());
	        }
        }
	        
    }
	
	private void setChineseTranslationList(String sAChinese)
	{
		this.o_ChineseTranslationList.add(sAChinese);
	}
}
