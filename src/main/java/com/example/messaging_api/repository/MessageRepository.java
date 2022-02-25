package com.example.messaging_api.repository;

import com.example.messaging_api.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
//    List<Message> findByReceiverId(Long receiverId);
    List<Message> findBySenderIdOrReceiverId(Long senderId, Long receiverId);
    List<Message> findBySenderIdAndReceiverId(Long senderId, Long receiverId);
    Optional<Message> findByIdAndSenderId(Long messageId, Long senderId);
}
