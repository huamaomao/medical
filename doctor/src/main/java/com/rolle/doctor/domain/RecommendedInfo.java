package com.rolle.doctor.domain;

import com.android.common.domain.ResponseMessage;

import java.util.List;

/**
 * Created by Dove on 2015/4/24.
 */
public class RecommendedInfo extends ResponseMessage{

    public List<RecommendedItemInfo> list;

    public List<RecommendedItemInfo> getList() {
        return list;
    }

    public void setList(List<RecommendedItemInfo> list) {
        this.list = list;
    }
}
