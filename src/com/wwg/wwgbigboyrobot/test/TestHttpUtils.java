package com.wwg.wwgbigboyrobot.test;

import com.wwg.wwgbigboyrobot.utils.HttpUtils;

import android.test.AndroidTestCase;
import android.util.Log;

public class TestHttpUtils extends AndroidTestCase {
	public void testSendInfo(){
		String result = HttpUtils.doGet("你好");
		Log.e("test", result);
		String result2 = HttpUtils.doGet("给我讲个笑话");
		Log.e("test", result2);
		String result3 = HttpUtils.doGet("给我讲个鬼故事");
		Log.e("test", result3);
		String result4 = HttpUtils.doGet("你真好");
		Log.e("test", result4);
	}
}
