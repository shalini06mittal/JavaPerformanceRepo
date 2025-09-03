package escape.references.challenge.solution;

public interface ReadonlyBook {

	int getId();

	String getTitle();

	String getAuthor();

	String toString();

	Price getPrice();

}