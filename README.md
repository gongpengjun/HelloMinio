# 工程简介

- 使用MinIO搭建本地S3开发服务器
- 使用minio-java SDK开发上传MinIO的服务供客户端使用

### 运行示例

```shell
$ curl --location --request POST 'http://127.0.0.1:8080/upload?public=true' \
> --form 'file=@"./im_file_storage_migrate.png"'
{
  "msg": "https://127.0.0.1:9001/baby-public/baby-public_1667205282570.png",
  "code": 1,
  "minio_response": "io.minio.ObjectWriteResponse@33ee0091"
}
```
