package com.bn.stock;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import static com.bn.util.Constant.*;
import com.bn.util.*;

import android.content.Context;
import android.util.Log;

public class ToolClass {

	public static String getJSonStr(String exchangeHall, // ��Ʊ����������
			String code // ��Ʊ����
	) {
		String jSonStr = "�����쳣";
		@SuppressWarnings("unused")
		String stocks[];

		try {
			StringBuilder sb = new StringBuilder("http://hq.sinajs.cn/list=");
			sb.append(exchangeHall);
			sb.append(code);
			String urlStr = null;

			urlStr = new String(sb.toString().getBytes(), "gbk");
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

			jSonStr = new String(bb, "gbk");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return jSonStr;
	}

	public static String[] CutStrArray(String exchangeHall, // ��Ʊ����������
			String code, // ��Ʊ����
			String jSonStr // ��Ʊ��Ϣ�ṩ�̷��ص��ַ���
	) {

		String[] ListArray = new String[12];
		String stocks[];

		stocks = jSonStr.split("\\,");
		String[] stock_name1;
		stock_name1 = stocks[0].split("\"");
		stocks[0] = stock_name1[1];

		int changeNUM = (int) (Float.parseFloat(stocks[8])) / 100;
		String StrchangeNUM = Float.toString(changeNUM);

		int changeVAL = (int) (Float.parseFloat(stocks[9])) / 10000;
		String StrchangeVAL = Float.toString(changeVAL);

		ListArray[CODE] = code;//��Ʊ����
		ListArray[NAME] = stocks[0];//��Ʊ����
		ListArray[PRICE_TODAY] = stocks[1];//���տ��̼�
		ListArray[PRICE_YESTADAY] = stocks[2];//�������̼�
		ListArray[PRICE_NOW] = stocks[3];//��ǰ��
		ListArray[TODAY_HIGHEST] = stocks[4];//������߼�
		ListArray[TODAY_LOWEST] = stocks[5];//������ͼ�
		ListArray[TRADING_VOLUME] = StrchangeNUM;//������
		ListArray[CHANGING_OVER] = StrchangeVAL;//���׶�
		ListArray[DATE] = stocks[30];//����
		ListArray[TIME] = stocks[31];//ʱ��
		ListArray[EXCHANGEHALL] = exchangeHall;//������ 
		
		return ListArray;
	}

	public static String[] CutMsgs(String msgs) {

		String[] ListArray = new String[12];
		String stocks[];

		stocks = msgs.split("\\,");
		String[] stock_name1;
		stock_name1 = stocks[0].split("\"");
		stocks[0] = stock_name1[1];

		int changeNUM = (int) (Float.parseFloat(stocks[8])) / 100;
		String StrchangeNUM = Float.toString(changeNUM);

		int changeVAL = (int) (Float.parseFloat(stocks[9])) / 10000;
		String StrchangeVAL = Float.toString(changeVAL);

		ListArray[CODE] = stocks[34];
		ListArray[NAME] = stocks[0];//��Ʊ����
		ListArray[PRICE_TODAY] = stocks[1];//���տ��̼�
		ListArray[PRICE_YESTADAY] = stocks[2];//�������̼�
		ListArray[PRICE_NOW] = stocks[3];//��ǰ��
		ListArray[TODAY_HIGHEST] = stocks[4];//������߼�
		ListArray[TODAY_LOWEST] = stocks[5];//������ͼ�
		ListArray[TRADING_VOLUME] = StrchangeNUM;
		ListArray[CHANGING_OVER] = StrchangeVAL;
		ListArray[DATE] = stocks[30];
		ListArray[TIME] = stocks[31];
		ListArray[EXCHANGEHALL] = stocks[33];

		for (int i = 0; i < ListArray.length; i++) {
			System.out.println("-" + i + "-" + ListArray[i]);
		}

		return ListArray;
	}

	public static String[][] selectAll(Context context) {

		ArrayList<String> all = new ArrayList<String>();
		List<Map<String, String>> list = DBUtil.ListviewInfo(null);

		for (int l = 0; l < list.size(); l++) {

			Map<String, String> map = list.get(l);
			Set<String> set = map.keySet();
			Iterator<String> it = set.iterator();
			String[] ss = new String[map.size()];

			for (int i = 0; i < map.size(); i++) {
				String item = it.next();
				ss[i] = map.get(item);
			}

			for (int i = 1; i < ss.length; i++) {
				all.add(ss[i]);
			}

		}

		String allInfo[][] = new String[all.size() / 12][12];
		for (int i = 0; i < all.size() / 12; i++) {
			for (int j = 0; j < 12; j++) {
				int n = i * 12 + j;
				allInfo[i][j] = all.get(n);
			}

		}

		return allInfo;
	}

	public static String[] selectOne(String code, Context context) {
		String[] codes = { code };

		Map<String, String> map = DBUtil.viewInfo(codes);
		Log.i("----->MAP��", map.toString());

		Set<String> set = map.keySet();
		Iterator<String> it = set.iterator();

		String[] ss = new String[map.size()];
		for (int i = 0; i < map.size(); i++) {
			String item = it.next();
			ss[i] = map.get(item);
		}

		String[] DetailInfo = new String[12];
		for (int i = 0; i < ss.length - 1; i++) {
			DetailInfo[i] = ss[i + 1];
		}

		for (int i = 0; i < DetailInfo.length; i++) {
			System.out.println("DetailInfo:" + DetailInfo[i]);
		}

		return DetailInfo;

	}

	public static boolean isExist(String code, Context context) {

		boolean flag = false;
		String[] info = selectOne(code, context);
		if (info[0] != null) {
			flag = true;
		}
		return flag;
	}

	public static float round(float value, // ��Ҫȷ�����ȵ���
			int scale, // ����
			RoundingMode roundingMode // ģʽ
	) {

		MathContext mc = new MathContext(scale, roundingMode);
		BigDecimal fvalue = new BigDecimal(value);
		BigDecimal divisor = new BigDecimal(1.0);
		float result = fvalue.divide(divisor, mc).floatValue();
		return result;
	}

}
