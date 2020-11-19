package edu.ntut.se1091.team1.pms.persistence.dao;

import edu.ntut.se1091.team1.pms.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}