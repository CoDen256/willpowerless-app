package io.github.coden.guard.external

object GuardBinder: Guard.Stub() {

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