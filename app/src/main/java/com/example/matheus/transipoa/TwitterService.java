package com.example.matheus.transipoa;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import twitter4j.ResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.OAuth2Token;
import twitter4j.conf.ConfigurationBuilder;



/**
 * Created by Matheus on 03/11/2014.
 */
public class TwitterService extends AsyncTask<String, Integer, List<TwitterStatusViewModel>> {
	// twitter consumer key and secret
	static String TWITTER_CONSUMER_KEY = "UmMKW0XRJyIfr3rlhkxQF6dIF";
	static String TWITTER_CONSUMER_SECRET = "sE5lZaMqDwhOERHvJAHD2Bcj4JLwNr5PuHQEXNMHDugxFCNLwj";


	// twitter acces token and accessTokenSecret
	static String TWITTER_ACCES_TOKEN = "56882800-rqCn5urhLqoC8yrDsZCbUeXLjPcDWri0Hrer54F0h";
	static String TWITTER_ACCES_TOKEN_SECRET = "HpzRruQr2J40rmh88w96fiTIgcgXtSMf2IbwrHFL3FzpR";

	OnTaskCompleted listener;
	Twitter twitterService;
	OAuth2Token oAuth2Token;

	static List<TwitterStatusViewModel> tweetsResult = new ArrayList<TwitterStatusViewModel>();

	public static TwitterService twitterServiceFactory(OnTaskCompleted listener) {
		if (tweetsResult == null)
			tweetsResult = new ArrayList<TwitterStatusViewModel>();

		return new TwitterService(listener);
	}

	public static List<TwitterStatusViewModel> getTweetsResult() {
		sortTwitterStatusList(tweetsResult);

		return tweetsResult;
	}

	public static void clearResultList() {
		if (tweetsResult != null)
			tweetsResult.clear();
	}


	private TwitterService(OnTaskCompleted listener) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);

		this.listener = listener;

		try {
			this.twitterService = this.twitterConnect(TWITTER_CONSUMER_KEY, TWITTER_CONSUMER_SECRET);
		} catch (Exception ex) {
			Log.i("Info", ex.getMessage());
		}
	}

	public Twitter twitterConnect(String twitter_consumer_key, String twitter_consumer_secret) throws Exception {
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


	public List<TwitterStatusViewModel> getTweets(String username) {
		List<TwitterStatusViewModel> twitterStatusList = new ArrayList<TwitterStatusViewModel>();

		try {
			ResponseList<twitter4j.Status> statuses = this.twitterService.getUserTimeline(username);
			twitterStatusList = convertTwitterStatus(statuses);
			DataMining.removeUselessTweets(twitterStatusList);
		} catch (Exception ex) {
			Log.i("Info", ex.getMessage());
		}
		return twitterStatusList;
	}

	public List<TwitterStatusViewModel> getTweets(List<String> usernames) {
		List<TwitterStatusViewModel> twitterStatusList = new ArrayList<TwitterStatusViewModel>();

		for (String username : usernames) {
			twitterStatusList.addAll(this.getTweets(username));
		}

		return twitterStatusList;
	}

	public List<TwitterStatusViewModel> getDumbTweets() {
		ArrayList<TwitterStatusViewModel> items = new ArrayList<TwitterStatusViewModel>();

		items.add(new TwitterStatusViewModel("Caiu um trem queda abaixo ó meu deus do céu", "@EPTC", "10:29:34 10/10/1990", "https://pbs.twimg.com/profile_images/568363261/logo_EPTC_400x400.jpg"));
		items.add(new TwitterStatusViewModel("Caiu um trem queda abaixo ó meu deus do céu", "@EPTC", "10:29:34 10/10/1990", "https://pbs.twimg.com/profile_images/568363261/logo_EPTC_400x400.jpg"));
		items.add(new TwitterStatusViewModel("Caiu um trem queda abaixo ó meu deus do céu", "@EPTC", "10:29:34 10/10/1990", "https://pbs.twimg.com/profile_images/568363261/logo_EPTC_400x400.jpg"));
		items.add(new TwitterStatusViewModel("Caiu um trem queda abaixo ó meu deus do céu", "@EPTC", "10:29:34 10/10/1990", "https://pbs.twimg.com/profile_images/568363261/logo_EPTC_400x400.jpg"));
		items.add(new TwitterStatusViewModel("Caiu um trem queda abaixo ó meu deus do céu", "@EPTC", "10:29:34 10/10/1990", "https://pbs.twimg.com/profile_images/568363261/logo_EPTC_400x400.jpg"));
		items.add(new TwitterStatusViewModel("Caiu um trem queda abaixo ó meu deus do céu", "@EPTC", "10:29:34 10/10/1990", "https://pbs.twimg.com/profile_images/568363261/logo_EPTC_400x400.jpg"));
		items.add(new TwitterStatusViewModel("Caiu um trem queda abaixo ó meu deus do céu", "@EPTC", "10:29:34 10/10/1990", "https://pbs.twimg.com/profile_images/568363261/logo_EPTC_400x400.jpg"));
		items.add(new TwitterStatusViewModel("Caiu um trem queda abaixo ó meu deus do céu", "@EPTC", "10:29:34 10/10/1990", "https://pbs.twimg.com/profile_images/568363261/logo_EPTC_400x400.jpg"));
		items.add(new TwitterStatusViewModel("Caiu um trem queda abaixo ó meu deus do céu", "@EPTC", "10:29:34 10/10/1990", "https://pbs.twimg.com/profile_images/568363261/logo_EPTC_400x400.jpg"));

		return items;
	}


	private List<TwitterStatusViewModel> convertTwitterStatus(ResponseList<twitter4j.Status> statuses) {
		ArrayList<TwitterStatusViewModel> twitterStatusList = new ArrayList<TwitterStatusViewModel>();

		if (statuses == null || statuses.isEmpty())
			return twitterStatusList;

		for (twitter4j.Status s : statuses) {
			twitterStatusList.add(new TwitterStatusViewModel(s));
		}
		return twitterStatusList;
	}

	private static void sortTwitterStatusList(List<TwitterStatusViewModel> tweets) {
		Collections.sort(tweets, Collections.reverseOrder(new Comparator<TwitterStatusViewModel>() {
			public int compare(TwitterStatusViewModel obj1, TwitterStatusViewModel obj2) {
				return obj1.getDate().compareTo(obj2.getDate());
			}
		}));
	}


	@Override
	protected List<TwitterStatusViewModel> doInBackground(String... params) {
		List<TwitterStatusViewModel> tweets = new ArrayList<TwitterStatusViewModel>();

		if (params == null && params.length > 0)
			return tweets;

		String username = params[0];

		try {
			Thread.sleep(200);
			tweets = this.getTweets(username);
			tweetsResult.addAll(tweets);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}


		return tweets;
	}

	@Override
	protected void onPostExecute(List<TwitterStatusViewModel> list) {
		if (listener != null)
			listener.onTaskCompleted();
	}
}


