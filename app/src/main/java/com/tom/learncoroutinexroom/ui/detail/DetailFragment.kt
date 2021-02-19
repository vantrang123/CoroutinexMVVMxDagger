package com.tom.learncoroutinexroom.ui.detail

import com.tom.learncoroutinexroom.R
import com.tom.learncoroutinexroom.base.BaseFragment
import com.tom.learncoroutinexroom.databinding.FragmentDetailBinding
import com.tom.learncoroutinexroom.di.injectViewModel

class DetailFragment : BaseFragment<FragmentDetailBinding, DetailViewModel>(){
    override fun injectViewModel() {
        mViewModel = injectViewModel(viewModelFactory)
    }

    override fun getViewModelClass(): Class<DetailViewModel> {
        return DetailViewModel::class.java
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_detail
    }
}