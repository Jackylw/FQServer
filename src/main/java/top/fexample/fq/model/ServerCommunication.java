/**
 * @author Jacky Feng
 * @date 2024/2/29 21:10
 */
package top.fexample.fq.model;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerCommunication {
    public ServerCommunication() {
        try {
            System.out.println("Server start");
            ServerSocket serverSocket = new ServerSocket(9000);
            while (true) {
                Socket socket = serverSocket.accept();
                // 接收客户端发送的数据
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                User user = (User) ois.readObject();

                // 验证用户
                Msg msg = new Msg();
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                System.out.println("接收到账号" + user.getAccountId());
                System.out.println("接收到密码" + user.getPassword());
                if (user.getAccountId().isEmpty() || user.getPassword().isEmpty()) {
                    msg.setMsgType(MsgType.LOGIN_ERROR);
                    oos.writeObject(msg);
                } else if (checkLogin(user.getAccountId(), user.getPassword())) {
                    msg.setMsgType(MsgType.LOGIN_SUCCESS);
                    oos.writeObject(msg);
                } else {
                    msg.setMsgType(MsgType.LOGIN_ERROR);
                    oos.writeObject(msg);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 校验账号密码
    public boolean checkLogin(String accountId, String password) {
        return "admin".equals(accountId) && "123456".equals(password);
    }
}
