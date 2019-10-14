package com.example.avaliacao.domain;


import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "EMPLOYEE")
public class Employee implements Serializable {

    @Column(name = "id")
    @Id
    Long id;

    @Column(name = "name")
    String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
