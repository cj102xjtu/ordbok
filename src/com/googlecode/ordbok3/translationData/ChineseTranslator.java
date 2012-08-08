package com.googlecode.ordbok3.translationData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;



//http://fanyi.youdao.com/openapi.do?keyfrom=FolketsLexikon&key=1235218692&type=data&doctype=xml&version=1.1&q=

public class ChineseTranslator implements TranslatorInterface
{


	// singleton
	private static ChineseTranslator kChineseTranslatorInstance;
	
	public static ChineseTranslator instance()
	{
		if(kChineseTranslatorInstance == null)
		{
			kChineseTranslatorInstance = new ChineseTranslator();
		}
		
		return kChineseTranslatorInstance;
	}
	
	private static String ksYouDaoUrl = "http://fanyi.youdao.com/openapi.do?keyfrom=FolketsLexikon&key=1235218692&type=data&doctype=xml&version=1.1&q=";
	private DefaultHttpClient o_Httpclient;
	private TranslationTable o_TranslationTable;

	// private construction for singleton
	private ChineseTranslator()
	{
		// initialize http client
		HttpParams params = new BasicHttpParams();
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(params, "utf-8");
		o_Httpclient = new DefaultHttpClient(params);
		
		// initialize translation table.
		o_TranslationTable = new TranslationTable();
	}
	
	@Override
    public void addEngWordToTranslate(String sAEnglishWord)
    {
		o_TranslationTable.addEngWordToTranslate(sAEnglishWord);
    }

	@Override
    public String getChTranslationForEng(String sAEnglishWord)
    {
	    return o_TranslationTable.getChTranslationForEng(sAEnglishWord);
    }

	@Override
    public boolean submitEngWordToTranslationServer()
    {
		return translateToChinese(o_TranslationTable.generateQueryString());
    }
	
	@Override
    public boolean initialTranslation()
    {
	    return o_TranslationTable.initialTranslation();
    }

	private boolean translateToChinese(String sAEnglishWord)
	{
		boolean bResult = false;
		try
        {
			// create request
	        String query = URLEncoder.encode(sAEnglishWord, "utf-8");
			String sUrl = ksYouDaoUrl + query;
			HttpGet request = new HttpGet(sUrl);

			// get response
	        HttpResponse response = o_Httpclient.execute(request);
	        
	        // read response
	        BufferedReader rd = new BufferedReader
	        		(new InputStreamReader(response.getEntity().getContent(), "utf-8"));
	        String line;
	        StringBuilder xml = new StringBuilder();
	        while ((line = rd.readLine()) != null) 
	        {
	        	xml.append(line);
	        }
	        
	        // clean the entity of response
	        HttpEntity enty = response.getEntity();
	        if (enty != null)
	            enty.consumeContent();
	        
	        // add translation result to translation table.
	        o_TranslationTable.addChTranslationResult(xml.toString());
	        
	        bResult = true;
        }
        catch (ClientProtocolException e)
        {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
        catch (UnsupportedEncodingException e1)
        {
	        // TODO Auto-generated catch block
	        e1.printStackTrace();
        }
        catch (IOException e)
        {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
		
		return bResult;
	}
	
	
	
	
}
