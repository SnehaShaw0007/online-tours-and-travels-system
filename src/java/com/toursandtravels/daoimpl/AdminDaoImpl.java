package com.toursandtravels.daoimpl;

import com.toursandtravels.dao.AdminDao;
import com.toursandtravels.dto.Admin;
import com.toursandtravels.util.DBConnection;

import java.sql.*;

public class AdminDaoImpl implements AdminDao {

    @Override
    public boolean registerAdmin(Admin admin) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "INSERT INTO ADMIN (NAME, PHONE_NO, EMAIL, PASSWORD, SECURITY_QUESTION, SECURITY_ANSWER) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, admin.getName());
            ps.setString(2, admin.getPhoneNo());
            ps.setString(3, admin.getEmail());
            ps.setString(4, admin.getPassword());
            ps.setString(5, admin.getSecurityQuestion());
            ps.setString(6, admin.getSecurityAnswer());

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
        String sql = "SELECT SECURITY_QUESTION FROM ADMIN WHERE EMAIL=?";
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
            "SELECT * FROM ADMIN WHERE EMAIL=? AND SECURITY_ANSWER=?";
        PreparedStatement ps1 = con.prepareStatement(checkSql);
        ps1.setString(1, email);
        ps1.setString(2, answer);

        ResultSet rs = ps1.executeQuery();
        if (!rs.next()) return false;

        String updateSql =
            "UPDATE ADMIN SET PASSWORD=? WHERE EMAIL=?";
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
    public Admin loginAdmin(String email, String password) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM ADMIN WHERE EMAIL=? AND PASSWORD=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Admin admin = new Admin();
                admin.setAdminId(rs.getInt("ADMIN_ID"));
                admin.setName(rs.getString("NAME"));
                admin.setEmail(rs.getString("EMAIL"));
                return admin;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
