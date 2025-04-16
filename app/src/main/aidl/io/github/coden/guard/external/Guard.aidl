// Guard.aidl
package io.github.coden.guard.external;

import io.github.coden.guard.external.DNSRuling;
import io.github.coden.guard.external.DomainRuling;
import io.github.coden.guard.external.TelegramChatRuling;
import io.github.coden.guard.external.TelegramUserRuling;

interface Guard {
    List<DNSRuling> dnsRulings();
    List<DomainRuling> domainRulings();
    List<TelegramChatRuling> telegramChatRulings();
    List<TelegramUserRuling> telegramUserRulings();
}