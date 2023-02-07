package com.grpc.app.server.loadbalancing.config;

import io.grpc.EquivalentAddressGroup;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ServiceRegistry {
    // for load balancing we register IPs
    private static final Map<String, List<EquivalentAddressGroup>> MAP = new HashMap<>();

    // payment service: 127.0.0.1:8081, 127.0.0.1:8082....
    public static void register(String service, List<String> instances) {
        List<EquivalentAddressGroup> addressGroupList = instances.stream()
                .map(str -> str.split(":"))
                .map(arr -> new InetSocketAddress(arr[0], Integer.parseInt(arr[1])))
                // .map(inetSocketAddress -> new EquivalentAddressGroup(inetSocketAddress))
                .map(EquivalentAddressGroup::new)
                .collect(Collectors.toList());

        MAP.put(service, addressGroupList);
    }

    public static List<EquivalentAddressGroup> getInstances(String service) {
        return MAP.get(service);
    }
}
