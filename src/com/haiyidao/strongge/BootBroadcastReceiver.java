package com.haiyidao.strongge;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootBroadcastReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
			
			/*Intent mainIntent = new Intent(context,MainActivity.class);
			mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(mainIntent);*/
			
			//开机启动个service，service主要做轮询
			Intent serviceIntent = new Intent(context,BackgroundService.class);
			context.startService(serviceIntent);
		}
	}

}
