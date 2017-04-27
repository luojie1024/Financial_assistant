package com.bn.financial_assistant;

import com.bn.account.AccountActivity;
import com.bn.calculator.CalculatorActivity;
import com.bn.knowledge.KnowledgeActivity;
import com.bn.notepad.NotepadActivity;
import com.bn.specification.SprcificationActivity;
import com.bn.stock.StockMainActivity;
import com.bn.util.DBUtil;
import com.bn.util.FontManager;
import com.bn.financial_assistant.R;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends Activity {
	private Button bn_account;
	private Button bn_law;
	private Button bn_stock;
	private Button bn_notepad;
	private Button bn_knowledge;
	private Button bn_calculator;
	private Button bn_exit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_main);
				
		DBUtil.createTable();
		bn_account = (Button) this.findViewById(R.id.layout_main_bn_account);
		bn_account.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, AccountActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.add_go, R.anim.main_go);
				MainActivity.this.finish();
			}
		});

		bn_law = (Button) this.findViewById(R.id.layout_main_bn_law);
		bn_law.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, KnowledgeActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.add_go, R.anim.main_go);
				MainActivity.this.finish();
			}
		});

		bn_stock = (Button) this.findViewById(R.id.layout_main_bn_stock);
		bn_stock.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, StockMainActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.add_go, R.anim.main_go);
				MainActivity.this.finish();
			}
		});

		bn_notepad = (Button) this.findViewById(R.id.layout_main_bn_notepad);
		bn_notepad.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, NotepadActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.add_go, R.anim.main_go);
				MainActivity.this.finish();
			}
		});

		bn_knowledge = (Button) this
				.findViewById(R.id.layout_main_bn_konwledges);
		bn_knowledge.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, SprcificationActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.add_go, R.anim.main_go);
				MainActivity.this.finish();
			}
		});

		bn_calculator = (Button) this
				.findViewById(R.id.layout_main_bn_calculator);
		bn_calculator.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, CalculatorActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.add_go, R.anim.main_go);
				MainActivity.this.finish();
			}
		});

		bn_exit = (Button)this.findViewById(R.id.layout_main_bn_exit);
		bn_exit.setOnClickListener(
				new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog();
					}
				}
				);
		
		FontManager.initTypeFace(this);
		ViewGroup vg = FontManager.getContentView(this);
		FontManager.changeFonts(vg, this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK
				|| keyCode == KeyEvent.KEYCODE_HOME
				&& event.getRepeatCount() == 0) {

			dialog();
			return false;

		}

		return false;

	}

	protected void dialog() {

		AlertDialog.Builder builder = new Builder(MainActivity.this);

		builder.setMessage("确定要退出吗?");

		builder.setTitle("提示");

		builder.setPositiveButton("确认",

		new android.content.DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				dialog.dismiss();

				MainActivity.this.finish();

			}

		});

		builder.setNegativeButton("取消",

		new android.content.DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				dialog.dismiss();

			}

		});

		builder.create().show();

	}

}
