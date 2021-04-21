package com.example.lalala.shared_info;

import com.example.lalala.entity.PaperSimpleData;
import com.example.lalala.entity.SquarePaperData;
import com.example.lalala.entity.UserActionData;
import com.example.lalala.entity.UserHistoryEntity;
import com.example.lalala.entity.UserInfoEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SaveUser {

    public static UserInfoEntity userInfoEntity = new UserInfoEntity();
    //广场与热榜的推荐论文数据
    public static int squarePaperPageNum = -1;
    public static int pageNum = -1;
    public static PaperSimpleData currentPaper;

    //用户为论文添加的标签数据
    public static Map<Integer, Set<String>> paperTagData = new HashMap<>();
    //用户的历史记录
    public static Set<Integer> userHistoryEntities = new HashSet<>();
    public static List<PaperSimpleData> userHistoryPaperData = new ArrayList<>();
    //用户的订阅操作序列
    public static UserActionData userSubscribeActionList = new UserActionData();
    //用户分享操作序列
    public static UserActionData userShareActionList = new UserActionData();
    //用户不感兴趣列表
    public static UserActionData userDislikeList = new UserActionData();
}
