package utils;

public class ValidationUtils {
    public static boolean isValidPhoneNumber(String phone) {
        return phone != null && phone.matches("^\\+?[0-9]{10,13}$");
    }

    public static boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty();
    }
}

