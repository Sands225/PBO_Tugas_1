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
        users.add(new Admin("Admin", "admin123"));
        users.add(new Customer("Sandi", "sandi123"));
        users.add(new Customer("Eri", "eri123"));

        saham.add(new Saham("BBCA", "Bank Central Asia", 10_000));
        saham.add(new Saham("TLKM", "Telkom Indonesia", 4_500));

        sbn.add(new SBN("SBR012", 6.0, 2, "01-06-2026", 800_000_000));
        sbn.add(new SBN("ORI023", 5.5, 2, "01-12-2027", 700_000_000));

        customerSaham.add(new CustomerSaham("Sandi", new Saham("BBCA", "Bank Central Asia", 10_000), 100, 1_000_000));
        customerSaham.add(new CustomerSaham("Eri", new Saham("BBCA", "Bank Central Asia", 10_000), 100, 1_000_000));

        customerSBN.add(new CustomerSBN("Sandi", new SBN("ORI023", 5.5, 2, "01-12-2027", 8_000_000), 4_00_000_000));
        customerSBN.add(new CustomerSBN("Eri", new SBN("ORI023", 5.5, 2, "01-12-2027", 8_000_000), 4_00_000_000));

    }
}
