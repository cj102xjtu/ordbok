package com.googlecode.ordbok3.translationData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

//http://fanyi.youdao.com/openapi.do?keyfrom=FolketsLexikon&key=1235218692&type=data&doctype=xml&version=1.1&q=要翻译的文本

public class ChineseTranslator
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

	// private construction for singleton
	private ChineseTranslator()
	{
		HttpParams params = new BasicHttpParams();
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(params, "utf-8");

		o_Httpclient = new DefaultHttpClient();
	}
	
	public String translateToChinese(String sAEnglishWord)
	{
		String sResult = "";
		// create request
		String sUrl = ksYouDaoUrl + sAEnglishWord;
		HttpGet request = new HttpGet(sUrl);
		
		// get response
		try
        {
	        HttpResponse response = o_Httpclient.execute(request);
	        
	        // read response
	        BufferedReader rd = new BufferedReader
	        		(new InputStreamReader(response.getEntity().getContent()));
	        String line = "";
	        StringBuilder xml = new StringBuilder();
	        while ((line = rd.readLine()) != null) 
	        {
	        	xml.append(line);
	        }
	        String xmlResult = xml.toString();
	        
	        // get translate result from xml
        }
        catch (ClientProtocolException e)
        {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
        catch (IOException e)
        {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
		
		return sResult;
	}
	
	
	
	
}
