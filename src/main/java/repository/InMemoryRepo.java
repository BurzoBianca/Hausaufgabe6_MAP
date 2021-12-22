package repository;

import java.util.ArrayList;
import java.util.List;


public abstract class InMemoryRepo <T> implements ICrudRepo<T> {
    protected List<T> repoList;

    public InMemoryRepo() {
        this.repoList = new ArrayList<>();

    }


    @Override
    public T create(T obj) {
        this.repoList.add(obj);
        return obj;
    }


    @Override
    public List<T> getAll() {
        return this.repoList;
    }


    @Override
    public void delete(T obj) {
        this.repoList.remove(obj);
    }

}