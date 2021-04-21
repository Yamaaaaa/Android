package com.example.lalala.http;

import android.os.AsyncTask;

import com.example.lalala.shared_info.SaveUser;
import com.google.gson.Gson;

public class GetUserHistoryTask extends AsyncTask<Void, Void, String> {

    private MessageResponse messageResponse;

    public void setMessageResponse(MessageResponse messageResponse) {
        this.messageResponse = messageResponse;
    }

    @Override
    protected String doInBackground(Void... voids) {
        return HttpHandler.doGet(HttpHandler.paperUrl + "/userHistory?userId="+ SaveUser.userInfoEntity.getId(), "");
    }

    @Override
    protected void onPostExecute(String res) {
        messageResponse.onReceived(res);
    }
}
