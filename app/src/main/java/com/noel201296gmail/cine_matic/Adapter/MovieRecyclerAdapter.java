package com.noel201296gmail.cine_matic.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

;
import com.noel201296gmail.cine_matic.Model.MovieResponse;
import com.noel201296gmail.cine_matic.MovieDetailActivity;
import com.noel201296gmail.cine_matic.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by OMOSEFE NOEL OBASEKI on 04/05/2017.
 */
public class MovieRecyclerAdapter extends RecyclerView.Adapter<MovieRecyclerAdapter.MyViewHolder>{



    private List<MovieResponse> mMovieList ;
    private Context mContext ;


    public MovieRecyclerAdapter(Context context , List<MovieResponse> MovieList){

        mContext=context;
        mMovieList=MovieList;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mTVTitle ;
        private CardView mCardView ;
        private  ImageView mIVThumbNail;
        private TextView mTVRating;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTVTitle = (TextView) itemView.findViewById(R.id.tv_title);
            mTVRating = (TextView) itemView.findViewById(R.id.tv_rating);
            mIVThumbNail = (ImageView) itemView.findViewById(R.id.iv_thumbnail);
            mCardView = (CardView) itemView.findViewById(R.id.card_view);


        }


        @Override
        public void onClick(View view) {
            int itemPosition = getAdapterPosition();
            MovieResponse movie = mMovieList.get(itemPosition);
            Intent MovieDetail = new Intent(mContext, MovieDetailActivity.class);
               MovieDetail.putExtra("Details",movie);
            //   MovieDetail.putExtras("Details", movie);
            mContext.startActivity(MovieDetail);


        }
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mTVTitle.setText(mMovieList.get(position).getTitle());
        holder.mTVRating.setText(String.valueOf(mMovieList.get(position).getVoteAverage()));
        String completePosterPath = mMovieList.get(position).getPosterPath();
        Picasso.with(mContext)
                .load(completePosterPath)
                .placeholder(R.color.colorAccent)
                .into(holder.mIVThumbNail);
    }



    @Override
    public int getItemCount() {
        return mMovieList.size();
    }
}
