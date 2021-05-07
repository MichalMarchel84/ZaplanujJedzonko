package pl.coderslab.service;

import pl.coderslab.dao.RecipeDAO;
import pl.coderslab.dao.RecipePlanDao;
import pl.coderslab.model.Plan;
import pl.coderslab.model.Recipe;
import pl.coderslab.model.RecipePlan;
import pl.coderslab.utils.DbUtil;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AddPlanService {

    private final RecipeDAO recipeDAO = DbUtil.getRecipeDAO();
    private final RecipePlanDao recipePlanDao = DbUtil.getRecipePlanDao();

    public void createSchedule(Map<String, String[]> params, Plan plan) {
        Integer[] days = params.keySet().stream()
                .filter(n -> n.matches("^d[0-9]+"))
                .map(n -> Integer.parseInt(n.replace("d", "")))
                .toArray(Integer[]::new);

        Map<Integer, String> meals = params.entrySet().stream()
                .filter(n -> n.getKey().matches("^mn[0-9]+"))
                .collect(Collectors.toMap(n -> Integer.parseInt(n.getKey().replace("mn", "")), n -> n.getValue()[0]));

        Integer[] mealNo = params.keySet().stream()
                .filter(n -> n.matches("^m[0-9]+"))
                .map(n -> Integer.parseInt(n.replace("m", "")))
                .toArray(Integer[]::new);

        List<Recipe> recipes = recipeDAO.findAllByAdmin(plan.getAdminId());
        for (int dayId : days) {
            for (int no : mealNo) {
                RecipePlan entry = new RecipePlan();
                entry.setDayNameId(dayId);
                entry.setDisplayOrder(no);
                entry.setMealName(meals.get(no));
                entry.setPlanId(plan.getId());
                entry.setRecipeId(recipes.get(0).getId());
                recipePlanDao.create(entry);
            }
        }
    }
}
