package com.hy.mvp.listener;


import com.hy.mvp.serivce.MsfService;

import org.jivesoftware.smack.ConnectionListener;


/**
 * @author baiyuliang
 */
public class CheckConnectionListener implements ConnectionListener {
	
	private MsfService context;
	
	public CheckConnectionListener(MsfService context){
		this.context=context;
	}

	@Override
	public void connectionClosed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void connectionClosedOnError(Exception e) {  
		if (e.getMessage().equals("stream:error (conflict)")) {
			
		}
	}

	@Override
	public void reconnectingIn(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reconnectionFailed(Exception arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reconnectionSuccessful() {
		// TODO Auto-generated method stub
		
	}

}
