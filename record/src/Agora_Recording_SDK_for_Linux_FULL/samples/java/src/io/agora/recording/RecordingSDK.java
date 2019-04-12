package io.agora.recording;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import io.agora.recording.common.Common.AudioFrame;
import io.agora.recording.common.Common.VideoFrame;
import io.agora.recording.common.Common.VideoMixingLayout;
import io.agora.recording.common.RecordingConfig;
import io.agora.recording.common.RecordingEngineProperties;
import io.agora.recording.common.Common.AudioVolumeInfo;

public class RecordingSDK {

	private List<RecordingEventHandler> recordingEventHandlers = null;

	/** Load Java library. */

	static {
		System.loadLibrary("recording");
	}

  /** Main methods that can be invoked by your app.*/
	public RecordingSDK() {
		recordingEventHandlers = new ArrayList<RecordingEventHandler>();
	}

  /** To register observer to receive Recording event notification. */
	public void registerOberserver(RecordingEventHandler recordingEventHandler) {
		if (!recordingEventHandlers.contains(recordingEventHandler)) {
			recordingEventHandlers.add(recordingEventHandler);
		}
	}

  /** To remove previously registered observer. */
	public void unRegisterOberserver(RecordingEventHandler recordingEventHandler) {
		if (recordingEventHandlers.contains(recordingEventHandler)) {
			recordingEventHandlers.remove(recordingEventHandler);
		}
	}

	/** This method creates a channel and enables the app to join the channel.
	 *
	 * @param appId The App ID used in the communication to be recorded. For details,
	 * see <a href="https://docs.agora.io/en/Recording/token?platform=All%20Platforms">Getting an App ID</a>.
	 *
	 * @param channelKey This parameter is optional if the user uses a static key, or App ID.
	 * In this case, pass NULL as the parameter value. For details,
	 * see <a href="https://docs.agora.io/en/Recording/token?platform=All%20Platforms">Use Security Keys</a>.
	 *
	 * @param name Unique channel name for the AgoraRTC session in the string format. The string length must be less than 64 bytes. Supported character scopes are:
   * <ul>
	 *   <li>The 26 lowercase English letters from a to z</li>
   *   <li>The 26 uppercase English letters from A to Z</li>
   *   <li>The 10 numbers from 0 to 9</li>
   *   <li>The space</li>
   *   <li>"!", "#", "$", "%", "&", "(", ")", "+", "-", ":", ";", "<", "=", ".", ">", "?", "@", "[", "]", "^", "_", " {", "}", "|", "~", ","</li>
	 * </ul>
	 *
	 * @param uid User ID. A 32-bit unsigned integer ranging from 1 to (2<sup>32</sup>-1) that is unique in a channel.
	 * Two Settings:
	 * <ul>
	 *   <li>Set to 0, the system will automatically assign a uid.</li>
	 *   <li>Set a unique uid (cannot be repeated with any uid in the current channel)</li>
	 * </ul>
	 *
	 * @param config Detailed recording configuration. See {@link io.agora.recording.common.RecordingConfig RecordingConfig}.
	 *
	 * @param logLevel Generate the level of the log. After setting up, only logs with a level lower than
	 * logLevel will be generated.
	 *
	 * @return
	 * <ul>
	 *   <li>0: Success.</li>
	 *   <li>< 0: Failure.</li>
	 * </ul>
	 *
	 * @note
	 * <ul>
	 *   <li>In the Recording SDK, requestToken and renewToken are private interfaces.Make sure that
	 *   you set <a href="https://docs.agora.io/en/Recording/token?platform=All%20Platforms#Generate_Token">expireTimestamp</a>
	 *   as 0 when generating a token, which means that the privilege, once generated, never expires.</li>
   *   <li>A channel does not accept duplicate uids. Otherwise, there will be unpredictable behaviors.</li>
	 * </ul>
	 */
	public native boolean createChannel(String appId, String channelKey, String name, int uid, RecordingConfig config, int logLevel);

	/** This method allows the recording app to leave the channel and release the thread resources.
	 *
	 * @param nativeHandle The recording engine.
	 *
	 * @return
	 * <ul>
	 *   <li>0: Success.</li>
	 *   <li>< 0: Failure.</li>
	 * </ul>
	 */
	public native boolean leaveChannel(long nativeHandle);

	/** This method sets the video mixing layout.
	 *
	 * @param nativeHandle The recording engine.
	 *
	 * @param layout Layout setting. See {@link io.agora.recording.common.Common.VideoMixingLayout VideoMixingLayout}.

	 *
	 * @return
	 * <ul>
	 *   <li>0: Success.</li>
	 *   <li>< 0: Failure.</li>
	 * </ul>
	 */
	public native int setVideoMixingLayout(long nativeHandle, VideoMixingLayout layout);

	/** This method manually starts a recording.
	 * The method is only valid when you set triggerMode to 1 (manually) when joining the channel.
   * For more information, see {@link io.agora.recording.common.RecordingConfig#triggerMode triggerMode}.
	 *
	 * @param nativeHandle The recording engine.
	 *
	 * @return
	 * <ul>
	 *   <li>0: Success.</li>
	 *   <li>< 0: Failure.</li>
	 * </ul>
	 */
	public native int startService(long nativeHandle);

	/** This method manually pauses the recording.
	 * The method is only valid when you set triggerMode to 1 (manually) when joining the channel.
   * For more information, see {@link io.agora.recording.common.RecordingConfig#triggerMode triggerMode}.
	 *
	 * @param nativeHandle The recording engine.
	 *
	 * @return
	 * <ul>
	 *   <li>0: Success.</li>
	 *   <li>< 0: Failure.</li>
	 * </ul>
	 */
	public native int stopService(long nativeHandle);

	/** This method allows you to retrieve the recording properties without joining a channel.
	 *
	 * @param nativeHandle The recording engine.
	 *
	 * @return RecordingEngineProperties. See {@link io.agora.recording.common.RecordingEngineProperties RecordingEngineProperties}.
	 * @note
	 * <ul>
	 *   <li>The recording properties only include the path of the recording files.</li>
	 *   <li>This method is different from {@link RecordingEventHandler#onUserJoined onUserJoined}.
	 *       You must call onUserJoined after joining the channel.</li>
	 * </ul>
	 */
  public native RecordingEngineProperties getProperties(long nativeHandle);


  private native int setUserBackground(long nativeHandle, int uid, String image_path);


  private native void setLogLevel(long nativeHandle, int level);


  private native void enableLogModule(long nativeHandle, int module, int enable);


  private void nativeObjectRef(long nativeHandle) {
        for (RecordingEventHandler oberserver : recordingEventHandlers) {
            oberserver.nativeObjectRef(nativeHandle);
        }
    }


	private void onLeaveChannel(int reason) {
		for (RecordingEventHandler oberserver : recordingEventHandlers) {
			oberserver.onLeaveChannel(reason);
		}
	}


	private void onError(int error, int stat_code) {
		for (RecordingEventHandler oberserver : recordingEventHandlers) {
			oberserver.onError(error, stat_code);
		}
	}


	private void onWarning(int warn) {
		for (RecordingEventHandler oberserver : recordingEventHandlers) {
			oberserver.onWarning(warn);
		}
	}


	private void onUserOffline(long uid, int reason) {
		for (RecordingEventHandler oberserver : recordingEventHandlers) {
			oberserver.onUserOffline(uid, reason);
		}
	}


	private void onUserJoined(long uid, String recordingDir) {
		for (RecordingEventHandler oberserver : recordingEventHandlers) {
			oberserver.onUserJoined(uid, recordingDir);
		}
	}


	private void audioFrameReceived(long uid, AudioFrame frame) {
		for (RecordingEventHandler oberserver : recordingEventHandlers) {
			oberserver.audioFrameReceived(uid, frame);
		}
	}


    private void onActiveSpeaker(long uid) {
		for (RecordingEventHandler oberserver : recordingEventHandlers) {
            oberserver.onActiveSpeaker(uid);
        }
    }

    /*
     * Brief: Callback to indicate audio volume 
     * @param AudioVolumeInfo[]  audio info arrary
     */
    private void onAudioVolumeIndication(AudioVolumeInfo[] infos) {
		for (RecordingEventHandler oberserver : recordingEventHandlers) {
            oberserver.onAudioVolumeIndication(infos);
        }
    }

    /*
     * Brief: Callback to indicate receiving stream status changed 
     * @param receivingAudio  receiving audio stream status 
     * @param receivingVideo  receiving video stream status 
     */
    private void onReceivingStreamStatusChanged(boolean receivingAudio, boolean receivingVideo) {
		for (RecordingEventHandler oberserver : recordingEventHandlers) {
            oberserver.onReceivingStreamStatusChanged(receivingAudio, receivingVideo);
        }
    }

    /*
    * when the network can not worked well, the function will be called
    */
    private void onConnectionLost() {
		for (RecordingEventHandler oberserver : recordingEventHandlers) {
            oberserver.onConnectionLost();
        }
    }

    /*
    * when local user disconnected by accident, the function will be called(then SDK will try to reconnect itself)
    */
    private void onConnectionInterrupted() {
		for (RecordingEventHandler oberserver : recordingEventHandlers) {
            oberserver.onConnectionInterrupted();
        }
    }
    /*
    * Brief: when the first remote video frame decoded, the function will be called
    *
    * @param uid the UID of the remote user
    *
    * @param width the width of the video frame
    *
    * @param height the height of the video frame
    *
    * @param elapsed the time elapsed from channel joined in ms
    */
    private void onFirstRemoteVideoDecoded(long uid, int width, int height, int elapsed) {
		for (RecordingEventHandler oberserver : recordingEventHandlers) {
            oberserver.onFirstRemoteVideoDecoded(uid, width, height, elapsed);
        }
    }

    /*
    * Brief: when the first remote audio frame arrived, the function will be called
    *
    * @param uid the UID of the remote user
    *
    * @param elapsed the time elapsed from channel joined in ms
    */
    private void onFirstRemoteAudioFrame(long uid, int elapsed) {
		for (RecordingEventHandler oberserver : recordingEventHandlers) {
            oberserver.onFirstRemoteAudioFrame(uid, elapsed);
        }
    }

    /*
     * Brief: Callback when received a video frame
	 * 
	 * @param uid user ID
	 * 
	 * @param type type of video frame, please refer to the define of VideoFrame
	 * 
	 * @param frame reference of received video frame
	 * 
	 * @param rotation rotation of video
	 */
	private void videoFrameReceived(long uid, int type, VideoFrame frame, int rotation) {
		for (RecordingEventHandler oberserver : recordingEventHandlers) {
			oberserver.videoFrameReceived(uid, type, frame, rotation);
		}
	}


	private void stopCallBack() {
		for (RecordingEventHandler oberserver : recordingEventHandlers) {
			oberserver.stopCallBack();
		}
	}


	private void recordingPathCallBack(String path) {
		for (RecordingEventHandler oberserver : recordingEventHandlers) {
			oberserver.recordingPathCallBack(path);
		}
	}


	 private void onJoinChannelSuccess(String channelId, long uid) {
		for (RecordingEventHandler oberserver : recordingEventHandlers) {
			oberserver.onJoinChannelSuccess(channelId, uid);
		}
	}


}
