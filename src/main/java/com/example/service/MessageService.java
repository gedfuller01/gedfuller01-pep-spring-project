package com.example.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.repository.Query;


import com.example.repository.*;

import java.util.Optional;
import com.example.entity.*;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MessageService {
    
    MessageRepository messageRepository;
    @Autowired
    AccountService accountService;

    @Autowired
    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    public Message createNewMessage(Message message){
        List<Account> persistedAccounts = accountService.getAllAccounts();
        if(message.getMessageText().equals("")){
            return null;
        }
        if(message.getMessageText().length() >255){
            return null;
        }
        for(int i = 0; i < persistedAccounts.size(); i++){
            if(persistedAccounts.get(i).getAccountId().intValue() == message.getPostedBy().intValue()){
                return messageRepository.save(message);
            }
        }
        return null;
    }

    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    public Message getMessageById(int id){
        List<Message> persistedMessages = this.getAllMessages();
        for (int i = 0; i < persistedMessages.size(); i++){
            if (persistedMessages.get(i).getMessageId().intValue() == id){
                return persistedMessages.get(i);
            }
        }
        return null;
    }

    public Boolean deleteMessage(Long id){
        List<Message> persistedMessages = this.getAllMessages();
        for (int i = 0; i < persistedMessages.size(); i++){
            if (persistedMessages.get(i).getMessageId().intValue() == id.intValue()){
                System.out.println("Correct");
                messageRepository.deleteMessageByMessageId(id.intValue());
                return true;
            }
        }
        return false;
    }

    public Boolean updateMessage(Long id, Message replacement){
        if(replacement.getMessageText() != ""){
            if(replacement.getMessageText().length() <= 255){
                Message message = this.getMessageById(id.intValue());
                if(message != null){
                    message.setMessageText(replacement.getMessageText());
                    messageRepository.save(message);
                    return true;
                }
            }
        }
        
        return false;
    }

    public List<Message> getAllMessagesFromUserById(int account_id){
        List<Message> messages = this.getAllMessages();

        for (int i = 0; i < messages.size(); i++){
            if(messages.get(i).getPostedBy().intValue() != account_id){
                messages.remove(i);
                i -= 1;
            }
        }
        

        return messages;

    }
}
