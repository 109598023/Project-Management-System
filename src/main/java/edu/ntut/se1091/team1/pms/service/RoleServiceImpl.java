package edu.ntut.se1091.team1.pms.service;

import edu.ntut.se1091.team1.pms.entity.Role;
import edu.ntut.se1091.team1.pms.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl  implements RoleService{

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Optional<Role> query(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public Optional<Role> save(Role role) {
        Optional<Role> roleOptional = roleRepository.findByName(role.getName());
        if (roleOptional.isPresent()) {
            return roleOptional;
        }
        return Optional.of(roleRepository.save(role));
    }

    @Override
    public Optional<Role> queryAndSave(String name) {
        Optional<Role> roleOptional = roleRepository.findByName(name);
        if (roleOptional.isEmpty()) {
            roleOptional = Optional.of(roleRepository.save(new Role(name)));
        }
        return roleOptional;
    }

    @Override
    public Optional<Role> update(Role role) {
        Optional<Role> roleOptional = roleRepository.findByName(role.getName());
        if (roleOptional.isPresent()) {
            return Optional.of(roleRepository.save(role));
        }
        return Optional.empty();
    }
}
