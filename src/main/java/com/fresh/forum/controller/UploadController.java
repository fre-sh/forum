package com.fresh.forum.controller;

import com.fresh.forum.dto.UploadTo;
import com.fresh.forum.service.AdminService;
import com.fresh.forum.service.UploadService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author guowenyu
 * @date 2020/6/1
 */
@Controller
public class UploadController extends BaseController{

    @Autowired
    UploadService uploadService;

    /**
     * 上传图片（wangEditor）
     * @return
     */
    @RequestMapping("/upload")
    @ResponseBody
    public Object upload(MultipartFile multipartFile) {
//        return success(adminService.upload());
        logger.info(multipartFile.getName());
        uploadService.upload(multipartFile);
        return new UploadTo();
    }

}
