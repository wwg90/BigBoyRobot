package com.wwg.wwgbigboyrobot.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.Date;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.wwg.wwgbigboyrobot.bean.ChatMessage;
import com.wwg.wwgbigboyrobot.bean.Result;
import com.wwg.wwgbigboyrobot.bean.ChatMessage.Type;

public class HttpUtils {
	private static final String URL = "http://www.tuling123.com/openapi/api";
	private static final String API_KEY = "07f73bdc368edadc2f56d1fe9ef383ed";

	/**
	 * 发送一个消息，得到返回的一个消息
	 * 
	 * @param msg
	 * @return
	 */
	public static ChatMessage sendMessage(String msg) {
		ChatMessage chatMessage = new ChatMessage();
		Gson gson = new Gson();
		String jsonRes = doGet(msg);
		Result result;

		try {
			result = gson.fromJson(jsonRes, Result.class);
			chatMessage.setMsg(result.getText());
		} catch (JsonSyntaxException e) {
			chatMessage.setMsg("服务器在吃饭，请稍候再试");
		}
		chatMessage.setDate(new Date());
		chatMessage.setType(Type.INCOMING);
		return chatMessage;
	}

	public static String doGet(String msg) {
		String result = "";
		String url = setParams(msg);
		InputStream is = null;
		ByteArrayOutputStream baos = null;

		try {
			java.net.URL urlNet = new java.net.URL(url);
			HttpURLConnection conn = (HttpURLConnection) urlNet
					.openConnection();
			conn.setReadTimeout(5000);
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("GET");

			is = conn.getInputStream();
			int len = -1;
			byte[] buf = new byte[128];
			baos = new ByteArrayOutputStream();
			while ((len = is.read(buf)) != -1) {
				baos.write(buf, 0, len);
			}

			baos.flush();
			result = new String(baos.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	private static String setParams(String msg) {
		String url = "";
		try {
			url = URL + "?key=" + API_KEY + "&info="
					+ URLEncoder.encode(msg, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return url;
	}

}
