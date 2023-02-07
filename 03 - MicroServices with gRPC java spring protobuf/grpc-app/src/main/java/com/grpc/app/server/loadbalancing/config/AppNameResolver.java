package com.grpc.app.server.loadbalancing.config;

import io.grpc.EquivalentAddressGroup;
import io.grpc.NameResolver;

import java.util.List;

public class AppNameResolver extends NameResolver {

    private String service;

    public AppNameResolver(String service) {
        this.service = service;
    }

    @Override
    public String getServiceAuthority() {
        return "AppNameResolver"; // any value
    }

    @Override
    public void shutdown() {

    }

    @Override
    public void refresh() {
        super.refresh();
    }

    @Override
    public void start(Listener2 listener) {
        List<EquivalentAddressGroup> addressGroupList = ServiceRegistry.getInstances(this.service);
        ResolutionResult resolutionResult = ResolutionResult.newBuilder().setAddresses(addressGroupList).build();
        listener.onResult(resolutionResult);
        // super.start(listener);
    }
}
