package com.roller.medicine.httpservice;

import java.util.HashMap;

import com.roller.medicine.myinterface.IResponseListener;

public class DataService {

	private static DataService dataService;

	public static DataService getInstance() {
		if (dataService == null) {
			dataService = new DataService();
		}
		return dataService;
	}

	private HashMap<String, Object> map;
	// private DataHttpService service = DataHttpService.getInstance();
	private DataHttpService service = new DataHttpService();

	public void httpConn(String... strs) {

	}

	public void testRequest(IResponseListener responseService, String username,
			String nickname, String realname, String pwd, String phone,
			String sex) throws Exception {
		HashMap map = new HashMap<String, Object>();
		map.put("username", username);
		map.put("nickname", nickname);
		map.put("realname", realname);
		map.put("pwd", pwd);
		map.put("phone", phone);
		map.put("sex", sex);
		DataHttpService.getInstance().requestByPost(
				responseService,
				"http://" + Constants.URL.IP
						+ ":8082/userAction!registerTemp.ds", map, 0);
	}
	
	/**
	 * 我的关注 我的粉丝
	 * @param responseService
	 * @param token
	 * @param typeId
	 * @throws Exception
	 */
	public void getRelationListByMap(IResponseListener responseService, String token,
			String typeId) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);
		map.put("typeId", typeId);
		DataHttpService.getInstance().requestByPost(
				responseService,Constants.URL.GETRELATIONLISTBYMAP, map, Constants.TAG.TAG_NONE);
	}
	
	/**
	 * 喜欢
	 * @param responseService
	 * @param token
	 * @throws Exception
	 */
	public void getPraiseListByMap(IResponseListener responseService, String token) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);
		DataHttpService.getInstance().requestByPost(
				responseService,Constants.URL.GETPRAISELISTBYMAP, map, Constants.TAG.TAG_NONE);
	}
	
	/**
	 * 保存个人信息
	 * @param responseService
	 * @param token
	 * @param type
	 * @param nickname
	 * @param email
	 * @param tel
	 * @param userName
	 * @param sex
	 * @param photoId
	 * @param idCardNo
	 * @param intro
	 * @param address
	 * @param regionId
	 * @param birthday
	 * @param age
	 * @throws Exception
	 */
	public void saveDoctor(IResponseListener responseService, String token,String type,
			String nickname,String email,String tel,String userName,String sex,String photoId,
			String idCardNo,String intro,String address,String regionId,String birthday,
			String age) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);
		map.put("type", type);
		map.put("nickname", nickname);
		map.put("email", email);
		map.put("tel", tel);
		map.put("userName", userName);
		map.put("sex", sex);
		map.put("photoId", photoId);
		map.put("idCardNo", idCardNo);
		map.put("intro", intro);
		map.put("address", address);
		map.put("regionId", regionId);
		map.put("birthday", birthday);
		map.put("age", age);
		DataHttpService.getInstance().requestByPost(
				responseService,Constants.URL.SAVEDOCTOR, map, Constants.TAG.TAG_NONE);
	}
	
	/**
	 * 得到我的模块数据
	 * @param responseService
	 * @param token
	 * @throws Exception
	 */
	public void getUserHome(IResponseListener responseService, String token) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);
		DataHttpService.getInstance().requestByPost(
				responseService,Constants.URL.GETUSERHOME, map, Constants.TAG.TAG_NONE);
	}
	
	/**
	 * 个人信息
	 * @param responseService
	 * @param token
	 * @throws Exception
	 */
	public void getUserByToken(IResponseListener responseService, String token) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);
		DataHttpService.getInstance().requestByPost(
				responseService,Constants.URL.GETUSERBYTOKEN, map, Constants.TAG.TAG_NONE);
	}
	

	
	/**
	 * 点赞
	 * @param responseService
	 * @param token
	 * @throws Exception
	 */
	public void savePraise(IResponseListener responseService, String token,String postId
			,String repiyId,String typeId,String mainUserId) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);
		map.put("postId", postId);
		map.put("repiyId", repiyId);
		map.put("typeId", typeId);
		map.put("mainUserId", mainUserId);
		DataHttpService.getInstance().requestByPost(
				responseService,Constants.URL.SAVEPRAISE, map, Constants.TAG.TAG_NONE);
	}
	
	/**
	 * 取消赞
	 * @param responseService
	 * @param token
	 * @throws Exception
	 */
	public void deletePraise(IResponseListener responseService, String token,String id) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);
		map.put("id", id);
		DataHttpService.getInstance().requestByPost(
				responseService,Constants.URL.DELETEREPLY, map, Constants.TAG.TAG_NONE);
	}
	
	/**
	 * 谈论详情
	 * @param responseService
	 * @param token
	 * @param id
	 * @param boardId
	 * @throws Exception
	 */
	public void getPostByMap(IResponseListener responseService, String token,String id,String boardId) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);
		map.put("postId", id);
		map.put("boardId", boardId);
		DataHttpService.getInstance().requestByPost(
				responseService,Constants.URL.GETPOSTBYMAP, map, Constants.TAG.TAG_NONE);
	}
	
	/**
	 * 发表评论
	 * @param responseService
	 * @param token
	 * @param id
	 * @param boardId
	 * @throws Exception
	 */
	public void saveReply(IResponseListener responseService, String token,String id,String boardId,String content,String byReplyUserId) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);
		map.put("postId", id);
		map.put("boardId", boardId);
		map.put("content", content);
		map.put("byReplyUserId", byReplyUserId);
		DataHttpService.getInstance().requestByPost(
				responseService,Constants.URL.SAVEREPLY, map, Constants.TAG.TAG_NONE);
	}
	
	/**
	 * 举报内容
	 * @param responseService
	 * @param token
	 * @param id
	 * @throws Exception
	 */
	public void informReply(IResponseListener responseService, String token,String id) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);
		map.put("postId", id);
		DataHttpService.getInstance().requestByPost(
				responseService,Constants.URL.INFORMREPLY, map, Constants.TAG.TAG_NONE);
	}
	
	/**
	 *  删除评论
	 * @param responseService
	 * @param token
	 * @param id
	 * @throws Exception
	 */
	public void deleteReply(IResponseListener responseService, String token,String id) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);
		map.put("postId", id);
		DataHttpService.getInstance().requestByPost(
				responseService,Constants.URL.DELETEPRAISE, map, Constants.TAG.TAG_NONE);
	}
	
	/**
	 * 我的评论
	 * @param responseService
	 * @param token
	 * @throws Exception
	 */
	public void getPostReplyListByMap(IResponseListener responseService, String token) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);
		DataHttpService.getInstance().requestByPost(
				responseService,Constants.URL.GETPOSTREPLYLISTBYMAP, map, Constants.TAG.TAG_NONE);
	}
	
	/**
	 * 新建家庭成员
	 * @param responseService
	 * @param token
	 * @throws Exception
	 */
	public void saveFamilyGroup(IResponseListener responseService, String token,String mobile,
			String sex,String birthday,String appellation) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);
		map.put("mobile", mobile);
		map.put("sex", sex);
		map.put("birthday", birthday);
		map.put("appellation", appellation);
		DataHttpService.getInstance().requestByPost(
				responseService,Constants.URL.SAVEFAMILYGROUP, map, Constants.TAG.TAG_NONE);
	}
	
	/**
	 * 获取成员列表
	 * @param responseService
	 * @param token
	 * @throws Exception
	 */
	public void getFamilyListByMap(IResponseListener responseService, String token) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);
		DataHttpService.getInstance().requestByPost(
				responseService,Constants.URL.GETFAMILYLISTBYMAP, map, Constants.TAG.TAG_NONE);
	}
	
	/**
	 * 删除帐号
	 * @param responseService
	 * @param token
	 * @param groupId
	 * @param familyGroupId
	 * @throws Exception
	 */
	public void deleteFamilyGroup(IResponseListener responseService, String token,String groupId,
			String familyGroupId) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);
		map.put("groupId", groupId);
		map.put("familyGroupId", familyGroupId);
		DataHttpService.getInstance().requestByPost(
				responseService,Constants.URL.DELETEFAMILYGROUP, map, Constants.TAG.TAG_NONE);
	}
	
	/**
	 * 更改户主
	 * @param responseService
	 * @param token
	 * @param groupId
	 * @param familyGroupId
	 * @throws Exception
	 */
	public void updateFamilyGroup(IResponseListener responseService, String token,String groupId,
			String familyGroupId) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);
		map.put("groupId", groupId);
		map.put("familyGroupId", familyGroupId);
		DataHttpService.getInstance().requestByPost(
				responseService,Constants.URL.UPDATEFAMILYGROUP, map, Constants.TAG.TAG_NONE);
	}
	
	/**
	 * 获取首页数据
	 * @param responseService
	 * @param token
	 * @throws Exception
	 */
	public void getPatientHome(IResponseListener responseService, String token) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);
		DataHttpService.getInstance().requestByPost(
				responseService,Constants.URL.GETPATIENTHOME, map, Constants.TAG.TAG_NONE);
	}
}
