package com.gongpengjun;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "minio")
public class MinioProp {
    private String endpoint;  // MinIO base url: http://127.0.0.1:9001
    private String accessKey; // MinIO username: minioadmin
    private String secretKey; // MinIO password: minioadmin
    private String publicBucketName;  //  public bucket name: baby-public
    private String privateBucketName; // private bucket name: baby-private
}
