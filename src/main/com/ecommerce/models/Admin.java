package main.com.ecommerce.models;

public class Admin extends User {
    private String adminLevel; // Örneğin: SuperAdmin, Moderator vs.

    public Admin(int id, String username, String password, String email, String adminLevel) {
        super(id, username, password, email, "ADMIN");
        this.adminLevel = adminLevel;
    }

   
    public Admin() {
        super();
    }

    public String getAdminLevel() {
        return adminLevel;
    }

    public void setAdminLevel(String adminLevel) {
        this.adminLevel = adminLevel;
    }

    @Override
    public String toString() {
        return super.toString() + " Admin Level: " + adminLevel;
    }
}
