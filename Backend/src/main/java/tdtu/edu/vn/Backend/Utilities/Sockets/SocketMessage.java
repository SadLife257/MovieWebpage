package tdtu.edu.vn.Backend.Utilities.Sockets;

import tdtu.edu.vn.Backend.Models.UsersModel;
import lombok.Data;

@Data
public class SocketMessage {
    private String message;

    private Long uid;

    public SocketMessage(){}

    public SocketMessage(String message, Long uid){
        this.message = message;
        this.uid = uid;
    }
}
