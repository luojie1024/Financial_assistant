package com.bn.calculator;

import com.bn.financial_assistant.MainActivity;
import com.bn.util.FontManager;
import com.bn.financial_assistant.R;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class CalculatorActivity extends Activity implements OnClickListener {
	/** Called when the activity is first created. */
	private String string = "0";
	private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0;
	private Button btnadd, btnsub, btnmul, btndiv, btneq, btnpoint, btndel,
			btnback, btnc;
	private TextView btnt0, btnt1;
	private GetValue getValue = new GetValue();
	private Judge judge = new Judge();
	private boolean flag = false;

	private void init() {
		btn1 = (Button) this.findViewById(R.id.one);
		btn2 = (Button) this.findViewById(R.id.two);
		btn3 = (Button) this.findViewById(R.id.three);
		btn4 = (Button) this.findViewById(R.id.four);
		btn5 = (Button) this.findViewById(R.id.five);
		btn6 = (Button) this.findViewById(R.id.six);
		btn7 = (Button) this.findViewById(R.id.seven);
		btn8 = (Button) this.findViewById(R.id.eight);
		btn9 = (Button) this.findViewById(R.id.nine);
		btn0 = (Button) this.findViewById(R.id.zero);
		btnc = (Button) this.findViewById(R.id.c);
		btnadd = (Button) this.findViewById(R.id.add);
		btnsub = (Button) this.findViewById(R.id.subtract);
		btnmul = (Button) this.findViewById(R.id.multiple);
		btndiv = (Button) this.findViewById(R.id.division);
		btneq = (Button) this.findViewById(R.id.eq);
		btnpoint = (Button) this.findViewById(R.id.point);
		btndel = (Button) this.findViewById(R.id.del);
		btnt0 = (TextView) this.findViewById(R.id.text0);
		btnt1 = (TextView) this.findViewById(R.id.text1);
		btnback = (Button) this.findViewById(R.id.calculator_bnback);

		this.btn0.setOnClickListener(this);
		this.btn1.setOnClickListener(this);
		this.btn2.setOnClickListener(this);
		this.btn3.setOnClickListener(this);
		this.btn4.setOnClickListener(this);
		this.btn5.setOnClickListener(this);
		this.btn6.setOnClickListener(this);
		this.btn7.setOnClickListener(this);
		this.btn8.setOnClickListener(this);
		this.btn9.setOnClickListener(this);
		this.btnc.setOnClickListener(this);
		this.btnadd.setOnClickListener(this);
		this.btndel.setOnClickListener(this);
		this.btndiv.setOnClickListener(this);
		this.btneq.setOnClickListener(this);
		this.btnmul.setOnClickListener(this);
		this.btnpoint.setOnClickListener(this);
		this.btnsub.setOnClickListener(this);
		this.btnback.setOnClickListener(this);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_calculator_main);
		this.init();
		FontManager.initTypeFace(this);
		ViewGroup vg = FontManager.getContentView(this);
		FontManager.changeFonts(vg, this);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItem exit = menu.add("EXIT");
		exit.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				System.exit(0);
				return true;
			}
		});
		return true;
	}

	@Override
	public void onClick(View v) {
		if ("error".equals(btnt1.getText().toString())
				|| "¡Þ".equals(btnt1.getText().toString())) {
			string = "0";
		}

		if (v == this.btn0) {
			string = judge.digit_judge(string, "0", flag);
			flag = false;
			btnt1.setText(string);
		} else if (v == this.btn1) {
			string = judge.digit_judge(string, "1", flag);
			flag = false;
			btnt1.setText(string);
		} else if (v == this.btn2) {
			string = judge.digit_judge(string, "2", flag);
			flag = false;
			btnt1.setText(string);
		} else if (v == this.btn3) {
			string = judge.digit_judge(string, "3", flag);
			flag = false;
			btnt1.setText(string);
		} else if (v == this.btn4) {
			string = judge.digit_judge(string, "4", flag);
			flag = false;
			btnt1.setText(string);
		} else if (v == this.btn5) {
			string = judge.digit_judge(string, "5", flag);
			flag = false;
			btnt1.setText(string);
		} else if (v == this.btn6) {
			string = judge.digit_judge(string, "6", flag);
			flag = false;
			btnt1.setText(string);
		} else if (v == this.btn7) {
			string = judge.digit_judge(string, "7", flag);
			flag = false;
			btnt1.setText(string);
		} else if (v == this.btn8) {
			string = judge.digit_judge(string, "8", flag);
			flag = false;
			btnt1.setText(string);
		} else if (v == this.btn9) {
			string = judge.digit_judge(string, "9", flag);
			flag = false;
			btnt1.setText(string);
		} else if (v == this.btneq) {
			btnt0.setText(string + "=");
			string = getValue.alg_dispose(string);
			string = judge.digit_dispose(string);
			flag = true;
			btnt1.setText(string);
		} else if (v == this.btnc) {
			string = "";
			btnt0.setText(string);
			string = "0";
			btnt1.setText(string);
			flag = false;
		} else if (v == this.btnpoint) {
			string = judge.judge1(string);
			flag = false;
			btnt1.setText(string);
		} else if (v == this.btndel) {
			if (!"0".equals(string)) {
				string = string.substring(0, string.length() - 1);
				if (0 == string.length())
					string = "0";
			}
			flag = false;
			btnt1.setText(string);
		} else if (v == this.btnadd) {
			string = judge.judge(string, "+");
			flag = false;
			btnt1.setText(string);
		} else if (v == this.btnsub) {
			string = judge.judge(string, "-");
			flag = false;
			btnt1.setText(string);
		} else if (v == this.btnmul) {
			string = judge.judge(string, "¡Á");
			flag = false;
			btnt1.setText(string);
		} else if (v == this.btndiv) {
			string = judge.judge(string, "¡Â");
			flag = false;
			btnt1.setText(string);
		} else if (v == this.btnback) {
			Intent intent = new Intent();
			intent.setClass(CalculatorActivity.this, MainActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.main_back, R.anim.add_back);
			CalculatorActivity.this.finish();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK
				&& keyCode == KeyEvent.KEYCODE_HOME
				|| event.getRepeatCount() == 0) {

			Intent intent = new Intent();
			intent.setClass(CalculatorActivity.this, MainActivity.class);
			startActivity(intent);
			CalculatorActivity.this.finish();

			return false;

		}
		return false;
	}
}