package Application.Interface;

import Application.Entity.MatchTable;

import org.springframework.data.repository.CrudRepository;

public interface MatchRepository extends CrudRepository<MatchTable, Long> {
}