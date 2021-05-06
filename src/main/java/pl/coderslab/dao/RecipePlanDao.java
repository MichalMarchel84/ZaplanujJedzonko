package pl.coderslab.dao;

import pl.coderslab.model.RecipePlan;
import pl.coderslab.utils.DbUtil;
import pl.coderslab.utils.EntityFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecipePlanDao {

    private EntityFactory<RecipePlan> factory;

    public RecipePlanDao() {
        try {
            factory = new EntityFactory<>(RecipePlan.class);
        }catch (NoSuchMethodException e){
            e.printStackTrace();
        }
    }

    public List<RecipePlan> getForPlan(int id){
        List<RecipePlan> list = new ArrayList<>();
        String sql = "SELECT * FROM recipe_plan WHERE plan_id = ?";
        try(Connection conn = DbUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, id);
            ResultSet set = stmt.executeQuery();
            list = factory.getAsList(set);
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public int create(RecipePlan entry){

        String sql = "INSERT INTO recipe_plan(recipe_id, meal_name, display_order, day_name_id, plan_id) VALUES (?,?,?, ?, ?)";

        try (Connection connection = DbUtil.getConnection();
             PreparedStatement insertStm = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            insertStm.setInt(1, entry.getRecipeId());
            insertStm.setString(2, entry.getMealName());
            insertStm.setInt(3, entry.getDisplayOrder());
            insertStm.setInt(4, entry.getDayNameId());
            insertStm.setInt(5, entry.getPlanId());

            return insertStm.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int update(RecipePlan entry){
        String sql = "UPDATE recipe_plan SET recipe_id = ?, meal_name = ?, display_order = ?, day_name_id = ?, plan_id = ? WHERE id = ?";

        try (Connection connection = DbUtil.getConnection();
             PreparedStatement insertStm = connection.prepareStatement(sql)) {

            insertStm.setInt(1, entry.getRecipeId());
            insertStm.setString(2, entry.getMealName());
            insertStm.setInt(3, entry.getDisplayOrder());
            insertStm.setInt(4, entry.getDayNameId());
            insertStm.setInt(5, entry.getPlanId());
            insertStm.setInt(6, entry.getId());

            return insertStm.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int delete(int id){
        String sql = "DELETE FROM recipe_plan WHERE id = ?";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setInt(1, id);
            return stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
