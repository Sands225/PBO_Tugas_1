import java.util.List;
import java.util.Scanner;

class Admin extends User {
    public Admin(String name, String password) {
        super(name, password, "admin");
    }
}