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
import com.example.lalala.entity.PaperEntity;
import com.example.lalala.entity.PaperSimpleData;
import com.example.lalala.entity.UserHistoryEntity;
import com.example.lalala.http.GetSubscribeUserTask;
import com.example.lalala.http.GetUserShareTask;
import com.example.lalala.http.MessageResponse;
import com.example.lalala.shared_info.SaveUser;
import com.example.lalala.ui.browse_fragment.PageViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ShareFragment extends Fragment implements MessageResponse {

    RecyclerView paperViewList;
    PageViewModel pageViewModel;
    List<PaperSimpleData> userShareList = new ArrayList<>();
    ShareListAdapter shareListAdapter;

    public static ShareFragment newInstance() {
        return new ShareFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        pageViewModel.setIndex(0);
        pageViewModel.getmIndex().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                getShare();
            }
        });
    }


    @Override
    public View onCreateView(
            @NonNull final LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_share, container, false);
        paperViewList = root.findViewById(R.id.shareList);
        shareListAdapter = new ShareListAdapter(userShareList, getActivity());
        paperViewList.setAdapter(shareListAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        paperViewList.setLayoutManager(layoutManager);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    }

    //获取历史记录
    private void getShare() {
        GetUserShareTask getUserShareTask = new GetUserShareTask();
        getUserShareTask.setMessageResponse(this);
        getUserShareTask.execute();
        try {
            getUserShareTask.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onReceived(String res) {
        System.out.println("userShareList: " + res);
        Gson gson = new Gson();
        Type type = new TypeToken<List<PaperSimpleData>>(){}.getType();
        List<PaperSimpleData> temp = gson.fromJson(res, type);
        userShareList.addAll((temp));
        shareListAdapter.notifyDataSetChanged();
    }
}
