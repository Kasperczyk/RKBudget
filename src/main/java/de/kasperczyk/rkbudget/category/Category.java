package de.kasperczyk.rkbudget.category;

import javax.persistence.*;

@Entity
class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    @Version
    private Long version;


}
