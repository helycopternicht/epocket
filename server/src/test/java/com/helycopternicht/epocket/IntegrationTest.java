package com.helycopternicht.epocket;

import com.helycopternicht.epocket.models.User;
import com.helycopternicht.epocket.repositories.UserRepository;
import org.junit.Rule;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;


@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "/application-test.properties")
@Sql("/database/clear_database.sql")
public class IntegrationTest {

    @Autowired
    private UserRepository userRepository;

//    @Rule
//    public final GrpcCleanupRule grpcCleanup = new GrpcCleanupRule();

    @Test
    public void test() {
        User user = new User();
        user.setId(3L);
        user.setName("LOX");

        userRepository.save(user);

        Optional<User> userOptional = userRepository.findById(3L);
    }
}
