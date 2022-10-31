package com.gongpengjun;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Validated
@RestController
public class MinioController {
    @Autowired
    private MinioUtils minioUtils;

    /**
     * 上传
     *
     * @param isPublicBucket
     * @param file
     * @param request
     * @return
     */
    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam(name = "public") Boolean isPublicBucket,
                         @RequestParam(name = "file", required = false) MultipartFile file,
                         HttpServletRequest request) {
        JSONObject res = null;
        try {
            res = minioUtils.uploadFile(isPublicBucket, file);
        } catch (Exception e) {
            e.printStackTrace();
            res.put("code", 0);
            res.put("msg", "上传失败");
        }
        return res.toJSONString();
    }
}
