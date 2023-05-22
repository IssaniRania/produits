package com.example.demo.produits.entities;

import javax.persistence.*;

@Entity
public class Role {
        @Id
        @Column(name = "role_id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;
        private String name;
        public Integer getId() {
            return id;
        }

    public String getName() {
        return name;
    }

    public Role (String name) {
            super();
            this.name = name;
        }

}
