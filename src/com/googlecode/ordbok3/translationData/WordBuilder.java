package com.googlecode.ordbok3.translationData;

import java.util.HashMap;

public class WordBuilder extends WordSkeleton
{
	private WordBuilder()
    {
    }


	private HashMap<String, WordSkeleton> o_RegisteredWords = new HashMap<String, WordSkeleton>();

	// Singleton
	static private WordBuilder instance;

	static public WordBuilder instance()
	{
		if (instance == null)
		{
			instance = new WordBuilder();
		}

		return instance;

	}

	public void registerWord(String sAWordID, Word p)
	{
		o_RegisteredWords.put(sAWordID, p);
	}

	// factory function for word
	public Word generateWord()
	{
		// The code below is the register the SwedishWord EnglishWord class to system.
		@SuppressWarnings("unused")
        String test = SwedishWord.ksWordId;
		test = EnglishWord.ksWordId;
		
		
		Word word = null;
		if (o_RegisteredWords.get(o_sLang) != null)
		{
			word = ((Word) o_RegisteredWords.get(o_sLang)).createWord();			
			word.copy(this);

			// clear the builder to create another word
			clear();
		}
		return word;
	}


}
