package com.roller.medicine.httpservice;

public class Constants {

	/***
	 * 放所有请求参数
	 * 
	 * @author Administrator
	 * 
	 */
	public static class PARAM {
		/** key */
		public static final String KEY = "key";
		/** 密码 */
		public static final String USERNAME = "username";
		/** 密码 */
		public static final String PWD = "pwd";
	}

	/***
	 * 放所有适配器参数
	 * 
	 * @author Administrator
	 * 
	 */
	public static class ADAPTER {

	}

	/***
	 * 放所有请求路径参数
	 * 
	 * @author Administrator
	 * 
	 */
	public static class URL {
		public static final int RESPONSE_SUCCESS = 200;

		// public static final String IP = "180.153.243.69";
		// public static final String PORT = "8080";

		public static final String IP = "192.168.1.88";
		public static final String PORT = "8080";

		public static final String IMAGEIP = "http://" + IP + ":" + PORT;

		/**
		 * 关注，粉丝
		 */
		public static final String GETRELATIONLISTBYMAP = "http://" + IP + ":"
				+ PORT + "/crm/relation_sp/getRelationListByMap.json";

		public static final String GETPRAISELISTBYMAP = "http://" + IP + ":"
				+ PORT + "/crm/praise_sp/getPraiseListByMap.json";

		public static final String SAVEDOCTOR = "http://" + IP + ":" + PORT
				+ "/crm/user_sp/saveDoctor.json";

		public static final String GETUSERHOME = "http://" + IP + ":" + PORT
				+ "/crm/user_sp/getUserHome.json";

		public static final String GETUSERBYTOKEN = "http://" + IP + ":" + PORT
				+ "/crm/user_sp/getUserByToken.json";

		public static final String GETPOSTLISTBYMAP = "http://" + IP + ":"
				+ PORT + "/crm/post_sp/getPostListByMap.json";

		public static final String SAVEPRAISE = "http://" + IP + ":" + PORT
				+ "/crm/praise_sp/savePraise.json";

		public static final String DELETEREPLY = "http://" + IP + ":" + PORT
				+ "/crm/praise_sp/deleteReply.json";

		public static final String GETPOSTBYMAP = "http://" + IP + ":" + PORT
				+ "/crm/post_sp/getPostByMap.json";

		public static final String SAVEREPLY = "http://" + IP + ":" + PORT
				+ "/crm/reply_sp/saveReply.json";

		public static final String INFORMREPLY = "http://" + IP + ":" + PORT
				+ "/crm/reply_sp/informReply.json";

		public static final String DELETEPRAISE = "http://" + IP + ":" + PORT
				+ "/crm/praise_sp/deletePraise.json";

		public static final String GETPOSTREPLYLISTBYMAP = "http://" + IP + ":"
				+ PORT + "/crm/post_sp/getPostReplyListByMap.json";

		public static final String SAVEFAMILYGROUP = "http://" + IP + ":"
				+ PORT + "/crm/family_sp/saveFamilyGroup.json";

		public static final String GETFAMILYLISTBYMAP = "http://" + IP + ":"
				+ PORT + "/crm/family_sp/getFamilyListByMap.json";

		public static final String DELETEFAMILYGROUP = "http://" + IP + ":"
				+ PORT + "/crm/family_sp/deleteFamilyGroup.json";

		public static final String UPDATEFAMILYGROUP = "http://" + IP + ":"
				+ PORT + "/crm/family_sp/updateFamilyGroup.json";

		public static final String GETPATIENTHOME = "http://" + IP + ":" + PORT
				+ "/crm/patient_sp/getPatientHome.json";
	}

	public static class TAG {
		public static final int TAG_NONE = -1;
		public static final int TAG_RECOMMENDED_LIST = 1;
		public static final int TAG_COMMENTS_LIST = 2;
		public static final int TAG_IMAGE_LIST = 3;
		public static final int TAG_IMAGE_GRID = 4;
		public static final int TAG_TRUE = 0x11111;
		public static final int TAG_FALSE = 0x11112;
	}

	public static class CHAT {

		public static final String APPKEY = "ed93dc65-d455-4735-889a-50ee2fd5323e";

		/** 1 在线 */
		public static final int STATE_ON_LINE = 1;
		/** -1 离线状态, 当网络连接将重新连接 */
		public static final int STATE_OFF_LINE = -1;
		/** 0 没有登陆或者已销毁 */
		public static final int STATE_UN_LINE = 0;

	}

	public static class FRAGMENT {
		public static final int FRAGMENT_MAIN_GETJOB = 0;
		public static final int FRAGMENT_MAIN_CHANNEL = 1;
		public static final int FRAGMENT_MAIN_FIND = 2;
		public static final int FRAGMENT_MAIN_TALK = 3;
		public static final int FRAGMENT_MAIN_ME = 4;

	}

	public static final String ID = "id";
	public static final String CONTENT = "content";
	/**
	 * id自动增长常量
	 */
	public static final String ONFOCUS = "1";
	public static final String ONFANS = "2";
	public static final String NICKNAME = "3";
	public static final String PHONE = "4";
	public static final String DESCRIBE = "5";

}
