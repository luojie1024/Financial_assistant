package com.bn.notepad;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bn.financial_assistant.MainActivity;
import com.bn.financial_assistant.R;
import com.bn.specification.SprcificationActivity;
import com.bn.stock.StockMainActivity;
import com.bn.util.DBUtil;
import com.bn.util.FontManager;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;

public class NotepadActivity extends Activity {
	private Button notepadBack;
	private Button notepadNew;
	private String[] nTime = DBUtil.getNoteDateStr();
	private String[] nContent = DBUtil.getNoteContentStr();
	private ListView noteList;
	private boolean delNoteFlag = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// ÊúÆÁ
		
		if (nTime.length == 0) {
			setContentView(R.layout.notepadlistnothing);
		} else {
			setContentView(R.layout.notepad_main);
			List<Map<String, Object>> noteListItems = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < nTime.length; i++) {
				Map<String, Object> noteListItem = new HashMap<String, Object>();
				noteListItem.put("nTime", nTime[i]);
				noteListItem.put("nContent", nContent[i]);
				noteListItems.add(noteListItem);
			}
			SimpleAdapter simpleAdapter = new SimpleAdapter(this,
					noteListItems, R.layout.notepad_list_item, new String[] {
							"nTime", "nContent" }, new int[] {
							R.id.notelist_time, R.id.notelist_content });
			noteList = (ListView) findViewById(R.id.note_main_list);
			noteList.setAdapter(simpleAdapter);
			noteList.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					String[] noteStr = DBUtil.getNotepadListStr(nTime[arg2],
							nContent[arg2]);
					listDialog(noteStr);
				}

			});
			noteList.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
				@Override
				public void onCreateContextMenu(ContextMenu menu, View v,
						ContextMenuInfo menuInfo) {
					// TODO Auto-generated method stub
					menu.setHeaderTitle("Ö´ÐÐ²Ù×÷");
					menu.add(0, 0, 0, "É¾³ý");

				}
			});

			FontManager.initTypeFace(this);
			ViewGroup vg = FontManager.getContentView(this);
			FontManager.changeFonts(vg, this);
		}

		notepadBack = (Button) findViewById(R.id.notepad_main_back_btn);
		notepadBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(NotepadActivity.this, MainActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.main_back, R.anim.add_back);
				NotepadActivity.this.finish();
			}
		});
		notepadNew = (Button) findViewById(R.id.notepad_main_new);
		notepadNew.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(NotepadActivity.this, NotepadEditActivity.class);
				startActivity(intent);
				NotepadActivity.this.finish();
			}
		});
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) item
				.getMenuInfo();
		int position = contextMenuInfo.position;
		switch (item.getItemId()) {
		case 0:
			delNoteFlag = DBUtil.deleteNotepadListStr(nTime[position],
					nContent[position]);
			if (delNoteFlag) {
				Toast toast = Toast.makeText(getApplicationContext(), "É¾³ý³É¹¦",
						Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			} else {
				Toast toast = Toast.makeText(getApplicationContext(), "É¾³ýÊ§°Ü",
						Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			}
			Intent intent = new Intent();
			intent.setClass(NotepadActivity.this, NotepadActivity.class);
			startActivity(intent);
			NotepadActivity.this.finish();
			break;
		default:
			break;
		}
		return super.onContextItemSelected(item);
	}

	private void listDialog(String[] info) {
		new AlertDialog.Builder(this).setTitle("ÏêÏ¸ÐÅÏ¢")
				.setMessage(info[0] + "\n\n\t" + info[1])
				.setPositiveButton("È·¶¨", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface paramDialogInterface,
							int paramInt) {
					}
				}).show();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK
				|| keyCode == KeyEvent.KEYCODE_HOME
				&& event.getRepeatCount() == 0) {

			Intent intent = new Intent();
			intent.setClass(NotepadActivity.this, MainActivity.class);
			startActivity(intent);
			NotepadActivity.this.finish();

			return false;

		}

		return false;

	}
}
