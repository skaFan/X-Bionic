package com.ska.x_bionic.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;


import com.ska.x_bionic.R;
import com.ska.x_bionic.application.MyApplication;
import com.ska.x_bionic.http.HttpHelper;
import com.ska.x_bionic.http.HttpMethod;
import com.ska.x_bionic.http.RequestEntity;
import com.ska.x_bionic.http.ResponseJsonEntity;
import com.ska.x_bionic.model.ShoppingCarProduct;
import com.ska.x_bionic.model.Shoppingcar;
import com.ska.x_bionic.model.ShoppingcarSysColor;
import com.ska.x_bionic.model.ShoppingcarSysSize;
import com.ska.x_bionic.model.User;
import com.ska.x_bionic.util.ConnectivityUtil;
import com.ska.x_bionic.util.JsonUtil;
import com.ska.x_bionic.util.TextUtil;
import com.ska.x_bionic.util.ToastUtil;

public class BlLoginActivity extends Activity implements OnClickListener{
	private ImageButton ibBack; 
	private EditText blUser,blPsw;
	private Button btFrogetPsw,blEnter;
	private ProgressDialog pd;
	private List<Shoppingcar> carList;

	
	
	
	/**
	 * 记住密码
	 */
	@Override
	protected void onResume() {
		SharedPreferences sp = getSharedPreferences("user",0);
		String userId = sp.getString("userName", "");
		String passoword=sp.getString("passWord", "");
		if(!TextUtil.isEmptyString(userId)&&!TextUtil.isEmptyString(passoword)){
			blUser.setText(userId);
			blPsw.setText(passoword);
		}
		super.onResume();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bl_login);
		ibBack=(ImageButton)findViewById(R.id.ib_bl_back);
		btFrogetPsw=(Button)findViewById(R.id.bl_forgetpsw);
		blEnter=(Button)findViewById(R.id.bl_enter);
		blUser=(EditText)findViewById(R.id.bl_user);
		blPsw = (EditText)findViewById(R.id.bl_password);
		btFrogetPsw.setOnClickListener(this);
		blEnter.setOnClickListener(this);
		ibBack.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.bl_login, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
		case R.id.ib_bl_back:
			this.finish();
			break;
		case R.id.bl_forgetpsw:
			Intent intent = new Intent(this,FindPswActivity.class);
			startActivity(intent);
			break;
		case R.id.bl_enter:
			doLogin();
			break;
		}
		
	}
	private void doLogin(){
		String userName = blUser.getText().toString();
		String passWord = blPsw.getText().toString();
		if(!ConnectivityUtil.isOnline(this)){
			ToastUtil.showToast(this, "没有网络连接");
		}
		else if(TextUtil.isNumber(userName)){
			if(TextUtil.isPhoneNumber(userName)){
			new Task(userName, passWord).execute();
				
			}
		}
		
		
	}
	 class Task extends AsyncTask<Void, Void, Integer>{
		 private String mUsername;
		 private String mPassword;
		 
		 Task(String userName,String passWord){
			 mUsername= userName;
			 mPassword= passWord;
			 
		 }
		 
		 @Override
		protected void onPreExecute() {
			pd= ProgressDialog.show(BlLoginActivity.this, "正在登录", "请稍后");
			super.onPreExecute();
		}

		@Override
		protected Integer doInBackground(Void... params) {
			ResponseJsonEntity responseEntity = null;
			String url = "passport/login.do";
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("phoneNumber", blUser.getText().toString());
			map.put("password", blPsw.getText().toString());
			map.put("client", "android");
			RequestEntity entity = new RequestEntity(url, map);
			String jason="";
			   
			try {
				jason = HttpHelper.execute(entity);
				 responseEntity=ResponseJsonEntity.fromJSON(jason);
				if(responseEntity.getStatus()==200){
					String data = responseEntity.getData();
					User user = JsonUtil.toObject(data, User.class);
					MyApplication.addUser(user);
					MyApplication.token=user.token;
					MyApplication.userId=user.id;
					Log.i("userid", MyApplication.userId+"");
					Log.i("token", MyApplication.token);

					SharedPreferences preferences = getSharedPreferences(
							"User", 0);
					SharedPreferences.Editor editor = preferences.edit();
					editor.putString("phoneNumber", user.displayName);
					editor.putString("token", user.token);
					editor.commit();
					
				}
				else {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							BlLoginActivity.this);
					builder.setTitle("X-bionic").setMessage("网络解析错误...")
							.setPositiveButton("确定", null).create().show();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Log.i("jason", jason);
			return responseEntity.getStatus();
		}
		
		@Override
		protected void onPostExecute(Integer result) {
			pd.dismiss();
//			if(TextUtil.isEmptyString(result)){
//				ToastUtil.showToast(BlLoginActivity.this, "输入有误");
//			}
			
			if(result==200){
				SharedPreferences sp = getSharedPreferences("user", 0);
				if(!sp.getString("userName", "").equals(mUsername)){
					SharedPreferences.Editor editor = sp.edit();
					editor.putString("userName", mUsername);
					editor.putString("passWord", mPassword);
					editor.commit();
				}
				new GetProductCount().execute();
			
				
			}
			else{
				ToastUtil.showToast(BlLoginActivity.this, "请求未成功");
				blPsw.setText("");
				blPsw.setFocusable(true);
				blPsw.requestFocus();
			}
			
			super.onPostExecute(result);
		}
		 
	 }
	 
	 class GetProductCount extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... params) {
			String url = "shoppingcart/list.do";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("token", MyApplication.token);
			map.put("userId", MyApplication.userId);
			RequestEntity entity = new RequestEntity(url, HttpMethod.POST, map);
			String json = null;
			try {
				json = HttpHelper.execute(entity);
				ResponseJsonEntity jsonEntity = ResponseJsonEntity
						.fromJSON(json);
				if (jsonEntity.getStatus() == 200) {
					String data = jsonEntity.getData();
					carList = JsonUtil.toObjectList(data, Shoppingcar.class);
					

				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			MyApplication.shopingCarProNum=carList.size();
			Intent intent = new Intent(BlLoginActivity.this, MainActivity.class);
		     startActivity(intent);
			finish();
			super.onPostExecute(result);
		}
		 
	 }

}
