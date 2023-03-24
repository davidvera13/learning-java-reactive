package org.example.rsocket.lec06RSocketConnexion;

import io.rsocket.core.RSocketServer;
import io.rsocket.transport.netty.server.CloseableChannel;
import io.rsocket.transport.netty.server.TcpServerTransport;
import org.example.rsocket.lec06RSocketConnexion.service.SocketAcceptorImpl;


public class AppServer {
    public static void main(String[] args) {
        // how can we pass credentials ? meta data before accessing to services ?
        RSocketServer rSocketServer = RSocketServer.create(new SocketAcceptorImpl());
        CloseableChannel closeableChannel = rSocketServer.bindNow(TcpServerTransport.create(9595));
        // keep listening
        closeableChannel.onClose().block();
    }
}
