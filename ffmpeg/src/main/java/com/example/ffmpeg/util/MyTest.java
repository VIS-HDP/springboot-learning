package com.example.ffmpeg.util;

import com.sun.imageio.plugins.common.ImageUtil;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacv.*;

import java.io.*;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;


/**
 * @Author: robingao robin
 * Date: 2020/4/6 13:41
 * @Version: since v1.0
 * @Description:
 */
public class MyTest {

    public static void main(String[] args) {
        String parent = "E:\\2222222222222222222222222\\Oxford-Reading-Tree-ximalaya\\201-400";
        File file = new File(parent);
        String[] list = file.list();
        for(String fileName : list){
            if(fileName.contains("【正课 】")){
                System.out.println(fileName);
                MyTest myTest = new MyTest();
                myTest.splitVideo(parent+"\\"+fileName);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
/*        String filePath = "D:\\1-test-video\\【正课】Go Away, Floppy-双语讲解【加入宝贝学堂微信bbxt1225】.mp4";
        MyTest myTest = new MyTest();
        myTest.splitVideo(filePath);*/
    }

    /** ffmpeg -ss 00:00:00 -t 00:00:30 -i test.mp4 -vcodec copy -acodec copy output.mp4
     **/
    public void splitVideo(String oldFilePath){

        try{
            List<String> command = new ArrayList<>();
            command.add("D:\\ffmpeg\\bin\\ffmpeg.exe");
            command.add("-ss");
            command.add("00:00:30");
            command.add("-t");
            command.add("01:00:00");
            command.add("-i");
            command.add(oldFilePath);
            command.add("-vcodec");
            command.add("copy");
            command.add("-acodec");
            command.add("copy");
            String[] old = oldFilePath.split("\\.");
            System.out.println(old.length);
            String newFileName = old[0]+"-simple-cut."+old[1];
            command.add(newFileName);

            Process videoProcess = new ProcessBuilder(command).redirectErrorStream(true).start();

            InputStream errorStream = videoProcess.getErrorStream();
            InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
            BufferedReader br = new BufferedReader(inputStreamReader);

            String line = "";
            while ( (line = br.readLine()) != null ) {
            }

            if (br != null) {
                br.close();
            }
            if (inputStreamReader != null) {
                inputStreamReader.close();
            }
            if (errorStream != null) {
                errorStream.close();
            }
            //videoProcess.waitFor();
            System.out.println("err="+line);
        }catch (Exception e){
            e.printStackTrace();
        }

    }





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
