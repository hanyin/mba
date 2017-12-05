package com.hy.mvp.serivce;


import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;


import com.hy.mvp.app.MyApplication;
import com.hy.mvp.constant.Const;
import com.hy.mvp.greendao.db.ChatMsgDao;
import com.hy.mvp.greendao.db.SessionDao;
import com.hy.mvp.greendao.manager.AppConfigDef;
import com.hy.mvp.greendao.manager.AppConfigHelper;
import com.hy.mvp.listener.AcceptMsgListener;
import com.hy.mvp.listener.MsgListener;
import com.hy.mvp.ui.bean.Msg;
import com.hy.mvp.ui.bean.Session;
import com.hy.mvp.utils.XmppConnectionManager;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;

import java.net.DatagramSocket;
import java.net.SocketException;


/**
 * @author 白玉梁
 */
public class MsfService extends Service{

	private static MsfService mInstance = null;
	public static DatagramSocket ds = null;

	private NotificationManager mNotificationManager;

	private XmppConnectionManager mXmppConnectionManager;
	private XMPPConnection mXMPPConnection;
	
	//private CheckConnectionListener checkConnectionListener;
	//private FriendsPacketListener friendsPacketListener;
	
	private final IBinder binder = new MyBinder();

	public class MyBinder extends Binder {
		public MsfService getService() {
			return MsfService.this;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}
	

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
		try {
			ds = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		mNotificationManager = (NotificationManager) getSystemService(android.content.Context.NOTIFICATION_SERVICE);         // 通知
		mXmppConnectionManager = XmppConnectionManager.getInstance();
		initXMPPTask();		
	}

	public static MsfService getInstance() {
		return mInstance;
	}
	

	/**
	 * 初始化xmpp和完成后台登录
	 */
	private void initXMPPTask() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try{
				    initXMPP();	
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}).start();
	}

	/**
	 * 初始化XMPP
	 */
	void initXMPP() {
		String userId  = AppConfigHelper.getConfig(AppConfigDef.userId);
		String password = AppConfigHelper.getConfig(AppConfigDef.password);
		mXMPPConnection = mXmppConnectionManager.init();//初始化XMPPConnection
		try {
			mXMPPConnection.connect();
			mXMPPConnection.login(userId,password);
		} catch (XMPPException e) {
			e.printStackTrace();
		}
		MyApplication.xmppConnection=mXMPPConnection;
		/*ChatManager chatmanager = mXMPPConnection.getChatManager();
		chatmanager.addChatListener(new ChatManagerListener() {
			@Override
			public void chatCreated(Chat arg0, boolean arg1) {
				arg0.addMessageListener(new MsgListener(MsfService.this, mNotificationManager));
			}
		});*/
		mXMPPConnection.addPacketListener(new AcceptMsgListener(MsfService.this, mNotificationManager),null);
	}

	@Override
	public void onDestroy() {
		if(mNotificationManager!=null){
			
		}
		try {
			if (mXMPPConnection != null) {
				mXMPPConnection.disconnect();
				mXMPPConnection = null;
			}
			if(mXmppConnectionManager!=null){
				mXmppConnectionManager = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.onDestroy();
	}



	/**
	 * 登录XMPP
	 */
	/*void loginXMPP() {
		try {
			mPassword = PreferencesUtils.getSharePreStr(this, "pwd");
			mXMPPConnection.connect();
		    try{
		    	if(checkConnectionListener!=null){
		    		mXMPPConnection.removeConnectionListener(checkConnectionListener);
		    		checkConnectionListener=null;
		    	}
		    }catch(Exception e){

		    }
			mXMPPConnection.login(mUserName, mPassword);
			if(mXMPPConnection.isAuthenticated()){                                     //登录成功
				QQApplication.xmppConnection=mXMPPConnection;
				sendLoginBroadcast(true);
				//添加xmpp连接监听
				checkConnectionListener=new CheckConnectionListener(this);
				mXMPPConnection.addConnectionListener(checkConnectionListener);
				// 注册好友状态更新监听
				friendsPacketListener=new FriendsPacketListener(this);
				PacketFilter filter = new AndFilter(new PacketTypeFilter(Presence.class));
				mXMPPConnection.addPacketListener(friendsPacketListener, filter);
			}else{
				sendLoginBroadcast(false);
				stopSelf();                                                                                        //如果登录失败，自动销毁Service
			}
		} catch (Exception e) {
			e.printStackTrace();
			sendLoginBroadcast(false);
			stopSelf();
		}
	}*/
}
