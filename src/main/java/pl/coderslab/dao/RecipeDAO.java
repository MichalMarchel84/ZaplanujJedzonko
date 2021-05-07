package pl.coderslab.dao;

import pl.coderslab.model.Recipe;
import pl.coderslab.utils.DaoMethods;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public class RecipeDAO {

    private static final String NUMBER_OF_RECIPES_ADDED_BY_ADMIN = "select count(*) as count from recipe where admin_id=?;";

    private final DaoMethods<Recipe> METHODS;

    public RecipeDAO() throws NoSuchMethodException {

        METHODS = new DaoMethods<>(Recipe.class, "recipe");
    }

    public Optional<Recipe> read(Integer recipeId) {

        return METHODS.readSingle("WHERE id = ?", recipeId);
    }

    public List<Recipe> findAll() {

        return METHODS.readList("");
    }

    public List<Recipe> findAllByAdmin(int adminId) {

        return METHODS.readList("WHERE admin_id = ?", adminId);
    }

    public Recipe create(Recipe recipe) {

        recipe.setCreated(new Timestamp(System.currentTimeMillis()));
        recipe.setUpdated(new Timestamp(System.currentTimeMillis()));
        recipe.setId(METHODS.create(recipe));
        return recipe;
    }

    public void delete(Integer recipeId) {

        METHODS.delete(recipeId);
    }

    public void update(Recipe recipe) {

        recipe.setUpdated(new Timestamp(System.currentTimeMillis()));
        METHODS.update(recipe);
    }

    public int numberOfRecipesByAdminId(int id) {

        return METHODS.count("WHERE admin_id = ?", id);
    }

    public int numberOfRecipes(){

        return METHODS.count("");
    }

    public List<Recipe> getWithLimit(int limit, int offset){

        return METHODS.readList("ORDER BY updated desc LIMIT ? OFFSET ?", limit, offset);
    }
}