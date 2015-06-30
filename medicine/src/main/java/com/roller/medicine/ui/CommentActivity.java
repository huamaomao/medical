package com.roller.medicine.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import com.android.common.domain.ResponseMessage;
import com.android.common.util.AppHttpExceptionHandler;
import com.android.common.util.CommonUtil;
import com.android.common.viewmodel.SimpleResponseListener;
import com.android.common.widget.InputMethodLinearLayout;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.roller.medicine.R;
import com.roller.medicine.base.BaseLoadingToolbarActivity;
import com.roller.medicine.info.ReplyInfo;
import com.roller.medicine.info.UserInfo;
import com.roller.medicine.utils.AppConstants;
import com.roller.medicine.viewmodel.DataModel;
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
	private String userId;
	private DataModel service;
	private int type;
	private String  id;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message_comment_fragment);
	}
	protected void initView(){
		Intent intent =getIntent();
		userId=getIntent().getExtras().getString(AppConstants.ITEM, "0");
		type=getIntent().getExtras().getInt(AppConstants.TYPE, 0);
		id=getIntent().getExtras().getString(AppConstants.DATA_CODE);
		super.initView();
		llLogin.setOnSizeChangedListenner(new InputMethodLinearLayout.OnSizeChangedListenner() {
			@Override
			public void onSizeChange(boolean paramBoolean, int w, int h) {

			}
		});

		service=new DataModel();


	}

	@OnClick(R.id.btn_send)
	public void onConfirm(){
		if (CommonUtil.isEmpty(et_content.getText().toString())){
			showLongMsg("请填写内容");
			return;
		}
		showLoading();
		if (type==0){
			service.requestComment(userId, et_content.getText().toString(), new SimpleResponseListener<ResponseMessage>() {
				@Override
				public void requestSuccess(ResponseMessage info, Response response) {
					showLongMsg("评论成功");
					Intent intent=new Intent();
					ReplyInfo replyInfo=new ReplyInfo();
					UserInfo userInfo=service.getLoginUser();
					replyInfo.nickname=userInfo.nickname;
					replyInfo.headImage=userInfo.headImage;
					intent.putExtra(AppConstants.ITEM, replyInfo);
					setResult(AppConstants.CODE,intent);
					finish();
				}

				@Override
				public void requestError(HttpException e, ResponseMessage info) {
					new AppHttpExceptionHandler().via(getContext()).handleException(e, info);
				}
				@Override
				public void requestView() {
					hideLoading();
				}
			});
		}else if (type==1){
			service.saveReply(userId, null, et_content.getText().toString(), id, new SimpleResponseListener<ResponseMessage>() {
				@Override
				public void requestSuccess(ResponseMessage info, Response response) {
					showLongMsg("评论成功");
					setResult(AppConstants.CODE);
					finish();
				}

				@Override
				public void requestError(HttpException e, ResponseMessage info) {
					new AppHttpExceptionHandler().via(getContext()).handleException(e, info);
				}

				@Override
				public void requestView() {
					hideLoading();
				}
			});
		}


	}

	@OnClick(R.id.v_view)
	void onClose(){
		finish();
	}


}


