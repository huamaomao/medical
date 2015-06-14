package com.roller.medicine.info;

import java.util.ArrayList;
import java.util.List;

public  class PostInfo {
        public String id;
        public String boardId;
        public String title;
        public String content;
        public String createTime;
        public String praiseCount;
        public String replyCount;
        public String source;
        public List<ImageInfo> images=new ArrayList();

    }