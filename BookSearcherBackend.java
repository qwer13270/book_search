import java.util.*;

/**
 * BookSearcherBackend implements IBookSearcherBackend interface
 * it is used to search and retrieve the database of shows within the BookSearcher app.
 */
@SuppressWarnings("all")
public class BookSearcherBackend implements IBookSearcherBackend{
    // TreeMap class for searching and retrieve the database by mapping titles and Book objects
    protected IRedBlackTreeSortedSets<String, List<IBook>> titleTree;
    // TreeMap class for searching and retrieve the database by mapping ratings and Book objects
    protected IRedBlackTreeSortedSets<Double, List<IBook>> ratingTree;
    private int booknumber;

    /**
     * use it while merging
     */
    public BookSearcherBackend() {
        this.titleTree =  new RedBlackTreeSortedSets<>();
        this.ratingTree = new RedBlackTreeSortedSets<>();
        this.booknumber = 0;
    }


    /**
     * add books to titleTree and ratingTree
     * @param book book object being added
     */
    @Override
    public void addBook(IBook book) {
        // if null then unable to add to the tree
        // no need for rating since default is 0
        // can be added for the rest
        if(book == null || book.getTitle() == null){
            return;
        }
        List<IBook> lt = new LinkedList<>();
        lt.add(book);

        this.booknumber++;
        //use first word as title key rather than the entire title
        //since the book obj also got the entire title
        String[] s = book.getTitle().split(" ");
        String firstWord = s[0];
        //adjust to the first word by the request of AE
        String target = firstWord.toLowerCase();
        //based on AE, change from book object to a lt while inserting
        titleTree.insert(target, lt);
        ratingTree.insert(book.getRating(), lt);
    }

    /**
     * get the total number of books
     * @return the book number of the entire database based on either the rating or the title keys
     */
    @Override
    public int getNumberOfBooks() {
        return this.booknumber;
    }

    /**
     * search all book objects in database matched with the providing String title, it can be the first word of the title
     * no such element of book obj then return null
     * @param word title in the
     * @return list of all books matched with the word searching for
     */
    @Override
    public List<IBook> searchByTitleWord(String word) {
        // no reason to search for an empty title
        if(word == null){
            return null;
        }
        String[] s = word.split(" ");
        String firstWord = s[0];
        //adjust to the first word by the request of AE
        String target = firstWord.toLowerCase();
        //no key for that book after searching
        ArrayList<IBook> result = new ArrayList<>();
        LinkedList<IBook> linkedList;

        try{
            linkedList = (LinkedList<IBook>) titleTree.search(target);
        }catch(NoSuchElementException e){
            return null;
        }

        //got the matching key and books
        for (IBook book :linkedList) {
            String[] titleCheck = book.getTitle().split(" ");
            String firstWordTitle = titleCheck[0].toLowerCase();
            //double-check the title to match the first word input from users
            if(!firstWordTitle.contains(target)){
                continue;
            }
            result.add(book);
        }

        return result;
    }

    /**
     * search all book objects in database matched with the providing double rate
     * @param rate rate integer being provided to search
     * @return list of all books matched with the double rate searching for
     */
    @Override
    public List<IBook> searchByRating(double rate) {
//        //in case the change from AE red version, which means I should add book rather than a list to the tree
//        //no key for that book after searching
//        ArrayList<IBook> result;
//        try{
//            result = (ArrayList<IBook>)ratingTree.search(rate);
//        }catch(NoSuchElementException e){
//            return null;
//        }
//        //got the matching key and books
//        result = (ArrayList<IBook>)ratingTree.search(rate);
//        ArrayList<IBook> finals = new ArrayList<>();
//        for (IBook book: result) {
//            finals.add(book);
//        }
//        return finals;


        //no key for that book after searching
        ArrayList<IBook> result = new ArrayList<>();
        LinkedList<IBook> linkedList;
        try{
            linkedList = (LinkedList<IBook>) ratingTree.search(rate);
        }catch(NoSuchElementException e){
            return null;
        }

        //got the matching key and books
        for(IBook b: linkedList){
            //double check for the rate
            if(b.getRating()!=rate){
                continue;
            }
            result.add(b);
        }
        return result;
    }

    /**
     * filter for the language is looking for in the providing list of books
     * @param lan language a string to filter / look for
     * @param lt list book objects
     * @return finals list of the books after filtering
     */
    @Override
    public List<IBook> getLanFilter(String lan, List<IBook> lt) {
        if(lan == null){
            throw new IllegalArgumentException("arg cannot be a null language");
        }
        if(lt.size() == 0 || lt == null){
            throw new IllegalArgumentException("arg cannot be a null lt");
        }
        ArrayList<IBook> finals = new ArrayList<>();
        for (IBook book: lt) {
            if(book!= null && book.isSameLanguage(lan)){
                finals.add(book);
            }
        }
        return finals;
    }

    /**
     * filter for the num of rating is looking for in the providing list of books
     * @param numRates ratingCounts a int to filter / look for
     * @param lt list book objects
     * @return finals list of the books after filtering
     */
    @Override
    public List<IBook> getRateFilter(int numRates, List<IBook> lt) {
        if(lt.size() == 0 || lt == null){
            return null;
        }

        ArrayList<IBook> finals = new ArrayList<>();
        for (IBook book: lt) {
            if(book!= null && book.getTotalRating() > numRates){
                finals.add(book);
            }
        }
        return finals;
    }

    /**
     * filter for the num of rating is looking for in the providing list of books
     * @param numPages num of pages int to filter / look for
     * @param lt list book objects
     * @return finals list of the books after filtering
     */
    @Override
    public List<IBook> getPagesFilter(int numPages, List<IBook> lt) {
        if(lt.size() == 0 || lt == null){
            return null;
        }
        ArrayList<IBook> finals = new ArrayList<>();
        for (IBook book: lt) {
            if(book!= null && book.getNumberOfPages() < numPages){
                finals.add(book);
            }
        }
        return finals;
    }
}

