package com.libin.moon.model;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

/**
 * <p>Description: </p>
 *
 * @author lilibin
 * @date 2021/8/13
 */
@RealmClass
public class DiaryModel implements RealmModel {

    public long stamp;

    public String content;

    public RealmList<MyPhoto> paths;
}
