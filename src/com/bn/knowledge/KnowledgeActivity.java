package com.bn.knowledge;

import com.bn.financial_assistant.MainActivity;
import com.bn.financial_assistant.R;
import com.bn.notepad.NotepadActivity;
import com.bn.util.FontManager;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

public class KnowledgeActivity extends Activity {

	private Button bn_book1, bn_book2, bn_book3, bn_book4, bn_book5, back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.knowledge_activity);

		bn_book1 = (Button) this.findViewById(R.id.know_Button1);
		bn_book2 = (Button) this.findViewById(R.id.know_Button2);
		bn_book3 = (Button) this.findViewById(R.id.know_Button3);
		bn_book4 = (Button) this.findViewById(R.id.know_Button4);
		bn_book5 = (Button) this.findViewById(R.id.know_Button5);
		back = (Button) this.findViewById(R.id.knoww_main_bn_back);

		bn_book1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int name = R.raw.xiaofeizhequanyibaohufa;
				String title = "《消费者权益保护法》";

				Intent intent = new Intent();
				intent.setClass(KnowledgeActivity.this,
						KnowledgeDetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("name", name);
				bundle.putString("title", title);
				intent.putExtras(bundle);
				startActivity(intent);
				overridePendingTransition(R.anim.add_go, R.anim.main_go);
				KnowledgeActivity.this.finish();

			}
		});

		bn_book2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int name = R.raw.jingjimingci;
				String title = "《经济名词》";

				Intent intent = new Intent();
				intent.setClass(KnowledgeActivity.this,
						KnowledgeDetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("name", name);
				bundle.putString("title", title);
				intent.putExtras(bundle);
				startActivity(intent);
				overridePendingTransition(R.anim.add_go, R.anim.main_go);
				KnowledgeActivity.this.finish();
			}
		});
		bn_book3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int name = R.raw.jinrongjichu;
				String title = "《金融基础》";

				Intent intent = new Intent();
				intent.setClass(KnowledgeActivity.this,
						KnowledgeDetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("name", name);
				bundle.putString("title", title);
				intent.putExtras(bundle);
				startActivity(intent);
				overridePendingTransition(R.anim.add_go, R.anim.main_go);
				KnowledgeActivity.this.finish();
			}
		});
		bn_book4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int name = R.raw.caijingjichu;
				String title = "《财经基础》";

				Intent intent = new Intent();
				intent.setClass(KnowledgeActivity.this,
						KnowledgeDetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("name", name);
				bundle.putString("title", title);
				intent.putExtras(bundle);
				startActivity(intent);
				overridePendingTransition(R.anim.add_go, R.anim.main_go);
				KnowledgeActivity.this.finish();
			}
		});
		bn_book5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int name = R.raw.shuiwu;
				String title = "《税务基础》";

				Intent intent = new Intent();
				intent.setClass(KnowledgeActivity.this,
						KnowledgeDetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("name", name);
				bundle.putString("title", title);
				intent.putExtras(bundle);
				startActivity(intent);
				overridePendingTransition(R.anim.add_go, R.anim.main_go);
				KnowledgeActivity.this.finish();
			}
		});
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(KnowledgeActivity.this, MainActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.main_back, R.anim.add_back);
				KnowledgeActivity.this.finish();
			}
		});
		FontManager.initTypeFace(this);
		ViewGroup vg = FontManager.getContentView(this);
		FontManager.changeFonts(vg, this);

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

			Intent intent = new Intent();
			intent.setClass(KnowledgeActivity.this, MainActivity.class);
			startActivity(intent);
			KnowledgeActivity.this.finish();
			return false;
		}
		return false;
	}

}
