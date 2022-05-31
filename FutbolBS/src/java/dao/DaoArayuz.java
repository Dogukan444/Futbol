package dao;

import java.util.List;

public interface DaoArayuz<Entity> {

    public boolean insert(Entity entity);

    public boolean delete(Entity entity);

    public int count();

    public List<Entity> findAll(String deger, int page, int pageSize);

    public Entity find(int id);

    public boolean update(Entity entity);
}
