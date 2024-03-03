/**
 * @author Jacky Feng
 */
package top.fexample.fq.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConClientThread extends Thread {
    Socket socket;

    // 把客户端的socket传入
    public ConClientThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        while (true) {
            // 线程接受客户端信息
            try {

                // 服务器端接受Sender消息
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Msg msg = (Msg) ois.readObject();
                System.out.println(msg.getSendTime() + "用户" + msg.getSenderId() + "给" + msg.getReceiverId() + "发送了消息：" + msg.getMsgContent());

                // 服务器向Receiver转发消息
                ConClientThread receiverThread = ManageClientThread.getClientThread(msg.getReceiverId());
                ObjectOutputStream oos = new ObjectOutputStream(receiverThread.socket.getOutputStream());
                oos.writeObject(msg);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
