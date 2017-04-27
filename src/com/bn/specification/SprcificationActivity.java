package com.bn.specification;

import com.bn.financial_assistant.MainActivity;
import com.bn.financial_assistant.R;
import com.bn.firstviewpage.GuideActivity;
import com.bn.stock.StockDetailActivity;
import com.bn.stock.StockMainActivity;
import com.bn.util.FontManager;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class SprcificationActivity extends Activity {
	private ListView SpList;
	private Button bn_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_specification);
		
		SpList = (ListView) this.findViewById(R.id.specificationlistView1);
		SpList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (position == 1) {
					Intent intent = new Intent();
					intent.setClass(SprcificationActivity.this,
							GuideActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.add_go, R.anim.main_go);
					SprcificationActivity.this.finish();
				}
			}
		});

		bn_back = (Button) this.findViewById(R.id.layout_specificatio_bn_back);
		bn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(SprcificationActivity.this, MainActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.main_back, R.anim.add_back);
				SprcificationActivity.this.finish();
			}
		});

		// ¸ü¸Ä×ÖÌå
		FontManager.initTypeFace(this);
		ViewGroup vg = FontManager.getContentView(this);
		FontManager.changeFonts(vg, this);

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK
				|| keyCode == KeyEvent.KEYCODE_HOME
				&& event.getRepeatCount() == 0) {

			Intent intent = new Intent();
			intent.setClass(SprcificationActivity.this, MainActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.main_back, R.anim.add_back);
			SprcificationActivity.this.finish();

			return false;

		}

		return false;

	}
}
