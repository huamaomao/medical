package com.rolle.doctor.ui;

import android.os.Bundle;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioGroup;

import com.android.common.adapter.QuickAdapter;
import com.android.common.util.ViewHolderHelp;
import com.rolle.doctor.R;
import com.rolle.doctor.adapter.ViewPagerAdapter;
import com.rolle.doctor.domain.ItemInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

/**
 * Created by Hua_ on 2015/3/27.
 */
public class PatientHActivity extends BaseActivity{

    @InjectView(R.id.viewpage) ViewPager viewPager;
    private List<View> viewpages;

    @InjectView(R.id.rg_group)RadioGroup radioGroup;


    ListView lsList;
    private List<ItemInfo> lsData;
    private QuickAdapter quickAdapter;


    ListView lsView1;
    private List<ItemInfo> lsData1;
    private QuickAdapter quickAdapter1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_h);
    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("小叶病史");
        viewpages=new ArrayList<View>();
        viewpages.add(getLayoutInflater().inflate(R.layout.viewpage_patient_1,null));
        viewpages.add(getLayoutInflater().inflate(R.layout.viewpage_patient_2,null));
       lsView1=(ListView)viewpages.get(1).findViewById(R.id.lv_data);
       lsList=(ListView)viewpages.get(1).findViewById(R.id.lv_list);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_tab_1:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.rb_tab_2:
                        viewPager.setCurrentItem(1);
                        break;
                }
            }
        });

        ViewPagerAdapter adapter=new ViewPagerAdapter(viewpages);

        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        radioGroup.check(R.id.rb_tab_1);
                        break;
                    case 1:
                        radioGroup.check(R.id.rb_tab_2);
                        break;
                }
            }
        });
        viewPager.setCurrentItem(0);
        radioGroup.check(R.id.rb_tab_1);



        lsData=new ArrayList<ItemInfo>();
        lsData.add(new ItemInfo("性别","男"));
        lsData.add(new ItemInfo("生日","1987年9月9日"));
        lsData.add(new ItemInfo("体重","120kg"));
        lsData.add(new ItemInfo("所在地","安亭"));

        quickAdapter=new QuickAdapter<ItemInfo>(this,R.layout.list_item_h,lsData) {
            @Override
            protected void convert(ViewHolderHelp helper, ItemInfo item) {
                helper.setText(R.id.tv_item_0, item.title)
                        .setText(R.id.tv_item_1,item.desc);
            }
        };
        lsList.setAdapter(quickAdapter);

        lsData1=new ArrayList<ItemInfo>();
        lsData1.add(new ItemInfo("健康","有轻微糖尿病"));
        lsData1.add(new ItemInfo("过敏药物","无"));

        quickAdapter1=new QuickAdapter<ItemInfo>(this,R.layout.list_item_h,lsData1) {
            @Override
            protected void convert(ViewHolderHelp helper, ItemInfo item) {
                helper.setText(R.id.tv_item_0, item.title)
                        .setText(R.id.tv_item_1,item.desc);
            }
        };
        lsView1.setAdapter(quickAdapter1);
    }

}
