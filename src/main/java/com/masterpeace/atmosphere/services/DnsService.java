package com.masterpeace.atmosphere.services;

import com.masterpeace.atmosphere.dao.DnsRepository;
import com.masterpeace.atmosphere.dao.IpRepository;
import com.masterpeace.atmosphere.model.Dns;
import com.masterpeace.atmosphere.model.IpAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class DnsService {

    private static final long BASE_NUMBER = 200;
    private static final String IP_TEMPLATE = "52-11-145-%s";
    private static final String DNS_TEMPLATE = "ec2-%s.us.west-2.amazon.com";
    private static long current = BASE_NUMBER;

    private final DnsRepository dnsRepository;
    private final IpRepository ipRepository;

    @Autowired
    public DnsService(DnsRepository dnsRepository, IpRepository ipRepository){
        this.dnsRepository = dnsRepository;
        this.ipRepository = ipRepository;
    }

    public IpAddress generateIp(boolean exposed){
        String ipString = String.format(IP_TEMPLATE, ++current).replaceAll("-", ".");
        IpAddress ip = new IpAddress(ipString, exposed);
        return this.ipRepository.save(ip);
    }

    public Dns generateDns(IpAddress ip, boolean exposed){
        Dns dns  = new Dns(exposed, String.format(DNS_TEMPLATE, ip.getValue()));
        return this.dnsRepository.save(dns);
    }
}
