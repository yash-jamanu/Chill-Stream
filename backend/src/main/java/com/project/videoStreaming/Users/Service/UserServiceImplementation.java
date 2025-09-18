package com.project.videoStreaming.Users.Service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.videoStreaming.Users.DTO.User;
import com.project.videoStreaming.Users.Entity.UserEntity;
import com.project.videoStreaming.Users.Repository.UserRepository;


@Service
public class UserServiceImplementation implements UserService{

    @Autowired
    UserRepository Repository;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public void userRegistration(User user) {
        
        try {
            UserEntity entity = new UserEntity();
            entity.setUsername(user.getUsername());
            entity.setFirstname(user.getFirstname());
            entity.setLastname(user.getLastname());
            entity.setEmail(user.getEmail());
            entity.setPassword(user.getPassword());
            entity.setBirthdate(user.getBirthdate());
            Repository.save(entity);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public User getUserDetails(UUID UserId){
        Optional <UserEntity> getUserDetails = Repository.findByUserId(UserId);
        User user = new User();
        if(getUserDetails.isPresent()){
            UserEntity entity = getUserDetails.get();
            user.setUserId(entity.getUserId());
            user.setEmail(entity.getEmail());
            user.setUsername(entity.getUsername());
            user.setFirstname(entity.getFirstname());
            user.setLastname(entity.getLastname());
        }
        return user;
    }

    @Override
    public User getChannelDetails (UUID userId){
        Optional <UserEntity> getChannel = Repository.findByUserId(userId);

        User channel = new User();
        if(getChannel.isPresent()){
            UserEntity entity = getChannel.get();
            channel.setUsername(entity.getUsername());
            channel.setFirstname(entity.getFirstname());
            channel.setLastname(entity.getLastname());
            channel.setProfile(entity.getProfile());
            channel.setSubs_count(entity.getSubs_count());
        }
        return channel;
    }
    
    @Override
    public void updateUserDetails(User user) {
        Optional<UserEntity> optionalEntity = Repository.findByUserId(user.getUserId());
        if (optionalEntity.isPresent()) {
            UserEntity entity = optionalEntity.get();
            if (user.getUsername() != null) entity.setUsername(user.getUsername());
            if (user.getFirstname() != null) entity.setFirstname(user.getFirstname());
            if (user.getLastname() != null) entity.setLastname(user.getLastname());
            //if (user.getPassword() != null) entity.setPassword(user.getPassword());
            if (user.getBirthdate() != null) entity.setBirthdate(user.getBirthdate());
            // if (user.getUserstatus() != null) entity.setUserstatus(user.getUserstatus());
            Repository.save(entity);
        } else {
        }
    }

    @Override
    public void deleteUser(String email, String password) {
        Optional <UserEntity> getUser = Repository.findByEmail(email);
    
        if(getUser.isPresent()){
            UserEntity user = getUser.get();
            
            if(encoder.matches(password, user.getPassword())){
                Repository.deleteById(user.getUserId());
            }else{
                throw new IllegalArgumentException ("Invalid password");
            }
        }else{
            throw new IllegalArgumentException("User Not Found.");
        }
    }

   
}
