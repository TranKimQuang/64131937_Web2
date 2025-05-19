package edu.quangtk.services;

import edu.quangtk.entity.Faculty;
import edu.quangtk.entity.User;
import edu.quangtk.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(User user) {
        // Mã hóa mật khẩu trước khi lưu
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public List<User> getAllUsers(Faculty facultyId) {
        return (facultyId != null) ? userRepository.findByFacultyId(facultyId) : userRepository.findAll();
    }
    public long getUserCount() {
        return userRepository.count();
    }
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }

    public User updateUser(Long id, User updatedUser) {
        User user = getUserById(id);
        
        if (updatedUser.getUsername() != null) {
            user.setUsername(updatedUser.getUsername());
        }
        
        // Mã hóa mật khẩu nếu có thay đổi
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }
        
        if (updatedUser.getFullName() != null) {
            user.setFullName(updatedUser.getFullName());
        }
        
        if (updatedUser.getEmail() != null) {
            user.setEmail(updatedUser.getEmail());
        }
        
        if (updatedUser.getRole() != null) {
            user.setRole(updatedUser.getRole());
        }
        
        if (updatedUser.getFaculty() != null) {
            user.setFaculty(updatedUser.getFaculty());
        }
        
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }
}