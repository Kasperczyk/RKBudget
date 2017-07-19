package de.kasperczyk.rkbudget.category;

import de.kasperczyk.rkbudget.user.User;

import javax.persistence.*;
import java.awt.*;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    @Version
    private Long version;

    @Column
    private String name;

    @Column
    @Convert(converter = ColorAttributeConverter.class)
    private Color color;

    @ManyToOne
    private User user;

    Category(String name, Color color, User user) {
        this.name = name;
        this.color = color;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
