package com.example.lalala.ui.follow;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lalala.R;
import com.example.lalala.entity.PaperSimpleData;
import com.example.lalala.entity.UserHistoryEntity;
import com.example.lalala.http.GetSubscribeUserTask;
import com.example.lalala.http.GetUserHistoryTask;
import com.example.lalala.http.MessageResponse;
import com.example.lalala.shared_info.SaveUser;
import com.example.lalala.ui.browse_fragment.EndlessRecyclerOnScrollListener;
import com.example.lalala.ui.browse_fragment.PageViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class HistoryFragment extends Fragment implements MessageResponse {

    RecyclerView paperViewList;
    PageViewModel pageViewModel;
    HistoryListAdapter historyListAdapter;
    List<PaperSimpleData> userHistoryList = SaveUser.userHistoryPaperData;

    public static HistoryFragment newInstance() {
        return new HistoryFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        pageViewModel.setIndex(0);
        pageViewModel.getmIndex().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                getHistory();
            }
        });
    }


    @Override
    public View onCreateView(
            @NonNull final LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        System.out.println("History: onCreateView");
        View root = inflater.inflate(R.layout.fragment_history, container, false);
        paperViewList = root.findViewById(R.id.historyList);
        historyListAdapter = new HistoryListAdapter(userHistoryList, getActivity());
        paperViewList.setAdapter(historyListAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        paperViewList.setLayoutManager(layoutManager);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    }

    //??????????????????
    private void getHistory() {
        GetUserHistoryTask getUserHistoryTask = new GetUserHistoryTask();
        getUserHistoryTask.setMessageResponse(this);
        getUserHistoryTask.execute();
        try {
            getUserHistoryTask.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onReceived(String res) {
        System.out.println("userHistoryData:" + res);
        Type type = new TypeToken<List<PaperSimpleData>>() {
        }.getType();
        Gson gson = new Gson();
        List<PaperSimpleData> temp = gson.fromJson(res, type);
        for(PaperSimpleData paperSimpleData: temp){
            if(!SaveUser.userHistoryEntities.contains(paperSimpleData.getPaperEntity().getId())){
                userHistoryList.add(paperSimpleData);
                SaveUser.userHistoryEntities.add(paperSimpleData.getPaperEntity().getId());
            }
        }
        System.out.println("userHistoryDataSize:" + userHistoryList.size());
        historyListAdapter.notifyDataSetChanged();
        System.out.println("userHistoryDataSize:" + historyListAdapter.getItemCount());
    }
}
