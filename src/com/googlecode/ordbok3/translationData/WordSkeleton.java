package com.googlecode.ordbok3.translationData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WordSkeleton
{
	String o_sLang = "";
	String o_sPhoneticSoundFile = "";
	String o_sPhoneticValue = "";
	String o_sWordClass = "";
	String o_sWordValue = "";
	List<SentenceComposite> o_ExampleList = new ArrayList<SentenceComposite>();
	List<SentenceComposite> o_CompoundList = new ArrayList<SentenceComposite>();
	List<SentenceComposite> o_IdiomList = new ArrayList<SentenceComposite>();
	List<String> o_ParadigmList = new ArrayList<String>();
	List<String> o_SynonymList = new ArrayList<String>();
	List<String> o_TranslationList = new ArrayList<String>();

	public void copy(WordSkeleton AWordSkeleton)
	{
		o_sLang = AWordSkeleton.o_sLang;
		o_sPhoneticSoundFile = AWordSkeleton.o_sPhoneticSoundFile;
		o_sPhoneticValue = AWordSkeleton.o_sPhoneticValue;
		o_sWordClass = AWordSkeleton.o_sWordClass;
		o_sWordValue = AWordSkeleton.o_sWordValue;

		o_ExampleList = new ArrayList<SentenceComposite>(
		        AWordSkeleton.o_ExampleList);
		Collections.copy(o_ExampleList, AWordSkeleton.o_ExampleList);
		
		o_CompoundList = new ArrayList<SentenceComposite>(AWordSkeleton.o_CompoundList);
		Collections.copy(o_CompoundList, AWordSkeleton.o_CompoundList);

		o_IdiomList = new ArrayList<SentenceComposite>(AWordSkeleton.o_IdiomList);
		Collections.copy(o_IdiomList, AWordSkeleton.o_IdiomList);

		o_ParadigmList = new ArrayList<String>(AWordSkeleton.o_ParadigmList);
		Collections.copy(o_ParadigmList, AWordSkeleton.o_ParadigmList);

		o_SynonymList = new ArrayList<String>(AWordSkeleton.o_SynonymList);
		Collections.copy(o_SynonymList, AWordSkeleton.o_SynonymList);

		o_TranslationList = new ArrayList<String>(
				AWordSkeleton.o_TranslationList);
		Collections.copy(o_TranslationList, AWordSkeleton.o_TranslationList);
	}

	public void addTranslation(String translation)
	{
		o_TranslationList.add(translation);
	}

	public List<SentenceComposite> getExampleList()
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

	public void addExample(SentenceComposite AExample)
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

	/**
	 * @return the o_CompoundList
	 */
	public List<SentenceComposite> getCompoundList()
	{
		return o_CompoundList;
	}

	/**
	 * @param o_CompoundList
	 *            the o_CompoundList to set
	 */
	public void addCompound(SentenceComposite ACompound)
	{
		this.o_CompoundList.add(ACompound);
	}

	/**
	 * @return the o_IdiomList
	 */
	public List<SentenceComposite> getIdiomList()
	{
		return o_IdiomList;
	}

	/**
	 * @param o_IdiomList
	 *            the o_IdiomList to set
	 */
	public void addIdiom(SentenceComposite AIdiom)
	{
		this.o_IdiomList.add(AIdiom);
	}

	public void clear()
	{
		o_sLang = "";
		o_sPhoneticSoundFile = "";
		o_sPhoneticValue = "";
		o_sWordClass = "";
		o_sWordValue = "";
		o_ExampleList.clear();
		o_IdiomList.clear();
		o_CompoundList.clear();
		o_ParadigmList.clear();
		o_SynonymList.clear();
		o_TranslationList.clear();
	}

}