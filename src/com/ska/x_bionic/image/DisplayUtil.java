package com.ska.x_bionic.image;

import android.content.Context;
import android.util.DisplayMetrics;

public class DisplayUtil {
	/**
	 * ��ȡ��Ļ��ȵ������?
	 * 
	 * @param context
	 * @return
	 */
	public static int getScreenWidth(Context context) {
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		return dm.widthPixels;
	}

	/**
	 * ��ȡ��Ļ�߶�����ֵ
	 * 
	 * @param context
	 * @return
	 */
	public static int getScreenHeight(Context context) {
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		return dm.heightPixels;
	}

	/**
	 * ��dp��λ�ĳߴ�ֵת��Ϊpx��λ
	 * 
	 * @param context
	 * @param dpValue
	 * @return
	 */
	public static int dip2Px(Context context, float dpValue) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * ��px��λ�ĳߴ�ֵת��Ϊdp��λ
	 * 
	 * @param context
	 * @param pxValue
	 * @return
	 */
	public static int px2Dip(Context context, float pxValue) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
}
