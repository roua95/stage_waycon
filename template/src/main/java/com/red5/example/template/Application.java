package com.red5.example.template;

import java.io.File;
import java.io.IOException;

/*import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.red5.server.adapter.MultiThreadedApplicationAdapter;
import org.red5.server.api.IConnection;
import org.red5.server.api.scope.IScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Application extends MultiThreadedApplicationAdapter {


	private static Logger log = LoggerFactory.getLogger(Application.class);




	@Override
	public boolean appStart(IScope app) {
		log.info("Application template roua started : {}", app);
		return super.appStart(app);
	}

    public int add (int a,int b) {
    	PrintWriter out;
		try {
			out = new PrintWriter("D:\\a.txt");
			out.println("give 2 numbers a and b");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	return a+b;
    }





	@Override
	public boolean appConnect(IConnection conn, Object[] params) {
		log.info("Client template connect : {}",  conn);
		return super.appConnect(conn, params);
	}







	@Override
	public void appDisconnect(IConnection conn) {
		log.info("Client template disconnect : {}",  conn);
		super.appDisconnect(conn);
	}







	@Override
	public void appStop(IScope arg0) {
		log.info("Application template stopped : {}", arg0);
		super.appStop(arg0);
	}



}
 */

/*
 * RED5 Open Source Flash Server - http://code.google.com/p/red5/
 * 
 * Copyright 2006-2012 by respective authors (see below). All rights reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

//package org.red5.demo.auth;


import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.Arrays;
import org.junit.Test;
import org.red5.logging.Red5LoggerFactory;
import org.red5.server.adapter.ApplicationLifecycle;
import org.red5.server.adapter.MultiThreadedApplicationAdapter;
import org.red5.server.api.IConnection;
import org.red5.server.exception.ClientRejectedException;
import org.red5.server.exception.ScopeNotFoundException;
import org.red5.server.net.rtmp.RTMPConnection;
import org.red5.server.net.rtmp.message.Constants;
import org.red5.server.scope.WebScope;
import org.red5.server.session.SessionManager;
import org.red5.server.stream.ClientBroadcastStream;
import org.red5.server.stream.IProviderService;
import org.red5.server.stream.PlaylistSubscriberStream;
import org.red5.server.util.ScopeUtils;
import org.red5.server.util.UrlQueryStringMap;
import org.slf4j.Logger;

/**
 * Provides Red5 specific authentication using an application listener.
 * 
 * This handler uses a basic challenge-response protocol:
 * <ul>
 * <li>Client requests a session</li>
 * <li>Server generates a unique, random ChallengeString (e.g. salt, guid) as well as a SessionID and sends both to client</li>
 * <li>Client gets UserID and Password from UI. Hashes the password once and call it PasswordHash. 
 * Then combines PasswordHash with the random string received from server in step 2, 
 * and hashes them together again, call this ResponseString</li>
 * <li>Client sends the server UserID, ResponseString and SessionID</li>
 * <li>Server looks up user’s stored PasswordHash based on UserID, and the original ChallengeString based on SessionID. 
 * Then computes the ResponseHash by hashing the PasswordHash and ChallengeString. 
 * If its equal to the ResponseString sent by user, then authentication succeeds.</li>
 * </ul>
 * 
 * @author Paul Gregoire
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream.Builder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.Adapter;

import  com.red5.example.template.MyStreamListener;

import org.red5.server.Context;
import org.red5.server.adapter.ApplicationAdapter;
import org.red5.server.api.IClient;
import org.red5.server.api.IConnection;
import org.red5.server.api.scope.IScope;
import org.red5.server.api.Red5;
import org.red5.server.api.service.IPendingServiceCall;
import org.red5.server.api.service.IPendingServiceCallback;
import org.red5.server.api.service.IServiceCapableConnection;
import org.red5.server.api.stream.IBroadcastStream;
import org.red5.server.api.stream.IClientBroadcastStream;
import org.red5.server.api.stream.IPlayItem;
import org.red5.server.api.stream.IPlaylistSubscriberStream;
import org.red5.server.api.stream.IStreamAwareScopeHandler;
import org.red5.server.api.stream.IStreamListener;
import org.red5.server.api.stream.ISubscriberStream;
import org.red5.server.api.stream.ResourceExistException;
import org.red5.server.api.stream.support.SimplePlayItem;
import org.red5.server.api.stream.support.StreamUtils;

public class Application extends ApplicationAdapter implements
IPendingServiceCallback, IStreamAwareScopeHandler {

	protected static Logger log = LoggerFactory.getLogger(Application.class);

	/** {@inheritDoc} */
	@Override
	public boolean appStart(IScope scope) {
		// init your handler here
		// System.out.println(getStreams());
		System.out.println("+++++++++++++++++++++++++++++++++++++++");
		String osName = System.getProperty("os.name");
		String osVersion = System.getProperty("os.version");
		System.out.println("OS Name: "+System.getProperty(osName)+" OS version: "+System.getProperty(osVersion)+" Architecture of THe OS: " + 
				System.getProperty("os.arch"));
		System.out.println("Free memory (bytes): " + 
				Runtime.getRuntime().freeMemory());
		System.out.println("la version jvm : " + 
				System.getProperty("java.version"));
		System.out.println("l'architec jvm : " + 
				System.getProperty("java.vm.name"));

		System.out.println("PROCESSOR_IDENTIFIER:\t"
				+ System.getenv("PROCESSOR_IDENTIFIER"));
		System.out.println("PROCESSOR_ARCHITECTURE:\t"
				+ System.getenv("PROCESSOR_ARCHITECTURE"));
		System.out.println("NUMBER_OF_PROCESSORS:\t"
				+ System.getenv("NUMBER_OF_PROCESSORS"));


		System.out.println("nombre des applications : "+getApplications().size());

		System.out.println("the applications are :"+getApplications().toString());

		System.out.println("the number of connected clients:"+getClients().size());


		Object liveStreamInfo = getLiveStreamInfo("live", "livestream1");

		IBroadcastStream stream = (IBroadcastStream) java.util.stream.Stream.builder();
		streamPublishStart(stream);






		/*	item.setStart(0);
	         		item.setLength(10000);
	         		item.setName("on2_flash8_w_audio");
	         			serverStream.addItem(item);
	         			item = new SimplePlayItem();
	         //			item.setStart(20000);
	         //			item.setLength(10000);
	         //			item.setName("on2_flash8_w_audio");
	         //			serverStream.addItem(item);
	         //			serverStream.start();
	         //			try {
	         //				serverStream.saveAs("aaa", false);
	         //				serverStream.saveAs("bbb", false);
//	 			} catch (Exception e) {}

		 */
		/*****second trial---a whole class****/





		return true;
	}

	/** {@inheritDoc} */
	@Override
	public boolean appConnect(IConnection conn, Object[] params) {
		IServiceCapableConnection service = (IServiceCapableConnection) conn;
		log.info("Client connected {} conn {}", new Object[]{conn.getClient().getId(), conn});
		service
		.invoke("setId", new Object[] { conn.getClient().getId() },
				this);
		return true;
	}

	public List<String> getApplications() {
		IScope findRoot = ScopeUtils.findRoot(scope);
		List<String> res = new ArrayList<>();
		Set<String> scopeNames = findRoot.getScopeNames();
		if (scopeNames != null && !scopeNames.isEmpty()) {
			res.addAll(scopeNames);
		}

		return res;
	}
	/** {@inheritDoc} */
	@Override
	public boolean appJoin(IClient client, IScope scope) {
		log.info("Client joined app {}", client.getId());
		// If you need the connection object you can access it via.
		IConnection conn = Red5.getConnectionLocal();
		return true;
	}

	/** {@inheritDoc} */
	public void streamPublishStart(IBroadcastStream stream) {
		// Notify all the clients that the stream had been started
		if (log.isDebugEnabled()) {
			log.debug("stream broadcast start: {}", stream.getPublishedName());
		}
		IConnection current = Red5.getConnectionLocal();
		for(Set<IConnection> connections : scope.getConnections()) {
			for (IConnection conn: connections) {
				if (conn.equals(current)) {
					// Don't notify current client
					continue;
				}

				if (conn instanceof IServiceCapableConnection) {
					((IServiceCapableConnection) conn).invoke("newStream",
							new Object[] { stream.getPublishedName() }, this);
					if (log.isDebugEnabled()) {
						log.debug("sending notification to {}", conn);
					}
				}
			}}
         //var mic:Microphone = Microphone.getMicrophone();
		//var camera:Camera = Camera.getCamera();
		
	}

	public void streamRecord(IBroadcastStream stream,String appName,String record_state) {
		

		try
		{   //get AppScope
			IScope scope=getAppScope(appName);
			MultiThreadedApplicationAdapter adapter = (MultiThreadedApplicationAdapter) scope.getHandler();
			//list of broadcaststream names in scope app
			Set<String> broadcastStreams = adapter.getBroadcastStreamNames(scope);

			Iterator<String> iter = broadcastStreams.iterator();

			while (iter.hasNext()) {
				String name = iter.next();
				if(name==stream.getName()) {
					IClientBroadcastStream stream1 = (IClientBroadcastStream) adapter.getBroadcastStream(scope, name);
					if (record_state=="start") streamRecordStart(stream1);
					else if(record_state=="stop") streamRecordStop(stream1);
				}}  	
		}


		catch (Exception e)
		{
			log.error("An unexpected error occurred." + e.getMessage());
			throw e;
		}

	}












	/** {@inheritDoc} */
	public void streamRecordStart(IBroadcastStream stream) {
		try
		{
			ClientBroadcastStream bStream = (ClientBroadcastStream) stream;
			if(!bStream.isRecording()) {
				bStream.saveAs(bStream.getPublishedName(), false);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void stopRecording(IBroadcastStream stream)
	{
		ClientBroadcastStream bStream = (ClientBroadcastStream) stream;
		if(bStream.isRecording()) {
			bStream.stopRecording();
		}

	}
	/** {@inheritDoc} */
	public void streamBroadcastClose(IBroadcastStream stream) {
		stream.removeStreamListener(myListener);
	}

	/** {@inheritDoc} */
	protected IStreamListener myListener = null;
	public void streamBroadcastStart(IBroadcastStream stream) {
		myListener = new MyStreamListener();
		//a""s a listener to t-e stream t-at just started
		stream.addStreamListener(myListener);
	}

	/** {@inheritDoc} */
	public void streamPlaylistItemPlay(IPlaylistSubscriberStream stream,
			IPlayItem item, boolean isLive) {


	}


	/** {@inheritDoc} */
	public void streamPlaylistItemStop(IPlaylistSubscriberStream stream,
			IPlayItem item) {
	}
	

	/** {@inheritDoc} */
	public void streamPlaylistVODItemPause(IPlaylistSubscriberStream stream,
			IPlayItem item, int position) {


	}

	/** {@inheritDoc} */
	public void streamPlaylistVODItemResume(IPlaylistSubscriberStream stream,
			IPlayItem item, int position) {


	}

	/** {@inheritDoc} */
	public void streamPlaylistVODItemSeek(IPlaylistSubscriberStream stream,
			IPlayItem item, int position) {


	}

	/** {@inheritDoc} */
	public void streamSubscriberClose(ISubscriberStream stream) {

	}

	/** {@inheritDoc} */
	public void streamSubscriberStart(ISubscriberStream stream) {

	}

	/**
	 * Get streams. called from client
	 * @return iterator of broadcast stream names
	 */
	/*public List<String> getStreams(String appName) {
		//IConnection conn = Red5.getConnectionLocal();
		//return new ArrayList<String>(getBroadcastStreamNames(conn.getScope()));
		//for(String app:getApplications()){
			IScope scope=getAppScope(appName);
			//MultiThreadedApplicationAdapter ad = null;
			//String br = null;
			ArrayList<String> Arr = new ArrayList<String>();
			for(String childScope:scope.getScopeNames()) {
			//IBroadcastStream b=ad.getBroadcastStream(childScope, br);
			//System.out.println(" Stream name: "+b.getName());
			//return new ArrayList<String>(getBroadcastStreamNames(scope));
				Arr.add(childScope);
		}
			return Arr;
	}*/
	public List<String> getLiveStreams(String appName) throws Exception {
		List<String> streams = null;

		try
		{
			IScope scope=getAppScope(appName);
			MultiThreadedApplicationAdapter adapter = (MultiThreadedApplicationAdapter) scope.getHandler();
			Set<String> broadcastStreams = adapter.getBroadcastStreamNames(scope);

			Iterator<String> iter = broadcastStreams.iterator();
			streams = new ArrayList<String>();

			while (iter.hasNext()) {
				String name = iter.next();
				IClientBroadcastStream stream = (IClientBroadcastStream) adapter.getBroadcastStream(scope, name);

				IConnection conn = stream.getConnection();
				if (conn != null && conn.isConnected())
					streams.add(name);
			}

			return streams;
		}
		catch (Exception e)
		{
			log.error("An unexpected error occurred." + e.getMessage());
			throw e;
		}
	}

	/**
	 * Handle callback from service call. 
	 */
	public void resultReceived(IPendingServiceCall call) {
		log.info("Received result {} for {}", new Object[]{call.getResult(), call.getServiceMethodName()});
	}

	
	public List<IClient> getClients(String appName) {
		// TODO Auto-generated method stub
		IConnection conn = Red5.getConnectionLocal();
		return (List<IClient>) conn.getClient();
		//return null;
	}

	private IScope getAppScope(String appName) throws ScopeNotFoundException {
		IScope scope=null;
		if (appName != null && !appName.equals("")) {
			IScope root = ScopeUtils.findRoot(getScope());
			scope = root.getScope(appName);
		} 

		return scope;
	}




	public Object getLiveStreamInfo(String appName, String streamName) {
		// TODO Auto-generated method stub
		if (getApplications().contains(appName)) {
			IScope scope=getAppScope(appName);
			MultiThreadedApplicationAdapter ad = (MultiThreadedApplicationAdapter) scope.getHandler();
			
			IBroadcastStream b=ad.getBroadcastStream(scope, streamName);
			System.out.println(" Stream name: "+b.getName()+"Stream PublishedName "+b.getPublishedName()+"Stream StartTime() "+b.getStartTime()+"Stream creation time : "+b.getCreationTime()+"Stream save file: "+b.getSaveFilename());
		}

		return null;
	}



}












