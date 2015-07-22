package com.roller.medicine.wxapi;

import android.content.Intent;
import android.os.Bundle;

import com.android.common.domain.ResponseMessage;
import com.android.common.util.AppHttpExceptionHandler;
import com.android.common.util.Log;
import com.android.common.util.ViewUtil;
import com.android.common.viewmodel.SimpleResponseListener;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.roller.medicine.base.BaseLoadingToolbarActivity;
import com.roller.medicine.event.WeixinEvent;
import com.roller.medicine.info.TokenInfo;
import com.roller.medicine.info.UserResponseInfo;
import com.roller.medicine.service.MedicineGotyeService;
import com.roller.medicine.ui.HomeActivity;
import com.roller.medicine.utils.AppConstants;
import com.roller.medicine.viewmodel.DataModel;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import de.greenrobot.event.EventBus;


public class WXEntryActivity extends BaseLoadingToolbarActivity implements IWXAPIEventHandler
{

    private IWXAPI api;
    private DataModel service;

    public void onCreate(Bundle paramBundle)
    {
        super.onCreate(paramBundle);
        service=new DataModel();
        this.api = WXAPIFactory.createWXAPI(this, AppConstants.APPID_WEIXIN, false);
        this.api.handleIntent(getIntent(), this);

    }




    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleIntent(intent);
    }


    private void handleIntent(Intent intent) {

        SendAuth.Resp resp = new SendAuth.Resp(intent.getExtras());
        if (resp.errCode == BaseResp.ErrCode.ERR_OK) {
            //用户同意

        }

    }


    @Override
    public void onReq(BaseReq baseReq) {

    }
    /****
     * ErrCode	ERR_OK = 0(用户同意)
     ERR_AUTH_DENIED = -4（用户拒绝授权）
     ERR_USER_CANCEL = -2（用户取消）
     * @param
     */
    @Override
    public void onResp(BaseResp baseResp) {
        switch (baseResp.errCode){
            case 0:
                if (baseResp instanceof  SendAuth.Resp){
                    SendAuth.Resp resp=(SendAuth.Resp)baseResp;
                    EventBus.getDefault().post(new WeixinEvent(resp.code));
                }else {

                }
                // 同意
       /* startService(new Intent(this, MedicineGotyeService.class));
        ViewUtil.startTopActivity(HomeActivity.class,this);*/
                break;
            case -4:
                showMsg("用户拒绝授权");
                break;
            case -2:
                showMsg("用户取消");
                break;
            default:
                break;
        }
        finish();

    }

}