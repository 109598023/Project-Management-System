package edu.ntut.se1091.team1.pms.repository;

import edu.ntut.se1091.team1.pms.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    Project findById(long id);
}
