package com.bn.account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bn.financial_assistant.R;
import com.bn.util.DBUtil;
import com.bn.util.FontManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class PayListActivity extends Activity {
	private String[] pTime = DBUtil.getPayDateStr();
	private String[] pCategory = DBUtil.getPayCategoryStr();
	private String[] pMoney = DBUtil.getPayMoneyStr();
	private ListView payList;
	private Button btn_payback;
	private Button btn_newPayList;
	private boolean delPayFlag = false;

	@Override
	public void onCreate(Bundle saveInstanceState) {
		super.onCreate(saveInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		if (pTime.length == 0) {
			setContentView(R.layout.paylistnothing);
		} else {
			setContentView(R.layout.account_paylist);
			List<Map<String, Object>> payListItems = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < pTime.length; i++) {
				Map<String, Object> payListItem = new HashMap<String, Object>();
				payListItem.put("pTime", pTime[i]);
				payListItem.put("pCategory", pCategory[i]);
				payListItem.put("pMoney", pMoney[i]);
				payListItems.add(payListItem);
			}

			SimpleAdapter simpleAdapter = new SimpleAdapter(this, payListItems,
					R.layout.list_item, new String[] { "pTime", "pCategory",
							"pMoney" }, new int[] { R.id.list_time,
							R.id.list_category, R.id.list_money });
			payList = (ListView) findViewById(R.id.paylist_list);
			payList.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					String[] payStr = DBUtil.getPayListStr(pTime[arg2],
							pCategory[arg2], pMoney[arg2]);
					listDialog(payStr);
				}
			});
			payList.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
				@Override
				public void onCreateContextMenu(ContextMenu menu, View v,
						ContextMenuInfo menuInfo) {
					// TODO Auto-generated method stub
					menu.setHeaderTitle("执行操作");
					menu.add(0, 0, 0, "删除");

				}
			});
			payList.setAdapter(simpleAdapter);
		}

		btn_newPayList = (Button) findViewById(R.id.btn_paylist_new);
		btn_newPayList.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putInt("viewItem", 0);
				intent.putExtras(bundle);
				intent.setClass(PayListActivity.this, AccountActivity.class);
				startActivity(intent);
				PayListActivity.this.finish();
			}
		});
		btn_payback = (Button) findViewById(R.id.btn_paylist_back);
		btn_payback.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(PayListActivity.this, AccountActivity.class);
				startActivity(intent);
				PayListActivity.this.finish();
			}
		});

		FontManager.initTypeFace(this);
		ViewGroup vg = FontManager.getContentView(this);
		FontManager.changeFonts(vg, this);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK
				&& keyCode == KeyEvent.KEYCODE_HOME
				|| event.getRepeatCount() == 0) {

			Intent intent = new Intent();
			intent.setClass(PayListActivity.this, AccountActivity.class);
			startActivity(intent);
			PayListActivity.this.finish();

			return false;

		}
		return false;
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) item
				.getMenuInfo();
		int position = contextMenuInfo.position;
		switch (item.getItemId()) {
		case 0:
			delPayFlag = DBUtil.deletePayListStr(pTime[position],
					pCategory[position], pMoney[position]);
			if (delPayFlag) {
				Toast toast = Toast.makeText(getApplicationContext(), "删除成功",
						Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			} else {
				Toast toast = Toast.makeText(getApplicationContext(), "删除失败",
						Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			}
			Intent intent = new Intent();
			intent.setClass(PayListActivity.this, PayListActivity.class);
			startActivity(intent);
			PayListActivity.this.finish();
			break;
		default:
			break;
		}
		return super.onContextItemSelected(item);
	}

	private void listDialog(String[] info) {
		new AlertDialog.Builder(this)
				.setTitle("详细信息")
				.setMessage(
						info[0] + "\n\n类型:\t" + info[1] + "\n金额:\t" + info[2]
								+ "元" + "\n消费账户:" + info[3] + "\n消费项目:\t"
								+ info[4] + "\n消费成员:\t" + info[5] + "\n\n备注:  "
								+ info[6])
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface paramDialogInterface,
							int paramInt) {
					}
				}).show();
	}

}
