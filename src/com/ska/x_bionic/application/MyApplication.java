package com.ska.x_bionic.application;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;

import com.ska.x_bionic.model.User;

public class MyApplication extends Application{
	public static List<String> pictureUrl =new ArrayList<String>();
	
	public static List<String> colorList = new ArrayList<String>();
	
	public static List<String> sizeList = new ArrayList<String>();
	public static List<User> userList = new ArrayList<User>();
	public static int shopingCarProNum;
	public static int userId;
	public static String token;

	public static void addUser(User user) {
		userList.add(user);
	}

	
	
	
	public static void addSizeinfo(String SizeInfo){
		sizeList.add(SizeInfo);
	}
	 
	public static String getSizeinfo(int index){
		return sizeList.get(index);
	}
	
	public static void clearSizeList(){
		sizeList.clear();
	}
	
	public static int getSizeListSize(){
		return sizeList.size();
	}
	
	
	
	
	
	
	public static void addColorUrl(String colorImage){
		colorList.add(colorImage);
	}
	 
	public static String getColorUrl(int index){
		return colorList.get(index);
	}
	
	public static void clearColorList(){
		colorList.clear();
	}
	
	public static int getCorlorListSize(){
		return colorList.size();
	}
	
	
	
	public static void addPictureUrl(String images) {
		pictureUrl.add(images);
	}
	
	public static String getPictureUrl(int index) {
		return pictureUrl.get(index);
	}
	
	public static void clearPictureUrlList() {
		pictureUrl.clear();
	}
	
	public static int getPictureUrlListSize () {
		return pictureUrl.size();
	}

}
