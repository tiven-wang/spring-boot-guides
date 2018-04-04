package wang.tiven.reactive.web.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.*;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import wang.tiven.reactive.web.model.Tweet;
import wang.tiven.reactive.web.repository.TweetRepository;

public class TweetRouter {

    private final TweetRepository repository;
    private final TweetHandler tweetHandler;
    
    public TweetRouter(TweetRepository repository) {
        this.repository = repository;
        this.tweetHandler = new TweetHandler(repository);
    }
    
    public RouterFunction<ServerResponse> router() {
        return nest(path("/tweets"), 
                    route(GET("/{id}"), request -> {
                        return repository.findById(request.pathVariable("id"))
                            .map(savedTweet -> ok().syncBody(savedTweet).block())
                            .defaultIfEmpty(notFound().build().block());
                    })
                    .andRoute(PUT("/{id}"), request -> {
                        return repository.findById(request.pathVariable("id"))
                            .flatMap(existingTweet -> {
                                return request.bodyToMono(Tweet.class)
                                    .map(tweet -> {
                                        existingTweet.setText(tweet.getText());
                                        return existingTweet;
                                    })
                                    .flatMap(repository::save);
                            })
                            .map(updatedTweet -> ok().syncBody(updatedTweet).block())
                            .defaultIfEmpty(notFound().build().block());
                    })
                    .andRoute(DELETE("/{id}"), request -> {
                        return repository.findById(request.pathVariable("id"))
                            .flatMap(existingTweet -> repository.delete(existingTweet).then(ok().build()))
                            .defaultIfEmpty(notFound().build().block());
                    })
                    .andRoute(method(HttpMethod.GET).and(accept(APPLICATION_JSON)), this.tweetHandler::getAllTweets)
                    .andRoute(method(HttpMethod.POST).and(accept(APPLICATION_JSON)), this.tweetHandler::createTweets)
            )
            .andRoute(GET("/stream/tweets"), request -> {
                return ok().contentType(MediaType.TEXT_EVENT_STREAM).body(repository.findAll(), Tweet.class);
            });
    }
}