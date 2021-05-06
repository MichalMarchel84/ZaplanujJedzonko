package pl.coderslab.dao;

import pl.coderslab.model.Plan;
import pl.coderslab.utils.DaoMethods;
import pl.coderslab.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class PlanDao {

    private DaoMethods<Plan> METHODS;

    public PlanDao() throws NoSuchMethodException {

        METHODS = new DaoMethods<>(Plan.class, "plan");
    }

    public int createPlan(Plan plan) {

        plan.setCreated(LocalDateTime.now());
        return METHODS.create(plan);
    }

    public int updatePlan(Plan plan) {

        return METHODS.update(plan);
    }

    public Optional<Plan> getById(int id) {

        return METHODS.readSingle("WHERE id = ?", id);
    }

    public List<Plan> getAll() {

        return METHODS.readList("");
    }

    public List<Plan> getByAdminId(int adminId) {

        return METHODS.readList("WHERE admin_id = ?", adminId);
    }


    public int deletePlan(int planId) {

        return METHODS.delete(planId);
    }

    public Optional<Plan> getLastPlan(int id) {

        return METHODS.readSingle("WHERE admin_id = ? ORDER BY created DESC LIMIT 1", id);
    }

    public int numberOfPlansByAdminId(int id) {
        String sql = "select count(*) as count from plan where admin_id = ?";
        int number = 0;
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                number = resultSet.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return number;
    }
}