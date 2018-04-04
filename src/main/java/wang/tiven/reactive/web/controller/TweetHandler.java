package wang.tiven.reactive.web.controller;

import static org.springframework.http.MediaType.*;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import wang.tiven.reactive.web.model.Tweet;
import wang.tiven.reactive.web.repository.TweetRepository;

public class TweetHandler {
    private final TweetRepository repository;

    public TweetHandler(TweetRepository repository) {
        this.repository = repository;
    }

    public Mono<ServerResponse> getAllTweets(ServerRequest request) { 
        Flux<Tweet> tweet = repository.findAll();
        return ServerResponse.ok().contentType(APPLICATION_JSON_UTF8).body(tweet, Tweet.class);
    }

    public Mono<ServerResponse> createTweets(ServerRequest request) {
        Mono<Tweet> tweet = request.bodyToMono(Tweet.class);
        return ServerResponse.ok().contentType(APPLICATION_JSON_UTF8).body(tweet.flatMap(repository::save), Tweet.class);
    }

}