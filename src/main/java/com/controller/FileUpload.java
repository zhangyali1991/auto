package com.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping(value = "/" )
public class FileUpload {
    //上传地址
    private String filePath = "D://";

    //    跳转上传页面
    @RequestMapping(value = "upload")
    public String upload() {
        return "upload.html";
    }

    //执行上传单文件
    @PostMapping("uploadFile")
    @ResponseBody
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model) {
        if (file == null || file.isEmpty()) {
            return "上传文件不能为空";
        }
        //获取上传文件名
        String filename = file.getOriginalFilename();
        //上传文件保存路径
        String path = filePath + "rotPhoto/";
        //新建文件
        File filepath = new File(path, filename);//D://
        // 判断路径是否存在，如果不存在就创建一个
        if (!filepath.getParentFile().exists()) {
            filepath.getParentFile().mkdirs();
        }
        try {
            // 写入文件
            file.transferTo(filepath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "上传成功";
    }

    //    跳转上传页面
    @RequestMapping(value = "upload2")
    public String upload2() {
        return "upload2.html";
    }

    //执行上传多文件
    @PostMapping("uploadFiles")
    @ResponseBody
    public String uploadFiles(@RequestParam("files") MultipartFile[] files) throws Exception {
        String filename = "";
        File filepath;
        //上传文件保存路径
        String path = filePath + "rotPhoto/";
        if (files == null || files.length == 0) {
            return "上传文件不能为空";
        }
        for (MultipartFile file : files) {
            //获取上传文件名
            filename = file.getOriginalFilename();
            //新建文件
            filepath = new File(path, filename);//D://
            // 判断路径是否存在，如果不存在就创建一个
            if (!filepath.getParentFile().exists()) {
                filepath.getParentFile().mkdirs();
            }
            try {
                // 写入文件
                file.transferTo(filepath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "上传成功";
    }

}
