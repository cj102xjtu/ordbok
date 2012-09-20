package com.googlecode.ordbok3.translationData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.googlecode.ordbok3.log.OrdbokLog;

public class TranslationTable implements TranslationTableInterface
{
	private static String LOG_TAG = "TranslationTable";
	private static String k_sDelimiterEnglish = "\n";
	private static String k_sTranslationPattern = "<paragraph><!\\[CDATA\\[(.*?)\\]\\]></paragraph>";
	private Map<String, String> o_EngToChTranslationTable = new HashMap<String, String>();
	
	@Override
	public void addEngWordToTranslate(String sAEnglishWord)
	{
		o_EngToChTranslationTable.put(sAEnglishWord, "");
	}
	
	@Override
	public String getChTranslationForEng(String sAEnglishWord)
	{
		String ChineseWord = o_EngToChTranslationTable.get(sAEnglishWord);
		return ChineseWord;
	}

	@Override
    public boolean initialTranslation()
    {
		o_EngToChTranslationTable.clear();
	    return true;
    }

	public String generateQueryString()
	{
		StringBuilder sQueryStringBuilder = new StringBuilder();
		
		// iterator the translation table connect all keys with delimiter to one string
		Iterator<Entry<String, String>> it = o_EngToChTranslationTable.entrySet().iterator();
		while (it.hasNext())
		{
			Map.Entry<String, String> pairs = (Map.Entry<String, String>) it.next();
			sQueryStringBuilder.append(pairs.getKey());
			sQueryStringBuilder.append(k_sDelimiterEnglish);
		}
		
		// remove extra delimiter in the end of the string
		sQueryStringBuilder.setLength(sQueryStringBuilder.length() - k_sDelimiterEnglish.length());
			
		return(sQueryStringBuilder.toString());
	}
	
	/**
	 * Because the Chinese translation API do not process translation text longer than 200 characters.
	 * we need to split the translation all translate string into less then 200 characters string list
	 * @return Query string list
	 */
	public ArrayList<String> generateQueryStringlist()
	{
		ArrayList<String> queryStringList = new ArrayList<String>();
		
		// use string builder make it faster.
		StringBuilder sQueryStringBuilder = new StringBuilder();
		
		// counter, make sure string is less then 200 characters
		int nCounter = 0;
		
		// iterator the translation table connect all keys with delimiter to one string
		Iterator<Entry<String, String>> it = o_EngToChTranslationTable.entrySet().iterator();
		while (it.hasNext())
		{
			Map.Entry<String, String> pairs = (Map.Entry<String, String>) it.next();
			if(pairs.getKey() != null)
			{
				nCounter = nCounter + pairs.getKey().length();
				
				// if counter biger than 198(delimiter is \n, 2 characters. create a query string from previous result
				if(nCounter > 98)
				{
					// remove extra delimiter in the end of the string
					sQueryStringBuilder.setLength(sQueryStringBuilder.length() - k_sDelimiterEnglish.length());
					
					// add string to string list
					queryStringList.add(sQueryStringBuilder.toString());
					
					// empty string builder
					sQueryStringBuilder = new StringBuilder();
					nCounter = 0;
				}
				
				sQueryStringBuilder.append(pairs.getKey());
				sQueryStringBuilder.append(k_sDelimiterEnglish);
			}

		}
		
		// add last string to list
		
		// remove extra delimiter in the end of the string
		sQueryStringBuilder.setLength(sQueryStringBuilder.length() - k_sDelimiterEnglish.length());
		
		// add string to string list
		queryStringList.add(sQueryStringBuilder.toString()); 
		
		return queryStringList;
	}
	
	public boolean addChTranslationResult(String sAXmlResult)
	{
		boolean bResult = false;
		OrdbokLog.d(LOG_TAG, "translation result in XML:");
		OrdbokLog.d(LOG_TAG, sAXmlResult);

		// simply use regular express to get translation result from Xml file
		Pattern MY_PATTERN = Pattern.compile(k_sTranslationPattern);
		Matcher m = MY_PATTERN.matcher(sAXmlResult);
		
		// get translation list
		ArrayList<String> chTranslationList= new ArrayList<String>();
		while (m.find())
		{
			chTranslationList.add(m.group(1));
		}
		
		
		// set the Chinese translation to translation table
		Iterator<Entry<String, String>> it = o_EngToChTranslationTable.entrySet().iterator();
		for (String chWord : chTranslationList)
        {
	        if(it.hasNext())
	        {
				Map.Entry<String, String> pairs = (Map.Entry<String, String>) it.next();
				pairs.setValue(chWord);
	        }
        }
		
		return bResult;
	}
	
	
}
