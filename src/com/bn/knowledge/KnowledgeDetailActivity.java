package com.bn.knowledge;

import java.io.InputStream;
import com.bn.financial_assistant.MainActivity;
import com.bn.financial_assistant.R;
import com.bn.knowledge.TxtReader;
import com.bn.util.FontManager;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class KnowledgeDetailActivity extends Activity {
	
	@SuppressWarnings("unused")
	private float textSize;
	private int bookname;
	private SeekBar sb;
	private TextView tv_text;
	private TextView tv_title;
	private Button bn_back;
	private String title = "Knowledge";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.knowledge_detail);

		Bundle bundle = this.getIntent().getExtras();
		bookname = bundle.getInt("name");
		title = bundle.getString("title");

		tv_title = (TextView) this.findViewById(R.id.knoww_detail_tv_title);
		tv_title.setText(title);

		tv_text = (TextView) this.findViewById(R.id.txt_textView);
		try {
			InputStream is = getResources().openRawResource(bookname);
			String text = TxtReader.getText(is);
			tv_text.setText(text);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();

		}

		sb = (SeekBar) this.findViewById(R.id.txt_seekBar);
		sb.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				tv_text.setTextSize(progress + 10);

			}
		});

		bn_back = (Button) this.findViewById(R.id.knoww_detail_bn_back);
		bn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent();
				intent.setClass(KnowledgeDetailActivity.this,
						KnowledgeActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.main_back, R.anim.add_back);
				KnowledgeDetailActivity.this.finish();
			}
		});
		
		Typeface tf = Typeface.createFromAsset (getAssets() ,"fonts/newfont.ttf");
		tv_title.setTypeface (tf);
		
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK
				|| keyCode == KeyEvent.KEYCODE_HOME
				&& event.getRepeatCount() == 0) {

			Intent intent = new Intent();
			intent.setClass(KnowledgeDetailActivity.this, MainActivity.class);
			startActivity(intent);
			KnowledgeDetailActivity.this.finish();
			return false;
		}
		return false;
		
	}
}
