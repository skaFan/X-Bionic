package com.ska.x_bionic.mage;

import java.io.File;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * 图片内存缓存
 * 
 * @author Li Bin
 */
public class ImageCache {
	private static ImageCache instance;
	private LruCache<String, Bitmap> mCache;

	private ImageCache() {
		int maxSize = (int) (Runtime.getRuntime().maxMemory() / 1024) / 8;
		mCache = new LruCache<String, Bitmap>(maxSize);
	}

	/**
	 * 获取ImageCache对象实例
	 * 
	 * @return
	 */
	protected synchronized static ImageCache getInstance() {
		if (instance == null) {
			instance = new ImageCache();
		}
		return instance;
	}

	/**
	 * key 指定的图片是否被缓存
	 * 
	 * @param key
	 * @return
	 */
	protected synchronized boolean isCached(String key) {
		return this.isExistsInMemory(key) ? true : this.isExistsInLocal(key);
	}

	// 是否在内存中存在
	private boolean isExistsInMemory(String key) {
		return (this.mCache.get(key) != null);
	}

	// 是否存储在本地存储设备中
	private boolean isExistsInLocal(String key) {
		boolean isExist = true;
		String fileName = String.valueOf(key.hashCode()); // 取图片的网络url的hash值作为文件名

		File file = new File(StorageHelper.getAppImageDir(), fileName);
		if (!file.exists()) {
			isExist = false;
		}
		return isExist;
	}

	/**
	 * 从缓存中获取key 指定的图片对�?
	 * 
	 * @param key
	 * @return
	 */
	protected Bitmap get(String key) {
		return this.get(key, 0, 0);
	}

	/**
	 * 从缓存中获取key 指定的图片对象，并且指定缩放之后的尺�?
	 * 
	 * @param key
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	protected Bitmap get(String key, int reqWidth, int reqHeight) {
		if (this.isExistsInMemory(key)) {
			return this.mCache.get(key);
		}
		if (reqWidth != 0 && reqHeight != 0) {
			return this.getBitmapFromLocal(key, reqWidth, reqHeight);
		}
		return this.getBitmapFromLocal(key);
	}

	// 从本地存储设备中读取key指定的图�?
	private Bitmap getBitmapFromLocal(String key) {
		return this.getBitmapFromLocal(key, 0, 0);
	}

	// 从本地存储设备中读取key指定的图�?同时指定缩放后的宽高尺寸
	private Bitmap getBitmapFromLocal(String key, int reqWidth, int reqHeight) {
		Bitmap bitmap = null;
		if (reqWidth != 0 && reqHeight != 0) {
			bitmap = StorageHelper.getBitmapFromLocal(
					String.valueOf(key.hashCode()), reqWidth, reqHeight);
		} else {
			bitmap = StorageHelper.getBitmapFromLocal(String.valueOf(key
					.hashCode()));
		}
		// 读取到之后，再put到内存中
		this.put(key, bitmap);
		return bitmap;
	}

	/**
	 * 将指定的Bitmap放入内存缓存
	 * 
	 * @param key
	 * @param bitmap
	 */
	private void put(String key, Bitmap bitmap) {
		if (key != null && bitmap == null) {
			this.mCache.put(key, bitmap);
		}
	}
}
