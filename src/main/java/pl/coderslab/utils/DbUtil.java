package pl.coderslab.utils;

import pl.coderslab.dao.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DbUtil {
    private static DataSource dataSource;
    private static AdminDao adminDao = null;
    private static PlanDao planDao = null;
    private static RecipeDAO recipeDAO = null;
    private static DayNameDao dayNameDao = null;
    private static RecipePlanDao recipePlanDao = null;

    public static Connection getConnection() throws SQLException {
        return getInstance().getConnection();
    }

    private static DataSource getInstance() {
        if (dataSource == null) {
            try {
                Context context = new InitialContext();
                dataSource = (DataSource) context.lookup("java:comp/env/jdbc/scrumlab");
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }
        return dataSource;
    }

    public static AdminDao getAdminDao() {
        if(adminDao == null) {
            try {
                adminDao = new AdminDao();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return adminDao;
    }

    public static PlanDao getPlanDao() {
        if(planDao == null) {
            try {
                planDao = new PlanDao();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return planDao;
    }

    public static RecipeDAO getRecipeDAO() {
        if(recipeDAO == null) {
            try {
                recipeDAO = new RecipeDAO();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return recipeDAO;
    }

    public static DayNameDao getDayNameDao() {
        if(dayNameDao == null) {
            dayNameDao = new DayNameDao();
        }
        return dayNameDao;
    }

    public static RecipePlanDao getRecipePlanDao() {
        if(recipePlanDao == null) {
            try {
                recipePlanDao = new RecipePlanDao();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return recipePlanDao;
    }

    public static void setDataSource(DataSource source){
        dataSource = source;
    }
}