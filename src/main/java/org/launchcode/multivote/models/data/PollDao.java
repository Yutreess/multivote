package org.launchcode.multivote.models.data;

import org.launchcode.multivote.models.Poll;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Repository
@Transactional
public interface PollDao extends CrudRepository<Poll, Integer> {

    ArrayList<Poll> findAllByUser(int ownerUserId);
    Poll findByUser(int ownerUserId);
}
