const AgoraRecordingSDK = require("../record/AgoraRecordSdk");
const path = require("path");
const fs = require("fs");
const uuidv4 = require('uuid/v4');
const awsCli = require('aws-cli-js');
class RecordManager{
    constructor() {
        this.recorders = {};
        //initialize output folder
        const output = path.resolve(__dirname, "./output");
        if (!fs.existsSync(output)){
            fs.mkdirSync(output);
        }
    }

    //find existing recorder
    find(sid) {
        return this.recorders[sid];
    }

    initStorage(appid, channel, sid) {
        return new Promise((resolve, reject) => {
            const storagePath = path.resolve(__dirname, `./output/${sid}`);
            fs.mkdir(storagePath, {recursive: true}, err => {
                if(err){
                    throw err;
                }
                resolve(storagePath);
            });
        })
    }

//    start(key, appid, channel) {
//        return new Promise((resolve, reject) => {
//            const sid = uuidv4();
//            this.initStorage(appid, channel, sid).then(storagePath => {
//                let sdk = new AgoraRecordingSDK();
//
//                let layout = {
//                    "canvasWidth": 1280,
//                    "canvasHeight": 720,
//                    "backgroundColor": "#000000",
//                    "regions": []
//                }
//                let recorder = {
//                    appid: appid,
//                    channel: channel,
//                    sdk: sdk,
//                    sid: sid,
//                    layout: layout
//                };
//                sdk.setMixLayout(layout);
//
//                sdk.joinChannel(key || null, channel, 0, appid, storagePath).then(() => {
//                    this.subscribeEvents(recorder);
//                    this.recorders[sid] = recorder;
//                    console.log(`recorder started ${appid} ${channel} ${sid}`)
//                    resolve(recorder);
//                }).catch(e => {
//                    reject(e);
//                });
//            });
//        });
//    }
//
//    subscribeEvents(recorder) {
//        let { sdk, sid, appid, channel } = recorder;
//        sdk.on("error", (err, stat) => {
//            console.error(`sdk stopped due to err code: ${err} stat: ${stat}`);
//            console.log(`stop recorder ${appid} ${channel} ${sid}`)
//            //clear recorder if error received
//            this.onCleanup(sid)
//        });
//        sdk.on("userleave", (uid) => {
//            console.log(`user leave ${uid}`);
//            //rearrange layout when user leaves
//
//            let recorder = this.find(sid);
//
//            if(!recorder) {
//                console.error("no reocrder found");
//                return;
//            }
//            let {layout} = recorder;
//            layout.regions = layout.regions.filter((region) => {
//                return region.uid !== uid
//            })
//            sdk.setMixLayout(layout);
//        });
//        sdk.on("userjoin", (uid) => {
//            //rearrange layout when new user joins
//            console.log(`user join ${uid}`);
//            let region = {
//                "x": 0,
//                "y": 0,
//                "width": 1280,
//                "height": 720,
//                "zOrder": 1,
//                "alpha": 1,
//                "uid": uid
//            }
//            let recorder = this.find(sid);
//
//            if(!recorder) {
//                console.error("no reocrder found");
//                return;
//            }
//
//            let {layout} = recorder;
//            switch(layout.regions.length) {
//                case 0:
//                    region.x = 0;
//                    region.y = 0;
//                    break;
//                case 1:
//                    region.x = 1280;
//                    region.y = 0;
//                    break;
//                case 2:
//                    region.x = 0;
//                    region.y = 720;
//                    break;
//                case 3:
//                    region.x = 1280;
//                    region.y = 720;
//                default:
//                    break;
//            }
//            layout.regions.push(region)
//            sdk.setMixLayout(layout);
//        });
//    }

   
start(key, appid, channel,streamType) {
        return new Promise((resolve, reject) => {
            const sid = uuidv4();
            this.initStorage(appid, channel, sid,streamType).then(storagePath => {
 		let sdk = new AgoraRecordingSDK();
                
                let layout = {}
console.log("************"+streamType);
                if (streamType == "0"){
                    layout = {
                        "canvasWidth": 720,
                        "canvasHeight": 1280,
                        "backgroundColor": "#000000",
                        "regions": []
                    }
                } else if (streamType == "1") {
                    layout = {
                        "canvasWidth": 1280,
                        "canvasHeight": 720,
                        "backgroundColor": "#000000",
                        "regions": []
                    }
                } else {
                    layout = {
                        "canvasWidth": 1280,
                        "canvasHeight": 720,
                        "backgroundColor": "#000000",
                        "regions": []
                    }
                }
                let recorder = {
                    appid: appid,
                    channel: channel,
                    sdk: sdk,
                    sid: sid,
                    layout: layout,
                    streamType: streamType
                };
                sdk.setMixLayout(layout);
console.log(layout);
console.log("1111111111"+streamType);
                sdk.joinChannel(key || null, channel, 0, appid, storagePath,streamType).then(() => {
		    this.subscribeEvents(recorder);
                    this.recorders[sid] = recorder;
                    console.log(`recorder started ${appid} ${channel} ${sid}`)
                    resolve(recorder);
                }).catch(e => {
                    reject(e);
                });
            });
        });
    }

 subscribeEvents(recorder) {
        let { sdk, sid, appid, channel, streamType } = recorder;
console.log("***********pppppp"+ streamType);        
	sdk.on("error", (err, stat) => {
            console.error(`sdk stopped due to err code: ${err} stat: ${stat}`);
            console.log(`stop recorder ${appid} ${channel} ${sid}`)
            //clear recorder if error received
            this.onCleanup(sid)
        });
        sdk.on("userleave", (uid) => {
            console.log(`user leave ${uid}`);
            //rearrange layout when user leaves

            let recorder = this.find(sid);

            if(!recorder) {
                console.error("no reocrder found");
                return;
            }
            let {layout} = recorder;
            layout.regions = layout.regions.filter((region) => {
                return region.uid !== uid
            })
            sdk.setMixLayout(layout);
        });
        sdk.on("userjoin", (uid) => {
            //rearrange layout when new user joins
            console.log(`user join ${uid}`);
                let region = {};
            if (streamType == "0"){
                region = {
                    "x": 0,
                    "y": 0,
                    "width": 720,
                    "height": 1280,
                    "zOrder": 1,
                    "alpha": 1,
                    "uid": uid
                }
                let recorder = this.find(sid);

            if(!recorder) {
                console.error("no reocrder found");
                return;
            }

            let {layout} = recorder;
            switch(layout.regions.length) {
                case 0:
                    region.x = 0;
                    region.y = 0;
                    break;
                case 1:
                    region.x = 720;
                    region.y = 0;
                    break;
                case 2:
                    region.x = 0;
                    region.y = 1280;
                    break;
                case 3:
                    region.x = 720;
                    region.y = 1280;
                default:
                    break;
            }
		console.log("++++++++++++"+region);
            layout.regions.push(region)
            sdk.setMixLayout(layout);
            } else if (streamType == "1") {
                region = {
                    "x": 0,
                    "y": 0,
                    "width": 1280,
                    "height": 720,
                    "zOrder": 1,
                    "alpha": 1,
                    "uid": uid
                }
                let recorder = this.find(sid);

                if(!recorder) {
                    console.error("no reocrder found");
                    return;
                }

                let {layout} = recorder;
                switch(layout.regions.length) {
                    case 0:
                        region.x = 0;
                        region.y = 0;
                        break;
                    case 1:
                        region.x = 1280;
                        region.y = 0;
                        break;
                    case 2:
                        region.x = 0;
                        region.y = 720;
                        break;
                    case 3:
                        region.x = 1280;
                        region.y = 720;
                    default:
                        break;
                }
                layout.regions.push(region)
                sdk.setMixLayout(layout);

            } else {
                region = {
                    "x": 0,
                    "y": 0,
                    "width": 1280,
                    "height": 720,
                    "zOrder": 1,
                    "alpha": 1,
                    "uid": uid
                }
                let recorder = this.find(sid);

                if(!recorder) {
                    console.error("no reocrder found");
                    return;
                }

                let {layout} = recorder;
                switch(layout.regions.length) {
                    case 0:
                        region.x = 0;
                        region.y = 0;
                        break;
                    case 1:
                        region.x = 1280;
                        region.y = 0;
                        break;
                    case 2:
                        region.x = 0;
                        region.y = 720;
                        break;
                    case 3:
                        region.x = 1280;
                        region.y = 720;
                    default:
                        break;
                }
                layout.regions.push(region)
                sdk.setMixLayout(layout);
            }
        });
    }
    stop(sid) {
        let recorder = this.recorders[sid];
        if(recorder) {
            let {appid, channel} = recorder;
            console.log(`stop recorder ${appid} ${channel} ${sid}`)
            this.onCleanup(sid);
        } else {
            throw new Error('recorder not exists');
        }
    }

    onCleanup(sid) {
        let recorder = this.recorders[sid];
        if(recorder) {
            let {sdk} = recorder;
            console.log(`releasing ${sid}`)
            sdk.leaveChannel()
            sdk.release()

       // var nowDate = new Date().getTime();
       // var timestamp = Date.parse(new Date());
       // timestamp = timestamp / 1000;

            // do record sid for deletion
        //    try {
          //      fs.appendFileSync(`./output/${sid}/readyToDelete.txt`, `${timestamp},${sid}\n`);
            //    console.log(`releasing ${sid}`);
             // } catch (err) {
              //  console.error(err)
             // }

            delete this.recorders[`${sid}`];
var Options = awsCli.Options;     
 var options = new Options(
        /* accessKey    */ "AKIAJWS7O4X7XYAJVUDQ",
        /* secretKey    */ "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"
      );

      // do aws uploading
var Aws = awsCli.Aws;      
var aws = new Aws(options);
      var syncCommand = `s3 sync /root/Dev/tempForRakuten/server/output/${sid} s3://rakuten.livestreaming.stg2/livestreamrecord/static/${sid}`;
      aws.command(syncCommand).then(function(data) {
        // upload successfully
        console.log('Succeed', data)
	var nowDate = new Date().getTime();
        var timestamp = Date.parse(new Date());
        timestamp = timestamp / 1000;

            // do record sid for deletion
            try {
                fs.appendFileSync(`/root/Dev/tempForRakuten/server/output/${sid}/readyToDelete.txt`, `${timestamp},${sid}\n`);
                console.log(`releasing ${sid}`);
              } catch (err) {
                console.error(err)
              }

      }).catch(function(err){
        console.error(err);
      })

        } else {
            throw new Error('recorder not exists');
        }
    }
}



module.exports = new RecordManager();
