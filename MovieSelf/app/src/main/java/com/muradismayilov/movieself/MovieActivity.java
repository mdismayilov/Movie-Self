package com.muradismayilov.movieself;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.squareup.picasso.Picasso;

public class MovieActivity extends AppCompatActivity {

    ImageView posterIV;
    TextView titleTV;
    TextView imdbTV;
    TextView typeTV;
    TextView yearTV;
    TextView releasedTV;
    TextView runtimeTV;
    TextView genreTV;
    TextView directorTV;
    TextView writerTV;
    TextView actorsTV;
    TextView plotTV;
    TextView languageTV;
    TextView countryTV;
    TextView awardsTV;
    TextView boxOfficeTV;
    TextView productionTV;
    TextView websiteTV;

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

    InterstitialAd interstitialAd;
    AdRequest adRequest_interstitial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        MobileAds.initialize(this,"ca-app-pub-3531666375863646/4918531137");
        adView_1 = findViewById(R.id.adView_1);
        AdRequest adRequest_1 = new AdRequest.Builder().build();
        adView_1.loadAd(adRequest_1);

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-3531666375863646/5471875536");
        adRequest_interstitial = new AdRequest.Builder().build();
        interstitialAd.loadAd(adRequest_interstitial);

        interstitialAd.setAdListener(new AdListener(){

            @Override
            public void onAdClosed() {
                super.onAdClosed();

                Intent intent = new Intent(MovieActivity.this, MainActivity.class);
                startActivity(intent);
                interstitialAd.loadAd(adRequest_interstitial);
            }
        });

        posterIV = findViewById(R.id.posterIV);
        titleTV = findViewById(R.id.titleTV);
        imdbTV = findViewById(R.id.imdbTV);
        typeTV = findViewById(R.id.typeTV);
        yearTV = findViewById(R.id.yearTV);
        releasedTV = findViewById(R.id.releasedTV);
        runtimeTV = findViewById(R.id.runtimeTV);
        genreTV = findViewById(R.id.genreTV);
        directorTV = findViewById(R.id.directorTV);
        writerTV = findViewById(R.id.writerTV);
        actorsTV = findViewById(R.id.actorsTV);
        plotTV = findViewById(R.id.plotTV);
        languageTV = findViewById(R.id.languageTV);
        countryTV = findViewById(R.id.countryTV);
        awardsTV = findViewById(R.id.awardsTV);
        boxOfficeTV = findViewById(R.id.boxOfficeTV);
        productionTV = findViewById(R.id.productionTV);
        websiteTV = findViewById(R.id.websiteTV);

        poster = getIntent().getStringExtra("poster");
        title = getIntent().getStringExtra("title");
        imdb = getIntent().getStringExtra("imdb");
        type = getIntent().getStringExtra("type");
        year = getIntent().getStringExtra("year");
        released = getIntent().getStringExtra("released");
        runtime = getIntent().getStringExtra("runtime");
        genre = getIntent().getStringExtra("genre");
        director = getIntent().getStringExtra("director");
        writer = getIntent().getStringExtra("writer");
        actors = getIntent().getStringExtra("actors");
        plot = getIntent().getStringExtra("plot");
        language = getIntent().getStringExtra("language");
        country = getIntent().getStringExtra("country");
        awards = getIntent().getStringExtra("awards");
        boxOffice = getIntent().getStringExtra("boxOffice");
        production = getIntent().getStringExtra("production");
        website = getIntent().getStringExtra("website");

        // Poster
        if(poster != null){
            Picasso.get().load(poster).into(posterIV);
        }
        else{
            Picasso.get().load(R.drawable.movieself).into(posterIV);
        }

        // Title
        if(title != null){
            titleTV.setText(title);
        }
        else{
            titleTV.setText("N/A");
        }

        // Imdb
        if(imdb != null){
            imdbTV.setText(imdb);
        }
        else{
            imdbTV.setText("N/A");
        }

        // Type
        if(type != null){
            typeTV.setText(type);
        }
        else{
            typeTV.setText("N/A");
        }

        // Year
        if(year != null){
            yearTV.setText(year);
        }
        else{
            yearTV.setText("N/A");
        }

        // Released
        if(released != null){
            releasedTV.setText(released);
        }
        else{
            releasedTV.setText("N/A");
        }

        // Runtime
        if(runtime != null){
            runtimeTV.setText(runtime);
        }
        else{
            runtimeTV.setText("N/A");
        }

        // Genre
        if(genre != null){
            genreTV.setText(genre);
        }
        else{
            genreTV.setText("N/A");
        }

        // Director
        if(director != null){
            directorTV.setText(director);
        }
        else{
            directorTV.setText("N/A");
        }

        // Writer
        if(writer != null){
            writerTV.setText(writer);
        }
        else{
            writerTV.setText("N/A");
        }

        // Actors
        if(actors != null){
            actorsTV.setText(actors);
        }
        else{
            actorsTV.setText("N/A");
        }

        // Plot
        if(plot != null){
            plotTV.setText(plot);
        }
        else{
            plotTV.setText("N/A");
        }

        // Language
        if(language != null){
            languageTV.setText(language);
        }
        else{
            languageTV.setText("N/A");
        }

        // Country
        if(country != null){
            countryTV.setText(country);
        }
        else{
            countryTV.setText("N/A");
        }

        // Awards
        if(awards != null){
            awardsTV.setText(awards);
        }
        else{
            awardsTV.setText("N/A");
        }

        // BoxOffice
        if(boxOffice != null){
            boxOfficeTV.setText(boxOffice);
        }
        else{
            boxOfficeTV.setText("N/A");
        }

        // Production
        if(production != null){
            productionTV.setText(production);
        }
        else{
            productionTV.setText("N/A");
        }

        // Website
        if(website != null){
            websiteTV.setText(website);
        }
        else{
            websiteTV.setText("N/A");
        }
    }

    @Override
    public void onBackPressed() {

        if(interstitialAd.isLoaded()){
            interstitialAd.show();
        }
    }
}