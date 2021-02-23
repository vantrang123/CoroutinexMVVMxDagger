package com.tom.learncoroutinexroom.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.tom.learncoroutinexroom.R
import com.tom.learncoroutinexroom.base.BaseAdapter
import com.tom.learncoroutinexroom.data.model.Player
import com.tom.learncoroutinexroom.databinding.PlayerRowBinding


class MainAdapter(val listener: Listener) : RecyclerView.Adapter<MainAdapter.PlayerHolder>() {
    private val list = arrayListOf<Player>()

    internal fun setPlayerList(list: List<Player>) {
        if (this.list.isNotEmpty()) {
            this.list.clear()
        }
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: PlayerHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    inner class PlayerHolder(var binding: PlayerRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Player) = binding.apply {
            player = item
            image = item.imageUrl
            val icon = if (item.isFavorite == true) {
                R.drawable.ic_star
            } else {
                R.drawable.ic_star_border
            }
            ivFavorite.setImageResource(icon)
            mainLayout.setOnClickListener {
                listener.invoke(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<PlayerRowBinding>(inflater, R.layout.player_row, parent, false)

        return PlayerHolder(binding)
    }
}

typealias Listener = (Player) -> Unit