package org.example.rsocket.lec01FireAndForget;

import io.rsocket.core.RSocketServer;
import io.rsocket.transport.netty.server.CloseableChannel;
import io.rsocket.transport.netty.server.TcpServerTransport;
import org.example.rsocket.lec01FireAndForget.service.SocketAcceptorSlowImpl;

public class AppServerSlow {
    public static void main(String[] args) {
        // creating a server for receiving data
        RSocketServer rSocketServer = RSocketServer.create(new SocketAcceptorSlowImpl());
        CloseableChannel closeableChannel = rSocketServer.bindNow(TcpServerTransport.create(6464));
        // keep listening
        closeableChannel.onClose().block();
    }
}
