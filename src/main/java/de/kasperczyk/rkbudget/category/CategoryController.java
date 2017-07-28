package de.kasperczyk.rkbudget.category;

import de.kasperczyk.rkbudget.user.UserController;
import org.ocpsoft.rewrite.annotation.Join;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@Scope("session")
@Join(path = "/categorization", to = "/pages/categorization.xhtml")
public class CategoryController {

    private final MessageSource messageSource;
    private final CategoryService categoryService;
    private final UserController userController;

    private String name;
    private PredefinedColor predefinedColor;

    private List<Category> categories;

    @Autowired
    public CategoryController(CategoryService categoryService,
                              UserController userController,
                              MessageSource messageSource) {
        this.categoryService = categoryService;
        this.userController = userController;
        this.messageSource = messageSource;
        this.categories = new ArrayList<>();
    }

    public List<PredefinedColor> getAllPredefinedColors() {
        return Arrays.asList(PredefinedColor.values());
    }

    public String getPredefinedColorName(PredefinedColor color) {
        return messageSource.getMessage(color.getMessageKey(), null, userController.getLocale());
    }

    public String getColorName(Color color) {
        return messageSource.getMessage(findPredefinedColorBy(color).getMessageKey(), null,
                userController.getLocale());
    }

    public void saveCategory() {
        Category category = new Category(name, predefinedColor.getColor(), userController.getCurrentUser());
        categoryService.saveCategory(category);
        categories.add(category);
        resetFields();
    }

    private void resetFields() {
        name = null;
        predefinedColor = null;
    }

    public String getColorKey(Color color) {
        return findPredefinedColorBy(color).getKey();
    }

    private PredefinedColor findPredefinedColorBy(Color color) {
        return Arrays.stream(PredefinedColor.values())
                .filter(pc -> pc.getColor().equals(color))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Color not found"));
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PredefinedColor getPredefinedColor() {
        return predefinedColor;
    }

    public void setPredefinedColor(PredefinedColor predefinedColor) {
        this.predefinedColor = predefinedColor;
    }

    public List<Category> getCategories() {
        return categories;
    }
}
