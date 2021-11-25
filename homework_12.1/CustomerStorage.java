import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerStorage {
    private final Map<String, Customer> storage;

    public CustomerStorage() {
        storage = new HashMap<>();
    }

    public void addCustomer(String data) {
        final int INDEX_NAME = 0;
        final int INDEX_SURNAME = 1;
        final int INDEX_EMAIL = 2;
        final int INDEX_PHONE = 3;

        try {
            String[] components = data.split("\\s+");

            Pattern patternMail = Pattern.compile("[a-z]+[@][a-z]+[.][a-z]+");
            Pattern phoneNumber = Pattern.compile("[+][7][0-9]{10}");
            Matcher matcherMail = patternMail.matcher(components[INDEX_EMAIL]);
            Matcher matcherPhone = phoneNumber.matcher(components[INDEX_PHONE]);

            if (components.length > 4 || !(matcherMail.reset(components[INDEX_EMAIL]).find()) ||
                    !(matcherPhone.reset(components[INDEX_PHONE]).find())) {
                throw new IllegalArgumentException();
            }

            String name = components[INDEX_NAME] + " " + components[INDEX_SURNAME];
            storage.put(name, new Customer(name, components[INDEX_PHONE], components[INDEX_EMAIL]));

        } catch (Exception e) {
            throw e;
        }
    }

    public void listCustomers() {
        storage.values().forEach(System.out::println);
    }

    public void removeCustomer(String name) {
        storage.remove(name);
    }

    public Customer getCustomer(String name) {
        return storage.get(name);
    }

    public int getCount() {
        return storage.size();
    }
}