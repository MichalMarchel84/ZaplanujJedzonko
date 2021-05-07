package pl.coderslab.dao;

import pl.coderslab.model.DayName;
import pl.coderslab.utils.DaoMethods;

import java.util.ArrayList;
import java.util.List;

public class DayNameDao {

    private static List<DayName> list;

    static {
        try {
            list = (new DaoMethods<>(DayName.class, "day_name")).readList("ORDER BY display_order");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            list = new ArrayList<>();
        }
    }

    public List<DayName> findAll() {
        return list;
    }
}
