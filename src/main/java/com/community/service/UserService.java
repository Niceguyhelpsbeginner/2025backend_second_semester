package com.community.service;

import com.community.dao.UserDao;
import com.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserDao userDao;

    public void register(User user) {
        // 중복 체크
        if (userDao.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("이미 존재하는 사용자명입니다.");
        }
        userDao.save(user);
    }

    public Optional<User> login(String username, String password) {
        Optional<User> user = userDao.findByUsername(username);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return user;
        }
        return Optional.empty();
    }

    public Optional<User> findById(Long userId) {
        return userDao.findById(userId);
    }

    public Optional<User> findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    public List<User> findAll() {
        return userDao.findAll();
    }

    public void update(User user) {
        userDao.update(user);
    }

    public void delete(Long userId) {
        userDao.delete(userId);
    }
}







