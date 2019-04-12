#ifndef _IAGORA_LINUXSDKCOMMON_H_
#define _IAGORA_LINUXSDKCOMMON_H_

#include <cstdint>
#include <string>

namespace agora {
namespace linuxsdk {

class IEngine{
    virtual ~IEngine() {};

};

class IEngineConfig {
    virtual ~IEngineConfig() {};
};

typedef unsigned char uchar_t;
typedef unsigned int uint_t;
typedef unsigned int uid_t;
typedef uint64_t u64_t;

/** Error codes.
 @note When using Recording SDK, you may also receive error codes from Native SDK. See [Interactive Broadcast Error Codes](https://docs.agora.io/en/Interactive%20Broadcast/API%20Reference/cpp/namespaceagora.html#a8affb9bb02864d82c4333529dc3d75a1).
 */
enum ERROR_CODE_TYPE {
    
    /** 0: No error. */
    ERR_OK = 0,
    //1~1000
    /** 1: General error with no classified reason. */
    ERR_FAILED = 1,
    /** 2: Invalid parameter. For example, the specific channel name contains illegal characters. */
    ERR_INVALID_ARGUMENT = 2,
    /** 3: The SDK module is not ready. Agora recommends the following methods to solve this error:
     - Check the audio device.
     - Check the completeness of the app.
     - Re-initialize the SDK.
     */
    ERR_INTERNAL_FAILED = 3,
};

/** State codes. */
enum STAT_CODE_TYPE {
    /** 0: Everything is normal. */
    STAT_OK = 0,
    /** 1: Error from the engine. */
    STAT_ERR_FROM_ENGINE = 1,
    /** 2: Failure to join the channel. */
    STAT_ERR_ARS_JOIN_CHANNEL = 2,
    /** 3: Failure to create a process. */
    STAT_ERR_CREATE_PROCESS = 3,
    /** 4: Failure to mix the video. */
    STAT_ERR_MIXED_INVALID_VIDEO_PARAM = 4,
    /** 5: Null pointer. */
    STAT_ERR_NULL_POINTER = 5,
    /** 6: Invalid parameters of the proxy server. */
    STAT_ERR_PROXY_SERVER_INVALID_PARAM = 6,

    /** 0x8: Error in polling. */
    STAT_POLL_ERR = 0x8,
    /** 0x10: Polling hangs up. */
    STAT_POLL_HANG_UP = 0x10,
    /** 0x20: Invalid polling request. */
    STAT_POLL_NVAL = 0x20,
};

/** The reasons why the recording application leaves the channel. */
enum LEAVE_PATH_CODE {
    /** 0: Initialization failure. */
    LEAVE_CODE_INIT = 0,
    /** 1: Signal triggered exit. */
    LEAVE_CODE_SIG = 1<<1,
    /** 2: There is no user in the channel except for the recording application. */
    LEAVE_CODE_NO_USERS = 1<<2,
    /** 3: Timer catch exit. */
    LEAVE_CODE_TIMER_CATCH = 1<<3,
    /** 4: The client leaves the channel. */
    LEAVE_CODE_CLIENT_LEAVE = 1 << 4,
};

/** Warning codes.
 @note When using the Agora Recording SDK, you may also receive warning codes from the Native SDK. See [Interactive Broadcast Warning Codes](https://docs.agora.io/en/Interactive%20Broadcast/API%20Reference/cpp/namespaceagora.html#a32d042123993336be6646469da251b21).
 */
enum WARN_CODE_TYPE {
    /** 103: No channel resources are available. Maybe because the server cannot allocate any channel resource. */
    WARN_NO_AVAILABLE_CHANNEL = 103,
    /** 104: A timeout when looking up the channel. When a user joins a channel, the SDK looks up the specified channel. This warning usually occurs when the network conditions are too poor to connect to the server.
     */
    WARN_LOOKUP_CHANNEL_TIMEOUT = 104,
    /** 105: The server rejected the request to look up the channel. The server cannot process this request or the request is illegal. */
    WARN_LOOKUP_CHANNEL_REJECTED = 105,
    /** 106: A timeout occurred when opening the channel. Once the specific channel is found, the SDK opens the channel. This warning usually occurs when the network conditions are too poor to connect to the server. */
    WARN_OPEN_CHANNEL_TIMEOUT = 106,
    /** 107: The server rejected the request to open the channel. The server cannot process this request or the request is illegal. */
    WARN_OPEN_CHANNEL_REJECTED = 107,
    /** 108: An abnormal error occurs. The SDK would resume the recording. */
    WARN_RECOVERY_CORE_SERVICE_FAILURE = 108,
};

/** Channel types.
 @note The Recording SDK must use the same channel profile as the Agora Native/Web SDK, otherwise issues may occur.
 */
enum CHANNEL_PROFILE_TYPE
{
    /** 0: (Default) Communication mode. This is used in one-on-one or group calls, where all users in the channel can talk freely. */
    CHANNEL_PROFILE_COMMUNICATION = 0,
    /** 1: Live broadcast mode. The host sends and receives voice/video, while the audience only receives voice/video. Host and audience roles can be set by calling setClientRole. */
    CHANNEL_PROFILE_LIVE_BROADCASTING = 1,
};

/** The reasons why the user leaves the channel or goes offline. */
enum USER_OFFLINE_REASON_TYPE
{
    /** 0: The user has quit the call. */
    USER_OFFLINE_QUIT = 0,
    /** 1: The SDK timed out and the user dropped offline because it has not received any data packet for a period of time. If a user quits the call and the message is not passed to the SDK (due to an unreliable channel), the SDK assumes the user has dropped offline.
     */
    USER_OFFLINE_DROPPED = 1,
    /** 2: The client role has changed from the host to the audience. The option is only valid when you set the channel profile as live broadcast when calling \ref agora::recording::IRecordingEngine::joinChannel "joinChannel".
     */
    USER_OFFLINE_BECOME_AUDIENCE = 2,
};

/** Takes effect only when the Agora Native SDK has enabled dual-stream mode (high stream by default). */
enum REMOTE_VIDEO_STREAM_TYPE
{
    /** 0: (Default) High stream. */
    REMOTE_VIDEO_STREAM_HIGH = 0,
    /** 1: Low stream. */
    REMOTE_VIDEO_STREAM_LOW = 1,
};

/** Video decoding format. */
enum VIDEO_FORMAT_TYPE {
    /** 0: Default video format. */
    VIDEO_FORMAT_DEFAULT_TYPE = 0,
    /** 1: Video frame in H.264 format. */
    VIDEO_FORMAT_H264_FRAME_TYPE = 1,
    /** 2: Video frame in YUV format. */
    VIDEO_FORMAT_YUV_FRAME_TYPE = 2,
    /** 3: Video frame in JPEG format. */
    VIDEO_FORMAT_JPG_FRAME_TYPE = 3,
    /** 4: JPEG file format. */
    VIDEO_FORMAT_JPG_FILE_TYPE = 4,
    /** 5: JPEG file format + MPEG-4 video.
     - Individual Mode (\ref agora::recording::RecordingConfig#isMixingEnabled "isMixingEnabled" is set as false): MPEG-4 video and JPEG files.
     - Composite Mode (\ref agora::recording::RecordingConfig#isMixingEnabled "isMixingEnabled" is set as true): MPEG-4 video file for mixed streams and JPEG files for individual streams.
     */
    VIDEO_FORMAT_JPG_VIDEO_FILE_TYPE = 5,
};

/** Audio decoding format. */
enum AUDIO_FORMAT_TYPE {
    /** 0: Default audio format. */
    AUDIO_FORMAT_DEFAULT_TYPE = 0,
    /** 1: Audio frame in AAC format. */
    AUDIO_FORMAT_AAC_FRAME_TYPE = 1,
    /** 2: Audio frame in PCM format. */
    AUDIO_FORMAT_PCM_FRAME_TYPE = 2,
    /** 3: Audio-mixing frame in PCM format. */
    AUDIO_FORMAT_MIXED_PCM_FRAME_TYPE = 3,
};

/** Audio frame type. */
enum AUDIO_FRAME_TYPE {
    /** 0: PCM format. */
    AUDIO_FRAME_RAW_PCM = 0,
    /** 1: AAC format. */
    AUDIO_FRAME_AAC = 1
};

/** Memory type. */
enum MEMORY_TYPE {
    /** 0: Stack. */
    STACK_MEM_TYPE = 0,
    /** 1: Heap. */
    HEAP_MEM_TYPE = 1
};

/** Video frame type. */
enum VIDEO_FRAME_TYPE {
    /** 0: YUV format. */
    VIDEO_FRAME_RAW_YUV = 0,
    /** 1: H.264 format. */
    VIDEO_FRAME_H264 = 1,
    /** 2: JPG format. */
    VIDEO_FRAME_JPG = 2,
};

enum SERVICE_MODE {
    RECORDING_MODE = 0,//down stream
    SERVER_MODE = 1,//up-down stream
    IOT_MODE = 2,//up-down stream
};

/** Whether to record automatically or manually. */
enum TRIGGER_MODE_TYPE {
    /** 0: Automatically. */
    AUTOMATICALLY_MODE = 0,
    /** 1: Manually. To start and stop recording, call \ref agora::recording::IRecordingEngine::startService "startService" and \ref agora::recording::IRecordingEngine::stopService "stopService" respectively.
     */
    MANUALLY_MODE = 1
};
    
/** The programming language. */
enum LANGUAGE_TYPE {
    /** 0: CPP_LANG. */
    CPP_LANG = 0,
    /** 1: Java. */
    JAVA_LANG = 1
};
    
/** Audio profile. Sets the sampling rate, bitrate, encode mode, and the number of channels. */
enum AUDIO_PROFILE_TYPE {
    /** 0: (Default) Sampling rate of 48 kHz, communication encoding, mono, and a bitrate of up to 48 Kbps. */
    AUDIO_PROFILE_DEFAULT = 0, //use default settings.
    /** 1: Sampling rate of 48 kHz, music encoding, mono, and a bitrate of up to 128 Kbps. */
    AUDIO_PROFILE_HIGH_QUALITY = 1, //48khz, 128kbps, mono, music
    /** 2: Sampling rate of 48 kHz, music encoding, stereo, and a bitrate of up to 192 Kbps. */
    AUDIO_PROFILE_HIGH_QUALITY_STEREO = 2, //48khz, 192kbps, stereo, music
};

/** Log level. */
enum agora_log_level {
    /** 1: Fatal. */
    AGORA_LOG_LEVEL_FATAL = 1,
    /** 2: Error. */
    AGORA_LOG_LEVEL_ERROR = 2,
    /** 3: Warn. */
    AGORA_LOG_LEVEL_WARN = 3,
    /** 4: Notice. */
    AGORA_LOG_LEVEL_NOTICE = 4,
    /** 5: Info. */
    AGORA_LOG_LEVEL_INFO = 5,
    /** 6: Debug. */
    AGORA_LOG_LEVEL_DEBUG = 6,
};


/** Mix audio and video in real time. Takes effect only when the \ref agora::recording::RecordingConfig#isMixingEnabled "isMixingEnabled" is set as true.
 
 Supported Players:
 | Platform | Player/Browser         | mixedVideoAudio = 0 | mixedVideoAudio = 1 | mixedVideoAudio = 2 |
 | -------- | ----------------------- | ----------------- | ----------------- | ----------------- |
 | Linux    | VLC Media Player        | Supported         | Supported         | Supported         |
 | Linux    | ffplayer                | Supported         | Supported         | Supported         |
 | Linux    | Chrome                  | **Not Supported** | **Not Supported** | **Not Supported** |
 | Windows  | Media Player            | Supported         | Supported         | Supported         |
 | Windows  | KM Player               | Supported         | Supported         | Supported         |
 | Windows  | VLC Player              | Supported         | Supported         | Supported         |
 | Windows  | Chrome (49.0.2623+)     | Supported         | Supported         | Supported         |
 | macOS    | QuickTime Player        | Supported         | Supported         | Supported         |
 | macOS    | VLC                     | **Not Supported** | **Not Supported** | **Not Supported** |
 | macOS    | Movist                  | Supported         | Supported         | Supported         |
 | macOS    | MPlayerX                | Supported         | Supported         | Supported         |
 | macOS    | KMPlayer                | **Not Supported** | **Not Supported** | **Not Supported** |
 | macOS    | Chrome (47.0.2526.111+) | Supported         | Supported         | Supported         |
 | macOS    | Safari (11.0.3+)        | Supported         | Supported         | Supported         |
 | iOS      | Default Player          | Supported         | Supported         | Supported         |
 | iOS      | VLC for Mobile          | **Not Supported** | **Not Supported** | Supported         |
 | iOS      | KMPlayer                | Supported         | Supported         | Supported         |
 | iOS      | Safari (9.0+)           | Supported         | Supported         | Supported         |
 | Android  | Default Player          | Supported         | Supported         | Supported         |
 | Android  | MXPlayer                | Supported         | Supported         | Supported         |
 | Android  | VLC for Android         | Supported         | Supported         | Supported         |
 | Android  | KMPlayer                | Supported         | Supported         | Supported         |
 | Android  | Chrome (49.0.2623+)     | Supported         | Supported         | Supported         |
 
 */
enum MIXED_AV_CODEC_TYPE {
    /** 0: (Default) Mixes the audio and video respectively.*/
    MIXED_AV_DEFAULT = 0,  
    /** 1: Mixes the audio and video in real time into an MPEG-4 file. Supports limited players.*/
    MIXED_AV_CODEC_V1 = 1,
    /** 2: Mixes the audio and video in real time into an MPEG-4 file. Supports more players.*/
    MIXED_AV_CODEC_V2 = 2,  
};

/* Properties of the audio volume information. An array containing the user ID and volume information for each speaker. */
struct AudioVolumeInfo {
    /* The user ID of the speaker. The uid of the local user is 0. */
    uid_t uid;
    /*The volume of the speaker. The value ranges between 0 (lowest volume) and 255 (highest volume).*/
    uint32_t volume;
};

/** The parameters of the audio frame in PCM format. */
class AudioPcmFrame {
    public:
    AudioPcmFrame(u64_t frame_ms, uint_t sample_rates, uint_t samples);
    ~AudioPcmFrame();
    public:
    /** Timestamp of the frame.*/
    u64_t frame_ms_;
    /** Number of audio channels.*/
    uint_t channels_; // 1
    /** Bitrate of the sampling data.*/
    uint_t sample_bits_; // 16
    /** Sampling rate.*/
    uint_t sample_rates_; // 8k, 16k, 32k
    /** Number of samples of the frame.*/
    uint_t samples_;

    /** Audio frame buffer.*/
    const uchar_t *pcmBuf_;
    /** Size of the audio frame buffer.*/
    uint_t pcmBufSize_;
};

/** The parameters of the audio frame in AAC format. */
class AudioAacFrame {
    public:
    explicit AudioAacFrame(u64_t frame_ms);
    ~AudioAacFrame();

    /** Audio frame buffer.*/
    const uchar_t *aacBuf_;
    /** Timestamp of the frame.*/
    u64_t frame_ms_;
    /** Size of the audio frame buffer.*/
    uint_t aacBufSize_;
    /** Number of audio channels.*/
    uint_t channels_;
    /** Bitrate of the audio.*/
    uint_t bitrate_;
};

/** The audio frame format.*/
struct AudioFrame {
    /** The audio frame format, PCM or ACC. See \ref agora::linuxsdk::AUDIO_FRAME_TYPE “AUDIO_FRAME_TYPE”. */
    AUDIO_FRAME_TYPE type;
    union {
        /** Audio data in PCM format. See the structure of \ref AudioPcmFrame "AudioPcmFrame".*/
        AudioPcmFrame *pcm;
        /** Audio data in AAC format. See the structure of \ref AudioAacFrame "AudioAacFrame".*/
        AudioAacFrame *aac;
    } frame;

    AudioFrame();
    ~AudioFrame();
    /** See \ref agora::linuxsdk::MEMORY_TYPE "MEMORY_TYPE". */
    MEMORY_TYPE mType;
};

/** The parameters of the video frame in YUV format. */
class VideoYuvFrame {
    public:
    VideoYuvFrame(u64_t frame_ms, uint_t width, uint_t height, uint_t ystride,
            uint_t ustride, uint_t vstride);
    ~VideoYuvFrame();

    /** Timestamp of the frame.*/
    u64_t frame_ms_;

    /** Y buffer pointer.*/
    const uchar_t *ybuf_;
    /** U buffer pointer.*/
    const uchar_t *ubuf_;
    /** V buffer pointer.*/
    const uchar_t *vbuf_;

    /** Width of the video in the number of pixels.*/
    uint_t width_;
    /** Height of the video in the number of pixels.*/
    uint_t height_;

    /** Line span of the Y buffer.*/
    uint_t ystride_;
    /** Line span of the U buffer.*/
    uint_t ustride_;
    /** Line span of the V buffer.*/
    uint_t vstride_;

    //all
    /** Video frame buffer.*/
    const uchar_t *buf_;
    /** Size of the video frame buffer.*/
    uint_t bufSize_;
};

/** The parameters of the video frame in H.264 format. */
struct VideoH264Frame {
    public:
    VideoH264Frame():
        frame_ms_(0),
        frame_num_(0),
        buf_(NULL),
        bufSize_(0)
    {}

    ~VideoH264Frame(){}
    /** Timestamp of the frame.*/
    u64_t frame_ms_;
    /** Index of the frame.*/
    uint_t frame_num_;

    //all
    /** Video frame buffer.*/
    const uchar_t *buf_;
    /** Size of the video frame buffer.*/
    uint_t bufSize_;
};

/** The parameters of the video frame in JPG format. */
struct VideoJpgFrame {
    public:
    VideoJpgFrame():
        frame_ms_(0),
        buf_(NULL),
        bufSize_(0){}

   ~VideoJpgFrame() {}
    /** Timestamp of the frame.*/
    u64_t frame_ms_;

    //all
    /** Video frame buffer.*/
    const uchar_t *buf_;
    /** Size of the video frame buffer.*/
    uint_t bufSize_;
};

/** The video frame format.*/
struct VideoFrame {
    /** The video frame format, Yuv, H264 or Jpg. See agora::linuxsdk::VIDEO_FRAME_TYPE “VIDEO_FRAME_TYPE” */
    VIDEO_FRAME_TYPE type;
    union {
        /** Video data in Yuv format. See the structure of \ref VideoYuvFrame "VideoYuvFrame".*/
        VideoYuvFrame *yuv;
        /** Video data in H264 format. See the structure of \ref VideoH264Frame "VideoH264Frame".*/
        VideoH264Frame *h264;
        /** Video data in Jpg format. See the structure of \ref VideoJpgFrame "VideoJpgFrame".*/
        VideoJpgFrame *jpg;
    } frame;

    /** The rotation of the video frame, 0, 90, 180 or 270. */
    int rotation_; // 0, 90, 180, 270
    VideoFrame();
    ~VideoFrame();

    /** See \ref agora::linuxsdk::MEMORY_TYPE "MEMORY_TYPE". */
    MEMORY_TYPE mType;
};

/** The layout setting of the mixed video. */
typedef struct VideoMixingLayout
{
    /** The setting of the region. */
    struct Region {
        /** UID of the user (communication mode)/host (live broadcast mode) displaying the video in the region. */
        uid_t uid;
        /** Relative horizontal position of the top-left corner of the region. The value is between 0.0 and 1.0. */
        double x;//[0,1]
        /** Relative vertical position of the top-left corner of the region. The value is between 0.0 and 1.0. */
        double y;//[0,1]
        /** Relative width of the region. The value is between 0.0 and 1.0. */
        double width;//[0,1]
        /** Relative height of the region. The value is between 0.0 and 1.0. */
        double height;//[0,1]
        //  Optional
        //  [0, 1.0] where 0 denotes throughly transparent, 1.0 opaque
        /** The transparency of the image. The value is between 0.0 (transparent) and 1.0 (opaque).
         
         @note This parameter is reserved for future use.
         */
        double alpha;

        /** Render mode.
         
         - RENDER_MODE_HIDDEN(1): Cropped mode. Uniformly scale the video until it fills the visible boundaries (cropped). One dimension of the video may have clipped contents.
         - RENDER_MODE_FIT(2): Fit mode. Uniformly scale the video until one of its dimension fits the boundary (zoomed to fit). Areas that are not filled due to the disparity in the aspect ratio will be filled with black.
         */
        int renderMode;//RENDER_MODE_HIDDEN: Crop, RENDER_MODE_FIT: Zoom to fit
        Region()
            :uid(0)
             , x(0)
             , y(0)
             , width(0)
             , height(0)
             , alpha(1.0)
             , renderMode(1)
        {}

    };
    /** Width of the canvas (the display window or screen). */
    int canvasWidth;
    /** Height of the canvas (the display window or screen). */
    int canvasHeight;
    /** The background color of the canvas (the display window or screen) in RGB hex value. */
    const char* backgroundColor;//e.g. "#C0C0C0" in RGB
    /** The number of the users (communication mode)/hosts (live broadcast mode) in the channel. */
    uint32_t regionCount;
    /** The user (communication mode)/host (live broadcast mode) list of #VideoMixingLayout. Each user (communication mode)/host (live broadcast mode) in the channel has a region to display the video on the screen with the following parameters to be set. See \ref agora::linuxsdk::VideoMixingLayout::Region "Region" to set parameters. */
    const Region* regions;
    /** User-defined data.*/
    const char* appData;
    /** The length of the user-defined data. */
    int appDataLength;
    VideoMixingLayout()
        :canvasWidth(0)
         , canvasHeight(0)
         , backgroundColor(NULL)
         , regionCount(0)
         , regions(NULL)
         , appData(NULL)
         , appDataLength(0)
    {}
} VideoMixingLayout;

/** UserJoinInfos. */
typedef struct UserJoinInfos {
    /** Directory of the recorded files. */
    const char* storageDir;
    //new attached info add below

    UserJoinInfos():
        storageDir(NULL)
    {}
}UserJoinInfos;


}
}

#endif
