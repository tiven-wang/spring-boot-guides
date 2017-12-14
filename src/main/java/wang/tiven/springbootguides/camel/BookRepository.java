package wang.tiven.springbootguides.camel;

public interface BookRepository {

	Iterable<Book> getAll();

	Book getByIsbn(String isbn);

}