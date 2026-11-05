public class UserService {
    public boolean isValidEmail(String email) {
        return email != null
                && email.contains("@")
                && email.indexOf("@") > 0
                && email.indexOf("@") < email.length() - 1;
    }
}