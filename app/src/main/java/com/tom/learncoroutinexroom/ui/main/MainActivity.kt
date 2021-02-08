package com.tom.learncoroutinexroom.ui.main

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
import com.tom.learncoroutinexroom.ui.MainAdapter

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    private lateinit var adapter: MainAdapter

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
                    binding.recyclerView.visibility = View.VISIBLE
                }

                Result.Status.ERROR -> {
                    result.message?.let {
                        Snackbar.make(
                            binding.recyclerView,
                            it,
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                    binding.recyclerView.visibility = View.VISIBLE
                }

                Result.Status.LOADING -> {
                }
            }
        })
    }

    override fun getLayoutResourceId(): Int = R.layout.activity_main

    private fun onItemClicked(player: Player) {
    }
}