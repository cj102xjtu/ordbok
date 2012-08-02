package com.googlecode.ordbok3.translationData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class WordBuilder
{
	private String o_sLang = "";
	private String o_sPhoneticSoundFile = "";
	private String o_sPhoneticValue = "";
	private String o_sWordClass = "";
	private String o_sWordValue = "";
	private List<Example> o_ExampleList = new ArrayList<Example>();
	private List<String> o_ParadigmList = new ArrayList<String>();
	private List<String> o_SynonymList = new ArrayList<String>();
	private List<String> o_TranslationList = new ArrayList<String>();

	private HashMap<String, Word> o_RegisteredWords = new HashMap<String, Word>();

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

	private WordBuilder()
	{
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
			word.o_sLang = o_sLang;
			word.o_sPhoneticSoundFile = o_sPhoneticSoundFile;
			word.o_sPhoneticValue = o_sPhoneticValue;
			word.o_sWordClass = o_sWordClass;
			word.o_sWordValue = o_sWordValue;

			word.o_TranslationList = new ArrayList<String>(o_TranslationList);
			Collections.copy(word.o_TranslationList, o_TranslationList);

			word.o_ExampleList = new ArrayList<Example>(o_ExampleList);
			Collections.copy(word.o_ExampleList, o_ExampleList);

			word.o_ParadigmList = new ArrayList<String>(o_ParadigmList);
			Collections.copy(word.o_ParadigmList, o_ParadigmList);

			word.o_SynonymList = new ArrayList<String>(o_SynonymList);
			Collections.copy(word.o_SynonymList, o_SynonymList);

			// clear the builder to create another word
			clear();
		}
		return word;
	}

	public void addTranslation(String translation)
	{
		o_TranslationList.add(translation);
	}

	public List<Example> getExampleList()
	{
		return o_ExampleList;
	}

	public String getLang()
	{
		return o_sLang;
	}

	public List<String> getParadigmList()
	{
		return o_ParadigmList;
	}

	public String getPhoneticSoundFile()
	{
		return o_sPhoneticSoundFile;
	}

	public String getPhoneticValue()
	{
		return o_sPhoneticValue;
	}

	public List<String> getSynonymList()
	{
		return o_SynonymList;
	}

	public List<String> getTranslationList()
	{
		return o_TranslationList;
	}

	public String getWordClass()
	{
		return o_sWordClass;
	}

	public String getWordValue()
	{
		return o_sWordValue;
	}

	public void addExample(Example AExample)
	{
		this.o_ExampleList.add(AExample);
	}

	public void setLang(String lang)
	{
		this.o_sLang = lang;
	}

	public void addParadigms(String inflection)
	{
		this.o_ParadigmList.add(inflection);
	}

	public void setPhoneticSoundFile(String phoneticSoundFile)
	{
		this.o_sPhoneticSoundFile = phoneticSoundFile;
	}

	public void setPhoneticValue(String phoneticValue)
	{
		this.o_sPhoneticValue = phoneticValue;
	}

	public void setSynonym(String synonym)
	{
		this.o_SynonymList.add(synonym);
	}

	public void setWordClass(String wordClass)
	{
		if (wordClass.equals("nn"))
		{
			this.o_sWordClass = "substantiv,";
			return;
		} else if (wordClass.equals("vb"))
		{
			this.o_sWordClass = "verb,";
			return;
		} else if (wordClass.equals("jj"))
		{
			this.o_sWordClass = "adjektiv,";
			return;
		} else if (wordClass.equals("ab"))
		{
			this.o_sWordClass = "adverb,";
			return;
		} else if (wordClass.equals("kn"))
		{
			this.o_sWordClass = "konjunktion,";
			return;
		} else if (wordClass.equals("pp"))
		{
			this.o_sWordClass = "preposition,";
			return;
		} else if (wordClass.equals("in"))
		{
			this.o_sWordClass = "interjection,";
			return;
		} else if (wordClass.equals("pn"))
		{
			this.o_sWordClass = "pronomen,";
			return;
		} else if (wordClass.equals("abbrev"))
		{
			this.o_sWordClass = "fšrkortning,";
			return;
		}
		this.o_sWordClass = wordClass;
	}

	public void setWordValue(String wordValue)
	{
		this.o_sWordValue = wordValue;
	}

	private void clear()
	{
		o_sLang = "";
		o_sPhoneticSoundFile = "";
		o_sPhoneticValue = "";
		o_sWordClass = "";
		o_sWordValue = "";
		o_ExampleList.clear();
		o_ParadigmList.clear();
		o_SynonymList.clear();
		o_TranslationList.clear();
	}

}
