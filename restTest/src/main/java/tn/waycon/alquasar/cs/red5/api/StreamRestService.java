package tn.waycon.alquasar.cs.red5.api;

import java.util.List;

import org.red5.server.api.IClient;

public interface StreamRestService {

	List<String> getLiveStreams(String appName);

	Object recordLiveStream(String appName, String streamName);

	Object stopStreamRecord(String appName, String streamName);

	Object getLiveStreamInfo(String appName, String streamName);

	List getAllLiveStreamInfo(String appName);

	List<IClient> getClients(String appName);

}
