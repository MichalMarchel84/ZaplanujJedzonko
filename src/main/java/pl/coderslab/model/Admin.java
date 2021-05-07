package pl.coderslab.model;

public class Admin implements Comparable<Admin> {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private int superadmin;
    private int enable;

    public int compareTo(Admin admin) {
        return (int) (this.id - admin.getId());
    }

    public Admin() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Admin(String firstName, String lastName, String email, String password, int superadmin) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.superadmin = superadmin;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public int getSuperadmin() {
        return superadmin;
    }

    public void setSuperadmin(int superadmin) {
        this.superadmin = superadmin;
    }

    @Override
    public String toString(){
        return id + " " + firstName + " " + lastName + " " + email + " " + password + " " + superadmin + "" + enable;
    }

    public void setEnable(int enabled) {
        this.enable = enabled;
    }

    public int getEnable() {
        return enable;
    }
}
