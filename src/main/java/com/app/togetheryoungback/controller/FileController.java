package com.app.togetheryoungback.controller;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@Slf4j
@RequestMapping("/file/*")
public class FileController {
    // 파일의 저장과 그에 맞는 uuid이름을 반환.
    // 이미지일 경우 이름 앞에 t_ 가 붙게 되며 썸네일로 생성됨
    @PostMapping("upload")
    @ResponseBody
    public List<String> upload(@RequestParam("uploadFile") List<MultipartFile> uploadFiles) throws IOException {
        log.info(uploadFiles.toString());
        String rootPath = "C:/upload/" + getPath();
        List<String> uuids = new ArrayList<>();
        File file = new File(rootPath);
        if(!file.exists()){
            file.mkdirs();
        }

        for(int i=0; i<uploadFiles.size(); i++){
            uuids.add(UUID.randomUUID().toString());
            uploadFiles.get(i).transferTo(new File(rootPath, uuids.get(i) + "_" + uploadFiles.get(i).getOriginalFilename()));
            if(uploadFiles.get(i).getContentType().startsWith("image")){
                FileOutputStream out = new FileOutputStream(new File(rootPath, "t_" + uuids.get(i) + "_" + uploadFiles.get(i).getOriginalFilename()));
                Thumbnailator.createThumbnail(uploadFiles.get(i).getInputStream(), out, 200, 200);
                out.close();
            }
        }

        return uuids;
    }

    @GetMapping("display")
    @ResponseBody
    public byte[] display(String fileName) throws IOException{
        log.info(fileName);
        return FileCopyUtils.copyToByteArray(new File("C:/upload", fileName));
    }

    private String getPath() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
    }
}
