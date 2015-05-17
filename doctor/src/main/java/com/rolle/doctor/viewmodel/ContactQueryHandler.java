package com.rolle.doctor.viewmodel;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import com.rolle.doctor.domain.ContactBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Hua_
 * @Description: 查看联系人
 * @date 2015/5/14 - 17:17
 */
public class ContactQueryHandler extends AsyncQueryHandler {

    private Context mContext;
    private HandleListener handleListener;
    public ContactQueryHandler(ContentResolver cr,HandleListener handleListener) {
        super(cr);
        this.handleListener=handleListener;

    }

    public void queryList(){
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        // 查询的字段
        String[] projection = { ContactsContract.CommonDataKinds.Phone._ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.DATA1, "sort_key",
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.PHOTO_ID,
                ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY };
        // 按照sort_key升序查詢
        startQuery(0, null, uri, projection, null, null,
                "sort_key COLLATE LOCALIZED asc");
    }

    @Override
    protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
        List<ContactBean> list=new ArrayList<>();
        Map<String,ContactBean> contactIdMap=new HashMap();
        if (cursor != null && cursor.getCount() > 0) {
            list = new ArrayList<ContactBean>();
            cursor.moveToFirst(); // 游标移动到第一项
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                String name = cursor.getString(1);
                String number = cursor.getString(2);
                if (!contactIdMap.containsKey(number)) {
                    ContactBean contact = new ContactBean();
                    contact.setDesplayName(name);
                    contact.setPhoneNum(number);
                    list.add(contact);
                    contactIdMap.put(number, contact);
                }
            }
            if (list.size() > 0) {
                handleListener.setAdapter(list);
            }
        }

        super.onQueryComplete(token, cookie, cursor);
    }
   public  interface HandleListener{
        public void setAdapter(List<ContactBean> list);
    }
}
