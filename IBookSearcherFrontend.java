import java.util.List;

/**
 * Instances of classes that implement this interface can be used to drive a
 * console based text user interface for the BookSearcher App
 */
public interface IBookSearcherFrontend {
	// constructor args (IBookSearcherBackend) reads input from
	// System.in

	// contructor args (String, IBookSearcherBackend) reads input
	// from String

	/**
	 * This method derives the entire read, eval, print loop (repl) for the Book
	 * Searcher App. This loop will continue to run until the user enters the quit
	 * command
	 */
	
	void runCommandLoop(); // the following helper methids will help support runCommandLoop():

	public void displayCommandMenu();// prints command options to System.out

	public void displayBooks(List<IBook> Books);// display list of books

	public void wordSearch();// reads word from System.in, display results

	public void ratingSearch();// reads rating from System.in, displays results

	public void languageSearch();// reads language from System.in, displays results

	public void numPageSearch();// reads page nnumber from System.in, displayes results
	
	public void totalRatingSearch();// reads page nnumber from System.in, displayes results

	public void clearFilter();// toggles off all filters

}
