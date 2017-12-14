package wang.tiven.springbootguides.caching;

import org.springframework.data.repository.CrudRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

public interface BookRepository extends CrudRepository<Book, Long> {

  @Cacheable("books")
  Book getByIsbn(String isbn);

  @CachePut(cacheNames = "books", key = "#p0.isbn")
  Book save(Book isbn);

  @CacheEvict(cacheNames = "books", allEntries = true)
  void deleteAll();
}
