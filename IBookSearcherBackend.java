import java.util.List;
/**
 * An instance of a class that implements the following interface can be used
 * to search and retrieve the database of books within the BookSearcher app.
 */
public interface IBookSearcherBackend {

    public void addBook(IBook book); // adds book to backend database
    public int getNumberOfBooks(); // retrieve number of books in database

    // these methods can be used to look-up books by title word or rating
    public List<IBook> searchByTitleWord(String word);
    public List<IBook> searchByRating(double rate);

    // calling either search method before setting the desired filters:
    // (all language, rating, and numPages are included in search results by default)
    // the results are searched according to the searchByTitleWord and searchByRating set above
    public List<IBook> getLanFilter(String lan, List<IBook> lt);

    // calling either search method before setting the desired filters:
    // (all language, number of ratings, and numPages are included in search results by default)
    // the results are searched according to the searchByTitleWord and searchByRating set above
    public List<IBook> getRateFilter(int numRates, List<IBook> lt);

    // calling either search method before setting the desired filters:
    // (all language, rating, and numPages are included in search results by default)
    // the results are searched according to the searchByTitleWord and searchByRating set above
    public List<IBook> getPagesFilter(int numPages, List<IBook> lt);

}

