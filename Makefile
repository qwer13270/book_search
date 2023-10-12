run: BookSearcherApp.class
	java BookSearcherApp

BookSearcherApp.class:
	javac BookSearcherApp.java

BackendDeveloperTests.class: BackendDeveloperTests.java
	javac IBook.java
	javac BookBD_Placeholder.java
	javac RedBlackTreeSortedSetsBD_Placeholder.java
	javac BookSearcherBackend.java
	javac IBookSearcherBackend.java
	javac -cp .:junit5.jar BackendDeveloperTests.java

FrontendDeveloperTests.class: FrontendDeveloperTests.java
	javac Book.java
	javac BookFrontend.java
	javac BookPlaceholder.java
	javac BookSearcherBackendPlaceholder.java
	javac IBook.java
	javac IBookSearcherBackend.java
	javac IBookSearcherFrontend.java
	javac TextUITester.java
	javac -cp .:junit5.jar FrontendDeveloperTests.java

AlgorithmTests.class: AlgorithmEngineerTests.java
	javac -cp .:junit5.jar AlgorithmEngineerTests.java -Xlint
	javac RedBlackTree.java
	javac RedBlackTreeSortedSets.java
	javac IRedBlackTreeSortedSets.java
	javac SortedCollectionInterface.java


DataWranglerTests.class: DataWranglerTests.java IBook.class IBookLoader.class
	javac Book.java
	javac BookFrontend.java
	javac BookPlaceholder.java
	javac BookSearcherBackend.java
	javac IBook.java
	javac IBookSearcherBackend.java
	javac IBookSearcherFrontend.java
	javac TextUITester.java
	javac -cp .:junit5.jar DataWranglerTests.java


IBook.class: IBook.java Book.java
	javac IBook.java
	javac Book.java

IBookLoader.class: IBookLoader.java BookLoader.java
	javac IBookLoader.java
	javac BookLoader.java

clean:
	rm *.class
	rm *~
