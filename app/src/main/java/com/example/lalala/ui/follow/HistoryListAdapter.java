package com.example.lalala.ui.follow;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lalala.PaperActivity;
import com.example.lalala.R;
import com.example.lalala.entity.PaperSimpleData;
import com.example.lalala.entity.UserHistoryEntity;
import com.example.lalala.entity.UserSubscribeData;
import com.example.lalala.shared_info.SaveUser;

import java.util.ArrayList;
import java.util.List;

public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.ViewHolder> {

    private List<PaperSimpleData> userHistoryDataList;
    private Context context;
    private Intent intent;


    public HistoryListAdapter(List<PaperSimpleData> userHistoryDataList, Context context) {
        this.userHistoryDataList = userHistoryDataList;
        this.context = context;
    }

    public List<PaperSimpleData> getUserHistoryDataList() {
        return userHistoryDataList;
    }

    public void setUserHistoryDataList(List<PaperSimpleData> userHistoryDataList) {
        this.userHistoryDataList = userHistoryDataList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView paperAbstract;
        public TextView browseTime;
        public LinearLayout paperData;


        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.paper_title);
            paperAbstract = view.findViewById(R.id.paper_abstract);
            browseTime = view.findViewById(R.id.browse_time);
            paperData = view.findViewById(R.id.paper_data);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //设置历史记录的内容，要通过UserHistoryEntity中的paperId获取，先设置静态值
        System.out.println("history: " + "onBindViewHolder" + " position = " + position);
        final PaperSimpleData paperSimpleData = userHistoryDataList.get(position);
        holder.title.setText(paperSimpleData.getPaperEntity().getTitle());
        holder.paperAbstract.setText(paperSimpleData.getPaperEntity().getAbst());
        holder.browseTime.setText("浏览量 " + paperSimpleData.getPaperEntity().getBrowseNum() + " · 热度 " + paperSimpleData.getPaperEntity().getRecentBrowseNum());
        holder.paperData.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                SaveUser.currentPaper = paperSimpleData;
                SaveUser.userHistoryEntities.remove(paperSimpleData);
                SaveUser.userHistoryPaperData.add(0, paperSimpleData);
                userHistoryDataList.remove(position);
                userHistoryDataList.add(0, paperSimpleData);
                notifyDataSetChanged();
                intent = new Intent(context, PaperActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userHistoryDataList.size();
    }
}
