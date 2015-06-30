package com.roller.medicine.ui;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.common.adapter.RecyclerAdapter;
import com.android.common.util.CommonUtil;
import com.android.common.util.ViewUtil;
import com.baoyz.widget.PullRefreshLayout;
import com.roller.medicine.R;
import com.roller.medicine.base.BaseToolbarActivity;
import com.roller.medicine.utils.CircleTransform;
import com.roller.medicine.viewmodel.DataModel;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * @author Hua_
 * @Description:
 * @date 2015/6/30 - 16:35
 */
public class DemoActivity extends BaseToolbarActivity {


    @InjectView(R.id.rv_view)
    RecyclerView rv_view;

    RecyclerAdapter<Integer> adapter;
    List<Integer> data;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycker);
    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("demo");
        data=new ArrayList<>();
        data.add(R.drawable.ic_work);
        data.add(R.drawable.ic_work);
        data.add(R.drawable.ic_work);
        data.add(R.drawable.ic_work);
        adapter=new RecyclerAdapter(getContext(),data);

        adapter.implementRecyclerAdapterMethods(new RecyclerAdapter.RecyclerAdapterMethods<Integer>() {
            @Override
            public void onBindViewHolder(final RecyclerAdapter.ViewHolder viewHolder, Integer item, int position) {
                 //
                Picasso.with(getContext()).load(item).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        Drawable drawable=new BitmapDrawable(getResources(),bitmap);
                        float height= viewHolder.itemView.getWidth()/drawable.getMinimumWidth()*drawable.getMinimumHeight();
                        RecyclerView.LayoutParams  layoutParams=(RecyclerView.LayoutParams) viewHolder.itemView.getLayoutParams();
                        layoutParams.height=(int)height;
                         viewHolder.itemView.setLayoutParams(layoutParams);
                        ((ImageView)viewHolder.itemView).setImageDrawable(drawable);
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
            }

            @Override
            public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
                return new RecyclerAdapter.ViewHolder(getLayoutInflater().inflate(R.layout.item_demo_image,viewGroup,false));
            }

            @Override
            public int getItemCount() {
                return data.size();
            }
        });
        ViewUtil.initRecyclerViewDecoration(rv_view, this, adapter);
    }
}
