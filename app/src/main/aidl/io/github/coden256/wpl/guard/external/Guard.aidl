// Guard.aidl
package io.github.coden256.wpl.guard.external;

import io.github.coden256.wpl.guard.external.DNSRuling;
import io.github.coden256.wpl.guard.external.DomainRuling;
import io.github.coden256.wpl.guard.external.TelegramChatRuling;
import io.github.coden256.wpl.guard.external.TelegramUserRuling;

interface Guard {
    List<DNSRuling> dnsRulings();
    List<DomainRuling> domainRulings();
    List<TelegramChatRuling> telegramChatRulings();
    List<TelegramUserRuling> telegramUserRulings();
}