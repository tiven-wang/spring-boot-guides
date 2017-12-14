package wang.tiven.springbootguides.caching;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(AppRunner.class);

	private final BookRepository bookRepository;

	public AppRunner(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@Override
	public void run(String... args) throws Exception {

		logger.info(".... Saving books");
		if(null == bookRepository.getByIsbn("isbn-1234")) {
			logger.info("isbn-1234 -->" + bookRepository.save(new Book("isbn-1234", "isbn 1234")));
		}
		if(null == bookRepository.getByIsbn("isbn-4567")) {
			logger.info("isbn-4567 -->" + bookRepository.save(new Book("isbn-4567", "isbn 4567")));
		}

		logger.info(".... Fetching books");
		logger.info("isbn-1234 -->" + bookRepository.getByIsbn("isbn-1234"));
		logger.info("isbn-4567 -->" + bookRepository.getByIsbn("isbn-4567"));
		logger.info("isbn-1234 -->" + bookRepository.getByIsbn("isbn-1234"));
		logger.info("isbn-4567 -->" + bookRepository.getByIsbn("isbn-4567"));
		logger.info("isbn-1234 -->" + bookRepository.getByIsbn("isbn-1234"));
		logger.info("isbn-1234 -->" + bookRepository.getByIsbn("isbn-1234"));

		logger.info(".... Cleaning books");
		bookRepository.deleteAll();
	}

}
