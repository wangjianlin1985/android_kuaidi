package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Notice {
    /*����id*/
    private int noticeId;
    public int getNoticeId() {
        return noticeId;
    }
    public void setNoticeId(int noticeId) {
        this.noticeId = noticeId;
    }

    /*����*/
    private String title;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    /*��������*/
    private String content;
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    /*����ʱ��*/
    private String publishDate;
    public String getPublishDate() {
        return publishDate;
    }
    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

}