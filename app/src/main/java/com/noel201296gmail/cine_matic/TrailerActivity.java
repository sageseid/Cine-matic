package com.noel201296gmail.cine_matic;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.noel201296gmail.cine_matic.Adapter.TrailerRecyclerAdapter;
import com.noel201296gmail.cine_matic.Model.MovieResponse;
import com.noel201296gmail.cine_matic.Model.TrailerResponse;
import com.noel201296gmail.cine_matic.Network.NetworkController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by OMOSEFE NOEL OBASEKI on 12/05/2017.
 */
public class TrailerActivity extends AppCompatActivity {


    //Base Url for TMDB
    private static final String API_BASE_URL = "http://api.themoviedb.org/3";
    //Key to access TMDB
    private static final String API_KEY = "INSERT_API_KEY_HERE";
    // Base Url for Youtube
    public static String BASE_URL_VIDEO = "https://www.youtube.com/watch?v=";

    Context mContext;


    List<TrailerResponse> trailersList = new ArrayList<TrailerResponse>();
    RequestQueue queue;
    RecyclerView recyclerView_2;
    TrailerRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_trailer);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent getTrailerId = getIntent();
        String ID_GET = getTrailerId.getStringExtra("id");


        recyclerView_2 = (RecyclerView) findViewById(R.id.my_recycler_view_3);
        adapter = new TrailerRecyclerAdapter(this, trailersList);
        recyclerView_2.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_2.setAdapter(adapter);
        queue = NetworkController.getInstance(this).getRequestQueue();


        Uri.Builder builder = Uri.parse(API_BASE_URL).buildUpon();
        builder.appendPath("movie").
                appendPath(ID_GET).
                appendPath("trailers").
                appendQueryParameter("api_key", API_KEY);
        String url_2 = builder.build().toString();


        final ProgressDialog loading = ProgressDialog.show(this, "Loading Data", "Please wait...");


        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, url_2, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Dismissing progress dialog
                        loading.dismiss();


                        try {

                            response.getInt("id");
                            response.getJSONArray("quicktime");
                            JSONArray jsonArray = response.getJSONArray("youtube");
                            Log.d("DEBUG", response.toString());
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                TrailerResponse dataSet = new TrailerResponse(jsonObject.getString("name"), jsonObject.getString("size"), jsonObject.getString("source"), jsonObject.getString("type"));


                                trailersList.add(dataSet);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

