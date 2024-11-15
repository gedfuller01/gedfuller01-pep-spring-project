package com.example.repository;

import com.example.entity.*;
import ch.qos.logback.core.pattern.color.BoldBlueCompositeConverter;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long>{
    
    void deleteMessageByMessageId(int message_id);
        
}
