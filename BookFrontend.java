import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class BookFrontend implements IBookSearcherFrontend {

	private IBookSearcherBackend placeholder;
	Scanner scanner;
	private boolean running;
	private String cmd;
	ArrayList<String> language_filt;
	ArrayList<Integer> rating_filt;
	ArrayList<Integer> num_pages_filt;

	// constructor args (BookBackend) reads input from System.in
	public BookFrontend(IBookSearcherBackend placeholder) {
		this.scanner = new Scanner(System.in);
		this.placeholder = placeholder;
		language_filt = new ArrayList<>();
		rating_filt = new ArrayList<>();
		num_pages_filt = new ArrayList<>();

	}
	// constructor args (String, BookBackend) reads input from String
	public BookFrontend(String input, IBookSearcherBackend placeholder) {
		this.scanner = new Scanner(input);
		this.placeholder = placeholder;
		language_filt = new ArrayList<>();
		rating_filt = new ArrayList<>();
		num_pages_filt = new ArrayList<>();
	}

	/**
	 * This method drives the entire read, eval, print loop (repl) for the Book
	 * Search App. This loop will continue to run until the user explicitly enters
	 * the quit command.
	 */
	public void runCommandLoop() {
		System.out.println("Welcome to the Book Searcher App!");
		System.out.println("==================================");
		boolean done = false;
		while (!done) {
			displayCommandMenu();
			String decision = scanner.nextLine().toLowerCase();
			if (decision.trim().length() == 0) {
				System.out.println("No input, type again");
			}
			switch (decision) {
			case "r":
			case "1":
				System.out.println("Choose a rating: ");
				ratingSearch();
				break;
			case "t":
			case "2":
				System.out.println("Choose a word that you would like to search for: ");
				wordSearch();
				break;
			case "fnp":
			case "3":
				System.out.println("Enter the number of pages that you’d like filter:");
				numPageSearch();
				break;
			case "fl":
			case "4":
				System.out.println("Enter the langauge you would like to filter: ");
				languageSearch();
				break;
			case "ftr":
			case "5":
				System.out.println("Enter the number of total rating that you’d like filter:");
				totalRatingSearch();
				break;
			case "6":
			case "cf":
				clearFilter();
				System.out.println("Filter cleared");
				break;
			case "q":
			case "7":
				System.out.println("Thanks for using Book Searcher App");
				done = true;
				break;
			default:
				System.out.println("Choice invalid please type again");
				break;
			}
		}
	}

	/**
	 * display the command menu
	 */
	public void displayCommandMenu() {
		// prints command options to System.out
		System.out.println("Command Menu:");
		System.out.println("\t1) Search by [R]ating");
		System.out.println("\t2) Search by [T]title word");
		System.out.println("\t3) [F]ilter by Number of Pages [FNP]");
		System.out.println("\t4) [F]ilter by Language [FL]");
		System.out.println("\t5) [F]ilter by Total Rating [FTR]");
		System.out.println("\t6) Clear filter [CF]");
		System.out.println("\t7) [Q]uit");
		System.out.println("Choose a command from the menu above (number or letter in bracket): ");
	}

	/**
	 * display the books that will be call after search word and rating
	 */
	public void displayBooks(List<IBook> books) {
		List<IBook> filt_books = books;
		int number = 1;
		// go over the 3 filter lists to filter out the books
		if (language_filt.size() != 0) {
			for (int i = 0; i < language_filt.size(); i++) {
				filt_books = placeholder.getLanFilter(language_filt.get(i), filt_books);
			}

		}
		if (rating_filt.size() != 0) {
			for (int i = 0; i < rating_filt.size(); i++) {
				filt_books = placeholder.getRateFilter(rating_filt.get(i), filt_books);

			}

		}
		if (num_pages_filt.size() != 0) {
			for (int i = 0; i < num_pages_filt.size(); i++) {
				filt_books = placeholder.getPagesFilter(num_pages_filt.get(i), filt_books);

			}

		}
		System.out.println("Found " + filt_books.size() + "/" + placeholder.getNumberOfBooks() + " matches.");
		// After going through the filter, we then display the books using a for loop
		for (IBook book : filt_books) {
			System.out.println(number + ". " + book.getTitle() + "\n\t" + "Written by: " + book.getAuthor() + "\n\t"
					+ "Average rating: " + book.getRating() + "/5" + "\n\t" + "Number of Pages: "
					+ book.getNumberOfPages() + "\n\t" + "Rating counts: " + book.getTotalRating());
			number++;
		}
	}

	/**
	 * call when user decides to search a word for the title reads word from
	 * System.in, and display books
	 */
	public void wordSearch() {
		String filter = scanner.nextLine().toLowerCase().trim();
		if (filter.length() != 0) {
			List<IBook> filteredByWord = placeholder.searchByTitleWord(filter);
			if(filteredByWord != null) {
				displayBooks(filteredByWord);
			}
			else {
				System.out.println("nothing fonud from this title");
			}
		} else {
			System.out.println("Input invalid, please try again");
		}
	}

	/**
	 * call when user wants to search a sepcidifc rating between 0~5 reads word from
	 * System.in, and display books
	 */
	public void ratingSearch() {
		try {
			double filter = Double.parseDouble(scanner.nextLine());
			if (!(filter > 5.0)) {
				List<IBook> filteredByRating = placeholder.searchByRating(filter);
				if(filteredByRating != null) {
					displayBooks(filteredByRating);
				}
				else {
					System.out.println("nothing fonud from this rating");
				}
			} else {
				System.out.println("choose another rating");
			}
		} catch (Exception e) {
			System.out.print("please enter a number");
		}
	}

	/**
	 * call when user decides to filter language reads word from System.in, and
	 * print out the filter statement
	 */
	public void languageSearch() {
		String filter = scanner.nextLine();
		language_filt.add(filter);
		System.out.println(getfilterStatement(language_filt, rating_filt, num_pages_filt));
	}

	/**
	 * call when user decides to filter total num pages reads word from System.in,
	 * and print out the filter statement
	 */
	public void numPageSearch() {
		try {
			int filter = Integer.parseInt(scanner.nextLine());
			if (num_pages_filt.size() != 0) {
				if (num_pages_filt.get(0) >= filter) {
					num_pages_filt.remove(0);
					num_pages_filt.add(filter);
				}
			} else {
				num_pages_filt.add(filter);
			}
			System.out.println(getfilterStatement(language_filt, rating_filt, num_pages_filt));
		} catch (Exception e) {
			System.out.println("please enter a number");
		}

		// TODO Auto-generated method stub

	}

	/**
	 * call when user decides to filter total rating reads word from System.in, and
	 * print out the filter statement
	 */
	public void totalRatingSearch() {
		try {
			int filter = Integer.parseInt(scanner.nextLine());
			if (rating_filt.size() != 0) {
				if (rating_filt.get(0) <= filter) {
					rating_filt.remove(0);
					rating_filt.add(filter);
				}
			} else {
				rating_filt.add(filter);
			}
			System.out.println(getfilterStatement(language_filt, rating_filt, num_pages_filt));
		} catch (Exception e) {
			System.out.println("please enter a number");
		}

	}

	/**
	 * clear the filter by clearing the 3 filter lists
	 */
	public void clearFilter() {
		// clear filter
		language_filt.clear();
		rating_filt.clear();
		num_pages_filt.clear();
	}

	/**
	 * a private method that print the filter statement after each filter
	 * 
	 * @param language_filt  the language filter list
	 * @param rating_filt    the rating filter list
	 * @param num_pages_filt the total num pages filter list
	 * @return
	 */
	private String getfilterStatement(List<String> language_filt, List<Integer> rating_filt,
			List<Integer> num_pages_filt) {
		String statement = "Filter success, now it will only return";
		String lang = "";
		if (language_filt.size() != 0) {
			for (int i = 0; i < language_filt.size(); i++) {
				if (i != language_filt.size() - 1) {
					lang = lang + language_filt.get(i) + ", ";
				} else {
					lang = lang + language_filt.get(i);
				}
			}
			statement = statement + " language in " + lang;
		}
		if (num_pages_filt.size() != 0) {
			statement = statement + ", number of pages < " + num_pages_filt.get(0);
		}
		if (rating_filt.size() != 0) {
			statement = statement + ", total rating > " + rating_filt.get(0);
		}
		return statement;
	}

}
