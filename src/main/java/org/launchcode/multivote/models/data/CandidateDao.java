package org.launchcode.multivote.models.data;

import org.launchcode.multivote.models.Candidate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface CandidateDao extends CrudRepository<Candidate, Integer> {
}
