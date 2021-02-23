package com.tom.learncoroutinexroom.ui.main

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.tom.learncoroutinexroom.R
import com.tom.learncoroutinexroom.base.BaseActivity
import com.tom.learncoroutinexroom.common.Result
import com.tom.learncoroutinexroom.data.model.Player
import com.tom.learncoroutinexroom.databinding.ActivityMainBinding
import com.tom.learncoroutinexroom.di.injectViewModel
import com.tom.learncoroutinexroom.extensions.visible
import com.tom.learncoroutinexroom.ui.MainAdapter
import com.tom.learncoroutinexroom.ui.detail.DetailDialogFragment
import javax.inject.Inject

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    private lateinit var adapter: MainAdapter

    @Inject
    lateinit var detailFragment: DetailDialogFragment

    override fun injectViewModel() {
        mViewModel = injectViewModel(viewModelFactory)
    }

    override fun getViewModelClass(): Class<MainViewModel> = MainViewModel::class.java

    override fun initView() {
        adapter = MainAdapter(this::onItemClicked)

        binding.recyclerView.layoutManager =
            LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL,
                false
            )

        binding.adapter = adapter
        observeUi()
    }

    private fun observeUi() {
        viewModel.player.observe(this, Observer { result ->
            when (result.status) {

                Result.Status.SUCCESS -> {
                    if (result.data != null) {
                        adapter.setPlayerList(result.data)
                    }
                    binding.recyclerView.visible()
                }

                Result.Status.ERROR -> {
                    result.message?.let { message -> snackBar(message) }
                    binding.recyclerView.visible()
                }
                Result.Status.LOADING -> {
                }
            }
        })
    }

    override fun getLayoutResourceId(): Int = R.layout.activity_main

    private fun onItemClicked(player: Player) {
        val args = Bundle().apply {
            putString(DetailDialogFragment.ID_PLAYER, player._ID.toString())
        }
        detailFragment.arguments = args
        detailFragment.show(supportFragmentManager, "DetailDialogFragment")
    }
}