package com.example.locationdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

/**
 * 此demo用来展示如何结合定位SDK实现定位，并使用MyLocationOverlay绘制定位位置 同时展示如何使用自定义图标绘制并点击时弹出泡泡
 */
public class LocationDemo extends Activity {

    // 定位相关
    LocationClient mLocClient;

    private TextView locationText;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        locationText = (TextView) findViewById(R.id.locationText);
        // 定位初始化
        mLocClient = new LocationClient(this);
		//定位的配置
		LocationClientOption option = new LocationClientOption();
		//定位模式选择，高精度、省电、仅设备
//			option.setLocationMode(mCurrentMode);
		option.setLocationMode(com.baidu.location.LocationClientOption.LocationMode.Hight_Accuracy);
		//定位坐标系类型选取, gcj02、bd09ll、bd09
		option.setCoorType("gcj02"); 
		//定位时间间隔
		option.setScanSpan(1000);
		//选择定位到地址
		option.setIsNeedAddress(true);
		mLocClient.setLocOption(option);
		//注册定位的成功的回调
		mLocClient.registerLocationListener(mBdLocationListener);
        mLocClient.start();
    }
    
private BDLocationListener mBdLocationListener = new BDLocationListener() {
		
		@Override
		public void onReceiveLocation(BDLocation location) {
			//Receive Location 
			StringBuffer sb = new StringBuffer(256);
			sb.append("time : ");
			sb.append(location.getTime());
			sb.append("\nerror code : ");
			sb.append(location.getLocType());
			sb.append("\nlatitude : ");
			sb.append(location.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(location.getLongitude());
			sb.append("\nradius : ");
			sb.append(location.getRadius());
			if (location.getLocType() == BDLocation.TypeGpsLocation){
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
				sb.append("\ndirection : ");
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				sb.append(location.getDirection());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				sb.append("\noperationers : ");
				sb.append(location.getOperators());
			}
			locationText.setText(sb.toString());
		}
	};


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        super.onDestroy();
    }

}
