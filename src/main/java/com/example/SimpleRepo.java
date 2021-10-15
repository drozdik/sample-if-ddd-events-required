package com.example;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class SimpleRepo<Id extends NumericId, Type extends Entity<Id>> {

    private static final AtomicInteger idCounter = new AtomicInteger();
    private final Class IdClass;
    private final Map<Id, Type> store = new HashMap<>();

    public SimpleRepo(Class idClass) {
        IdClass = idClass;
    }

    public Optional<Type> findById(Id id) {
        return Optional.ofNullable(store.get(id));
    }

    public Type save(Type obj) {
        if (obj.getId() == null) {
            try {
                obj.setId((Id) this.IdClass.getDeclaredConstructor(Integer.class).newInstance(idCounter.incrementAndGet()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        store.put(obj.getId(), obj);
        return obj;
    }

    public Collection<Type> findAll() {
        return store.values();
    }
}
