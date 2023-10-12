public interface IBook extends Comparable<IBook> {

    // constructor args (String title, int rating, int pages, String language, int ratingCounts)
    // where the language the book is written in and ratingCounts contains the number of
    // total ratings the books has received

    String getTitle(); // retrieve the title of this book object
    double getRating(); // retrieve the Rating of the book (out of 5)
    int getNumberOfPages(); // retrieves the total page number of the book
    String getAuthor(); //retrieves the author of the book
    int getTotalRating(); //retrievs the total number of ratings the books has received
    int getTotalReviews(); // retrives the total reviews
    Boolean isSameLanguage(String language); //check if the book is written in same language
    // compareTo() method supports sorting books in descending order by rating

}
