package io.agora.recording;

import io.agora.recording.common.Common.AudioFrame;
import io.agora.recording.common.Common.VideoFrame;
import io.agora.recording.common.Common.AudioVolumeInfo;

public interface RecordingEventHandler {

  /** This callback returns the JNI instance. You need to pass this JNI instance
	 * when calling each main method, except {@link RecordingSDK#createChannel() createChannel}.
   *
	 * @param nativeHandle The recording engine.
	 */
	void nativeObjectRef(long nativeHandle);

	/** This callback is triggered when a user leaves the channel.
	 *
	 * @param reason The reasons why the recording app leaves the channel. See {@link io.agora.recording.common.Common#LEAVE_PATH_CODE LEAVE_PATH_CODE}.
	 */
	void onLeaveChannel(int reason);

	/** This callback is triggered when an error occurrs during SDK runtime.
   *
   * The SDK cannot fix the issue or resume running, which requires intervention
	 * from the app and informs the user on the issue.
   *
	 * @param error {@link io.agora.recording.common.Common#ERROR_CODE_TYPE Error codes}.
	 * @param stat_code {@link io.agora.recording.common.Common#STAT_CODE_TYPE State codes}.
	 */
	void onError(int error, int stat_code);

	/** This callback is triggered when a warning occurrs during SDK runtime.
   *
   * In most cases, the app can ignore the warnings reported by the SDK because
	 * the SDK can usually fix the issue and resume running.
	 *
   * @param warn {@link io.agora.recording.common.Common#WARN_CODE_TYPE Warning codes}.
	 */
	void onWarning(int warn);

	/** This callback is triggered when the recording app successfully joins the specified
	 * channel with an assigned Channel ID and user ID.
	 *
   * @param channelId Channel ID assigned based on the channel name specified in {@link RecordingSDK#createChannel() createChannel}.
	 * @param uid	User ID of the user.
	 */
	void onJoinChannelSuccess(String channelId, long uid);

	/** This callback is triggered when a user leaves the channel or goes offline.
   *
   * The SDK reads the timeout data to determine if a user leaves the channel (or goes offline).
	 * If no data package is received from the user within 15 seconds, the SDK assumes the user is offline.
	 * A poor network connection may lead to false detections, so use signaling for reliable offline detection.
   *
	 * @param uid User ID of the user.
	 * @param reason The rerasons why the user leaves the channel or goes offline. See {@link io.agora.recording.common.Common#USER_OFFLINE_REASON_TYPE USER_OFFLINE_REASON_TYPE}.
	 */
	void onUserOffline(long uid, int reason);

	/** This callback is triggered when a user joins the channel and returns the UID of the new user.
   *
   *  If there are users in the channel before the recording app joins the channel, the SDK will also
	 *  reports on the UIDs of the existing users. This callback is triggered as many times as the number
	 *  of the users in the channel.
   *
	 * @param uid User ID of the user.
	 * @param recordingDir	Directory of the recorded files.
	 */
  void onUserJoined(long uid, String recordingDir);

	/** This callback returns the user ID of the active speaker.
   *
	 *  @param uid The user ID of the active speaker.
	 */
  void onActiveSpeaker(long uid);

	/** This callback is triggered when the raw audio data is received.
   * @param uid User ID of the user.
	 * @param frame Received raw audio data in PCM or AAC format. See {@link io.agora.recording.common.Common.AudioFrame AudioFrame}.
	 */
	void audioFrameReceived(long uid, AudioFrame frame);

	/** This callback is triggered when the raw video data is received.
	 *
	 * This callback is triggered for every received raw video frame and can be used
	 * to detect sexually explicit content, if necessary.
   *
	 * Agora recommends capturing the i frame only and neglecting the others.
	 *
	 * @param uid User ID of the remote user as specified in the createChannel() method.
	 * If no uid is previously assigned, the Agora server automatically assigns a uid.
	 * @param type The format of the received video data:
	 * <ul>
   *  <li>0: YUV</li>
   *  <li>1: H.264</li>
   *  <li>2: JPEG</li>
   * </ul>
	 * @param frame Received video data in YUV, H.264, or JPEG format. See {@link io.agora.recording.common.Common.VideoFrame VideoFrame}.
   * @param rotation Rotational angle: 0, 90, 180, or 270.
	 */
	void videoFrameReceived(long uid, int type, VideoFrame frame, int rotation);

	/** The SDK triggers this callback when it stop to receive other callbacks and cannot call the JNI function.
	 */
	void stopCallBack();

	/** This callback returns the directory of the recorded files.
	 *  @param path The directory of the recorded files.
	 */
	void recordingPathCallBack(String path);


	  /** Reports the list of users who are speaking and their volumes.
	   * This callback works only when {@link io.agora.recording.common.RecordingConfig#audioIndicationInterval audioIndicationInterval} > 0.
	   * @param speakers    An array containing the user ID and volume information for each speaker. For more information, see {@link io.agora.recording.common.Common#AudioVolumeInfo AudioVolumeInfo}。
	   * @param speakerNum  The total number of users who are speaking.
	   */
    void onAudioVolumeIndication(AudioVolumeInfo[] infos);

		/** Occurs when the first remote video frame is decoded.
	   * This callback applies only to the composite mode and is triggered after the first frame of the remote video is received and decoded. The app can configure the user view settings with this callback.
	   * @param uid     The user ID.
	   * @param width   The width of the video frame.
	   * @param height  The height of the video frame.
	   * @param elapsed Time elapsed (ms) from the local user calling {@link RecordingSDK#createChannel() createChannel} until this callback is triggered.
	   */
    void onFirstRemoteVideoDecoded(long uid, int width, int height, int elapsed);

		/** Occurs when the first remote audio frame is received.
	   * @param uid      The user ID.
	   * @param elapsed  Time elapsed (ms) from the local user calling {@link RecordingSDK#createChannel() createChannel} until this callback is triggered.
	   */
    void onFirstRemoteAudioFrame(long uid, int elapsed);

		/** Occurs when the status of receiving the audio or video stream changes.
	   * @param receivingAudio  Whether or not the recording application is receiving the audio stream.
	   * @param receivingVideo  Whether or not the recording application is receiving the video stream.
	   */
    void onReceivingStreamStatusChanged(boolean receivingAudio, boolean receivingVideo);

		/** Occurs when the SDK cannot reconnect to Agora's edge server 10 seconds after its connection to the server is interrupted.
	   * The SDK triggers this callback when it cannot connect to the server 10 seconds after calling {@link RecordingSDK#createChannel() createChannel} regardless of whether it is in the channel or not.
	   * This callback is different from {@link RecordingEventHandler#onConnectionInterrupted onConnectionInterrupted}:
		 * <ul>
		 *  <li> The SDK triggers the {@link RecordingEventHandler#onConnectionInterrupted onConnectionInterrupted} callback when the SDK loses connection with the server for more than 4 seconds after it joins the channel.</li>
		 *  <li> The SDK triggers the {@link RecordingEventHandler#onConnectionLost onConnectionLost} callback when the SDK loses connection with the server for more than 10 seconds, regardless of whether it joins the channel or not.</li>
	   * </ul>
	   * For both callbacks, the SDK tries to reconnect to the server until the app calls {@link RecordingSDK#leaveChannel() leaveChannel}.
	   */
    void onConnectionLost();

		/** Occurs when the connection between the SDK and the server is interrupted.
		* The SDK triggers this callback when it cannot connect to the server 10 seconds after calling {@link RecordingSDK#createChannel() createChannel} regardless of whether it is in the channel or not.
		* This callback is different from {@link RecordingEventHandler#onConnectionLost onConnectionLost}:
	  * <ul>
		*  <li> The SDK triggers the {@link RecordingEventHandler#onConnectionInterrupted onConnectionInterrupted} callback when the SDK loses connection with the server for more than 4 seconds after it joins the channel.</li>
		*  <li> The SDK triggers the {@link RecordingEventHandler#onConnectionLost onConnectionLost} callback when the SDK loses connection with the server for more than 10 seconds, regardless of whether it joins the channel or not.</li>
		* </ul>
		* For both callbacks, the SDK tries to reconnect to the server until the app calls {@link RecordingSDK#leaveChannel() leaveChannel}.
		*/
    void onConnectionInterrupted();
}
