package com.wwg.wwgbigboyrobot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.wwg.wwgbigboyrobot.bean.ChatMessage;
import com.wwg.wwgbigboyrobot.bean.ChatMessage.Type;
import com.wwg.wwgbigboyrobot.utils.HttpUtils;

public class MainActivity extends Activity {

	private ListView listView;
	private ChatMessageAdapter mAdapter;
	private List<ChatMessage> mData;

	private EditText msg_info;
	private Button send;

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			ChatMessage fromMsg = (ChatMessage) msg.obj;
			mData.add(fromMsg);
			mAdapter.notifyDataSetChanged();
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		initView();
		initData();
		initListener();

	}

	private void initListener() {
		msg_info.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				Log.d("tag", arg1+"-");
				if(arg1 == true){
					listView.smoothScrollToPosition(mData.size()-1);
				}
				
			}
		});
		
		send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				final String msg = msg_info.getText().toString().trim();
				if (TextUtils.isEmpty(msg)) {
					Toast.makeText(MainActivity.this, "内容不能为空",
							Toast.LENGTH_SHORT).show();
					return;
				}

				ChatMessage toMessage = new ChatMessage();
				toMessage.setDate(new Date());
				toMessage.setMsg(msg);
				toMessage.setType(Type.OUTCOMING);
				mData.add(toMessage);
				mAdapter.notifyDataSetChanged();

				msg_info.setText("");

				new Thread(new Runnable() {

					@Override
					public void run() {
						ChatMessage formMessage = HttpUtils.sendMessage(msg);
						Message message = Message.obtain();
						message.obj = formMessage;
						mHandler.sendMessage(message);
					}
				}).start();

			}
		});

	}

	private void initData() {
		mData = new ArrayList<ChatMessage>();
		mData.add(new ChatMessage("您好，我是大男孩", Type.INCOMING, new Date()));
		mAdapter = new ChatMessageAdapter(this, mData);
		listView.setAdapter(mAdapter);
	}

	private void initView() {
		listView = (ListView) findViewById(R.id.id_listview_msgs);
		msg_info = (EditText) findViewById(R.id.id_input_msg);
		send = (Button) findViewById(R.id.id_send_msg);
	}

}
