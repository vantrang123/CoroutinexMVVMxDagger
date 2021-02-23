package com.tom.learncoroutinexroom.ui.detail

import androidx.lifecycle.Observer
import com.tom.learncoroutinexroom.R
import com.tom.learncoroutinexroom.base.BaseDialogFragment
import com.tom.learncoroutinexroom.base.BaseFragment
import com.tom.learncoroutinexroom.common.Result
import com.tom.learncoroutinexroom.data.model.Player
import com.tom.learncoroutinexroom.databinding.FragmentDetailBinding
import com.tom.learncoroutinexroom.di.injectViewModel

class DetailDialogFragment : BaseDialogFragment<FragmentDetailBinding, DetailViewModel>(){
    override fun injectViewModel() {
        mViewModel = injectViewModel(viewModelFactory)
    }

    override fun getViewModelClass(): Class<DetailViewModel> {
        return DetailViewModel::class.java
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_detail
    }

    override fun initView() {
        super.initView()
        arguments?.getString(ID_PLAYER)?.let {
            viewModel.observePlayerByUUID(id = it)
        }
    }

    override fun initViewModel() {
        super.initViewModel()
        viewModel.apply {
            player.observe(viewLifecycleOwner, Observer {
                when(it.status) {
                    Result.Status.SUCCESS -> {
                        it.data?.let { player ->  displayPlayer(player) }
                    }
                    Result.Status.LOADING -> {}
                    Result.Status.ERROR -> { it.message?.let { message -> snackBar(message) } }
                }
            })
        }
    }

    private fun displayPlayer(data: Player) {
        binding.apply {
            player = data
            image = data.imageUrl
        }
    }

    companion object {
        const val ID_PLAYER = "id_player"
    }
}