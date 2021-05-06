package pl.coderslab.dao;

import pl.coderslab.model.DayName;
import pl.coderslab.utils.DbUtil;
import pl.coderslab.utils.EntityFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DayNameDao {

    private static final String FIND_ALL_BOOKS_QUERY = "SELECT * FROM day_name ORDER BY display_order ASC;";

    private EntityFactory<DayName> factory;

    public DayNameDao() {
        try {
            factory  = new EntityFactory<>(DayName.class);
        }catch (NoSuchMethodException e){
            e.printStackTrace();
        }
    }

    public List<DayName> findAll() {
        List<DayName> dayNames = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_BOOKS_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            dayNames = factory.getAsList(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dayNames;

    }
}
