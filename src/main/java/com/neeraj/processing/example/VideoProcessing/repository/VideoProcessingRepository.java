package com.neeraj.processing.example.VideoProcessing.repository;

import com.neeraj.processing.example.VideoProcessing.entity.VideoProcessing;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author neeraj on 2019-05-26
 * Copyright (c) 2019, VideoProcessing.
 * All rights reserved.
 */
@Repository
public interface VideoProcessingRepository extends CrudRepository<VideoProcessing, Long> {

    List<VideoProcessing> findAllByStatusNot(VideoProcessing.STATUS status);
}
