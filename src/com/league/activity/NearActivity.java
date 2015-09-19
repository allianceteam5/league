package com.league.activity;

import com.mine.league.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.BDNotifyListener;//假如用到位置提醒功能，需要import该类
import com.baidu.location.Poi;
import com.baidu.location.LocationClientOption.LocationMode;
/**  
 *   
 * @author liugang  
 * @date 2015年9月16日   
 */
public class NearActivity extends Activity implements OnClickListener{

	private ImageButton imgBack;
	private TextView nearTitle;
	private TextView nearLocation;
	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = new MyLocationListener();
	LocationClientOption option = new LocationClientOption();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_near);
		init();
	}
	public void init(){
		imgBack=(ImageButton) findViewById(R.id.imgbtn_back);
		nearTitle=(TextView) findViewById(R.id.oh_title);
		nearLocation=(TextView) findViewById(R.id.location);
		nearTitle.setText("附近");
		imgBack.setVisibility(View.VISIBLE);
		imgBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
				mLocationClient.stop();
			}
		});
		mLocationClient = new LocationClient(this);//声明LocationClient类
	    mLocationClient.registerLocationListener(myListener);//注册监听函数
	    option.setLocationMode(LocationMode.Device_Sensors);//设置定位模式
	    option.setCoorType("bd09ll");//返回的定位结果是百度经纬度,默认值gcj02
	    option.setIsNeedAddress(true);//返回的定位结果包含地址信息
	    mLocationClient.setLocOption(option);
	    mLocationClient.start();
	    mLocationClient.requestLocation();
	}
	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation arg0) {
			// TODO Auto-generated method stub
			nearLocation.setText(//arg0.getCountry()+" "
//			+arg0.getProvince().substring(0, arg0.getProvince().length()-1)+" "+
					arg0.getCity().substring(0, arg0.getCity().length()-1));
		}
		
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		
		this.finish();
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.near_searchinfo:
			Toast.makeText(this, "near_searchinfo", 1).show();
			break;
		case R.id.near_featurecom:
			Toast.makeText(this, "near_featurecom", 1).show();
			break;
		case R.id.near_likefriend:
			Toast.makeText(this, "near_likefriend", 1).show();
			break;
		case R.id.near_sfcar:
			Toast.makeText(this, "near_sfcar", 1).show();
			break;
		case R.id.near_others:
			Toast.makeText(this, "near_others", 1).show();
			break;
		default:
			break;
		}
	}

}
