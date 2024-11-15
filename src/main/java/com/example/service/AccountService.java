package com.example.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repository.*;
import com.example.entity.*;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service    
public class AccountService {
    MessageRepository messageRepository;
    AccountRepository accountRepository;
    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public int registerAccount(Account account){
        List<Account> persistedAccounts = getAllAccounts();
        if(account.getPassword().length() >= 4){
            String username = account.getUsername();
            if (username != ""){
                for (int i = 0; i < persistedAccounts.size(); i++){
                    if (username.equals(persistedAccounts.get(i).getUsername())){
                        return 0;
                    }
                }
            }
            else{
                return -1;
            }
        }
        else{
            return -1;
        }
        accountRepository.save(account);
        return 1;
    }

    public List<Account> getAllAccounts(){
        return accountRepository.findAll();
    }

    public Account login(Account account){
        List<Account> persistedAccounts = getAllAccounts();
        String username = account.getUsername();
        String password = account.getPassword();
        for (int i = 0; i < persistedAccounts.size(); i++){
            if (username.equals(persistedAccounts.get(i).getUsername())){
                if(password.equals(persistedAccounts.get(i).getPassword())){
                    return persistedAccounts.get(i);
                }
            }
        }
        return null;
    }

    
}
