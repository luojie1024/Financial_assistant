package com.bn.stock;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import com.bn.financial_assistant.R;
import com.bn.util.DBUtil;
import com.bn.util.FontManager;
import static com.bn.util.Constant.*;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

@SuppressLint("ShowToast")
public class StockSearchActivity extends Activity {

	private ToolClass tc;
	private TextView tv;
	private EditText et;
	private Spinner sp;
	private ImageView iv;
	private Button bn_back;
	private Button bn_search;
	private Button bn_add;
	private Handler myHandler;
	private String msgs;
	private String jSonStr;
	private String stocks[];
	private String stock_name = "601006";
	private String stock_code;
	private String exchangeHall;
	private String[] FinalArray;
	private boolean existflag = false;
	private int addflag = 0; // 0Ϊ������� 1��־Ϊ�ɹ���ȡ�ַ���������ӵ����ݿ� �� 2��־������ݿ�ɹ�
	@SuppressLint("ShowToast")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_stock_search);

		tv = (TextView) this.findViewById(R.id.stock_search_detail_info);
		et = (EditText) this.findViewById(R.id.stock_search_et_code);
		bn_search = (Button) this.findViewById(R.id.stock_search_bn_search);
		sp = (Spinner) this.findViewById(R.id.stock_search_spinner1);
		iv = (ImageView) this.findViewById(R.id.stock_search_image);

		bn_back = (Button) this.findViewById(R.id.stock_search_bn_back);
		bn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent();
				intent.setClass(StockSearchActivity.this,
						StockMainActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.main_back, R.anim.add_back);
				StockSearchActivity.this.finish();
			}

		});

		myHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 0:
					Bundle b = msg.getData();
					// ��ȡ�����ַ���
					msgs = b.getString("msg");
					if (msgs != null) {
						FinalArray = ToolClass.CutStrArray(exchangeHall,
								stock_code, msgs);
						tv.setText("��Ʊ���룺" + FinalArray[0] + "\n" + "��Ʊ���ƣ�"
								+ FinalArray[1]);
						addflag = 1;
						existflag = ToolClass.isExist(FinalArray[0],
								getBaseContext());

					}
					break;
				case 1:
					if (msg.obj != null) {
						iv.setImageBitmap((Bitmap) msg.obj);

					}
					break;
				case -1:
					Toast.makeText(StockSearchActivity.this,
							"��Ʊ�����ڻ���������ʧ�ܣ�������ٲ�ѯ��", 300).show();
					et.setText("");
					break;
				}
			}
		};

		sp.setOnItemSelectedListener(new OnItemSelectedListener() {

			@SuppressLint("ShowToast")
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				String selected = arg0.getItemAtPosition(arg2).toString();
				if (arg2 == 0) {
					exchangeHall = "sh";
				} else {
					exchangeHall = "sz";
				}
				Toast.makeText(StockSearchActivity.this, "��ѡ��" + selected, 300)
						.show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				String tips = "��ѡ���Ӧ�Ľ�������Ȼ����Ӵ���";
				Toast.makeText(StockSearchActivity.this, tips,
						Toast.LENGTH_SHORT).show();
			}

		});

		bn_search.setOnClickListener(new OnClickListener() {

			@SuppressLint("ShowToast")
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				if (et.getText().toString() != null) {
					stock_code = et.getText().toString();

					new Thread() {
						@Override
						public void run() {
							try {
								StringBuilder sb = new StringBuilder(
										"http://hq.sinajs.cn/list=");
								sb.append(exchangeHall);
								sb.append(stock_code);
								String urlStr = null;

								urlStr = new String(sb.toString().getBytes(),
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
								Message msg = new Message();
								jSonStr = new String(bb, "gbk");

								if (jSonStr.length() > 25) {
									Bundle b = new Bundle();
									b.putString("msg", jSonStr);
									msg.setData(b);
									msg.what = 0;
									myHandler.sendMessage(msg);
								} else {
									msg.what = -1;
									myHandler.sendMessage(msg);
								}

							} catch (Exception e) {
								e.printStackTrace();

							}
						}
					}.start();

					new Thread() {
						@Override
						public void run() {

							try {
								StringBuilder sb = new StringBuilder(
										"http://image.sinajs.cn/newchart/daily/n/");
								sb.append(exchangeHall);
								sb.append(stock_code);
								sb.append(".gif");
								String urlStr = null;
								urlStr = new String(sb.toString().getBytes(),
										"gbk");
								URL url = new URL(urlStr);
								URLConnection conn = url.openConnection();
								InputStream is = conn.getInputStream();

								Message msg = new Message();
								msg.what = 1;
								msg.obj = BitmapFactory.decodeStream(is);
								myHandler.sendMessage(msg);
								is.close();

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
						}
					}.start();
				} else {
					Toast.makeText(StockSearchActivity.this, "����û�������Ʊ���룡", 200)
							.show();
				}

			}

		});

		bn_add = (Button) this.findViewById(R.id.stock_search_bn_add);
		bn_add.setOnClickListener(new OnClickListener() {

			@SuppressLint("ShowToast")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (existflag == true) {
					Toast.makeText(StockSearchActivity.this, "�˹�Ʊ����ѡ�����Ѵ��ڣ�",
							500).show();
				} else {

					if (addflag == 1) {

						Object[] objects = { FinalArray[CODE],
								FinalArray[NAME], FinalArray[PRICE_TODAY],
								FinalArray[PRICE_YESTADAY],
								FinalArray[PRICE_NOW],
								FinalArray[TODAY_HIGHEST],
								FinalArray[TODAY_LOWEST],
								FinalArray[TRADING_VOLUME],
								FinalArray[CHANGING_OVER], FinalArray[DATE],
								FinalArray[TIME], FinalArray[EXCHANGEHALL] };
						boolean flag = DBUtil.addInfo(objects);
						Toast.makeText(StockSearchActivity.this, "��ӳɹ���", 2000)
								.show();
						if (flag == true) {
							addflag = 2;
						}

						Intent intent = new Intent();
						intent.setClass(StockSearchActivity.this,
								StockMainActivity.class);
						startActivity(intent);
						overridePendingTransition(R.anim.main_back,
								R.anim.add_back);
						StockSearchActivity.this.finish();

					} else {
						switch (addflag) {
						case 0:
							Toast.makeText(StockSearchActivity.this,
									"��Ʊ��Ϣ��ȡʧ�ܣ�", 2000).show();
							et = (EditText) findViewById(R.id.stock_search_et_code);
							et.setText("");
							break;
						case 1:
							Toast.makeText(StockSearchActivity.this,
									"��Ӳ��ɹ��������ԣ�", 2000).show();
							break;
						case 2:
							Toast.makeText(StockSearchActivity.this, "�˹�����ӣ�",
									2000).show();
							break;
						}
					}
				}
			}

		});

		// ��������
		FontManager.initTypeFace(this);
		ViewGroup vg = FontManager.getContentView(this);
		FontManager.changeFonts(vg, this);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

			Intent intent = new Intent();
			intent.setClass(StockSearchActivity.this, StockMainActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.main_back, R.anim.add_back);
			StockSearchActivity.this.finish();

			return true;
		}
		return false;

	}

}
