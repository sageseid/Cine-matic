package com.noel201296gmail.cine_matic;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import com.noel201296gmail.cine_matic.Adapter.ReviewRecyclerAdapter;
import com.noel201296gmail.cine_matic.Model.MovieResponse;
import com.noel201296gmail.cine_matic.Model.ReviewResponse;
import com.noel201296gmail.cine_matic.Network.NetworkController;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by OMOSEFE NOEL OBASEKI on 08/05/2017.
 */
public class MovieDetailActivity extends AppCompatActivity {

    RequestQueue queue;

    //Base Url for TMDB
    private static final String API_BASE_URL = "http://api.themoviedb.org/3";
    //Key to access TMDB
    private static final String API_KEY = "b7a6da7f6401f0bad741c3d311b15234";

    List<ReviewResponse> ReviewList = new ArrayList<ReviewResponse>();


    ReviewRecyclerAdapter adapter;
    CoordinatorLayout mCoordinatorLayout;
    CollapsingToolbarLayout mCollapsingToolBar;
    RecyclerView mRecyclerView;
    ImageView mIvBackDrop;
    TextView mTvTitle;
    TextView mTvReleaseDate;
    TextView mTvRating;
    TextView mTvOverview;
    ImageView mIvPoster;
    FloatingActionButton mButtonFavorite;
    FloatingActionButton mButtonTrailer;
    FloatingActionButton mButtonShare;
    Context mContext;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_details);
        Intent intent = getIntent();
        MovieResponse films = intent.getParcelableExtra("Details");

        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinate_layout);
        mCollapsingToolBar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mIvBackDrop = (ImageView) findViewById(R.id.iv_backdrop);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvReleaseDate = (TextView) findViewById(R.id.tv_release);
        mTvRating = (TextView) findViewById(R.id.tv_rating);
        mTvOverview = (TextView) findViewById(R.id.tv_movie_overview);
        mIvPoster = (ImageView) findViewById(R.id.iv_poster);
        mButtonFavorite = (FloatingActionButton) findViewById(R.id.fab_favorite);
        mButtonTrailer = (FloatingActionButton) findViewById(R.id.fab_trailer);
        mButtonShare = (FloatingActionButton) findViewById(R.id.fab_share);


        mCollapsingToolBar.setTitle(films.getOriginalTitle());
        mTvTitle.setText(films.getOriginalLanguage());
        mRecyclerView= (RecyclerView) findViewById(R.id.my_recycler_view_1);

     //   mTvReleaseDate.setText(finalDateStr);
        mTvRating.setText(String.valueOf(films.getVoteAverage()));
        mTvOverview.setText(films.getOverview());

        Picasso.with(mContext)
                .load(films.getBackdropPath())
                .placeholder(R.color.colorAccent)
                .into(mIvBackDrop);


        Picasso.with(mContext)
                .load(films.getPosterPath())
                .placeholder(R.color.colorAccent)
                .into(mIvPoster);

        adapter = new ReviewRecyclerAdapter(ReviewList,this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
        queue = NetworkController.getInstance(this).getRequestQueue();
        final ProgressDialog loading = ProgressDialog.show(this, "Loading Data", "Please wait...");

        Uri.Builder builder = Uri.parse(API_BASE_URL).buildUpon();
        builder.appendPath("movie").
                appendPath(films.getId().toString()).
                appendPath("reviews").
                appendQueryParameter("api_key", API_KEY);
        String url = builder.build().toString();


        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Dismissing progress dialog
                        loading.dismiss();


                        try {

                            response.getInt("id");
                            response.getInt("page");
                            JSONArray jsonArray = response.getJSONArray("results");
                            Log.d("DEBUG", response.toString());
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                ReviewResponse dataSet_1 = new ReviewResponse(jsonObject.getString  ("id"),jsonObject.getString("author"),
                                        jsonObject.getString("content"),jsonObject.getString("url") );


                                ReviewList.add(dataSet_1);
                                adapter.notifyItemChanged(i);


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if (error instanceof NetworkError) {
                                    loading.setCancelable(true);
                                    Toast.makeText(getApplicationContext(), "Network Error!....Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                                } else if (error instanceof ServerError) {
                                    loading.setCancelable(true);
                                    Toast.makeText(getApplicationContext(), "Server Side Error!...The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                                } else if (error instanceof ParseError) {
                                    loading.setCancelable(true);
                                    Toast.makeText(getApplicationContext(), "Parsing error!......Please try again after some time!!", Toast.LENGTH_SHORT).show();
                                } else if (error instanceof NoConnectionError) {
                                    loading.setCancelable(true);
                                    Toast.makeText(getApplicationContext(), "Connection Error!.....Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                                } else if (error instanceof TimeoutError) {
                                    loading.setCancelable(true);
                                    Toast.makeText(getApplicationContext(), "Timeout Error!......Connection TimeOut! Please check your internet connect   ion.", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
        queue.add(jsonRequest);

    }
}