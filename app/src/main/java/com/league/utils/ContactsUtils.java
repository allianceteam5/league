package com.league.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.league.bean.ContactBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author liugang
 * @date 2015年8月29日
 */
public class ContactsUtils {
	
	// ----------------得到本地联系人信息-------------------------------------
	public static List<ContactBean> getLocalContacts(Context context) {
		List<ContactBean> localList = new ArrayList<>(); 
		ContentResolver cr = context.getContentResolver();
		String str[] = { Phone.CONTACT_ID, Phone.DISPLAY_NAME, Phone.NUMBER,
				Phone.PHOTO_ID };
		Cursor cur = cr.query(
				Phone.CONTENT_URI, str, null,
				null, null);

		if (cur != null) {
			while (cur.moveToNext()) {
				ContactBean contactBean = new ContactBean();
				contactBean.setPhone(cur.getString(cur
						.getColumnIndex(Phone.NUMBER)));// 得到手机号码

				contactBean.setNickname(cur.getString(cur
						.getColumnIndex(Phone.DISPLAY_NAME)));
				Log.i("phone", contactBean.getPhone() + contactBean.getNickname());
				localList.add(contactBean);
			}
		}
		cur.close();
		return localList;

	}

	public List<ContactBean> getcontactBeans(Context context) {
		List<ContactBean> SIMList = new ArrayList<>();
		TelephonyManager mTelephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);

		ContentResolver cr = context.getContentResolver();
		final String SIM_URI_ADN = "content://icc/adn";// SIM卡

		Uri uri = Uri.parse(SIM_URI_ADN);
		Cursor cursor = cr.query(uri, null, null, null, null);
		while (cursor.moveToFirst()) {
			ContactBean contactBean = new ContactBean();
			contactBean.setNickname(cursor.getString(cursor
					.getColumnIndex("name")));
			contactBean.setPhone(cursor.getString(cursor
					.getColumnIndex("number")));
			SIMList.add(contactBean);
		}
		cursor.close();
		return SIMList;
	}

}
