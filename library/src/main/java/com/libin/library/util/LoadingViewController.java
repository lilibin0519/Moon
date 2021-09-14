package com.libin.library.util;

import android.view.View;

/**
 * <p>Description: </p>
 * LoadingViewController
 *
 * @author lilibin
 * @createDate 2020/1/22 09:55
 */

public interface LoadingViewController {

    void restoreView();

    void toggleShowLoading(boolean toggle, String msg);

    void toggleShowEmpty(boolean toggle, String msg, View.OnClickListener onClickListener);

    void toggleShowError(boolean toggle, String msg, View.OnClickListener onClickListener);

    void toggleNetworkError(boolean toggle, View.OnClickListener onClickListener);
}
