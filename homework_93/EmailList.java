import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailList {

    HashSet<String> emailList = new HashSet<>();

    public void add(String email) {
        String regex = "[a-z]+[@][a-z]+[.][a-z]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (emailList.contains(email.toLowerCase()) || emailList.contains(email)) {
        } else if (matcher.find()) {
            emailList.add(email);
        }
    }

    public List<String> getSortedEmails() {
        List<String> emailListArray = new ArrayList<>(emailList);
        Collections.sort(emailListArray);
        return emailListArray;
    }

}
