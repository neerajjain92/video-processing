package com.neeraj.processing.example.VideoProcessing.scheduler;

import com.neeraj.processing.example.VideoProcessing.entity.VideoProcessing;
import com.neeraj.processing.example.VideoProcessing.repository.VideoProcessingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author neeraj on 2019-05-26
 * Copyright (c) 2019, VideoProcessing.
 * All rights reserved.
 */
@Component
public class VideoProcessingScheduler {

    @Autowired
    private VideoProcessingRepository videoProcessingRepository;

//    private static Logger LOGGER = LoggerFactory.getLogger(VideoProcessingScheduler.class);

    @Scheduled(fixedRate = 30000)
    public void processUnprocessedVideos() {

//        LOGGER.info("Processing unprocessed videos at time : {} ", new Date());

        // Get All Videos which are not yet processed
        List<VideoProcessing> videosToBeProcessed = videoProcessingRepository.findAllByStatusNot(VideoProcessing.STATUS.COMPLETED);

        videosToBeProcessed.stream().forEach(video -> {

//            LOGGER.info("Processing video {}", video);

            // TODO Make 3rd party call here
            /**
             * Make 3rd party API call
             * https://www.onlinevideoconverter.com/youtube-converter/videoName
             * This API call can return following things
             *
             * 1) 102 Status Processing (If Accepted request is now in processing stage)
             * 2) 200 OK file processing is successful let's just download the file
             */

            // We are assuming that server returned 200 OK
            video.setStatus(VideoProcessing.STATUS.COMPLETED);
            video.setProcessingCompleted(new Date());

            // Marking Status as completed
            videoProcessingRepository.save(video);

//            LOGGER.info("Video  {} processed successfully", video);
        });
    }
}
