package edu.ntut.se1091.team1.pms.entity;

import javax.persistence.*;

@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @Column(unique = true)
    private String name;

    public Role() {
    }

    public Role(String name) {
        super();
        this.name = name;
    }

    public String getName() {
        return name;
    }
}