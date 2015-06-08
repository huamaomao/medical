package com.rolle.doctor.presenter;

import com.android.common.domain.ResponseMessage;
import com.android.common.presenter.Presenter;
import com.android.common.util.AppHttpExceptionHandler;
import com.android.common.util.CommonUtil;
import com.android.common.util.ViewUtil;
import com.android.common.view.IView;
import com.android.common.viewmodel.SimpleResponseListener;
import com.android.common.viewmodel.ViewModel;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.rolle.doctor.ui.BaseActivity;
import com.rolle.doctor.ui.MyInviteCodeActivity;
import com.rolle.doctor.viewmodel.UserModel;

/**
 * @author Hua_
 * @Description:
 * @date 2015/4/22 - 14:16
 */
public class WriteInviteCodePresenter extends Presenter {
    private UserModel model;
    private IInviteCodeView view;

    public WriteInviteCodePresenter(IInviteCodeView view){
        this.view=view;
        model=new UserModel((BaseActivity)view.getContext());
    }

    public void doSave(){
        if (CommonUtil.isEmpty(view.getInviteCode())){
            view.msgShow("请填写邀请码");
            return;
        }
        view.showLoading();
        model.requestWriteInviteCode(view.getInviteCode(), new SimpleResponseListener<ResponseMessage>() {
            @Override
            public void requestSuccess(ResponseMessage info, Response response) {
                view.msgShow("填写成功");
                ViewUtil.openActivity(MyInviteCodeActivity.class,view.getContext(),true);
            }

            @Override
            public void requestError(HttpException e, ResponseMessage info) {
                new AppHttpExceptionHandler().via(view.getContext()).handleException(e, info);
            }

            @Override
            public void requestView() {
                view.hideLoading();
            }
        });


    }



    public static interface IInviteCodeView extends IView {
        public String getInviteCode();
    }
}
