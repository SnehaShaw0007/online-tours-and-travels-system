package com.toursandtravels.dao;

import com.toursandtravels.dto.Admin;

public interface AdminDao {

    boolean registerAdmin(Admin admin);

    Admin loginAdmin(String email, String password);
    String getSecurityQuestion(String email);

boolean resetPassword(String email, String answer, String newPassword);

}
