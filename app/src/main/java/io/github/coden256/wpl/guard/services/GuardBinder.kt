package io.github.coden256.wpl.guard.services

import android.util.Log
import io.github.coden256.wpl.guard.DNSRuling
import io.github.coden256.wpl.guard.DomainRuling
import io.github.coden256.wpl.guard.TelegramChatRuling
import io.github.coden256.wpl.guard.TelegramUserRuling
import io.github.coden256.wpl.guard.Guard

class GuardBinder(val appConfig: AppConfig) : Guard.Stub() {
    override fun dnsRulings(): List<DNSRuling> {
        return appConfig
            .dnsRulings
            .map { Log.i("GuardBinder","[dns] Sending ${it}" ); it }
            .map { entry ->
                DNSRuling().also {
                    it.action = entry.action.toString()
                    it.dns = entry.path
                }
            }
    }

    override fun domainRulings(): List<DomainRuling> {
        return appConfig
            .dnsRulings
            .map { Log.i("GuardBinder","[domain] Sending ${it}" ); it }
            .map { entry ->
                DomainRuling().also {
                    it.action = entry.action.toString()
                    it.domain = entry.path
                }
            }

    }

    override fun telegramChatRulings(): List<TelegramChatRuling> {
        return appConfig
            .telegramChatRulings
            .map { Log.i("GuardBinder","[telchat] Sending ${it}" ); it }
            .map { entry ->
                TelegramChatRuling().also {
                    it.action = entry.action.toString()
                    it.chat = entry.path
                }
            }

    }

    override fun telegramUserRulings(): List<TelegramUserRuling> {
        return appConfig
            .telegramUserRulings
            .map { Log.i("GuardBinder","[teluser] Sending ${it}" ); it }
            .map { entry ->
                TelegramUserRuling().also {
                    it.action = entry.action.toString()
                    it.chat = entry.path
                }
            }
    }
}