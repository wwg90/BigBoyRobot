package com.wwg.wwgbigboyrobot.test;

import com.wwg.wwgbigboyrobot.utils.HttpUtils;

import android.test.AndroidTestCase;
import android.util.Log;

public class TestHttpUtils extends AndroidTestCase {
	public void testSendInfo(){
		String result = HttpUtils.doGet("���");
		Log.e("test", result);
		String result2 = HttpUtils.doGet("���ҽ���Ц��");
		Log.e("test", result2);
		String result3 = HttpUtils.doGet("���ҽ��������");
		Log.e("test", result3);
		String result4 = HttpUtils.doGet("�����");
		Log.e("test", result4);
	}
}
