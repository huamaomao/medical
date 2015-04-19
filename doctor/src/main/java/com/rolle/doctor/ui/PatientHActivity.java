package com.rolle.doctor.ui;

import android.os.Bundle;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.android.common.adapter.QuickAdapter;
import com.android.common.util.DividerItemDecoration;
import com.android.common.util.ViewHolderHelp;
import com.android.common.util.ViewUtil;
import com.rolle.doctor.R;
import com.rolle.doctor.adapter.PatientHInfoListAdapater;
import com.rolle.doctor.adapter.PatientHListAdapater;
import com.rolle.doctor.adapter.ViewPagerAdapter;
import com.rolle.doctor.adapter.YearSpinnerAdpater;
import com.rolle.doctor.domain.ItemInfo;

import java.util.ArrayList;
import java.util.List;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Hua_ on 2015/3/27.
 */
public class PatientHActivity extends BaseActivity{

    @InjectView(R.id.viewpage) ViewPager viewPager;
    private List<View> viewpages;
    @InjectView(R.id.rg_group)RadioGroup radioGroup;

    private List<ItemInfo> lsData;
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewInfo;

    private PatientHListAdapater adapater;
    private PatientHInfoListAdapater adapater1;

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

        recyclerView=(RecyclerView)viewpages.get(0).findViewById(R.id.rv_view);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        List ls=new ArrayList();
        ls.add(1);
        ls.add(1);
        ls.add(1);
        adapater=new PatientHListAdapater(this,ls);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapater);

        recyclerViewInfo=(RecyclerView)viewpages.get(1).findViewById(R.id.rv_view);
        LinearLayoutManager manage1=new LinearLayoutManager(this);
        recyclerViewInfo.setLayoutManager(manage1);
        lsData=new ArrayList<ItemInfo>();
        lsData.add(null);
        lsData.add(new ItemInfo("性别","男"));
        lsData.add(new ItemInfo("生日","1987年9月9日"));
        lsData.add(new ItemInfo("体重","120kg"));
        lsData.add(new ItemInfo("所在地","安亭"));
        lsData.add(null);
        lsData.add(new ItemInfo("健康","有轻微糖尿病"));
        lsData.add(new ItemInfo("过敏药物","无"));
        adapater1=new PatientHInfoListAdapater(this,lsData);
        recyclerViewInfo.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        recyclerViewInfo.setAdapter(adapater1);

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

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_more,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.toolbar_set:
                ViewUtil.openActivity(NoteActivity.class,this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.iv_send)
    void toMessageActivity(){
        ViewUtil.openActivity(MessageActivity.class, this);
    }
}
