package com.example.ffmpeg.util;

import com.sun.imageio.plugins.common.ImageUtil;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacv.*;

import java.nio.IntBuffer;

import static org.bytedeco.javacpp.opencv_imgproc.CV_BGR2RGBA;
import static org.bytedeco.javacpp.opencv_imgproc.cvCvtColor;

/**
 * @Author: robingao robin
 * Date: 2020/4/6 13:41
 * @Version: since v1.0
 * @Description:
 */
public class MyTest {


    public void test() throws FrameRecorder.Exception, FrameGrabber.Exception {
        String mMvPath = "D:\\111\\117.mp4";
        FFmpegFrameGrabber mGrabber = new FFmpegFrameGrabber("");

        String fileName = mMvPath.replace(".mp4", "_edited.mp4");
        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(fileName, mGrabber.getImageWidth(), mGrabber.getImageHeight(),mGrabber.getAudioChannels());
        recorder.setVideoCodec(mGrabber.getVideoCodec()); //avcodec.AV_CODEC_ID_H264  //AV_CODEC_ID_MPEG4
        recorder.setFormat(mGrabber.getFormat());
        recorder.setFrameRate(mGrabber.getFrameRate());
        recorder.setSampleFormat(mGrabber.getSampleFormat());  //
        recorder.setSampleRate(mGrabber.getSampleRate());
        recorder.setFrameRate(mGrabber.getFrameRate());
        recorder.start();


/*        while (true) {
            Frame frame = mGrabber.grabFrame();

            if (frame == null)
                break;

            opencv_core.IplImage img = frame.image;//保存BMP
            cvCvtColor(img,tempImage,CV_BGR2RGBA);
            IntBuffer intBuf = tempImage.getIntBuffer();
            bmp.copyPixelsFromBuffer(intBuf);
            ImageUtil.saveBitmap(bmp);

            recorder.record(img);//录制
        }*/

        recorder.stop();
        recorder.release();
        mGrabber.stop();

/*
        if (mEvent != null){
            mEvent.onDecordFinish();
        }
*/
    }

}
