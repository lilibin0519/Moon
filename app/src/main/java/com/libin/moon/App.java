package com.libin.moon;

import com.libin.library.MyApplication;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * <p>Description: </p>
 *
 * @author lilibin
 * @date 2021/8/13
 */
public class App extends MyApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        initRealm();
    }

    private void initRealm() {
        Realm.init(this);
        RealmConfiguration config = new  RealmConfiguration.Builder()
                .name("moon.realm")
                .schemaVersion(AppConstant.INT_DB_VERSION)
                .migration(new MyMigration())
                .build();
        Realm.setDefaultConfiguration(config);
    }
}
