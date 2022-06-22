package com.app.ecommercestore;

import com.app.ecommercestore.model.Role;
import com.app.ecommercestore.model.User;
import com.app.ecommercestore.repository.RoleRepository;
import com.app.ecommercestore.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@SpringBootApplication
public class EcommerceStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceStoreApplication.class, args);
    }

    /**
     * Here I have created a admin account which will be created when there is no admin account.
     * So, every time I'll check whether this admin account exists in our database or not, if not then add this.
     * This admin account will be the admin of all the products that will be storing the products and all other things.
     *
     * @param userRepository
     * @param passwordEncoder
     * @param roleRepository
     * @return
     */
    @Bean
    CommandLineRunner run(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        return args -> {
         
            Optional<Role> existingRoleCheck = roleRepository.findByName("ROLE_ADMIN");
            if (existingRoleCheck.isPresent()) {
                Optional<User> optionalUser = userRepository.findUserByUsername("admin");
                if (!optionalUser.isPresent()) {

                
                    userRepository.save(User.builder()
                            .firstName("Abdiaziz")
                            .lastName("Irshat")
                            .address("Minneapolis, Minnesota")
                            .email("airshat22@gmail.com")
                            .username("airshat22")
                            .password(passwordEncoder.encode("admin"))
                            .role(existingRoleCheck.get())
                            .build());
                }
            } else {

                Role role = new Role();
                role.setName("ROLE_ADMIN");
                Role savedRole = roleRepository.save(role);
                Optional<User> optionalUser = userRepository.findUserByUsername("admin");
                if (!optionalUser.isPresent()) {
                    User user = User.builder()
                            .firstName("Admin")
                            .lastName("Account")
                            .address("Minneapolis, Minnesota")
                            .email("admin@gmail.com")
                            .username("admin")
                            .password(passwordEncoder.encode("admin"))
                            .role(savedRole)
                            .build();
                    userRepository.save(user);
                }
            }
        };
    }
}
