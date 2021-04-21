package com.example.lalala.http;

import android.os.AsyncTask;

import com.example.lalala.shared_info.SaveUser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FinalTask extends AsyncTask<Void, Void, Void> {
    @Override
    protected Void doInBackground(Void... voids) {
        Gson gson = new Gson();
        //1. 用户对论文的标签添加
        if(SaveUser.paperTagData.size() > 0){
            System.out.println("为论文添加Tag");
            System.out.println("paperTagData:"+SaveUser.paperTagData);
            HttpHandler.doPost(HttpHandler.tagUrl + "/addPaperTag", gson.toJson(SaveUser.paperTagData));
        }
        //2. 用户的历史记录
        if(SaveUser.userHistoryEntities.size() > 0){
            Map<Integer, Set<Integer>> historyData = new HashMap<>();
            historyData.put(SaveUser.userInfoEntity.getId(), SaveUser.userHistoryEntities);
            System.out.println("传输用户历史记录");
            System.out.println("historyData:"+historyData);
            HttpHandler.doPost(HttpHandler.recommendUrl + "/updateHistory", gson.toJson(historyData));
        }

        //3. 用户的订阅行为
        if(SaveUser.userSubscribeActionList.getActions().size() > 0){
            System.out.println("传输用户订阅行为");
            System.out.println("userSubscribeActionList:"+SaveUser.userSubscribeActionList.getActions());
            SaveUser.userSubscribeActionList.setUserId(SaveUser.userInfoEntity.getId());
            HttpHandler.doPost(HttpHandler.accountUrl + "/addSubscribe", gson.toJson(SaveUser.userSubscribeActionList));
        }

        //4. 用户的分享行为
        if(SaveUser.userShareActionList.getActions().size() > 0){
            System.out.println("传输用户分享行为");
            System.out.println("userShareActionList:"+SaveUser.userShareActionList.getActions());
            SaveUser.userShareActionList.setUserId(SaveUser.userInfoEntity.getId());
            HttpHandler.doPost(HttpHandler.accountUrl + "/userShare", gson.toJson(SaveUser.userShareActionList));
        }
        if(SaveUser.userDislikeList.getActions().size() > 0){
            System.out.println("传输用户不感兴趣");
            SaveUser.userDislikeList.setUserId(SaveUser.userInfoEntity.getId());
            System.out.println("userDislikeList:"+SaveUser.userDislikeList.getActions());
            HttpHandler.doPost(HttpHandler.recommendUrl + "/userDislike", gson.toJson(SaveUser.userDislikeList));
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
