package wang.tiven.reactive.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;

import wang.tiven.reactive.web.controller.TweetRouter;
import wang.tiven.reactive.web.repository.TweetRepository;

@SpringBootApplication
public class WebApplication {

	public static void main(String[] args) {
		// System.out.println(greet(Arrays.asList("Larry", "Moe", "Curly")));
		SpringApplication.run(WebApplication.class, args);
	}

	@Bean
    public RouterFunction<?> routerFunctionTweet(TweetRepository repository) {
        // System.out.println("WebConfig EnableWebFlux ...");
        return new TweetRouter(repository).router();
    }

}
