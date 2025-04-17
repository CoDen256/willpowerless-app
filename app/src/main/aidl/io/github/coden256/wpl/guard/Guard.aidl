// Guard.aidl
package io.github.coden256.wpl.guard;

import io.github.coden256.wpl.guard.DNSRuling;
import io.github.coden256.wpl.guard.DomainRuling;
import io.github.coden256.wpl.guard.TelegramChatRuling;
import io.github.coden256.wpl.guard.TelegramUserRuling;

interface Guard {
    List<DNSRuling> dnsRulings();
    List<DomainRuling> domainRulings();
    List<TelegramChatRuling> telegramChatRulings();
    List<TelegramUserRuling> telegramUserRulings();
}