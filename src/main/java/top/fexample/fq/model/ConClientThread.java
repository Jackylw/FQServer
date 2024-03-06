/**
 * @author Jacky Feng
 */
package top.fexample.fq.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;

public class ConClientThread extends Thread {
    Socket socket;

    // 把客户端的socket传入
    public ConClientThread(Socket socket) {
        this.socket = socket;
    }

    // 让该线程去通知其他在线用户
    public void notifyUser(String userId) {
        // 得到所有在线的人
        HashMap<String, ConClientThread> onlineUsers = ManageClientThread.clients;
        for (String s : onlineUsers.keySet()) {
            Msg msg = new Msg();
            // 告诉别人我是谁
            msg.setMsgContent(userId);
            msg.setMsgType(Msg.REC_USER_ONLINE);
            try {
                ObjectOutputStream oos = new ObjectOutputStream(ManageClientThread.getClientThread(s).socket.getOutputStream());
                // 设置接收方为在线的用户
                System.out.println("发送人：" + userId + "上线了,通知：" + s);
                msg.setReceiverId(s);
                oos.writeObject(msg);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            // 线程接受客户端信息
            try {

                // 服务器端接受Sender消息
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Msg msg = (Msg) ois.readObject();

                // 对客户端发来的消息进行处理
                if (msg.getMsgType().equals(Msg.GET_USER_ONLINE)) {
                    System.out.println(msg.getSenderId() + "请求在线好友");
                    String res = ManageClientThread.getOnlineUserList();
                    Msg msgRes = new Msg();
                    msgRes.setMsgType(Msg.REC_USER_ONLINE);
                    msgRes.setMsgContent(res);
                    msgRes.setReceiverId(msg.getSenderId());
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(msgRes);
                } else if (msg.getMsgType().equals(Msg.TEXT_MSG)) {
                    // 服务器向Receiver转发消息
                    System.out.println(msg.getSendTime() + "用户" + msg.getSenderId() + "给" + msg.getReceiverId() + "发送了消息：" + msg.getMsgContent());
                    ConClientThread receiverThread = ManageClientThread.getClientThread(msg.getReceiverId());
                    ObjectOutputStream oos = new ObjectOutputStream(receiverThread.socket.getOutputStream());
                    oos.writeObject(msg);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
