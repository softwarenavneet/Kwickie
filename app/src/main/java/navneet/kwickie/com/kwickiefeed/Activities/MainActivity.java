package navneet.kwickie.com.kwickiefeed.Activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.List;

import navneet.kwickie.com.kwickiefeed.Adapters.KwickieAdapter;
import navneet.kwickie.com.kwickiefeed.HTTP.HttpManager;
import navneet.kwickie.com.kwickiefeed.Kwickie.Kwickie;
import navneet.kwickie.com.kwickiefeed.R;
import navneet.kwickie.com.kwickiefeed.jsonparser.JsonParser;

public class MainActivity extends ListActivity {

    private List<Kwickie> kwickieList;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lv = (ListView) findViewById(android.R.id.list);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent in = new Intent(MainActivity.this, KwickieVideoActivity.class);
                in.putExtra("VIDEOURL", kwickieList.get(position).getVideoUrl());

                startActivity(in);
            }
        });


        pb = (ProgressBar) findViewById(R.id.progressBar);
        pb.setVisibility(View.INVISIBLE);

        String loginUri = "https://bigdev.kwickie.com/api/members/login";

        BackgroundTask task = new BackgroundTask();
        task.execute(loginUri);

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


    private class BackgroundTask extends AsyncTask<String, String, List<Kwickie>> {

        @Override
        protected void onPreExecute() {
            pb.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Kwickie> doInBackground(String... params) {
            String loginResponse = HttpManager.post(params[0]);

            if (loginResponse != null) {
                String id = JsonParser.parseID(loginResponse);

                if (id != null) {
                    String dataUri = "https://bigdev.kwickie.com/api/kwickies/approved?access_token=" + id;
                    String dataResponse = HttpManager.get(dataUri);
                    kwickieList = JsonParser.parse(dataResponse);

                    return kwickieList;
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<Kwickie> kwickies) {
            if (kwickies != null) {
                displayKwickies(kwickies);
            }
            pb.setVisibility(View.INVISIBLE);
        }
    }

    private void displayKwickies(List<Kwickie> kwickies) {
        KwickieAdapter adapter = new KwickieAdapter(this, R.layout.item_kwickie, kwickies);
        setListAdapter(adapter);
    }
}