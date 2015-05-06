package com.android.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import com.android.common.R;

/**
 * Created by IzanRodrigo on 19/03/2015.
 */
public class CustomEmptyView extends BaseEmptyView {

   // Constants
   private static final int NO_VALUE = -1;

   // Constructors
   public CustomEmptyView(Context context) {
      super(context);
   }

   public CustomEmptyView(Context context, AttributeSet attrs) {
      super(context, attrs);
      readAttrs(attrs);
   }

   public CustomEmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
      super(context, attrs, defStyleAttr);
      readAttrs(attrs);
   }

   // Initialization methods
   private void readAttrs(AttributeSet attrs) {
      // Load attrs
      TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(
            attrs,
            R.styleable.CustomEmptyView,
            0, 0
      );

      // Read from attrs
      try {
         LayoutInflater layoutInflater = LayoutInflater.from(getContext());

         // Empty layout
         int emptyLayoutRes = typedArray.getResourceId(R.styleable.CustomEmptyView_emptyLayout, NO_VALUE);
         if (emptyLayoutRes != NO_VALUE) {
            setEmptyView(layoutInflater.inflate(emptyLayoutRes, this, false));
         }

         // Error layout
         int errorLayoutRes = typedArray.getResourceId(R.styleable.CustomEmptyView_errorLayout, NO_VALUE);
         if (errorLayoutRes != NO_VALUE) {
            setErrorView(layoutInflater.inflate(errorLayoutRes, this, false));
         }

         // Loading layout
         int loadingLayoutRes = typedArray.getResourceId(R.styleable.CustomEmptyView_loadingLayout, NO_VALUE);
         if (loadingLayoutRes != NO_VALUE) {
            setLoadingView(layoutInflater.inflate(loadingLayoutRes, this, false));
         }
      } finally {
         typedArray.recycle();
      }
   }
}
