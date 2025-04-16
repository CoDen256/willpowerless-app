// Guard.aidl
package io.github.coden.guard.external;

import io.github.coden.guard.external.DNSRuling;
import io.github.coden.guard.external.DomainRuling;

interface Guard {
    List<DNSRuling> dnsRulings();
    List<DomainRuling> domainRulings();
}