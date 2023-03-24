package org.example.rsocket.lec04Backpressure;

import io.rsocket.core.RSocketServer;
import io.rsocket.transport.netty.server.CloseableChannel;
import io.rsocket.transport.netty.server.TcpServerTransport;
import org.example.rsocket.lec04Backpressure.service.SocketAcceptorImpl;

public class AppServer {
    public static void main(String[] args) {
        // creating a server for receiving data
        // the values will be produced slowly
        RSocketServer rSocketServer = RSocketServer.create(new SocketAcceptorImpl());
        CloseableChannel closeableChannel = rSocketServer.bindNow(TcpServerTransport.create(9595));
        // keep listening
        closeableChannel.onClose().block();
    }
}
