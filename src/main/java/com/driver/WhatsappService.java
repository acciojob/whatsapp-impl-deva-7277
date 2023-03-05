package com.driver;

import java.util.List;

public class WhatsappService {

    public WhatsappRepository whatsappRepository = new WhatsappRepository();

    public String createUser(String name, String mobile){
        whatsappRepository.createUser(name,mobile);
        return "Success";
    }


    public Group createGroup(List<User> users) {
        Group group = whatsappRepository.createGroup(users);
        return group;
    }

    public int createMessage(String content) {
        Message message = whatsappRepository.createMessage(content);
        return message.getId();
    }

    public int sendMessage(Message message, User sender, Group group) throws Exception {
        return whatsappRepository.sendMessage(message,sender,group);
    }

    public String changeAdmin(User approver, User user, Group group) throws Exception {
        return whatsappRepository.changeAdmin(approver,user,group);
    }
}
