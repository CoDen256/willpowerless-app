// Guard.aidl
package io.github.coden.guard.service;
import io.github.coden.guard.service.DNSRuling;
import io.github.coden.guard.service.DomainRuling;

interface Guard {
    List<DNSRuling> dnsRulings();
    List<DomainRuling> domainRulings();
}