package com.googlecode.ordbok3;

import org.apache.http.protocol.HTTP;

import com.googlecode.ordbok3.log.OrdbokLog;
import com.googlecode.ordbok3.translationData.Word;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class WordDetailView extends Activity {

	static final String WORD_INFO = "WordInformation";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.word_detail_view);
		WebView webView = (WebView)findViewById(R.id.webView);
		Word displayedWord = (Word) getIntent().getSerializableExtra(WORD_INFO);
		
		String webPage = DetailViewGenerator.generateWebPageFromWord(displayedWord, this);
		OrdbokLog.e(WORD_INFO, webPage);
		webView.loadData(webPage, "text/html",HTTP.UTF_8);
	}

}
