package com.roller.medicine.customview.scrollview;

import android.content.Context;
import android.widget.ScrollView;

public class PublicScrollView extends ScrollView{

	public PublicScrollView(Context context) {
		super(context);
	}

	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		this.scrollTo(0, 0);
	}
}
