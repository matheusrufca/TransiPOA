package com.example.matheus.transipoa;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Matheus on 24/11/2014.
 */

public class TimeAgo {
	private static final List<Long> times = Arrays.asList(
			TimeUnit.DAYS.toMillis(365),
			TimeUnit.DAYS.toMillis(30),
			TimeUnit.DAYS.toMillis(1),
			TimeUnit.HOURS.toMillis(1),
			TimeUnit.MINUTES.toMillis(1),
			TimeUnit.SECONDS.toMillis(1));

	private static final List<String> timesString = Arrays.asList("ano", "mês", "dia", "hora", "minuto", "segundo");

	public static String toDuration(long duration) {
		StringBuffer output = new StringBuffer();

		for (int i = 0; i < times.size(); i++) {
			Long current = times.get(i);
			long temp = duration / current;

			if (temp > 0) {
				output.append(temp).append(" ").append(timesString.get(i)).append(temp > 1 ? "s" : "");
				break;
			}
		}

		if (output.toString().equals(""))
			output.append("0 segundos");

		return output.toString();
	}
}