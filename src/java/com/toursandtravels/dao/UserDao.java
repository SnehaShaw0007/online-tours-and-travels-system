package com.toursandtravels.dao;

import com.toursandtravels.dto.User;

public interface UserDao {

    boolean registerUser(User user);

    User loginUser(String email, String password);
    String getSecurityQuestion(String email);

boolean resetPassword(String email, String answer, String newPassword);

}
