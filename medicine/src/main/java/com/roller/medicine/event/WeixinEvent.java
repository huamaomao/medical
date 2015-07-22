package com.roller.medicine.event;

/**
 * @author Hua_
 * @Description:
 * @date 2015/7/9 - 17:25
 */
public class WeixinEvent extends BaseEvent{
    private String code;

    public WeixinEvent(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}
