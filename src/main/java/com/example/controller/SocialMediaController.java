package com.example.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.h2.util.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.example.entity.Account;
import com.example.entity.Message;

import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.Query;


import com.example.repository.*;
import com.example.service.*;;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    @Autowired
    AccountService accountService;
    @Autowired
    MessageService messageService;

    @PostMapping("/register")
    public ResponseEntity registerAccount(@RequestBody Account account){
        int value = accountService.registerAccount(account);
        if(value == 0){
            return ResponseEntity.status(409).body("Conflict");
        }
        if(value == -1){
            return ResponseEntity.status(400).body("Client error");
        }
        return ResponseEntity.status(200).body(account);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody Account account){
        account = accountService.login(account);
        if(account == null){
            return ResponseEntity.status(401).body("Unauthorized");
        }
        return ResponseEntity.status(200).body(account);
    }

    @PostMapping("/messages")
    public ResponseEntity createMessage(@RequestBody Message message){
        message = messageService.createNewMessage(message);
        if(message == null){
            return ResponseEntity.status(400).body("Client error");
        }
        return ResponseEntity.status(200).body(message);
    }

    @GetMapping("/messages")
    public ResponseEntity getAllMessages(){
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.status(200).body(messages);
    }

    @GetMapping("/messages/{message_id}")
    public ResponseEntity getMessageGivenMessageId(@PathVariable int message_id){
           
        Message message = messageService.getMessageById(message_id);
        if(message == null){
            return ResponseEntity.status(200).body("");
        }
        return ResponseEntity.status(200).body(message);
    }

    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity deleteMessageGivenMessageId(@PathVariable Long message_id){
        System.out.println("here");
        boolean rows_deleted = messageService.deleteMessage(message_id);
        if(rows_deleted){
            return ResponseEntity.status(200).body(1);
        }
        return ResponseEntity.status(200).body("");
    }

    @PatchMapping("/messages/{message_id}")
    public ResponseEntity updateMessage(@RequestBody Message message, @PathVariable Long message_id){
        boolean rows_updated = messageService.updateMessage(message_id, message);
        if(rows_updated){
            return ResponseEntity.status(200).body(1);
        }
        return ResponseEntity.status(400).body("Client error");
    }

    @GetMapping("/accounts/{account_id}/messages")
    public ResponseEntity getAllMessagesByUser(@PathVariable int account_id){
        List<Message> messages = messageService.getAllMessagesFromUserById(account_id);
        return ResponseEntity.status(200).body(messages);
    }
}
