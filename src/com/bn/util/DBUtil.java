package com.bn.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

@SuppressWarnings("unused")
public class DBUtil {
	// ����
	public static void createTable() {
		SQLiteDatabase financialData = null;
		financialData = SQLiteDatabase.openDatabase(
				"/data/data/com.bn.financial_assistant/financialdata", null,
				SQLiteDatabase.OPEN_READWRITE
						| SQLiteDatabase.CREATE_IF_NECESSARY);
		// ����֧����
		String sqlpay = "create table if not exists accountpay(";
		sqlpay += "id integer PRIMARY KEY,moneypay varchar(10),categorypay varchar(20),zhuanghupay varchar(20),";
		sqlpay += "timepay varchar(50),projectpay varchar(20),memberpay varchar(20),pspay varchar(200));";
		financialData.execSQL(sqlpay);
		// ���������
		String sqlincome = "create table if not exists accountincome(";
		sqlincome += "id integer PRIMARY KEY," +
				"moneyincome varchar(10)," +
				"categoryincome varchar(20)," +
				"zhuanghuincome varchar(20),"+
				"timeincome varchar(50)," +
				"projectincome varchar(20)," +
				"memberincome varchar(20)," +
				"psincome varchar(200));";
		financialData.execSQL(sqlincome);
		// ��������¼��
		String sqlnote = "create table if not exists notepad(" +
				"id integer PRIMARY KEY," +
				"timenote varchar(20)," +
				"contentnote varchar(300));";
		financialData.execSQL(sqlnote);
		// ������Ʊ��
		String sqlstock = "create table if not exists stockinfo("
				+ "ID integer primary key autoincrement," + // 0.ID
				"CODE varchar(15)," + // 1.��Ʊ����
				"NAME varchar(20)," + // 2.��Ʊ����
				"PRICE_TODAY varchar(15)," + // 3.���տ��̼�
				"PRICE_YESTADAY varchar(15)," + // 4.�������̼�
				"PRICE_NOW varchar(15)," + // 5.��ǰ��
				"TODAY_HIGHEST varchar(15)," + // 6.������߼�
				"TODAY_LOWEST varchar(15)," + // 7.������ͼ�
				"TRADING_VOLUME varchar(20)," + // 8.������
				"CHANGING_OVER varchar(20)," + // 9.���׶�
				"DATE varchar(20)," + // 10.����
				"TIME varchar(20)," + // 11.ʱ��
				"EXCHANGEHALL varchar(20))"; // 12.������
		financialData.execSQL(sqlstock);

	}

	// -------------------------------֧��
	// begin----------------------------------------------
	// ���֧��
	public static boolean addPay(String[] info) {
		SQLiteDatabase financialData = null;
		Cursor cur = null;
		financialData = SQLiteDatabase.openDatabase(
				"/data/data/com.bn.financial_assistant/financialdata", null,
				SQLiteDatabase.OPEN_READWRITE
						| SQLiteDatabase.CREATE_IF_NECESSARY);
		int payId = -1;
		try {
			String sql = "select max(id) from accountpay;";
			cur = financialData.rawQuery(sql, null);
			if (cur.moveToFirst()) {
				payId = cur.getInt(0) + 1;
			} else {
				payId = 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if (info[6].length() == 0) {
				String sql2 = "insert into accountpay values(";
				sql2 += payId + ",'"; // id
				sql2 += info[0] + "','"; // ���
				sql2 += info[1] + "','"; // ���
				sql2 += info[2] + "','"; // �˻�
				sql2 += info[3] + "','"; // ʱ��
				sql2 += info[4] + "','"; // ��Ŀ
				sql2 += info[5] + "',"; // ��Ա
				sql2 += null + ");"; // ��ע
				financialData.execSQL(sql2);
			} else {
				String sql2 = "insert into accountpay values(";
				sql2 += payId + ",'"; // id
				sql2 += info[0] + "','"; // ���
				sql2 += info[1] + "','"; // ���
				sql2 += info[2] + "','"; // �˻�
				sql2 += info[3] + "','"; // ʱ��
				sql2 += info[4] + "','"; // ��Ŀ
				sql2 += info[5] + "','"; // ��Ա
				sql2 += info[6] + "');"; // ��ע
				financialData.execSQL(sql2);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cur != null) {
				cur.close();
			}
			financialData.close();
		}
		return false;
	}

	// �������
	public static Boolean addIncome(String[] info) {
		SQLiteDatabase financialData = null;
		Cursor cur = null;
		financialData = SQLiteDatabase.openDatabase(
				"/data/data/com.bn.financial_assistant/financialdata", null,
				SQLiteDatabase.OPEN_READWRITE
						| SQLiteDatabase.CREATE_IF_NECESSARY);
		int incomeId = -1;
		try {
			String sql = "select max(id) from accountincome;";
			cur = financialData.rawQuery(sql, null);
			if (cur.moveToFirst()) {
				incomeId = cur.getInt(0) + 1;
			} else {
				incomeId = 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if (info[6].length() == 0) {
				String sql2 = "insert into accountincome values(";
				sql2 += incomeId + ",'"; // id
				sql2 += info[0] + "','"; // ���
				sql2 += info[1] + "','"; // ���
				sql2 += info[2] + "','"; // �˻�
				sql2 += info[3] + "','"; // ʱ��
				sql2 += info[4] + "','"; // ��Ŀ
				sql2 += info[5] + "',"; // ��Ա
				sql2 += null + ");"; // ��ע
				financialData.execSQL(sql2);
			} else {
				String sql2 = "insert into accountincome values(";
				sql2 += incomeId + ",'"; // id
				sql2 += info[0] + "','"; // ���
				sql2 += info[1] + "','"; // ���
				sql2 += info[2] + "','"; // �˻�
				sql2 += info[3] + "','"; // ʱ��
				sql2 += info[4] + "','"; // ��Ŀ
				sql2 += info[5] + "','"; // ��Ա
				sql2 += info[6] + "');"; // ��ע
				financialData.execSQL(sql2);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cur != null) {
				cur.close();
			}
			financialData.close();
		}
		return false;
	}

	// ���֧��ʱ��
	public static String[] getPayDateStr() {
		SQLiteDatabase financialData = null;
		Cursor cur = null;
		financialData = SQLiteDatabase.openDatabase(
				"/data/data/com.bn.financial_assistant/financialdata", null,
				SQLiteDatabase.OPEN_READONLY);
		int tNum = -1;
		try {
			String sql = "select count(timepay) from accountpay;";
			cur = financialData.rawQuery(sql, null);
			if (cur.moveToFirst()) {
				tNum = cur.getInt(0);
			} else {
				tNum = 0;
			}
			cur = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] payTime = new String[tNum];
		try {
			String sql2 = "select timepay from accountpay;";
			cur = financialData.rawQuery(sql2, null);

			int count = 0;
			while (cur.moveToNext()) {
				payTime[count++] = cur.getString(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cur != null) {
				cur.close();
			}
			financialData.close();
		}
		return payTime;
	}

	// ���֧�����
	public static String[] getPayCategoryStr() {
		SQLiteDatabase financialData = null;
		Cursor cur = null;
		financialData = SQLiteDatabase.openDatabase(
				"/data/data/com.bn.financial_assistant/financialdata", null,
				SQLiteDatabase.OPEN_READONLY);
		int cNum = -1;
		try {
			String sql = "select count(categorypay) from accountpay;";
			cur = financialData.rawQuery(sql, null);
			if (cur.moveToFirst()) {
				cNum = cur.getInt(0);
			} else {
				cNum = 0;
			}
			cur = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] payCategory = new String[cNum];
		try {
			String sql2 = "select categorypay from accountpay;";
			cur = financialData.rawQuery(sql2, null);
			int count = 0;
			while (cur.moveToNext()) {
				payCategory[count++] = cur.getString(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cur != null) {
				cur.close();
			}
			financialData.close();
		}
		return payCategory;
	}

	// ���֧�����
	public static String[] getPayMoneyStr() {
		SQLiteDatabase financialData = null;
		Cursor cur = null;
		financialData = SQLiteDatabase.openDatabase(
				"/data/data/com.bn.financial_assistant/financialdata", null,
				SQLiteDatabase.OPEN_READONLY);
		int mNum = -1;
		try {
			String sql = "select count(moneypay) from accountpay;";
			cur = financialData.rawQuery(sql, null);
			if (cur.moveToFirst()) {
				mNum = cur.getInt(0);
			} else {
				mNum = 0;
			}
			cur = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] payMoney = new String[mNum];
		try {
			String sql2 = "select moneypay from accountpay;";
			cur = financialData.rawQuery(sql2, null);
			int count = 0;
			while (cur.moveToNext()) {
				payMoney[count++] = cur.getString(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cur != null) {
				cur.close();
			}
			financialData.close();
		}
		return payMoney;
	}

	// ���֧��
	public static String[] getPayListStr(String tStr, String cStr, String mStr) {
		SQLiteDatabase financialData = null;
		Cursor cur = null;
		String payStr[] = null;
		financialData = SQLiteDatabase.openDatabase(
				"/data/data/com.bn.financial_assistant/financialdata", null,
				SQLiteDatabase.OPEN_READONLY);
		try {
			String sql = "select timepay,categorypay,moneypay,zhuanghupay,projectpay,memberpay,pspay from accountpay "
					+ "where timepay ='"
					+ tStr
					+ "' and categorypay ='"
					+ cStr
					+ "' and moneypay ='" + mStr + "';";
			cur = financialData.rawQuery(sql, null);
			if (cur.moveToFirst()) {
				payStr = new String[cur.getColumnCount()];
				for (int i = 0; i < cur.getColumnCount(); i++) {
					payStr[i] = cur.getString(i);
				}
				if (payStr[6] == null) {
					payStr[6] = "��";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cur != null) {
				cur.close();
			}
			financialData.close();
		}
		return payStr;
	}

	// ɾ��֧��
	public static boolean deletePayListStr(String tStr, String cStr, String mStr) {
		SQLiteDatabase financialData = null;
		financialData = SQLiteDatabase.openDatabase(
				"/data/data/com.bn.financial_assistant/financialdata", null,
				SQLiteDatabase.OPEN_READWRITE
						| SQLiteDatabase.CREATE_IF_NECESSARY);
		try {
			String sql = "delete from accountpay " + "where timepay ='" + tStr
					+ "' and categorypay ='" + cStr + "' and moneypay ='"
					+ mStr + "';";
			financialData.execSQL(sql);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			financialData.close();
		}
		return false;
	}

	// -------------------------------֧��
	// end----------------------------------------------
	// -------------------------------����
	// begin----------------------------------------------
	// �������ʱ��
	public static String[] getIncomeDateStr() {
		SQLiteDatabase financialData = null;
		Cursor cur = null;
		financialData = SQLiteDatabase.openDatabase(
				"/data/data/com.bn.financial_assistant/financialdata", null,
				SQLiteDatabase.OPEN_READONLY);
		int tNum = -1;
		try {
			String sql = "select count(timeincome) from accountincome;";
			cur = financialData.rawQuery(sql, null);
			if (cur.moveToFirst()) {
				tNum = cur.getInt(0);
			} else {
				tNum = 0;
			}
			cur = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] incomeTime = new String[tNum];
		try {
			String sql2 = "select timeincome from accountincome;";
			cur = financialData.rawQuery(sql2, null);

			int count = 0;
			while (cur.moveToNext()) {
				incomeTime[count++] = cur.getString(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cur != null) {
				cur.close();
			}
			financialData.close();
		}
		return incomeTime;
	}

	// ����������
	public static String[] getIncomeCategoryStr() {
		SQLiteDatabase financialData = null;
		Cursor cur = null;
		financialData = SQLiteDatabase.openDatabase(
				"/data/data/com.bn.financial_assistant/financialdata", null,
				SQLiteDatabase.OPEN_READONLY);
		int cNum = -1;
		try {
			String sql = "select count(categoryincome) from accountincome;";
			cur = financialData.rawQuery(sql, null);
			if (cur.moveToFirst()) {
				cNum = cur.getInt(0);
			} else {
				cNum = 0;
			}
			cur = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] incomeCategory = new String[cNum];
		try {
			String sql2 = "select categoryincome from accountincome;";
			cur = financialData.rawQuery(sql2, null);
			int count = 0;
			while (cur.moveToNext()) {
				incomeCategory[count++] = cur.getString(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cur != null) {
				cur.close();
			}
			financialData.close();
		}
		return incomeCategory;
	}

	// ���������
	public static String[] getIncomeMoneyStr() {
		SQLiteDatabase financialData = null;
		Cursor cur = null;
		financialData = SQLiteDatabase.openDatabase(
				"/data/data/com.bn.financial_assistant/financialdata", null,
				SQLiteDatabase.OPEN_READONLY);
		int mNum = -1;
		try {
			String sql = "select count(moneyincome) from accountincome;";
			cur = financialData.rawQuery(sql, null);
			if (cur.moveToFirst()) {
				mNum = cur.getInt(0);
			} else {
				mNum = 0;
			}
			cur = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] incomeMoney = new String[mNum];
		try {
			String sql2 = "select moneyincome from accountincome;";
			cur = financialData.rawQuery(sql2, null);
			int count = 0;
			while (cur.moveToNext()) {
				incomeMoney[count++] = cur.getString(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cur != null) {
				cur.close();
			}
			financialData.close();
		}
		return incomeMoney;
	}

	// �������
	public static String[] getIncomeListStr(String tStr, String cStr,
			String mStr) {
		SQLiteDatabase financialData = null;
		Cursor cur = null;
		String incomeStr[] = null;
		financialData = SQLiteDatabase.openDatabase(
				"/data/data/com.bn.financial_assistant/financialdata", null,
				SQLiteDatabase.OPEN_READONLY);
		try {
			String sql = "select timeincome,categoryincome,moneyincome,zhuanghuincome,projectincome,memberincome,psincome from accountincome "
					+ "where timeincome ='"
					+ tStr
					+ "' and categoryincome ='"
					+ cStr + "' and moneyincome ='" + mStr + "';";
			cur = financialData.rawQuery(sql, null);
			if (cur.moveToFirst()) {
				incomeStr = new String[cur.getColumnCount()];
				for (int i = 0; i < cur.getColumnCount(); i++) {
					incomeStr[i] = cur.getString(i);
				}
				if (incomeStr[6] == null) {
					incomeStr[6] = "��";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cur != null) {
				cur.close();
			}
			financialData.close();
		}
		return incomeStr;
	}

	// ɾ������
	public static boolean deleteIncomeListStr(String tStr, String cStr,
			String mStr) {
		SQLiteDatabase financialData = null;
		financialData = SQLiteDatabase.openDatabase(
				"/data/data/com.bn.financial_assistant/financialdata", null,
				SQLiteDatabase.OPEN_READWRITE
						| SQLiteDatabase.CREATE_IF_NECESSARY);
		try {
			String sql = "delete from accountincome " + "where timeincome ='"
					+ tStr + "' and categoryincome ='" + cStr
					+ "' and moneyincome ='" + mStr + "';";
			financialData.execSQL(sql);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			financialData.close();
		}
		return false;
	}

	// ===========================================����
	// end======================================================================

	// ============================================����¼
	// begin===========================================================================
	// ��Ӽ��¼�¼
	public static boolean addNotepad(String[] info) {
		SQLiteDatabase financialData = null;
		Cursor cur = null;
		financialData = SQLiteDatabase.openDatabase(
				"/data/data/com.bn.financial_assistant/financialdata", null,
				SQLiteDatabase.OPEN_READWRITE
						| SQLiteDatabase.CREATE_IF_NECESSARY);
		int noteId = -1;
		try {
			String sql = "select max(id) from notepad;";
			cur = financialData.rawQuery(sql, null);
			if (cur.moveToFirst()) {
				noteId = cur.getInt(0) + 1;
			} else {
				noteId = 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			String sql2 = "insert into notepad values(";
			sql2 += noteId + ",'"; // id
			sql2 += info[0] + "','"; // ʱ��
			sql2 += info[1] + "');"; // ����
			financialData.execSQL(sql2);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cur != null) {
				cur.close();
			}
			financialData.close();
		}
		return false;
	}

	// ��ü���ʱ��
	public static String[] getNoteDateStr() {
		SQLiteDatabase financialData = null;
		Cursor cur = null;
		financialData = SQLiteDatabase.openDatabase(
				"/data/data/com.bn.financial_assistant/financialdata", null,
				SQLiteDatabase.OPEN_READWRITE
						| SQLiteDatabase.CREATE_IF_NECESSARY);
		int tNum = -1;
		try {
			String sql = "select count(timenote) from notepad;";
			cur = financialData.rawQuery(sql, null);
			if (cur.moveToFirst()) {
				tNum = cur.getInt(0);
			} else {
				tNum = 0;
			}
			cur = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] noteTime = new String[tNum];
		try {
			String sql2 = "select timenote from notepad;";
			cur = financialData.rawQuery(sql2, null);

			int count = 0;
			while (cur.moveToNext()) {
				noteTime[count++] = cur.getString(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cur != null) {
				cur.close();
			}
			financialData.close();
		}
		return noteTime;
	}

	// ��ü�������
	public static String[] getNoteContentStr() {
		SQLiteDatabase financialData = null;
		Cursor cur = null;
		financialData = SQLiteDatabase.openDatabase(
				"/data/data/com.bn.financial_assistant/financialdata", null,
				SQLiteDatabase.OPEN_READWRITE
						| SQLiteDatabase.CREATE_IF_NECESSARY);
		int tNum = -1;

		try {
			String sql = "select count(contentnote) from notepad;";
			cur = financialData.rawQuery(sql, null);
			if (cur.moveToFirst()) {
				tNum = cur.getInt(0);
			} else {
				tNum = 0;
			}
			cur = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] noteContent = new String[tNum];
		try {
			String sql2 = "select substr(contentnote,1,20) from notepad;";
			cur = financialData.rawQuery(sql2, null);

			int count = 0;
			while (cur.moveToNext()) {
				noteContent[count++] = cur.getString(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cur != null) {
				cur.close();
			}
			financialData.close();
		}
		return noteContent;
	}

	// ����ض��ļ�¼
	public static String[] getNotepadListStr(String tStr, String cStr) {
		SQLiteDatabase financialData = null;
		Cursor cur = null;
		financialData = SQLiteDatabase.openDatabase(
				"/data/data/com.bn.financial_assistant/financialdata", null,
				SQLiteDatabase.OPEN_READWRITE
						| SQLiteDatabase.CREATE_IF_NECESSARY);
		String[] noteStr = null;
		try {
			String sql = "select timenote,contentnote from notepad "
					+ "where timenote ='" + tStr + "' and contentnote like '"
					+ cStr + "%';";
			cur = financialData.rawQuery(sql, null);
			if (cur.moveToFirst()) {
				noteStr = new String[cur.getColumnCount()];
				for (int i = 0; i < cur.getColumnCount(); i++) {
					noteStr[i] = cur.getString(i);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cur != null) {
				cur.close();
			}
			financialData.close();
		}
		return noteStr;
	}

	// ɾ������¼
	public static boolean deleteNotepadListStr(String tStr, String cStr) {
		SQLiteDatabase financialData = null;
		financialData = SQLiteDatabase.openDatabase(
				"/data/data/com.bn.financial_assistant/financialdata", null,
				SQLiteDatabase.OPEN_READWRITE
						| SQLiteDatabase.CREATE_IF_NECESSARY);
		try {
			String sql = "delete from notepad " + "where timenote ='" + tStr
					+ "' and contentnote like '" + cStr + "%';";
			financialData.execSQL(sql);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			financialData.close();
		}
		return false;
	}

	// -------------------------------����¼
	// end------------------------------------------------

	// -------------------------------��Ʊ
	// begin------------------------------------------------

	// ��ӹ�Ʊ��Ϣ
	public static boolean addInfo(Object[] objects) {
		// TODO Auto-generated method stub
		boolean flag = false;
		SQLiteDatabase financialData = null;
		financialData = SQLiteDatabase.openDatabase(
				"/data/data/com.bn.financial_assistant/financialdata", null,
				SQLiteDatabase.OPEN_READWRITE
						| SQLiteDatabase.CREATE_IF_NECESSARY);
		try {
			String sql = "insert into stockinfo(code,name,price_today,price_yestaday,price_now,today_highest,today_lowest,TRADING_VOLUME,CHANGING_OVER,date,time,EXCHANGEHALL) values(?,?,?,?,?,?,?,?,?,?,?,?)";
			financialData.execSQL(sql, objects);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (financialData != null) {
				financialData.close();
			}
		}
		return flag;
	}

	// ɾ����Ʊ��Ϣ
	public static boolean deleteInfo(Object[] objects) {
		// TODO Auto-generated method stub
		boolean flag = false;
		SQLiteDatabase financialData = null;
		financialData = SQLiteDatabase.openDatabase(
				"/data/data/com.bn.financial_assistant/financialdata", null,
				SQLiteDatabase.OPEN_READWRITE
						| SQLiteDatabase.CREATE_IF_NECESSARY);
		try {
			String sql = "delete from stockinfo where CODE=?";
			financialData.execSQL(sql, objects);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (financialData != null) {
				financialData.close();
			}
		}
		return flag;
	}

	// ɾ����
	public static boolean deleteAll() {
		// TODO Auto-generated method stub
		boolean flag = false;
		SQLiteDatabase financialData = null;
		financialData = SQLiteDatabase.openDatabase(
				"/data/data/com.bn.financial_assistant/financialdata", null,
				SQLiteDatabase.OPEN_READWRITE
						| SQLiteDatabase.CREATE_IF_NECESSARY);
		try {
			String sql = "drop from stockinfo";
			financialData.execSQL(sql);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (financialData != null) {
				financialData.close();
			}
		}
		return flag;
	}

	// ���¹�Ʊ��Ϣ
	public static boolean updateInfo(Object[] objects) {
		// TODO Auto-generated method stub
		boolean flag = false;
		SQLiteDatabase financialData = null;
		financialData = SQLiteDatabase.openDatabase(
				"/data/data/com.bn.financial_assistant/financialdata", null,
				SQLiteDatabase.OPEN_READWRITE
						| SQLiteDatabase.CREATE_IF_NECESSARY);
		try {
			String sql = "update stockinfo set " + "CODE =?," + "NAME =?,"
					+ "PRICE_TODAY =?," + "PRICE_YESTADAY =?,"
					+ "PRICE_NOW =?," + "TODAY_HIGHEST =?,"
					+ "TODAY_LOWEST =?," + "TRADING_VOLUME =?,"
					+ "CHANGING_OVER =?," + "DATE =?," + "TIME =?,"
					+ "EXCHANGEHALL =?" + "where CODE=?";

			financialData.execSQL(sql, objects);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (financialData != null) {
				financialData.close();
			}
		}
		return flag;
	}

	// ���ݹ�Ʊ�����ȡ������Ϣ
	public static Map<String, String> viewInfo(String[] strings) {
		// TODO Auto-generated method stub
		Map<String, String> map = new LinkedHashMap<String, String>();
		SQLiteDatabase financialData = null;
		financialData = SQLiteDatabase.openDatabase(
				"/data/data/com.bn.financial_assistant/financialdata", null,
				SQLiteDatabase.OPEN_READONLY);
		try {
			String sql = "select * from stockinfo where code =? ";

			Cursor cursor = financialData.rawQuery(sql, strings);
			int colums = cursor.getColumnCount();

			while (cursor.moveToNext()) {
				for (int i = 0; i < colums; i++) {
					String cols_name = cursor.getColumnName(i);
					String cols_values = cursor.getString(cursor
							.getColumnIndex(cols_name));
					if (cols_values == null) {
						cols_values = "";
					}
					map.put(cols_name, cols_values);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (financialData != null) {
				financialData.close();
			}
		}
		return map;
	}

	// ��ȡ���й�Ʊ��Ϣ
	public static List<Map<String, String>> ListviewInfo(String[] strings) {
		// TODO Auto-generated method stub
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		SQLiteDatabase financialData = null;
		financialData = SQLiteDatabase.openDatabase(
				"/data/data/com.bn.financial_assistant/financialdata", null,
				SQLiteDatabase.OPEN_READONLY);
		try {
			String sql = "select * from stockinfo";
			Cursor cursor = financialData.rawQuery(sql, strings);
			int colums = cursor.getColumnCount();

			while (cursor.moveToNext()) {
				Map<String, String> map = new LinkedHashMap<String, String>();
				for (int i = 0; i < colums; i++) {
					String cols_name = cursor.getColumnName(i);
					String cols_values = cursor.getString(cursor
							.getColumnIndex(cols_name));
					if (cols_values == null) {
						cols_values = "";
					}
					map.put(cols_name, cols_values);
				}
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (financialData != null) {
				financialData.close();
			}
		}
		return list;
	}
	// -------------------------------��Ʊ
	// end------------------------------------------------
}
