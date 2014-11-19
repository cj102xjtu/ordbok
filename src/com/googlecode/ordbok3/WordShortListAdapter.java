package com.googlecode.ordbok3;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class WordShortListAdapter extends ArrayAdapter<Word> {
	  private final Context o_context;
	  
	  static class ViewHolder {
		    public TextView firstLine;
		    public TextView secondLine;
		    public ImageView image;
		  }
	
	public WordShortListAdapter(Context context, int resource, ArrayList<Word> wordList) {
		super(context, resource, wordList);
		o_context = context;
	}
	

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;

		// reuse views
		if (rowView == null)
		{
			LayoutInflater inflater = ((Activity) o_context)
					.getLayoutInflater();
			rowView = inflater.inflate(R.layout.rowlayout, parent, false);
			// configure view holder
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.firstLine = (TextView) rowView
					.findViewById(R.id.firstLine);
			viewHolder.secondLine = (TextView) rowView
					.findViewById(R.id.secondLine);
			viewHolder.image = (ImageView) rowView.findViewById(R.id.icon);
			rowView.setTag(viewHolder);
		}

		// fill data
		ViewHolder holder = (ViewHolder) rowView.getTag();
		Word word = getItem(position);
		if(word != null)
		{
			String s = getItem(position).getWordValue();
			holder.firstLine.setText(s);
			holder.secondLine.setText(s);
			holder.image.setImageResource(R.drawable.flag_sv);
		}
		
		return rowView;
	}

	
	

}
