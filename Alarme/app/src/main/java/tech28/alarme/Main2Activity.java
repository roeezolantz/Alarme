package tech28.alarme;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.codinguser.android.contactpicker.ContactsPickerActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    private List<EventItem> eventsList;
    private RecyclerView mRecyclerView;
    private EventsRecycleAdapter adapter;

    private static final int GET_PHONE_NUMBER = 3007;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        final com.github.clans.fab.FloatingActionButton floatingActionButton =
                (com.github.clans.fab.FloatingActionButton) (findViewById(R.id.main_menu_fab));

        final Context cnt = this;

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(cnt, EventBuilder.class);

                startActivity(new Intent(cnt, ContactsPickerActivity.class));
            }
        });

        // Initialize recycler view
        mRecyclerView = (RecyclerView) findViewById(R.id.events_recycle);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Downloading data from below url
        final String url = "http://www.omdbapi.com/?t=Dad&y=2013&plot=short&r=json";
        new AsyncHttpTask().execute(url);

        startService(new Intent(this, TimeService.class));
    }
    public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            setProgressBarIndeterminateVisibility(true);
        }

        @Override
        protected Integer doInBackground(String... params) {
            Integer result = 0;
            HttpURLConnection urlConnection;
            try {
                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                int statusCode = urlConnection.getResponseCode();

                // 200 represents HTTP OK
                if (statusCode == 200) {
                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    parseResult(response.toString());
                    result = 1; // Successful
                } else {
                    result = 0; //"Failed to fetch data!";
                }
            } catch (Exception e) {
                Log.d("", e.getLocalizedMessage());
            }
            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
            // Download complete. Let us update UI

            if (result == 1) {
                adapter = new EventsRecycleAdapter(Main2Activity.this, eventsList);
                mRecyclerView.setAdapter(adapter);
            } else {
                Toast.makeText(Main2Activity.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void parseResult(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("");
            eventsList = new ArrayList<>();

            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.optJSONObject(i);
                EventItem item = new EventItem();
                item.setMessage(post.optString("Title"));

                eventsList.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
