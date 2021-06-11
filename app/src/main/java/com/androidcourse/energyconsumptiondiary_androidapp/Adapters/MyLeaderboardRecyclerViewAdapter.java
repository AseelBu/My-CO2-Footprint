package com.androidcourse.energyconsumptiondiary_androidapp.Adapters;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.User;
import com.androidcourse.energyconsumptiondiary_androidapp.R;
import com.androidcourse.energyconsumptiondiary_androidapp.core.DataHolder;
import java.util.List;

public class MyLeaderboardRecyclerViewAdapter extends RecyclerView.Adapter<MyLeaderboardRecyclerViewAdapter.ViewHolder> {
    private DataHolder dh = DataHolder.getInstance();
    private final List<User> mUsers;
    private Context context;
    private boolean isColored=false;// true if first 3 rows are colored
    public boolean flag1=true;
    public boolean flag2=true;
    public boolean flag3=true;

    public MyLeaderboardRecyclerViewAdapter(Context context,List<User> leadUsers) {
        mUsers = leadUsers;
        this.context = context;
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
        holder.setData(user,position+1);
        if(!isColored) {
            if (position == 0&&flag1==true) {
                Log.d("flag1 is", String.valueOf(flag1));
                Log.d("position is :", String.valueOf(position));
                flag1=false;

                holder.mUserCard.setBackgroundColor(context.getResources().getColor(R.color.green1));
            } else if (position == 1&&flag2==true) {

                Log.d("flag2 is", String.valueOf(flag2));
                Log.d("position is :", String.valueOf(position));
                flag2=false;
                holder.mUserCard.setBackgroundColor(context.getResources().getColor(R.color.green2));
            } else if (position == 2&&flag3==true) {

                Log.d("flag3 is", String.valueOf(flag3));
                Log.d("position is :", String.valueOf(position));
                flag3=false;
                holder.mUserCard.setBackgroundColor(context.getResources().getColor(R.color.green3));
                isColored = true;
            }
        }
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
        public final LinearLayout mUserCard;
        public User user=null;

        public ViewHolder(View view) {
            super(view);
//            setIsRecyclable(false);
            mView = view;
            mIndex = (TextView) view.findViewById(R.id.userIndex);
            mName = (TextView) view.findViewById(R.id.userName);
            mImg = (ImageView) view.findViewById(R.id.userImg);
            mPoints = (TextView) view.findViewById((R.id.userPoints));
            mUserCard = (LinearLayout) view.findViewById((R.id.leaderboardUser));
        }

        public void setData(User user,int position){
            this.user=user;
            mIndex.setText(String.valueOf(position));
            mName.setText(user.getName());
            mPoints.setText(String.valueOf(user.getPoints()));
//            mImg.setImageDrawable(user.getImage());
        }
    }
}