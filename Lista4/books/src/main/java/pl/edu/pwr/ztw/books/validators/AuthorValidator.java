package pl.edu.pwr.ztw.books.validators;

public class AuthorValidator {
    public static boolean isValid(String firstName, String lastName) {
        return !firstName.equals("") && !lastName.equals("");
    }
}
