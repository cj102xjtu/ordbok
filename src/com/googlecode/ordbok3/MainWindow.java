package com.googlecode.ordbok3;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnCloseListener;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;

import com.googlecode.ordbok3.feedParser.FeedParser;
import com.googlecode.ordbok3.log.OrdbokLog;
import com.googlecode.ordbok3.translationData.Word;

public class MainWindow extends Activity implements OnClickListener {
	
	private class LookupWordTask extends AsyncTask<String, Integer, String> {
		
		
		
	    @Override
		protected void onPreExecute() {
	    	o_progressDialog.setTitle("Searching");
	    	o_progressDialog.show();
			super.onPreExecute();
		}


		// Do the long-running work in here
	    protected String doInBackground(String... keyword) {

			String content = "";
			try {
				URL url;
				URLConnection urlConn;
				DataOutputStream dos;
				BufferedReader dis;

				String ord = keyword[0];

				url = new URL(
						"http://folkets-lexikon.csc.kth.se/folkets/folkets/lookupword");
				// url =new URL("http://folkets/folkets/lookupword");
				urlConn = url.openConnection();
				urlConn.setDoInput(true);
				urlConn.setDoOutput(true);
				urlConn.setUseCaches(false);
				 urlConn.setRequestProperty("Content-Type","text/x-gwt-rpc; charset=utf-8");
//				urlConn.setRequestProperty("Content-Type",
//						"application/x-www-form-urlencoded");

				dos = new DataOutputStream(urlConn.getOutputStream());

				// Log.e(LOG_TAG, ord);
				ord = ord.replace("ä", "Ã¤");//ï¿½?		" +
//				char c='Â£'+1;
//				ord = ord.replaceAll("Ã¤",""+c);//ï¿½?	
				ord = ord.replace("å", "Ã¥");
				ord = ord.replace("ö", "Ã¶");
				String message = "7|0|6|http://folkets-lexikon.csc.kth.se/folkets/folkets/|1F6DF5ACEAE7CE88AACB1E5E4208A6EC|se.algoritmica.folkets.client.LookUpService|lookUpWord|se.algoritmica.folkets.client.LookUpRequest/1089007912|" + ord + "|1|2|3|4|1|5|5|1|0|1|6|";
				dos.writeBytes(message);
				dos.flush();
				dos.close();

				dis = new BufferedReader(new InputStreamReader(
						urlConn.getInputStream(), "UTF-8"));
				String s = "";

				while ((s = dis.readLine()) != null) {

					content += s;
				}
				// System.out.println(content);
				OrdbokLog.i(LOG_TAG, "raw content" + content);				
				dis.close(); 
				
			} catch (MalformedURLException mue) {
				Log.e(LOG_TAG, mue.getMessage());
			} catch (IOException ioe) {
				Log.e(LOG_TAG, ioe.getMessage());
			}
//	        }
	        return content;
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
	

	TextView textViewText;
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
	    
	    searchView.setOnQueryTextListener(new OnQueryTextListener() { 

            @Override 
            public boolean onQueryTextChange(String query) {
                return false; 

            }

			@Override
			public boolean onQueryTextSubmit(String query) {
				LookUpWord(query);
				return true;
			} 

        });
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
	    
	    o_progressDialog = new ProgressDialog(this);  
		
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
		if ((!content.contains("<"))||(!content.contains(">"))) 
		{
			textViewText.setText("Sorry,  word not found!");
	    	o_progressDialog.dismiss();
			return;
		}
		content = content.substring(content.indexOf("<"),content.lastIndexOf(">")+1);
		
		OrdbokLog.i(LOG_TAG, "xml content" + content);
		
		new Translate2ChTask().execute(content);
	}

	private void populateLookupResult(List<Word> words) {

//		words = words.replace("&cd lt", "[");
//		words = words.replace("&gt", "]");
//		words = words.replace("&quot", "\"");
//		words = words.replace("Ã¤", "ä");//&aring;
//		words = words.replace("Ã¥", "å");//�?		
//		words = words.replace("Ã¶", "ö");
//		words = words.replace("&amp;#39;", "'");
//		words = words.replace("&amp", "&");
//		
//		words = words.replace("&amp;quot;", "");
//		words = words.replace("origin=lexin", "");
//		words = words.replaceAll("date=(\\S{12})", "");
//		
//		//content = content.replaceAll("Ã¯Â¿Â½?, "Ã¯Â¿Â½?");//Ã¯Â¿Â½?		
//		words = words.replace("\\\"", "");
//		words = words.replace(">", ">\n");
//		words = words.replace("+", "_");
//		words = words.replace("comment=", " -- ");
//		words = words.replace("origin=lexin", "");
//
//
//		
//		
//		//Log.e(LOG_TAG,content);
//		ArrayList<Word> words = new ArrayList<Word>();
//		Word word=new Word();
//		
//		
//		String strContents[] = words.split("\n");
//		
//		// print the final formated content to log
//		for (String line : strContents)
//		{
//		    OrdbokLog.i(LOG_TAG, line);
//		}
//		
//		
//		for (int i=0;i<strContents.length;i++)
//		{
//			//System.out.println(i+": "+strContents[i]);
//			try{
//			if (strContents[i].contains("<word value"))
//			{
//				word.setWordValue(strContents[i].substring(strContents[i].indexOf("value=")+6,strContents[i].indexOf("lang=")-1));
//				word.setLang(strContents[i].substring(strContents[i].indexOf("lang=")+5,strContents[i].indexOf("class=")-1));
//				word.setWordClass(strContents[i].substring(strContents[i].indexOf("class=")+6,strContents[i].indexOf("id=")-1));
//			} else if (strContents[i].contains("</word"))
//			{
//				words.add(word);
//				word = new Word();
//			}
//			else if (strContents[i].contains("<translation value"))
//			{
//				if (strContents[i-1].contains("word")){
//					word.setWordContent("<h3><font color='black'><b>");
//					word.setWordContent("<l>"+strContents[i].substring(strContents[i].indexOf("value=")+6,strContents[i].indexOf(">"))+"</l>");
//					word.setWordContent("</b></font></h3>");
//				} else {
//					word.setWordContent("<l>"+strContents[i].substring(strContents[i].indexOf("value=")+6,strContents[i].indexOf(">"))+"</l>");
//					word.setWordContent("<br /><br />");
//				}
//			} else if (strContents[i].contains("<phonetic value"))
//			{
//				word.setPhoneticValue(strContents[i].substring(strContents[i].indexOf("value=")+6,strContents[i].indexOf("soundFile=")-1));
//				word.setPhoneticSoundFile(SafeSubString(strContents[i],strContents[i].indexOf("soundFile=")+10,strContents[i].indexOf(">")));
//				if (!word.getPhoneticSoundFile().trim().equalsIgnoreCase("")) 
//				{
//					soundFile = word.getPhoneticSoundFile().trim();
//					//buttonUttal.setEnabled(true);
//				}
//			} else if (strContents[i].contains("<inflection value"))
//			{
//				word.setWordContent(SafeSubString(strContents[i],strContents[i].indexOf("value=")+6,strContents[i].indexOf(">")));
//				word.setWordContent(" ");
//			} else if (strContents[i].contains("<example"))
//			{
//				if (!strContents[i-1].contains("example")) word.setWordContent("<font color='green'><b>Examples</b></font><br />");
//				word.setWordContent("<b>"+SafeSubString(strContents[i],strContents[i].indexOf("value=")+6,strContents[i].indexOf(">"))+"</b>");
//				word.setWordContent("; ");
//			} else if (strContents[i].contains("<compound"))
//			{
//				if (!strContents[i-1].contains("compound")) word.setWordContent("<font color='yellow'><b>Compound</b></font><br />");
//				word.setWordContent(SafeSubString(strContents[i],strContents[i].indexOf("value=")+6,strContents[i].indexOf(">")));
//				word.setWordContent("; ");
//			} else if (strContents[i].contains("<idiom"))
//			{
//				if (!strContents[i-1].contains("idiom")) word.setWordContent("<font color='purple'><b>Idiom</b></font><br />");
//				word.setWordContent("<b>"+SafeSubString(strContents[i],strContents[i].indexOf("value=")+6,strContents[i].indexOf(">"))+"</b>");
//				word.setWordContent("; ");
//			}else if (strContents[i].contains("/paradigm"))
//			{
//				word.setWordContent("<br />");
//			} else if (strContents[i].contains("</inflection>"))
//			{
//				word.setWordContent(" ");
//			} else if (strContents[i].contains("<inflection value"))
//			{
//				if (!strContents[i-1].contains("inflection")) word.setWordContent("<font color='red'><b>Inflection</b></font><br />");
//				word.setWordContent(SafeSubString(strContents[i],strContents[i].indexOf("value=")+6,strContents[i].indexOf(">")));
//				word.setWordContent(" ");
//			} else if (strContents[i].contains("<synonym value"))
//			{
//				if (!strContents[i-1].contains("synonym")) word.setWordContent("<font color='red'><b>Synonym</b></font><br />");
//				word.setWordContent(SafeSubString(strContents[i],strContents[i].indexOf("value=")+6,strContents[i].indexOf("level=")-1));
//				word.setWordContent(" ");
//			} else if (strContents[i].contains("</synonym"))
//			{
//				if (!strContents[i+1].contains("synonym")) word.setWordContent("<br />");
//			}
//			}
//			catch(Exception e){
//				Log.e(LOG_TAG,strContents[i]+ e.getMessage());
//			}
//		}
//		
//		String allWords = "";
//		for (int j=0;j<words.size();j++)
//		{
//			
//			allWords =allWords+words.get(j).toString()+"<font color='white'> --------------------------------------- </font><br />";
//		}
//		allWords = allWords.replace("<br /><br />", "<br />");
		
		o_translateResultList = words;
		o_listAdapter.clear();
		o_listAdapter.addAll(o_translateResultList);
		
		//Log.i("i ",allWords);
		//textViewText.setText(allWords);
		
//		if (words.trim().equals("")) 
//		    textViewText.setText("Sorry, the word is not found.");
//		else textViewText.setText(Html.fromHtml(allWords), TextView.BufferType.SPANNABLE);
	}

	}