package com.rolle.doctor.viewmodel;

import com.android.common.presenter.Presenter;
import com.android.common.util.CommonUtil;
import com.android.common.util.Log;
import com.gotye.api.GotyeAPI;
import com.gotye.api.GotyeChatTarget;
import com.gotye.api.GotyeMessage;
import com.gotye.api.GotyeUser;
import com.gotye.api.listener.ChatListener;
import java.util.List;

/**
 * @author Hua_
 * @Description:
 * @date 2015/4/29 - 10:37
 */
public final class GotyeModel extends Presenter {
  private final String TAG="GotyeModel";
  public static GotyeAPI gotyeAPI=GotyeAPI.getInstance();

    /****
     * 获取聊天记录集合
     * @param chatUser
     * @return
     */
    public List<GotyeMessage> getMessageList(GotyeUser chatUser){
        return gotyeAPI.getMessageList(chatUser,true);

    }

    /*****
     * 聊天监听初始化
     * @param chatUser
     * @param messageListener
     */
    public void initMessageList(final GotyeUser chatUser,final ChatMessageListener messageListener){
        gotyeAPI.activeSession(chatUser);
        gotyeAPI.requestAddFriend(chatUser);
        final GotyeUser loginUser=gotyeAPI.getLoginUser();
        gotyeAPI.addListener(new ChatListener() {
            @Override
            public void onSendMessage(int code, GotyeMessage message) {

            }

            @Override
            public void onReceiveMessage(GotyeMessage message) {
                Log.i("onReceiveMessage:"+gotyeAPI.getUserDetail(message.getSender().getName(),false).getIcon());
                if(message.getSender()!=null
                        && chatUser.getName().equals(message.getSender().getName())
                        && loginUser.getName().equals(message.getReceiver().getName())){
                    messageListener.onReceiveMessage(message);
                    gotyeAPI.downloadMediaInMessage(message);
                }
            }

            @Override
            public void onDownloadMediaInMessage(int code, GotyeMessage message) {

            }

            @Override
            public void onReport(int code, GotyeMessage message) {

            }

            @Override
            public void onStartTalk(int code, boolean isRealTime, int targetType, GotyeChatTarget target) {

            }

            @Override
            public void onStopTalk(int code, GotyeMessage message, boolean isVoiceReal) {

            }

            @Override
            public void onDecodeMessage(int code, GotyeMessage message) {

            }

            @Override
            public void onGetMessageList(int code, List<GotyeMessage> list) {

            }

            @Override
            public void onOutputAudioData(byte[] datas) {

            }
        });
    }

    /******
     * 发送消息
     * @param chatUser
     * @param message
     */
    public void sendMessage(final GotyeUser chatUser,final String message,final  OnValidationListener listener){
        if (CommonUtil.isEmpty(message)){
            listener.errorMessage();
            return;
        }
        GotyeMessage toSendMsg =GotyeMessage.createTextMessage(gotyeAPI.getLoginUser(), chatUser,message);
        Log.d(TAG,"发送消息code:"+gotyeAPI.sendMessage(toSendMsg));
    }


    /******
     * 会话列表
     */
    public List<GotyeChatTarget> getFriendMessage(){
        return  gotyeAPI.getSessionList();
    }

    /****
     * 删除会话
     * @param target
     * @param flag  是否删除记录
     */
    public void deleteSession(GotyeChatTarget target,boolean flag){
        gotyeAPI.deleteSession(target, flag);
    }

    /****
     * 回去最后一条消息
     * @param target
     */
    public GotyeMessage getLastMessage(GotyeChatTarget target){
       return  gotyeAPI.getLastMessage(target);
    }

    /****
     * 删除会话
     * @param target
     */
    public void deleteSession(GotyeChatTarget target){
        gotyeAPI.markSessionIsTop(target, false);
    }

    public static interface ChatMessageListener{
        void onReceiveMessage(GotyeMessage message);
    }

    public static interface OnValidationListener{
        void errorMessage();
    }
}
