# How to enable player-side ad insertion using Wowza Streaming Engine
With a few small pieces of custom Java and HTML code, you can extend Wowza Streaming Engine[special-tm][/special-tm] media server software so that it can insert ad requests on demand and enable a to player insert and broadcast ads during the playback of live streams.

This player-side ad insertion workflow uses an HTTP provider to enable Wowza Streaming Engine server to receive a request  to insert an ad break into a live stream. Then it uses an event-listener module to convert the HTTP request into an ID3 tag that it sends with the transmuxed Apple HLS stream to supporting players. Finally, custom code on the player side detects the tag and makes a Video Ad Serving Template (VAST) call to retrieve and play the ad during playback.

This article explains how to extend the Wowza Streaming Engine server and JW Player 7 to support player-side ad insertion by using demo code and a demo ad in a .zip file.

## Prerequisites

Wowza Streaming Engine 4.0.0 or later is required.

The player-side ad insertion file adDemo.zip is also required. adDemo.zip contains the following files:

* **/java/HTTPProviderAdBreakInsertion.java** - An HTTP provider that allows Wowza Streaming Engine to accept an HTTP POST request to inject a data event into the ingested stream that's requesting the ad break.

* **/java/ModuleCupertinoLiveOnAdBreakToID3.java** - A Wowza Streaming Engine module that listens for the data event in the stream during transmuxing. It converts the event to an ID3 tag in the Apple HLS stream that Wowza Streaming Engine sends to the player.

* **/html/live_ad.html** - HTML code that contains the button to insert the ad break and plays the video. Includes the JW Player custom code that detects the ad break, makes the VAST call, and plays the ad.

* **/html/testVAST.xml** - The VAST XML file that points to the sample ad. For this demo, it's deployed to a local web server.

* **/html/testAd.mp4** - The sample ad.

[Download adDemo.zip](https://www.wowza.com/resources/addemo.zip) 

## Wowza Streaming Engine setup 
Start by preparing your Wowza Streaming Engine instance to receive and process an ad break request using the **live** application that comes pre-configured with the Wowza Streaming Engine Manager.

1. On the **Setup** tab of the **live** application, under **Playback Types**, make sure **Apple HLS** is selected.

2. Click **Sources (Live)**, select the encoder or camera you're using for your source, and make sure the **Stream Name** is **myStream**. For more information about configuring a live stream source, see [How to connect a live source to Wowza Streaming Engine] (https://www.wowza.com/forums/content.php?610-How-to-connect-a-publisher-to-Wowza-Streaming-Engine).

3. If you haven't done so already, download **adDemo.zip** and extract its contents. 

4. Compile **/java/HTTPProviderAdBreakInsertion.java** and **/java/ModuleCupertinoLiveOnAdBreakToID3.java** into a .jar file and then copy the .jar file to **[install-dir]/lib/**.


## HTTP provider configuration
The ad demo HTTP provider allows Wowza Streaming Engine to accept an HTTP POST request to inject a data event into the ingested stream that's requesting the ad break.

1. Open [install-dir]/conf/VHost.xml in a text editor.

2. Add the following to the list of host port 8086 providers:

```<HTTPProvider>
	<BaseClass>com.wowza.wms.plugin.addemo.HTTPProviderAdBreakInsertion</BaseClass>
	<RequestFilters>insertadmarker</RequestFilters>
	<AuthenticationMethod>none</AuthenticationMethod>
</HTTPProvider>
```

_**Important:** Be sure to add the ad demo provider immediately above the last provider in the 8086 list, **http.HTTPServerVersion**._

## Custom module configuration 
The custom ad demo module listens for the data event while Wowza Streaming Engine transmuxes the live stream. When the module hears the event, it converts the event to an ID3 tag in the Apple HLS stream that it sends to the player.

1. Open [install-dir]/conf/live/Application.xml in a text editor.

2. Add the following properties to the **Modules** section:

```<Module>
	<Name>ModuleAdID3Tags</Name>
	<Description>Convert ad requests to ID3 tags</Description>
	<Class>com.wowza.wms.plugin.addemo.ModuleCupertinoLiveOnAdBreakToID3</Class>
</Module>
```

Alternatively, you can configure the ad demo event listener in the **Modules** tab of the **live** application in Wowza Streaming Engine Manager. Access to the **Modules** tab requires an administrator user with advanced permissions. See [Configure modules](https://www.wowza.com/forums/content.php?625-How-to-get-started-as-a-Wowza-Streaming-Engine-Manager-administrator#configModules) for details.

**Name** | **Description** | **Fully Qualified Class Name**
ModuleAdID3Tags | Convert ad requests to ID3 tags | com.wowza.wms.plugin.addemo.ModuleCupertinoLiveOnAdBreakToID3

## Player configuration 

Copy the contents of **/html/** to a local web server, for example, to **/htdocs/ads/**.</li>


## Final configuration
1. Restart the Wowza Streaming Engine server.

2. In a web browser, load localhost/ads/live_ad.html.


## Usage
Now you can make ad break requests in the live stream.

1. In Wowza Streaming Engine Manger, start the stream source for the **live** application to begin your live streaming broadcast.

2. When you want to insert an ad into the live stream, use a command-line tool such as Terminal to make an HTTP POST call to the Wowza Streaming Engine instance. The base resource for the request is the URL-encoded VAST ad URL. For example,

'''http://localhost/adexample/_definst_/myStream/ad.ad?[URL-encoded-VAST-server-URL]'''

The ad appears in the player when the ad break occurs in the stream.


## More resources
Wowza Media Systems[special-tm][/special-tm] provides developers with a platform to create streaming applications and solutions. See [URL="https://www.wowza.com/resources/developers"]Wowza Developer Tools[/URL] to learn more about our APIs and SDK.
[Wowza Media Systems™, LLC](https://www.wowza.com/contact)