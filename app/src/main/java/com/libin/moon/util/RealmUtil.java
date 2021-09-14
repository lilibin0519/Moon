package com.libin.moon.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * <p>Description: </p>
 *
 * @author lilibin
 * @date 2021/8/13
 */
public class RealmUtil {

    private static Realm realm;

    public static  <T extends RealmModel> void query(Class<T> clazz, OnQueryListener<T> listener){
        final RealmQuery<T> query = createQuery(clazz);
        doQuery(query, listener);
    }

    public static <T extends RealmModel> void doQuery(RealmQuery<T> query, OnQueryListener<T> listener) {
        RealmResults<T> results = query.findAllAsync();
        if (!results.isValid()) {
            listener.onError();
            return;
        }
        results.addChangeListener((doorModels, changeSet) -> {
            results.removeAllChangeListeners();
            listener.onResult(realm.copyFromRealm(doorModels));
            realm.close();
        });
    }

    public static <T extends RealmModel> RealmQuery<T> createQuery(Class<T> clazz){
        if (null == realm || realm.isClosed()) {
            realm = Realm.getDefaultInstance();
        }
        return realm.where(clazz).sort("stamp", Sort.DESCENDING);
    }

    public static void insert(Collection<? extends RealmModel> data, OnOperationListener listener){
        initRealm();
        realm.executeTransactionAsync(realm -> realm.insert(data), () -> {
            if (null != listener) {
                listener.onSuccess();
            }
            realm.close();
        }, error -> {
            if (null != listener) {
                listener.onError();
            }
            realm.close();
            error.printStackTrace();
        });
    }

    public static void insert(RealmModel data, OnOperationListener listener){
        initRealm();
        realm.executeTransactionAsync(realm -> realm.insert(data), () -> {
            if (null != listener) {
                listener.onSuccess();
            }
            realm.close();
        }, error -> {
            if (null != listener) {
                listener.onError();
            }
            realm.close();
            error.printStackTrace();
        });
    }

    private static void initRealm() {
        if (null == realm || realm.isClosed()) {
            realm = Realm.getDefaultInstance();
        }
    }

    public interface OnOperationListener {
        void onSuccess();
        void onError();
    }
}
