package tn.waycon.alquasar.cs.red5.api;

import java.util.List;

import org.red5.server.api.IClient;
import org.red5.server.api.IConnection;

public interface StreamRestService {

	List<String> getLiveStreams(String appName);

	Object recordLiveStream(String appName, String streamName);

	Object stopStreamRecord(String appName, String streamName);

	String getLiveStreamInfo(String appName, String streamName);
//I added the type of list in here
	List <String>getAllLiveStreamInfo(String appName); 

	List<String> getClients(String appName);
	
	String  getSystemInfo();

}
