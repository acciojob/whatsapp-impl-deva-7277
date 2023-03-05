package com.driver;

import java.sql.Timestamp;
import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class WhatsappRepository {

    //Assume that each user belongs to at most one group
    //You can use the below mentioned hashmaps or delete these and create your own.
    private List<User> users;
    private List<Group> groups;

    private List<Message> messages;
    private HashMap<Group, List<User>> groupUserMap;
    private HashMap<Group, List<Message>> groupMessageMap;
    private HashMap<Message, User> senderMap;
    private HashMap<Group, User> adminMap;
    private HashSet<String> userMobile;
    private int customGroupCount;
    private int messageId;

    public WhatsappRepository(){
        this.users = new ArrayList<>();
        this.messages = new ArrayList<>();
        this.groupMessageMap = new HashMap<Group, List<Message>>();
        this.groupUserMap = new HashMap<Group, List<User>>();
        this.senderMap = new HashMap<Message, User>();
        this.adminMap = new HashMap<Group, User>();
        this.userMobile = new HashSet<>();
        this.customGroupCount = 0;
        this.messageId = 0;
    }

    public User createUser(String name, String mobile){
        User user = new User(name,mobile);
        users.add(user);
        return user;
    }

    public Group createGroup(List<User> users) {
        int grpsize = users.size();
        if(grpsize==2){
            Group group = new Group(users.get(1).getName(), grpsize);
            groups.add(group);
            adminMap.put(group,users.get(1));
            customGroupCount++;
            return group;
        }
        Group group = new Group("Group "+String.valueOf(grpsize), grpsize);
        groups.add(group);
        adminMap.put(group,users.get(0));
        customGroupCount++;
        return group;
    }

    public Message createMessage(String content) {
        Message message = new Message(this.messageId+1, content, new Date());
        messages.add(message);
        return message;
    }

    public int sendMessage(Message message, User sender, Group group) throws Exception {
        if(!groupMessageMap.containsKey(group)) throw new Exception("Group does not exist");
        List<User> userList = groupUserMap.get(group);
        User thisUser = null;
        for(User user: userList){
            if(user.equals(sender))
            {
                thisUser = sender;
            }
        }
        if(thisUser==null) {
            throw new Exception("You are not allowed to send message");
        }
        List<Message> thisGroupMessage = groupMessageMap.get(group);
        thisGroupMessage.add(message);
        groupMessageMap.put(group,thisGroupMessage);
        senderMap.put(message,sender);
        return thisGroupMessage.size();
    }

    public String changeAdmin(User approver, User user, Group group) throws Exception {
        Group group1 = null;
        for(Group g: groups){
            if(g.equals(group))
                group1 = group;
        }
        if(group1==null) throw new Exception("Group does not exist");
        if(!adminMap.get(group).equals(approver)) throw new Exception("Approver does not have rights");
        adminMap.put(group,user);
        return "SUCCESS";
    }
}
