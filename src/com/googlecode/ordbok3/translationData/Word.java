package com.googlecode.ordbok3.translationData;


public abstract class Word extends WordSkeleton
{	
	abstract public void submitTranslat�onDate(TranslatorInterface ATranslator);
	abstract public void getTranslateResultFromTranslator(TranslatorInterface ATranslator);
	abstract public Word createWord();
}
