package com.example.ffmpeg.util;


import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * @Author: robingao robin
 * Date: 2020/3/16 21:03
 * @Version: since v1.0
 * @Description:
 */
@Component
@Slf4j
public class VideoUtil {


    public static String getTempPath(String tempPath, String filePath) throws Exception {
//        String tempPath="    ";//保存的目标路径
        File targetFile = new File(tempPath);
        if (!targetFile.getParentFile().exists()) {
            targetFile.getParentFile().mkdirs();
        }
        File file2 = new File(filePath);
        if (file2.exists()) {
            //log.info("文件存在，路径正确！");
            FFmpegFrameGrabber ff = new FFmpegFrameGrabber(file2);
            ff.start();
            int ftp = ff.getLengthInFrames();
            int flag=0;
            Frame frame = null;
            while (flag <= ftp) {
                //获取帧
                frame = ff.grabImage();
                //过滤前3帧，避免出现全黑图片
                if ((flag>3)&&(frame != null)) {
                    break;
                }
                flag++;
            }
            ImageIO.write(FrameToBufferedImage(frame), "jpg", targetFile);
            ff.close();
            ff.stop();
        }
        return null;
    }
    private static RenderedImage FrameToBufferedImage(Frame frame) {
        //创建BufferedImage对象
        Java2DFrameConverter converter = new Java2DFrameConverter();
        BufferedImage bufferedImage = converter.getBufferedImage(frame);
        return bufferedImage;
    }

    public static void main(String[] args) {
        try {
            //----getTempPath("D:\\test.jpg","https://music.ojiajob.com/music/material/2020-03-16/bee4df39dfe148d1b6e3f123e9680fe2.mp4");
            getTempPath("D:\\111\\117-1.jpg", "D:\\111\\117.mp4");
            getTempPath("D:\\111\\119-1.jpg", "D:\\111\\119.mp4");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
