package tech28.alarme;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    private Spinner dropDownChooser;
    private LinearLayout soldiersInfo;
    private LinearLayout commandersInfo;
    private Button btnSoldiersAccept;
    private Button btnCommandersAccept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.

        final Context cnt = this;

        soldiersInfo = (LinearLayout) findViewById(R.id.container_soldier);
        commandersInfo = (LinearLayout) findViewById(R.id.container_commander);

        btnSoldiersAccept = (Button) findViewById(R.id.btnAcceptSoldier);
        btnSoldiersAccept.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                new usersCreatorAsyncHttpTask().execute();
                //URI.create("http://10.56.3.220:3000/users/createSoldier").toURL();
         }
        });

        dropDownChooser = (Spinner) findViewById(R.id.job_dropdown);
        dropDownChooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: {
                        soldiersInfo.setVisibility(View.VISIBLE);
                        commandersInfo.setVisibility(View.INVISIBLE);
                        break;
                    }
                    case 1: {
                        soldiersInfo.setVisibility(View.INVISIBLE);
                        commandersInfo.setVisibility(View.VISIBLE);
                        break;
                    }
                    case 2: {
                        soldiersInfo.setVisibility(View.INVISIBLE);
                        commandersInfo.setVisibility(View.INVISIBLE);
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnCommandersAccept = (Button)findViewById(R.id.btnAcceptCommander);
        btnCommandersAccept.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(cnt, EventBuilder.class);

                startActivity(new Intent(cnt, commander_side.class));            }
        });
    }

    public class usersCreatorAsyncHttpTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            //setProgressBarIndeterminateVisibility(true);
        }

        @Override
        protected Integer doInBackground(String... params) {
            URL url = null;
            HttpURLConnection client = null;
            try {
                url = new URL("http://10.56.3.220/users/createSoldier");
                client = (HttpURLConnection) url.openConnection();

                client.setRequestMethod("POST");
                client.setRequestProperty("Key","Value");
                client.setDoOutput(true);

                OutputStream outputPost = new BufferedOutputStream(client.getOutputStream());
                writeStream(outputPost);
                outputPost.flush();
                outputPost.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(client != null) // Make sure the connection is not null.
                    client.disconnect();
            }
//            HttpClient httpClient = new DefaultHttpClient();
//            // replace with your url
//
//            HttpPost httpPost = null;
//            try {
//                httpPost = new HttpPost(new URI("10.56.3.220/users/createSoldier"));
//
//            } catch (URISyntaxException e) {
//                e.printStackTrace();
//            }
//            // /users/createSoldier
//
//            //Post Data
//            List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
//            nameValuePair.add(new BasicNameValuePair("username", "test_user"));
//            nameValuePair.add(new BasicNameValuePair("password", "123456789"));
//
//
//            //Encoding POST data
//            try {
//                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
//            } catch (UnsupportedEncodingException e) {
//                // log exception
//                e.printStackTrace();
//            }
//
//            //making POST request.
//            try {
//                HttpResponse response = httpClient.execute(httpPost);
//                // write response to log
//                Log.d("Http Post Response:", response.toString());
//            } catch (ClientProtocolException e) {
//                // Log exception
//                e.printStackTrace();
//            } catch (IOException e) {
//                // Log exception
//                e.printStackTrace();
//            }
//
//
//            HttpClient httpClient = new DefaultHttpClient();
//            // replace with your url
//
//            HttpPost httpPost = null;
//            try {
//                httpPost = new HttpPost(new URI("10.56.3.220/users/createSoldier"));
//
//            } catch (URISyntaxException e) {
//                e.printStackTrace();
//            }
//            // /users/createSoldier
//
//            //Post Data
//            List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
//            nameValuePair.add(new BasicNameValuePair("username", "test_user"));
//            nameValuePair.add(new BasicNameValuePair("password", "123456789"));
//
//
//            //Encoding POST data
//            try {
//                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
//            } catch (UnsupportedEncodingException e) {
//                // log exception
//                e.printStackTrace();
//            }
//
//            //making POST request.
//            try {
//                HttpResponse response = httpClient.execute(httpPost);
//                // write response to log
//                Log.d("Http Post Response:", response.toString());
//            } catch (ClientProtocolException e) {
//                // Log exception
//                e.printStackTrace();
//            } catch (IOException e) {
//                // Log exception
//                e.printStackTrace();
//            }


            return 0;
        }

        private void writeStream(OutputStream out) throws IOException {
            String output = "Hello world";

            out.write(output.getBytes());
            //out.flush();
        }

        @Override
        protected void onPostExecute(Integer result) {
            // Download complete. Let us update UI

            if (result == 1) {
            } else {
            }
        }
    }
}