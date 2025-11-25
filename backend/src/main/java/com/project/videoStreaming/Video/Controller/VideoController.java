package com.project.videoStreaming.Video.Controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.videoStreaming.Video.DTO.Video;
import com.project.videoStreaming.Video.Service.VideoServiceImplementation;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import com.project.videoStreaming.SecurityConfig.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/video")
public class VideoController {
    
    @Autowired
    VideoServiceImplementation implementation;

    @Autowired
    AuthService auth;

    @GetMapping("/{videoId}")
    public Video getVideoDetailsByID(@PathVariable UUID videoId) {
        return implementation.getVideoByVideoId(videoId);
    }
    

    // Get videos uploaded by you.
    @GetMapping("/my-videos")
    public List<Video> getVideosByUserID(){ 
        UUID userid = auth.getUserId();
        return implementation.getVideosByUserID(userid);
    }

    //Get videos uploaded by other users.
    @GetMapping("/user/{channelid}")
    public List<Video> getVideosByChannelID(@PathVariable UUID channelID) {  

        return implementation.getVideosByUserID(channelID);
    }
    

    @GetMapping("/random-videos")
    public List<Video> getRamdonVideos(){
        return implementation.getRandomVideos();
    }

    @GetMapping("/category/{value}")
    public List<Video> getVideosByCategory(@PathVariable String value){
        return implementation.getVideosByCategory(value);
    }

    @PostMapping("/file/video")
    public String storeVideoFile(@RequestParam MultipartFile file){
        String filePath = "C:\\Users\\yashj\\OneDrive\\Desktop\\VS-Files\\videos";

        File folder = new File(filePath);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File files = new File(filePath, file.getOriginalFilename());
        System.out.println(files);

        try {
            Files.copy(file.getInputStream(), files.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return file.getOriginalFilename();
        } catch (java.io.IOException e) {
            throw new RuntimeException("Failed to store uploaded file", e);
        }        
    }

    @PostMapping("/file/thumbnail")
    public String storeThumbnailFile(@RequestParam MultipartFile file){
        String filePath = "C:\\Users\\yashj\\OneDrive\\Desktop\\VS-Files\\thumbnail";

        File folder = new File(filePath);
        if (!folder.exists()) {
            folder.mkdirs(); 
        }

        File files = new File(filePath, file.getOriginalFilename());
        System.out.println(files);

        try {
            Files.copy(file.getInputStream(), files.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return file.getOriginalFilename();
        } catch (java.io.IOException e) {
            throw new RuntimeException("Failed to store uploaded file", e);
        }        
    }



    @GetMapping("/videofile/{path}")
    public ResponseEntity<Resource> getVideoFIle(@PathVariable String path){

        Path folder = Paths.get("C:\\Users\\yashj\\OneDrive\\Desktop\\VS-Files\\videos\\");
        Path videoPath = folder.resolve(path);

        if(!Files.exists(videoPath)){
            return ResponseEntity.notFound().build();
        }

        try{
            InputStream inputStream = Files.newInputStream(videoPath);
            InputStreamResource inputStreamResource = new InputStreamResource(inputStream);

            String mimeType = Files.probeContentType(videoPath);
            if (mimeType == null)
                mimeType = "application/octet-stream";

            return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(mimeType))
            .contentLength(Files.size(videoPath))
            .body(inputStreamResource);

        }catch(IOException e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/thumbnail/{path}")
    public ResponseEntity<Resource> getThumbnailFIle(@PathVariable String path){

        Path folder = Paths.get("C:\\Users\\yashj\\OneDrive\\Desktop\\VS-Files\\thumbnail\\");
        Path thumbnailPath = folder.resolve(path);

        if(!Files.exists(thumbnailPath)){
            return ResponseEntity.notFound().build();
        }

        try{
            Resource resource = new UrlResource(thumbnailPath.toUri());

            String mimeType = Files.probeContentType(thumbnailPath);
            if (mimeType == null)
                mimeType = "application/octet-stream";

            return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(mimeType))
            .contentLength(Files.size(thumbnailPath))
            .body(resource);

        }catch(IOException e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/create")
    public void createVideo(@RequestBody Video video) {
        System.out.println(video);
        UUID userid = auth.getUserId();
        video.setUserId(userid);
        implementation.createVideo(video);
    }

    @PutMapping("/update/{videoid}")
    public void updateVideo(@RequestBody Video video, @PathVariable UUID videoid){
        implementation.updateVideoUsingUserIDAndVideoID(video, auth.getUserId(), videoid);
    }

    @DeleteMapping("/delete/{videoid}")
    public void deleteVideo(@PathVariable UUID videoid){
        implementation.deleteVideo(auth.getUserId(), videoid);
    }    
}
