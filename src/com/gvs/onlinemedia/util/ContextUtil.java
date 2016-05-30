package com.gvs.onlinemedia.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

public class ContextUtil {
	public static boolean isNumeric(String str) {
		for (int i = str.length(); --i >= 0;) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 拍照（从视屏流中截取)
	 * **/
	public static int getPhotoFormImg(Context ctx, Bitmap bitmap,
			int screenWidth, int screenHeight, View cv, String name, int type) {
		boolean flag = false;
		int result;
		if (bitmap == null) {
			return -1;
		}

		String savePath = initSDCardDir(ctx, type);
		String fileurl = savePath + name;

		try {
			createFile(fileurl, true);
			File file = new File(fileurl);
			FileOutputStream out = new FileOutputStream(file);
			if (bitmap.compress(Bitmap.CompressFormat.PNG, 50, out)) {
				out.flush();
				out.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result = -1;
			e.printStackTrace();
		}
		result = 1;
		return result;
	}

	/**
	 * 创建文件或覆盖文件 【不管文件目录是否存在】
	 *
	 * @param filePath
	 *            文件路径
	 * @param bool
	 *            是否覆盖 true 覆盖 false 不覆盖
	 * @return File 文件对象
	 */
	public static File createFile(String filePath, boolean bool) {
		try {
			File file = new File(filePath);

			if (!file.exists()) {// 不存在
				File parent = file.getAbsoluteFile().getParentFile();// 获得父级目录
				if (parent.exists() || parent.mkdirs())// 目录不存在则创建目录
					if (file.createNewFile())// 创建文件
						return file;

				return null;
			} else if (bool) {// 存在 是否覆盖
				file.delete();
				file.createNewFile();
			}

			return file;
		} catch (IOException e) {
			return null;
		}
	}

	public static boolean isExist(String filePath) {
		if (!TextUtils.isEmpty(filePath)) {
			File file = new File(filePath);
			if (file.exists()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/*
	 * dip转换成px
	 */
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	// sdcard是否可读写
	public static boolean IsCanUseSdCard() {
		try {
			return android.os.Environment.getExternalStorageState().equals(
					android.os.Environment.MEDIA_MOUNTED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 获取程序外部的缓存目录
	 *
	 * @param context
	 * @return
	 */
	public static File getExternalCacheDir(Context context) {
		final String cacheDir = "/Android/data/" + context.getPackageName()
				+ "/cache/";
		return new File(Environment.getExternalStorageDirectory().getPath()
				+ cacheDir);
	}

	// 初始化SD卡文件目录
	public static String initSDCardDir(Context ctx, int type) {
		// String savePath;

		// String sdPath = android.os.Environment.getExternalStorageDirectory()
		// .toString();// 获取跟目录
		// LogMgr.showLog("sdPath=>" + sdPath);
		// String account = "test";
		// if (!sdPath.endsWith("/")) {
		// savePath = sdPath + "/" + ctx.getString(R.string.app_name) +
		// File.separator
		// + account ;
		// } else {
		// savePath = sdPath + ctx.getString(R.string.app_name) + File.separator
		// + account ;
		// }
		final String cachePath = Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState()) ? getExternalCacheDir(ctx)
				.getPath() : ctx.getCacheDir().getPath();
		File PHOTO_DIR = new File(cachePath + File.separator + "DCIM");
		PHOTO_DIR.mkdirs();
		String savePath = PHOTO_DIR.getAbsolutePath();
		// 创建一级目录
		// File file = new File(savePath);
		// if (!file.exists()) {
		// boolean ok=file.mkdir();
		// LogMgr.showLog("ok==>"+ok);
		// }
		// LogMgr.showLog("savePath=>" + savePath);
		// 创建二级目录
		if (type == 1) {
			savePath += "/video/";
		} else if (type == 2) {
			savePath += "/videoPhoto/";
		} else if (type == 3) {
			savePath += "/monitorPhoto/";
		} else if (type == 4) {
			savePath += "/alarmPhoto/";
		} else {
			savePath += "/photo/";
		}
		File file = new File(savePath);
		if (!file.exists()) {
			file.mkdir();
		}
		return savePath;
	}

	public static String[] getAppVersionName(Context context) {
		String[] codename = null;
		String versionCode = ""; //
		String versionName = ""; //
		try {
			// ---get the package info---
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			versionCode = pi.versionCode + "";
			versionName = pi.versionName;

			codename = new String[] { versionName,versionCode  };
			// codename=new String[]{versionCode,versionName};
		} catch (Exception e) {
			codename = new String[] { "0", "" };
			Log.d("getAppVersionName", "getAppVersionName->" + e.toString());
		}
		return codename;
	}

	public static int getWidth(Context cx) {
		int width = 0;
		DisplayMetrics dm = null;
		dm = new DisplayMetrics();
		dm = cx.getApplicationContext().getResources().getDisplayMetrics();
		width = dm.widthPixels;
		return width;
	}

	public static int getHeith(Context cx) {
		int height = 0;
		DisplayMetrics dm = null;
		dm = new DisplayMetrics();
		dm = cx.getApplicationContext().getResources().getDisplayMetrics();
		height = dm.heightPixels;
		return height;
	}

	public static String getImsiImei(Context act, String type) {
		String IMSI = "";
		String IMEI = "";
		// TelephonyManager tm =
		// (TelephonyManager)getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
		TelephonyManager tm = (TelephonyManager) act
				.getSystemService(Context.TELEPHONY_SERVICE);
		IMSI = tm.getSubscriberId() == null ? "" : tm.getSubscriberId(); // IMSI
		IMEI = tm.getDeviceId(); // IMEI
		boolean right = "imsi".equalsIgnoreCase(type);
		if (right) {
			return IMSI;
		} else if ("imei".equalsIgnoreCase(type)) {
			return IMEI;
		} else {
			return IMSI;
		}
	}

	public static long getDiffTime(String time1, String time2) {
		long minute = 0;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date d1 = df.parse(time1);

			Date d2 = df.parse(time2);

			long diff = d1.getTime() - d2.getTime();

			minute = diff / (1000 * 60);
		} catch (Exception e) {
		}
		return minute;
	}

	public static String getDealTime(String text) {
		if (!TextUtils.isEmpty(text)) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			try {
				Date date = simpleDateFormat.parse(text);
				Calendar dealcal = Calendar.getInstance();
				dealcal.setTime(date);
				Calendar syscal = Calendar.getInstance();
				int year = syscal.get(Calendar.YEAR);
				int month = syscal.get(Calendar.MONTH);
				int day = syscal.get(Calendar.DAY_OF_MONTH);
				if (dealcal.get(Calendar.YEAR) == year) {
					if (dealcal.get(Calendar.MONTH) == month) {
						if (dealcal.get(Calendar.DAY_OF_MONTH) == day) {
							return (dealcal.get(Calendar.HOUR_OF_DAY) > 9 ? dealcal
									.get(Calendar.HOUR_OF_DAY) : "0"
									+ dealcal.get(Calendar.HOUR_OF_DAY))
									+ ":"
									+ (dealcal.get(Calendar.MINUTE) > 9 ? dealcal
											.get(Calendar.MINUTE)
											: ("0" + dealcal
													.get(Calendar.MINUTE)));
						} else {
							return (dealcal.get(Calendar.MONTH) + 1)
									+ "-"
									+ dealcal.get(Calendar.DAY_OF_MONTH)
									+ " "
									+ (dealcal.get(Calendar.HOUR_OF_DAY) > 9 ? dealcal
											.get(Calendar.HOUR_OF_DAY) : "0"
											+ dealcal.get(Calendar.HOUR_OF_DAY))
									+ ":"
									+ (dealcal.get(Calendar.MINUTE) > 9 ? dealcal
											.get(Calendar.MINUTE)
											: ("0" + dealcal
													.get(Calendar.MINUTE)));
						}
					} else {
						return (dealcal.get(Calendar.MONTH) + 1)
								+ "-"
								+ dealcal.get(Calendar.DAY_OF_MONTH)
								+ " "
								+ (dealcal.get(Calendar.HOUR_OF_DAY) > 9 ? dealcal
										.get(Calendar.HOUR_OF_DAY) : "0"
										+ dealcal.get(Calendar.HOUR_OF_DAY))
								+ ":"
								+ (dealcal.get(Calendar.MINUTE) > 9 ? dealcal
										.get(Calendar.MINUTE) : ("0" + dealcal
										.get(Calendar.MINUTE)));
					}
				} else {
					return dealcal.get(Calendar.YEAR)
							+ "-"
							+ (dealcal.get(Calendar.MONTH) + 1)
							+ "-"
							+ dealcal.get(Calendar.DAY_OF_MONTH)
							+ " "
							+ (dealcal.get(Calendar.HOUR_OF_DAY) > 9 ? dealcal
									.get(Calendar.HOUR_OF_DAY) : "0"
									+ dealcal.get(Calendar.HOUR_OF_DAY))
							+ ":"
							+ (dealcal.get(Calendar.MINUTE) > 9 ? dealcal
									.get(Calendar.MINUTE) : ("0" + dealcal
									.get(Calendar.MINUTE)));
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return text;
	}

	public static String getSubStringPrefix(String text, String mark) {
		if (!TextUtils.isEmpty(text)) {
			if (text.contains(mark)) {
				String str = text.substring(0, text.lastIndexOf(mark));
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				try {
					Date date = simpleDateFormat.parse(str);
					Calendar dealcal = Calendar.getInstance();
					dealcal.setTime(date);
					Calendar syscal = Calendar.getInstance();
					int year = syscal.get(Calendar.YEAR);
					int month = syscal.get(Calendar.MONTH);
					int day = syscal.get(Calendar.DAY_OF_MONTH);
					if (dealcal.get(Calendar.YEAR) == year) {
						if (dealcal.get(Calendar.DAY_OF_MONTH) == day) {
							return (dealcal.get(Calendar.HOUR_OF_DAY) > 9 ? dealcal
									.get(Calendar.HOUR_OF_DAY) : "0"
									+ dealcal.get(Calendar.HOUR_OF_DAY))
									+ ":"
									+ (dealcal.get(Calendar.MINUTE) > 9 ? dealcal
											.get(Calendar.MINUTE)
											: ("0" + dealcal
													.get(Calendar.MINUTE)));
						} else {
							return (dealcal.get(Calendar.MONTH) + 1)
									+ "-"
									+ dealcal.get(Calendar.DAY_OF_MONTH)
									+ " "
									+ (dealcal.get(Calendar.HOUR_OF_DAY) > 9 ? dealcal
											.get(Calendar.HOUR_OF_DAY) : "0"
											+ dealcal.get(Calendar.HOUR_OF_DAY))
									+ ":"
									+ (dealcal.get(Calendar.MINUTE) > 9 ? dealcal
											.get(Calendar.MINUTE)
											: ("0" + dealcal
													.get(Calendar.MINUTE)));
						}
					} else {
						return dealcal.get(Calendar.YEAR)
								+ "-"
								+ (dealcal.get(Calendar.MONTH) + 1)
								+ "-"
								+ dealcal.get(Calendar.DAY_OF_MONTH)
								+ " "
								+ (dealcal.get(Calendar.HOUR_OF_DAY) > 9 ? dealcal
										.get(Calendar.HOUR_OF_DAY) : "0"
										+ dealcal.get(Calendar.HOUR_OF_DAY))
								+ ":"
								+ (dealcal.get(Calendar.MINUTE) > 9 ? dealcal
										.get(Calendar.MINUTE) : ("0" + dealcal
										.get(Calendar.MINUTE)));
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return text;
	}

	public static String getSubStringByTag(String text, String mark) {
		if (!TextUtils.isEmpty(text)) {
			if (text.contains(mark)) {
				return text.substring(0, text.lastIndexOf(mark));
			}
		}
		return text;
	}

	public static String getWeekOfDate() {
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance();
		Date dt = new Date();
		cal.setTime(dt);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return weekDays[w];
	}

	public static String getWeekTranDate(String data) {
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = dateFormat.parse(data);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (date != null) {
			cal.setTime(date);
			int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
			if (w < 0)
				w = 0;
			return weekDays[w];
		} else {
			return weekDays[0];
		}
	}

	/**
	 * MD5加密
	 *
	 * @param str
	 * @return
	 */
	public static String encodeMD5(String str) {
		if (str == null)
			return str;
		return MD5.compile(str);
		// return encrypt(str.getBytes()).toString();
	}

//	/**
//	 * 判断白天和黑夜
//	 *
//	 * @return
//	 */
//	public static boolean isNight() {
//		Date date = new Date();
//		long hours = date.getHours();
//		if (hours < 19 && hours > 6) {
//			return false;
//		}
//		return true;
//	}

	/**
	 * 判断白天和黑夜
	 *
	 * @return
	 */
	public static boolean isNight() {
		Date date = new Date();
		int month = date.getMonth();
		SimpleDateFormat sdf =   new SimpleDateFormat("HH:mm");
		String str = sdf.format(new Date());
//		if(DateCompare(str,"7")){
//
//		}
		Log.e("sssssssss",month+"");
		switch (month) {
			case 0:
				if (DateCompare("18:00",str) && DateCompare(str,"7:00")) {
					return false;
				}
				break;

			case 1:
				if(DateCompare("18:30",str) && DateCompare(str,"7:00")){
					return false;
				}
				break;

			case 2:
				if (DateCompare("18:30",str) && DateCompare(str,"6:30")) {
					return false;
				}
				break;
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
				if (DateCompare("19:00",str) && DateCompare(str,"6:00")) {
					return false;
				}
				break;
			case 8:
				if (DateCompare("18:30",str) && DateCompare(str,"6:30")) {
					return false;
				}
				break;
			case 9:
				if (DateCompare("18:00",str) && DateCompare(str,"6:30")) {
					return false;
				}
				break;
			case 10:
				if (DateCompare("17:30",str) && DateCompare(str,"6:30")) {
					return false;
				}
				break;
			case 11:
				if (DateCompare("18:00",str) && DateCompare(str,"7:00")) {
					return false;
				}
				break;

			default:
				break;
		}
		return true;
	}

	public static boolean DateCompare(String s1,String s2)
	{
		//设定时间的模板
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		//得到指定模范的时间
		try {
			Date d1 = sdf.parse(s1);
			Date d2 = sdf.parse(s2);
			if(d1.getTime() >d2.getTime()){
				return true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}

}
