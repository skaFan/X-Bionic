package com.ska.x_bionic.image;

import java.io.File;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * 图片内存缓存
 * 
 * @author Li Bin
 */
public class ImageCache2 {
	private static ImageCache2 instance;
	private LruCache<String, Bitmap> mCache;
	
	private  ImageCache2(){
		int maxSize=(int) ((Runtime.getRuntime().maxMemory()/1024)/8);
		mCache=new LruCache<String, Bitmap>(maxSize);
	}
	/**
	 * 获取ImageCache对象实例
	 * 
	 * @return
	 */
	private synchronized static ImageCache2 getInstance(){
		if(instance==null){
			instance=new ImageCache2();
		}
		return instance;
	}
	/**
	 * key 指定的图片是否被缓存
	 * 
	 * @param key
	 * @return
	 */
	protected synchronized boolean isCached(String key){
		
		return this.isExistsInLocal(key)?true:this.isExistsInMemory(key);
		
	}
	
	private boolean isExistsInMemory(String key){
		return (this.mCache.get(key)!=null);
		
	}
	
	private boolean isExistsInLocal(String key){
		boolean isExist = true;
		String fileName = String.valueOf(key.hashCode());
		File file = new File(StorageHelper.getAppImageDir(),fileName);
		if(!file.exists()){
			isExist=false;
		}
		return isExist;
		
	}
	
	protected Bitmap get(String key){
		return this.get(key, 0, 0);
		
	}
	
	protected Bitmap get(String key,int reqWidth, int reqHeight){
		if(isExistsInMemory(key)){
			return this.mCache.get(key);
		}
		if(reqWidth!=0&&reqHeight!=0){
			return this.getBitmapFromLocal(key, reqWidth, reqHeight);
		}
		
		
		return this.getBitmapFromLocal(key);
		
	}
	private Bitmap getBitmapFromLocal(String key){
		return this.getBitmapFromLocal(key, 0, 0);
	}
	
	private Bitmap getBitmapFromLocal(String key,int reqWidth, int reqHeight){
		Bitmap bitmap = null;
		if(reqWidth != 0 && reqHeight != 0){
			bitmap = StorageHelper.getBitmapFromLocal(String.valueOf(key.hashCode()), reqWidth, reqHeight);
		}else{
			bitmap=StorageHelper.getBitmapFromLocal(String.valueOf(key.hashCode()));
		}
			put(key,bitmap);
		return bitmap;
		
	}
	private void put(String key,Bitmap bitmap){
		if (key != null && bitmap == null) {
			this.mCache.put(key, bitmap);
			}
	}
	
	
	

}
