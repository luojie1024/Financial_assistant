package com.bn.stock;

import com.bn.financial_assistant.MainActivity;
import com.bn.financial_assistant.R;
import com.bn.util.DBUtil;
import static com.bn.util.Constant.*;
import com.bn.util.FontManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.RoundingMode;
import java.net.URL;
import java.net.URLConnection;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class StockMainActivity extends Activity {

	private Button bn_back;
	private Button bn_refresh;
	private Button bn_add;
	private ListView lv_info_click;
	private String msgs;
	private Handler myHandler;
	private final int MENU_LOOK = 3;
	private final int MENU_DELETE = 4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 竖屏
		setContentView(R.layout.activity_stock_main);

		initStockList();

		bn_add = (Button) this.findViewById(R.id.stock_main_add);
		bn_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent();
				intent.setClass(StockMainActivity.this,
						StockSearchActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.add_go, R.anim.main_go);
				StockMainActivity.this.finish();
			}

		});

		lv_info_click = (ListView) this.findViewById(R.id.stock_main_listView1);
		lv_info_click.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(StockMainActivity.this,
						StockDetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("position", arg2);
				intent.putExtras(bundle);
				startActivity(intent);
				overridePendingTransition(R.anim.add_go, R.anim.main_go);
				StockMainActivity.this.finish();

			}

		});
		lv_info_click
				.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {

					@Override
					public void onCreateContextMenu(ContextMenu menu, View v,
							ContextMenuInfo menuInfo) {
						// TODO Auto-generated method stub
						menu.setHeaderTitle("执行操作");
						menu.add(0, MENU_LOOK, 0, "查看");
						menu.add(0, MENU_DELETE, 0, "删除");

					}
				});

		bn_refresh = (Button) this.findViewById(R.id.stock_main_bn_refresh);
		bn_refresh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				refresh();
			}
		});

		bn_back = (Button) this.findViewById(R.id.stock_main_bn_back);
		bn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(StockMainActivity.this, MainActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.main_back, R.anim.add_back);
				StockMainActivity.this.finish();
			}
		});

		// 更改字体
		FontManager.initTypeFace(this);
		ViewGroup vg = FontManager.getContentView(this);
		FontManager.changeFonts(vg, this);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && 
						  event.getRepeatCount() == 0) {
			Intent intent = new Intent();
			intent.setClass(StockMainActivity.this, MainActivity.class);
			startActivity(intent);
			StockMainActivity.this.finish();
			return false;
		}
		return false;
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) item
				.getMenuInfo();
		int position = contextMenuInfo.position;
		String allInfo[][] = ToolClass.selectAll(getBaseContext());
		switch (item.getItemId()) {
		case MENU_LOOK:

			Intent intent = new Intent();
			intent.setClass(StockMainActivity.this, StockDetailActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("position", position);
			intent.putExtras(bundle);
			startActivity(intent);
			overridePendingTransition(R.anim.add_go, R.anim.main_go);
			StockMainActivity.this.finish();

			break;
		case MENU_DELETE:

			Object[] objects = { allInfo[position][0] };
			boolean flag = DBUtil.deleteInfo(objects);
			initStockList();

			break;
		default:
			break;
		}
		return super.onContextItemSelected(item);
	}

	public void initStockList() {

		final String[][] stock_all = ToolClass.selectAll(getBaseContext());

		final int stockCount = stock_all.length;

		// 为ListView准备内容适配器
		BaseAdapter badapter = new BaseAdapter() {
			LayoutInflater inflater = LayoutInflater
					.from(StockMainActivity.this);

			@Override
			public int getCount() {
				return stockCount;// 总共cityCount个选项
			}

			@Override
			public Object getItem(int arg0) {
				return null;
			}

			@Override
			public long getItemId(int arg0) {
				return 0;
			}

			@Override
			public View getView(int arg0, View arg1, ViewGroup arg2) {
				RelativeLayout ll = (RelativeLayout) arg1;
				if (ll == null) // =============================
				{
					System.out.println("ll == null");
					ll = (RelativeLayout) (inflater.inflate(
							R.layout.main_stock_info, null)
							.findViewById(R.id.main_stock_info2));
				}

				// 初始化TextView
				TextView tv = (TextView) ll.getChildAt(0);
				tv.setText(stock_all[arg0][1]);// 设置内容

				TextView tv2 = (TextView) ll.getChildAt(1);
				tv2.setText(stock_all[arg0][0]);// 设置内容

				TextView tv3 = (TextView) ll.getChildAt(3);
				tv3.setText(stock_all[arg0][5]);// 设置内容

				TextView tv4 = (TextView) ll.getChildAt(2);

				float fvalue = (Float.parseFloat(stock_all[arg0][4]) - Float
						.parseFloat(stock_all[arg0][3]))
						/ Float.parseFloat(stock_all[arg0][3]) * 100;
				float result = ToolClass.round(fvalue, 2,
						RoundingMode.HALF_DOWN);
				if (fvalue > 0) {
					tv4.setTextColor(Color.RED);
					tv4.setText(Float.toString(result) + "%");// 设置内容

				} else if (fvalue < 0) {
					tv4.setTextColor(Color.rgb(0, 96, 0));
					tv4.setText(Float.toString(result) + "%");// 设置内容

				} else {
					tv4.setTextColor(Color.BLACK);
					tv4.setText(Float.toString(result) + "%");// 设置内容

				}
				return ll;

			}
		};

		ListView lv = (ListView) this.findViewById(R.id.stock_main_listView1);
		lv.setAdapter(badapter);

	}

	public void refresh() {
		final String[][] stock_all = ToolClass.selectAll(getBaseContext());

		myHandler = new Handler() {
			@SuppressLint("HandlerLeak")
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 0:
					Bundle b = msg.getData();
					// 获取内容字符串
					msgs = b.getString("msg");
					System.out.println("-------------------------------------");
					System.out.println("**case_0  已加载列表信息");
					if (msgs != null) {
						String[] newInfo = ToolClass.CutMsgs(msgs);

						Object[] objects = { newInfo[CODE], newInfo[NAME],
								newInfo[PRICE_TODAY], newInfo[PRICE_YESTADAY],
								newInfo[PRICE_NOW], newInfo[TODAY_HIGHEST],
								newInfo[TODAY_LOWEST], newInfo[TRADING_VOLUME],
								newInfo[CHANGING_OVER], newInfo[DATE],
								newInfo[TIME], newInfo[EXCHANGEHALL],
								newInfo[CODE] };

						boolean flag = DBUtil.updateInfo(objects);
						System.out.println(newInfo[1] + "-是否更新成功：" + flag);
					}
					break;
				}
			}
		};

		for (int i = 0; i < stock_all.length; i++) {
			StringBuilder sb = new StringBuilder();
			sb.append(stock_all[i][EXCHANGEHALL]);
			sb.append(stock_all[i][CODE]);

			final String URLcode = sb.toString();
			final String exchangeHall = stock_all[i][EXCHANGEHALL];
			final String stockcode = stock_all[i][CODE];

			new Thread() {
				@Override
				public void run() {
					try {
						StringBuilder sb = new StringBuilder(
								"http://hq.sinajs.cn/list=");
						sb.append(URLcode);
						String urlStr = new String(sb.toString().getBytes(),
								"gbk");
						URL url = new URL(urlStr);
						URLConnection uc = url.openConnection();
						InputStream in = uc.getInputStream();
						int ch = 0;
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						while ((ch = in.read()) != -1) {
							baos.write(ch);
						}
						byte[] bb = baos.toByteArray();
						baos.close();
						in.close();
						String jSonStr = new String(bb, "gbk");

						jSonStr = jSonStr
								+ ("," + exchangeHall + "," + stockcode);

						Bundle b = new Bundle();
						// 将内容字符串放进数据Bundle中
						b.putString("msg", jSonStr);
						// 创建消息对象
						Message msg = new Message();
						// 设置数据Bundle到消息中
						msg.setData(b);
						// 设置消息的what值
						msg.what = 0;
						myHandler.sendMessage(msg);
						System.out.println("**已将列表信息加入消息对象");

					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("线程--列表JSON异常捕捉");
					}
				}
			}.start();

		}

	}

}
