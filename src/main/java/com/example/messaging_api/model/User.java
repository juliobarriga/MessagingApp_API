package com.example.messaging_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String phoneNumber;

    @Column(unique = true)
    private String userName;

    @Column
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column
    private LocalDate birthDate;

    @OneToMany(mappedBy = "sender", orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    private List<Message> sentMessagesList;

    @OneToMany(mappedBy = "receiver", orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    private List<Message> receivedMessagesList;

    public User() {
    }

    public User(Long id, String phoneNumber, String userName, String password, LocalDate birthDate) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.userName = userName;
        this.password = password;
        this.birthDate = birthDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public List<Message> getSentMessagesList() {
        return sentMessagesList;
    }

    public void setSentMessagesList(List<Message> sentMessagesList) {
        this.sentMessagesList = sentMessagesList;
    }

    public List<Message> getReceivedMessagesList() {
        return receivedMessagesList;
    }

    public void setReceivedMessagesList(List<Message> receivedMessagesList) {
        this.receivedMessagesList = receivedMessagesList;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}
