package io.github.coden256.wpl.guard.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import io.github.coden256.wpl.guard.DNSRuling
import io.github.coden256.wpl.guard.DomainRuling
import io.github.coden256.wpl.guard.Guard
import io.github.coden256.wpl.guard.TelegramChatRuling
import io.github.coden256.wpl.guard.TelegramUserRuling


object GuardBinder: Guard.Stub (){
    override fun dnsRulings(): List<DNSRuling> {
        return listOf(
            DNSRuling().also {
                it.dns = "1-bdaacaaaeaaia"
                it.action = "FORCE"
            }
        )
    }

    override fun domainRulings(): List<DomainRuling> {
        return listOf(
            DomainRuling().also {
                it.domain = "*.reddit.com"
                it.action = "BLOCK"
            }
        )
    }

    override fun telegramChatRulings(): List<TelegramChatRuling> {
        TODO("Not yet implemented")
    }

    override fun telegramUserRulings(): List<TelegramUserRuling> {
        TODO("Not yet implemented")
    }
}

class GuardService : Service(){

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("GuardService", "Guard Service started: $intent")
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder {
        Log.i("GuardService", "Guard Service received a new connection: $intent")
        return GuardBinder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.i("GuardService", "Guard Service unbounded: $intent")
        return super.onUnbind(intent)
    }

    override fun onRebind(intent: Intent?) {
        Log.i("GuardService", "Guard Service rebinding: $intent")
        super.onRebind(intent)
    }
}