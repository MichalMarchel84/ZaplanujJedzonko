package pl.coderslab.dao;

import pl.coderslab.model.RecipePlan;
import pl.coderslab.utils.DaoMethods;
import pl.coderslab.utils.DbUtil;
import pl.coderslab.utils.EntityFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecipePlanDao {

    private final DaoMethods<RecipePlan> METHODS;

    public RecipePlanDao() throws NoSuchMethodException {
       METHODS = new DaoMethods<>(RecipePlan.class, "recipe_plan");
    }

    public List<RecipePlan> getForPlan(int id){

        return METHODS.readList("WHERE plan_id = ?", id);
    }

    public List<RecipePlan> getForRecipe(int id){

        return METHODS.readList("WHERE recipe_id = ?", id);
    }

    public int create(RecipePlan entry){

        return METHODS.create(entry);
    }

    public void createMultiple(List<RecipePlan> list){

        METHODS.createMultiple(list);
    }

    public int update(RecipePlan entry){

        return METHODS.update(entry);
    }

    public void updateMultiple(List<RecipePlan> list){

        METHODS.updateMultiple(list);
    }

    public int delete(int id){

        return METHODS.delete(id);
    }

    public void deleteMultiple(List<Integer> list){

        METHODS.deleteMultiple(list);
    }
}
