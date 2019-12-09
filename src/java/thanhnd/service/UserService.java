/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thanhnd.service;

import org.hibernate.Session;
import thanhnd.entity.User;
import thanhnd.repository.UserRepository;

/**
 *
 * @author thanh
 */
public class UserService {
    private final UserRepository userRepository;

    public UserService(Session hibernateSession) {
        this.userRepository = new UserRepository(hibernateSession);
    }

    public User findByUsernameAndPassword(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }
}
