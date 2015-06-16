package com.roller.medicine.info;

import com.android.common.domain.ResponseMessage;

import java.util.List;

/**
 * @author Hua_
 * @Description:
 * @date 2015/6/16 - 17:12
 */
public class MyHomeInfo extends ResponseMessage {
    public String id;
    //喜欢
    public String praiseCount;
    //评论
    public String replyCount;
    //粉丝
    public String fansCount;
    //关注
    public String attentCount;
    public String nickname;
    public String headImage;
    public String qrCode;
    public String intro;
    public List<HomeInfo.Family> list;

}
