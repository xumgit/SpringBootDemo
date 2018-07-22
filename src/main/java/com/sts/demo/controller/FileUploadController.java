package com.sts.demo.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
@RequestMapping(value="/fileIndex")
public class FileUploadController {

	private static final Logger LOG = LogManager.getLogger(FileUploadController.class);
	
	@RequestMapping(value="/file")
    public String file(){
        return "fileupload/file";
    }
	
	@RequestMapping("/fileUpload")
    @ResponseBody 
    public String fileUpload(@RequestParam("fileName") MultipartFile file, HttpServletRequest request){
        if(file.isEmpty()){
            return "false";
        }
        String fileName = file.getOriginalFilename();
        int size = (int) file.getSize();
        LOG.info(fileName + "-->" + size);

        String path = "";
        try {
			String root = ResourceUtils.getURL("classpath:").getPath();
			LOG.info("root="+root);
			File rootFile = new File(root);
			if(!rootFile.exists()) {
				rootFile = new File("");
			}
			LOG.info("rootFile="+rootFile.getAbsolutePath());
			File upload = new File(rootFile.getAbsolutePath(),"static/upload/");
			if(!upload.exists()) {
				upload.mkdirs();
			}
			LOG.info("upload url="+upload.getAbsolutePath());
			path = upload.getAbsolutePath();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
		}
        
        LOG.info("path="+path);
        if (path == "") { 
        	 	return "false";
        }
        File dest = new File(path + "/" + fileName);
        if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
            dest.getParentFile().mkdir();
        }
        try {
            file.transferTo(dest); //保存文件
            return "true";
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "false";
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "false";
        }
    }

    @RequestMapping("/multifile")
    public String multifile(){
        return "fileupload/multifile";
    }
    
    @RequestMapping(value="/multifileUpload",method=RequestMethod.POST) 
    /**public @ResponseBody String multifileUpload(@RequestParam("fileName")List<MultipartFile> files) */
    public @ResponseBody String multifileUpload(HttpServletRequest request){
        
        List<MultipartFile> files = ((MultipartHttpServletRequest)request).getFiles("fileName");
        
        if(files.isEmpty()){
            return "false";
        }

        String path = "";
        try {
			String root = ResourceUtils.getURL("classpath:").getPath();
			LOG.info("root="+root);
			File rootFile = new File(root);
			if(!rootFile.exists()) {
				rootFile = new File("");
			}
			LOG.info("rootFile="+rootFile.getAbsolutePath());
			File upload = new File(rootFile.getAbsolutePath(),"static/upload/");
			if(!upload.exists()) {
				upload.mkdirs();
			}
			LOG.info("upload url="+upload.getAbsolutePath());
			path = upload.getAbsolutePath();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
		}
        
        LOG.info("path="+path);
        if (path == "") { 
        	 	return "false";
        }
        
        for(MultipartFile file:files){
            String fileName = file.getOriginalFilename();
            int size = (int) file.getSize();
            LOG.info(fileName + "-->" + size);
            
            if(file.isEmpty()){
            		LOG.info("file is empty");
            		continue;
                //return "false";
            }else{        
                File dest = new File(path + "/" + fileName);
                if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
                    dest.getParentFile().mkdir();
                }
                try {
                    file.transferTo(dest);
                }catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    return "false";
                } 
            }
        }
        return "true";
    }
	
}
