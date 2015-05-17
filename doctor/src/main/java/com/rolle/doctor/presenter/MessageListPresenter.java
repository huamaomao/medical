package com.rolle.doctor.presenter;

import com.android.common.presenter.Presenter;
import com.android.common.util.CommonUtil;
import com.android.common.util.Log;
import com.android.common.view.IView;
import com.gotye.api.GotyeChatTarget;
import com.gotye.api.GotyeMessage;
import com.gotye.api.GotyeUser;
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
                Log.d("onReceiveMessage  接受消息："+message);
                doMessage();
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
       User user1=null;
       GotyeUser userTarget=null;
       GotyeMessage message=null;
       for (GotyeChatTarget target:ls){
           //     可能获取不到用户  需从服务器拉取  不在好友列表中
           user1= model.getUser(Integer.valueOf(target.getName()));
           if (CommonUtil.notNull(user1)){
               userTarget=new GotyeUser();
               userTarget.setName(user1.id+"");
               user1.messageNum=gotyeModel.getMessageCount(userTarget);
               message=gotyeModel.getLastMessage(userTarget);
               Log.d(user1);
               if (CommonUtil.notNull(message)){
                   user1.message=message.getText();
                   user1.date= TimeUtil.getDiffTime(message.getDate()*1000);
               }
               userList.add(user1);
           }

       }
       view.addMessagelist(userList);
    }


    public static interface IMessageView extends IView{
        void addMessagelist(List<User> ls);
        void addMessageItem(User item);
        void pushMessageItem(User item);
    }

}
