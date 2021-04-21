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
import com.example.lalala.entity.PaperEntity;
import com.example.lalala.entity.PaperSimpleData;
import com.example.lalala.entity.UserHistoryEntity;
import com.example.lalala.entity.UserSubscribeData;
import com.example.lalala.shared_info.SaveUser;

import java.util.ArrayList;
import java.util.List;

public class ShareListAdapter extends RecyclerView.Adapter<ShareListAdapter.ViewHolder> {

    private List<PaperSimpleData> userShareList;
    private Context context;
    private Intent intent;


    public ShareListAdapter(List<PaperSimpleData> userShareList, Context context) {
        this.userShareList = userShareList;
        this.context = context;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView paperAbstract;
        public TextView browseNum;
        public LinearLayout paperData;


        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.paper_title);
            paperAbstract = view.findViewById(R.id.paper_abstract);
            browseNum = view.findViewById(R.id.browse_num);
            paperData = view.findViewById(R.id.paper_data);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.share_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //设置历史记录的内容，要通过UserHistoryEntity中的paperId获取，先设置静态值
        System.out.println("share: " + "onBindViewHolder" + " position = " + position);
        final PaperSimpleData paperSimpleData = userShareList.get(position);
        holder.title.setText(paperSimpleData.getPaperEntity().getTitle());
        holder.paperAbstract.setText(paperSimpleData.getPaperEntity().getAbst());
        holder.browseNum.setText("浏览量 " + paperSimpleData.getPaperEntity().getBrowseNum() + " · 热度 " + paperSimpleData.getPaperEntity().getRecentBrowseNum());
        holder.paperData.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                SaveUser.currentPaper = paperSimpleData;
                SaveUser.userHistoryEntities.add(paperSimpleData.getPaperEntity().getId());
                SaveUser.userHistoryPaperData.add(0, paperSimpleData);
                intent = new Intent(context, PaperActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userShareList.size();
    }
}
