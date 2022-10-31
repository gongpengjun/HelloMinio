## SSLPoke使用说明

### 使用命令

异常诊断：

```shell
gongpengjun@mbp tools$ pwd
/Users/gongpengjun/workspace/java/HelloMinio/tools
gongpengjun@mbp tools$ java SSLPoke 127.0.0.1 9001

gongpengjun@mbp tools$ java -Djavax.net.debug=ssl SSLPoke 127.0.0.1 9001
```

预期返回：

```shell
gongpengjun@mbp tools$ java SSLPoke 127.0.0.1 9001
Successfully connected
```

### 使用案例

使用案例详见[issue_2_self_sign_tls_certificate_verify_failed.md](issues/issue_2_self_sign_tls_certificate_verify_failed.md)

### 参考文档

- https://confluence.atlassian.com/kb/unable-to-connect-to-ssl-services-due-to-pkix-path-building-failed-error-779355358.html
- https://confluence.atlassian.com/kb/how-to-import-a-public-ssl-certificate-into-a-jvm-867025849.html
