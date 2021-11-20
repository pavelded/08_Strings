import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneBook {

    Map<String, String> phoneBook = new HashMap<>();

    public void addContact(String phone, String name) {
        // проверьте корректность формата имени и телефона (отдельные методы для проверки)
        // если такой номер уже есть в списке, то перезаписать имя абонента
        String regexName = "[а-я]+"; //[а-я]+
        String regexPhone = "[7][0-9]+"; //[7][0-9]+
        Pattern patternName = Pattern.compile(regexName);
        Pattern patternPhone = Pattern.compile(regexPhone);
        Matcher matcherName = patternName.matcher(name);
        Matcher matcherPhone = patternPhone.matcher(phone);

        Set<Map.Entry<String, String>> phoneBookSet = phoneBook.entrySet();
        String oldName = "";
        String oldPhoneNumber = "";

        for (Map.Entry<String, String> entry : phoneBookSet) {
            oldPhoneNumber = entry.getValue();
            if (oldPhoneNumber.contains(phone)) {
                oldName = entry.getKey();
            }
        }
        if (matcherName.reset(name).find() && matcherPhone.reset(phone).find() && !(phoneBook.containsKey(name)) && !(phoneBook.containsValue(phone))) {
            phoneBook.put(name, phone);
        } else if (matcherName.reset(name).find() && matcherPhone.reset(phone).find() && phoneBook.containsValue(phone) && !(phoneBook.containsKey(name))) {
            phoneBook.remove(oldName, oldPhoneNumber);
            phoneBook.put(name, phone);
        } else if (matcherName.reset(name).find() && matcherPhone.reset(phone).find() && !(phoneBook.containsValue(phone)) && phoneBook.containsKey(name)) {
            phoneBook.computeIfPresent(name, (k, v) -> v + ", " + phone);
        }
    }

    public String getContactByPhone(String phone) {
        // формат одного контакта "Имя - Телефон"
        // если контакт не найдены - вернуть пустую строку
        Set<Map.Entry<String, String>> phoneBookSet = phoneBook.entrySet();

        for (Map.Entry<String, String> entry : phoneBookSet) {
            String phoneNumber = entry.getValue();
            if (phoneNumber.equals(phone)) {
                return entry.getKey() + " - " + phoneNumber;
            }
        }
        return "";
    }

    public Set<String> getContactByName(String name) {
        // формат одного контакта "Имя - Телефон"
        // если контакт не найден - вернуть пустой TreeSet
        Set<String> getContactByName = new TreeSet<String>();
        StringBuilder valuesAll = new StringBuilder();
        if (phoneBook.containsKey(name)) {
            getContactByName.add(name + " - " + phoneBook.get(name));
            return getContactByName;
        }
        return new TreeSet<>();
    }

    public Set<String> getAllContacts() {
        Set<String> allContacts = new HashSet<String>();
        Set<Map.Entry<String, String>> phoneBookSet = phoneBook.entrySet();
        if (!(phoneBook.isEmpty())) {
        for (Map.Entry<String, String> entry : phoneBookSet) {
            String phoneNumbers = entry.getValue();
            String phoneNames = entry.getKey();
            allContacts.add(phoneNames + " - " + phoneNumbers);
        }
            return allContacts;
        }
        return new TreeSet<>();
    }
}