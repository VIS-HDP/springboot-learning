package com.example.ffmpeg.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FfmpegService {


    public void convertor(String inputPath,String outPath)throws Exception{
        List<String> command = new ArrayList<>();
        command.add("ffmpegEXE");
        command.add("-i");
        command.add(inputPath);
        command.add(outPath);


        ProcessBuilder process  =  new ProcessBuilder(command);
        process.start();
    }

    public static void main(String[] args) throws Exception {
        FfmpegService service = new FfmpegService();
        service.convertor("","");
    }
}
