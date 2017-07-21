package de.kasperczyk.rkbudget.category;

import de.kasperczyk.rkbudget.user.User;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.awt.*;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "categorySequenceGenerator")
    @GenericGenerator(
            name = "categorySequenceGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "CATEGORY_SEQUENCE"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
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
