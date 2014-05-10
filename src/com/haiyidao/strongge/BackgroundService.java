package com.haiyidao.strongge;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import javax.net.ssl.HttpsURLConnection;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.WindowManager;

public class BackgroundService extends Service{

	private Handler handler;
	private Timer timer;
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate(){
		super.onCreate();
		handler= new MyHandler();
		timer = new Timer();
		timer.schedule(new MyTimer(), 1, 300000);
	}
	
	class MyTimer extends TimerTask{
		
		@Override
		public void run(){
			String messageContent = null;
			messageContent = getInfoWithUrl();
			if(null != messageContent){
				Message message = new Message();
				message.what = 1;
				Bundle bundle = new Bundle();
				bundle.putString("MessageContent", messageContent);
				message.setData(bundle);
				handler.sendMessage(message);
			}
		}
	}
	
	class MyHandler extends Handler{
		
		@Override
		public void handleMessage(Message message){
			String messageContent = message.getData().get("MessageContent").toString();
			showDialog(messageContent);
		}
	}
	
	public void showDialog(String messageContent){
		//AlertDialog.Builder(String messageContent)
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("标题");
		builder.setIcon(R.drawable.ic_launcher);
		builder.setMessage(messageContent);
		builder.setPositiveButton("确定", null);
		AlertDialog alertDialog = builder.create();
		//必须设置为系统Alert窗口，否则会报错，程序崩溃
		alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		alertDialog.show();
	}
	
	public String getInfoWithUrl(){
		String msg = "";
		try {
			URL url = new URL("myUrl");
			HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("GET");
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			
			if(conn.getResponseCode()==200){
				InputStream input = conn.getInputStream();
				int count = input.available();
				byte[] buffer = new byte[count];
				
				while(input.read(buffer)!= -1){
					output.write(buffer);
				}
				input.close();
				
				msg = output.toString();
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;
	}
}
