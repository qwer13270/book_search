import java.util.List;
import java.io.FileNotFoundException;
/**
 * Instances of classes that implement this interface can be used to load a 
 * list of books from a specified csv source file.
 * The following csv columns are used to load these show attributes:
 *   - title: the title of the book
 *   - authors: the author of the book
 *   - average_rating: the average of all ratings the book has received
 *   - language_code: the language the book is written in
 *   - num_pages: total number of pages the book has
 *   - ratings_count: number of ratings received by the book
 *   - text_reviews_count: number of text review received by the book
 */
public interface IBookLoader {

    /**
     * This method loads the list of books described within a CSV file.
     * @param filepath is relative to executable's working directory
     * @return a list of book objects that were read from specified file
     */
    List<IBook> loadBooks(String filepath) throws FileNotFoundException;

}
