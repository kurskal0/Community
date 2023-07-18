package com.nowcoder.community.util;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class OSSUtil {

    @Value("${aliyun.oss.file.end-point}")
    private String endPoint;
    @Value("${aliyun.oss.file.access-key-id}")
    private String accessKeyId;
    @Value("${aliyun.oss.file.access-key-secret}")
    private String accessKeySecret;
    @Value("${aliyun.oss.file.bucket-name}")
    private String bucketName;
    @Value("${aliyun.oss.file.dir-name}")
    private String dirName;


    /**
     * 上传文件
     * @param objectName 文件名(带后缀)
     * @param file 文件
     * @return
     */
    public String uploadFile(String objectName , File file){
        objectName = dirName + "/" + objectName;

        // 创建OSSClient实例
        OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);

        try {
            ossClient.putObject(bucketName, objectName, file);
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
            return null;
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
            return null;
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }

        String filePath = "https://"
                + bucketName + "."
                + endPoint.substring(endPoint.lastIndexOf("/")+1)
                + "/" + objectName;


        return filePath;
    }
}
