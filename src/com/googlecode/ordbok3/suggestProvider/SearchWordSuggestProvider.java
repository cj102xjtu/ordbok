package com.googlecode.ordbok3.suggestProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.protocol.HTTP;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

import com.googlecode.ordbok3.R;
import com.googlecode.ordbok3.log.OrdbokLog;

public class SearchWordSuggestProvider extends ContentProvider {
	final String LOG_TAG = this.getClass().getSimpleName();
	static final String LANG_ENG = "Engelska";

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		String[] columeName = { BaseColumns._ID,
				SearchManager.SUGGEST_COLUMN_TEXT_1,
				SearchManager.SUGGEST_COLUMN_ICON_1,
				SearchManager.SUGGEST_COLUMN_INTENT_DATA };
		MatrixCursor cursor = new MatrixCursor(columeName);
		String query = uri.getLastPathSegment()
				.toLowerCase(Locale.getDefault());
		if (query.equals(SearchManager.SUGGEST_URI_PATH_QUERY) == false) {
			String content = null;
			try {
				URL url;
				URLConnection urlConn;
				OutputStreamWriter dos;
				BufferedReader dis;
				Log.i(LOG_TAG, "Query word for suggestion: " + query);

				url = new URL(
						"http://folkets-lexikon.csc.kth.se/folkets/folkets/generatecompletion");

				urlConn = url.openConnection();
				urlConn.setDoOutput(true);
				urlConn.setRequestProperty(HTTP.CONTENT_TYPE,
						"text/x-gwt-rpc; charset=utf-8");
				dos = new OutputStreamWriter(urlConn.getOutputStream(),
						HTTP.UTF_8);

				String message = "7|0|7|http://folkets-lexikon.csc.kth.se/folkets/folkets/|72408650102EFF3C0092D16FF6C6E52F|se.algoritmica.folkets.client.ItemSuggestService|getSuggestions|se.algoritmica.folkets.client.ProposalRequest/3613917143|com.google.gwt.user.client.ui.SuggestOracle$Request/3707347745|"
						+ query + "|1|2|3|4|1|5|5|0|6|5|7|";
				dos.write(message);
				dos.flush();
				dos.close();

				dis = new BufferedReader(new InputStreamReader(
						urlConn.getInputStream(), HTTP.UTF_8));
				String s = "";

				while ((s = dis.readLine()) != null) {
					content += s;
				}
				OrdbokLog.i(LOG_TAG, "raw content of suggestion: " + content);
				dis.close();

			} catch (MalformedURLException mue) {
				Log.e(LOG_TAG, mue.getMessage());
			} catch (IOException ioe) {
				Log.e(LOG_TAG, ioe.getMessage());
			}

			Pattern p = Pattern
					.compile("alt=.*?\\((\\w*)\\).*?/>\\s(.*?)\\\".*?[<|\\]]");
			Matcher m = p.matcher(content);
			int id = 0;
			while (m.find()) { // Find each match in turn; String can't do this.
				String language = m.group(1); // Access a submatch group; String
												// can't do this.
				String suggestWords = m.group(2);
				Log.d(LOG_TAG, "language info: " + language);
				Log.d(LOG_TAG, "suggestWord: " + suggestWords);

				int iconId = R.drawable.flag_sv;
				if (language.equals(LANG_ENG)) {
					iconId = R.drawable.flag_en;
				}
				cursor.newRow().add(id).add(suggestWords).add(iconId).add(suggestWords);

				id++;
			}
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
