import java.util.*;

public class Main {
    public static void main(String[] args) {

        List<User> users = new ArrayList<>();
        users.add(new Admin("admin1", "admin123"));
        users.add(new Customer("customer1", "customer123"));

        List<Saham> saham = new ArrayList<>();
        saham.add(new Saham("BBCA", "Bank Central Asia", 10000));
        saham.add(new Saham("TLKM", "Telkom Indonesia", 4500));

        List<SuratBerhargaNegara> sbn = new ArrayList<>();
        sbn.add(new SuratBerhargaNegara("SBR012", 6.0, 2, "01-06-2026", 5));
        sbn.add(new SuratBerhargaNegara("ORI023", 5.5, 2, "01-12-2027", 7));
    }
}
