package com.libin.moon;

import android.os.Trace;

import androidx.annotation.NonNull;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;

/**
 * <p>Description: </p>
 *
 * @author lilibin
 * @date 2021/8/13
 */
public class MyMigration implements RealmMigration {
    @Override
    public void migrate(@NonNull DynamicRealm realm, long oldVersion, long newVersion) {
        for (int i = (int) oldVersion; i < newVersion; i++) {
            switch (i) {
                case 0:
//                    upgradeToDB1(realm);
                    break;
                case 1:
//                    upgradeToDB2(realm);
                    break;
                case 2:
//                    upgradeToDB3(realm);
                    break;
            }
        }
    }
}
