package com.springboot.controller;

import com.springboot.oss.OssUtil;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("/upload")
public class OssUploadController {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 文件上传
     * @param file
     */
    @RequestMapping(value = "/oss",method = RequestMethod.POST)
    @ResponseBody
    public String uploadBlog(MultipartFile file){

        logger.info("==========>进入文件上传");
        String uploadUrl = null;
        try {
            if(null != file){
                String filename = file.getOriginalFilename();
                if(!"".equals(filename.trim())){
                    File newFile = new File(filename);
                    FileOutputStream os = new FileOutputStream(newFile);
                    os.write(file.getBytes());
                    os.close();
                    file.transferTo(newFile);
                    //上传到OSS
                    uploadUrl = OssUtil.upload(newFile);

                }

            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return uploadUrl;
    }

    @RequestMapping("/toUpload")
    public String toUpload(){
        return "upload";
    }
    @RequestMapping("/direcUpload")
    public String direcUpload(){
        return "index";
    }


    /**
     * 方法描述:文件上传 ,未经过测试。。。。
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/multiUpload", method = { RequestMethod.POST })
    @ResponseBody
    public List<String> upload(HttpServletRequest request, HttpServletResponse response) {
        List<String> resultModel = new ArrayList<>();
        try {
            // 创建一个通用的多部分解析器
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
            List<String> files = new ArrayList<>();
            // 判断 request 是否有文件上传,即多部分请求
            if (multipartResolver.isMultipart(request)) {
                // 转换成多部分request
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
                // 取得request中的所有文件名
                Iterator<String> iter = multiRequest.getFileNames();
                while (iter.hasNext()) {
                    // 取得上传文件
                    MultipartFile file = multiRequest.getFile(iter.next());
                    if (file != null) {
                        // 取得当前上传文件的文件名称
                        String fileName = file.getOriginalFilename();
                        // 如果名称不为空,说明该文件存在，否则说明该文件不存在
                        if (fileName.trim() != "") {
                            File newFile = new File(fileName);
                            FileOutputStream outStream = new FileOutputStream(newFile); // 文件输出流用于将数据写入文件
                            outStream.write(file.getBytes());
                            outStream.close(); // 关闭文件输出流
                            file.transferTo(newFile);
                            // 上传到阿里云
                            files.add(OssUtil.upload(newFile));
                            newFile.delete();
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return resultModel;
    }



}
