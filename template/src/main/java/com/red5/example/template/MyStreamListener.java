package com.red5.example.template;

import org.red5.server.api.stream.IBroadcastStream;
import org.red5.server.api.stream.IStreamListener;
import org.red5.server.api.stream.IStreamPacket;
import org.red5.server.net.rtmp.message.Constants;

public class MyStreamListener implements IStreamListener {

	@Override
	public void packetReceived(IBroadcastStream stream, IStreamPacket packet) {
		// TODO Auto-generated method stub
		byte dataType = packet.getDataType();
    	if (dataType == Constants.TYPE_AUDIO_DATA) {
    	System.out.println("audio packet received");
    	} else if (dataType == Constants.TYPE_VIDEO_DATA) {
    	System.out.println("video packet received");
    	}
    }

	

}
