package com.noel201296gmail.cine_matic.Adapter;

import android.content.ContentValues;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.noel201296gmail.cine_matic.Model.ReviewResponse;
import com.noel201296gmail.cine_matic.R;

import java.util.List;

/**
 * Created by OMOSEFE NOEL OBASEKI on 09/05/2017.
 */
public class ReviewRecyclerAdapter extends RecyclerView.Adapter<ReviewRecyclerAdapter.MyViewHolder>{

    private List<ReviewResponse> mReviewList ;
    private Context mContext;




    public ReviewRecyclerAdapter(List<ReviewResponse> Reviewlist , Context context){
       mContext = context ;
       mReviewList = Reviewlist ;

    }
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mAuthor ;
        TextView mReview ;

        public MyViewHolder(View itemView) {
            super(itemView);
            mAuthor= (TextView) itemView.findViewById(R.id.AUTHOR);
            mReview = (TextView) itemView.findViewById(R.id.REVIEW);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item_view  , parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mAuthor.setText(mReviewList.get(position).getAuthor());
        holder.mReview.setText(mReviewList.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return mReviewList.size();
    }

}
