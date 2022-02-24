package com.example.messaging_api.service;

import com.example.messaging_api.model.Message;
import com.example.messaging_api.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    private MessageRepository messageRepository;

    @Autowired

    public void setMessageRepository(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<Message> getAllMessages(){
        List<Message> messages = messageRepository.findAll();
        return messages;
    }

    public Message createMessageByReceiverId(Long receiverId, Message messageObject) {
//        User receiver = userRepository.findById(receiverId);
//        messageObject.setReceiver(receiver);
        return messageRepository.save(messageObject);
    }

    public List<Message> getMessagesByReceiverId(Long receiverId) {
        List<Message> messages = messageRepository.findByReceiverId(receiverId);
        return messages;
    }

    public Message updateMessage(Long messageId) {
        Optional<Message> message = messageRepository.findById(messageId);
        message.get().setRead(true);
        return messageRepository.save(message.get());
    }

    public Message deleteMessage(Long messageId) {
        Optional<Message> message = messageRepository.findById(messageId);
        messageRepository.deleteById(messageId);
        return message.get();
    }
}
