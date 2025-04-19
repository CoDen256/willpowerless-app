package io.github.coden256.wpl.guard.config

import android.content.Context
import io.github.coden256.wpl.judge.Ruling

class AppConfig
internal constructor(
    private val context: Context,
    private val persistentState: PersistentState
) {

    var dnsRulings: List<Ruling> = emptyList()
    var domainRulings: List<Ruling> = emptyList()
    var vpnRulings: List<Ruling> = emptyList()
    var appRulings: List<Ruling> = emptyList()
    var telegramChatRulings: List<Ruling> = emptyList()
    var telegramUserRulings: List<Ruling> = emptyList()
}