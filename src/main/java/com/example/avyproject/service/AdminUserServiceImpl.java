package com.example.avyproject.service;

import com.example.avyproject.entity.AdminUser;
import com.example.avyproject.exceptions.InvalidFormatException;
import com.example.avyproject.exceptions.UserNotFoundException;
import com.example.avyproject.repository.AdminUserRepository;
import com.example.avyproject.service.utility.FormatChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {

    private final AdminUserRepository adminUserRepository;
    private final FormatChecker formatChecker;

    @Override
    public List<AdminUser> getAll() {
        return adminUserRepository.findAll();
    }

    @Override
    public AdminUser findById(Long id) {
        return adminUserRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with ID %d not found", id)));
    }

    @Override
    public AdminUser findByEmail(String email) {
        return adminUserRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with email \"%s\"" + " not found", email)));
    }

    @Override
    public AdminUser save(AdminUser adminUser) {
        if (formatChecker.checkEmail(adminUser.getEmail())) {
            if (formatChecker.checkPassword(adminUser.getPassword())) {
                return adminUserRepository.save(adminUser);
            }
            throw new InvalidFormatException("Invalid password format");
        }
        throw new InvalidFormatException("Invalid email format");
    }

    @Override
    public void delete() {
        //
    }
}
