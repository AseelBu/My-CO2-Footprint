package com.androidcourse.energyconsumptiondiary_androidapp;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidcourse.energyconsumptiondiary_androidapp.Model.User;


import java.util.List;


public class MyLeaderboardRecyclerViewAdapter extends RecyclerView.Adapter<MyLeaderboardRecyclerViewAdapter.ViewHolder> {

    private final List<User> mUsers;

    public MyLeaderboardRecyclerViewAdapter(List<User> leadUsers) {
        mUsers = leadUsers;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_user_laederboard, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        User user = mUsers.get(position);
        holder.setData(user,position);

    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIndex;
        public final TextView  mName;
        public final ImageView  mImg;
        public final TextView  mPoints;
        public User user=null;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIndex = (TextView) view.findViewById(R.id.userIndex);
             mName = (TextView) view.findViewById(R.id.userName);
             mImg = (ImageView) view.findViewById(R.id.userImg);
             mPoints = (TextView) view.findViewById((R.id.userPoints));
        }

        public void setData(User user,int position){
            this.user=user;
            mIndex.setText(position);
            mName.setText(user.getName());
            mPoints.setText(user.getPoints());
            mImg.setImageDrawable(user.getImage());
        }


    }
}