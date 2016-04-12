# Player-side ad insertion
With a few small pieces of custom Java and HTML code, you can extend [Wowza Streaming Engine™ media server software](https://www.wowza.com/products/streaming-engine) to insert ad requests on demand and enable a player to insert and broadcast ads during playback of live streams.

The player-side ad insertion workflow uses an HTTP provider to enable Wowza Streaming Engine to receive a request to insert an ad break into a live stream. Then it uses an event-listener module to convert the HTTP request to an ID3 tag that it sends with the transmuxed Apple HLS stream to supporting players. Finally, custom code on the player side detects the tag and makes a Video Ad Serving Template (VAST) call to retrieve and play the ad during playback.

Example code and a sample ad are provided to demonstrate how to extend the Wowza Streaming Engine server and JW Player 7 to support player-side ad insertion.
(**Dave** Is JW Player 7 required to run workflow described in this document? If so, what edition?)
## Prerequisites

Wowza Streaming Engine 4.0.0 or later is required.

Player-side ad insertion files (**adDemo.zip**) are also required. The compressed (zipped) folder contains the following files: *(**Dave** consider adding the Readme.html file to this list, either first or last)*

* **/java/HTTPProviderAdBreakInsertion.java** - An HTTP provider that allows Wowza Streaming Engine to accept an HTTP POST request to inject a data event into the ingested stream that's requesting the ad break.

* **/java/ModuleCupertinoLiveOnAdBreakToID3.java** - A Wowza Streaming Engine module that listens for the data event in the stream during transmuxing. It converts the event to an ID3 tag in the Apple HLS stream that Wowza Streaming Engine sends to the player.

* **/html/live_ad.html** - HTML code that contains the button to insert the ad break and plays the video. Includes the JW Player custom code that detects the ad break, makes the VAST call, and plays the ad.

* **/html/testVAST.xml** - The VAST XML file that points to the sample ad. For this demo, it's deployed to a local web server.

* **/html/testAd.mp4** - The sample ad.

[Download adDemo.zip](https://www.wowza.com/downloads/forums/adDemo.zip) (**Dave** Temporarily uploaded sample 'adDemo.zip' file to /downloads/forums to verify link. Should probably be moved to /downloads/forums/adDemo but I don't have permissions to create the 'adDemo' folder in that directory.***

## Wowza Streaming Engine setup
Start by preparing your Wowza Streaming Engine instance to receive and process an ad break request using the **live** application that comes pre-configured with the Wowza Streaming Engine Manager.

1. On the **Setup** tab of the **live** application, under **Playback Types**, make sure **Apple HLS** is selected.

2. Click **Sources (Live)**, select the encoder or camera you're using for your source, and make sure the **Stream Name** is **myStream**. For more information about configuring a live stream source, see [How to connect a live source to Wowza Streaming Engine](https://www.wowza.com/forums/content.php?610-How-to-connect-a-publisher-to-Wowza-Streaming-Engine).

3. If you haven't done so already, download **adDemo.zip** and extract its contents.

4. Compile **/java/HTTPProviderAdBreakInsertion.java** and **/java/ModuleCupertinoLiveOnAdBreakToID3.java** into a .jar file and then copy the .jar file to the Wowza Streaming Engine **[install-dir]/lib** folder.

## HTTP Provider configuration
The ad demo HTTP Provider allows Wowza Streaming Engine to accept an HTTP POST request to inject a data event into the ingested stream that's requesting the ad break.

1. Open **[install-dir]/conf/VHost.xml** in a text editor.

2. Add the following to the list of host port 8086 providers:
```
<HTTPProvider>
	<BaseClass>com.wowza.wms.plugin.addemo.HTTPProviderAdBreakInsertion</BaseClass>
	<RequestFilters>insertadmarker</RequestFilters>
	<AuthenticationMethod>none</AuthenticationMethod>
</HTTPProvider>
```
> **Important:** Be sure to add the ad demo provider immediately above the last provider in the 8086 list (**http.HTTPServerVersion**).

## Custom module configuration
The custom ad demo module listens for the data event while Wowza Streaming Engine transmuxes the live stream. When the module detects the event, it converts the event to an ID3 tag in the Apple HLS stream that it sends to the player.

1. Open **[install-dir]/conf/live/Application.xml** in a text editor.

2. Add the following properties to the **Modules** section:
```
<Module>
	<Name>ModuleAdID3Tags</Name>
	<Description>Convert ad requests to ID3 tags</Description>
	<Class>com.wowza.wms.plugin.addemo.ModuleCupertinoLiveOnAdBreakToID3</Class>
</Module>
```

Alternatively, you can configure the ad demo event listener in the **Modules** tab of the **live** application in Wowza Streaming Engine Manager. Access to the **Modules** tab requires an administrator user with advanced permissions. See [Configure modules](https://www.wowza.com/forums/content.php?625-How-to-get-started-as-a-Wowza-Streaming-Engine-Manager-administrator#configModules) for details.

**Name** | **Description** | **Fully Qualified Class Name**
-----|-------------|---------------------------
ModuleAdID3Tags | Converts ad requests to ID3 tags. | com.wowza.wms.plugin.addemo.ModuleCupertinoLiveOnAdBreakToID3

## Player configuration

Copy the contents of **/html/** (**Dave** from the adDemo.zip folder) to a local web server, for example, to **/htdocs/ads/**.

## Final configuration
1. Restart the Wowza Streaming Engine server.

2. In a web browser, load **localhost/ads/live_ad.html**.

## Usage
Now you can make ad break requests in the live stream.

1. In Wowza Streaming Engine Manger, start the stream source for the **live** application to begin your live streaming broadcast.

2. When you want to insert an ad into the live stream, use a command-line tool such as Terminal to make an HTTP POST call to the Wowza Streaming Engine instance. The base resource for the request is the URL-encoded VAST ad URL. For example,
```
http://localhost/adexample/_definst_/myStream/ad.ad?[URL-encoded-VAST-server-URL]
```

The ad appears in the player when the ad break occurs in the stream.

## More resources
[How to extend Wowza Streaming Engine using the Wowza IDE](https://www.wowza.com/forums/content.php?759-How-to-extend-Wowza-Streaming-Engine-using-the-Wowza-IDE)

Wowza Media Systems™ provides developers with a platform to create streaming applications and solutions. See [Wowza Developer Tools](https://www.wowza.com/resources/developers) to learn more about our APIs and SDK.

## Contact
[Wowza Media Systems, LLC](https://www.wowza.com/contact)

## License
This code is distributed under the [Wowza Public License](https://github.com/WowzaMediaSystems/wse-example-ad-insertion/blob/master/LICENSE.txt).
