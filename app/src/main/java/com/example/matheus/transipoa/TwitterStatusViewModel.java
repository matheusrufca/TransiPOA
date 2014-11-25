package com.example.matheus.transipoa;

import org.apache.http.impl.cookie.DateUtils;

import java.util.Comparator;
import java.util.Date;

import twitter4j.Status;

public class TwitterStatusViewModel {
    public String status;
    public String author;
    public String author_pic;
	private Date date;

	public String getDateString(){
		return DateUtils.formatDate(date, "HH:mm:ss MM/dd/yyyy");
	}

	public Date getDate(){
		return date;
	}

	public String getDateTimestamp(){
		long epoch = this.date.getTime() / 1000;
		return Long.toString(epoch);
	}

	public String getTimeAgo(){
		long timeBetween = new Date().getTime() - this.date.getTime();

		return TimeAgo.toDuration(timeBetween) + " atrás";
	}

    public TwitterStatusViewModel(String status, String author, String date, String author_pic){
        this.status = status;
        this.author = author;
	    this.date = new Date();
        this.author_pic = author_pic;
    }

    public TwitterStatusViewModel(Status twitterStatus){
        this.status = twitterStatus.getText();
	    this.author = twitterStatus.getUser().getScreenName();
	    this.date = twitterStatus.getCreatedAt();
	    this.author_pic = twitterStatus.getUser().getMiniProfileImageURL();
    }
}