package com.libin.library.util;

import android.content.Context;
import android.content.res.Configuration;

/**
 * <p>Description: </p>
 *
 * @author lilibin
 * @date 2021/8/12
 */
public class SystemUtil {

    public static boolean isDarkMode(Context context) {
        return (context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_YES) != 0;
    }
}
