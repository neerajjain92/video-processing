package com.neeraj.processing.example.VideoProcessing.controller;

import com.neeraj.processing.example.VideoProcessing.entity.VideoProcessing;
import com.neeraj.processing.example.VideoProcessing.repository.VideoProcessingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Date;
import java.util.Objects;

import static com.neeraj.processing.example.VideoProcessing.entity.VideoProcessing.STATUS.COMPLETED;

/**
 * @author neeraj on 2019-05-26
 * Copyright (c) 2019, VideoProcessing.
 * All rights reserved.
 */
@RestController
@RequestMapping("/video")
public class VideoProcessingController {

    @Autowired
    private VideoProcessingRepository videoProcessingRepository;

    @PostMapping("/process")
    public ResponseEntity processVideo(@RequestParam("videoName") String videoName) {


        // TODO : Make actual 3rd party call
        /**
         * Make 3rd party API call
         * https://www.onlinevideoconverter.com/youtube-converter/videoName
         * This API call can return following things
         *
         * 1) 202 Status Accepted (If Request accepted )
         * 2) 102 Status Processing (If Accepted request is now in processing stage)
         * 3) 200 OK file processing is successful let's just download the file
         */

        // Now let's assume 3rd Party returned ID as 123
        VideoProcessing videoProcessing = new VideoProcessing();
        videoProcessing.setStatus(VideoProcessing.STATUS.ACCEPTED);
        videoProcessing.setFileName(videoName);
        videoProcessing.setProcessingStarted(new Date());


        // Saving it first time to get the unique generated id by database,
        // In realtime this will come from 3rd party
        videoProcessing = videoProcessingRepository.save(videoProcessing);

        // This URL should be used next time to get the converted file
        String videoProcessedURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/video/")
                .path(videoProcessing.getId().toString())
                .toUriString();
        videoProcessing.setURL(videoProcessedURL);
        videoProcessing = videoProcessingRepository.save(videoProcessing);

        // Updating the video processing instance with the URL.
        videoProcessing = videoProcessingRepository.save(videoProcessing);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(videoProcessing);
    }

    @GetMapping("/{id}")
    public ResponseEntity downloadVideo(@PathVariable("id") Long id) {
        VideoProcessing videoProcessing = videoProcessingRepository.findById(id).orElse(null);

        if (Objects.nonNull(videoProcessing)) {

            HttpStatus httpStatus = videoProcessing.getStatus() == COMPLETED ? HttpStatus.OK : HttpStatus.ACCEPTED;

            if (httpStatus == HttpStatus.OK) {

                // This URL will be used to download the file
                String videoDownloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/video/download/")
                        .path(videoProcessing.getId().toString())
                        .toUriString();
                videoProcessing.setVideoLocation(videoDownloadUrl);
            }
            return ResponseEntity.status(httpStatus).body(videoProcessing);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Video Id is invalid");
        }
    }
}
