package com.noel201296gmail.cine_matic;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import com.noel201296gmail.cine_matic.Adapter.MovieRecyclerAdapter;
import com.noel201296gmail.cine_matic.Model.MovieResponse;
import com.noel201296gmail.cine_matic.Network.NetworkController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {


    //Base Url for TMDB
    private static final String API_BASE_URL = "http://api.themoviedb.org/3";
    //Key to access TMDB
    private static final String API_KEY = "b7a6da7f6401f0bad741c3d311b15234";

    private String SortOrder;


    RequestQueue queue;
    RecyclerView recyclerView;
    List<MovieResponse> feedsList = new ArrayList<MovieResponse>();
    MovieRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        adapter = new MovieRecyclerAdapter(this, feedsList);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);
        queue = NetworkController.getInstance(this).getRequestQueue();
        final ProgressDialog loading = ProgressDialog.show(this, "Loading Data", "Please wait...");

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        Find_Order(sharedPreferences);

        Uri.Builder builder = Uri.parse(API_BASE_URL).buildUpon();
        builder.appendPath("movie").
                appendPath(SortOrder).
                appendQueryParameter("api_key", API_KEY);
        String url = builder.build().toString();


        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Dismissing progress dialog
                        loading.dismiss();


                        try {

                            response.getInt("page");
                            JSONArray jsonArray = response.getJSONArray("results");
                            Log.d("DEBUG", response.toString());
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                MovieResponse dataSet = new MovieResponse(jsonObject.getString("poster_path"),jsonObject.getBoolean("adult"),jsonObject.getString("overview"),
                                        jsonObject.getString("release_date"),jsonObject.getJSONArray("genre_ids"),jsonObject.getInt("id"),jsonObject.getString("original_title"),
                                        jsonObject.getString("original_language"),jsonObject.getString("title"),jsonObject.getString("backdrop_path"),jsonObject.getDouble("popularity"),
                                        jsonObject.getInt("vote_count"),jsonObject.getBoolean("video"),jsonObject.getDouble("vote_average") );


                                feedsList.add(dataSet);
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


    private String Find_Order(SharedPreferences sharedPreferences) {
        SortOrder = sharedPreferences.getString(getString(R.string.sort_by_key), getString(R.string.sort_by_default));
        return  SortOrder ;
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        Find_Order(sharedPreferences);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {

            case R.id.action_settings:

                Intent Tr = new Intent(this, SettingsActivity.class);
                startActivity(Tr);
                return true;
        }


        return super.onOptionsItemSelected(item);
    }

}
