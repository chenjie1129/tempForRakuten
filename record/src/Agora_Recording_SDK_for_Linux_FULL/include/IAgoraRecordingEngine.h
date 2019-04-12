#ifndef _IAGORA_RECORDINGENGINE_H_
#define _IAGORA_RECORDINGENGINE_H_
#include "IAgoraLinuxSdkCommon.h"

namespace agora {
namespace recording {
/**
 The IRecordingEngineEventHandler class enables callbacks to your application.
 */
class IRecordingEngineEventHandler {

public:
    virtual ~IRecordingEngineEventHandler() {}
    
    /** Occurs when an error occurs during SDK runtime.
     
     The SDK cannot fix the issue or resume running. It requires intervention from the application and informs the user on the issue.
     
     @param error \ref agora::linuxsdk::ERROR_CODE_TYPE "Error codes".
     @param stat_code \ref agora::linuxsdk::STAT_CODE_TYPE "State codes".
     
     */
    virtual void onError(int error, agora::linuxsdk::STAT_CODE_TYPE stat_code) = 0;

    /** Occurs when a warning occurs during SDK runtime.
     
     In most cases, the application can ignore the warnings reported by the SDK because the SDK can usually fix the issue and resume running.
     
     @param warn \ref agora::linuxsdk::WARN_CODE_TYPE "Warning codes".
     
     */
    virtual void onWarning(int warn) = 0;
   
    /** Occurs when the recording application joins the channel.
     
     @param channelId Channel ID assigned based on the channel name specified in \ref IRecordingEngine::joinChannel "joinChannel".
     @param uid The user ID.

     */
    virtual void onJoinChannelSuccess(const char * channelId, uid_t uid) = 0;
   
    /** Occurs when the recording application leaves the channel.
     
     @param code The reasons why the recording application leaves the channel. See the \ref agora::linuxsdk::LEAVE_PATH_CODE "LEAVE_PATH_CODE".

     */
    virtual void onLeaveChannel(agora::linuxsdk::LEAVE_PATH_CODE code) = 0;

    /** Occurs when a user joins the channel and returns the UID of the new user.
     
     If there are users in the channel before the recording app joins the channel, the SDK will also reports on the UIDs of the existing users. This callback is triggered as many times as the number of the users in the channel.
     
     @param uid The user ID.
     @param infos \ref agora::linuxsdk::UserJoinInfos "User information".
     
     */
    virtual void onUserJoined(uid_t uid, agora::linuxsdk::UserJoinInfos &infos) = 0;
   
    /** Occurs when a user leaves the channel or goes offline.
     
     When no data package of a user is received for a certain period of time (15 seconds), the SDK assumes that the user has goes offline. Weak network connections may lead to misinformation, so Agora recommends using the signaling system for offline event detection.
     
     @param uid The user ID.
     @param reason The \ref agora::linuxsdk::USER_OFFLINE_REASON_TYPE "reasons" why the user leaves the channel or goes offline.
     
     */
    virtual void onUserOffline(uid_t uid, agora::linuxsdk::USER_OFFLINE_REASON_TYPE reason) = 0;

    /** Occurs when the raw audio data is received.
     
     @param uid The user ID.
     @param frame Received raw audio data in PCM or AAC format. For more information, see \ref agora::linuxsdk::AudioFrame "AudioFrame".

     */
    virtual void audioFrameReceived(unsigned int uid, const agora::linuxsdk::AudioFrame *frame) const = 0;

    /** Occurs when the raw video data is received.
     
     This callback is triggered for every received raw video frame and can be used to detect sexually explicit content, if necessary. Agora recommends capturing the I frame only and neglecting the others.
     
     @param uid The user ID.
     @param frame Received raw audio data in YUV, H.264 or JPG format. For more information, see \ref agora::linuxsdk::VideoFrame "VideoFrame".
     
     */
    virtual void videoFrameReceived(unsigned int uid, const agora::linuxsdk::VideoFrame *frame) const = 0;

    /** This callback returns the UID of the active speaker in the channel.
     
     @param uid The user ID.
     */
    virtual void onActiveSpeaker(uid_t uid)  = 0;

    /** Reports the list of users who are speaking and their volumes.
     
     This callback works only when `audioIndicationInterval` in RecordingConfig is greater than 0.
    
     @param speakers    An array containing the user ID and volume information for each speaker. For more information, see \ref agora::linuxsdk::AudioVolumeInfo "AudioVolumeInfo".
     @param speakerNum  The total number of users who are speaking.
     */
    virtual void onAudioVolumeIndication(const agora::linuxsdk::AudioVolumeInfo* speakers, unsigned int speakerNum)  = 0;

    /** Occurs when the first remote video frame is decoded.
     
     This callback applies only to the composite mode and is triggered after the first frame of the remote video is received and decoded. The app can configure the user view settings with this callback.
     
     @param uid     The user ID.
     @param width   The width of the video frame.
     @param height  The height of the video frame.
     @param elapsed Time elapsed (ms) from the local user calling \ref IRecordingEngine::joinChannel "joinChannel" until this callback is triggered.
     
     */
    virtual void onFirstRemoteVideoDecoded(uid_t uid, int width, int height, int elapsed) = 0;

    /** Occurs when the first remote audio frame is received.
     
     @param uid      The user ID.
     @param elapsed  Time elapsed (ms) from the local user calling \ref IRecordingEngine::joinChannel "joinChannel" until this callback is triggered.
     */
    virtual void onFirstRemoteAudioFrame(uid_t uid, int elapsed) = 0;

    /** Occurs when the status of receiving the audio or video stream changes.
     
     @param receivingAudio  Whether or not the recording application is receiving the audio stream.
     @param receivingVideo  Whether or not the recording application is receiving the video stream.
     */
    virtual void onReceivingStreamStatusChanged(bool receivingAudio, bool receivingVideo) = 0;

    /** Occurs when the SDK cannot reconnect to Agora's edge server 10 seconds after its connection to the server is interrupted.
     
     The SDK triggers this callback when it cannot connect to the server 10 seconds after calling \ref IRecordingEngine::joinChannel "joinChannel", regardless of whether it is in the channel or not.

     This callback is different from \ref IRecordingEngineEventHandler::onConnectionInterrupted "onConnectionInterrupted":
     
     - The SDK triggers the \ref IRecordingEngineEventHandler::onConnectionInterrupted "onConnectionInterrupted" callback when the SDK loses connection with the server for more than 4 seconds after it joins the channel.
     - The SDK triggers the \ref IRecordingEngineEventHandler::onConnectionLost "onConnectionLost" callback when the SDK loses connection with the server for more than 10 seconds, regardless of whether it joins the channel or not.
     
     For both callbacks, the SDK tries to reconnect to the server until the app calls \ref IRecordingEngine::leaveChannel "leaveChannel".
    */
    virtual void onConnectionLost() = 0;

    /** Occurs when the connection between the SDK and the server is interrupted.
     
     The SDK triggers this callback when it loses connection to the server for more than 4 seconds after the connection is established. After triggering this callback, the SDK tries to reconnect to the server. You can use this callback to implement pop-up reminders. 

     This callback is different from \ref IRecordingEngineEventHandler::onConnectionInterrupted "onConnectionInterrupted":
     
     - The SDK triggers the \ref IRecordingEngineEventHandler::onConnectionInterrupted "onConnectionInterrupted" callback when the SDK loses connection with the server for more than 4 seconds after it joins the channel.
     - The SDK triggers the \ref IRecordingEngineEventHandler::onConnectionLost "onConnectionLost" callback when the SDK loses connection with the server for more than 10 seconds, regardless of whether it joins the channel or not.
     
     For both callbacks, the SDK tries to reconnect to the server until the app calls \ref IRecordingEngine::leaveChannel "leaveChannel".
    */
    virtual void onConnectionInterrupted() = 0;
};

/** The recording configuration. */
typedef struct RecordingConfig {
    /** Sets whether or not to record audio only.
     - true: Enables audio recording and disables video recording.
     - false: (Default) Enables both audio and video recording.
     
     Used together with #isVideoOnly:
     - If #isAudioOnly is true and #isVideoOnly is false, only records audio;
     - If #isAudioOnly is false and #isVideoOnly is true, only records video;
     - If #isAudioOnly is false and #isVideoOnly is false, records both audio and video;
     - #isAudioOnly and #isVideoOnly can not be set as true at the same time.
     
     */
    bool isAudioOnly;
    /** Sets whether or not to record video only.
     - true: Enables video recording and disables audio recording.
     - false: (Default) Enables both audio and video recording.
     
     Used together with #isAudioOnly:
     - If #isAudioOnly is true and #isVideoOnly is false, only records audio;
     - If #isAudioOnly is false and #isVideoOnly is true, only records video;
     - If #isAudioOnly is false and #isVideoOnly is false, records both audio and video;
     - #isAudioOnly and #isVideoOnly cannot be set as true at the same time.
     
     */
    bool isVideoOnly;
    /** Sets whether or not to enable the audio- or video-mixing mode.
     - true: Enables the composite mode, which means the audio of all uids is mixed in an audio file and the video of all uids is mixed in a video file. The sample rate, bitrate, and audio channel number of the recorded file are the same as the highest level of those of the original audio streams.
     - false: (Default) Enables the individual mode, which means one audio or video file for an uid. The bitrate and audio channel number of the recording file are the same as those of the original audio stream.
     
     */
    bool isMixingEnabled;
    /**  If you set #isMixingEnabled as true and enable the composite mode, #mixedVideoAudio allows you to mix the audio and video in a file in real time. For more information, see \ref agora::linuxsdk::MIXED_AV_CODEC_TYPE "MIXED_AV_CODEC_TYPE". */
    agora::linuxsdk::MIXED_AV_CODEC_TYPE mixedVideoAudio;
    /** If you set #isMixingEnabled as true and enable the composite mode, #mixResolution allows you to set the video profile, including the width, height, frame rate, and bitrate.
     
     * **Resolution, Frame Rate and Bitrate Table**
     
     | Resolution             | Frame Rate (fps) | Base Bitrate (Kbps, for Communication) | Live Bitrate (Kbps, for Live Broadcast)|
     |------------------------|------------------|----------------------------------------|----------------------------------------|
     | 160 &times; 120        | 15               | 65                                     | 130                                    |
     | 120 &times; 120        | 15               | 50                                     | 100                                    |
     | 320 &times; 180        | 15               | 140                                    | 280                                    |
     | 180 &times; 180        | 15               | 100                                    | 200                                    |
     | 240 &times; 180        | 15               | 120                                    | 240                                    |
     | 320 &times; 240        | 15               | 200                                    | 400                                    |
     | 240 &times; 240        | 15               | 140                                    | 280                                    |
     | 424 &times; 240        | 15               | 220                                    | 440                                    |
     | 640 &times; 360        | 15               | 400                                    | 800                                    |
     | 360 &times; 360        | 15               | 260                                    | 520                                    |
     | 640 &times; 360        | 30               | 600                                    | 1200                                   |
     | 360 &times; 360        | 30               | 400                                    | 800                                    |
     | 480 &times; 360        | 15               | 320                                    | 640                                    |
     | 480 &times; 360        | 30               | 490                                    | 980                                    |
     | 640 &times; 480        | 15               | 500                                    | 1000                                   |
     | 480 &times; 480        | 15               | 400                                    | 800                                    |
     | 640 &times; 480        | 30               | 750                                    | 1500                                   |
     | 480 &times; 480        | 30               | 600                                    | 1200                                   |
     | 848 &times; 480        | 15               | 610                                    | 1220                                   |
     | 848 &times; 480        | 30               | 930                                    | 1860                                   |
     | 640 &times; 480        | 10               | 400                                    | 800                                    |
     | 1280 &times; 720       | 15               | 1130                                   | 2260                                   |
     | 1280 &times; 720       | 30               | 1710                                   | 3420                                   |
     | 960 &times; 720        | 15               | 910                                    | 1820                                   |
     | 960 &times; 720        | 30               | 1380                                   | 2760                                   |
     | 1920 &times; 1080      | 15               | 2080                                   | 4160                                   |
     | 1920 &times; 1080      | 30               | 3150                                   | 6300                                   |
     | 1920 &times; 1080      | 60               | 4780                                   | 6500                                   |
     | 2560 &times; 1440      | 30               | 4850                                   | 6500                                   |
     | 2560 &times; 1440      | 60               | 6500                                   | 6500                                   |
     | 3840 &times; 2160      | 30               | 6500                                   | 6500                                   |
     | 3840 &times; 2160      | 60               | 6500                                   | 6500                                   |
     
     */
    char * mixResolution;
    /** When the whole channel is encrypted, the recording SDK uses #decryptionMode to enable the built-in decryption function.
     
     The following decryption methods are supported:
     - “aes-128-xts”: AES-128, XTS mode.
     - “aes-128-ecb”: AES-128, ECB mode.
     - “aes-256-xts”: AES-256, XTS mode.
     */
    char * decryptionMode;
    /** The decryption password when the #decryptionMode is enabled. The default value is NULL. */
    char * secret;
    /** The directory of AgoraCoreService. The default value is NULL. */
    char * appliteDir;
    /** The root directory of the recorded files. The default value is NULL. The sub-path will be generated automatically.
     */
    char * recordFileRootDir;
    /** The path of the configuration file. The default value is NULL. In the configuration file, you can set the absolute path of the recorded file, but the sub-path will not be generated automatically. The content in the configuration file must be in JSON format, such as {“Recording_Dir” : “<recording path>”}. “Recording_Dir” can not be changed. 
     */
    char * cfgFilePath;
    /** Video decoding format. For more information, see \ref agora::linuxsdk::VIDEO_FORMAT_TYPE "VIDEO_FORMAT_TYPE".
     
     @note
     - When \ref agora::linuxsdk::VIDEO_FORMAT_TYPE "VIDEO_FORMAT_TYPE" = 1, 2, 3 or 4, \ref agora::recording::RecordingConfig#isMixingEnabled "isMixingEnabled" cannot be set as true.
     - When \ref agora::linuxsdk::VIDEO_FORMAT_TYPE "VIDEO_FORMAT_TYPE" = 1, 2, 3 or 5, raw video data in YUV format for the Web SDK is supported while H.264 format is not supported.
     */
    agora::linuxsdk::VIDEO_FORMAT_TYPE decodeVideo;
    /** Audio decoding format. For more information, see \ref agora::linuxsdk::AUDIO_FORMAT_TYPE "AUDIO_FORMAT_TYPE".
     
     @note When \ref agora::linuxsdk::AUDIO_FORMAT_TYPE "AUDIO_FORMAT_TYPE" = 1, 2 or 3, \ref agora::recording::RecordingConfig#isMixingEnabled "isMixingEnabled" cannot be set as true.
     
     */
    agora::linuxsdk::AUDIO_FORMAT_TYPE decodeAudio;
    /** The lowest UDP port.
    
     Ensure that the value of highUdpPort - lowUdpPort is ≥ 4. The default value is 0.
     */
    int lowUdpPort;
    /** The highest UDP port.
    
     Ensure that the value of highUdpPort - lowUdpPort is ≥ 4. The default value is 0.
     */
    int highUdpPort;
    /** The Agora Recording SDK automatically stops recording when there is no user in the channel after a time period of idleLimitSec.
     
     The value must be ≥ 3 seconds. The default value is 300 seconds.
     */
    int idleLimitSec;
    /** The time interval of the screen capture.
     
     The time interval must be longer than 1 second and the default value is 5 seconds. #captureInterval is only valid when the \ref agora::linuxsdk::VIDEO_FORMAT_TYPE "VIDEO_FORMAT_TYPE" = 3, 4 or 5.
     */
    int captureInterval;
    /** Whether or not to detect speakers. It is disabled by default.
     
     - ≤ 0: Disables the function of detecting speakers.
     - > 0: The time interval (ms) of detecting speakers. Agora recommends setting the time interval to be longer than 200ms. When a speaker is found, the SDK returns the UID of the speaker in the \ref agora::recording::IRecordingEngineEventHandler::onActiveSpeaker "onActiveSpeaker" callback.
     */
    int audioIndicationInterval;
    /** Sets the channel mode. For more information, see \ref agora::linuxsdk::CHANNEL_PROFILE_TYPE "CHANNEL_PROFILE_TYPE". */
    agora::linuxsdk::CHANNEL_PROFILE_TYPE channelProfile;
    /** Takes effect only when the Agora Native SDK enables the dual-stream mode (high stream by default). For more information, see \ref agora::linuxsdk::REMOTE_VIDEO_STREAM_TYPE "REMOTE_VIDEO_STREAM_TYPE".
     */
    agora::linuxsdk::REMOTE_VIDEO_STREAM_TYPE streamType;
    /** Sets whether to record automatically or manually. For more information, see \ref agora::linuxsdk::TRIGGER_MODE_TYPE "TRIGGER_MODE_TYPE". */
    agora::linuxsdk::TRIGGER_MODE_TYPE triggerMode;
    /** Sets the programming language. For more information, see \ref agora::linuxsdk::LANGUAGE_TYPE "LANGUAGE_TYPE". */
    agora::linuxsdk::LANGUAGE_TYPE lang;
    /** Sets the proxy server, which allows recording with the Intranet server. For more information, please contact sales@agora.io. */
    char * proxyServer;
    /** Audio profile of the recording file. Takes effect only when <code>isMixingEnabled</code> is set as true. For more information, see \ref agora::linuxsdk::AUDIO_PROFILE_TYPE "AUDIO_PROFILE_TYPE". */
    agora::linuxsdk::AUDIO_PROFILE_TYPE audioProfile;
    /** The default background image of the canvas. */
    char * defaultVideoBg;
    /** The background image is displayed when a user is online but not sending out any video stream.
     
     @note If the user uses Agora Web SDK, the user sends the audio and video stream all the time. In this case, the background image is not displayed.
     
     */
    char * defaultUserBg;


    RecordingConfig(): channelProfile(agora::linuxsdk::CHANNEL_PROFILE_COMMUNICATION),
        isAudioOnly(false),
        isVideoOnly(false),
        isMixingEnabled(false),
        mixResolution(NULL),
        decryptionMode(NULL),
        secret(NULL),
        idleLimitSec(300),
        appliteDir(NULL),
        recordFileRootDir(NULL),
        cfgFilePath(NULL),
        lowUdpPort(0),
        highUdpPort(0),
        captureInterval(5),
        decodeAudio(agora::linuxsdk::AUDIO_FORMAT_DEFAULT_TYPE),
        decodeVideo(agora::linuxsdk::VIDEO_FORMAT_DEFAULT_TYPE),
        mixedVideoAudio(agora::linuxsdk::MIXED_AV_DEFAULT),
        streamType(agora::linuxsdk::REMOTE_VIDEO_STREAM_HIGH),
        triggerMode(agora::linuxsdk::AUTOMATICALLY_MODE),
        lang(agora::linuxsdk::CPP_LANG),
        proxyServer(NULL),
        audioProfile(agora::linuxsdk::AUDIO_PROFILE_DEFAULT),
        defaultVideoBg(NULL),
        defaultUserBg(NULL)
    {}

    virtual ~RecordingConfig() {}
} RecordingConfig;

/** The recording properties. Gets the directory of the recorded files and recording log. */
typedef struct RecordingEngineProperties {
    /** @param storageDir Diectory of the recorded files. */
    char* storageDir;
    RecordingEngineProperties(): storageDir(NULL)
                          {}
}RecordingEngineProperties;

/** The IRecordingEngine class provides the main methods that can be invoked by your application. */
class IRecordingEngine {
public:

    /** This method creates the Agora recording engine object.
     
     @param appId The App ID used in the communications to be recorded. For more information, see [Get an App ID](https://docs.agora.io/en/Recording/token?platform=All%20Platforms#app-id).
     @param eventHandler The Agora Recording SDK notifies the application of the triggered events by callbacks in the \ref agora::recording::IRecordingEngineEventHandler "IRecordingEngineEventHandler".
     @return
     - 0: Success.
     - < 0: Failure.
     
     */
    static IRecordingEngine* createAgoraRecordingEngine(const char * appId, IRecordingEngineEventHandler *eventHandler);

    virtual ~IRecordingEngine() {}

    /** This method allows the recording application to join a channel and start recording.
     
     @param channelKey This parameter is optional if the user uses a static key, or App ID. In this case, pass NULL as the parameter value. For more information, see [Use Security Keys](https://docs.agora.io/en/Voice/token?platform=All%20Platforms#app-id-native).
     @param channelId  Name of the channel to be recorded. The length must be within 64 bytes. The following is the supported scope:
     - The 26 lowercase English letters from a to z
     - The 26 uppercase English letters from A to Z
     - The 10 numbers from 0 to 9
     - The space
     - "!", "#", "$", "%", "&", "(", ")", "+", "-", ":", ";", "<", "=", ".", ">", "?", "@", "[", "]", "^", "_", " {", "}", "|", "~", ","
     
     @param uid        UID. A 32-bit unsigned integer ranging from 1 to (2<sup>32</sup>-1) that is unique in a channel.
     @param config     Detailed recording configuration. See the structure \ref agora::recording::RecordingConfig "RecordingConfig".
     @return
     - 0: Success.
     - < 0: Failure.
     @note
     - In the Recording SDK, requestToken and renewToken are private methods. Make sure that you set [expireTimestamp](https://docs.agora.io/en/Recording/token?platform=All%20Platforms&_ga=2.205808721.316116830.1544409452-764614247.1539586349#Generate_Token) as 0 when generating a token, which means that the privilege, once generated, never expires.
     - A channel does not accept duplicate uids. Otherwise, there will be unpredictable behaviors.
     */
    virtual int joinChannel(const char * channelKey, const char *channelId, uid_t uid, const RecordingConfig &config) = 0;

    /** This method sets the video mixing layout.
     
     @param layout Layout setting. See \ref agora::linuxsdk::VideoMixingLayout "VideoMixingLayout".
     @return
     - 0: Success.
     - < 0: Failure.
     */
    virtual int setVideoMixingLayout(const agora::linuxsdk::VideoMixingLayout &layout) = 0;
   
    /** This method stops \ref agora::recording::IRecordingEngineEventHandler::onError "onError" handler. It is called in \ref agora::recording::IRecordingEngineEventHandler::onError "onError" handler when meeting an error which cannot be handled.
     
     @return
     - 0: Success.
     - < 0: Failure.
     */
    virtual int stoppedOnError() = 0;

    /** This method allows the recording application to leave the channel and release the thread resources.
     
     @return
     - 0: Success.
     - < 0: Failure.
     */
    virtual int leaveChannel() = 0;

    /** This method destroys the \ref agora::recording::IRecordingEngine "IRecordingEngine" object.
     
     @return
     - 0: Success.
     - < 0: Failure.
     */
    virtual int release() = 0;

    /** This method allows you to retrieve the recording properties without joining a channel. For more information, see \ref agora::recording::RecordingEngineProperties "RecordingEngineProperties".
     
     @return
     - 0: Success.
     - < 0: Failure.
     
     @note
     - The recording properties only include the information of the path where the recording files are stored.
     - This method is different from \ref agora::recording::IRecordingEngineEventHandler::onUserJoined "onUserJoined". You must call \ref agora::recording::IRecordingEngineEventHandler::onUserJoined "onUserJoined" after joining the channel.
     */
    virtual const RecordingEngineProperties* getProperties() = 0;

    /** This method manually starts recording.
     
     The method is only valid when you set triggerMode to 1 (manually) when joining the channel. For more information, see \ref agora::linuxsdk::TRIGGER_MODE_TYPE "TRIGGER_MODE_TYPE".
     
     @return
     - 0: Success.
     - < 0: Failure.
     */
    virtual int startService() = 0;

    /** This method manually stops recording.
     
     The method is only valid when you set triggerMode to 1 (manually) when joining the channel. For more information, see \ref agora::linuxsdk::TRIGGER_MODE_TYPE "TRIGGER_MODE_TYPE".
     
     @return
     - 0: Success.
     - < 0: Failure.
     */
    virtual int stopService() = 0;
     
    /** This method sets the background image of a specified user. When the user is online but not sending out any video stream, the background image is displayed.
     
     @note If the user uses Agora Web SDK, the user sends the audio and video stream all the time. In this case, the background image is not displayed.
     @param uid The UID of the user for the background image to be set.
     @param img_path The path of the image file.
     @return
     - 0: Success.
     - < 0: Failure.
     */
    virtual int setUserBackground(uid_t uid, const char* img_path) = 0;

    /** This method sets the log level.
     
     Only log levels preceding the selected level are generated. The default value of the log level is 6.
     
     @param level See \ref agora::linuxsdk::agora_log_level "agora_log_level".
     @return
     - 0: Success.
     - < 0: Failure.
     */
    virtual int setLogLevel(agora::linuxsdk::agora_log_level level) = 0;

};

}
}

#endif
