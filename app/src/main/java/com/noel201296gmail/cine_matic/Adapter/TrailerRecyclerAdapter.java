package com.noel201296gmail.cine_matic.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.noel201296gmail.cine_matic.Model.TrailerResponse;
import com.noel201296gmail.cine_matic.R;

import com.noel201296gmail.cine_matic.TrailerActivity;
import com.noel201296gmail.cine_matic.TrailerActivity.*;

import java.util.List;

/**
 * Created by OMOSEFE NOEL OBASEKI on 12/05/2017.
 */
public class TrailerRecyclerAdapter extends RecyclerView.Adapter<TrailerRecyclerAdapter.MyViewHolder> {

    public static final String YouTubeBaseUrl ="https://www.youtube.com/watch";

    List<TrailerResponse> mTrailerList ;
  Context mContext;

   public  TrailerRecyclerAdapter(Context context , List<TrailerResponse> TrailerList) {
       mTrailerList = TrailerList ;
       mContext = context;
   }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
              TextView mName;
              TextView mType;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        mName= (TextView) itemView.findViewById(R.id.NAME);
        mType= (TextView) itemView.findViewById(R.id.TYPE);
        }

        @Override
        public void onClick(View v) {
            int itemPosition = getAdapterPosition();
            TrailerResponse trailer = mTrailerList.get(itemPosition);
            String source = mTrailerList.get(itemPosition).getSource();
            String url = "https://www.youtube.com/watch?v=" + source;
            Uri webpage = Uri.parse(url);
            Intent i = new Intent(Intent.ACTION_VIEW,webpage);
            mContext.startActivity(i);
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_item_view  , parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mName.setText(mTrailerList.get(position).getName());
        holder.mType.setText(mTrailerList.get(position).getType());
    }

    @Override
    public int getItemCount() {
        return mTrailerList.size() ;
    }



}
