# MinIO Server on Mac

[TOC]

### 1、参考文档

- http://docs.minio.org.cn/docs/
- https://min.io/docs/minio/macos/index.html macOS
- https://min.io/docs/minio/macos/operations/network-encryption.html
- https://www.digitalocean.com/community/tutorials/how-to-set-up-minio-object-storage-server-in-standalone-mode-on-ubuntu-20-04

### 2、下载安装

```shell
$ brew install minio/stable/minio
```

实际执行：

```shell
$ brew install minio/stable/minio
==> Tapping minio/stable
Cloning into '/usr/local/Homebrew/Library/Taps/minio/homebrew-stable'...
==> Useful links:
Command-line Access: https://docs.min.io/docs/minio-client-quickstart-guide
EXAMPLES:
  1. Start minio server on "/home/shared" directory.
     $ minio server /home/shared
  2. Start single node server with 64 local drives "/mnt/data1" to "/mnt/data64".
     $ minio server /mnt/data{1...64}
🍺  /usr/local/Cellar/minio/RELEASE.2022-10-29T06-21-33Z_1: 3 files, 104.3MB, built in 7 seconds
```

### 3、配置TLS证书

https://github.com/minio/certgen

安装certgen for mac

| OS    | ARCH  | Binary                                                       |
| ----- | ----- | ------------------------------------------------------------ |
| Apple | amd64 | [darwin-amd64](https://github.com/minio/certgen/releases/latest/download/certgen-darwin-amd64) |

下载Binary，放在/usr/local/bin目录下

```shell
gongpengjun@mbp ~$ ls -lrth /usr/local/bin/certgen-darwin-amd64
-rw-r--r--@ 1 gongpengjun  staff   2.8M 10 30 11:34 /usr/local/bin/certgen-darwin-amd64
gongpengjun@mbp ~$ ln -s /usr/local/bin/certgen-darwin-amd64 /usr/local/bin/certgen
gongpengjun@mbp ~$ chmod +x /usr/local/bin/certgen-darwin-amd64
gongpengjun@mbp ~$ ls -lrth /usr/local/bin/certge*
-rwxr-xr-x@ 1 gongpengjun  staff   2.8M 10 30 11:34 /usr/local/bin/certgen-darwin-amd64
lrwxr-xr-x  1 gongpengjun  staff    35B 10 30 11:39 /usr/local/bin/certgen -> /usr/local/bin/certgen-darwin-amd64
```

创建自签名证书：

```shell
gongpengjun@mbp ~$ certgen -host "127.0.0.1,localhost"
Created a new certificate 'public.crt', 'private.key' valid for the following names 📜
 - "127.0.0.1"
 - "localhost"
```

安装自签名证书：

```
gongpengjun@mbp ~$ ls -lrth private.key public.crt
-rw-r--r--  1 gongpengjun  staff   717B 10 30 11:43 public.crt
-rw-------  1 gongpengjun  staff   241B 10 30 11:43 private.key
gongpengjun@mbp ~$ mv private.key public.crt ~/.minio/certs/
gongpengjun@mbp ~$ ls -lrth ~/.minio/certs/
total 16
-rw-r--r--  1 gongpengjun  staff   717B 10 30 11:43 public.crt
-rw-------  1 gongpengjun  staff   241B 10 30 11:43 private.key
gongpengjun@mbp ~$ tree ~/.minio
/Users/gongpengjun/.minio
└── certs
    ├── CAs
    ├── private.key
    └── public.crt

2 directories, 2 files
```

### 4、启动运行

```shell
minio server start --address :9001
```

实际执行：

```shell
gongpengjun@mbp ~$ echo $MINIO_CONFIG_ENV_FILE

gongpengjun@mbp ~$ minio server start --address :9001
WARNING: Detected default credentials 'minioadmin:minioadmin', we recommend that you change these values with 'MINIO_ROOT_USER' and 'MINIO_ROOT_PASSWORD' environment variables
MinIO Object Storage Server
Copyright: 2015-2022 MinIO, Inc.
License: GNU AGPLv3 <https://www.gnu.org/licenses/agpl-3.0.html>
Version: RELEASE.2022-10-29T06-21-33Z (go1.19.2 darwin/amd64)

Status:         1 Online, 0 Offline.
API: https://172.28.123.199:9001  https://10.0.0.8:9001  https://127.0.0.1:9001  https://127.94.0.1:9001
RootUser: minioadmin
RootPass: minioadmin
Console: https://172.28.123.199:54550 https://10.0.0.8:54550 https://127.0.0.1:54550 https://127.94.0.1:54550
RootUser: minioadmin
RootPass: minioadmin

Command-line: https://min.io/docs/minio/linux/reference/minio-mc.html#quickstart
   $ mc alias set myminio https://172.28.123.199:9001 minioadmin minioadmin

Documentation: https://min.io/docs/minio/linux/index.html
```

存储路径：

```shell
gongpengjun@mbp ~$ ls -a ~/start/
.		..		.minio.sys	baby-private	baby-public
gongpengjun@mbp ~$ tree ~/start
/Users/gongpengjun/start
├── baby-private
│   └── display_form.jpg
│       ├── b0b95ec0-f06b-4e0a-a214-eba745166c14
│       │   └── part.1
│       └── xl.meta
└── baby-public
    └── kedis.png
        └── xl.meta

5 directories, 3 files
```

### 5、访问

#### 5.1、网页版 MinIO Console

https://127.0.0.1:9001

```shell
RootUser: minioadmin
RootPass: minioadmin
```

#### 5.2、命令行 mc

```shell
gongpengjun@mbp ~$ brew install minio/stable/mc
==> Downloading https://dl.min.io/client/mc/release/darwin-amd64/archive/mc.RELEASE.2022-10-29T10-09-23Z
######################################################################## 100.0%
==> Installing mc from minio/stable
🍺  /usr/local/Cellar/mc/RELEASE.2022-10-29T10-09-23Z_1: 3 files, 26.4MB, built in 6 seconds
```

配置

```shell
gongpengjun@mbp ~$ mc --autocompletion
mc: Configuration written to `/Users/gongpengjun/.mc/config.json`. Please update your access credentials.
mc: Successfully created `/Users/gongpengjun/.mc/share`.
mc: Initialized share uploads `/Users/gongpengjun/.mc/share/uploads.json` file.
mc: Initialized share downloads `/Users/gongpengjun/.mc/share/downloads.json` file.
mc: Your shell is set to 'bash', by env var 'SHELL'.
mc: enabled autocompletion in your 'bash' rc file. Please restart your shell.
```

配置

```shell
gongpengjun@mbp shell_samples$ mc alias set local https://127.0.0.1:9001 minioadmin minioadmin
Fingerprint of local public key: 536b2bc770ef7d6a1ea022d545f702b3ec023b30f2c4f44563b96a7e83e737f0
Confirm public key y/N: y
Added `local` successfully
```

访问：

```shell
mc admin info local
mc ls local
mc watch local
```

实际执行：

```shell
gongpengjun@mbp shell_samples$ mc admin info local
●  127.0.0.1:9001
   Uptime: 1 hour
   Version: 2022-10-29T06:21:33Z
   Network: 1/1 OK
   Drives: 1/1 OK
   Pool: 1

Pools:
   1st, Erasure sets: 1, Drives per erasure set: 1

56 KiB Used, 2 Buckets, 1 Object
1 drive online, 0 drives offline

gongpengjun@mbp shell_samples$ mc ping local
  1: https://127.0.0.1:9001   min=18.07ms    max=18.07ms    average=18.07ms    errors=0   roundtrip=18.07ms
  2: https://127.0.0.1:9001   min=0.67ms     max=18.07ms    average=9.37ms     errors=0   roundtrip=0.67ms
  3: https://127.0.0.1:9001   min=0.67ms     max=18.07ms    average=6.49ms     errors=0   roundtrip=0.73ms
gongpengjun@mbp shell_samples$ mc ls local
[2022-10-30 15:50:39 CST]     0B baby-private/
[2022-10-30 16:03:26 CST]     0B baby-public/
gongpengjun@mbp shell_samples$ mc watch local
[2022-10-30T08:13:36.657Z] 398 KiB s3:ObjectCreated:Put https://127.0.0.1:9001/baby-private/display_form.jpg
[2022-10-30T08:13:56.887Z]        s3:ObjectAccessed:Head https://127.0.0.1:9001/baby-public/kedis.png
[2022-10-30T08:13:56.891Z]        s3:ObjectAccessed:Get https://127.0.0.1:9001/baby-public/kedis.png
[2022-10-30T08:14:13.212Z]        s3:ObjectAccessed:Get https://127.0.0.1:9001/baby-public/kedis.png
gongpengjun@mbp shell_samples$ mc tree local
local
├─ baby-private
└─ baby-public
gongpengjun@mbp shell_samples$ mc tree local/baby-public
gongpengjun@mbp shell_samples$ mc ls local/baby-public
[2022-10-30 16:04:19 CST]  56KiB STANDARD kedis.png
gongpengjun@mbp shell_samples$ mc ls local/baby-private
[2022-10-30 16:13:36 CST] 398KiB STANDARD display_form.jpg
```

私有bucket生成下载链接：

```shell
gongpengjun@mbp shell_samples$ mc share download local/baby-private/display_form.jpg
URL: https://127.0.0.1:9001/baby-private/display_form.jpg
Expire: 7 days 0 hours 0 minutes 0 seconds
Share: https://127.0.0.1:9001/baby-private/display_form.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=minioadmin%2F20221030%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20221030T083930Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=b793b786cacc1ead98b11c0aaa455923a86a50d0ca3c98201876f9a1beae8a30
```

公有bucket生成下载链接：

```shell
gongpengjun@mbp shell_samples$ mc share download local/baby-public/kedis.png
URL: https://127.0.0.1:9001/baby-public/kedis.png
Expire: 7 days 0 hours 0 minutes 0 seconds
Share: https://127.0.0.1:9001/baby-public/kedis.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=minioadmin%2F20221030%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20221030T084013Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=fc715ae3fb83a014a920eeced0ac96abe922e0fa0ab8f3b05460da861fbec43b
```

查看文件metadata

```shell
gongpengjun@mbp shell_samples$ mc stat local/baby-public/kedis.png
Name      : kedis.png
Date      : 2022-10-30 16:04:19 CST
Size      : 56 KiB
ETag      : d653356dd1e064c1a5035067af1bf1ab
Type      : file
Metadata  :
  Content-Type: image/png
```

查找文件

```shell
gongpengjun@mbp ~$ mc find local/baby-public --name "*.png"
local/baby-public/kedis.png
gongpengjun@mbp ~$ mc find local/baby-private --name "*.jpg"
local/baby-private/display_form.jpg
```

#### 5.3、开发应用 minio-java sdk

详见HelloMinio项目[README.md](https://github.com/gongpengjun/HelloMinio#readme)

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
