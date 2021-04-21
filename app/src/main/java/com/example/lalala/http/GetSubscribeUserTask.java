package com.example.lalala.http;

import android.os.AsyncTask;

import com.example.lalala.shared_info.SaveUser;


//获取用户订阅的其他用户列表，数据类型为List<UserSubscribeData>
public class GetSubscribeUserTask extends AsyncTask<Void, Void, String> {
    private MessageResponse messageResponse;

    public void setMessageResponse(MessageResponse messageResponse) {
        this.messageResponse = messageResponse;
    }

    @Override
    protected String doInBackground(Void... voids) {
        return HttpHandler.doGet(HttpHandler.accountUrl + "/userSubscribe?userId="+ SaveUser.userInfoEntity.getId(), "");
    }

    @Override
    protected void onPostExecute(String res) {
        messageResponse.onReceived(res);
    }
}
