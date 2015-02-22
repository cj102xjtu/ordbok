package com.googlecode.ordbok3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.lang.StringUtils;
import org.apache.http.protocol.HTTP;

import android.content.Context;

import com.googlecode.ordbok3.translationData.EnglishWord;
import com.googlecode.ordbok3.translationData.SwedishWord;
import com.googlecode.ordbok3.translationData.Word;


public class DetailViewGenerator {

//	static private String WEB_TEMPLATE = "WebPageTemplate.ftl";
	static private String WEB_TEMPLATE = "testTemplate";
	static private String WORD_VALUE ="$WordValue";
	static private String TRANSLATION_LIST= "$TranslationList";
	static private String CHINESE_TRANSLATION_LIST = "$ChineseTranslation";
	
	static class Tag{
		static public String startTag = null;
		static public String endTag = null;
	}
	
	static public String generateWebPageFromWord(Word word, Context context)
	{
		String result = null;
		try {
			InputStream is = context.getAssets().open(WEB_TEMPLATE);
			
			BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, HTTP.UTF_8));

			StringBuilder stBuilder = new StringBuilder();
			String line = null;
			while ((line = fileReader.readLine()) != null) {
				stBuilder.append(line);
			}			
			
			fileReader.close();
			
			result = replaceTheParameterInTemplate(word, stBuilder.toString());		
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
		
	}
	
	static private String replaceTheParameterInTemplate(Word word, String template)
	{
		template = StringUtils.replace(template, WORD_VALUE, word.getWordValue());
		template = StringUtils.replace(template, TRANSLATION_LIST, word.getTranslationList().toString());

		
		if(word.getLang().equals(SwedishWord.ksWordId))
		{
			template = StringUtils.replace(template, CHINESE_TRANSLATION_LIST, ((SwedishWord)word).getChineseTranslationList().toString());
		}
		else if(word.getLang().equals(EnglishWord.ksWordId))
		{
			template = StringUtils.replace(template, CHINESE_TRANSLATION_LIST, ((EnglishWord)word).getChineseWordValue());
		}
		
		return template;
	}
	
}
