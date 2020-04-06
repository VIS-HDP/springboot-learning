package com.example.ffmpeg.util;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class FfmpegCommandBuilder {
	public static final String RESOLUTION_SUPERDEFINITION = "spd";
	public static final String RESOLUTION_HIGHDEFINITION = "hd";
	public static final String RESOLUTION_SIMPLEDEFINITION = "smd";
	public static final int WATERMARKPOSITION_LEFTTOP = 1;
	public static final int WATERMARKPOSITION_RIGHTTOP = 2;
	public static final int WATERMARKPOSITION_LEFTBOTTOM = 3;
	public static final int WATERMARKPOSITION_RIGHTBOTTOM = 4;
 
	public FfmpegCommandBuilder() {
	}
 
	public static enum VideoWatermarkPosition {
		LeftTop, RightTop, LeftBottom, RightBottom;
 
		public static VideoWatermarkPosition getValue(int position) {
			switch (position) {
			case 1:
				return LeftTop;
			case 2:
				return RightTop;
			case 3:
				return LeftBottom;
			case 4:
				return RightBottom;
			}
			return LeftTop;
		}
	}
 
	public static enum VideoResolution {
		SuperDefinition, HighDefinition, SimpleDefinition;
 
		public static VideoResolution getValue(String resolution) {
			if ("spd".equalsIgnoreCase(resolution))
				return SuperDefinition;
			if ("hd".equalsIgnoreCase(resolution)) {
				return HighDefinition;
			}
			return SimpleDefinition;
		}
	}
 
	private List<String> commands = new ArrayList<String>();
 
	public List<String> getCommandList() {
		return this.commands;
	}
 
	public void addFfmpegCommand() {
		addFfmpegCommand(null);
	}
 
	public void addFfmpegCommand(String ffmpegPath) {
		String filename = "ffmpeg";
		if (System.getProperty("os.name").toLowerCase().indexOf("windows") >= 0) {
			if (StringUtils.isEmpty(ffmpegPath)) {
				filename = "\"" + System.getProperty("user.dir") + "/tools/ffmpeg.exe\"";
				//filename = "\"" + PathKit.getWebRootPath() + "/tools/ffmpeg.exe\"";
			} else {
				filename = ffmpegPath;
			}
		}
		this.commands.add(filename);
	}
 
	public void addCommand(String command) {
		this.commands.add(command);
	}
 
	public void addInputFileCommand(String filePath) {
		this.commands.add("-i");
		this.commands.add(filePath);
	}
 
	public void addFormatCommand(String format) {
		this.commands.add("-f");
		this.commands.add(format);
	}
 
	public void addOutputFileCommand(String filePath) {
		this.commands.add(filePath);
	}
 
	public void addOverwriteCommand() {
		this.commands.add("-y");
	}
 
	public void addSimpleAudioCommand() {
		this.commands.add("-acodec");
		this.commands.add("libvo_aacenc");
 
		this.commands.add("-ab");
		this.commands.add("128");
 
		this.commands.add("-ac");
		this.commands.add("1");
 
		this.commands.add("-ar");
		this.commands.add("22050");
	}
 
	public void addVideoBitRateCommand(long bitrate) {
		this.commands.add("-b");
		this.commands.add(bitrate + "k");
	}
 
	public void addVideoConvertCommand(VideoResolution vr) {
		this.commands.add("-vcodec");
		this.commands.add("h264");
 
		this.commands.add("-r");
		this.commands.add("25");
 
		if (VideoResolution.SuperDefinition == vr) {
			this.commands.add("-qscale");
			this.commands.add("4");
		} else if (VideoResolution.HighDefinition == vr) {
			addVideoBitRateCommand(768L);
		} else {
			addVideoBitRateCommand(384L);
		}
	}
 
	public void addWatermarkCommand(String imageName, VideoWatermarkPosition position) {
		if (StringUtils.isEmpty(imageName)) {
			return;
		}
		String positionParam = "";
		if (position == VideoWatermarkPosition.RightTop)
			positionParam = "movie=" + imageName + " [watermark]; [in][watermark] overlay=main_w-overlay_w-10:10 [out]";
		if (position == VideoWatermarkPosition.LeftBottom)
			positionParam = "movie=" + imageName + " [watermark]; [in][watermark] overlay=10:main_h-overlay_h-10 [out]";
		if (position == VideoWatermarkPosition.RightBottom) {
			positionParam = "movie=" + imageName + " [watermark]; [in][watermark] overlay=main_w-overlay_w-10:main_h-overlay_h-10 [out]";
		} else {
			positionParam = "movie=" + imageName + " [watermark]; [in][watermark] overlay=10:10 [out]";
		}
		this.commands.add("-vf");
		this.commands.add(positionParam);
	}
 
	public void addCaptureCommand(int captureSecond, int width, int height) {
		this.commands.add("-r");
		this.commands.add("1");
 
		this.commands.add("-f");
		this.commands.add("image2");
 
		this.commands.add("-ss");
		int s = captureSecond % 60;
		int m = captureSecond / 60 % 60;
		int h = captureSecond / 60 / 60 % 60;
		this.commands.add(h + ":" + m + ":" + s);
 
		this.commands.add("-t");
		this.commands.add("00:00:01");
 
		this.commands.add("-s");
		if (width == 0) {
			width = 320;
		}
		if (height == 0) {
			height = 240;
		}
		this.commands.add(width + "x" + height);
	}
 
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (String command : this.commands) {
			sb.append(command).append(" ");
		}
		return sb.toString();
	}

	public static boolean interceptVideo(String src, String dest,String start,String time) {
		FfmpegCommandBuilder fcb = new FfmpegCommandBuilder();
		fcb.addFfmpegCommand();
		fcb.addCommand("-ss");
		fcb.addCommand(start);
		fcb.addCommand("-t");
		fcb.addCommand(time);
		fcb.addInputFileCommand(src);
		fcb.addCommand("-vcodec");
		fcb.addCommand("copy");
		//fcb.addCommand("h264");  //指定编码为播放器可支持的编码  无特殊要求填copy  copy表示复制原编码
		fcb.addCommand("-acodec");
		fcb.addCommand("copy");
		fcb.addCommand(dest);
		//execute(fcb.getCommandList(), src, dest, false, event);
		return true;
	}

}