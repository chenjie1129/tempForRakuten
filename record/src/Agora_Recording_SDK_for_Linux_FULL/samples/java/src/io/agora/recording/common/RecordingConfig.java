package io.agora.recording.common;


import io.agora.recording.common.Common.AUDIO_FORMAT_TYPE;
import io.agora.recording.common.Common.CHANNEL_PROFILE_TYPE;
import io.agora.recording.common.Common.REMOTE_VIDEO_STREAM_TYPE;
import io.agora.recording.common.Common.VIDEO_FORMAT_TYPE;
import io.agora.recording.common.Common.MIXED_AV_CODEC_TYPE;

public class RecordingConfig {
  public RecordingConfig() {
    isAudioOnly = false;
    isVideoOnly = false;
    isMixingEnabled = false;
    mixedVideoAudio = MIXED_AV_CODEC_TYPE.MIXED_AV_DEFAULT;

    mixResolution = "";
    decryptionMode = "";
    secret = "";
    appliteDir = "";
    recordFileRootDir = "";
    cfgFilePath = "";
    proxyServer = "";
    defaultVideoBgPath = "";
    defaultUserBgPath = "";

    lowUdpPort = 0;//40000;
    highUdpPort = 0;//40004;
    idleLimitSec = 300;
    captureInterval = 5;
    triggerMode = 0;
    audioIndicationInterval = 0;
    audioProfile = 0;

    decodeVideo = VIDEO_FORMAT_TYPE.VIDEO_FORMAT_DEFAULT_TYPE;
    decodeAudio = AUDIO_FORMAT_TYPE.AUDIO_FORMAT_DEFAULT_TYPE;
    channelProfile = CHANNEL_PROFILE_TYPE.CHANNEL_PROFILE_COMMUNICATION;
    streamType = REMOTE_VIDEO_STREAM_TYPE.REMOTE_VIDEO_STREAM_HIGH;
  }

  /** Sets whether or not to record audio only:
   * <ul>
   *  <li>true: Enables audio recording and disables video recording.</li>
   *  <li>false: (Default) Enables both audio and video recording.</li>
   * </ul>
   *
   * Used together with isVideoOnly:
   * <ul>
   *   <li>If isAudioOnly is true and isVideoOnly is false, only records audio.</li>
   *   <li>If isAudioOnly is false and isVideoOnly is true, only records video.</li>
   *   <li>If isAudioOnly is false and isVideoOnly is false, records both audio and video.</li>
   *   <li>isAudioOnly and isVideoOnly can not be set as true at the same time.</li>
   * </ul>
   */
  public boolean isAudioOnly;

  /** Sets whether or not to record video only:
   * <ul>
   *   <li>true: Enables video recording and disable audio recording.</li>
   *   <li>false: (Default) Enables both audio and video recording.</li>
   * </ul>
   *
   * Used together with isAudioOnly:
   * <ul>
   *   <li>If isAudioOnly is true and isVideoOnly is false, only records audio.</li>
   *   <li>If isAudioOnly is false and isVideoOnly is true, only records video.</li>
   *   <li>If isAudioOnly is false and isVideoOnly is false, records both audio and video.</li>
   *   <li>isAudioOnly and isVideoOnly can not be set as true at the same time.</li>
   * </ul>
   */
  public boolean isVideoOnly;

  /** Sets whether or not to enable the audio- or video-composite mode.
   * <ul>
   *   <li>true: Enables the composite mode, which means the audio of all uids is mixed in an audio
   *       file and the video of all uids is mixed in a video file.The sample rate, bitrate, and audio
   *       channel number of the recording file are the same as the highest level of those of the original audio streams.</li>
   *   <li>false: (Default) Enables the individual mode, which means one audio or video file for an uid.
   *       The bitrate and audio channel number of the recording file are the same as those of the original audio stream.</li>
   * </ul>
   */
  public boolean isMixingEnabled;

  /** If you set {@link io.agora.recording.common.RecordingConfig#isMixingEnabled isMixingEnabled} as true, {@link io.agora.recording.common.RecordingConfig#mixedVideoAudio mixedVideoAudio} allows you to mix audio and video in real time.
   * For details, see {@link io.agora.recording.common.Common#MIXED_AV_CODEC_TYPE MIXED_AV_CODEC_TYPE}.
   */
  public MIXED_AV_CODEC_TYPE mixedVideoAudio;

  /** If you set {@link io.agora.recording.common.RecordingConfig#isMixingEnabled isMixingEnabled} as true, {@link io.agora.recording.common.RecordingConfig#mixResolution mixResolution} allows you to set the resolution
   * in the format of width, height, fps, and Kbps; representing the width, height, frame
   * rate, and bitrate of the video stream.
   *
   * **Resolution, Frame Rate and Bitrate Table**
   * <table>
   *     <tr>
   *         <th>Resolution</th>
   *         <th>Frame Rate(fps)</th>
   *         <th>Base Bitrate (Kbps, for Communication)</th>
   *         <th>Live Bitrate (Kbps, for Live Broadcast)</th>
   *     </tr>
   *     <tr>
   *         <td>160 &times; 120</td>
   *         <td>15</td>
   *         <td>65</td>
   *         <td>130</td>
   *     </tr>
   *     <tr>
   *         <td>120 &times; 120</td>
   *         <td>15</td>
   *         <td>50</td>
   *         <td>100</td>
   *     </tr>
   *     <tr>
   *         <td>320 &times; 180</td>
   *         <td>15</td>
   *         <td>140</td>
   *         <td>280</td>
   *     </tr>
   *     <tr>
   *         <td>180 &times; 180</td>
   *         <td>15</td>
   *         <td>100</td>
   *         <td>200</td>
   *     </tr>
   *     <tr>
   *         <td>240 &times; 180</td>
   *         <td>15</td>
   *         <td>120</td>
   *         <td>240</td>
   *     </tr>
   *     <tr>
   *         <td>320 &times; 240</td>
   *         <td>15</td>
   *         <td>200</td>
   *         <td>400</td>
   *     </tr>
   *     <tr>
   *         <td>240 &times; 240</td>
   *         <td>15</td>
   *         <td>140</td>
   *         <td>280</td>
   *     </tr>
   *     <tr>
   *         <td>424 &times; 240</td>
   *         <td>15</td>
   *         <td>220</td>
   *         <td>440</td>
   *     </tr>
   *     <tr>
   *         <td>640 &times; 360</td>
   *         <td>15</td>
   *         <td>400</td>
   *         <td>800</td>
   *     </tr>
   *     <tr>
   *         <td>360 &times; 360</td>
   *         <td>15</td>
   *         <td>260</td>
   *         <td>520</td>
   *     </tr>
   *     <tr>
   *         <td>640 &times; 360</td>
   *         <td>30</td>
   *         <td>600</td>
   *         <td>1200</td>
   *     </tr>
   *     <tr>
   *         <td>360 &times; 360</td>
   *         <td>30</td>
   *         <td>400</td>
   *         <td>800</td>
   *     </tr>
   *     <tr>
   *         <td>480 &times; 360</td>
   *         <td>15</td>
   *         <td>320</td>
   *         <td>640</td>
   *     </tr>
   *     <tr>
   *         <td>480 &times; 360</td>
   *         <td>30</td>
   *         <td>490</td>
   *         <td>980</td>
   *     </tr>
   *     <tr>
   *         <td>640 &times; 480</td>
   *         <td>15</td>
   *         <td>500</td>
   *         <td>1000</td>
   *     </tr>
   *     <tr>
   *         <td>480 &times; 480</td>
   *         <td>15</td>
   *         <td>400</td>
   *         <td>800</td>
   *     </tr>
   *     <tr>
   *         <td>640 &times; 480</td>
   *         <td>30</td>
   *         <td>750</td>
   *         <td>1500</td>
   *     </tr>
   *     <tr>
   *         <td>480 &times; 480</td>
   *         <td>30</td>
   *         <td>600</td>
   *         <td>1200</td>
   *     </tr>
   *     <tr>
   *         <td>848 &times; 480</td>
   *         <td>15</td>
   *         <td>610</td>
   *         <td>1220</td>
   *     </tr>
   *     <tr>
   *         <td>848 &times; 480</td>
   *         <td>30</td>
   *         <td>930</td>
   *         <td>1860</td>
   *     </tr>
   *     <tr>
   *         <td>640 &times; 480</td>
   *         <td>10</td>
   *         <td>400</td>
   *         <td>800</td>
   *     </tr>
   *     <tr>
   *         <td>1280 &times; 720</td>
   *         <td>15</td>
   *         <td>1130</td>
   *         <td>2260</td>
   *     </tr>
   *     <tr>
   *         <td>1280 &times; 720</td>
   *         <td>30</td>
   *         <td>1710</td>
   *         <td>3420</td>
   *     </tr>
   *     <tr>
   *         <td>960 &times; 720</td>
   *         <td>15</td>
   *         <td>910</td>
   *         <td>1820</td>
   *     </tr>
   *     <tr>
   *         <td>960 &times; 720</td>
   *         <td>30</td>
   *         <td>1380</td>
   *         <td>2760</td>
   *     </tr>
   * </table>
   *
   */
  public String mixResolution;

  /** When the whole channel is encrypted, the recording SDK uses decryptionMode to enable the built-in decryption function:
   * <ul>
   *   <li>“aes-128-xts”: AES-128, XTS mode</li>
   *   <li>“aes-128-ecb”: AES-128, ECB mode</li>
   *   <li>“aes-256-xts”: AES-256, XTS mode</li>
   * </ul>
   */
  public String decryptionMode;

  /** The decryption password when decryption mode is enabled. The default value is NULL. */
  public String secret;

  /** The directory of AgoraCoreService. The default value is NULL. */
  public String appliteDir;

  /** The root directory of the recording files. The default value is NULL. The sub-path will be generated automatically.
   */
  public String recordFileRootDir;

  /** The path of the configuration file. The default value is NULL. In the configuration file, you can set the absolute
   * path of the recorded file, but the sub-path will not be generated automatically. The content in the configuration
   * file must be in JSON format, such as {“Recording_Dir” : “<recording path>”}. “Recording_Dir” can not be changed.
   */
  public String cfgFilePath;

  //decodeVideo: default 0 (0:save as file, 1:h.264, 2:yuv, 3:jpg buffer, 4:jpg file, 5:jpg file and video file)
  /** Video decoding format. See {@link io.agora.recording.common.Common#VIDEO_FORMAT_TYPE VIDEO_FORMAT_TYPE}.
  * @note
  * <ul>
  *   <li>When {@link io.agora.recording.common.Common#VIDEO_FORMAT_TYPE VIDEO_FORMAT_TYPE} = 1, 2, 3 or 4, {@link io.agora.recording.common.RecordingConfig#isMixingEnabled isMixingEnabled} cannot be set as true.</li>
  *   <li>When the video is decoded into raw video data, that is, {@link io.agora.recording.common.Common#VIDEO_FORMAT_TYPE VIDEO_FORMAT_TYPE} = 1, 2, 3 or 5, raw video data in YUV format for the Web SDK is supported while H.264 format is not supported.</li>
  * </ul>
  */
  public VIDEO_FORMAT_TYPE decodeVideo;

  //decodeAudio:  (default 0 (0:save as file, 1:aac frame, 2:pcm frame, 3:mixed pcm frame) (Can't combine with isMixingEnabled) /option)
  /** Audio decoding format. See {@link io.agora.recording.common.Common#AUDIO_FORMAT_TYPE AUDIO_FORMAT_TYPE}.
  * @note
  * When {@link io.agora.recording.common.Common#AUDIO_FORMAT_TYPE AUDIO_FORMAT_TYPE} = 1, 2 or 3, {@link io.agora.recording.common.RecordingConfig#isMixingEnabled isMixingEnabled} cannot be set as true.
  */
  public AUDIO_FORMAT_TYPE decodeAudio;

  /** The lowest UDP port. The default value is 0. Ensure that the value of highUdpPort - lowUdpPort ≥ 4. */
  public int lowUdpPort;

  /** The highest UDP port. The default value is 0. Ensure that the value of highUdpPort - lowUdpPort ≥ 4. */
  public int highUdpPort;

  /** The Agora Recording SDK automatically stops recording when there is no user
   * in the recorded channel after a time period of idleLimitSec. The value must
   * be ≥ three seconds. The default value is 300 seconds.
   */
  public int idleLimitSec;

  /** Time interval of the screen capture. The value ranges between one second and
   * five seconds. You need to use captureInterval with {@link io.agora.recording.common.RecordingConfig#decodeVideo decodeVideo} = 3, 4 or 5 when joining
   * the channel. See {@link RecordingSDK#createChannel() createChannel} to allow a user to join a channel.
   */
  public int captureInterval;

  /** Whether or not to detect the speakers. It is disabled by default.
   * <ul>
   *   <li>≤ 0: Disables detecting the speakers.</li>
   *   <li>> 0: The time interval (ms) of detecting the speakers, which should be longer than 200 ms. When an active speaker
   *         is found, the SDK returns the user ID of the speaker in the {@link RecordingEventHandler#onActiveSpeaker onActiveSpeaker} callback.</li>
   * </ul>
   */
  public int audioIndicationInterval;

  //channelProfile:0 communicate, 1:braodacast; default is 0
  /** Sets the channel mode. See {@link io.agora.recording.common.Common#CHANNEL_PROFILE_TYPE CHANNEL_PROFILE_TYPE}. */
  public CHANNEL_PROFILE_TYPE channelProfile;

  //streamType:0:get high stream 1:get low stream; default is 0
  /** streamType takes effect only when the Agora Native SDK or Web SDK has enabled the dual-stream
   * mode (high stream by default). See {@link io.agora.recording.common.Common#REMOTE_VIDEO_STREAM_TYPE REMOTE_VIDEO_STREAM_TYPE}.
   */
  public REMOTE_VIDEO_STREAM_TYPE streamType;

  /** Sets whether to record automatically or manually upon joining the channel:
   * <ul>
   *   <li>0: Automatically</li>
   *   <li>1: Manually</li>
   * </ul>
   * If you wish to call {@link RecordingSDK#startService() startService} and {@link RecordingSDK#stopService() stopService}, then choose Manually.
   */
  public int triggerMode;

  /** Deploy the Enterprise Proxy. It allows you to record the content with the Intranet server.
   * For details, please contact <a href="mailto:ssles@agora.io">support@agora.io</a>.
   */
  public String proxyServer; //format ipv4:port

  /** Audio profile of the recording file:
  * <ul>
  *   <li>AUDIO_PROFILE_DEFAULT = 0: (Default) Sampling rate of 48 kHz, communication encoding, mono, and a bitrate of up to 48 Kbps.</li>
  *   <li>AUDIO_PROFILE_MUSIC_HIGH_QUALITY = 1: Sampling rate of 48 kHz, music encoding, mono, and a bitrate of up to 128 Kbps.</li>
  *   <li>AUDIO_PROFILE_MUSIC_HIGH_QUALITY_STEREO = 2: Sampling rate of 48 kHz, music encoding, stereo, and a bitrate of up to 192 Kbps.</li>
  * </ul>
   */
  public int audioProfile;

  /** The default background image of the canvas. */
  public String defaultVideoBgPath;

  /** The default background image of the user. The background image is displayed when a user is online but not sending out any video stream.
  * @note
  * If the user uses Agora Web SDK, the user sends the audio and video stream all the time. In this case, the background image is not displayed.
  */
  public String defaultUserBgPath;
}
