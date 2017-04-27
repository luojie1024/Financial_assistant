package com.bn.notepad;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.bn.financial_assistant.MainActivity;
import com.bn.financial_assistant.R;
import com.bn.util.DBUtil;
import com.bn.util.FontManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NotepadEditActivity extends Activity {
	private TextView noteTime;
	private EditText noteContent;
	private Button noteSave;
	private Button noteBack;
	private boolean flag = false;
	// 获得系统当前年月日
	@SuppressLint("SimpleDateFormat")
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	public String date = format.format(new java.util.Date());

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 竖屏
		setContentView(R.layout.notepad_edit);
		// 自动设置编辑时间
		setNoteTime();

		noteContent = (EditText) findViewById(R.id.notepad_edit_et);
		// 保存键监听
		noteSave = (Button) findViewById(R.id.notepad_edit_save);
		noteSave.setOnClickListener(new OnClickListener() {
			String nTime = noteTime.getText().toString();

			@SuppressLint("ShowToast")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String[] content = new String[] { nTime,
						noteContent.getText().toString() };
				if (noteContent.getText().toString().length() == 0) {
					Toast toast = Toast.makeText(getApplicationContext(),
							"文本内容不能为空", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
					return;
				} else {
					flag = DBUtil.addNotepad(content);
					Toast toast;
					if (flag) {
						toast = Toast.makeText(getApplicationContext(), "添加成功",
								Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					} else {
						toast = Toast.makeText(getApplicationContext(), "添加失败",
								Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					}
				}
				Intent intent = new Intent();
				intent.setClass(NotepadEditActivity.this, NotepadActivity.class);
				startActivity(intent);
				NotepadEditActivity.this.finish();
			}
		});
		// 返回键监听
		noteBack = (Button) findViewById(R.id.notepad_edit_back);
		noteBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 返回
				Intent intent = new Intent();
				intent.setClass(NotepadEditActivity.this, NotepadActivity.class);
				startActivity(intent);
				NotepadEditActivity.this.finish();
			}
		});

		// 更改字体
		FontManager.initTypeFace(this);
		ViewGroup vg = FontManager.getContentView(this);
		FontManager.changeFonts(vg, this);
	}

	// 设置时间框内容为系统当前日期
	private void setNoteTime() {
		noteTime = (TextView) findViewById(R.id.notepad_edit_time);
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(format.parse(date));
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		noteTime.setText(date);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK
				|| keyCode == KeyEvent.KEYCODE_HOME
				&& event.getRepeatCount() == 0) {

			Intent intent = new Intent();
			intent.setClass(NotepadEditActivity.this, NotepadActivity.class);
			startActivity(intent);
			NotepadEditActivity.this.finish();

			return false;

		}

		return false;

	}
}
