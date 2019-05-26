package com.neeraj.processing.example.VideoProcessing.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @author neeraj on 2019-05-26
 * Copyright (c) 2019, VideoProcessing.
 * All rights reserved.
 */
@Entity
@Data
public class VideoProcessing implements Serializable {

    public enum STATUS {
        ACCEPTED,
        PROCESSING,
        COMPLETED
    }

    @Id
    @GeneratedValue
    private Long id;
    private STATUS status;
    private String fileName;
    private String videoLocation;
    private String URL;
    private Date processingStarted;
    private Date processingCompleted;

}
