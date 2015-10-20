package com.league.activity;

import com.mine.league.R;

import android.app.Activity;
import android.content.Intent;
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
			Intent intent=new Intent(this,SechJobInfo.class);
			intent.putExtra("mode", 1);
			startActivity(intent);
			break;
		case R.id.near_featurecom:
			Intent intent2=new Intent(this,SechJobInfo.class);
			intent2.putExtra("mode", 2);
			startActivity(intent2);
			break;
		case R.id.near_likefriend:
			Intent intent3=new Intent(this,SechJobInfo.class);
			intent3.putExtra("mode", 3);
			startActivity(intent3);
			break;
		case R.id.near_sfcar:
			Intent intent4=new Intent(this,SechJobInfo.class);
			intent4.putExtra("mode", 4);
			startActivity(intent4);
			break;
		case R.id.near_searchpeople:
			Intent intent5=new Intent(this,SechJobInfo.class);
			intent5.putExtra("mode", 5);
			startActivity(intent5);
			break;
		case R.id.near_others:
			Intent intent6=new Intent(this,SechJobInfo.class);
			intent6.putExtra("mode", 6);
			startActivity(intent6);
			break;
		default:
			break;
		}
	}

}
