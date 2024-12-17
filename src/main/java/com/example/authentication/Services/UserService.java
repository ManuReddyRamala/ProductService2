package com.example.authentication.Services;

import com.example.authentication.Config.KafkaProducerClient;
import com.example.authentication.Models.Token;
import com.example.authentication.Models.User;
import com.example.authentication.Repo.TokenRepo;
import com.example.authentication.Repo.UserRepo;
import com.example.authentication.dtos.SendEmailDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private TokenRepo tokenRepo;
    private KafkaProducerClient kafkaProducerClient;
    private ObjectMapper objectMapper;
    UserService(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepo userRepo, TokenRepo tokenRepo,
                KafkaProducerClient kafkaProducerClient,ObjectMapper objectMapper) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepo = userRepo;
        this.tokenRepo = tokenRepo;
        this.kafkaProducerClient = kafkaProducerClient;
    }
    public User signUp(String email, String password,String name) throws JsonProcessingException {
        User u = new User();
        u.setEmail(email);
        u.setVerified(true);
        u.setName(name);
        u.setHashedPassword(bCryptPasswordEncoder.encode(password));
        SendEmailDto sendEmailDto=new SendEmailDto();
        sendEmailDto.setTo(u.getEmail());
        sendEmailDto.setFrom("manureddy927@gmail.com");
        sendEmailDto.setSubject("Welcome");
        sendEmailDto.setSubject("Welcome email...... from Manu's IT Solutions");
        kafkaProducerClient.send("SendEmail",objectMapper.writeValueAsString(sendEmailDto) );
        System.out.println(u);

       User u2= userRepo.save(u);
       System.out.println(u2);
        return u2;

    }
    public Token login(String email, String password)
    {
        //1.find user by email
        Optional<User> userOptional=userRepo.findByEmail(email);
        if(userOptional.isEmpty())
            throw new RuntimeException("User not found");
        User u=userOptional.get();
        //2. use matches method to match the pwd
        if(!bCryptPasswordEncoder.matches(password,u.getHashedPassword()))
        {
            throw new RuntimeException("Wrong password");
        }

        //3. generate Token....
        Token t=generateToken(u);
        //4. save token
        Token token=tokenRepo.save(t);
        // 5. Return Token
        return token;

    }
    private Token generateToken(User u)
    {
        LocalDate currenDate = LocalDate.now();
        LocalDate ThirtyDaysLater = currenDate.plusDays(30);
        Date expiry=Date.from(ThirtyDaysLater.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Token token = new Token();
        token.setExpiry(expiry);
        token.setUser(u);
        token.set_valid(true);
        token.setValue(RandomStringUtils.randomAlphanumeric(128));
        return token;

    }

    public void logout(String token)
    {
       Optional<Token> optionalToken= tokenRepo.findByValue(token);
       if(optionalToken.isEmpty())
       {
           throw new RuntimeException("Token not found");
       }
       //tokenRepo.delete(optionalToken.get());
        Token t=optionalToken.get();
       t.set_valid(false);
       tokenRepo.save(t);
    }

    public User validateToken(String t) {
        Optional<Token>optionalToken=tokenRepo.findByValueAndIs_validAndExpiryGreaterThan(t,true,new Date());
        if(optionalToken.isEmpty())
            return null;
//            throw new RuntimeException("Token not found");
      return  optionalToken.get().getUser();
    }
}
