package com.roller.medicine.ui;

import android.os.Bundle;
import android.widget.EditText;

import com.android.common.util.CommonUtil;
import com.lidroid.xutils.exception.HttpException;
import com.roller.medicine.R;
import com.roller.medicine.base.BaseLoadingToolbarActivity;
import com.roller.medicine.httpservice.MedicineDataService;
import com.roller.medicine.info.BaseInfo;
import com.roller.medicine.myinterface.SimpleResponseListener;
import com.roller.medicine.utils.Constants;
import com.roller.medicine.widget.InputMethodLinearLayout;

import butterknife.InjectView;
import butterknife.OnClick;

/****
 *  内容
 */
public class CommentActivity extends BaseLoadingToolbarActivity{

	@InjectView(R.id.ll_login)
	InputMethodLinearLayout llLogin;
	@InjectView(R.id.et_content)
	EditText et_content;
	private int userId;
	private MedicineDataService service;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message_comment_fragment);
	}
	protected void initView(){
		userId=getIntent().getIntExtra(Constants.ITEM,0);
		super.initView();
		llLogin.setOnSizeChangedListenner(new InputMethodLinearLayout.OnSizeChangedListenner() {
			@Override
			public void onSizeChange(boolean paramBoolean, int w, int h) {
				/*LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
				lp.gravity = Gravity.CENTER_HORIZONTAL;
				if (paramBoolean) {
					lp.setMargins(0, ViewUtil.px2dip(CommentActivity.this, 80f), 0, 0);

				} else {
					lp.setMargins(0, ViewUtil.px2dip(CommentActivity.this, 640f), 0, 0);

				}*/
			}
		});

		service=new MedicineDataService();


	}

	@OnClick(R.id.btn_send)
	public void onConfirm(){
		if (CommonUtil.isEmpty(et_content.getText().toString())){
			showLongMsg("请填写内容");
			return;
		}
		showLoading();
		try {
			service.requestComment(userId+"", et_content.getText().toString(), new SimpleResponseListener<BaseInfo>(){
				@Override
				public void requestSuccess(BaseInfo info, String result) {
					showLongMsg("评论成功");
					setResult(Constants.CODE);
					finish();
				}

				@Override
				public void requestError(HttpException e, BaseInfo info) {
					showLongMsg("评论失败");
				}

				@Override
				public void requestView() {
					 hideLoading();
				}
			});
		}catch (Exception e){

		}

	}

	@OnClick(R.id.v_view)
	void onClose(){
		finish();
	}


}


