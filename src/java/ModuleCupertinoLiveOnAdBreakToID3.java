package com.wowza.wms.plugin.addemo;

import com.wowza.wms.amf.*;
import com.wowza.wms.application.*;
import com.wowza.wms.httpstreamer.cupertinostreaming.livestreampacketizer.*;
import com.wowza.wms.media.mp3.model.idtags.*;
import com.wowza.wms.module.*;
import com.wowza.wms.stream.livepacketizer.*;

/**
 * This code and all components (c) Copyright 2006 - 2016, Wowza Media Systems, LLC.  All rights reserved.
 * This code is licensed pursuant to the Wowza Public License version 1.0, available at www.wowza.com/legal.
 */
public class ModuleCupertinoLiveOnAdBreakToID3 extends ModuleBase
{
	class LiveStreamPacketizerDataHandler implements IHTTPStreamerCupertinoLivePacketizerDataHandler
	{
		IApplicationInstance appInstance = null;
		String streamName = "unknown";
		
		public LiveStreamPacketizerDataHandler(IApplicationInstance appInstance, String streamName)
		{
			this.appInstance = appInstance;
			this.streamName = streamName;
		}

		// Called for each data packet when creating the HLS chunks
		public void onFillChunkDataPacket(CupertinoPacketHolder holder, AMFPacket packet, ID3Frames id3Frames)
		{			
			while(true)
			{
				byte[] buffer = packet.getData();
				if (buffer == null)
					break;
				
				if (packet.getSize() <= 2)
					break;
				
				int offset = 0;
				if (buffer[0] == 0)
					offset++;

				// Detect the onAdBreak AMF data event
				AMFDataList amfList = new AMFDataList(buffer, offset, buffer.length-offset);
				
				if (amfList.size() <= 1)
					break;
				
				if (amfList.get(0).getType() != AMFData.DATA_TYPE_STRING && amfList.get(1).getType() != AMFData.DATA_TYPE_OBJECT)
					break;
				
				String metaDataStr = amfList.getString(0);
								
				if (!metaDataStr.equalsIgnoreCase("onAdBreak"))
					break;

				// We have AMF onAdBreak, create ID3 tag in the HLS chunk
				AMFDataObj dataObj = amfList.getObject(1);
								
				AMFDataItem adID = (AMFDataItem)dataObj.get("adID");
				if (adID == null)
					break;

				ID3V2FrameTextInformationUserDefined comment = new ID3V2FrameTextInformationUserDefined();

				comment.setDescription("adID");
				comment.setValue(adID.toString());
				id3Frames.putFrame(comment);

				getLogger().info("ModuleCupertinoLiveOnAdBreakToID3.onFillChunkDataPacket["+this.hashCode()+":"+packet.hashCode()+":"+appInstance.getContextStr()+"] Send string: "+adID);
				break;
			}
		}
	}

	// Packetizer listener: wires up data handler
	class LiveStreamPacketizerListener implements ILiveStreamPacketizerActionNotify
	{
		IApplicationInstance appInstance = null;
		
		public LiveStreamPacketizerListener(IApplicationInstance appInstance)
		{
			this.appInstance = appInstance;
		}

		public void onLiveStreamPacketizerCreate(ILiveStreamPacketizer liveStreamPacketizer, String streamName)
		{
			getLogger().info("ModuleCupertinoLiveOnAdBreakToID3#MyLiveListener.onLiveStreamPacketizerCreate["+streamName+"]");

			// Register data handler for HLS packetizer
			if (liveStreamPacketizer instanceof LiveStreamPacketizerCupertino)
			{
				((LiveStreamPacketizerCupertino)liveStreamPacketizer).setDataHandler(new LiveStreamPacketizerDataHandler(appInstance, streamName));
			}
		}

		public void onLiveStreamPacketizerDestroy(ILiveStreamPacketizer liveStreamPacketizer)
		{
		}

		public void onLiveStreamPacketizerInit(ILiveStreamPacketizer liveStreamPacketizer, String streamName)
		{
		}
	}

	public void onAppStart(IApplicationInstance appInstance)
	{		
		appInstance.addLiveStreamPacketizerListener(new LiveStreamPacketizerListener(appInstance));

		// Register packetizer listener
		getLogger().info("ModuleCupertinoLiveOnAdBreakToID3.onAppStart["+appInstance.getContextStr()+"]");
	}
}
