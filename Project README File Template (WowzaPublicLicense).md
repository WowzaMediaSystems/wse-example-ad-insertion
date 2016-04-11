*# About this README template*

> ***Important Notes for the Author:***

> *This template is designed to help you create **README.md** files with the proper structure, formatting, and syntax for your **Wowza Streaming Engine** and **Wowza Streaming Cloud** GitHub projects distributed under the Wowza Public License. It is **NOT for use** with Wowza GitHub projects distributed under the BSD license.*

> *After you've created your README content using this template, be sure to review the information in [Saving your README.md File](#finalizing_readme) for final instructions.*

*The text formatted in italics is internal guidance for the author for associated content parts of the README. After reviewing it, delete it as you go or all at once after you're done (it shouldn't appear in the final product).*

*For the example text that's shown, you should try to use it without changing it. This is to make sure that all of our README files use consistent structure and formatting. But there will be situations where you must modify this text. Make minimal modifications to preserve as much of the original text/formatting as possible--but know that you can modify as needed so that it works for your content.*

*Available resources:*

*- [Wowza Style Guide](https://wowza.box.com/s/2n8rk6v39n5teossuvnsj4xterv1sk69)*

*- [Writing on GitHub](https://help.github.com/categories/writing-on-github/)*

*- If you have questions or need help, email [idocs@wowza.com](mailto:idocs@wowza.com)*

# [module-name]
*Your H1 header should be the name that that we instruct the user to enter as the **Name** *value in the "Configuration" section (not the module JAR file name). The [module-name] is typically different from the [module-jar-file-name]. For example, a module named **ModuleStreamResolver** *might have a JAR file named **wse-plugin-resolver.jar**

The **[module-common-name]** module for \[branded-software-name] [beginning with present-tense verb/verb phrase, briefly describe what the module does and the benefits that it provides. Try to do this within 3 or 4 sentences and limit to 150 words]. For example:*

The **ModuleAddAudioTrack** module for [Wowza Streaming Engine™ media server software](https://www.wowza.com/products/streaming-engine) adds an audio track to a live stream that doesn't already have audio. Microsoft Smooth Streaming and some service providers such as Akamai and YouTube require streams to have both video and audio. You can use this module to insert audio from a file into the video before pushing it Akamai or providing playback to Smooth Streaming clients. Conversion of a video-only stream from an IP camera into a video/audio stream is an example of how this module can be used.

*The [module-common-name] is typically different from the [module-jar-file-name]. For example, the **wse-plugin-resolver** *JAR file might commonly be referred to as the **StreamResolver** *module.*

*Linked [branded-software-name] examples:*

*- [Wowza Streaming Engine™ media server software](https://www.wowza.com/products/streaming-engine)*

*- [Wowza Streaming Cloud™ service](https://www.wowza.com/products/streaming-cloud)*

## Prerequisites
*If this project is for use with Wowza Streaming Engine software, list the minimum required software version (to a minimum of 3 digits) to use the module. For example:*

Wowza Streaming Engine 4.0.0 or later is required.

*If other software is required, or there are other requirements (such as pre-configuration requirements), list each software or other requirement on separate paragraphs. Wowza software requirement(s) should always be the first paragraph, followed by any additional requirement(s). If 3rd-party software is required, be sure to include a link to where it can be acquired (where to get it), and specifics such as the minimum required software version.*

Wowza Streaming Engine 4.2.0 or later is required.

This module will only work with streams that are compliant with Apple App Store 3G rules. For more information on how to meet these requirements, see [How to create Apple App Store compliant streams](https://www.wowza.com/forums/content.php?208-How-to-create-Apple-App-Store-compliant-streams).


## Installation
*In this section, walk the user through the process of installing any required software (this is different from just getting software, which is described in the **Prerequisites** *section), plus any other installation or configuration that must be completed before the user can add the module definition, configure properties, and then click 'Go'. Examples of this latter information might be sample video files encoded at certain bitrates with specific codecs and stored in the Streaming Engine installation for testing purposes--or 3rd-party account configuration that might need to be set up in advance.*

*If only one installation step is required, don't use a numbered list (for example):*

Copy the **wse-plugin-resolver.jar** file to your Wowza Streaming Engine **[install-dir]/lib/** folder.

*Use a numbered list if multiple steps are required to complete installation (for example):*

1. Copy the **wse-plugin-resolver.jar** file to your Wowza Streaming Engine **[install-dir]/lib/** folder.
2. Unzip the Amazon Web Services (AWS) SDK for Java, and then copy the **lib/aws-java-sdk-xx.xx.xx.jar** to the **[install-dir]/lib/** folder in your Wowza Streaming Engine installation.
3. Encode an on-demand video file at 720 Kbps with the HEVC codec and store it in the Wowza Streaming Engine **[install-dir]/content/** folder.
4. Sign in to AWS EC2 Management Console with your AWS account.

## Configuration
*The **Configuration** section typically refers to enabling the module by adding the module definition. Use the following syntax/formatting:*

To enable this module, add the following module definition to your application configuration. See [Configure modules](https://www.wowza.com/forums/content.php?625-How-to-get-started-as-a-Wowza-Streaming-Engine-Manager-administrator#configModules) for details.

**Name** | **Description** | **Fully Qualified Class Name**
-----|-------------|---------------------------
[module-name] | [Short description beginning with present-tense-verb (Adds, Resolves, etc.).] | [fully-qualified-class-name]

*Use the above text exactly if possible but adjust it if needed (for example, to state whether you must add the module definition to "live" or "on-demand" applications, to multiple servers, etc).*

*If any additional information is required to fully enable the module, list it in a **Note** *below the table (using the markdown formatting for quotes):*

> **Note:** Here's something else you need to do to enable this module.

*If the additional information consists of separate discrete pieces, use a **Notes** *heading followed by the discrete information separated into a bulleted list:*

> **Notes:**
> - Here's something else you need to do to enable this module.
> - And then do this other thing to enable the module.

*Alternatively, you can use a **Note** *heading with a numbered list, especially if something has to be done in a precise order to fully enable the module:*

> **Note:** To finish enabling this module, do the following:
1. Do this first.
2. Do this next.
3. Do this last.

## Properties
*After the module is enabled, if property configuration is required, include this section. If property configuration is complex, for example you must configure a large number of properties--some at the server level and others at the application level, or configure properties for different application types and/or on different servers, create multiple property configuration sections with appropriate heading text.*

After enabling the module, you can adjust the default settings by adding the following properties to your application. See [Configure properties](https://www.wowza.com/forums/content.php?625-How-to-get-started-as-a-Wowza-Streaming-Engine-Manager-administrator#configProperties) for details.

*Use the exact text above, if possible (even if only describing one property), but modify as needed, such as to state that you must add the properties for specific application types.*

**Path** | **Name** | **Type** | **Value** | **Notes**
-----|------|------|-------|------
[xml-path] | [propertyName] | String/Integer/Boolean | [propertyValue] | [Brief description of what the property is or does. (default: **[default-value]**)

*Below are examples of what to include in the table (copied from various articles). Note that all "values" use bold formatting. To see more examples, see our [Module Collection articles](https://www.wowza.com/forums/content.php?585-Module-Collection).*

**Path** | **Name** | **Type** | **Value** | **Notes**
-----|------|------|-------|------
Root/Application | addAudioTrackAudioSourceFilename | String | myAudioFile.mp4 | Name of file that contains the audio track. The same file is used for all streams. (default: **mp4:sample.mp4**)
Root/Server | wowzalyticsThreadPoolSize | Integer | 5 | Number of threads used to send data. (default: **5**)
Root/Application/MediaReader | randomAccessReaderClass | String | com.wowza.wms.plugin.collection.mediacache.MediaCacheLocalFirstRandomAccessReader | [left-blank-in-this-case]
Root/Application | wowzaOptionalPropertyName | Boolean | true | (Optional) Preface any "optional" notes in this manner. (default: **true**)

*Below the table, list any special circumstances regarding property configuration in a **Note** *or **Notes** *. Typically these caveats are more expansive than what you might include in the property **Notes** *column in the table (for example, different combinations of property values you might use to achieve specific results).*

> **Notes:**
> - The **Root/Application/MediaReaderpath** setting is not the same as for a regular module property.
> - If the application is a VOD Edge application, the **randomAccessReaderClass** property will have been set already and you should update the property value. There must be only one **randomAccessReaderClass** property.

## Final Configuration
*If after the module definition is created and all properties are configured, use this section to describe any final configuration that's needed before the module can be used. This section assumes a dependency on module definition/properties being in place so you can skip this section if that's not the case.*

## Usage
*In this section, describe how to use the module. In a separate paragraph below this content, describe what the user "should" see if the module works properly (aka, what success looks like). For example:*

When a client/player requests a stream from an edge server, the edge will first look for existing streams that it has already requested for a match. If none are found, it will then query known origin servers to return the requested stream. The edge server will connect to the origin server that responds first with a valid stream, and then serve that stream to the client/player.

## Troubleshooting
*In this section, describe what failure looks like. Be sure to only describe failures that will be commonly encountered. Describe each failure in a separate paragraph and provide a workaround for each one.*

## API Reference
*In this section, the link to the server-side API is required. If adding links to multiple API references, change the heading to **API References** and make the link to the server-side API first in a bulleted list.*

[Wowza Streaming Engine Server-Side API Reference](https://www.wowza.com/resources/WowzaStreamingEngine_ServerSideAPI.pdf)

## Contact
*Include this section exactly as shown.*

[Wowza Media Systems™, LLC](https://www.wowza.com/contact)

Wowza Media Systems provides developers with a platform to create streaming applications and solutions. See [Wowza Developer Tools](https://www.wowza.com/resources/developers) to learn more about our APIs and SDK.

## License
*This section references the LICENSE.txt file in your GitHub project repository, which should use the path shown below. When you create the GitHub project, use the module JAR file name without the file name extension as your project name ([jar-file-name]). Then update [jar-file-name] in the path text below and store the LICENSE.txt file in the specified project repository location to enable the link.*

This code is distributed under the [Wowza Public License](https://github.com/WowzaMediaSystems/[jar-file-name]/blob/master/LICENSE.txt).

## *Saving your README.md File*
*After you've finished creating your README content by using this template, be sure to do the following:*

*1. Make sure that all of the instructional text for the author is removed from the content.*

*2. If desired, have another subject-matter expert review your finished content for technical accuracy.*

*3. If desired, request a copyedit for the finished content by sending an email to [articles@wowza.com](mailto:articles@wowza.com). In your request, be sure to note important details such as when the copyedit must be completed.*

***Note:*** *Your copyeditor CAN'T ensure that the content is technically accurate.*

*4. When your content is final, save a copy of it using the following file name and extension: **README.md** *. Be sure to use all uppercase letters in the file name (and all lowercase letters in the file name extension).*

*5. Upload your completed **README.md** *file to your GitHub project.*
