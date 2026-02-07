package com.toursandtravels.daoimpl;

import com.toursandtravels.dao.UserDao;
import com.toursandtravels.dto.User;
import com.toursandtravels.util.DBConnection;

import java.sql.*;

public class UserDaoImpl implements UserDao {

    @Override
    public boolean registerUser(User user) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "INSERT INTO USERS (NAME, PHONE_NO, EMAIL, PASSWORD, SECURITY_QUESTION, SECURITY_ANSWER) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, user.getName());
            ps.setString(2, user.getPhoneNo());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getSecurityQuestion());
            ps.setString(6, user.getSecurityAnswer());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
public String getSecurityQuestion(String email) {
    try {
        Connection con = DBConnection.getConnection();
        String sql = "SELECT SECURITY_QUESTION FROM USERS WHERE EMAIL=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, email);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getString("SECURITY_QUESTION");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}

@Override
public boolean resetPassword(String email, String answer, String newPassword) {
    try {
        Connection con = DBConnection.getConnection();

        String checkSql =
            "SELECT * FROM USERS WHERE EMAIL=? AND SECURITY_ANSWER=?";
        PreparedStatement ps1 = con.prepareStatement(checkSql);
        ps1.setString(1, email);
        ps1.setString(2, answer);

        ResultSet rs = ps1.executeQuery();
        if (!rs.next()) return false;

        String updateSql =
            "UPDATE USERS SET PASSWORD=? WHERE EMAIL=?";
        PreparedStatement ps2 = con.prepareStatement(updateSql);
        ps2.setString(1, newPassword);
        ps2.setString(2, email);

        return ps2.executeUpdate() > 0;

    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}


    @Override
    public User loginUser(String email, String password) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM USERS WHERE EMAIL=? AND PASSWORD=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("USER_ID"));
                user.setName(rs.getString("NAME"));
                user.setEmail(rs.getString("EMAIL"));
                return user;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
        
        
    }
}
