package pl.coderslab.utils;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class DaoMethods <T>{

    private final Method[] GETTERS;
    private final String[] COLUMNS;
    private final EntityFactory<T> FACTORY;
    private final String CREATE;
    private final String READ;
    private final String UPDATE;
    private final String DELETE;
    private final String COUNT;

    public DaoMethods(Class<T> type, String table) throws NoSuchMethodException {

        String[] gNames = Arrays.stream(type.getDeclaredFields())
                .map(n -> Conversions.fieldToGetter(n.getName()))
                .toArray(String[] :: new);
        GETTERS = new Method[gNames.length];
        for (int i = 0; i < GETTERS.length; i++) {
            GETTERS[i] = type.getMethod(gNames[i]);
        }

        COLUMNS = Arrays.stream(type.getDeclaredFields())
                .map(n -> Conversions.fieldToColumn(n.getName()))
                .toArray(String[] :: new);

        FACTORY = new EntityFactory<>(type);

        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ").append(table).append(" VALUES (NULL");
        for (int i = 0; i < type.getDeclaredFields().length - 1; i++) {
            sb.append(", ?");
        }
        sb.append(")");
        CREATE = sb.toString();

        READ = "SELECT * FROM " + table + " ";

        sb = new StringBuilder();
        sb.append("UPDATE ").append(table).append(" SET ");
        for (int i = 1; i < COLUMNS.length; i++) {
            sb.append(COLUMNS[i]).append(" = ?");
            if(i < COLUMNS.length - 1) sb.append(", ");
        }
        sb.append(" WHERE ").append(COLUMNS[0]).append(" = ?");

        UPDATE = sb.toString();

        DELETE = "DELETE FROM " + table + " WHERE id = ?";

        COUNT = "SELECT COUNT(id) AS 'count' FROM " + table + " ";
    }

    public int create(T entity){

        try(Connection conn = DbUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(CREATE, PreparedStatement.RETURN_GENERATED_KEYS)) {
            for (int i = 1; i < GETTERS.length; i++) {
                stmt.setObject(i, GETTERS[i].invoke(entity));
            }
            stmt.executeUpdate();
            ResultSet set = stmt.getGeneratedKeys();
            if(set.next()) return set.getInt(1);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public Optional<T> readSingle(String whereClause, Object... args){
        String sql = READ + whereClause;
        try(Connection conn = DbUtil.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            for (int i = 0; i < args.length; i++) {
                stmt.setObject(i + 1, args[i]);
            }
            ResultSet set = stmt.executeQuery();
            return Optional.ofNullable(FACTORY.getEntity(set));
        }catch (Exception e){
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public List<T> readList(String whereClause, Object... args){
        String sql = READ + whereClause;
        try(Connection conn = DbUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            for (int i = 0; i < args.length; i++) {
                stmt.setObject(i + 1, args[i]);
            }
            ResultSet set = stmt.executeQuery();
            return FACTORY.getAsList(set);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public int update(T entity){

        try(Connection conn = DbUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(UPDATE)) {
            for (int i = 1; i < GETTERS.length; i++) {
                stmt.setObject(i, GETTERS[i].invoke(entity));
            }
            stmt.setObject(COLUMNS.length, GETTERS[0].invoke(entity));
            return stmt.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public int delete(int id){
        try(Connection conn = DbUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(DELETE)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public int count(String whereClause, Object... args){
        try(Connection conn = DbUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(COUNT + whereClause)) {
            for (int i = 0; i < args.length; i++) {
                stmt.setObject(i + 1, args[i]);
            }
            ResultSet set = stmt.executeQuery();
            if(set.next()) return set.getInt(1);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
}
