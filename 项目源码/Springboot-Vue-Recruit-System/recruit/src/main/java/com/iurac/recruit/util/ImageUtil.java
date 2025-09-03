package com.iurac.recruit.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Book;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class ImageUtil {

    public static void deleteImage(String imageName,String path){
        if(!imageName.equals("default.png")){
            File folder = new File("D:\\upload\\"+path);
            File[] files = folder.listFiles();
            if (Objects.nonNull(files)){
                for(File file : files){
                    if (file.getName().equals(imageName)) {
                        System.out.println("删除"+file.getName()+"图片");
                        file.delete();
                    }
                }
            }


        }
    }

    public static String getFileName(String extension){
        String today = DateUtil.today();
        String uuid = IdUtil.simpleUUID();
        return today+uuid+extension;
    }


    public static void saveImage(MultipartFile file, String newFileName, String path) throws IOException {
        if(!newFileName.equals("default.png")) {
            deleteImage(newFileName, path);
            File target = new File("D:\\upload\\" + path, newFileName);
            file.transferTo(target);
        }
    }
}

