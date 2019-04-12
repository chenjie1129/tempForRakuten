package io.agora.recording.common;

import java.nio.ByteBuffer;

public class Common{
  /** Error codes.
   * @note When using the Recording SDK, you may receive error codes from the Native SDK. See <a href="https://docs.agora.io/en/Interactive%20Broadcast/API%20Reference/java/classio_1_1agora_1_1rtc_1_1_i_rtc_engine_event_handler_1_1_error_code.html"> Interactive Broadcast Error Codes</a>.
   */
  public enum ERROR_CODE_TYPE {
    /** 0: No error. */
    ERR_OK(0),
    /** 1: General error with no classified reason. */
    ERR_FAILED(1),
    /** 2: Invalid parameter is called. For example, the specific channel name contains illegal characters. */
    ERR_INVALID_ARGUMENT(2),
    /** 3: The SDK module is not ready. Agora recommends the following methods to solve this error:
    * <ul>
    *   <li>Check the audio device.</li>
    *   <li>Check the completeness of the app.</li>
    *   <li>Re-initialize the SDK.</li>
    * </ul>
    */
    ERR_INTERNAL_FAILED(3);
    private ERROR_CODE_TYPE(int value){}
  }

  /** State codes. */
  public enum STAT_CODE_TYPE {
    /** 1: Error from the engine. */
    STAT_ERR_FROM_ENGINE(1),
    /** 2: Failure to join the channel. */
    STAT_ERR_ARS_JOIN_CHANNEL(2),
    /** 3: Failure to create a process. */
    STAT_ERR_CREATE_PROCESS(3),
    /** 4: Failure to mix the video. */
    STAT_ERR_MIXED_INVALID_VIDEO_PARAM(4),
    /** 5: Null pointer. */
    STAT_ERR_NULL_POINTER(5),
    /** 6: Invalid parameters of the proxy server. */
    STAT_ERR_PROXY_SERVER_INVALID_PARAM(6),
    /** 0x8: Error in polling. */
    STAT_POLL_ERR(8),
    /** 0x10: Polling hangs up. */
    STAT_POLL_HANG_UP(16),
    /** 0x20: Invalid polling request. */
    STAT_POLL_NVAL(32);
    private STAT_CODE_TYPE(int value) {}
  }

  /** The reasons why the recording app leaves channel. */
  public enum LEAVE_PATH_CODE {
    /** 0: Initialization failure. */
    LEAVE_CODE_INIT(0),
    /** 1: Signal triggered exit. */
    LEAVE_CODE_SIG(1<<1),
    /** 2: There is no user in the channel except for the recording app. */
    LEAVE_CODE_NO_USERS(1<<2),
    /** 3: Timer catch exit. */
    LEAVE_CODE_TIMER_CATCH(1<<3),
    /** 4: The client leaves the channel. */
    LEAVE_CODE_CLIENT_LEAVE(1<<4);
    private LEAVE_PATH_CODE(int code) {}
  }

  /** Warning codes.
   *
   * @note When using the Recording SDK, you may receive warning codes from the Native SDK. See <a href="https://docs.agora.io/en/Interactive%20Broadcast/API%20Reference/java/classio_1_1agora_1_1rtc_1_1_i_rtc_engine_event_handler_1_1_warn_code.html"> Interactive Broadcast Warning Codes</a>.
   */
  public enum WARN_CODE_TYPE {
    /** 103: No channel resources are available. Maybe because the server cannot
     * allocate any channel resource.
     */
    WARN_NO_AVAILABLE_CHANNEL(103),
    /** 104: A timeout when looking up the channel. When a user joins a channel, the SDK
     * looks up the specified channel. This warning usually occurs when the network
     * conditions are too poor to connect to the server.
     */
    WARN_LOOKUP_CHANNEL_TIMEOUT(104),
    /** 105: The server rejected the request to look up the channel.
     * The server cannot process this request or the request is illegal.
     */
    WARN_LOOKUP_CHANNEL_REJECTED(105),
    /** 106: A timeout occurred when opening the channel. Once the specific channel
     * is found, the SDK opens the channel. This warning usually occurs when the
     * network conditions are too poor to connect to the server.
     */
    WARN_OPEN_CHANNEL_TIMEOUT(106),
    /** 107: The server rejected the request to open the channel. The server cannot
     * process this request or the request is illegal.
     */
    WARN_OPEN_CHANNEL_REJECTED(107);
    private WARN_CODE_TYPE(int value){}
  }

  /** Sets the channel mode.
   @note The Recording SDK must use the same channel profile as the Agora Native/Web SDK, otherwise issues may occur.
   */
  public enum CHANNEL_PROFILE_TYPE {
    /** 0: (Default) Communication mode. This is used in one-on-one or group calls,
     * where all users in the channel can talk freely.
     */
    CHANNEL_PROFILE_COMMUNICATION(0),
    /** 1: Live broadcast. The host sends and receives voice/video, while the audience
     * only receives voice/video. Host and audience roles can be set by calling setClientRole.
     */
    CHANNEL_PROFILE_LIVE_BROADCASTING(1);
    private int value;
    private CHANNEL_PROFILE_TYPE(int value) {
      this.value = value;
    }
    public int getValue() {
      return value;
    }
  }

  /** The reasons why a user leaves the channel or goes offline.*/
  public enum USER_OFFLINE_REASON_TYPE  {
    /** 0: The user has quit the call. */
    USER_OFFLINE_QUIT(0),
    /** 1: The SDK timed out and the user dropped offline because it has not
     * received any data packet for a period of time. If a user quits the call
     * and the message is not passed to the SDK (due to an unreliable channel),
     * the SDK assumes the user has dropped offline.
     */
    USER_OFFLINE_DROPPED(1),
    /** 2: The client role has changed from the host to the audience. The option
     * is only valid when you set the channel profile as live broadcast when calling joinChannel.
     */
    USER_OFFLINE_BECOME_AUDIENCE(2);
    private USER_OFFLINE_REASON_TYPE(int code) {}
  }

  /** streamType takes effect only when the Native SDK or Web SDK has enabled dual-stream mode (high stream by default). */
  public enum REMOTE_VIDEO_STREAM_TYPE {
    /** 0: (Default) High stream. */
    REMOTE_VIDEO_STREAM_HIGH(0),
    /** 1: Low stream. */
    REMOTE_VIDEO_STREAM_LOW(1);
    private int value;
    private REMOTE_VIDEO_STREAM_TYPE(int value) {
      this.value = value;
    }
    public int getValue() {
      return value;
    }
  }

  public enum SERVICE_MODE {
    RECORDING_MODE(0),//down stream
    SERVER_MODE(1),//up-down stream
    IOT_MODE(2);//up-down stream
    private SERVICE_MODE(int value){}
  }

  /** Video decoding format. */
  public enum VIDEO_FORMAT_TYPE {
    /** 0: Default video format. */
    VIDEO_FORMAT_DEFAULT_TYPE(0),
    /** 1: Video frame in H.264 format. */
    VIDEO_FORMAT_H264_FRAME_TYPE(1),
    /** 2: Video frame in YUV format. */
    VIDEO_FORMAT_YUV_FRAME_TYPE(2),
    /** 3: Video frame in JPEG format. */
    VIDEO_FORMAT_JPG_FRAME_TYPE(3),
    /** 4: JPEG file format. */
    VIDEO_FORMAT_JPG_FILE_TYPE(4),
    /** 5: JPEG file format + MPEG-4 video.
     * <ul>
     *   <li>Individual Mode ({@link io.agora.recording.common.RecordingConfig#isMixingEnabled isMixingEnabled} is set as false): MPEG-4 video and JPEG files are supported. </li>
     *   <li>Composite Mode ({@link io.agora.recording.common.RecordingConfig#isMixingEnabled isMixingEnabled} is set as true): MPEG-4 video files for combined streams and JPEG files for individual streams are supported. </li>
     * </ul>
     */
    VIDEO_FORMAT_JPG_VIDEO_FILE_TYPE(5);
    private int value;
    private VIDEO_FORMAT_TYPE(int value) {
    this.value = value;
    }
    private int getValue(){
      return value;
    }
  }

  /** Audio decoding format. */
  public enum AUDIO_FORMAT_TYPE {
    /** 0: Default audio format. */
    AUDIO_FORMAT_DEFAULT_TYPE(0),
    /** 1: Audio frame in AAC format. */
    AUDIO_FORMAT_AAC_FRAME_TYPE(1),
    /** 2: Audio frame in PCM format. */
    AUDIO_FORMAT_PCM_FRAME_TYPE(2),
    /** 3: Audio frame in PCM audio-mixing format.*/
    AUDIO_FORMAT_MIXED_PCM_FRAME_TYPE(3);
    private int value;
    private AUDIO_FORMAT_TYPE(int value) {
      this.value = value;
    }
    private int getValue(){
      return value;
    }
  }

  /** Audio frame type. */
  public enum AUDIO_FRAME_TYPE {
    /** PCM frame. */
    AUDIO_FRAME_RAW_PCM,
    /** AAC frame. */
    AUDIO_FRAME_AAC
  }

  /**  Video frame type.*/
  public class VIDEO_FRAME_TYPE {
    /** 0: YUV frame. */
    public final int VIDEO_FRAME_RAW_YUV = 0;
    /** 1: H.264 frame. */
    public final int VIDEO_FRAME_H264 = 1;
    /** 2: JPG frame. */
    public final int VIDEO_FRAME_JPG = 2;
    public int type = 0;
    public int getValue(){
      return type;
    }
  }


  /** Supported Players:
  * <table>
  *   <tr>
  *     <th>Platform</th>
  *     <th>Player/Browser</th>
  *     <th>mixedVideoAudio=0</th>
  *     <th>mixedVideoAudio=1</th>
  *     <th>mixedVideoAudio=2</th>
  *   </tr>
  *   <tr>
  *     <td>Linux</td>
  *     <td>VLC Media Player</td>
  *     <td>Supported</td>
  *     <td>Supported</td>
  *     <td>Supported</td>
  *   </tr>
  *   <tr>
  *     <td>Linux</td>
  *     <td>ffplayer</td>
  *     <td>Supported</td>
  *     <td>Supported</td>
  *     <td>Supported</td>
  *   </tr>
  *   <tr>
  *     <td>Linux</td>
  *     <td>Chrome</td>
  *     <td><strong>Not Supported</strong></td>
  *     <td><strong>Not Supported</strong></td>
  *     <td><strong>Not Supported</strong></td>
  *   </tr>
  *   <tr>
  *     <td>Windows</td>
  *     <td>Media Player</td>
  *     <td>Supported</td>
  *     <td>Supported</td>
  *     <td>Supported</td>
  *   </tr>
  *   <tr>
  *     <td>Windows</td>
  *     <td>KM Player</td>
  *     <td>Supported</td>
  *     <td>Supported</td>
  *     <td>Supported</td>
  *   </tr>
  *   <tr>
  *     <td>Windows</td>
  *     <td>VLC Player</td>
  *     <td>Supported</td>
  *     <td>Supported</td>
  *     <td>Supported</td>
  *   </tr>
  *   <tr>
  *     <td>Windows</td>
  *     <td>Chrome (49.0.2623+)</td>
  *     <td>Supported</td>
  *     <td>Supported</td>
  *     <td>Supported</td>
  *   </tr>
  *   <tr>
  *     <td>macOS</td>
  *     <td>QuickTime Player</td>
  *     <td>Supported</td>
  *     <td>Supported</td>
  *     <td>Supported</td>
  *   </tr>
  *   <tr>
  *     <td>macOS</td>
  *     <td>VLC</td>
  *     <td><strong>Not Supported</strong></td>
  *     <td><strong>Not Supported</strong></td>
  *     <td><strong>Not Supported</strong></td>
  *   </tr>
  *   <tr>
  *     <td>macOS</td>
  *     <td>Movist</td>
  *     <td>Supported</td>
  *     <td>Supported</td>
  *     <td>Supported</td>
  *   </tr>
  *   <tr>
  *     <td>macOS</td>
  *     <td>MPlayerX</td>
  *     <td>Supported</td>
  *     <td>Supported</td>
  *     <td>Supported</td>
  *   </tr>
  *   <tr>
  *     <td>macOS</td>
  *     <td>KM Player</td>
  *     <td><strong>Not Supported</strong></td>
  *     <td><strong>Not Supported</strong></td>
  *     <td><strong>Not Supported</strong></td>
  *   </tr>
  *   <tr>
  *     <td>macOS</td>
  *     <td>Chrome (47.0.2526.111+)</td>
  *     <td>Supported</td>
  *     <td>Supported</td>
  *     <td>Supported</td>
  *   </tr>
  *   <tr>
  *     <td>macOS</td>
  *     <td>Safari (11.0.3+)</td>
  *     <td>Supported</td>
  *     <td>Supported</td>
  *     <td>Supported</td>
  *   </tr>
  *   <tr>
  *     <td>iOS</td>
  *     <td>Default Player</td>
  *     <td>Supported</td>
  *     <td>Supported</td>
  *     <td>Supported</td>
  *   </tr>
  *   <tr>
  *     <td>iOS</td>
  *     <td>VLC for Mobile</td>
  *     <td><strong>Not Supported</strong></td>
  *     <td><strong>Not Supported</strong></td>
  *     <td>Supported</td>
  *   </tr>
  *   <tr>
  *     <td>iOS</td>
  *     <td>KM Player</td>
  *     <td>Supported</td>
  *     <td>Supported</td>
  *     <td>Supported</td>
  *   </tr>
  *   <tr>
  *     <td>iOS</td>
  *     <td>Safari (9.0+)</td>
  *     <td>Supported</td>
  *     <td>Supported</td>
  *     <td>Supported</td>
  *   </tr>
  *   <tr>
  *     <td>Android</td>
  *     <td>Default Player</td>
  *     <td>Supported</td>
  *     <td>Supported</td>
  *     <td>Supported</td>
  *   </tr>
  *   <tr>
  *     <td>Android</td>
  *     <td>MX Player</td>
  *     <td>Supported</td>
  *     <td>Supported</td>
  *     <td>Supported</td>
  *   </tr>
  *   <tr>
  *     <td>Android</td>
  *     <td>VLC for Android</td>
  *     <td>Supported</td>
  *     <td>Supported</td>
  *     <td>Supported</td>
  *   </tr>
  *   <tr>
  *     <td>Android</td>
  *     <td>KM Player</td>
  *     <td>Supported</td>
  *     <td>Supported</td>
  *     <td>Supported</td>
  *   </tr>
  *   <tr>
  *     <td>Android</td>
  *     <td>Chrome (49.0.2623+)</td>
  *     <td>Supported</td>
  *     <td>Supported</td>
  *     <td>Supported</td>
  *   </tr>
  * </table>
  */


  public enum MIXED_AV_CODEC_TYPE {
      /** 0: (Default) Not mixes the audio and video. */
      MIXED_AV_DEFAULT(0),
      /** 1: Mixes the audio and video in real time into an MPEG-4 file. Supports limited players. */
      MIXED_AV_CODEC_V1(1),
      /** 2: Mixes the audio and video in real time into an MPEG-4 file. Supports more players. */
      MIXED_AV_CODEC_V2(2);
      private int value;
      private MIXED_AV_CODEC_TYPE(int value) {
          this.value = value;
      }
      private int getValue(){
          return value;
      }
  }

  /** AudioFrame class when raw audio data is used. */
  public class AudioFrame {
    /** AUDIO_FRAME_TYPE indicates the audio frame format, PCM or AAC. */
    public AUDIO_FRAME_TYPE type;
    /** If type is AUDIO_FRAME_RAW_PCM, then PCM field points to PCM format audio data, AAC should not be used. See the structure of {@link io.agora.recording.common.Common#AudioPcmFrame AudioPcmFrame}. */
    public AudioPcmFrame pcm;
    /** If type is AUDIO_FRAME_AAC, then AAC field points to AAC format audio data, PCM should not be used. See the structure of {@link io.agora.recording.common.Common#AudioAacFrame AudioAacFrame}. */
    public AudioAacFrame aac;
  }

  /** The parameters of raw audio data in PCM format. */
  public class AudioPcmFrame {
    public AudioPcmFrame(long frame_ms, long sample_rates, long samples) {
    }
    /** Timestamp of the frame. */
    public long frame_ms;
    /** Number of audio channels. */
    public long channels; // 1
    /** Bitrate of the sampling data. */
    public long sample_bits; // 16
    /** Sampling rate. */
    public long sample_rates; // 8k, 16k, 32k
    /** Number of samples of the frame. */
    public long samples;
    /** Audio frame buffer. */
    public byte[] pcmBuf;
    /** Size of the audio frame buffer. */
    public long pcmBufSize;
  }

  /** TThe parameters of raw audio data in AAC format. */
  public class AudioAacFrame {
    public AudioAacFrame(long framems) {
      frame_ms = framems;
      aacBufSize = 0;
    }
    /** Timestamp of the frame. */
    public long frame_ms;
    /** Audio frame buffer. */
    public byte[] aacBuf;
    /** Size of the audio frame buffer. */
    public long aacBufSize;
    /** Channels Number of audio channels. */
    public int channels;
    /** Bitrate Bitrate of the audio.*/
    public int bitrate;
  }

  /** The parameters of raw video data in YUV format. */
  public class VideoYuvFrame {
    VideoYuvFrame(long framems, int width, int height, int ystride,int ustride, int vstride){
      this.frame_ms = framems;
      this.width = width;
      this.height = height;
      this.ystride = ystride;
      this.ustride = ustride;
      this.vstride = vstride;
    }
    /** Timestamp of the frame. */
    public long frame_ms;
    /** Y buffer pointer. */
    public ByteBuffer ybuf;
    /** U buffer pointer. */
    public ByteBuffer ubuf;
    /** V buffer pointer. */
    public ByteBuffer vbuf;
    /** Width of the video in the number of pixels. */
    public int width;
    /** Height of the video in the number of pixels. */
    public int height;
    /** Line span of the Y buffer. */
    public int ystride;
    /** Line span of the U buffer. */
    public int ustride;
    /** Line span of the V buffer. */
    public int vstride;
    /** Video frame buffer. */
    public byte[] buf;
    /** Size of the video frame buffer. */
    public long bufSize;
  }

  /** The parameters of raw video data in H.264 format.  */
  public class VideoH264Frame {
    VideoH264Frame(){
      frame_ms = 0;
      frame_num = 0;
      bufSize = 0;
    }
    /** Timestamp of the frame. */
    public long frame_ms;
    /** Index of the frame. */
    public long frame_num;
    /** Video frame buffer. */
    public byte[] buf;
    /** Size of the video frame buffer. */
    public long bufSize;
  }

  /** The parameters of raw video data in JPG format. */
  public class VideoJpgFrame {
    VideoJpgFrame(){
      frame_ms = 0;
      bufSize = 0;
    }
    /** Timestamp of the frame. */
    public long frame_ms;
    /** Video frame buffer. */
    public byte[] buf;
    /** Size of the video frame buffer. */
    public long bufSize;
  }

  /** VideoFrame class when raw video data is used. */
  public class VideoFrame {
    /** If VideoFrame is in YUV format, then YUV points to video data in YUV format, H.264 and JPG fields should not be used. See {@link io.agora.recording.common.Common#VideoYuvFrame VideoYuvFrame}. */
    public VideoYuvFrame yuv;
    /** If VideoFrame is in H.264 format, then H.264 points to video data in H.264 format, YUV and JPG fields should not be used. See {@link io.agora.recording.common.Common#VideoH264Frame VideoH264Frame}. */
    public VideoH264Frame h264;
    /** If VideoFrame is in JPG format, then JPG points to video data in JPG format, YUV and H.264 fields should not be used. See {@link io.agora.recording.common.Common#VideoJpgFrame VideoJpgFrame}. */
    public VideoJpgFrame jpg;
    /** Indicates the rotation of the video frame. */
    public int rotation; // 0, 90, 180, 270
  }

  public class UserJoinInfos {
    String storageDir;
    UserJoinInfos(){
      storageDir = "";
    }
  }

  /* Properties of the audio volume information. An array containing the user ID and volume information for each speaker. */
  public class AudioVolumeInfo {
    /* The user ID of the speaker. The uid of the local user is 0. */
    public long uid;
     /*The volume of the speaker. The value ranges between 0 (lowest volume) and 255 (highest volume).*/
    public int volume; // 0 ~ 255
  }

  /** The structure of VideoMixingLayout. */
  public class VideoMixingLayout
  {
    /** Width of the canvas (the display window or screen). */
    public int canvasWidth;
    /** Height of the canvas (the display window or screen). */
    public int canvasHeight;
    /** The background color of the canvas (the display window or screen) in RGB hex value. */
    public String backgroundColor;//e.g. "#C0C0C0" in RGB
    /** The number of the users (communication mode) or hosts (live broadcast mode) in the channel. */
    public int regionCount;
    /** The users (communication mode) or hosts (live broadcast mode) list of VideoMixingLayout. Each user (communication mode) or host (live broadcast mode) in the channel has a region to display the video on the screen. See {@link io.agora.recording.common.Common#VideoMixingLayout#Region Region} to set parameters. */
    public Region[] regions;
    /** User-defined data. */
    public String appData;
    /** The length of the user-defined data. */
    public int appDataLength;
    /** The parameters of region. */
    public class Region {
      /** User IDs of the users (communication mode) or host (live broadcast mode) displaying the video in the region. */
      public long uid;
      /** Relative horizontal position of the top-left corner of the region. The value is between 0.0 and 1.0. */
      public double x;//[0,1]
      /** Relative vertical position of the top-left corner of the region. The value is between 0.0 and 1.0. */
      public double y;//[0,1]
      /**  Relative width of the region. The value is between 0.0 and 1.0. */
      public double width;//[0,1]
      /** Relative height of the region. The value is between 0.0 and 1.0. */
      public double height;//[0,1]
      /**  The transparency of the image. The value is between 0.0 (transparent) and 1.0 (opaque).
      * @note
      * This parameter is reserved for future use.
      */
      public double alpha;
      /**  Render mode:
       * <ul>
       *   <li> RENDER_MODE_HIDDEN(1): Cropped mode. Uniformly scale the video until it fills the visible boundaries (cropped). One dimension of the video may have clipped contents.</li>
       *   <li> RENDER_MODE_FIT(2): Fit mode. Uniformly scale the video until one of its dimension fits the boundary (zoomed to fit). Areas that are not filled due to the disparity in the aspect ratio will be filled with black.</li>
       * </ul>
       */
      public int renderMode;//RENDER_MODE_HIDDEN: Crop, RENDER_MODE_FIT: Zoom to fit
      public Region(){
        uid = 0;
        x = 0;
        y = 0;
        width = 0;
        height = 0;
        alpha = 1.0;
        renderMode = 0;
      }
    }
    public VideoMixingLayout() {
      canvasWidth = 0;
      canvasHeight = 0;
      backgroundColor = "";
      regionCount = 0;
      //regions = 0;
      appData = "";
      appDataLength = 0;
    }
  }
}
