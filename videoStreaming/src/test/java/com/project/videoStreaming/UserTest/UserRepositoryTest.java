package com.project.videoStreaming.UserTest;

import java.time.LocalDate;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.videoStreaming.Users.Entity.UserEntity;
import com.project.videoStreaming.Users.Repository.UserRepository;

@SpringBootTest
class UserRepositoryTest {

    @Autowired 
    UserRepository repository;

    @Test
	void SaveUserDataRepository() {

		//Arrange
		UserEntity userSave = UserEntity.builder()
		.username("yashj")
		.firstname("yash")
		.lastname("jamanu")
		.email("yashjjj@gmail.com")
		.password("yashj111")
		// .userstatus("Premium")
		.birthdate(LocalDate.parse("2004-08-25")).build();

		//Act
		UserEntity SavedUser = repository.save(userSave);

		//Assert 
		Assertions.assertThat(SavedUser).isNotNull();
		Assertions.assertThat(SavedUser.getUsername()).isEqualTo("yashj").isNotNull();
		Assertions.assertThat(SavedUser.getFirstname()).isEqualTo("yash").isNotNull();
		Assertions.assertThat(SavedUser.getLastname()).isEqualTo("jamanu").isNotNull();
		Assertions.assertThat(SavedUser.getEmail()).isEqualTo("yashjjj@gmail.com").isNotNull();
		Assertions.assertThat(SavedUser.getPassword()).isEqualTo("yashj111").isNotNull();
		// Assertions.assertThat(SavedUser.getUserstatus()).isEqualTo("Premium").isNotNull();
	}

    @Test
    void testUserUpdateRepository(){
        //ACT
        String email = "yashjjj@gmail.com";
        Optional<UserEntity> getUser = repository.findByEmail(email);

        Assertions.assertThat(getUser).isPresent();
        Assertions.assertThat(getUser.get().getUsername()).isEqualTo("yashj");
    }

}
