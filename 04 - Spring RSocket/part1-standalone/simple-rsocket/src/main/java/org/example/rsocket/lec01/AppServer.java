package org.example.rsocket.lec01;

import io.rsocket.core.RSocketServer;
import io.rsocket.transport.netty.server.CloseableChannel;
import io.rsocket.transport.netty.server.TcpServerTransport;
import org.example.rsocket.lec01.service.SocketAcceptorImpl;

public class AppServer {
    public static void main(String[] args) {
        // creating a server for receiving data
        RSocketServer rSocketServer = RSocketServer.create(new SocketAcceptorImpl());
        CloseableChannel closeableChannel = rSocketServer.bindNow(TcpServerTransport.create(6565));
        // keep listening
        closeableChannel.onClose().block();
    }
}
