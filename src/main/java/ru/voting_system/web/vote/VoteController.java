package ru.voting_system.web.vote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.voting_system.model.Vote;
import ru.voting_system.service.VoteService;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.voting_system.web.SecurityUtil.authUserId;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {

    static final String REST_URL = "/votes";

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    VoteService voteService;

    @GetMapping
    public List<Vote> getAllForAuthUser() {
        log.info("Get all votes for user with id={}", authUserId());
        return voteService.getAllByUserIdWithRestaurants(authUserId());
    }

    @GetMapping("/filter")
    public List<Vote> getByDate(@RequestParam @Nullable LocalDate date) {
        log.info("Get all votes for {}", date);
        return voteService.getAllByDateWithRestaurants(date);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Vote> vote(@RequestBody Vote vote) throws Exception {
        log.info("vote for restaurant: {}", vote.getRestaurant().getId());
        Vote created = voteService.vote(vote.getRestaurant().getId(), authUserId());
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
