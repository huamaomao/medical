package com.roller.medicine.httpservice;

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
public final class GotyeService{
  private final String TAG="GotyeService";
  public static GotyeAPI gotyeAPI= GotyeAPI.getInstance();

    /****
     * 获取聊天记录集合
     * @param chatUser
     * @return
     */
    public List<GotyeMessage> getMessageList(GotyeUser chatUser,boolean flag){
        return gotyeAPI.getMessageList(chatUser,flag);
    }



    /*****
     * 聊天监听初始化
     * @param chatUser
     * @param messageListener
     */
    public void initMessageList(final GotyeUser chatUser,final ChatMessageListener messageListener){
        gotyeAPI.requestAddFriend(chatUser);
        gotyeAPI.activeSession(chatUser);
        gotyeAPI.markMessagesAsRead(chatUser, true);
        final GotyeUser loginUser=gotyeAPI.getLoginUser();
        gotyeAPI.addListener(new ChatListener() {
            @Override
            public void onSendMessage(int code, GotyeMessage message) {
                Log.i("onSendMessage:"+message.getText());
                messageListener.onSendMessage(code,message);
            }

            @Override
            public void onReceiveMessage(GotyeMessage message) {
                Log.i("onReceiveMessage:"+gotyeAPI.getUserDetail(message.getSender().getName(),false).getIcon());
                messageListener.onReceiveMessage(message);
                gotyeAPI.downloadMediaInMessage(message);

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
                messageListener.onMessageList(list);
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
    public GotyeMessage sendMessage(final GotyeUser chatUser,final String message,final  OnValidationListener listener){
        if (CommonUtil.isEmpty(message)){
            listener.errorMessage();
            return null;
        }
        GotyeMessage toSendMsg = GotyeMessage.createTextMessage(gotyeAPI.getLoginUser(), chatUser, message);
        Log.d(TAG, "发送消息code:" + gotyeAPI.sendMessage(toSendMsg));
        return toSendMsg;
    }

    /******
     * 发送消息
     * @param chatUser
     * @param imagePath
     */
    public GotyeMessage sendImageMessage(final GotyeUser chatUser,String imagePath){
        GotyeMessage gotyeMessage = GotyeMessage.createImageMessage(chatUser, imagePath);
        Log.d(TAG,"发送消息code:"+gotyeAPI.sendMessage(gotyeMessage));
        return gotyeMessage;
    }

    /******
     * 发送消息
     */
    public int getMessageCount(GotyeUser gotyeUser){
        return gotyeAPI.getUnreadMessageCount(gotyeUser);
    }

    public void initReceive(final ReceiveMessageListener listener){
        gotyeAPI.addListener(new ChatListener() {
            @Override
            public void onSendMessage(int code, GotyeMessage message) {

            }

            @Override
            public void onReceiveMessage(GotyeMessage message) {
                gotyeAPI.downloadMediaInMessage(message);
                listener.onReceiveMessage(message);
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


    /*****
     * 添加黑名单
     * @param id
     */
    public void addFriendBlocked(String id){
        GotyeUser gotyeUser=new GotyeUser();
        gotyeUser.setName(id);
        gotyeAPI.reqAddBlocked(gotyeUser);
    }

    /*****
     * 删除记录
     * @param id
     */
    public void clearMessage(String id){
        GotyeUser gotyeUser=new GotyeUser();
        gotyeUser.setName(id);
        gotyeAPI.clearMessages(gotyeUser);
    }


    /******
     * 会话列表
     */
    public List<GotyeChatTarget> getFriendSession(){
        return gotyeAPI.getSessionList();
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
        void onMessageList(List<GotyeMessage> messages);
        void onSendMessage(int code, GotyeMessage message);
    }

    public static interface ReceiveMessageListener{
        void onReceiveMessage(GotyeMessage message);
    }

    public static interface OnValidationListener{
        void errorMessage();
    }
}
