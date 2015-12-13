package com.league.activity.personinfoactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.league.adapter.AddressAdapter;
import com.league.bean.ShippingAddressBean;
import com.league.utils.Constants;
import com.league.utils.PersonInfoBaseActivity;
import com.league.utils.api.ApiUtil;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.mine.league.R;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

public class ShippingAddress extends PersonInfoBaseActivity implements View.OnClickListener {

    private ListView listView;
    private List<ShippingAddressBean> mAddressData=new ArrayList<>();
    private AddressAdapter addressAdapter;
    private View empty;
    private Button newAdd1,newAdd2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("收货地址");

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shippingaddress;
    }

    @Override
    protected void initView() {
        listView= (ListView) findViewById(R.id.addresslistview);
        empty=findViewById(R.id.list_emptyview);
        empty.setVisibility(View.GONE);
        newAdd1= (Button) findViewById(R.id.newadd);
        newAdd1.setOnClickListener(this);
        newAdd2= (Button) findViewById(R.id.addshipaddress);
        newAdd2.setOnClickListener(this);
        addressAdapter=new AddressAdapter(mAddressData,getApplicationContext());
        listView.setAdapter(addressAdapter);
        newAdd1.setVisibility(View.GONE);
        showProgressDialog();
    }

    @Override
    protected void initData() {
        ApiUtil.getShipAddress(getApplicationContext(), Constants.PHONENUM, new BaseJsonHttpResponseHandler<ArrayList<ShippingAddressBean>>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, ArrayList<ShippingAddressBean> response) {
                mAddressData.clear();
                mAddressData.addAll(response);
                addressAdapter.notifyDataSetChanged();
                if(response.size()==0){
                    listView.setVisibility(View.GONE);
                    empty.setVisibility(View.VISIBLE);
                    newAdd1.setVisibility(View.GONE);
                }else{
                    listView.setVisibility(View.VISIBLE);
                    empty.setVisibility(View.GONE);
                    newAdd1.setVisibility(View.VISIBLE);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent add=new Intent(ShippingAddress.this,AddShippingAdr.class);
                            add.putExtra("flag",1);//1表示修改地址
                            add.putExtra("name",mAddressData.get(position).getName());
                            add.putExtra("aphone",mAddressData.get(position).getAphone());
                            add.putExtra("postcode",mAddressData.get(position).getPostcode());
                            add.putExtra("address",mAddressData.get(position).getAddress());
                            add.putExtra("addressID",mAddressData.get(position).getId());
                            startActivity(add);
                        }
                    });
                }
                closeProgressDialog();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, ArrayList<ShippingAddressBean> errorResponse) {
                closeProgressDialog();
            }

            @Override
            protected ArrayList<ShippingAddressBean> parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return new ObjectMapper().readValue(rawJsonData, new TypeReference<ArrayList<ShippingAddressBean>>() {});
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.newadd:
            case R.id.addshipaddress:
                Intent add=new Intent(ShippingAddress.this,AddShippingAdr.class);
                add.putExtra("flag",0);//0表示新添加地址
                startActivity(add);
                break;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }
}
