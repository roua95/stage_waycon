package tn.waycon.alquasar.cs.red5;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.red5.server.adapter.ApplicationAdapter;
import org.red5.server.api.IClient;
import org.red5.server.api.scope.IScope;

/**
 * Red 5 rest api gateway
 * @author Lenovo
 *
 */
public class Red5RestApplication extends ApplicationAdapter {
	
	

    public Red5RestApplication() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
    public boolean appStart(IScope app) {
        super.appStart(app);
        log.info("springmvc appStart");
        return super.appStart(app);
    }
    
    public List<String> getLiveStreams() {

		Iterator<IClient> iter = scope.getClients().iterator();
	
		List<String> streams = new ArrayList<>();
		while(iter.hasNext()) {
			streams.add(iter.next().toString());
			
		}

		return streams;

	}

}
