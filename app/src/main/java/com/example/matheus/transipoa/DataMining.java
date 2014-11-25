package com.example.matheus.transipoa;

import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matheus on 24/11/2014.
 */
public class DataMining {
	public static void removeUselessTweets(List<TwitterStatusViewModel> tweets) {
		DataMining dm = new DataMining();
		Map<String, List<String>> filtros = dm.filtros;
		int aux = 0;

		for (int i = 0; i < tweets.size(); i++) {
			TwitterStatusViewModel t = tweets.get(i);
			String author = t.author.toUpperCase();

			/* verifica se possui filtros para o author */
			if(!filtros.containsKey(author))
				break;

			/* exclui o tweet se tiver alguma palavra filtrada */
			if(!containWordFromList(t.status, filtros.get(author)))
				tweets.remove(i);
		}
	}


	private static boolean containWordFromList(String input, List<String> words) {
		for(String w : words)
		{
			if(input.toLowerCase().contains(w.toLowerCase()))
				return true;
		}
		return false;
	}



	private Map<String, List<String>> filtros =  new HashMap<String, List<String>>() {{
		put("EPTC_POA", Arrays.asList("acidente", "samu", "engarrafamento", "atencão", "bloque", "colisão", "lento", "evit"));
	}};

}
