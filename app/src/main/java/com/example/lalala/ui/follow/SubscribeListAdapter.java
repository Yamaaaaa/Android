package com.example.lalala.ui.follow;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lalala.PaperActivity;
import com.example.lalala.R;
import com.example.lalala.entity.PaperSimpleData;
import com.example.lalala.entity.SquarePaperData;
import com.example.lalala.entity.UserHistoryEntity;
import com.example.lalala.entity.UserSubscribeData;
import com.example.lalala.shared_info.SaveUser;
import com.example.lalala.ui.browse_fragment.SquareAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SubscribeListAdapter  extends RecyclerView.Adapter<SubscribeListAdapter.ViewHolder>{
    private List<UserSubscribeData> userSubscribeDataList;
    private Context context;
    private Intent intent;

    public SubscribeListAdapter(List<UserSubscribeData> userSubscribeDataList, Context context) {
        this.userSubscribeDataList = userSubscribeDataList;
        this.context = context;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView userName;
        public Button subscribe;
        //public TextView citeNum;

        public ViewHolder(View view) {
            super(view);
            userName = view.findViewById(R.id.user_name);
            subscribe = view.findViewById(R.id.subscribe);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subscribe_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        System.out.println("subscribe: " + "onBindViewHolder" + " position = " + position);
        final UserSubscribeData userSubscribeData = userSubscribeDataList.get(position);
        holder.userName.setText(userSubscribeData.getUserName());
        holder.subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userSubscribeDataList.get(position).isSubscribe()){
                    userSubscribeDataList.get(position).setSubscribe(false);
                    SaveUser.userSubscribeActionList.getActions().put(userSubscribeDataList.get(position).getUserId(), false);
                }else{
                    userSubscribeDataList.get(position).setSubscribe(true);
                    SaveUser.userSubscribeActionList.getActions().put(userSubscribeDataList.get(position).getUserId(), true);
                }
                notifyItemChanged(position);
            }
        });
        if(userSubscribeDataList.get(position).isSubscribe()) {
            holder.subscribe.setBackgroundResource(R.drawable.subscribe);
        }else{
            holder.subscribe.setBackgroundResource(R.drawable.unsubscribe);
        }
    }

    @Override
    public int getItemCount() {
        return userSubscribeDataList.size();
    }
}
