package tn.waycon.alquasar.cs.red5.api.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.red5.compatibility.flex.messaging.io.ArrayCollection;
import org.red5.server.ClientRegistry;
import org.red5.server.adapter.MultiThreadedApplicationAdapter;
import org.red5.server.api.IClient;
import org.red5.server.api.IConnection;
import org.red5.server.api.Red5;
import org.red5.server.api.scope.IScope;
import org.red5.server.api.service.IServiceCapableConnection;
import org.red5.server.api.stream.IBroadcastStream;
import org.red5.server.api.stream.IClientBroadcastStream;
import org.red5.server.api.stream.IStreamListener;
import org.red5.server.exception.ScopeNotFoundException;
import org.red5.server.scope.WebScope;
import org.red5.server.stream.ClientBroadcastStream;
import org.red5.server.stream.IProviderService;
import org.red5.server.stream.ProviderService;
import org.red5.server.util.ScopeUtils;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import tn.waycon.alquasar.cs.red5.Red5RestApplication;
import tn.waycon.alquasar.cs.red5.api.Red5RestService;

public class Red5RestServiceImpl implements Red5RestService {

	private WebScope scope;
	private Red5RestApplication handler;

	public void setHandler(Red5RestApplication handler) {
		this.handler = handler;
	}

	public void setScope(WebScope scope) {
		this.scope = scope;
	}
	
	public Red5RestServiceImpl() {
		System.out.println("Red5RestServiceImpl.Red5RestServiceImpl()");
	}

	@Override
	public List<String> getApplications() {
		IScope findRoot = ScopeUtils.findRoot(scope);
		List<String> res = new ArrayList<>();
		Set<String> scopeNames = findRoot.getScopeNames();
		if (scopeNames != null && !scopeNames.isEmpty()) {
			res.addAll(scopeNames);
		}

		return res;
	}

	
		public ArrayCollection<String> getScopeStreamList(String
				scopeName) {
				ArrayCollection<String> streams = new
				ArrayCollection<String>();
				IScope target = null;
				if (scopeName == null) {
				target = Red5.getConnectionLocal().getScope();
				scopeName = target.getName();
				} else {
				target = ScopeUtils.resolveScope(scope, scopeName);
				List<String> streamNames = (List<String>) getBroadcastStreamNames(target);
				for (String name : streamNames) {
				streams.add(name);
				}
				return streams;
				}
				return streams;
		}
		public Set<String> getBroadcastStreamNames(IScope scope) {
		    IProviderService service = (IProviderService) ScopeUtils.getScopeService(scope, IProviderService.class, ProviderService.class);
		    return service.getBroadcastStreamNames(scope);
		}
		public List<String> getStreams() {
			IConnection conn = Red5.getConnectionLocal();
			return new ArrayList<String>(getBroadcastStreamNames(conn.getScope()));
		}

		

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
		

	@Override
	public Object recordLiveStream(String appName, String streamName) {
		// TODO Auto-generated method stub
		
		return null;
	}

	@Override
	public Object stopStreamRecord(String appName, String streamName) {
		// TODO Auto-generated method stub
		return null;
	}



	public String getSystemInfo() {
		// TODO Auto-generated method stub
		String osName = System.getProperty("os.name");
		String osVersion = System.getProperty("os.version");
		System.out.println("OS Name: "+System.getProperty(osName)+" OS version: "+System.getProperty(osVersion)+" Architecture of THe OS: " + 
				System.getProperty("os.arch"));
		System.out.println("Free memory (bytes): " + 
				Runtime.getRuntime().freeMemory());
		 String Free_memory=("Free memory (bytes): " + 
	                Runtime.getRuntime().freeMemory());
	        String jvm=("la version jvm : " + 
	        		System.getProperty("ava.vm.specification.version")+("la version jvm : " + 
	    	        		System.getProperty("ava.vm.specification.arch")));
	       
		String processor_info="PROCESSOR_IDENTIFIER:\t"
				+ System.getenv("PROCESSOR_IDENTIFIER")+"PROCESSOR_ARCHITECTURE:\t"+ System.getenv("PROCESSOR_ARCHITECTURE")+"NUMBER_OF_PROCESSORS:\t"+ System.getenv("NUMBER_OF_PROCESSORS");
		


		String nbre_app="nombre des applications : "+getApplications().size();


		//System.out.println("the number of connected clients:"+getClients().size());


		//Object liveStreamInfo = getLiveStreamInfo("live", "livestream1");

	



		return Free_memory+jvm+processor_info+nbre_app;
	}

	public List<String> getLiveStreams(String appName) {
		List<String> streams = null;

		
		
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
	
	private IScope getAppScope(String appName) throws ScopeNotFoundException {
		IScope scope=null;
		if (appName != null && !appName.equals("")) {
			IScope root = ScopeUtils.findRoot(getScope());
			scope = root.getScope(appName);
		} 

		return scope;
	}


	private IScope getScope() {
		return this.scope;
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

	public String getLiveStreamInfo(String appName, String streamName) {
		// TODO Auto-generated method stub
		
			IScope scope=getAppScope(appName);
			MultiThreadedApplicationAdapter ad = (MultiThreadedApplicationAdapter) scope.getHandler();
			
			IBroadcastStream b=ad.getBroadcastStream(scope, streamName);
			String stream_info=(" Stream name: "+b.getName()+"Stream PublishedName "+b.getPublishedName()+"Stream creation time : "+b.getCreationTime()+"Stream save file: "+b.getSaveFilename());
		

		return stream_info;
	}
	
//conn ? not working
	@Override
	public List<String> getClients(String appName) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		IScope scope=getAppScope(appName);
		MultiThreadedApplicationAdapter adapter = (MultiThreadedApplicationAdapter) scope.getHandler();
		Collection<Set<IConnection>> connections = adapter.getConnections();
		List<String> res=new ArrayList<>();
		for (Set<IConnection> set : connections) {
			for (IConnection c : set) {
				if(c.isConnected()) {
					res.add(gson.toJson(c));
				}
			}
			
		}
		return res;
	}

@Override
public List<String> getAllLiveStreamInfo(String appName) {
	List <String>streamsInfo=new ArrayList<String>();
	// TODO Auto-generated method stub
	IScope scope=getAppScope(appName);
	MultiThreadedApplicationAdapter ad = (MultiThreadedApplicationAdapter) scope.getHandler();
	Set<String> StreamList = ad.getBroadcastStreamNames(scope);
		Set<String> scopeNames = scope.getScopeNames();
	for (String stream : StreamList) {
		streamsInfo.add(getLiveStreamInfo(appName,stream));
	}
	return streamsInfo;
}
/*
public List<IConnection> getallClients() {
	List<IConnection> clients = null;
IConnection conn = Red5.getConnectionLocal();
Red5 r5 = new Red5();
IScope scope = r5.getScope();
conn = r5.getConnection();
r5 = new Red5(conn);
IClient client = r5.getClient();
clients.add((IConnection) client.getAttributeNames());
	return clients;}*/
	
	
}
