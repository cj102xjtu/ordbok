package com.googlecode.ordbok3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.protocol.HTTP;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;

import com.googlecode.ordbok3.feedParser.FeedParser;
import com.googlecode.ordbok3.log.OrdbokLog;
import com.googlecode.ordbok3.translationData.Word;
import com.googlecode.ordbok3.translationData.WordBuilder;

public class MainWindow extends Activity implements OnClickListener {
	
	private class LookupWordTask extends AsyncTask<String, Integer, String> {
		
		
		
	    @Override
		protected void onPreExecute() {
	    	o_progressDialog.setTitle("Searching");
	    	o_progressDialog.setMessage("Translating to Swedish and English.");
	    	o_progressDialog.show();
			super.onPreExecute();
		}


		// Do the long-running work in here
	    protected String doInBackground(String... keyword) {

			StringBuilder content = new StringBuilder();
			try {
				URL url;
				URLConnection urlConn;
				OutputStreamWriter dos;
				BufferedReader dis;

				String ord = keyword[0];

				url = new URL(
						"http://folkets-lexikon.csc.kth.se/folkets/folkets/lookupword");
				urlConn = url.openConnection();
				urlConn.setDoOutput(true);
				 urlConn.setRequestProperty(HTTP.CONTENT_TYPE,"text/x-gwt-rpc; charset=utf-8");
				dos = new OutputStreamWriter(urlConn.getOutputStream(), HTTP.UTF_8);

				String message = "7|0|6|http://folkets-lexikon.csc.kth.se/folkets/folkets/|1F6DF5ACEAE7CE88AACB1E5E4208A6EC|se.algoritmica.folkets.client.LookUpService|lookUpWord|se.algoritmica.folkets.client.LookUpRequest/1089007912|" + ord + "|1|2|3|4|1|5|5|1|0|1|6|";
				dos.write(message);
				dos.flush();
				dos.close();

				dis = new BufferedReader(new InputStreamReader(
						urlConn.getInputStream(), HTTP.UTF_8));
				String s = "";

				while ((s = dis.readLine()) != null) {

					content.append(s);
				}
				OrdbokLog.i(LOG_TAG, "raw content" + content);				
				dis.close(); 
				
			} catch (MalformedURLException mue) {
				Log.e(LOG_TAG, mue.getMessage());
			} catch (IOException ioe) {
				Log.e(LOG_TAG, ioe.getMessage());
			}
	        return content.toString();
	    }    
	    

	    // This is called each time you call publishProgress()
	    protected void onProgressUpdate(Integer... progress) {
	    }

	    // This is called when doInBackground() is finished
	    protected void onPostExecute(String result) {
	    	TranslateWord2Chinese(result);
	    }
	}
	
	
	private class Translate2ChTask extends AsyncTask<String, Integer, List<Word> > 
	{
		
	    @Override
		protected void onPreExecute() {
	    	o_progressDialog.setMessage("Translating to Chinese.");
			super.onPreExecute();
		}


		// Do the long-running work in here
	    protected List<Word> doInBackground(String... contents) {
			FeedParser parser = new FeedParser();
			List<Word> words = parser.parse(contents[0]);
	        return words;
	    }    
	    

	    // This is called each time you call publishProgress()
	    protected void onProgressUpdate(Integer... progress) {
	    }

	    // This is called when doInBackground() is finished
	    protected void onPostExecute(List<Word>  result) {
	    	populateLookupResult(result);
	    	o_progressDialog.dismiss();
	    }
	}
	

	String soundFile;
	final String LOG_TAG = this.getClass().getSimpleName();
	List<Word> o_translateResultList = new ArrayList<Word>();
	WordShortListAdapter o_listAdapter;
	ProgressDialog o_progressDialog;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// initialize ordbokLog
		OrdbokLog.initialize(this);
		
		setContentView(R.layout.main);
		
	    final SearchView searchView =
	            (SearchView) findViewById(R.id.searchView);
	    
	    SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	    searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
	    
		// Catch event on [x] button inside search view
		int searchCloseButtonId = searchView.getContext().getResources()
				.getIdentifier("android:id/search_close_btn", null, null);
		// Get the search close button image view
		ImageView closeButton = (ImageView) searchView
				.findViewById(searchCloseButtonId);

		// Set on click listener
		closeButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				searchView.setQuery("", false);
				o_translateResultList = null;
				o_listAdapter.clear();
			}
		});
		
	    
	    ListView listView = (ListView)findViewById(R.id.listView);
	    o_listAdapter = new WordShortListAdapter(this, R.layout.rowlayout, o_translateResultList);
	    listView.setAdapter(o_listAdapter);
	    
	    listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Word selectedWord = ((WordShortListAdapter)(parent.getAdapter())).getItem(position);
			    Intent intent = new Intent(parent.getContext(), WordDetailView.class);
			    intent.putExtra(WordDetailView.WORD_INFO, selectedWord);
			    startActivity(intent);				
			}
		});
	    
	    o_progressDialog = new ProgressDialog(this);  		

	}
	
	
	
	@Override
	protected void onNewIntent(Intent intent) {
	    // Get the intent, verify the action and get the query
	    if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
	    	String query = intent.getStringExtra(SearchManager.QUERY);
	    	LookUpWord(query);
	    }
	    else if (Intent.ACTION_VIEW.equalsIgnoreCase(intent.getAction())) {
	        // Handle a suggestions click (because the suggestions all use ACTION_VIEW)
	        Uri data = intent.getData();
	        OrdbokLog.d(LOG_TAG, "the selected query suggestion is: " + data.toString());
	        LookUpWord(data.toString());
	    }
		super.onNewIntent(intent);
	}



	@Override
	protected void onDestroy() 
	{
		OrdbokLog.uninitialize();
		super.onDestroy();
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	    case R.id.itemExit:
	        this.finish();
	        return true;
	    case R.id.itemAbout:
	        Intent i=new Intent(this,About.class);
	        startActivity(i);
	        return true;
	    }
		return false;
	}

	@Override
	public void onClick(View v) {
	}
	
	String SafeSubString(String s,int start,int end)
	{
		if (start<end)	s=s.substring(start, end);
		return s;
	}


	void LookUpWord(String query) {
			new LookupWordTask().execute(query);
		}  // end of postNewItem() method 
	
	void TranslateWord2Chinese(String content)
	{
		SearchView searchView = (SearchView) findViewById(R.id.searchView);
		
		if ((!content.contains("<"))||(!content.contains(">"))) 
		{
			// update the error message in view
			WordBuilder wordBuilder = WordBuilder.instance();
			wordBuilder.setLang("en");
			wordBuilder.setWordValue("Sorry can not find the word: " + searchView.getQuery());			
			List<Word> words = new ArrayList<Word>();
			words.add(wordBuilder.generateWord());
			populateLookupResult(words);
			
			
		    searchView.setQuery("", false);
			
	    	o_progressDialog.dismiss();
			return;
		}
		else
		{
			
		}
		content = content.substring(content.indexOf("<"),content.lastIndexOf(">")+1);
		
		OrdbokLog.i(LOG_TAG, "xml content" + content);
		
		new Translate2ChTask().execute(content);
	}

	private void populateLookupResult(List<Word> words) {
		
		o_translateResultList = words;
		o_listAdapter.clear();
		o_listAdapter.addAll(o_translateResultList);
		
	}

	}