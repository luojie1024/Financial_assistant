package com.bn.account;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.bn.financial_assistant.MainActivity;
import com.bn.financial_assistant.R;
import com.bn.stock.StockMainActivity;
import com.bn.util.DBUtil;
import com.bn.util.FontManager;

@SuppressLint({ "NewApi", "SimpleDateFormat", "ShowToast" })
public class AccountActivity extends Activity {
	private int viewitem = 0;
	private boolean flag = false;
	private ViewPager viewPager;// 页卡内容
	private ImageView imageView;// 动画图片（小条）
	private TextView textpView, textiView;// 两个头标
	private List<View> views;// Tab页面列表
	private int offset = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int bmpW;// 动画图片宽度
	private View pView, iView; // 各个页卡
	private Button back;

	private TextView pay_time;
	private TextView income_time;
	// 获得系统当前年月日
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	public String date = format.format(new java.util.Date());
	// pView
	private TextView payMoneyTV;
	private TextView payCateTV;
	private TextView payZhTV;
	private TextView payProjectTV;
	private TextView payMemberTV;
	private Spinner payCategorySp;
	private String payCategoryStr;
	private ArrayAdapter<CharSequence> adapterPayCate= null;
	private Spinner payZhanghuSp;
	private String payZhanghuStr;
	private ArrayAdapter<CharSequence> adapterPayZh= null;
	private Spinner payProjectSp;
	private String payProjectStr;
	private ArrayAdapter<CharSequence> adapterPayProject= null;
	private Spinner payMemberSp;
	private String payMemberStr;
	private ArrayAdapter<CharSequence> adapterPayMember= null;
	private EditText pay_ps;
	private Button pay_saveBtn;
	private Button pay_check;
	private EditText pay_money;
	// iView
	private TextView inMoneyTV;
	private TextView inCateTV;
	private TextView inZhTV;
	private TextView inProjectTV;
	private TextView inMemberTV;
	private Spinner incomeCategorySp;
	private String incomeCategoryStr;
	private ArrayAdapter<CharSequence> adapterInCate= null;
	private Spinner incomeZhanghuSp;
	private String incomeZhanghuStr;
	private ArrayAdapter<CharSequence> adapterInZh= null;
	private Spinner incomeProjectSp;
	private String incomeProjectStr;
	private ArrayAdapter<CharSequence> adapterInProject= null;
	private Spinner incomeMemberSp;
	private String incomeMemberStr;
	private ArrayAdapter<CharSequence> adapterInMember= null;
	private EditText income_ps;
	private EditText income_money;
	private Button income_saveBtn;
	private Button income_check;

	DatePickerDialog.OnDateSetListener OnDateSetListener;
	ViewGroup pViewGroup;
	ViewGroup iViewGroup;
//	ViewGroup payViewGroup;

	@SuppressLint("ShowToast")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);		
		setContentView(R.layout.account_main);
		
		DBUtil.createTable();
		InitImageView();
		InitTextView();
		InitViewPager();

		FontManager.changeFonts(FontManager.getContentView(this),this);
		// 返回键 监听
		back = (Button) this.findViewById(R.id.layout_account_bn_back);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 返回
				Intent intent = new Intent();
				intent.setClass(AccountActivity.this, MainActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.main_back, R.anim.add_back);
				AccountActivity.this.finish();
			}
		});
		// 支出 保存键监听
		pay_saveBtn.setTypeface(FontManager.tf);
		pay_saveBtn.setOnClickListener(new OnClickListener() {
			@SuppressLint("ShowToast")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String[] pay_content = new String[] {
						pay_money.getText().toString(), payCategoryStr,
						payZhanghuStr, pay_time.getText().toString(), payProjectStr, payMemberStr,
						pay_ps.getText().toString() };
				if (pay_money.getText().toString().length() == 0) {
					Toast toast = Toast.makeText(getApplicationContext(),
							"请输入消费金额", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
					return;
				} else {
					flag = DBUtil.addPay(pay_content);
					Toast toast;
					if (flag) {
						toast = Toast.makeText(getApplicationContext(), "添加成功",
								Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
						pay_money.setText("");
						payCategorySp.setSelection(0,true);
						payZhanghuSp.setSelection(0, true);
						payProjectSp.setSelection(0, true);
						payMemberSp.setSelection(0, true);
						pay_ps.setText("");
					} else {
						toast = Toast.makeText(getApplicationContext(), "添加失败",
								Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					}
				}
			}
		});

		// 支出 保存键监听
		income_saveBtn.setTypeface(FontManager.tf);
		income_saveBtn.setOnClickListener(new OnClickListener() {
			@SuppressLint("ShowToast")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String[] income_content = new String[] {
						income_money.getText().toString(), incomeCategoryStr,
						incomeZhanghuStr, income_time.getText().toString(), incomeProjectStr,
						incomeMemberStr, income_ps.getText().toString() };
				if (income_money.getText().toString().length() == 0) {
					Toast toast = Toast.makeText(getApplicationContext(),
							"请输入消费金额", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
					return;
				} else {
					flag = DBUtil.addIncome(income_content);
					Toast toast;
					if (flag) {
						toast = Toast.makeText(getApplicationContext(), "添加成功",
								Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
						income_money.setText("");
						incomeCategorySp.setSelection(0,true);
						incomeZhanghuSp.setSelection(0, true);
						incomeProjectSp.setSelection(0, true);
						incomeMemberSp.setSelection(0, true);
						income_ps.setText("");
					} else {
						toast = Toast.makeText(getApplicationContext(), "添加失败",
								Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					}
				}
			}
		});
		pay_check.setTypeface(FontManager.tf);
		pay_check.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(AccountActivity.this, PayListActivity.class);
				startActivity(intent);
				AccountActivity.this.finish();
			}
		});
		income_check.setTypeface(FontManager.tf);
		income_check.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(AccountActivity.this, IncomeListActivity.class);
				startActivity(intent);
				AccountActivity.this.finish();
			}
		});
		
		FontManager.changeFonts(FontManager.getContentView(this), this);
		FontManager.changeFonts(pViewGroup, this);
		FontManager.changeFonts(iViewGroup, this);
;
	}

	// ====================================pay 页面初始化
	// begin====================================================

	// 初始化页卡
	@SuppressLint("NewApi")
	private void InitViewPager() {
		viewPager = (ViewPager) findViewById(R.id.vPager);
		views = new ArrayList<View>();
		LayoutInflater inflater = getLayoutInflater();

		pView = inflater.inflate(R.layout.account_pay, pViewGroup);
		pViewGroup=(ViewGroup) pView;	
		payMoneyTV =(TextView) pView.findViewById(R.id.pay_Text_money);
		payCateTV =(TextView) pView.findViewById(R.id.pay_Text_category);
		payZhTV =(TextView) pView.findViewById(R.id.pay_Text_zhanghu);
		payProjectTV =(TextView) pView.findViewById(R.id.pay_Text_project);
		payMemberTV =(TextView) pView.findViewById(R.id.pay_Text_member);
		pay_money = (EditText) pView.findViewById(R.id.money_et);
		pay_ps = (EditText) pView.findViewById(R.id.pay_et_ps);
		pay_saveBtn = (Button) pView.findViewById(R.id.btn_pay_save);
		pay_check = (Button) pView.findViewById(R.id.btn_pay_check);

		InitpayCategorySp();
		InitpayZhanghuSp();
		InitpayProjectSp();
		InitpayTimetv();
		InitpayMemberSp();
		////////////////////////////////////////////////////////////////
		iView = inflater.inflate(R.layout.account_income, iViewGroup);
		iViewGroup = (ViewGroup) iView;
		inMoneyTV = (TextView) iView.findViewById(R.id.income_Text_money);
		inCateTV = (TextView) iView.findViewById(R.id.income_Text_category);
		inZhTV = (TextView) iView.findViewById(R.id.income_Text_zhanghu);
		inProjectTV = (TextView) iView.findViewById(R.id.income_Text_project);
		inMemberTV = (TextView) iView.findViewById(R.id.income_Text_member);
		income_money = (EditText) iView.findViewById(R.id.money_et2);
		income_ps = (EditText) iView.findViewById(R.id.income_et_ps);
		income_saveBtn = (Button) iView.findViewById(R.id.btn_income_save);
		income_check = (Button) iView.findViewById(R.id.btn_income_check);
		
		InitincomeCategorySp();
		InitincomeZhanghuSp();
		InitincomeProjectSp();
		InitincomeTimetv();
		InitincomeMemberSp();
		
		//日期按键监听
		OnDateSetListener = new DatePickerDialog.OnDateSetListener() {

			public void onDateSet(DatePicker paramDatePicker, int paramInt1,
					int paramInt2, int paramInt3) {
				String sTime = paramInt1 + "-" + (paramInt2 + 1) + "-"
						+ paramInt3;
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Calendar c = Calendar.getInstance();
				try {

					c.setTime(format.parse(sTime));

				} catch (java.text.ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String Week = "星期";
				if (c.get(Calendar.DAY_OF_WEEK) == 1) {
					Week += "日";
				}
				if (c.get(Calendar.DAY_OF_WEEK) == 2) {
					Week += "一";
				}
				if (c.get(Calendar.DAY_OF_WEEK) == 3) {
					Week += "二";
				}
				if (c.get(Calendar.DAY_OF_WEEK) == 4) {
					Week += "三";
				}
				if (c.get(Calendar.DAY_OF_WEEK) == 5) {
					Week += "四";
				}
				if (c.get(Calendar.DAY_OF_WEEK) == 6) {
					Week += "五";
				}
				if (c.get(Calendar.DAY_OF_WEEK) == 7) {
					Week += "六";
				}
				income_time.setText(sTime + " " + Week);
				pay_time.setText(sTime + " " + Week);
			}
		};

		views.add(pView);
		views.add(iView);
		viewPager.setAdapter(new MyViewPagerAdapter(views));
		Bundle bundle = this.getIntent().getExtras();
		if (bundle != null) {
			viewitem = bundle.getInt("viewItem");
		}
		viewPager.setCurrentItem(viewitem);
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	// 初始化支出页卡的spinner
	private void InitpayCategorySp() {
		
		payCategorySp = (Spinner) pView.findViewById(R.id.paycategory_sp);
		adapterPayCate = ArrayAdapter.createFromResource(this, 
                R.array.pay_Category,R.layout.spinnerlay); // 实例化了ArrayAdapter 
        adapterPayCate
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        payCategorySp.setAdapter(adapterPayCate);
        
		payCategorySp.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				TextView tv =(TextView) arg1;
				tv.setTypeface(FontManager.tf);
				payCategoryStr = arg0.getItemAtPosition(arg2).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});
	}

	// 初始化pView账户spinner
	private void InitpayZhanghuSp() {
		payZhanghuSp = (Spinner) pView.findViewById(R.id.payzhanghu_sp);
		adapterPayZh = ArrayAdapter.createFromResource(this, 
                R.array.Zhanghu,R.layout.spinnerlay); // 实例化了ArrayAdapter 
        adapterPayZh
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        payZhanghuSp.setAdapter(adapterPayZh);
		payZhanghuSp.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				TextView tv =(TextView) arg1;
				tv.setTypeface(FontManager.tf);
				payZhanghuStr = arg0.getItemAtPosition(arg2).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});

	}

	// 初始化pView项目spinner
	private void InitpayProjectSp() {
		payProjectSp = (Spinner) pView.findViewById(R.id.payproject_sp);
		adapterPayProject = ArrayAdapter.createFromResource(this, 
                R.array.Project,R.layout.spinnerlay); // 实例化了ArrayAdapter 
        adapterPayProject
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        payProjectSp.setAdapter(adapterPayProject);
		payProjectSp.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				TextView tv =(TextView) arg1;
				tv.setTypeface(FontManager.tf);
				payProjectStr = arg0.getItemAtPosition(arg2).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});
	}

	// 初始化pView成员spinner
	private void InitpayMemberSp() {
		payMemberSp = (Spinner) pView.findViewById(R.id.paymember_sp);
		adapterPayMember = ArrayAdapter.createFromResource(this, 
                R.array.Member,R.layout.spinnerlay); // 实例化了ArrayAdapter 
        adapterPayMember
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        payMemberSp.setAdapter(adapterPayMember);
		payMemberSp.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				TextView tv =(TextView) arg1;
				tv.setTypeface(FontManager.tf);
				payMemberStr = arg0.getItemAtPosition(arg2).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});
	}

	// 初始化日期选项栏
	private void InitpayTimetv() {
		pay_time = (TextView) pView.findViewById(R.id.tv_time);
		pay_time.setText("日期:");
		pay_time.setTypeface(FontManager.tf);
		
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(format.parse(date));
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		String Week = "星期";
		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			Week += "日";
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 2) {
			Week += "一";
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 3) {
			Week += "二";
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 4) {
			Week += "三";
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 5) {
			Week += "四";
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 6) {
			Week += "五";
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 7) {
			Week += "六";
		}

		pay_time.setText(date + " " + Week);
		pay_time.setOnClickListener(new DateOnClick());
	}

	// =====================================pay 页面初始化
	// end==============================================

	// =====================================income 页面初始化
	// begin==============================================
	// 初始化支出页卡的spinner
	private void InitincomeCategorySp() {
		incomeCategorySp = (Spinner) iView.findViewById(R.id.incomecategory_sp);
		adapterInCate = ArrayAdapter.createFromResource(this, 
                R.array.income_Category,R.layout.spinnerlay); // 实例化了ArrayAdapter 
        adapterInCate
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        incomeCategorySp.setAdapter(adapterInCate);
		incomeCategorySp
				.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						TextView tv =(TextView) arg1;
						tv.setTypeface(FontManager.tf);
						incomeCategoryStr = arg0.getItemAtPosition(arg2)
								.toString();
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
					}
				});
	}

	// 初始化pView账户spinner
	private void InitincomeZhanghuSp() {
		incomeZhanghuSp = (Spinner) iView.findViewById(R.id.incomezhanghu_sp);
		adapterInZh = ArrayAdapter.createFromResource(this, 
                R.array.Zhanghu,R.layout.spinnerlay); // 实例化了ArrayAdapter 
        adapterInZh
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        incomeZhanghuSp.setAdapter(adapterInZh);
		incomeZhanghuSp.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				TextView tv =(TextView) arg1;
				tv.setTypeface(FontManager.tf);
				incomeZhanghuStr = arg0.getItemAtPosition(arg2).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});

	}

	// 初始化pView项目spinner
	private void InitincomeProjectSp() {
		incomeProjectSp = (Spinner) iView.findViewById(R.id.incomeproject_sp);
		adapterInProject = ArrayAdapter.createFromResource(this, 
                R.array.Project,R.layout.spinnerlay); // 实例化了ArrayAdapter 
        adapterInProject
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        incomeProjectSp.setAdapter(adapterInProject);
		incomeProjectSp.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				TextView tv =(TextView) arg1;
				tv.setTypeface(FontManager.tf);
				incomeProjectStr = arg0.getItemAtPosition(arg2).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});
	}

	// 初始化pView成员spinner
	private void InitincomeMemberSp() {
		incomeMemberSp = (Spinner) iView.findViewById(R.id.incomemember_sp);
		adapterInMember = ArrayAdapter.createFromResource(this, 
                R.array.Member,R.layout.spinnerlay); // 实例化了ArrayAdapter 
        adapterInMember
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        incomeMemberSp.setAdapter(adapterInMember);
		incomeMemberSp.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				TextView tv =(TextView) arg1;
				tv.setTypeface(FontManager.tf);
				incomeMemberStr = arg0.getItemAtPosition(arg2).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});
	}

	// 初始化日期选项栏
	private void InitincomeTimetv() {
		income_time = (TextView) iView.findViewById(R.id.tv_time2);
		income_time.setText("日期");
		income_time.setTypeface(FontManager.tf);
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(format.parse(date));
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		String Week = "星期";
		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			Week += "日";
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 2) {
			Week += "一";
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 3) {
			Week += "二";
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 4) {
			Week += "三";
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 5) {
			Week += "四";
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 6) {
			Week += "五";
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 7) {
			Week += "六";
		}

		income_time.setText(date + " " + Week);
		income_time.setOnClickListener(new DateOnClick());
	}

	// =====================================income 页面初始化
	// end===============================================

	// =====================================总 页面初始化
	// begin==============================================
	// 初始化头标
	private void InitTextView() {
		textpView = (TextView) findViewById(R.id.text1);
		textiView = (TextView) findViewById(R.id.text2);

		textpView.setOnClickListener(new MyOnClickListener(0));
		textiView.setOnClickListener(new MyOnClickListener(1));
	}

	/*
	 * 初始化动画，这个就是页卡滑动时， 下面的横线也滑动的效果，在这里需要计算一些数据
	 */
	private void InitImageView() {
		imageView = (ImageView) findViewById(R.id.cursor);
		// 获得图片宽度
		bmpW = BitmapFactory.decodeResource(getResources(),
				R.drawable.select_flag).getWidth();
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;// 获取分辨率宽度
		offset = (screenW / 2 - bmpW) / 2;// 计算偏移量
		Intent intent = getIntent();
		int info = intent.getIntExtra("double", 0);
		if (info == 1) {
			int one = offset * 4;
			Animation animation = new TranslateAnimation(one * currIndex, one,
					0, 0);
			animation.setFillAfter(true);// true：图片停在动画结束位置
			animation.setDuration(0);
			imageView.startAnimation(animation);
		}
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		imageView.setImageMatrix(matrix);// 设置动画初始位置
	}

	// 头标点击监听
	private class MyOnClickListener implements OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		public void onClick(View v) {
			viewPager.setCurrentItem(index);
		}

	}

	// 继承ViewPager适配器
	public class MyViewPagerAdapter extends PagerAdapter {
		private List<View> mListViews;

		public MyViewPagerAdapter(List<View> mListViews) {
			this.mListViews = mListViews;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(mListViews.get(position));
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(mListViews.get(position), 0);
			return mListViews.get(position);
		}

		@Override
		public int getCount() {
			return mListViews.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}
	}

	// ViewPager在处理滑动事件的时候要用到OnPageChangeListener
	public class MyOnPageChangeListener implements OnPageChangeListener {
		int one = offset * 2 + bmpW;// 页卡1->页卡2 偏移量

		public void onPageScrollStateChanged(int arg0) {
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		public void onPageSelected(int arg0) {
			Animation animation = new TranslateAnimation(one * currIndex, one
					* arg0, 0, 0);
			currIndex = arg0;
			animation.setFillAfter(true);// true：图片停在动画结束位置
			animation.setDuration(300);
			imageView.startAnimation(animation);
		}
	}

	// 支付日期选项栏弹窗
	protected Dialog onCreateDialog(int paramInt) {
		Calendar localCalendar = Calendar.getInstance();
		int i = localCalendar.get(Calendar.YEAR);
		int j = localCalendar.get(Calendar.MONTH);
		int k = localCalendar.get(Calendar.DAY_OF_MONTH);
		switch (paramInt) {
		default:
			return null;
		case 1:
		}
		return new DatePickerDialog(this, this.OnDateSetListener, i, j, k);
	}
	class DateOnClick implements View.OnClickListener {
		DateOnClick() {
		}

		@SuppressWarnings("deprecation")
		public void onClick(View paramView) {
			showDialog(1);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getRepeatCount() == 0) {

			Intent intent = new Intent();
			intent.setClass(AccountActivity.this, MainActivity.class);
			startActivity(intent);
			AccountActivity.this.finish();

			return false;

		}
		return false;
	}
	// =====================================总页面初始化
	// end==============================================
}
