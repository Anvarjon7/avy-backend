package com.example.avyproject.controller;
// package com.example.avyproject.service;

import com.example.avyproject.entity.Role;
import com.example.avyproject.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

// @Component
@Component("uniqueRoleServiceInitializer")
public class RoleInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        // Check if "ROLE_USER" exists, if not, create it
        if (roleRepository.findRoleByRoleName("ROLE_USER").isEmpty()) {
            Role roleUser = new Role();
            roleUser.setRoleName("ROLE_USER");
            roleRepository.save(roleUser);
        }

        // Check if "ROLE_ADMIN" exists, if not, create it
        if (roleRepository.findRoleByRoleName("ROLE_ADMIN").isEmpty()) {
            Role roleAdmin = new Role();
            roleAdmin.setRoleName("ROLE_ADMIN");
            roleRepository.save(roleAdmin);
        }

        // Add more roles if necessary
    }
}
