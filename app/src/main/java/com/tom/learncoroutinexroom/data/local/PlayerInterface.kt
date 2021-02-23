package com.tom.learncoroutinexroom.data.local

import com.tom.learncoroutinexroom.data.model.Player
import io.realm.Realm

interface PlayerInterface {
    fun addOrUpdatePlayer(realm: Realm, player: Player) : Boolean
    fun getPlayer(realm: Realm, playerId: String) : Player?
    fun saveAll(realm: Realm, players: List<Player>) : Boolean
}