package com.gongpengjun;

import com.alibaba.fastjson.JSONObject;
import io.minio.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Slf4j
@Component
public class MinioUtils {
    @Autowired
    private MinioClient minioClient;
    @Autowired
    private MinioProp minioProp;

    /**
     * 创建bucket
     *
     * @param bucketName bucket名称
     */
    @SneakyThrows
    public void checkOrCreateBucket(String bucketName) {
        boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (!found) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        } else {
            log.warn("Bucket {} already exists.", bucketName);
        }
    }

    /**
     * 上传文件
     *
     * @param file       文件
     * @param isPublicBucket 是否上传到公开桶
     * @return json
     */
    public JSONObject uploadFile(Boolean isPublicBucket, MultipartFile file) {
        String bucketName = isPublicBucket ? minioProp.getPublicBucketName() : minioProp.getPrivateBucketName();
        JSONObject res = new JSONObject();
        res.put("code", 0);
        // 判断上传文件是否为空
        if (null == file || 0 == file.getSize()) {
            res.put("msg", "上传文件不能为空");
            return res;
        }
        try {
            // 判断存储桶是否存在
            checkOrCreateBucket(bucketName);
            // 文件名
            String originalFilename = file.getOriginalFilename();
            // 新的文件名 = 存储桶名称_时间戳.后缀名
            assert originalFilename != null;
            String fileName = bucketName + "_" + System.currentTimeMillis() + originalFilename.substring(originalFilename.lastIndexOf("."));

            // Upload unknown sized input stream.
            InputStream inputStream = file.getInputStream();
            ObjectWriteResponse response = minioClient.putObject(
                            PutObjectArgs.builder()
                                    .bucket(bucketName)
                                    .object(fileName)
                                    .stream(inputStream, -1, 10485760)
                                    .contentType("image/png")
                                    .build());
            res.put("code", 1);
            res.put("msg", minioProp.getEndpoint() + "/" + bucketName + "/" + fileName);
            res.put("minio_response", response.toString());
            return res;
        }  catch (Exception e) {
            log.error("上传文件失败：{}", e.getMessage());
        }

        res.put("msg", "上传失败");
        return res;
    }
}
