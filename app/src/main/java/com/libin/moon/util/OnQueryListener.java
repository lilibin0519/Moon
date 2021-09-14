package com.libin.moon.util;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmModel;

/**
 * <p>Description: </p>
 *
 * @author lilibin
 * @date 2021/8/13
 */
public abstract class OnQueryListener<T extends RealmModel>{
    public abstract void onResult(List<T> result);

    public void onError(){
        onResult(new ArrayList<>());
    }
}
