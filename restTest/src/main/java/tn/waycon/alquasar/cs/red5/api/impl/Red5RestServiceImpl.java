package tn.waycon.alquasar.cs.red5.api.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.red5.server.api.IClient;
import org.red5.server.api.scope.IScope;
import org.red5.server.scope.WebScope;
import org.red5.server.util.ScopeUtils;
import org.springframework.stereotype.Service;

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

	@Override
	public List<String> getLiveStreams(String appName) {
		// TODO Auto-generated method stub
		return null;
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

	@Override
	public Object getLiveStreamInfo(String appName, String streamName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getAllLiveStreamInfo(String appName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IClient> getClients(String appName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getSystemInfo() {
		// TODO Auto-generated method stub
		return null;
	}

}
