package com.example.matheus.transipoa;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends Activity implements OnTaskCompleted {
	private StatusAdapter listAdapter;
	private TwitterService twitterService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main);
    }

	@Override
	protected void onStart() {
		super.onStart();

		this.listAdapter = new StatusAdapter(this, new ArrayList<TwitterStatusViewModel>());
		ListView listView = (ListView) findViewById(R.id.listView);
		listView.setAdapter(this.listAdapter);

		refreshListAsync();
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


	public void onClickBtn(View v) {
		showToast("Atualizando... aguarde");

		/* limpa tweets atuais */
		cleanList();

		/* atualiza os tweets */
		refreshListAsync();
	}


	private void refreshListAsync(){
		this.twitterService = TwitterService.twitterServiceFactory(this);
		List<TwitterStatusViewModel> tweets = new ArrayList<TwitterStatusViewModel>();

		try {
			List<String> usernames = Arrays.asList("EPTC_POA","transitozh");

			/* executa tarefa async */
			this.twitterService.execute(usernames);

			/* obtém resultado da tarefa */
			//tweets = twitterService.get();

			/* põe os tweets na view */
			//setListFromSource(tweets);
		} catch (Exception e) {
			showToast("Ocorreu um erro, tente novamente");
		}
	}

	private void cleanList(){
		/* limpa a lista do adapter */
		this.listAdapter.clear();

		/* notifica a alteração */
		this.listAdapter.notifyDataSetChanged();

		/* passa uma lista vazia para o adapter */
		//setListFromSource(new ArrayList<TwitterStatusViewModel>());
	}

	private void showToast(String message){
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}

	private void setListFromSource(List<TwitterStatusViewModel> tweets) {
		this.listAdapter.clear();
		this.listAdapter.addAll(tweets);
		this.listAdapter.notifyDataSetChanged();
	}

	public void onTaskCompleted() {
		List<TwitterStatusViewModel> tweets = this.twitterService.getTweetsResult();
		setListFromSource(tweets);
	}
}