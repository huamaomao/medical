package com.rolle.doctor.ui;

import android.os.Bundle;
import android.widget.ListView;

import com.android.common.adapter.QuickAdapter;
import com.android.common.util.ViewHolderHelp;
import com.rolle.doctor.R;
import com.rolle.doctor.domain.ItemInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by Hua_ on 2015/3/27.
 */
public class UserInfoActivity extends BaseActivity{

    @InjectView(R.id.lv_list)
    ListView lsList;
    private List<ItemInfo> lsData;
    protected QuickAdapter quickAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("资料");
        lsData=new ArrayList<ItemInfo>();
        lsData.add(new ItemInfo("工作地址","上海市杨浦区人民广场"));
        lsData.add(new ItemInfo("所在医院","上海市人民医院"));
        lsData.add(new ItemInfo("医生职称","副主任医师"));
        lsData.add(new ItemInfo("所在科室","慢性病科"));
        lsData.add(new ItemInfo("专长","糖尿病"));

        quickAdapter=new QuickAdapter<ItemInfo>(this,R.layout.list_item_h,lsData) {
            @Override
            protected void convert(ViewHolderHelp helper, ItemInfo item) {
                helper.setText(R.id.tv_item_0, item.title)
                        .setText(R.id.tv_item_1,item.desc);
            }
        };
        lsList.setAdapter(quickAdapter);
    }
}
