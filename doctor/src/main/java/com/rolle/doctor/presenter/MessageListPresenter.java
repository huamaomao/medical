package com.rolle.doctor.presenter;

import com.android.common.presenter.Presenter;
import com.android.common.util.CommonUtil;
import com.android.common.util.Log;
import com.android.common.view.IView;
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
                User user=getUser(message.getSender().getName());
                if (CommonUtil.notNull(user)){
                    if (user.id==model.getLoginUser().id){
                       return;
                    }
                    view.addMessageItem(user);
                }
        }
        });
    }


    /****
     * 处理消息
     */
   public void doMessage(){
       List<GotyeChatTarget> ls =gotyeModel.getFriendSession();
       if (CommonUtil.isNull(ls))return;
       List<User> userList=new ArrayList<>();
       User user=null;
       for (GotyeChatTarget target:ls){
           user=getUser(target.getName());
           if (CommonUtil.notNull(user)){
               if (target.getName().equals(model.getLoginUser().id+"")){
                    continue;
               }
                userList.add(user);
           }

       }
      // view.addMessagelist(userList);
    }

     public  User getUser(String id){
         User user1=null;
         GotyeUser userTarget=null;
         GotyeMessage message=null;
             //     可能获取不到用户  需从服务器拉取  不在好友列表中
             user1= model.getUser(Integer.valueOf(id));
             if (CommonUtil.notNull(user1)){
                 userTarget=new GotyeUser();
                 userTarget.setName(user1.id + "");
                 user1.messageNum=gotyeModel.getMessageCount(userTarget);
                 message=gotyeModel.getLastMessage(userTarget);
                 Log.d(user1);
                 if (CommonUtil.notNull(message)){
                     user1.message=message.getText();
                     user1.date= TimeUtil.getDiffTime(message.getDate()*1000);
                 }
             }else {
                 model.requestUserInfo(id, new ViewModel.ModelListener<User>() {
                     @Override
                     public void model(Response response, User user) {
                         GotyeUser   userTarget=new GotyeUser();
                         userTarget.setName(user.id + "");
                         user.messageNum=gotyeModel.getMessageCount(userTarget);
                         GotyeMessage gotyeMessage=gotyeModel.getLastMessage(userTarget);
                         if (CommonUtil.notNull(gotyeMessage)){
                             user.message=gotyeMessage.getText();
                             user.date= TimeUtil.getDiffTime(gotyeMessage.getDate()*1000);
                         }
                         view.addMessageItem(user);
                     }

                     @Override
                     public void errorModel(HttpException e, Response response) {

                     }

                     @Override
                     public void view() {

                     }
                 });
             }

         return user1;

     }


    public static interface IMessageView extends IView{
        void addMessagelist(List<User> ls);
        void addMessageItem(User item);
        void pushMessageItem(User item);
    }

}
