package main.com.ecommerce.services;

import main.com.ecommerce.models.Admin;
import main.com.ecommerce.exceptions.AdminNotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.List;


public class AdminService {
    private Map<Integer, Admin> adminMap = new HashMap<>();

    public void addAdmin(Admin admin) {
        adminMap.put(admin.getId(), admin);
    }

    public Admin getAdmin(int id) throws AdminNotFoundException {
        Admin admin = adminMap.get(id);
        if (admin == null) {
            throw new AdminNotFoundException("Admin not found with ID: " + id);
        }
        return admin;
    }
    public List<Admin> getAllAdmins() {
        return adminMap.values().stream().collect(Collectors.toList());
    }
}