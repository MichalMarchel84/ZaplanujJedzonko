package pl.coderslab.dao;

import pl.coderslab.model.Admin;
import pl.coderslab.utils.DaoMethods;
import pl.coderslab.utils.DbUtil;
import pl.coderslab.utils.EntityFactory;
import pl.coderslab.utils.Hashing;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AdminDao {

    private final DaoMethods<Admin> METHODS;

    public AdminDao() throws NoSuchMethodException {
        METHODS = new DaoMethods<>(Admin.class, "admins");
    }

    public int createAdmin(Admin admin) {
        admin.setPassword(Hashing.hashPassword(admin.getPassword()));
        return METHODS.create(admin);
    }

    public int deleteAdmin(int adminId) {
        return METHODS.delete(adminId);
    }

    public List<Admin> findAll() {
        return METHODS.readList("");
    }

    public Optional<Admin> findByEmail(String email) {

        return METHODS.readSingle("WHERE email = ?", email);
    }

    public Optional<Admin> findById(int adminId) {

        return METHODS.readSingle("WHERE id = ?", adminId);
    }

    public int updateAdmin(Admin admin) {

        return METHODS.update(admin);
    }
}
