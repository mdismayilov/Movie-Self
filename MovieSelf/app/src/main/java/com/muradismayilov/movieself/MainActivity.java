package com.muradismayilov.movieself;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int TRIGGER_AUTO_COMPLETE = 100;
    private static final long AUTO_COMPLETE_DELAY = 100;
    private Handler handler;
    private AutoSuggestAdapter autoSuggestAdapter;

    String poster = "";
    String title = "";
    String imdb = "";
    String type = "";
    String year = "";
    String released = "";
    String runtime = "";
    String genre = "";
    String director = "";
    String writer = "";
    String actors = "";
    String plot = "";
    String language = "";
    String country = "";
    String awards = "";
    String boxOffice = "";
    String production = "";
    String website = "";

    AdView adView_1;

    TextView privacy_policyTV, supportTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this,"ca-app-pub-3531666375863646/4918531137");
        adView_1 = findViewById(R.id.adView_1);
        AdRequest adRequest_1 = new AdRequest.Builder().build();
        adView_1.loadAd(adRequest_1);

        supportTV = findViewById(R.id.supportTV);
        supportTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Support");
                builder.setMessage("If you want to support me, just click the ads or review on Google Play :)");
                builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });

        final AppCompatAutoCompleteTextView autoCompleteTextView =
                findViewById(R.id.auto_complete_edit_text);

        //Setting up the adapter for AutoSuggest
        autoSuggestAdapter = new AutoSuggestAdapter(getApplicationContext(),
                android.R.layout.simple_dropdown_item_1line);
        autoCompleteTextView.setThreshold(2);
        autoCompleteTextView.setDropDownBackgroundResource(R.color.colorPrimaryDark);
        autoCompleteTextView.setAdapter(autoSuggestAdapter);
        autoCompleteTextView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {


                        DownloadData downloadData = new DownloadData();

                        try{

                            String yeah = "";
                            String[] liste = autoSuggestAdapter.getObject(position).split(" ");
                            for(String s : liste){
                                if((liste[liste.length-1]) != s){
                                    yeah += s +"+";
                                }
                                else{
                                    yeah += s+"";
                                }
                            }

                            String url = "http://www.omdbapi.com/?t=" + yeah + "&apikey=cdcc8a12";
                            downloadData.execute(url);



                        }
                        catch (Exception e){

                            e.printStackTrace();
                        }

                    }
                });

        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int
                    count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                handler.removeMessages(TRIGGER_AUTO_COMPLETE);
                handler.sendEmptyMessageDelayed(TRIGGER_AUTO_COMPLETE,
                        AUTO_COMPLETE_DELAY);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == TRIGGER_AUTO_COMPLETE) {
                    if (!TextUtils.isEmpty(autoCompleteTextView.getText())) {
                        makeApiCall(autoCompleteTextView.getText().toString());
                    }
                }
                return false;
            }
        });

        privacy_policyTV = findViewById(R.id.privacy_policyTV);
        privacy_policyTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://movieselfapp.blogspot.com/p/privacy-policy.html");
                Intent privacy_policy_intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(privacy_policy_intent);
            }
        });

    }



    private void makeApiCall(String text) {
        ApiCall.make(getApplicationContext(), text, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //parsing logic, please change it as per your requirement
                List<String> stringList = new ArrayList<>();
                try {
                    JSONObject responseObject = new JSONObject(response);
                    JSONArray array = responseObject.getJSONArray("Search");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject row = array.getJSONObject(i);
                        stringList.add(row.getString("Title"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //IMPORTANT: set data here and notify
                autoSuggestAdapter.setData(stringList);
                autoSuggestAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }


    private class DownloadData extends AsyncTask<String,Void,String> {



        @Override
        protected String doInBackground(String... strings) {

            String result = "";
            URL url;
            HttpURLConnection httpURLConnection;

            try{

                url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                int data = inputStreamReader.read();

                while (data > 0){

                    char character = (char)data;
                    result += character;

                    data = inputStreamReader.read();

                }

                return result;
            }
            catch (Exception e){
                return null;
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try{
                JSONObject jsonObject = new JSONObject(s);

                poster = jsonObject.getString("Poster");
                title = jsonObject.getString("Title");
                imdb = jsonObject.getString("imdbRating");
                type = jsonObject.getString("Type");
                year = jsonObject.getString("Year");
                released = jsonObject.getString("Released");
                runtime = jsonObject.getString("Runtime");
                genre = jsonObject.getString("Genre");
                director = jsonObject.getString("Director");
                writer = jsonObject.getString("Writer");
                actors = jsonObject.getString("Actors");
                plot = jsonObject.getString("Plot");
                language = jsonObject.getString("Language");
                country = jsonObject.getString("Country");
                awards = jsonObject.getString("Awards");

                if(type.equals("movie")) {

                    boxOffice = jsonObject.getString("BoxOffice");
                    production = jsonObject.getString("Production");
                    website = jsonObject.getString("Website");

                    Intent intent = new Intent(MainActivity.this, MovieActivity.class);
                    intent.putExtra("poster", poster);
                    intent.putExtra("title", title);
                    intent.putExtra("imdb", imdb);
                    intent.putExtra("type", type);
                    intent.putExtra("year", year);
                    intent.putExtra("released", released);
                    intent.putExtra("runtime", runtime);
                    intent.putExtra("genre", genre);
                    intent.putExtra("director", director);
                    intent.putExtra("writer", writer);
                    intent.putExtra("actors", actors);
                    intent.putExtra("plot", plot);
                    intent.putExtra("language", language);
                    intent.putExtra("country", country);
                    intent.putExtra("awards", awards);
                    intent.putExtra("boxOffice", boxOffice);
                    intent.putExtra("production", production);
                    intent.putExtra("website", website);
                    startActivity(intent);
                }
                else{

                    Intent intent = new Intent(MainActivity.this, MovieActivity.class);
                    intent.putExtra("poster", poster);
                    intent.putExtra("title", title);
                    intent.putExtra("imdb", imdb);
                    intent.putExtra("type", type);
                    intent.putExtra("year", year);
                    intent.putExtra("released", released);
                    intent.putExtra("runtime", runtime);
                    intent.putExtra("genre", genre);
                    intent.putExtra("director", director);
                    intent.putExtra("writer", writer);
                    intent.putExtra("actors", actors);
                    intent.putExtra("plot", plot);
                    intent.putExtra("language", language);
                    intent.putExtra("country", country);
                    intent.putExtra("awards", awards);
                    intent.putExtra("boxOffice", "-");
                    intent.putExtra("production", "-");
                    intent.putExtra("website", "-");
                    startActivity(intent);
                }


                View view2 = (View) getWindow().getDecorView().getRootView().getWindowToken();
                view2.clearFocus();





            }
            catch (Exception e){

                e.printStackTrace();
            }
        }
    }
}
