package com.rolle.doctor.presenter;

import com.android.common.presenter.Presenter;
import com.android.common.util.CommonUtil;
import com.android.common.view.IView;
import com.gotye.api.GotyeChatTarget;
import com.gotye.api.GotyeMessage;
import com.rolle.doctor.domain.MessageUser;
import com.rolle.doctor.domain.User;
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
        model=new UserModel(view.getContext());
        gotyeModel=new GotyeModel();
    }

    /****
     * 处理消息
     */
   public void doMessage(){
       List<GotyeChatTarget> ls =gotyeModel.getFriendMessage();
       if (CommonUtil.isNull(ls))return;
       List<MessageUser> userList=new ArrayList<>();
       MessageUser user=null;
       User user1=null;
       GotyeChatTarget userTarget=null;
       GotyeMessage message=null;
       for (GotyeChatTarget target:ls){
           user=new MessageUser();
           user1= model.getUser(target.getId()+"");
           if (CommonUtil.notNull(user1)){
               user.id=user1.id+"";
               user.nickname=user1.nickname;
               user.icon=user1.getPhotoId();
               userTarget=new GotyeChatTarget();
               userTarget.setId(user1.getId());
               message=gotyeModel.getLastMessage(userTarget);
               if (CommonUtil.notNull(message)){
                   user.message=message.getText();
                   user.date=message.getDate()+"";
               }
           }
           userList.add(user);
       }
       view.addMessagelist(userList);
    }


    public static interface IMessageView extends IView{
        void addMessagelist(List<MessageUser> ls);
        void addMessageItem(MessageUser item);
        void pushMessageItem(MessageUser item);
    }

}
