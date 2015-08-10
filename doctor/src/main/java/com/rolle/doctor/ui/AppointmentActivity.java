package com.rolle.doctor.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.common.adapter.MultiplicityRecylerAdapter;
import com.android.common.adapter.RecyclerAdapter;
import com.android.common.adapter.RecyclerOnScrollListener;
import com.android.common.domain.ResponseMessage;
import com.android.common.util.CommonUtil;
import com.android.common.viewmodel.SimpleResponseListener;
import com.android.common.widget.RefreshLayout;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.rolle.doctor.R;
import com.rolle.doctor.domain.Appointment;
import com.rolle.doctor.util.CircleTransform;
import com.rolle.doctor.viewmodel.UserModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

import static com.rolle.doctor.R.mipmap.icon_default;

/**
 * 预约
 */
public class AppointmentActivity extends BaseLoadingActivity {

    @InjectView(R.id.refresh)
    RefreshLayout refresh;
    @InjectView(R.id.rv_view)
    RecyclerView lvView;
    private List<Appointment.Item> data;
    private MultiplicityRecylerAdapter<Appointment.Item> adapater;
    private UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_refresh_recycker);
    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("预约");
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestData(1);
            }
        });
        refresh.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));

        userModel=new UserModel(getContext());
        data=new ArrayList();
        adapater=new MultiplicityRecylerAdapter(getContext(),data,lvView);

        adapater.empty="暂无预约信息...";
        loadingFragment.setRequestMessage();
        adapater.implementRecyclerAdapterMethods(new RecyclerAdapter.RecyclerAdapterMethods<Appointment.Item>() {
            @Override
            public void onBindViewHolder(RecyclerAdapter.ViewHolder viewHolder, final Appointment.Item item, final int position) {
                viewHolder.setText(R.id.tv_name, item.getNickname());
                viewHolder.setText(R.id.tv_date, item.getClinicDate());
                viewHolder.setText(R.id.btn_status, item.getStatusName());
                Picasso.with(getContext()).load(item.getHeadImage()).placeholder(icon_default).
                        transform(new CircleTransform()).into((ImageView) viewHolder.getView(R.id.iv_photo));


            }

            @Override
            public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
                return new RecyclerAdapter.ViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.list_item_appointment, viewGroup, false));
            }

            @Override
            public int getItemCount() {
                return data.size();
            }
        });
        adapater.setOnLoadMoreListener(new MultiplicityRecylerAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore(int page) {
                requestData(page);
            }
        });

        lvView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (refresh.isRefreshing()){
                    return true;
                }
                return false;
            }
        });
        adapater.setOnClickEvent(new RecyclerAdapter.OnClickEvent<Appointment.Item>() {
            @Override
            public void onClick(View v, Appointment.Item item, int position) {
                if (CommonUtil.isFastClick()) return;
                Bundle bundle = new Bundle();
                // bundle.putParcelable(AppConstants.ITEM, item);
                /*if (AppConstants.USER_TYPE_PATIENT.equals(item.typeId)) {
                    ViewUtil.openActivity(PatientHActivity.class, bundle, getContext());
                } else {
                    ViewUtil.openActivity(DoctorDetialActivity.class, bundle, getContext());
                }*/
            }
        });

        refresh.setAnimationCacheEnabled(true);
        (new Handler()).post(new Runnable() {
            @Override
            public void run() {
                refresh.setRefreshing(true);
                requestData(1);
            }
        });


    }

    private void requestData(final int page){
        userModel.requestAppointmentList(page, new SimpleResponseListener<Appointment>() {
            @Override
            public void requestSuccess(Appointment info, Response response) {
                if (page==1){
                    adapater.addItemAll(info.getList());
                }else {
                    adapater.setNextPage(info.getList());
                }
                //scrollListener.nextPage(info.getList());
            }

            @Override
            public void requestError(HttpException e, ResponseMessage info) {

            }

            @Override
            public void requestView() {
                super.requestView();
                if (CommonUtil.notNull(refresh)){
                    if (page==1) {
                        refresh.setRefreshing(false);
                    }else{

                    }
                    adapater.checkEmpty();
                }
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapater.onDestroyReceiver();
    }
}