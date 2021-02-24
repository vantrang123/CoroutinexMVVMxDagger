package com.tom.learncoroutinexroom.data.local

import com.tom.learncoroutinexroom.data.model.Player
import io.realm.Realm

interface PlayerInterface {
    fun addOrUpdatePlayer(realm: Realm, player: Player) : Boolean
    fun getPlayer(playerId: String) : Player?
    fun saveAll(players: List<Player>) : Boolean
}