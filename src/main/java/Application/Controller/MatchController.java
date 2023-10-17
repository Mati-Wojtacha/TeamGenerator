package Application.Controller;

import Application.Entity.MatchTable;
import Application.Interface.MatchRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matches")
public class MatchController {
    private final MatchRepository matchRepository;

    @Autowired
    public MatchController(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }
    @GetMapping
    public ResponseEntity<List<MatchTable>> getAllMatches() {
        List<MatchTable> matches = (List<MatchTable>) matchRepository.findAll();
        if (!matches.isEmpty()) {
            return new ResponseEntity<>(matches, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatchTable> getMatchById(@PathVariable Long id) {
        MatchTable match = matchRepository.findById(id).orElse(null);
        if (match != null) {
            return new ResponseEntity<>(match, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<MatchTable> createMatch(@RequestBody MatchTable match) {
        MatchTable savedMatch = matchRepository.save(match);
        return new ResponseEntity<>(savedMatch, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MatchTable> updateMatch(@PathVariable Long id, @RequestBody MatchTable updatedMatch) {
        MatchTable match = matchRepository.findById(id).orElse(null);
        if (match != null) {
            updatedMatch.setId(match.getId());
            MatchTable savedMatch = matchRepository.save(updatedMatch);
            return new ResponseEntity<>(savedMatch, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteMatch(@PathVariable Long id) {
        MatchTable match = matchRepository.findById(id).orElse(null);
        if (match != null) {
            matchRepository.delete(match);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
