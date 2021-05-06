package pl.coderslab.dao;

import pl.coderslab.model.Plan;
import pl.coderslab.utils.DbUtil;
import pl.coderslab.utils.EntityFactory;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
public class PlanDao {

    private EntityFactory<Plan> factory;

    public PlanDao() {
        try {
            factory = new EntityFactory<>(Plan.class);
        }catch (NoSuchMethodException e){
            e.printStackTrace();
        }
    }

    public int createPlan(Plan plan){
        String sql = "INSERT INTO plan VALUES (NULL, ?, ?, ?, ?)";

        try(Connection conn = DbUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            stmt.setString(1, plan.getName());
            stmt.setString(2, plan.getDescription());
            if(plan.getCreated() != null) stmt.setObject(3, plan.getCreated());
            else stmt.setObject(3, LocalDateTime.now());
            stmt.setInt(4, plan.getAdminId());
            stmt.executeUpdate();
            ResultSet set = stmt.getGeneratedKeys();
            if(set.next()) return set.getInt(1);
            return 0;
        }catch (SQLException e){
            e.printStackTrace();
            return 0;
        }
    }
    public int updatePlan(Plan plan){
        String sql = "UPDATE plan SET name = ?, description = ? WHERE id = ?";
        try(Connection conn = DbUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, plan.getName());
            stmt.setString(2, plan.getDescription());
            stmt.setInt(3, plan.getId());
            return stmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
            return 0;
        }
    }
    public Plan getById(int id){
        Plan plan = null;
        String sql = "SELECT * FROM plan WHERE id = ?";
        try(Connection conn = DbUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, id);
            ResultSet set = stmt.executeQuery();
            plan = factory.getEntity(set);
        }catch (Exception e){
            e.printStackTrace();
        }
        return plan;
    }
    public List<Plan> getAll(){
        List<Plan> list = new ArrayList<>();
        String sql = "SELECT * FROM plan";
        try(Connection conn = DbUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            ResultSet set = stmt.executeQuery();
            list = factory.getAsList(set);
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
    public List<Plan> getByAdminId(int adminId){
        List<Plan> list = new ArrayList<>();
        String sql = "SELECT * FROM plan WHERE admin_id = ?";
        try(Connection conn = DbUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, adminId);
            ResultSet set = stmt.executeQuery();
            list = factory.getAsList(set);
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }


    public int deletePlan(int planId){
        String sql = "DELETE FROM plan WHERE id = ?";
        try(Connection conn = DbUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, planId);
            return stmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
            return 0;
        }
    }

    public Plan getLastPlan(int id){
        Plan plan = null;
        String sql = "select * from plan where admin_id = ? order by created desc limit 1;";
        try(Connection conn = DbUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, id);
            ResultSet set = stmt.executeQuery();
            plan = factory.getEntity(set);
        }catch (Exception e){
            e.printStackTrace();
        }
        return plan;
    }
    public int numberOfPlansByAdminId (int id) {
        String sql = "select count(*) as count from plan where admin_id = ?";
        int number = 0;
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE)) {
            stmt.setInt(1,id);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.first()) {
                number = resultSet.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return number;
    }
}