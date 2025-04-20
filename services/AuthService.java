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
                    System.out.printf("Selamat datang %s!\n", admin.getName());

                    AdminView adminView = new AdminView();
                    adminView.adminMenu();
                    return true;
                } else if (user instanceof Customer) {
                    Customer customer = (Customer) user;
                    System.out.printf("Selamat datang %s!\n", customer.getName());

                    CustomerView customerView = new CustomerView();
                    customerView.customerMenu(customer);
                    return true;
                }
            }
        }
        System.out.println("Username atau password tidak valid! Coba lagi!");
        return false;
    }
}
