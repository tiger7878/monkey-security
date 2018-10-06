package com.monkey.web.controller;

import com.monkey.dto.FileInfo;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;

/**
 * 文件上传和下载的控制器
 *
 * @author: monkey
 * @date: 2018/10/6 17:23
 */
@RestController
@RequestMapping("/file")
public class FileController {

    private String folder = "F:\\JAVA\\upload";//上传和下载的路径

    /**
     * 上传
     *
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping
    public FileInfo upload(MultipartFile file) throws IOException {
        System.out.println(file.getName());
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getSize());

        //本地文件
        File localFile = new File(folder, new Date().getTime() + ".txt");

        file.transferTo(localFile);//下载

        return new FileInfo(localFile.getAbsolutePath());
    }

    /**
     * 下载
     * @param id
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping("/{id}")
    public void download(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //JDK7的新语法，可以帮我们关闭流，不用再catch中来关闭了
        try (
                InputStream inputStream = new FileInputStream(new File(folder, id + ".txt"));
                OutputStream outputStream = response.getOutputStream();
        ) {

            response.setContentType("application/x-download");
            response.addHeader("Content-Disposition", "attachment;filename=test.txt");
            //把文件的输入流拷贝到输出流
            IOUtils.copy(inputStream,outputStream);
            outputStream.flush();
        }
    }

}
