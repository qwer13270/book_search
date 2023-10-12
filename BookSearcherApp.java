import java.util.ArrayList;
import java.util.List;

/**
 * Entry Main method for the app, step through all role classes to run the program
 */
public class BookSearcherApp {
    public static void main(String[] args) throws Exception {
        IBookLoader loader = new BookLoader();
        List<IBook> books = loader.loadBooks("books.xml");
        IBookSearcherBackend backend = new BookSearcherBackend(); //can delete books if merging
        for(IBook book : books) backend.addBook(book);
        IBookSearcherFrontend frontend = new BookFrontend(backend);
        frontend.runCommandLoop();
    }
}

