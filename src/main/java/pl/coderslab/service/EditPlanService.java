package pl.coderslab.service;

import pl.coderslab.dao.RecipePlanDao;
import pl.coderslab.model.RecipePlan;
import pl.coderslab.utils.DbUtil;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EditPlanService {

    private final RecipePlanDao dao = DbUtil.getRecipePlanDao();

    public void updatePlanDetails(int planId, Map<String, String[]> params){

        dao.updateMultiple(updateEntries(dao.getForPlan(planId), params));
    }

    private List<RecipePlan> updateEntries(List<RecipePlan> list, Map<String, String[]> params){

        Map<Integer, RecipePlan> meals = list.stream().collect(Collectors.toMap(n -> n.getId(), Function.identity()));

        Set<Map.Entry<String, String[]>> set = params.entrySet().stream()
                .filter(n -> n.getKey().matches("^[0-9]+-.+"))
                .collect(Collectors.toSet());

        Map<Integer, List<String[]>> commands = new HashMap<>();
        for (Map.Entry<String, String[]> entry : set){
            String[] key = entry.getKey().split("-");
            int id = Integer.parseInt(key[0]);
            String[] command = {key[1], entry.getValue()[0]};
            if(commands.containsKey(id)) commands.get(id).add(command);
            else {
                ArrayList<String[]> l = new ArrayList<>();
                l.add(command);
                commands.put(id, l);
            }
        }

        List<Integer> forDeletion = new ArrayList<>();
        for (Map.Entry<Integer, List<String[]>> entry : commands.entrySet()){
            RecipePlan meal = meals.get(entry.getKey());
            for (String[] command : entry.getValue()){
                if(command[0].equals("delete")){
                    meals.remove(meal.getId());
                    forDeletion.add(meal.getId());
                    break;
                }
                execute(command, meal);
            }
        }
        dao.deleteMultiple(forDeletion);

        return new ArrayList<>(meals.values());
    }

    private void execute(String[] command, RecipePlan entry){

        switch (command[0]){
            case "displayOrder":
                entry.setDisplayOrder(Integer.parseInt(command[1]));
                break;
            case "mealName":
                entry.setMealName(command[1]);
                break;
            case "recipeId":
                entry.setRecipeId(Integer.parseInt(command[1]));
                break;
        }
    }
}
