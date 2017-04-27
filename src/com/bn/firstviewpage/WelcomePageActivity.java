package com.bn.firstviewpage;


import com.bn.financial_assistant.MainActivity;
import com.bn.financial_assistant.R;
import com.bn.util.FontManager;
import static com.bn.util.Constant.*;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

public class WelcomePageActivity extends Activity {

	private static final int LOWBRIGHTNESS = -230;
	private static final String SHAREDPREFERENCES_NAME = "first_pref";
	private static final double everycut = 5;
	private boolean isFirstIn = false;
	private Bitmap bmp;
	private ImageView iv;
	private int brightness = 70;
	private Handler mHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.welcome_page);

		bmp = BitmapFactory.decodeResource(this.getBaseContext().getResources(), R.drawable.welcome_android);

		iv = (ImageView) this.findViewById(R.id.iv_welcome);

		mHandler = new Handler()// Handler构造器
		{
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {

				case DECREASE:
					brightness -= everycut * 1.05;
					brightChanged(brightness, bmp, iv);
					break;
				case GO_INDEX:
					goIndex();
					break;
				case GO_GUIDE:
					goGuide();
					break;
				}
				super.handleMessage(msg);
			}
		};
		new Thread() {
			@Override
			public void run() {
				while (true) {
					mHandler.sendEmptyMessage(DECREASE);// Handler发出请求
					if (brightness < LOWBRIGHTNESS) {
						init();
						break;
					}
					try {
						Thread.sleep(50);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}.start();

		FontManager.initTypeFace(this);
		ViewGroup vg = FontManager.getContentView(this);
		FontManager.changeFonts(vg, this);
	}

	private void init() {
		// 读取SharedPreferences中需要的数据
		// 使用SharedPreferences来记录程序的使用次数
		SharedPreferences preferences = getSharedPreferences(
				SHAREDPREFERENCES_NAME, MODE_PRIVATE);
		// 取得相应的值，如果没有该值，说明还未写入，用true作为默认值
		isFirstIn = preferences.getBoolean("isFirstIn", true);
		// 判断程序与第几次运行，如果是第一次运行则跳转到引导界面，否则跳转到主界面
		if (!isFirstIn) {
			// 使用Handler的postDelayed方法，3秒后执行跳转到MainActivity
			mHandler.sendEmptyMessageDelayed(GO_INDEX, KEEP_INDEX_TIME);
		} else {
			mHandler.sendEmptyMessageDelayed(GO_GUIDE, KEEP_INDEX_TIME);
		}

	}

	private void goIndex() {
		Intent intent = new Intent(WelcomePageActivity.this, MainActivity.class);
		WelcomePageActivity.this.startActivity(intent);
		WelcomePageActivity.this.finish();
	}

	private void goGuide() {
		Intent intent = new Intent(WelcomePageActivity.this, GuideActivity.class);
		WelcomePageActivity.this.startActivity(intent);
		WelcomePageActivity.this.finish();
	}

	public void brightChanged(int brightness, Bitmap bitmap,
			ImageView iv) {
		int imgHeight = bitmap.getHeight();
		int imgWidth = bitmap.getWidth();
		Bitmap bmp = Bitmap.createBitmap(imgWidth, imgHeight, Config.ARGB_8888);
		ColorMatrix cMatrix = new ColorMatrix();
		cMatrix.set(
			new float[] { 
				1, 0, 0, 0, brightness, // 改变亮度
				0, 1, 0, 0, brightness,
				0, 0, 1, 0, brightness, 
				0, 0, 0, 1, 0 
			});
		
		Paint paint = new Paint();
		paint.setColorFilter(new ColorMatrixColorFilter(cMatrix));

		Canvas canvas = new Canvas(bmp);
		// 在Canvas上绘制一个已经存在的Bitmap。这样，dstBitmap就和srcBitmap一摸一样了
		canvas.drawBitmap(bitmap, 0, 0, paint);
		iv.setImageBitmap(bmp);
	}
}