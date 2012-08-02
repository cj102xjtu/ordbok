package com.googlecode.ordbok3.translationData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Word
{
	String o_sLang = "";
	String o_sPhoneticSoundFile = "";
	String o_sPhoneticValue = "";
	String o_sWordClass = "";
	String o_sWordValue = "";
	List<Example> o_ExampleList = new ArrayList<Example>();
	List<String> o_ParadigmList = new ArrayList<String>();
	List<String> o_SynonymList = new ArrayList<String>();
	List<String> o_TranslationList = new ArrayList<String>();

	// factory function for word
	static public Word createWord(String sALang)
	{
		Word returnWord = null;
		if (sALang.equals("en"))
		{
			returnWord = new EnglishWord();
		}

		if (sALang.equals("se"))
		{
			returnWord = new SwedishWord();
		}

		return returnWord;
	}

	public Word(Word AWord)
	{
		o_sLang = AWord.o_sLang;
		o_sPhoneticSoundFile = AWord.o_sPhoneticSoundFile;
		o_sPhoneticValue = AWord.o_sPhoneticValue;
		o_sWordClass = AWord.o_sWordClass;
		o_sWordValue = AWord.o_sWordValue;

		o_TranslationList = new ArrayList<String>(AWord.o_TranslationList);
		Collections.copy(o_TranslationList, AWord.o_TranslationList);

		o_ExampleList = new ArrayList<Example>(AWord.o_ExampleList);
		Collections.copy(o_ExampleList, AWord.o_ExampleList);

		o_ParadigmList = new ArrayList<String>(AWord.o_ParadigmList);
		Collections.copy(o_ParadigmList, AWord.o_ParadigmList);

		o_SynonymList = new ArrayList<String>(AWord.o_SynonymList);
		Collections.copy(o_SynonymList, AWord.o_SynonymList);
	}

	public Word()
	{
		// TODO Auto-generated constructor stub
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

	public void clear()
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

	abstract public void translateToChinese();
	abstract public Word createWord();

}
