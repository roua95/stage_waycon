package tn.waycon.alquasar.cs.red5.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.red5.logging.Red5LoggerFactory;
import org.red5.server.api.IClient;
import org.red5.server.api.IConnection;
import org.red5.server.api.Red5;
import org.red5.server.util.ScopeUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tn.waycon.alquasar.cs.red5.api.Red5RestService;
import tn.waycon.alquasar.cs.red5.api.impl.Red5RestServiceImpl;

@RestController
public class Red5ResController {

	private static Logger log = Red5LoggerFactory.getLogger(Red5ResController.class, "oflaDemo");

	private static final String VIEW_INDEX = "index";

	private static int counter = 0;
	@Autowired
	private Red5RestService red5RestService;

	@RequestMapping(value = "/applications", method = RequestMethod.GET)
	public List<String> getApplications() {

		return red5RestService.getApplications();

	}
	
	@RequestMapping(value = "/liveStream/{appName}", method = RequestMethod.GET)
	public List<String> getLiveStream(@PathVariable String appName) {

		return red5RestService.getLiveStreams(appName);

	}
	@RequestMapping(value = "/systemInfo", method = RequestMethod.GET)
	public String getSystemInfo() {

		return red5RestService.getSystemInfo();

	}
	//getLiveStreamInfo
	@RequestMapping(value = "/liveStreamInfo/{appName}/{streamName}", method = RequestMethod.GET)
	
	public String getLiveStreamInfo(@PathVariable String appName,@PathVariable String streamName) {

		return red5RestService.getLiveStreamInfo(appName, streamName);
	}
	//to verify!!!
@RequestMapping(value = "/AllliveStreamInfo/{appName}", method = RequestMethod.GET)
	
	public List <String>getAllLiveStreamInfo(@PathVariable String appName) {

		return red5RestService.getAllLiveStreamInfo(appName);
	}
	
	

	@RequestMapping(value = "/clients/{appName}", method = RequestMethod.GET)
	public List <String> getClients(@PathVariable String appName) {

		return red5RestService.getClients(appName);
	}
	
	@RequestMapping(value = "/recordStream/{appName}/{streamName}", method = RequestMethod.GET)
	public Object recordLiveStream(@PathVariable String appName,@PathVariable String streamName) {

		return red5RestService.recordLiveStream(appName,streamName);
	}
	@RequestMapping(value = "/stopStreamRecord/{appName}/{streamName}", method = RequestMethod.GET)
	public Object stopStreamRecord(@PathVariable String appName,@PathVariable String streamName) {

		return red5RestService.stopStreamRecord(appName,streamName);
	}
	
}