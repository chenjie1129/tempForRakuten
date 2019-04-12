> This Java API is a data encapsulation of the C++ sample code with JNI, and is therefore slightly different from that of the C++ API in structure. The Agora SDK (sample code shared by C++ and Java) implements the C++ recording APIs and callbacks, goes through data encapsulation in the JNI layer, and then works as the Java interface and class of the Native SDK through the JNI proxy.

- The {@link io.agora.recording.RecordingSDK RecordingSDK} class provides the main methods that can be invoked by your application.
- The {@link io.agora.recording.RecordingEventHandler RecordingEventHandler} class enables callbacks to your application.

@section RecordingSDK RecordingSDK Class

<table>
<tr>
<th>Method</th>
<th>Description</th>
</tr>
<tr>
<td>{@link io.agora.recording.RecordingSDK#createChannel createChannel}</td>
<td>Creates a channel.</td>
</tr>
<tr>
<td>{@link io.agora.recording.RecordingSDK#setVideoMixingLayout setVideoMixingLayout}</td>
<td>Sets the video mixing layout.</td>
</tr>
<tr>
<td>{@link io.agora.recording.RecordingSDK#leaveChannel leaveChannel}</td>
<td>Allows the app to leave the channel.</td>
</tr>
<tr>
<td>{@link io.agora.recording.RecordingSDK#getProperties getProperties}</td>
<td>Retrieves the recording properties.</td>
</tr>
<tr>
<td>{@link io.agora.recording.RecordingSDK#startService startService}</td>
<td>Starts a recording.</td>
</tr>
<tr>
<td>{@link io.agora.recording.RecordingSDK#stopService stopService}</td>
<td>Pauses the recording.</td>
</tr>
</table>


@section RecordingEventHandler RecordingEventHandler Class

<table>
<tr>
<th>Callback</th>
<th>Description</th>
</tr>
<tr>
<td>{@link io.agora.recording.RecordingEventHandler#nativeObjectRef nativeObjectRef}</td>
<td>Returns the JNI instance.</td>
</tr>
<tr>
<td>{@link io.agora.recording.RecordingEventHandler#onWarning onWarning}</td>
<td>Occurs when a warning occurs.</td>
</tr>
<tr>
<td>{@link io.agora.recording.RecordingEventHandler#onError onError}</td>
<td>Occurs when an error occurs.</td>
</tr>
<tr>
<td>{@link io.agora.recording.RecordingEventHandler#onConnectionLost onConnectionLost}</td>
<td>Occurs when the SDK loses connection to the server. </td>
</tr>
<tr>
<td>{@link io.agora.recording.RecordingEventHandler#onConnectionInterrupted onConnectionInterrupted}</td>
<td>Occurs when the connection between the SDK and the server is interrupted.</td>
</tr>
<tr>
<td>{@link io.agora.recording.RecordingEventHandler#onJoinChannelSuccess onJoinChannelSuccess}</td>
<td>Occurs when the recording app joins a channel.</td>
</tr>
<tr>
<td>{@link io.agora.recording.RecordingEventHandler#onLeaveChannel onLeaveChannel}</td>
<td>Occurs when the recording app leaves the channel.</td>
</tr>
<tr>
<td>{@link io.agora.recording.RecordingEventHandler#onUserJoined onUserJoined}</td>
<td>Occurs when a user joins the channel.</td>
</tr>
<tr>
<td>{@link io.agora.recording.RecordingEventHandler#onUserOffline onUserOffline}</td>
<td>Occurs when a user leaves the channel or goes offline.</td>
</tr>
<tr>
<td>{@link io.agora.recording.RecordingEventHandler#audioFrameReceived audioFrameReceived}</td>
<td>Occurs when the raw audio data is received.</td>
</tr>
<tr>
<td>{@link io.agora.recording.RecordingEventHandler#videoFrameReceived videoFrameReceived}</td>
<td>Occurs when the raw video data is received.</td>
</tr>
<tr>
<td>{@link io.agora.recording.RecordingEventHandler#onAudioVolumeIndication onAudioVolumeIndication}</td>
<td>Reports the list of users who are speaking and their volumes.</td>
</tr>
<tr>
<td>{@link io.agora.recording.RecordingEventHandler#onActiveSpeaker onActiveSpeaker}</td>
<td>Occurs when a speaker is detectesd in the channel.</td>
</tr>
<tr>
<td>{@link io.agora.recording.RecordingEventHandler#onFirstRemoteAudioFrame onFirstRemoteAudioFrame}</td>
<td>Occurs when the first remote audio frame is received.</td>
</tr>
<tr>
<td>{@link io.agora.recording.RecordingEventHandler#onFirstRemoteVideoDecoded onFirstRemoteVideoDecoded}</td>
<td>Occurs when the first remote video frame is decoded. </td>
</tr>
<tr>
<td>{@link io.agora.recording.RecordingEventHandler#onReceivingStreamStatusChanged onReceivingStreamStatusChanged}</td>
<td>Occurs when the status of receiving the audio or video stream changes. </td>
</tr>
<tr>
<td>{@link io.agora.recording.RecordingEventHandler#stopCallBack stopCallBack}</td>
<td>Occurs when the SDK cannot call the JNI function.</td>
</tr>
<tr>
<td>{@link io.agora.recording.RecordingEventHandler#recordingPathCallBack recordingPathCallBack}</td>
<td>Reports the directory of the recorded files.</td>
</tr>
</table>
