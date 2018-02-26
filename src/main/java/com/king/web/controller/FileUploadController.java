package com.king.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/file")
public class FileUploadController {

    @GetMapping("/upload")
    public String fileUploadPage() {
        return "testUpload";
    }

    @PostMapping("/upload")
    public ModelAndView fileUploadProcess( @RequestPart(name = "file") MultipartFile multipartFile) {
        ModelAndView mv=new ModelAndView();
        mv.setView(new MappingJackson2JsonView());
        String fileName = multipartFile.getOriginalFilename();
        multipartFile.getContentType();
        File dest = new File(fileName);
        try {
            multipartFile.transferTo(dest);
            mv.addObject("msg", "上传成功");
        } catch (IOException e) {
            mv.addObject("msg", "上传失败");
            e.printStackTrace();
        }
        return mv;
    }

}
