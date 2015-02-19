package com.googlecode.ordbok3.translationData;

import java.io.Serializable;


public abstract class Word extends WordSkeleton implements Serializable
{	
	private static final long serialVersionUID = 1L;
	abstract public void submitTranslatíonDate(TranslatorInterface ATranslator);
	abstract public void getTranslateResultFromTranslator(TranslatorInterface ATranslator);
	abstract public Word createWord();
}
