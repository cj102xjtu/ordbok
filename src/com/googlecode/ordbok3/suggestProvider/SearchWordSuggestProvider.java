package com.googlecode.ordbok3.suggestProvider;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;

import com.googlecode.ordbok3.R;
import com.googlecode.ordbok3.log.OrdbokLog;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.BaseColumns;
import android.util.Log;

public class SearchWordSuggestProvider extends ContentProvider{
	final String LOG_TAG = this.getClass().getSimpleName();

	private class SuggestCompletion extends AsyncTask<String, Integer, String> {
		
		@Override
		protected void onPreExecute() {
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
						"http://folkets-lexikon.csc.kth.se/folkets/folkets/generatecompletion");
				
				urlConn = url.openConnection();
				urlConn.setDoInput(true);
				urlConn.setDoOutput(true);
				urlConn.setUseCaches(false);
				 urlConn.setRequestProperty("Content-Type","text/x-gwt-rpc; charset=utf-8");
				dos = new DataOutputStream(urlConn.getOutputStream());

				ord = ord.replace("ä", "Ã¤");//ï¿½?		" +
				ord = ord.replace("å", "Ã¥");
				ord = ord.replace("ö", "Ã¶");
				String message = "7|0|7|http://folkets-lexikon.csc.kth.se/folkets/folkets/|72408650102EFF3C0092D16FF6C6E52F|se.algoritmica.folkets.client.ItemSuggestService|getSuggestions|se.algoritmica.folkets.client.ProposalRequest/3613917143|com.google.gwt.user.client.ui.SuggestOracle$Request/3707347745|" + ord + "|1|2|3|4|1|5|5|2|6|5|7|";
				dos.writeBytes(message);
				dos.flush();
				dos.close();

				dis = new BufferedReader(new InputStreamReader(
						urlConn.getInputStream(), "UTF-8"));
				String s = "";

				while ((s = dis.readLine()) != null) {

					content += s;
				}
				OrdbokLog.i(LOG_TAG, "raw content" + content);				
				dis.close(); 
				
			} catch (MalformedURLException mue) {
				Log.e(LOG_TAG, mue.getMessage());
			} catch (IOException ioe) {
				Log.e(LOG_TAG, ioe.getMessage());
			}
	        return content;
	    }    
	    

	    // This is called each time you call publishProgress()
	    protected void onProgressUpdate(Integer... progress) {
	    }

	    // This is called when doInBackground() is finished
	    protected void onPostExecute(String result) {
//	    	TranslateWord2Chinese(result);
	    }
	}
	
	
	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		String [] columeName = {BaseColumns._ID, SearchManager.SUGGEST_COLUMN_TEXT_1, SearchManager.SUGGEST_COLUMN_INTENT_DATA};
		MatrixCursor cursor = new MatrixCursor(columeName);
		String query = uri.getLastPathSegment().toLowerCase(Locale.getDefault());
		
		new SuggestCompletion().execute(query);
		// if there is query string appended after SUGGEST_URI_PATH_QUERY. 
		// popup the suggestion.
		if(query.equals(SearchManager.SUGGEST_URI_PATH_QUERY) == false)
		{
			// test code
			cursor.newRow().add(0).add(query).add(query);
			cursor.newRow().add(1).add(query).add(query);
			cursor.newRow().add(2).add(query).add(query);			
		}
		return cursor;
	}

	@Override
	public String getType(Uri uri) {
		String type = "vnd.android.cursor.dir/vnd.com.googlecode.ordbok3.suggestProvider.table1";
		return type;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}
	

}
