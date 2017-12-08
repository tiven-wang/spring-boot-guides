package wang.tiven.springbootguides.caching;

public interface BookRepository {

    Book getByIsbn(String isbn);

}
