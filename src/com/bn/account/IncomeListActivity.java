package com.bn.account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bn.financial_assistant.MainActivity;
import com.bn.financial_assistant.R;
import com.bn.stock.StockMainActivity;
import com.bn.util.DBUtil;
import com.bn.util.FontManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class IncomeListActivity extends Activity {
	private String[] iTime = DBUtil.getIncomeDateStr();
	private String[] iCategory = DBUtil.getIncomeCategoryStr();
	private String[] iMoney = DBUtil.getIncomeMoneyStr();
	private ListView incomeList;
	private Button btn_incomeback;
	private Button btn_newIncomeList;
	private boolean delIncomeFlag = false;

	@Override
	public void onCreate(Bundle saveInstanceState) {
		super.onCreate(saveInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		if (iTime.length == 0) {
			setContentView(R.layout.incomelistnothing);
		} else {
			setContentView(R.layout.account_incomelist);
			FontManager.changeFonts(FontManager.getContentView(this),this);
			List<Map<String, Object>> incomeListItems = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < iTime.length; i++) {
				Map<String, Object> incomeListItem = new HashMap<String, Object>();
				incomeListItem.put("iTime", iTime[i]);
				incomeListItem.put("iCategory", iCategory[i]);
				incomeListItem.put("iMoney", iMoney[i]);
				incomeListItems.add(incomeListItem);
			}

			SimpleAdapter simpleAdapter = new SimpleAdapter(this,
					incomeListItems, R.layout.list_item, new String[] {
							"iTime", "iCategory", "iMoney" },
					new int[] { R.id.list_time, R.id.list_category,
							R.id.list_money });
			incomeList = (ListView) findViewById(R.id.incomelist_list);

			incomeList.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					String[] incomeStr = DBUtil.getIncomeListStr(iTime[arg2],
							iCategory[arg2], iMoney[arg2]);
					listDialog(incomeStr);
				}

			}

			);
			incomeList
					.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
						@Override
						public void onCreateContextMenu(ContextMenu menu,
								View v, ContextMenuInfo menuInfo) {
							// TODO Auto-generated method stub
							menu.setHeaderTitle("执行操作");
							menu.add(0, 0, 0, "删除");

						}
					});
			incomeList.setAdapter(simpleAdapter);
		}

		btn_newIncomeList = (Button) findViewById(R.id.btn_incomelist_new);
		btn_newIncomeList.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putInt("viewItem", 1);
				intent.putExtra("double", 1);
				intent.putExtras(bundle);
				intent.setClass(IncomeListActivity.this, AccountActivity.class);
				startActivity(intent);
				IncomeListActivity.this.finish();
			}
		});
		btn_incomeback = (Button) findViewById(R.id.btn_incomelist_back);
		btn_incomeback.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putInt("viewItem", 1);
				intent.putExtra("double", 1);
				intent.putExtras(bundle);
				intent.setClass(IncomeListActivity.this, AccountActivity.class);
				startActivity(intent);
				IncomeListActivity.this.finish();
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
			Bundle bundle = new Bundle();
			bundle.putInt("viewItem", 1);
			intent.putExtra("double", 1);
			intent.putExtras(bundle);
			intent.setClass(IncomeListActivity.this, AccountActivity.class);
			startActivity(intent);
			IncomeListActivity.this.finish();

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
			delIncomeFlag = DBUtil.deleteIncomeListStr(iTime[position],
					iCategory[position], iMoney[position]);
			if (delIncomeFlag) {
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
			intent.setClass(IncomeListActivity.this, IncomeListActivity.class);
			startActivity(intent);
			IncomeListActivity.this.finish();
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