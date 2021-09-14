package com.libin.moon.model;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

/**
 * <p>Description: </p>
 *
 * @author lilibin
 * @date 2021/8/13
 */
@RealmClass
public class MyPhoto implements RealmModel {

    public long stamp;

    public String path;
}
