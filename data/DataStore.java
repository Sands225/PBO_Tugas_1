package data;

import java.util.ArrayList;
import java.util.List;

import models.*;

public class DataStore {
    public static final List<User> users = new ArrayList<>();
    public static final List<Saham> saham = new ArrayList<>();
    public static final List<SBN> sbn = new ArrayList<>();

    public static final List<CustomerSaham> customerSaham = new ArrayList<>();
    public static final List<CustomerSBN> customerSBN = new ArrayList<>();

    static {
        // Initialize users
        users.add(new Admin("admin1", "admin123"));
        users.add(new Customer("customer1", "customer123"));

        // Initialize saham
        saham.add(new Saham("BBCA", "Bank Central Asia", 10000));
        saham.add(new Saham("TLKM", "Telkom Indonesia", 4500));

        // Initialize SBN
        sbn.add(new SBN("SBR012", 6.0, 2, "01-06-2026", 5));
        sbn.add(new SBN("ORI023", 5.5, 2, "01-12-2027", 7));
    }
}
