package com.grpc.app.server.loadbalancing.config;

import io.grpc.NameResolver;
import io.grpc.NameResolverProvider;

import java.net.URI;

public class AppNameResolverProvider extends NameResolverProvider {


    @Override
    protected boolean isAvailable() {
        return true;
    }

    @Override
    protected int priority() {
        return 5;
    }

    @Override
    public NameResolver newNameResolver(URI uri, NameResolver.Args args) {
        System.out.println("Looking for service " + uri.getAuthority());
        return new AppNameResolver(uri.getAuthority());
    }

    @Override
    public String getDefaultScheme() {
        return "http";
    }
}
