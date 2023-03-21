package org.example.rsocket.lec01;

import io.rsocket.core.RSocketServer;
import io.rsocket.transport.netty.server.CloseableChannel;
import io.rsocket.transport.netty.server.TcpServerTransport;
import org.example.rsocket.lec01.service.SocketAcceptorObjectsImpl;
import org.example.rsocket.lec01.service.SocketAcceptorSlowImpl;

public class AppServerObjects {
    public static void main(String[] args) {
        // creating a server for receiving data
        RSocketServer rSocketServer = RSocketServer.create(new SocketAcceptorObjectsImpl());
        CloseableChannel closeableChannel = rSocketServer.bindNow(TcpServerTransport.create(6363));
        // keep listening
        closeableChannel.onClose().block();
    }
}
