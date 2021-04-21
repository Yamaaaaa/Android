package com.example.lalala;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lalala.shared_info.SaveUser;
import com.example.lalala.tool.PixelTool;
import com.google.android.flexbox.FlexboxLayout;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PaperActivity extends AppCompatActivity {
    private ScrollView scrollView;
    private LinearLayout linearLayout;
    private FlexboxLayout flexboxLayout_exist;
    private FlexboxLayout flexboxLayout_userAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paper);

        //从当前打开的论文中获取相关信息并显示
        TextView tvPaperTitle = findViewById(R.id.tv_paper_title);
        TextView tvPaperAbstract = findViewById(R.id.tv_paper_abstract);
        tvPaperTitle.setText(SaveUser.currentPaper.getPaperEntity().getTitle());


        tvPaperAbstract.setText(SaveUser.currentPaper.getPaperEntity().getAbst());

        //显示已有tags
        flexboxLayout_exist = findViewById(R.id.fl_tags);
        for (String tag : SaveUser.currentPaper.getTags()) {
            newButton(tag,flexboxLayout_exist);
        }

        flexboxLayout_userAdd = findViewById(R.id.fl_user_add_tags);
        if(SaveUser.paperTagData.containsKey(SaveUser.currentPaper.getPaperEntity().getId())) {
            for (String tag : SaveUser.paperTagData.get(SaveUser.currentPaper.getPaperEntity().getId())) {
                newButton(tag, flexboxLayout_userAdd);
            }
        }

//        //显示添加的tags
//        flexboxLayout_add = findViewById(R.id.fl_add_tags);
//        for (TagSimpleData tag : SaveUser.curPaper.getRecomTag()) {
//            newButton(tag.getName(), flexboxLayout_add);
//        }

        Button btnSubmit = findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    @Override
    protected void onDestroy() {

        Log.d("PaperActivity", "onDestroy: begin final");
        super.onDestroy();
    }

    public void Add_Tag(View view) {
        final EditText et = new EditText(this);
        et.setHint("请输入标签名");
        new AlertDialog.Builder(this).setTitle("请输入要添加的Tag名称")
                .setView(et)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String tagName = et.getText().toString();
                        if(SaveUser.paperTagData.containsKey(SaveUser.currentPaper.getPaperEntity().getId())) {
                            if (SaveUser.paperTagData.get(SaveUser.currentPaper.getPaperEntity().getId()).contains(tagName)) {
                                Toast toast = Toast.makeText(PaperActivity.this, "标签已存在！", Toast.LENGTH_SHORT);
                                toast.show();
                            } //else if(SaveUser.currentPaper.getTags().contains(tagName)) {

                            //}
                            else {
                                SaveUser.paperTagData.get(SaveUser.currentPaper.getPaperEntity().getId()).add(tagName);
                                newButton(tagName, flexboxLayout_userAdd);
                            }
                        }else{
                            SaveUser.paperTagData.put(SaveUser.currentPaper.getPaperEntity().getId(), new HashSet<String>());
                            SaveUser.paperTagData.get(SaveUser.currentPaper.getPaperEntity().getId()).add(tagName);
                            newButton(tagName, flexboxLayout_userAdd);
                        }
                        System.out.println("paperTagData: " + SaveUser.paperTagData);
                        assert SaveUser.paperTagData.get(SaveUser.currentPaper.getPaperEntity().getId()).size() > 0;
                    }
                }).setNegativeButton("取消", null).show();
    }

    //在自定义标签框里添加标签
    private void newButton(final String tagName, FlexboxLayout flexboxLayout) {
        Button btn_tag = new Button(PaperActivity.this);
        btn_tag.setText(tagName);
        btn_tag.setBackgroundResource(R.drawable.tag_outline);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, PixelTool.dpToPx(PaperActivity.this, 30));
        lp.setMargins(PixelTool.dpToPx(PaperActivity.this, 5),
                PixelTool.dpToPx(PaperActivity.this, 8),
                PixelTool.dpToPx(PaperActivity.this, 5),
                PixelTool.dpToPx(PaperActivity.this, 8));

        btn_tag.setPadding(PixelTool.dpToPx(PaperActivity.this, 10),
                PixelTool.dpToPx(PaperActivity.this, 0),
                PixelTool.dpToPx(PaperActivity.this, 10),
                PixelTool.dpToPx(PaperActivity.this, 0));

        btn_tag.setTextSize(18);
        btn_tag.setLayoutParams(lp);

//        switch (flexboxLayout.getId()) {
//            case R.id.fl_tags:
//                break;
//            case R.id.fl_add_tags:
//                btn_tag.setOnClickListener(new onClickAdd());
//                break;
//        }

        flexboxLayout.addView(btn_tag);
    }


    //弹出对话框
    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("提交成功!");
        builder.setPositiveButton("我知道了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

//    class onClickRec implements View.OnClickListener {
//        @Override
//        public void onClick(View v) {
//            String tagName = ((Button) v).getText().toString();
//            if (tagNames.contains(tagName)) {
//                Toast toast = Toast.makeText(PaperActivity.this, "标签已存在！", Toast.LENGTH_SHORT);
//                toast.show();
//            } else {
//                tagNames.add(tagName);
//                existTags.add(recTags.get(tagName));
//                newButton(tagName, flexboxLayout_add);
//            }
//        }
//    }

//    class onClickAdd implements View.OnClickListener {
//        @Override
//        public void onClick(View v) {
//            String tagName = ((Button) v).getText().toString();
//            addTags.remove(tagName);
//            flexboxLayout_add.removeView(v);
//        }
//    }
}
