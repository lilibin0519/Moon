package com.libin.moon

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.libin.moon.databinding.ActivityPostBinding
import com.libin.moon.model.MyPhoto
import em.sang.com.allrecycleview.adapter.DefaultAdapter
import em.sang.com.allrecycleview.holder.CustomHolder
import em.sang.com.allrecycleview.inter.DefaultAdapterViewListener

/**
 * <p>Description: </p>
 *
 * @author lilibin
 * @date 2021/9/26
 */
class PostActivity : BaseActivity<ActivityPostBinding>() {

    private var menuDoneItem: MenuItem? = null

    private var menuIsInflated: Boolean = false

    private val data: MutableList<MyPhoto> = mutableListOf()

    private lateinit var content: RecyclerView

    private lateinit var adapter: DefaultAdapter<MyPhoto>

    override fun initViewAndData() {
        content = binding.postContent
        val layoutManager = GridLayoutManager(this, 3)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position >= data.size) 3 else 1
            }
        }
        content.layoutManager = GridLayoutManager(this, 3)
        adapter = DefaultAdapter(this, data, R.layout.item_add_photo, object : DefaultAdapterViewListener<MyPhoto>() {
            override fun getBodyHolder(
                context: Context,
                lists: List<MyPhoto>,
                itemID: Int): CustomHolder<MyPhoto> {
                return object : CustomHolder<MyPhoto>(context, lists, itemID) {
                    override fun initView(
                        position: Int,
                        data: List<MyPhoto?>,
                        context: Context) {
                        holderHelper.setImageResource()
                    }
                }
            }
        })
    }

    override fun getBundleExtras(extras: Bundle?) {
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (!menuIsInflated) {
            menuInflater.inflate(R.menu.post_menu, menu)
            menuDoneItem = menu?.findItem(R.id.save)
            menuDoneItem?.isEnabled = false
            menuIsInflated = true
            return true
        }
        return super.onCreateOptionsMenu(menu)
    }
}