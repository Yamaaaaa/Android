package com.example.lalala.http;

import android.os.AsyncTask;

import com.example.lalala.shared_info.SaveUser;
import com.google.gson.Gson;


//获取用户分享的论文列表，获取的数据类型为List<PaperSimpleData>
public class GetUserShareTask extends AsyncTask<Void, Void, String> {

    private MessageResponse messageResponse;

    public void setMessageResponse(MessageResponse messageResponse) {
        this.messageResponse = messageResponse;
    }

    @Override
    protected String doInBackground(Void... voids) {
        return HttpHandler.doGet(HttpHandler.paperUrl + "/userSharePaper?userId="+ SaveUser.userInfoEntity.getId(), "");
    }

    @Override
    protected void onPostExecute(String res) {
        messageResponse.onReceived(res);
    }
}
