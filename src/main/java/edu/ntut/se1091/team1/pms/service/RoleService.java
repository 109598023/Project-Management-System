package edu.ntut.se1091.team1.pms.service;

import edu.ntut.se1091.team1.pms.entity.Role;

import java.util.Optional;

public interface RoleService {

    Optional<Role> query(String name);

    Optional<Role> save(Role role);

    Optional<Role> queryAndSave(String name);
}
