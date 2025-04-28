package services;

import data.DataStore;
import models.Admin;
import models.Customer;
import models.User;
import views.AdminView;
import views.CustomerView;
import views.View;

public class AuthService {
    public boolean loginHandler(String inputName, String inputPassword) {
        for (User user: DataStore.users) {
            if (user.getName().equals(inputName) && user.getPassword().equals(inputPassword)) {
                if (user instanceof Admin) {
                    Admin admin = (Admin) user;
                    View.greetUser(admin.getName());

                    AdminView.adminMenu();
                    return true;
                } else if (user instanceof Customer) {
                    Customer customer = (Customer) user;
                    View.greetUser(customer.getName());

                    CustomerView.customerMenu(customer);
                    return true;
                }
            }
        }
        System.out.println("Username atau password tidak valid! Coba lagi!");
        return false;
    }
}
