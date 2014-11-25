package com.example.matheus.transipoa;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import twitter4j.ResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.OAuth2Token;
import twitter4j.conf.ConfigurationBuilder;



/**
 * Created by Matheus on 03/11/2014.
 */
public class TwitterService extends AsyncTask<List<String>, Integer, List<TwitterStatusViewModel>> {
	// twitter consumer key and secret
	static String TWITTER_CONSUMER_KEY = "UmMKW0XRJyIfr3rlhkxQF6dIF";
	static String TWITTER_CONSUMER_SECRET = "sE5lZaMqDwhOERHvJAHD2Bcj4JLwNr5PuHQEXNMHDugxFCNLwj";


	// twitter acces token and accessTokenSecret
	static String TWITTER_ACCES_TOKEN = "56882800-rqCn5urhLqoC8yrDsZCbUeXLjPcDWri0Hrer54F0h";
	static String TWITTER_ACCES_TOKEN_SECRET = "HpzRruQr2J40rmh88w96fiTIgcgXtSMf2IbwrHFL3FzpR";

	OnTaskCompleted listener;
	Twitter twitterService;
	OAuth2Token oAuth2Token;
	List<TwitterStatusViewModel> tweetsResult = new ArrayList<TwitterStatusViewModel>();

	public static TwitterService twitterServiceFactory(OnTaskCompleted listener){
		return new TwitterService(listener);
	}


	private TwitterService(OnTaskCompleted listener) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);

		this.listener = listener;

		try {
			this.twitterService = this.twitterConnect(TWITTER_CONSUMER_KEY, TWITTER_CONSUMER_SECRET);
		}catch (Exception ex) {
			Log.i("Info", ex.getMessage());
		}
	}

	public Twitter twitterConnect(String twitter_consumer_key, String twitter_consumer_secret)throws Exception{
		ConfigurationBuilder builder;

		try {
			// setup
			builder = new ConfigurationBuilder();
			//builder.setUseSSL(true);
			builder.setApplicationOnlyAuthEnabled(true);

			twitterService = new TwitterFactory(builder.build()).getInstance();
			twitterService.setOAuthConsumer(twitter_consumer_key, twitter_consumer_secret);
			oAuth2Token = twitterService.getOAuth2Token();
		} catch (Exception ex) {
			Log.i("Info", ex.getMessage());
			throw ex;
		}

		return twitterService;
	}

	public List<TwitterStatusViewModel> getTweets(String username){
		List<TwitterStatusViewModel> twitterStatusList = new ArrayList<TwitterStatusViewModel>();

		try {
			ResponseList<twitter4j.Status> statuses = this.twitterService.getUserTimeline(username);
			twitterStatusList = convertTwitterStatus(statuses);
		}
		catch (Exception ex) {
			Log.i("Info", ex.getMessage());
		}
		this.tweetsResult = twitterStatusList;

		return twitterStatusList;
	}

	public List<TwitterStatusViewModel> getTweets(List<String> usernames){
		List<TwitterStatusViewModel> twitterStatusList = new ArrayList<TwitterStatusViewModel>();

		for(String username : usernames){
			twitterStatusList.addAll(this.getTweets(username));
		}

		/* ordena os tweets por data */
		//Collections.sort(twitterStatusList, new DateComparator());

		return twitterStatusList;
	}

	private List<TwitterStatusViewModel> convertTwitterStatus(ResponseList<twitter4j.Status> statuses) {
		ArrayList<TwitterStatusViewModel> twitterStatusList = new ArrayList<TwitterStatusViewModel>();

		if(statuses == null || statuses.isEmpty())
			return twitterStatusList;

		for(twitter4j.Status s: statuses) {
			twitterStatusList.add(new TwitterStatusViewModel(s));
		}
		return twitterStatusList;
	}

	public List<TwitterStatusViewModel> getDumbTweets(){
		ArrayList<TwitterStatusViewModel> items = new ArrayList<TwitterStatusViewModel>();

		items.add(new TwitterStatusViewModel("Caiu um trem queda abaixo ó meu deus do céu","@EPTC", "10:29:34 10/10/1990","https://pbs.twimg.com/profile_images/568363261/logo_EPTC_400x400.jpg"));
		items.add(new TwitterStatusViewModel("Caiu um trem queda abaixo ó meu deus do céu","@EPTC", "10:29:34 10/10/1990","https://pbs.twimg.com/profile_images/568363261/logo_EPTC_400x400.jpg"));
		items.add(new TwitterStatusViewModel("Caiu um trem queda abaixo ó meu deus do céu","@EPTC", "10:29:34 10/10/1990","https://pbs.twimg.com/profile_images/568363261/logo_EPTC_400x400.jpg"));
		items.add(new TwitterStatusViewModel("Caiu um trem queda abaixo ó meu deus do céu","@EPTC", "10:29:34 10/10/1990","https://pbs.twimg.com/profile_images/568363261/logo_EPTC_400x400.jpg"));
		items.add(new TwitterStatusViewModel("Caiu um trem queda abaixo ó meu deus do céu","@EPTC", "10:29:34 10/10/1990","https://pbs.twimg.com/profile_images/568363261/logo_EPTC_400x400.jpg"));
		items.add(new TwitterStatusViewModel("Caiu um trem queda abaixo ó meu deus do céu","@EPTC", "10:29:34 10/10/1990","https://pbs.twimg.com/profile_images/568363261/logo_EPTC_400x400.jpg"));
		items.add(new TwitterStatusViewModel("Caiu um trem queda abaixo ó meu deus do céu","@EPTC", "10:29:34 10/10/1990","https://pbs.twimg.com/profile_images/568363261/logo_EPTC_400x400.jpg"));
		items.add(new TwitterStatusViewModel("Caiu um trem queda abaixo ó meu deus do céu","@EPTC", "10:29:34 10/10/1990","https://pbs.twimg.com/profile_images/568363261/logo_EPTC_400x400.jpg"));
		items.add(new TwitterStatusViewModel("Caiu um trem queda abaixo ó meu deus do céu","@EPTC", "10:29:34 10/10/1990","https://pbs.twimg.com/profile_images/568363261/logo_EPTC_400x400.jpg"));

		return items;
	}

	public List<TwitterStatusViewModel> getTweetsResult(){
		return this.tweetsResult;
	}

	@Override
	protected List<TwitterStatusViewModel> doInBackground(List<String>... params) {
		List<String> usernames = new ArrayList<String>();
		List<TwitterStatusViewModel> tweets = new ArrayList<TwitterStatusViewModel>();

		if(params == null && params.length > 0)
			return tweets;

		usernames  = params[0];

		try {
			Thread.sleep(200);
			tweets = this.getTweets(usernames);
			//tweets = this.getDumbTweets(usernames);
			this.tweetsResult = tweets;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}


		return tweets;
	}

	@Override
	protected void onPostExecute(List<TwitterStatusViewModel> list){
		if (listener != null)
			listener.onTaskCompleted();
	}
}


