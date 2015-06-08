package com.rolle.doctor.presenter;

import com.android.common.domain.ResponseMessage;
import com.android.common.presenter.Presenter;
import com.android.common.util.CommonUtil;
import com.android.common.util.Log;
import com.android.common.view.IView;
import com.android.common.viewmodel.SimpleResponseListener;
import com.android.common.viewmodel.ViewModel;
import com.gotye.api.GotyeChatTarget;
import com.gotye.api.GotyeMessage;
import com.gotye.api.GotyeUser;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.rolle.doctor.domain.FriendResponse;
import com.rolle.doctor.domain.MessageUser;
import com.rolle.doctor.domain.User;
import com.rolle.doctor.ui.BaseActivity;
import com.rolle.doctor.util.TimeUtil;
import com.rolle.doctor.viewmodel.GotyeModel;
import com.rolle.doctor.viewmodel.UserModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hua
 * @Description: 注册
 */
public class MessageListPresenter extends Presenter {

    private IMessageView view;
    private GotyeModel gotyeModel;
    private UserModel model;

     public MessageListPresenter(IMessageView iView) {
        this.view = iView;
        model=new UserModel((BaseActivity)view.getContext());
        gotyeModel=new GotyeModel();
    }

    public void initReceive(){
        gotyeModel.initReceive(new GotyeModel.ReceiveMessageListener() {
            @Override
            public void onReceiveMessage(GotyeMessage message){
                Log.d("onReceiveMessage  接受消息：" + message);
                //doMessage();
               getUser(message.getSender().getName());
        }
        });
    }


    /****
     * 处理消息
     */
   public void doMessage(){
       List<GotyeChatTarget> ls =gotyeModel.getFriendSession();
       if (CommonUtil.isNull(ls))return;
       int length=ls.size();
       GotyeChatTarget target=null;
       for (int i=length-1;i>=0;i++){
           target=ls.get(i);
           getUser(target.getName());
           getUser(target.getName());
       }
       view.setEmpty();
    }

     public void getUser(final String id){
         //可能获取不到用户  需从服务器拉取  不在好友列表中
         model.requestUserInfo(id, new SimpleResponseListener<User>() {
                  @Override
                  public void requestSuccess(User info, Response response) {
                      GotyeUser   userTarget=new GotyeUser();
                      userTarget.setName(info.id + "");
                      info.messageNum=gotyeModel.getMessageCount(userTarget);
                      GotyeMessage gotyeMessage=gotyeModel.getLastMessage(userTarget);
                      if (CommonUtil.notNull(gotyeMessage)){
                          info.message=gotyeMessage.getText();
                          info.date= TimeUtil.getDiffTime(gotyeMessage.getDate()*1000);
                      }
                      if (info.id==model.getLoginUser().id){
                          return;
                      }
                      view.addMessageItem(info);
                      view.setEmpty();
                  }

                  @Override
                  public void requestError(HttpException e, ResponseMessage info) {
                      User user1= model.getUser(Integer.valueOf(id));
                      if (CommonUtil.isNull(user1))return;
                      GotyeUser  userTarget=new GotyeUser();
                      userTarget.setName(user1.id + "");
                      user1.messageNum=gotyeModel.getMessageCount(userTarget);
                      GotyeMessage message=gotyeModel.getLastMessage(userTarget);
                      Log.d(user1);
                      if (CommonUtil.notNull(message)){
                          user1.message=message.getText();
                          user1.date= TimeUtil.getDiffTime(message.getDate()*1000);
                      }
                      if (user1.id==model.getLoginUser().id){
                          return;
                      }
                      view.addMessageItem(user1);
                      view.setEmpty();

                  }

              });

     }


    public static interface IMessageView extends IView{
        void addMessagelist(List<User> ls);
        void addMessageItem(User item);
        void setEmpty();
    }

}
