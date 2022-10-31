
### 问题现象

javax.net.ssl.SSLHandshakeException: sun.security.validator.ValidatorException: PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target


### 根因分析

https://confluence.atlassian.com/kb/unable-to-connect-to-ssl-services-due-to-pkix-path-building-failed-error-779355358.html
https://confluence.atlassian.com/kb/how-to-import-a-public-ssl-certificate-into-a-jvm-867025849.html

gongpengjun@mbp tools$ pwd
/Users/gongpengjun/workspace/java/HelloMinio/tools
gongpengjun@mbp tools$ java SSLPoke 127.0.0.1 9001
sun.security.validator.ValidatorException: PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target
	at sun.security.validator.PKIXValidator.doBuild(PKIXValidator.java:439)
	at sun.security.validator.PKIXValidator.engineValidate(PKIXValidator.java:306)
	at sun.security.validator.Validator.validate(Validator.java:271)
	at sun.security.ssl.X509TrustManagerImpl.validate(X509TrustManagerImpl.java:312)
	at sun.security.ssl.X509TrustManagerImpl.checkTrusted(X509TrustManagerImpl.java:221)
	at sun.security.ssl.X509TrustManagerImpl.checkServerTrusted(X509TrustManagerImpl.java:128)
	at sun.security.ssl.CertificateMessage$T12CertificateConsumer.checkServerCerts(CertificateMessage.java:636)
	at sun.security.ssl.CertificateMessage$T12CertificateConsumer.onCertificate(CertificateMessage.java:471)
	at sun.security.ssl.CertificateMessage$T12CertificateConsumer.consume(CertificateMessage.java:367)
	at sun.security.ssl.SSLHandshake.consume(SSLHandshake.java:376)
	at sun.security.ssl.HandshakeContext.dispatch(HandshakeContext.java:444)
	at sun.security.ssl.HandshakeContext.dispatch(HandshakeContext.java:422)
	at sun.security.ssl.TransportContext.dispatch(TransportContext.java:183)
	at sun.security.ssl.SSLTransport.decode(SSLTransport.java:154)
	at sun.security.ssl.SSLSocketImpl.decode(SSLSocketImpl.java:1279)
	at sun.security.ssl.SSLSocketImpl.readHandshakeRecord(SSLSocketImpl.java:1188)
	at sun.security.ssl.SSLSocketImpl.startHandshake(SSLSocketImpl.java:401)
	at sun.security.ssl.SSLSocketImpl.ensureNegotiated(SSLSocketImpl.java:808)
	at sun.security.ssl.SSLSocketImpl.access$200(SSLSocketImpl.java:75)
	at sun.security.ssl.SSLSocketImpl$AppOutputStream.write(SSLSocketImpl.java:1093)
	at sun.security.ssl.SSLSocketImpl$AppOutputStream.write(SSLSocketImpl.java:1065)
	at SSLPoke.main(SSLPoke.java:31)
Caused by: sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target
	at sun.security.provider.certpath.SunCertPathBuilder.build(SunCertPathBuilder.java:141)
	at sun.security.provider.certpath.SunCertPathBuilder.engineBuild(SunCertPathBuilder.java:126)
	at java.security.cert.CertPathBuilder.build(CertPathBuilder.java:280)
	at sun.security.validator.PKIXValidator.doBuild(PKIXValidator.java:434)
	... 21 more
gongpengjun@mbp tools$ java -Djavax.net.debug=ssl SSLPoke 127.0.0.1 9001
javax.net.ssl|FINE|01|main|2022-10-31 16:02:34.799 CST|SSLCipher.java:438|jdk.tls.keyLimits:  entry = AES/GCM/NoPadding KeyUpdate 2^37. AES/GCM/NOPADDING:KEYUPDATE = 137438953472
javax.net.ssl|SEVERE|01|main|2022-10-31 16:02:34.916 CST|TransportContext.java:345|Fatal (CERTIFICATE_UNKNOWN): sun.security.validator.ValidatorException: PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target (
"throwable" : {
  sun.security.validator.ValidatorException: PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target
  	at sun.security.validator.PKIXValidator.doBuild(PKIXValidator.java:439)
  	at sun.security.validator.PKIXValidator.engineValidate(PKIXValidator.java:306)
  	at sun.security.validator.Validator.validate(Validator.java:271)
  	at sun.security.ssl.X509TrustManagerImpl.validate(X509TrustManagerImpl.java:312)
  	at sun.security.ssl.X509TrustManagerImpl.checkTrusted(X509TrustManagerImpl.java:221)
  	at sun.security.ssl.X509TrustManagerImpl.checkServerTrusted(X509TrustManagerImpl.java:128)
  	at sun.security.ssl.CertificateMessage$T12CertificateConsumer.checkServerCerts(CertificateMessage.java:636)
  	at sun.security.ssl.CertificateMessage$T12CertificateConsumer.onCertificate(CertificateMessage.java:471)
  	at sun.security.ssl.CertificateMessage$T12CertificateConsumer.consume(CertificateMessage.java:367)
  	at sun.security.ssl.SSLHandshake.consume(SSLHandshake.java:376)
  	at sun.security.ssl.HandshakeContext.dispatch(HandshakeContext.java:444)
  	at sun.security.ssl.HandshakeContext.dispatch(HandshakeContext.java:422)
  	at sun.security.ssl.TransportContext.dispatch(TransportContext.java:183)
  	at sun.security.ssl.SSLTransport.decode(SSLTransport.java:154)
  	at sun.security.ssl.SSLSocketImpl.decode(SSLSocketImpl.java:1279)
  	at sun.security.ssl.SSLSocketImpl.readHandshakeRecord(SSLSocketImpl.java:1188)
  	at sun.security.ssl.SSLSocketImpl.startHandshake(SSLSocketImpl.java:401)
  	at sun.security.ssl.SSLSocketImpl.ensureNegotiated(SSLSocketImpl.java:808)
  	at sun.security.ssl.SSLSocketImpl.access$200(SSLSocketImpl.java:75)
  	at sun.security.ssl.SSLSocketImpl$AppOutputStream.write(SSLSocketImpl.java:1093)
  	at sun.security.ssl.SSLSocketImpl$AppOutputStream.write(SSLSocketImpl.java:1065)
  	at SSLPoke.main(SSLPoke.java:31)
  Caused by: sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target
  	at sun.security.provider.certpath.SunCertPathBuilder.build(SunCertPathBuilder.java:141)
  	at sun.security.provider.certpath.SunCertPathBuilder.engineBuild(SunCertPathBuilder.java:126)
  	at java.security.cert.CertPathBuilder.build(CertPathBuilder.java:280)
  	at sun.security.validator.PKIXValidator.doBuild(PKIXValidator.java:434)
  	... 21 more}

)
javax.net.ssl|FINE|01|main|2022-10-31 16:02:34.917 CST|SSLSocketImpl.java:1500|close the underlying socket
javax.net.ssl|FINE|01|main|2022-10-31 16:02:34.917 CST|SSLSocketImpl.java:1519|close the SSL connection (initiative)
sun.security.validator.ValidatorException: PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target
	at sun.security.validator.PKIXValidator.doBuild(PKIXValidator.java:439)
	at sun.security.validator.PKIXValidator.engineValidate(PKIXValidator.java:306)
	at sun.security.validator.Validator.validate(Validator.java:271)
	at sun.security.ssl.X509TrustManagerImpl.validate(X509TrustManagerImpl.java:312)
	at sun.security.ssl.X509TrustManagerImpl.checkTrusted(X509TrustManagerImpl.java:221)
	at sun.security.ssl.X509TrustManagerImpl.checkServerTrusted(X509TrustManagerImpl.java:128)
	at sun.security.ssl.CertificateMessage$T12CertificateConsumer.checkServerCerts(CertificateMessage.java:636)
	at sun.security.ssl.CertificateMessage$T12CertificateConsumer.onCertificate(CertificateMessage.java:471)
	at sun.security.ssl.CertificateMessage$T12CertificateConsumer.consume(CertificateMessage.java:367)
	at sun.security.ssl.SSLHandshake.consume(SSLHandshake.java:376)
	at sun.security.ssl.HandshakeContext.dispatch(HandshakeContext.java:444)
	at sun.security.ssl.HandshakeContext.dispatch(HandshakeContext.java:422)
	at sun.security.ssl.TransportContext.dispatch(TransportContext.java:183)
	at sun.security.ssl.SSLTransport.decode(SSLTransport.java:154)
	at sun.security.ssl.SSLSocketImpl.decode(SSLSocketImpl.java:1279)
	at sun.security.ssl.SSLSocketImpl.readHandshakeRecord(SSLSocketImpl.java:1188)
	at sun.security.ssl.SSLSocketImpl.startHandshake(SSLSocketImpl.java:401)
	at sun.security.ssl.SSLSocketImpl.ensureNegotiated(SSLSocketImpl.java:808)
	at sun.security.ssl.SSLSocketImpl.access$200(SSLSocketImpl.java:75)
	at sun.security.ssl.SSLSocketImpl$AppOutputStream.write(SSLSocketImpl.java:1093)
	at sun.security.ssl.SSLSocketImpl$AppOutputStream.write(SSLSocketImpl.java:1065)
	at SSLPoke.main(SSLPoke.java:31)
Caused by: sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target
	at sun.security.provider.certpath.SunCertPathBuilder.build(SunCertPathBuilder.java:141)
	at sun.security.provider.certpath.SunCertPathBuilder.engineBuild(SunCertPathBuilder.java:126)
	at java.security.cert.CertPathBuilder.build(CertPathBuilder.java:280)
	at sun.security.validator.PKIXValidator.doBuild(PKIXValidator.java:434)
	... 21 more
gongpengjun@mbp tools$

### 解决方案

https://confluence.atlassian.com/kb/how-to-import-a-public-ssl-certificate-into-a-jvm-867025849.html
https://stackoverflow.com/a/22782035/328435

gongpengjun@mbp tools$ ls -lrth ~/.minio/certs/public.crt
-rw-r--r--  1 gongpengjun  staff   717B 10 30 11:43 /Users/gongpengjun/.minio/certs/public.crt
gongpengjun@mbp tools$ ls -lrth $JAVA_HOME/jre/lib/security/cacerts
-rw-r--r--  1 root  wheel   107K 12 22  2021 /Library/Java/JavaVirtualMachines/jdk1.8.0_271.jdk/Contents/Home/jre/lib/security/cacerts
gongpengjun@mbp tools$ sudo keytool -importcert -alias 127.0.0.1 -keystore $JAVA_HOME/jre/lib/security/cacerts -file ~/.minio/certs/public.crt
Password:
输入密钥库口令:
密钥库口令太短 - 至少必须为 6 个字符
输入密钥库口令:
keytool 错误: java.io.IOException: Keystore was tampered with, or password was incorrect
gongpengjun@mbp tools$ sudo keytool -importcert -alias 127.0.0.1 -keystore $JAVA_HOME/jre/lib/security/cacerts -file ~/.minio/certs/public.crt
输入密钥库口令: changeit
所有者: OU=gongpengjun@bogon, O=Certgen Development
发布者: OU=gongpengjun@bogon, O=Certgen Development
序列号: 2ad6253f8051d57dae9ece5582856a61
生效时间: Sun Oct 30 11:43:11 CST 2022, 失效时间: Mon Oct 30 11:43:11 CST 2023
证书指纹:
	 SHA1: A0:D2:CD:26:18:B1:C1:99:46:24:0E:82:0F:FC:5B:2A:A7:8E:94:FD
	 SHA256: 21:21:58:A4:16:A0:1D:60:24:F9:D4:41:F2:A2:BC:3C:5A:4B:E8:86:BB:DA:E2:71:66:13:C1:34:B7:85:77:C1
签名算法名称: SHA256withECDSA
主体公共密钥算法: 256 位 EC 密钥
版本: 3

扩展:

#1: ObjectId: 2.5.29.19 Criticality=true
BasicConstraints:[
  CA:true
  PathLen:2147483647
]

#2: ObjectId: 2.5.29.37 Criticality=false
ExtendedKeyUsages [
  serverAuth
]

#3: ObjectId: 2.5.29.15 Criticality=true
KeyUsage [
  DigitalSignature
  Key_Encipherment
  Key_CertSign
]

#4: ObjectId: 2.5.29.17 Criticality=false
SubjectAlternativeName [
  DNSName: localhost
  IPAddress: 127.0.0.1
]

#5: ObjectId: 2.5.29.14 Criticality=false
SubjectKeyIdentifier [
KeyIdentifier [
0000: 1E A3 6C 37 C2 6B 3B 09   60 F4 22 8C A1 22 99 9E  ..l7.k;.`.".."..
0010: 1E 82 B3 54                                        ...T
]
]

是否信任此证书? [否]:  y
证书已添加到密钥库中
gongpengjun@mbp tools$

### 效果验证

gongpengjun@mbp tools$ java SSLPoke 127.0.0.1 9001
Successfully connected


curl --location --request POST 'http://127.0.0.1:8080/upload?public=true' \
--form 'file=@"/Users/gongpengjun/Downloads/Communication_Milestones.png"'
{"msg":"https://127.0.0.1:9001/baby-public/baby-public_1667204831306.png","code":1,"minio_response":"io.minio.ObjectWriteResponse@658f7f4c"}

