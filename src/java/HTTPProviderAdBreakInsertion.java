/*
 * This code and all components (c) Copyright 2006 - 2016, Wowza Media Systems, LLC.  All rights reserved.
 * This code is licensed pursuant to the Wowza Public License version 1.0, available at www.wowza.com/legal.
 */
package com.wowza.wms.plugin.addemo;

import java.util.*;

import com.wowza.util.*;
import com.wowza.wms.amf.*;
import com.wowza.wms.application.*;
import com.wowza.wms.http.*;
import com.wowza.wms.logging.*;
import com.wowza.wms.stream.*;
import com.wowza.wms.vhost.*;

public class HTTPProviderAdBreakInsertion extends HTTProvider2Base
{
	private static final String CLASSNAME = "HTTPProviderAdBreakInsertion";
	private static final Class<HTTPProviderAdBreakInsertion> CLASS = HTTPProviderAdBreakInsertion.class;

	public void onBind(IVHost vhost, HostPort hostPort)
	{
		super.onBind(vhost, hostPort);
	}
	
	public void onHTTPRequest(IVHost vhost, IHTTPRequest req, IHTTPResponse resp)
	{
		if (!doHTTPAuthentication(vhost, req, resp))
			return;

		try
		{
			while(true)
			{
				// Pull parameters from HTTPPOST query string
				String queryStr = req.getQueryString();
				if (queryStr == null)
				{
					WMSLoggerFactory.getLogger(CLASS).warn(CLASSNAME+".onHTTPRequest: Query string missing");
					break;
				}
	
		        Map<String, String> queryMap = HTTPUtils.splitQueryStr(queryStr);
	
				String streamName = queryMap.get("streamName");
				String appName = queryMap.get("application");
				String appInstanceName = IApplicationInstance.DEFAULT_APPINSTANCE_NAME;
				String adUrl = queryMap.get("url");
				
				if (streamName == null)
				{
					WMSLoggerFactory.getLogger(CLASS).warn(CLASSNAME+".onHTTPRequest: streamName is null");
					break;
				}
				
				if (appName == null)
				{
					WMSLoggerFactory.getLogger(CLASS).warn(CLASSNAME+".onHTTPRequest: appName is null");
					break;
				}
				
				if (adUrl == null)
				{
					WMSLoggerFactory.getLogger(CLASS).warn(CLASSNAME+".onHTTPRequest: adUrl is null");
					break;
				}
				
				// If <application>/<appInstance> specified
				int cindex = appName.indexOf("/");
				if (cindex > 0)
				{
					appInstanceName = appName.substring(cindex+1);
					appName = appName.substring(0, cindex);
				}
	
				WMSLoggerFactory.getLogger(CLASS).info(CLASSNAME+".onHTTPRequest: ad:"+adUrl+" stream:"+appName+"/"+appInstanceName+"/"+streamName);
	
				// Find application, application instance and stream in running WSE
				IApplication app = vhost.getApplication(appName);
				if (app == null)
				{
					WMSLoggerFactory.getLogger(CLASS).warn(CLASSNAME+".onHTTPRequest: Application could not be loaded: "+appName);
					break;
				}
	
				IApplicationInstance appInstance = app.getAppInstance(appInstanceName);
				if (appInstance == null)
				{
					WMSLoggerFactory.getLogger(CLASS).warn(CLASSNAME+".onHTTPRequest: Application instance could not be loaded: "+appName+"/"+appInstanceName);
					break;
				}
	
				MediaStreamMap streams = appInstance.getStreams();
				if (streams == null)
				{
					WMSLoggerFactory.getLogger(CLASS).warn(CLASSNAME+".onHTTPRequest: No streams: "+appName+"/"+appInstanceName);
					break;
				}
					
				IMediaStream stream = streams.getStream(streamName);
	
				// Send the Ad break request
				sendAdBreakMessage(stream, adUrl);
				
				break;
			}
			
		}
		catch (Exception e)
		{
			WMSLoggerFactory.getLogger(CLASS).warn(CLASSNAME+".onHTTPRequest: ", e);
		}
	}
	
	public void sendAdBreakMessage(IMediaStream stream, String adID)
	{
		try
		{
			// Create AMF data event
			AMFDataObj amfData = new AMFDataObj();		
			amfData.put("adID", new AMFDataItem(adID));

			WMSLoggerFactory.getLogger(CLASS).info("sendAdBreakMessage ["+stream.getContextStr()+"]");
			
			// Send the data event
			stream.sendDirect("onAdBreak", amfData);
			((MediaStream)stream).processSendDirectMessages();				
		}
		catch(Exception e)
		{
			WMSLoggerFactory.getLogger(CLASS).error(CLASSNAME+".sendAdBreakMessage["+stream.getContextStr()+"]: ", e);
		}
	}
}
