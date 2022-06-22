package com.app.ecommercestore.service;

import com.app.ecommercestore.model.Role;
import com.app.ecommercestore.model.User;
import com.app.ecommercestore.repository.RoleRepository;
import com.app.ecommercestore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder; 

    

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    
    public User save(User user) {
        Optional<Role> existingRoleCheck = roleRepository.findByName("ROLE_USER");
        if (existingRoleCheck.isPresent()) {
            user.setRole(existingRoleCheck.get());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            Role role = new Role();
            role.setName("ROLE_USER");
            Role customerRole = roleRepository.save(role);
            user.setRole(customerRole);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return userRepository.save(user);
    }

    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByUsername(username);
        if (user.isPresent()) {
            return new org.springframework.security.core.userdetails.User(
                    user.get().getUsername(),
                    user.get().getPassword(),
                    getAuthority(user.get())
            );
        } else {
            throw new RuntimeException("User doesn't exists");
        }
    }

    
    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().getName()));
        return authorities;
    }
}
