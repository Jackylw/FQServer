/**
 * @author Jacky Feng
 * @description 如果在线，则HashMap中存在该用户的通信线程
 */
package top.fexample.fq.model;

import java.util.HashMap;
import java.util.Iterator;

public class ManageClientThread {

    // userId和ConClientThread的映射
    public static HashMap<String, ConClientThread> clients = new HashMap<>();

    // 向客户端添加通讯线程
    public static void addClient(String userId, ConClientThread client) {
        clients.put(userId, client);
    }

    public static ConClientThread getClientThread(String userId) {
        return clients.get(userId);
    }

    // 返回在线用户列表
    public static String getOnlineUserList() {
        Iterator<String> it = clients.keySet().iterator();
        StringBuilder res = new StringBuilder();
        while (it.hasNext()) {
            res.append(it.next()).append(" ");
        }
        return res.toString();
    }
}
