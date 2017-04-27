package com.bn.stock;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.bn.financial_assistant.R;
import com.bn.util.FontManager;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;
import android.widget.TextView;

public class StockDetailActivity extends Activity {

	private String msgs; // 用于接收Handler收到的字符串
	private GridView gv; // 抽屉中的GridView对象
	private ImageView im;// 抽屉上的“拉手”图片
	private Button bnback; // 创建返回按钮对象
	private ListView lv_info;// 创建详情列表对象
	private MyAdapter adapter;// 声明图片适配器的对象
	private Handler myHandler;// 声明消息对象
	private String[] stock_Items;// 声明项目数组
	private String[] stock_Info;// 声明股票信息数组
	private int choosePosition = 0;// 记录选择的位置
	private Bitmap[] bm_array = new Bitmap[2];// 声明抽屉中的图片数组
	private String[] items = { "实时K线", "日K线图" };// 生命抽屉中的标题数组

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_stock_detail);

		// 加载默认图片
		Bitmap nonePic = BitmapFactory.decodeResource(
				this.getBaseContext().getResources(), 
				R.drawable.skin_loading_icon);
		bm_array[0] = nonePic;
		bm_array[1] = nonePic;

		// 使用自定义的MyGridViewAdapter设置GridView里面的item内容
		gv = (GridView) findViewById(R.id.myContent1);
		im = (ImageView) findViewById(R.id.myImage1);

		Bundle bundel = this.getIntent().getExtras();
		choosePosition = bundel.getInt("position");
		// 获取表中的所有信息，存储在一个二维数组中
		final String[][] stock_all = ToolClass.selectAll(getBaseContext());
		final MyAdapter adapter = new MyAdapter(StockDetailActivity.this,
				items, bm_array);
		// 启动线程
		Threads(stock_all[choosePosition][11], stock_all[choosePosition][0]);
		// 启动Handler消息循环
		Handlers(adapter);

		lv_info = (ListView) this.findViewById(R.id.stock_detail_listView1);
		bnback = (Button) this.findViewById(R.id.stock_detail_bnback);
		bnback.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(StockDetailActivity.this,
						StockMainActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.main_back, R.anim.add_back);
				StockDetailActivity.this.finish();
			}

		});

		stock_Items = getResources().getStringArray(R.array.items);
		final int stockCount = stock_Items.length;

		stock_Info = stock_all[choosePosition];

		// 为ListView准备内容适配器
		BaseAdapter badapter = new BaseAdapter() {
			LayoutInflater inflater = LayoutInflater
					.from(StockDetailActivity.this);

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

				LinearLayout ll = (LinearLayout) arg1;
				if (ll == null) // =============================
				{
					ll = (LinearLayout) (inflater.inflate(
							R.layout.detail_stock_info, null)
							.findViewById(R.id.detail_stock_info_raw));
				}

				// 初始化TextView
				TextView tv = (TextView) ll.getChildAt(0);
				tv.setText(stock_Items[arg0]);// 设置内容

				TextView tv2 = (TextView) ll.getChildAt(1);
				tv2.setText(stock_Info[arg0]);// 设置内容

				return ll;
			}
		};
		lv_info.setAdapter(badapter);

		// 更改字体
		FontManager.initTypeFace(this);
		ViewGroup vg = FontManager.getContentView(this);
		FontManager.changeFonts(vg, this);

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

			Intent intent = new Intent();
			intent.setClass(StockDetailActivity.this, StockMainActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.main_back, R.anim.add_back);
			StockDetailActivity.this.finish();

			return false;
		}
		return false;

	}

	// 获取实时K线图
	public void Threads(final String exchangeHall, final String code) {

		new Thread() {
			@Override
			public void run() {
				Bitmap bm_min = null;

				try {
					StringBuilder sb = new StringBuilder(
							"http://image.sinajs.cn/newchart/min/n/");// 字符串默认部分
					sb.append(exchangeHall);// 证券交易所
					sb.append(code);// 股票代码
					sb.append(".gif");// 字符串默认部分
					String urlStr = null;
					urlStr = new String(sb.toString().getBytes(), "gbk");
					URL url = new URL(urlStr);
					URLConnection conn = url.openConnection();
					InputStream is = conn.getInputStream();
					bm_min = sizeChanged(BitmapFactory.decodeStream(is), 720,
							430);
				} catch (MalformedURLException e) {

					e.printStackTrace();// 打印异常
					myHandler.sendEmptyMessage(0);

				} catch (IOException e) {
					myHandler.sendEmptyMessage(0);
					e.printStackTrace();// 打印异常
				} catch (NullPointerException e) {
					myHandler.sendEmptyMessage(0);
					e.printStackTrace();// 打印异常
				}

				Message msg = new Message();
				msg.what = 0;
				msg.obj = bm_min;
				myHandler.sendMessage(msg);
			}
		}.start();// 开启线程

		// 获取日K线图
		new Thread() {
			@Override
			public void run() {
				Bitmap bm_day = null;
				try {
					StringBuilder sb = new StringBuilder(
							"http://image.sinajs.cn/newchart/daily/n/");
					sb.append(exchangeHall);
					sb.append(code);
					sb.append(".gif");

					String urlStr = null;
					urlStr = new String(sb.toString().getBytes(), "gbk");
					URL url = new URL(urlStr);

					URLConnection conn = url.openConnection();
					InputStream is = conn.getInputStream();
					bm_day = sizeChanged(BitmapFactory.decodeStream(is), 720,
							430);
				} catch (MalformedURLException e) {
					e.printStackTrace();
					myHandler.sendEmptyMessage(0);

				} catch (IOException e) {
					myHandler.sendEmptyMessage(0);
					e.printStackTrace();
				} catch (NullPointerException e) {
					myHandler.sendEmptyMessage(0);
					e.printStackTrace();
				}

				Message msg = new Message();
				msg.what = 1;
				msg.obj = bm_day;
				myHandler.sendMessage(msg);
			}
		}.start();
		
	}

	public void Handlers(final MyAdapter adapter) {

		this.adapter = adapter;
		myHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {

				case 0:
					if (msg.obj != null) {
						bm_array[0] = ((Bitmap) msg.obj);
					}

					break;
				case 1:
					if (msg.obj != null) {
						bm_array[1] = ((Bitmap) msg.obj);
					}

					break;
				}

				if (bm_array[0] != null && bm_array[1] != null) {
					gv.setAdapter(adapter);
				}

			}
		};
	}

	public Bitmap sizeChanged(Bitmap bm, // 图片的Bitmap
			int newWidth, // 需要得到的宽度
			int newHeight // 需要得到的高度
	) {

		// 获得图片的宽高
		int width = bm.getWidth();
		int height = bm.getHeight();
		// 计算缩放比例
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// 取得想要缩放的matrix参数
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		// 得到新的图片
		Bitmap newbitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix,
				true);
		return newbitmap;

	}

}
