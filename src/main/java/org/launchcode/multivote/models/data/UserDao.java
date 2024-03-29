package org.launchcode.multivote.models.data;

import org.launchcode.multivote.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface UserDao extends CrudRepository<User, Integer> {

    User findByName(String name);
}
