package com.ska.x_bionic.util;

public class JasonEntity {
	private static int status;
	private static String data;
	private static String messages;

	private JasonEntity() {

	}

	public static JasonEntity getJasonEntity(String jason) {
		JasonEntity jasonEntity = new JasonEntity();
		status = Integer.parseInt(JsonUtil.getJsonValueByKey(jason, "status"));
		data = JsonUtil.getJsonValueByKey(jason, "data");
		messages = JsonUtil.getJsonValueByKey(jason, "message");
		return jasonEntity;
	}

	public int getStatus() {
		return status;
	}

	public String getData() {
		return data;
	}

	public String getMessages() {
		return messages;
	}

}
