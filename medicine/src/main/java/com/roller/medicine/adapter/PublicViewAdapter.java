package com.roller.medicine.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class PublicViewAdapter<T> extends BaseAdapter implements OnClickListener {
	protected LayoutInflater mInflater;
	protected Context mContext;
	protected List<T> mDatas = new ArrayList<T>();
	protected final int mItemLayoutId;
	private ICommonGetView<T> commonGetView;
	private ICommonOnClick commonOnClick;
	private OnClickListener onClick;
	private Object tag = -1;

	public PublicViewAdapter(Context context, List<T> mDatas, int itemLayoutId,
			ICommonGetView<T> commonGetView, ICommonOnClick commonOnClick, Object tag) {
		this.mContext = context;
		this.mInflater = LayoutInflater.from(mContext);
		if(mDatas != null) {
			this.mDatas = mDatas;
		}	
		this.mItemLayoutId = itemLayoutId;
		this.commonGetView = commonGetView;
		this.commonOnClick = commonOnClick;
		this.onClick = this;
		if(tag != null)
		this.tag = tag;
	}

	@Override
	public int getCount() {
		return mDatas.size();
	}

	@Override
	public T getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final PublicViewHolder viewHolder = getViewHolder(position,
				convertView, parent);
		commonGetView.commonGetView(viewHolder, getItem(position), onClick,
				position, tag);
		return viewHolder.getConvertView();

	}

	private PublicViewHolder getViewHolder(int position, View convertView,
			ViewGroup parent) {
		return PublicViewHolder.get(mContext, convertView, parent,
				mItemLayoutId, position);
	}

	public interface ICommonGetView<T> {
		public void commonGetView(PublicViewHolder helper, T item,
				OnClickListener onClick, int position,Object tag);
	}

	public interface ICommonOnClick {
		public void commonOnClick(View v);
	}  
	
	@Override
	public void onClick(View v) {
		commonOnClick.commonOnClick(v);
	}

	public void setTag(Object tag){
		this.tag = tag;
	}
	
	public List<T> getAdapterData(){
		return mDatas;
	}
	public void addItem(T item) {
		mDatas.add(item);
		notifyDataSetChanged();
	}

	public void addItemsTop(List<T> item) {
		mDatas.addAll(0, item);
		notifyDataSetChanged();
	}

	public void addItemsButtom(List<T> item) {
		mDatas.addAll(mDatas.size(), item);
		notifyDataSetChanged();
	}

	public void addItems(List<T> mItems) {

		mDatas.addAll(mItems);
		notifyDataSetChanged();
	}

	public void removeAllItems() {
		mDatas = null;
		mDatas = new ArrayList<T>();
		notifyDataSetChanged();
	}
	
}
