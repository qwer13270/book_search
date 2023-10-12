public class Book implements IBook{
private String title;
private int pages;
private double rating;
private String authors;
private int ratingCounts;
private int totalReviews;
private String language;

/*
* Constructor class
*/
public Book(String title, String authors, double rating, int pages, String language, int ratingCounts) {
this.title = title;
this.authors = authors;
this.pages = pages;
this.rating = rating;
this.ratingCounts = ratingCounts;
this.language = language;
}

/*
* compareTo() method supports sorting books in descending order by rating
* @param IBook o to compare other object
* @return 1, 0,-1 based on the needs of descending or ascending
*/
@Override
public int compareTo(IBook o) {
if (o.getRating() < this.rating) {
return -1;
} else if (o.getRating() > this.rating) {
return 1;
} else {
return 0;
}
}

@Override
public String getTitle() {
return this.title;
}

@Override
public double getRating() {
return this.rating;
}

@Override
public int getNumberOfPages() {
return this.pages;
}

@Override
public String getAuthor() {
return this.authors;
}

@Override
public int getTotalRating() {
return this.ratingCounts;
}

@Override
public int getTotalReviews() {
return this.totalReviews;
}

@Override
public Boolean isSameLanguage(String language) {
if(this.language.equals(language))
return true;
return false;
}
/**
* toString() for testing
*
* @return
*/
public String toString() {
return "Title = " + title + "\n" +
"Number of Pages = " + pages + "\n" +
"Rating = " + rating + "\n" +
"Author = " + authors + "\n" +
"Total Rating = " + ratingCounts + "\n" +
"Total Reviews = " +totalReviews + "\n" +
"Language = " + language;
}
}

