package com.example.matheus.transipoa;

import java.util.Comparator;

/**
 * Created by Matheus on 12/11/2014.
 */
public class DateComparator implements Comparator<TwitterStatusViewModel> {
	public int compare(TwitterStatusViewModel obj1, TwitterStatusViewModel obj2) {
		return obj1.getDateTimestamp().compareTo(obj2.getDateTimestamp());
	}
}