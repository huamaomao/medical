package com.roller.medicine.info;

import com.android.common.domain.ResponseMessage;

import java.util.List;

public class HomeAdviceInfo extends ResponseMessage {
	public String id;
	public String timeBucket;
	public String conditionId;
	public String isPharmacy;
	public String doctorAdvice;
	public String foodAdvice;
	public String foodId;
	public String disabled;
	public String createTime;
	public List<Food> foods;

	public static  class Food{
		public String id;
		public String foodName;
		public String foodExplain;
		public String typeId;
		public String url;
		public String disabled;
		public String createTime;
	}


}
