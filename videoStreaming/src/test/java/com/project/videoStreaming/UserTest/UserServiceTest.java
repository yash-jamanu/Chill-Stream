package com.project.videoStreaming.UserTest;

import java.time.LocalDate;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.videoStreaming.Users.DTO.User;
import com.project.videoStreaming.Users.Entity.UserEntity;
import com.project.videoStreaming.Users.Repository.UserRepository;
import com.project.videoStreaming.Users.Service.UserServiceImplementation;

@SpringBootTest
public class UserServiceTest {

	@Autowired
	UserRepository repository;

	@Autowired
	UserServiceImplementation Service;

    @Test
	void SaveDataServiceLayer(){

		//Arrange
		User userSave = User.builder()
		.username("NinadSEV")
		.firstname("NINAD")
		.lastname("SEVEKAR")
		.email("NinadCR7@gmail.com")
		.password("CR7")
		// .userstatus("premium")
		.birthdate(LocalDate.parse("2004-12-11")).build();

		//Act
		Service.userRegistration(userSave);

		//Assert
		Optional <UserEntity> getUser = repository.findByUsername(userSave.getUsername());

		Assertions.assertThat(userSave).isNotNull();

		Assertions.assertThat(getUser.get().getUsername()).isEqualTo("NinadSEV");
	}

    @Test
	void UpdateUserDataService(){
		//Arrange
		User user = User.builder()
		.email("yashjjj@gmail.com")
		.username("yashjamanu")
		// .userstatus("regular")
		.build();

		//Act
		Service.updateUserDetails(user);

		Optional<UserEntity> getUser = repository.findByEmail(user.getEmail());

        Assertions.assertThat(getUser).isPresent();
        Assertions.assertThat(getUser.get().getUsername()).isEqualTo("yashjamanu");

	}
}
