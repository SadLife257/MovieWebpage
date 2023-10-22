package tdtu.edu.vn.Backend.Utilities.Sockets;

import com.corundumstudio.socketio.SocketIOServer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SocketRunner implements CommandLineRunner {
    private final SocketIOServer socketIOServer;

    @Value("${socket-server.host}")
    private String host;

    @Value("${socket-server.port}")
    private Integer port;

    @Override
    public void run(String... args) throws Exception {
        socketIOServer.start();
        System.out.println("SocketIOServer started at "+host+":"+port);
    }
}
