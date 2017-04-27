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
	// ���ϵͳ��ǰ������
	@SuppressLint("SimpleDateFormat")
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	public String date = format.format(new java.util.Date());

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// ����
		setContentView(R.layout.notepad_edit);
		// �Զ����ñ༭ʱ��
		setNoteTime();

		noteContent = (EditText) findViewById(R.id.notepad_edit_et);
		// ���������
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
							"�ı����ݲ���Ϊ��", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
					return;
				} else {
					flag = DBUtil.addNotepad(content);
					Toast toast;
					if (flag) {
						toast = Toast.makeText(getApplicationContext(), "��ӳɹ�",
								Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					} else {
						toast = Toast.makeText(getApplicationContext(), "���ʧ��",
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
		// ���ؼ�����
		noteBack = (Button) findViewById(R.id.notepad_edit_back);
		noteBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// ����
				Intent intent = new Intent();
				intent.setClass(NotepadEditActivity.this, NotepadActivity.class);
				startActivity(intent);
				NotepadEditActivity.this.finish();
			}
		});

		// ��������
		FontManager.initTypeFace(this);
		ViewGroup vg = FontManager.getContentView(this);
		FontManager.changeFonts(vg, this);
	}

	// ����ʱ�������Ϊϵͳ��ǰ����
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
