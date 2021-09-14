package com.libin.moon;

import android.animation.ObjectAnimator;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.libin.library.activity.BaseAppCompatActivity;
import com.libin.library.util.PreferenceUtil;
import com.libin.library.util.SystemUtil;
import com.libin.moon.databinding.ActivityStartBinding;
import com.libin.moon.model.DiaryModel;
import com.libin.moon.util.RealmUtil;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class StartActivity extends BaseActivity<ActivityStartBinding> {

    private static final int DELAY_MILLIS = 4000;

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected void initViewAndData() {
        if (SystemUtil.isDarkMode(this)) {
            getBinding().activityStartSlogan.setVisibility(View.GONE);
        } else {
            getBinding().activityStartSlogan.setScaleX(1.2f);
            getBinding().activityStartSlogan.setScaleY(1.2f);
            getBinding().activityStartSlogan.animate().scaleX(1).scaleY(1).alpha(1).setDuration(2000).setListener(null);
        }
        new Handler().postDelayed(() -> readyGoThenKill(ScrollingActivity.class), DELAY_MILLIS);

        init();
    }

    private void init() {
        if (!PreferenceUtil.readBoolean(AppConstant.KEY_INIT_STATE)) {
            DiaryModel model = new DiaryModel();
            model.stamp = 1620948300000L;
            model.content = "宝贝，偷偷告诉你，我穿越啦！你现在看到的是我在2021年5月14号给你的留言，因为上次被你打个措手不及，所以回到这天弥补一下，下面有些话想和宝贝说，注意听讲咯！嘿嘿~\n\n" +
                    "1.以前感觉很复杂，很想你又觉得很坎坷，有的时候过度想念就会引发负面情绪，导致发生跟你的小矛盾，是我不好，我会改正哒哈哈哈(对不起宝~)\n" +
                    "2.每次见面之前心里都排练过几次见面的场景和要讲的话，以为自己有很多话想跟你说，见了面才发现不是忘记了，不好意思表现出来，最后就只会亲亲和抱抱，然后腻歪在一起(#^.^#)\n" +
                    "3.总觉得时间过得很慢，等待很漫长，可不知不觉也过了一年了...我也不知道这一年的时间对你算好还是不好，不过我一直在努力做到更好，争取让宝贝和我在一起的时间都能开开心心的！\n" +
                    "4.不知道路还有多远，也不知道未来还能有多少个一年在等着我们，更不知道结果会怎么样，所以有些话现在不说的话，以后可能就没有机会了，我也怕成为宝贝的遗憾，所以...我想说...正在看我日记的小盆友，你是否愿意成为我正式的女朋友？(久等了.)";
            RealmUtil.insert(model, new RealmUtil.OnOperationListener() {
                @Override
                public void onSuccess() {
                    PreferenceUtil.write(AppConstant.KEY_INIT_STATE, true);
                }

                @Override
                public void onError() {

                }
            });
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    protected int getStatusBarColorRes() {
        return android.R.color.transparent;
    }
}