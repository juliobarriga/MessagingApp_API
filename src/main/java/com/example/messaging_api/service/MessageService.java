package com.example.messaging_api.service;

import com.example.messaging_api.exceptions.InformationNotFoundException;
import com.example.messaging_api.model.Message;
import com.example.messaging_api.model.User;
import com.example.messaging_api.repository.MessageRepository;
import com.example.messaging_api.repository.UserRepository;
import com.example.messaging_api.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    private MessageRepository messageRepository;

    @Autowired
    public void setMessageRepository(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<Message> getAllMessages(){
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Message> messages = messageRepository.findBySenderIdOrReceiverId(userDetails.getUser().getId(), userDetails.getUser().getId());
        return messages;
    }

    public Message createMessageByReceiverId(Long receiverId, Message messageObject) {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        messageObject.setSender(userDetails.getUser());
        Optional<User> receiver = userRepository.findById(receiverId);
        messageObject.setReceiver(receiver.get());
        messageObject.setTimestamp(LocalDateTime.now());
        messageObject.setRead(false);
        return messageRepository.save(messageObject);
    }

    public List<Message> getMessagesByReceiverId(Long receiverId) {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Message> messages = messageRepository.findBySenderIdAndReceiverId(userDetails.getUser().getId(), receiverId);
        return messages;
    }

    public List<Message> getMessagesBySenderId(Long senderId) {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Message> messages = messageRepository.findBySenderIdAndReceiverId(senderId, userDetails.getUser().getId());
        return messages;
    }

    public Message updateMessage(Long messageId) {
        Optional<Message> message = messageRepository.findById(messageId);
        message.get().setRead(true);
        return messageRepository.save(message.get());
    }

    public Message deleteMessage(Long messageId) {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Message> message = messageRepository.findByIdAndSenderId(messageId, userDetails.getUser().getId());
        if(message.isPresent()){
            messageRepository.deleteById(messageId);
            return message.get();
        } else {
            throw new InformationNotFoundException("User with id " + userDetails.getUser().getId() + " does not have message with Id " + messageId);
        }

    }

    public User getUserInfo() {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUser();
    }
}
