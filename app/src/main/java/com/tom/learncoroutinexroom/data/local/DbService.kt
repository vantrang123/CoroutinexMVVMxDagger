package com.tom.learncoroutinexroom.data.local

import com.google.gson.Gson
import com.tom.learncoroutinexroom.data.model.Player
import io.realm.Realm
import io.realm.RealmResults
import timber.log.Timber


class DbService : PlayerInterface {
    override fun addOrUpdatePlayer(realm: Realm, player: Player): Boolean {
        return try {
            realm.beginTransaction()
            realm.copyToRealmOrUpdate(player)
            realm.commitTransaction()
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun getPlayer(realm: Realm, playerId: String): Player? {
        return try {
            realm.where(Player::class.java).equalTo("_ID", playerId).findFirst()
        } catch (e: Exception) {
            null
        }
    }

    override fun saveAll(realm: Realm, players: List<Player>): Boolean {
        return try {
            realm.beginTransaction()
            realm.copyToRealmOrUpdate(players)
            realm.commitTransaction()
            true
        } catch (e: Exception) {
            false
        }
    }
}