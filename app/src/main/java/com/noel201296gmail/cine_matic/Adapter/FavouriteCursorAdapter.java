package com.noel201296gmail.cine_matic.Adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.noel201296gmail.cine_matic.Data.MovieContract;
import com.noel201296gmail.cine_matic.Model.MovieResponse;
import com.noel201296gmail.cine_matic.MovieDetailActivity;
import com.noel201296gmail.cine_matic.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by OMOSEFE NOEL OBASEKI on 14/05/2017.
 */
public class FavouriteCursorAdapter extends RecyclerView.Adapter<FavouriteCursorAdapter.MyViewHolder_1> {

    private Cursor mCursor;
    private Context mContext ;


    public FavouriteCursorAdapter (Context context ){

        mContext=context;
    }

    @Override
    public MyViewHolder_1 onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item_view, parent, false);
        return new MyViewHolder_1(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder_1 holder, int position) {

        int idIndexRating = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_RATING);
        int descriptionIndex = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_NAME);
        int picIndex = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_PIC);
        mCursor.moveToPosition(position);
         String rating = String.valueOf(mCursor.getInt(idIndexRating));
        String description = mCursor.getString(descriptionIndex);


        holder.mTVTitle.setText(description);
        holder.mTVRating.setText(rating);
    }

    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
    }

    public class MyViewHolder_1 extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTVTitle;
        private CardView mCardView;
        private ImageView mIVThumbNail;
        private TextView mTVRating;

        public MyViewHolder_1(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTVTitle = (TextView) itemView.findViewById(R.id.tv_title);
            mTVRating = (TextView) itemView.findViewById(R.id.tv_rating);
            mIVThumbNail = (ImageView) itemView.findViewById(R.id.iv_thumbnail);
            mCardView = (CardView) itemView.findViewById(R.id.card_view);


        }


        @Override
        public void onClick(View v) {
        }
    }
    public Cursor swapCursor(Cursor c) {
        // check if this cursor is the same as the previous cursor (mCursor)
        if (mCursor == c) {
            return null; // bc nothing has changed
        }
        Cursor temp = mCursor;
        this.mCursor = c; // new cursor value assigned

        //check if this is a valid cursor, then update the cursor
        if (c != null) {
            this.notifyDataSetChanged();
        }
        return temp;
    }

}
