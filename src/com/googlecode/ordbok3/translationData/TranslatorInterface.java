package com.googlecode.ordbok3.translationData;

public interface TranslatorInterface extends TranslationTableInterface
{
	public boolean submitEngWordToTranslationServer();
}

interface TranslationTableInterface
{
	public void addEngWordToTranslate(String sAEnglishWord);
	public String getChTranslationForEng(String sAEnglishWord);
	public boolean initialTranslation();
}