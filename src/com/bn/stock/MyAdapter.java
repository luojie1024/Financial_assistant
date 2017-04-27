package com.bn.stock;

import com.bn.financial_assistant.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {

	private Context context;
	private String[] items;
	private Bitmap[] bm_array;
	private Bitmap bm;

	public MyAdapter(Context context, String[] items, Bitmap[] bm_array) {
		this.context = context;
		this.items = items;
		this.bm_array = bm_array;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return items[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View onvertView, ViewGroup parent) {
		// TODO Auto-generated method

		LayoutInflater factory = LayoutInflater.from(context);
		View v = (View) factory.inflate(R.layout.images, null);
		ImageView iv = (ImageView) v.findViewById(R.id.imageView1);
		TextView tv = (TextView) v.findViewById(R.id.textView1);

		iv.setImageBitmap(bm_array[position]);
		tv.setText(items[position]);

		return v;
	}

}
