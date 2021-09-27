package com.libin.moon

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.libin.library.util.PreferenceUtil
import com.libin.library.util.SystemUtil
import com.libin.moon.databinding.ActivityStartBinding
import com.libin.moon.model.DiaryModel
import com.libin.moon.util.RealmUtil
import com.libin.moon.util.RealmUtil.OnOperationListener

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class NewStartActivity : BaseActivity<ActivityStartBinding?>() {

    private val delayMillis = 4000

    override fun initViewAndData() {
        if (SystemUtil.isDarkMode(this)) {
            binding!!.activityStartSlogan.visibility = View.GONE
        } else {
            binding!!.activityStartSlogan.scaleX = 1.2f
            binding!!.activityStartSlogan.scaleY = 1.2f
            binding!!.activityStartSlogan.animate().scaleX(1f).scaleY(1f).alpha(1f).setDuration(2000)
                .setListener(null)
        }
        Handler(Looper.getMainLooper()).postDelayed(
            { readyGoThenKill(ScrollingActivity::class.java) },
            delayMillis.toLong()
        )
        init()
    }

    private fun init() {
        if (!PreferenceUtil.readBoolean(AppConstant.KEY_INIT_STATE)) {
            val model = DiaryModel()
            model.stamp = 1620948300000L
            model.content =
                """
                宝贝，偷偷告诉你，我穿越啦！你现在看到的是我在2021年5月14号给你的留言，因为上次被你打个措手不及，所以回到这天弥补一下，下面有些话想和宝贝说，注意听讲咯！嘿嘿~
                
                1.以前感觉很复杂，很想你又觉得很坎坷，有的时候过度想念就会引发负面情绪，导致发生跟你的小矛盾，是我不好，我会改正哒哈哈哈(对不起宝~)
                2.每次见面之前心里都排练过几次见面的场景和要讲的话，以为自己有很多话想跟你说，见了面才发现不是忘记了，不好意思表现出来，最后就只会亲亲和抱抱，然后腻歪在一起(#^.^#)
                3.总觉得时间过得很慢，等待很漫长，可不知不觉也过了一年了...我也不知道这一年的时间对你算好还是不好，不过我一直在努力做到更好，争取让宝贝和我在一起的时间都能开开心心的！
                4.不知道路还有多远，也不知道未来还能有多少个一年在等着我们，更不知道结果会怎么样，所以有些话现在不说的话，以后可能就没有机会了，我也怕成为宝贝的遗憾，所以...我想说...正在看我日记的小盆友，你是否愿意成为我正式的女朋友？(久等了.)
                """.trimIndent()
            RealmUtil.insert(model, object : OnOperationListener {
                override fun onSuccess() {
                    PreferenceUtil.write(AppConstant.KEY_INIT_STATE, true)
                }

                override fun onError() {}
            })
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    override fun getStatusBarColorRes(): Int {
        return android.R.color.transparent
    }

    override fun getBundleExtras(extras: Bundle) {}
}