package com.springboot.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.springboot.config.OssProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;

import javax.sql.rowset.serial.SerialBlob;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * oss 图片上传简单工具
 */
public class OssUtil {
    private static  Logger logger = LoggerFactory.getLogger(OssUtil.class);
    /**
     *  上传图片到阿里云oss
     * @param newFile
     * @return
     */
    public static String upload(File newFile){
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = OssProperties.OSS_END_POINT;
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，
        String accessKeyId = OssProperties.OSS_ACCESS_KEY_ID;
        String accessKeySecret = OssProperties.OSS_ACCESS_KEY_SECRET;
        String bucketName = OssProperties.OSS_BUCKET_NAME1;
        logger.info("=========>开始上传");
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String datePath = dateFormat.format(new Date());
        String fileName = null;
        String filePath = null;
        logger.info("=========>ossClient创建");
        try{
            fileName = newFile.getName();
            filePath= datePath+"/"+fileName ;

            ObjectMetadata metadata = new ObjectMetadata();
            //metadata.setContentType("");
            metadata.setContentLength(newFile.length());
            metadata.setContentEncoding("utf-8");
            //设置公共读取权限
            metadata.setObjectAcl(CannedAccessControlList.PublicRead);

            // 上传文件。buketname , filename , file
            PutObjectResult result = ossClient.putObject(bucketName, filePath, newFile,metadata);
            logger.info("=========>文件上传成功====etag="+result.getETag());
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            ossClient.shutdown();
            logger.info("=========>ossClient关闭");
        }
        String imgUrl = "http://"+bucketName+"."+endpoint+"/"+filePath ;
        logger.info("imgUrl="+imgUrl);
        return imgUrl;
    }
    public static SerialBlob decodeToImage(String imageString) throws Exception {
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] imageByte = decoder.decodeBuffer(imageString);
        return new SerialBlob(imageByte);
    }

    public  static InputStream decodeToInputStream(String imageString) throws Exception {
        String[] content = imageString.split(",");
        SerialBlob serialBlob = decodeToImage(content[1]);
        return serialBlob.getBinaryStream();
    }

    /**
     *  上传图片到阿里云oss
     * @param
     * @return
     */
    public static String base64StruploadOSS(InputStream inputStream,String name){
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = OssProperties.OSS_END_POINT;
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，
        String accessKeyId = OssProperties.OSS_ACCESS_KEY_ID;
        String accessKeySecret = OssProperties.OSS_ACCESS_KEY_SECRET;
        String bucketName = OssProperties.OSS_BUCKET_NAME1;
        logger.info("=========>开始上传");
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String datePath = dateFormat.format(new Date());
        String fileName = null;
        String filePath = null;
        logger.info("=========>ossClient创建");
        try{

            if(fileName ==null){
                fileName = name;
            }else{
                fileName = UUID.randomUUID().toString()+".jpg";
            }

            filePath= datePath+"/"+fileName ;

            ObjectMetadata metadata = new ObjectMetadata();
            //metadata.setContentType("");
            //metadata.setContentLength();
            metadata.setContentEncoding("utf-8");
            //设置公共读取权限
            metadata.setObjectAcl(CannedAccessControlList.PublicRead);
            // 上传文件。buketname , filename , file
            PutObjectResult result = ossClient.putObject(bucketName, filePath, inputStream,metadata);
            logger.info("=========>文件上传成功====etag="+result.getETag());
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            ossClient.shutdown();
            logger.info("=========>ossClient关闭");
        }
        String imgUrl = "http://"+bucketName+"."+endpoint+"/"+filePath ;
        logger.info("imgUrl="+imgUrl);
        return imgUrl;
    }

    public static void main(String[] args) {

    }
}
