package com.tom.learncoroutinexroom.data.local

import com.tom.learncoroutinexroom.data.model.Player
import io.realm.Realm


class DbService : PlayerInterface {
    override fun addOrUpdatePlayer(realm: Realm, player: Player): Boolean {
        val realm = Realm.getDefaultInstance()
        return try {
            realm.use {
                it.beginTransaction()
                it.copyToRealmOrUpdate(player)
                it.commitTransaction()
                it.close()
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun getPlayer(playerId: String): Player? {
        val realm = Realm.getDefaultInstance()
        return try {
            realm.use {
                it.copyFromRealm(it.where(Player::class.java).equalTo("id", playerId).findFirst())
            }
        } catch (e: Exception) {
            null
        }
    }

    override fun saveAll(players: List<Player>): Boolean {
        val r = Realm.getDefaultInstance()
//        val realmList = RealmList<Player>()
//        realmList.addAll(players)
        return try {
            r.use {
                it.beginTransaction()
                it.deleteAll()
                it.copyToRealmOrUpdate(players)
                it.commitTransaction()
                it.close()
            }
            true
        } catch (e: Exception) {
            false
        }
    }
}