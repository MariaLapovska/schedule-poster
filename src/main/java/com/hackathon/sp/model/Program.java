package com.hackathon.sp.model;

import lombok.Data;

import java.util.Date;

@Data
public class Program {
    private String title;
    private String subtitle;
    private String imageUrl;
    private Date begin;
    private Date end;
}