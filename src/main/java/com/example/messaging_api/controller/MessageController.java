package com.example.messaging_api.controller;

import com.example.messaging_api.model.Message;
import com.example.messaging_api.model.User;
import com.example.messaging_api.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MessageController {

    private MessageService messageService;

    @Autowired
    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/messages")
    public List<Message> getAllMessages(){
        return messageService.getAllMessages();
    }

    @PostMapping("/messages/{receiverId}")
    public Message createMessageByReceiverId(@PathVariable(value = "receiverId") Long receiverId, @RequestBody Message messageObject){
        return messageService.createMessageByReceiverId(receiverId, messageObject);
    }

    @GetMapping("/messages/sent/{receiverId}")
    public List<Message> getMessagesByReceiverId(@PathVariable(value = "receiverId") Long receiverId){
        return messageService.getMessagesByReceiverId(receiverId);
    }

    @GetMapping("/messages/received/{senderId}")
    public List<Message> getMessagesBySenderId(@PathVariable(value = "senderId") Long senderId){
        return messageService.getMessagesBySenderId(senderId);
    }

    @GetMapping("/messages/shared/{secondUserId}")
    public List<Message> getSharedMessages(@PathVariable(value = "secondUserId") Long secondUserId){
        return messageService.getSharedMessages(secondUserId);
    }

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return messageService.getAllUsers();
    }

    @PutMapping("/messages/{messageId}")
    public Message updateMessage(@PathVariable(value = "messageId") Long messageId){
        return messageService.updateMessage(messageId);
    }

    @DeleteMapping("/messages/{messageId}")
    public Message deleteMessage(@PathVariable(value = "messageId") Long messageId){
        return messageService.deleteMessage(messageId);
    }

    @GetMapping("/user")
    public User getUserInfo(){ return messageService.getUserInfo(); }

}
