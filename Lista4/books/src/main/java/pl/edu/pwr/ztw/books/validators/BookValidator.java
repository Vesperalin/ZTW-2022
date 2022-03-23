package pl.edu.pwr.ztw.books.validators;

public class BookValidator {
    public static boolean isValid(int pages, String title, int authorId) {
        return pages > 0 && !title.equals("") && authorId > 0;
    }
}
