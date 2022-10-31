# 工程简介

- 使用MinIO搭建本地S3开发服务器
- 使用minio-java SDK开发上传MinIO的服务供客户端使用

### 运行示例

上传到公开Bucket：

```shell
$ curl --location --request POST 'http://127.0.0.1:8080/upload?public=true' \
> --form 'file=@"./im_file_storage_migrate.png"'
{
  "msg": "https://127.0.0.1:9001/baby-public/baby-public_1667205282570.png",
  "code": 1,
  "minio_response": "io.minio.ObjectWriteResponse@33ee0091"
}
```

上传到私有Bucket：

```shell
$ curl --location --request POST 'http://127.0.0.1:8080/upload?public=false'  --form 'file=@"./baby-release_plan.png"'
{
"msg": "https://127.0.0.1:9001/baby-private/baby-private_1667206122422.png",
"code": 1,
"minio_response": "io.minio.ObjectWriteResponse@163bbbff"
}
```

### 常见问题

MinIO服务使用自签名TLS证书会导致上传失败，
参考[SSLPoke_README.txt](tools/SSLPoke_README.txt)将自签名证书导入JVM Key store解决该问题。
