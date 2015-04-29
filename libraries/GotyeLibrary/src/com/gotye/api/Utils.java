package com.gotye.api;

import org.json.JSONException;
import org.json.JSONObject;

public class Utils {
	static GotyeChatTarget jsonToSession(JSONObject jsonObject) {
		GotyeChatTarget target = null;
		try {
			int type = jsonObject.getInt("type");
			if (type == 0) {
				target = new GotyeUser();
			} else if (type == 1) {
				target = new GotyeRoom();
			} else {
				target = new GotyeGroup();
			}
			target.Id = jsonObject.optLong("id");
			target.name = jsonObject.optString("name");
		} catch (JSONException e1) {
			e1.printStackTrace();
		}

		return target;
	}
}
