package com.rolle.doctor.wxapi;

import com.rolle.doctor.ui.BaseActivity;

public class WXEntryActivity extends BaseActivity

{
   /* implements IWXAPIEventHandler
  private IWXAPI api;

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.api = WXAPIFactory.createWXAPI(this, "wx574e614a1f64cf2c", false);
    this.api.handleIntent(getIntent(), this);
  }

  public void onReq(BaseReq paramBaseReq)
  {
  }

  public void onResp(BaseResp paramBaseResp)
  {
    Trace.e("weixin", paramBaseResp.toString());
    ShareFeedbackModel localShareFeedbackModel = new ShareFeedbackModel();
    String str;
    String[] arrayOfString;
    if (paramBaseResp.getType() == 0)
    {
      str = "moments";
      localShareFeedbackModel.setPlatform(str);
      arrayOfString = AppSharePreferences.getSharePlatform(this, localShareFeedbackModel.platform);
      if ((arrayOfString != null) && (arrayOfString.length >= 2))
      {
        AppSharePreferences.deleteSharePlatform(getApplicationContext(), localShareFeedbackModel.platform);
        localShareFeedbackModel.setType(arrayOfString[0]);
        localShareFeedbackModel.setId(arrayOfString[1]);
      }
      switch (paramBaseResp.errCode)
      {
      case -3:
      case -1:
      default:
        AppUtils.showToast(this, "分享异常");
        localShareFeedbackModel.setStatus("fail");
      case 0:
      case -2:
      case -4:
      }
    }
    while (true)
    {
      if ((arrayOfString != null) && (arrayOfString.length >= 2))
        AppService.startShareFeedback(getApplicationContext(), localShareFeedbackModel);
      finish();
      return;
      str = "moments";
      break;
      AppUtils.showToast(this, "分享成功");
      localShareFeedbackModel.setStatus("succ");
      continue;
      AppUtils.showToast(this, "分享取消");
      localShareFeedbackModel.setStatus("fail");
      continue;
      AppUtils.showToast(this, "分享拒绝");
      localShareFeedbackModel.setStatus("fail");
    }
  }*/
}