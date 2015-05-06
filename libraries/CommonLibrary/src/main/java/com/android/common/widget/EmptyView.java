package com.android.common.widget;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by IzanRodrigo on 19/03/2015.
 */
public interface EmptyView {
   @Nullable
   View getEmptyView();

   void setEmptyView(@NonNull View emptyView);

   @Nullable
   View getErrorView();

   void setErrorView(@NonNull View errorView);

   @Nullable
   View getLoadingView();

   void setLoadingView(@NonNull View loadingView);

   View getContentView();

   void showEmpty();

   void showError();

   void showLoading();

   void showContent();
}
