package com.android.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * Created by IzanRodrigo on 19/03/2015.
 */
public class BasicEmptyView extends BaseEmptyView {

   // Constants
   private static final int NO_VALUE = -1;

   // Constructors
   public BasicEmptyView(Context context) {
      super(context);
      initWidget();
   }

   public BasicEmptyView(Context context, AttributeSet attrs) {
      super(context, attrs);
      initWidget();
      readAttrs(attrs);
   }

   public BasicEmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
      super(context, attrs, defStyleAttr);
      initWidget();
      readAttrs(attrs);
   }

   private void initWidget() {
      setEmptyView(LayoutInflater.from(getContext()).inflate(R.layout.default_text, this, false));
      setErrorView(LayoutInflater.from(getContext()).inflate(R.layout.default_error, this, false));
      setLoadingView(LayoutInflater.from(getContext()).inflate(R.layout.default_loading, this, false));
   }

   private void readAttrs(AttributeSet attrs) {
      // Load attrs
      TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(
            attrs,
            R.styleable.BasicEmptyView,
            0, 0
      );

      // Read from attrs
      try {
         // Empty text
         String emptyText = typedArray.getString(R.styleable.BasicEmptyView_emptyText);
         if (!TextUtils.isEmpty(emptyText)) {
            ((TextView) getEmptyView()).setText(emptyText);
         }

         // Error text
         String errorText = typedArray.getString(R.styleable.BasicEmptyView_errorText);
         TextView errorTextView = (TextView) getErrorView().findViewById(R.id.emptyView_errorText);
         if (!TextUtils.isEmpty(errorText)) {
            errorTextView.setText(errorText);
         }

         // Text padding
         int textPadding = (int) typedArray.getDimension(R.styleable.BasicEmptyView_textPadding, NO_VALUE);
         if (textPadding != NO_VALUE) {
            getEmptyView().setPadding(textPadding, textPadding, textPadding, textPadding);
            errorTextView.setPadding(textPadding, textPadding, textPadding, textPadding);
         }

         // Text size
         int textSize = typedArray.getInteger(R.styleable.BasicEmptyView_textSizeSP, NO_VALUE);
         if (textSize != NO_VALUE) {
            ((TextView) getEmptyView()).setTextSize(textSize);
            errorTextView.setTextSize(textSize);
         }
      } finally {
         typedArray.recycle();
      }
   }

   @NonNull
   @Override
   public View getEmptyView() {
      return super.getEmptyView();
   }

   @NonNull
   @Override
   public View getErrorView() {
      return super.getErrorView();
   }

   @NonNull
   @Override
   public View getLoadingView() {
      return super.getLoadingView();
   }
}
