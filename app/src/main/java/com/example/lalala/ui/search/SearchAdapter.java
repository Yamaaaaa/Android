package com.example.lalala.ui.search;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lalala.PaperActivity;
import com.example.lalala.R;
import com.example.lalala.entity.PaperSimpleData;
import com.example.lalala.entity.UserHistoryEntity;
import com.example.lalala.http.MessageResponse;
import com.example.lalala.shared_info.SaveUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> implements MessageResponse {
    private List<PaperSimpleData> searchResultPapers = new ArrayList<>();
    private Context context;
    private Intent intent;

    public SearchAdapter(List<PaperSimpleData> searchResultPapers, Context context) {
        this.context = context;
        this.searchResultPapers = searchResultPapers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ViewHolder holder, int position) {
        final PaperSimpleData paperSimpleData = searchResultPapers.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveUser.userHistoryEntities.add(paperSimpleData.getPaperEntity().getId());
                SaveUser.userHistoryPaperData.add(0, paperSimpleData);
                SaveUser.currentPaper = paperSimpleData;
            }
        });
        holder.title.setText(paperSimpleData.getPaperEntity().getTitle());
        holder.abst.setText(paperSimpleData.getPaperEntity().getAbst());
        holder.browseNum.setText("浏览量 " + paperSimpleData.getPaperEntity().getBrowseNum() + " · 热度 " + paperSimpleData.getPaperEntity().getRecentBrowseNum());
    }

    @Override
    public int getItemCount() {
        return searchResultPapers.size();
    }

    @Override
    public void onReceived(String resJson) {
        context.startActivity(intent);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView abst;
        public TextView browseNum;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            abst = itemView.findViewById(R.id.abst);
            browseNum = itemView.findViewById(R.id.browseNum);
        }
    }
}
