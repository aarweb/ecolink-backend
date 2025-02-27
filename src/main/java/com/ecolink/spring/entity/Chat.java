package com.ecolink.spring.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    private UserBase sender;

    @ManyToOne
    @JsonIgnore
    private UserBase receiver;

    @OneToMany(mappedBy = "chat")
    @JsonIgnore
    private List<Message> messages;

    public Chat(UserBase sender, UserBase receiver) {
        this.sender = sender;
        this.receiver = receiver;
        this.messages = new ArrayList<>();
    }
}