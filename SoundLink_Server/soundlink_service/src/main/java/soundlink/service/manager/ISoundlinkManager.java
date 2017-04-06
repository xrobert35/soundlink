package soundlink.service.manager;

import java.util.List;

public interface ISoundlinkManager<E, K> {

    E create(E entity);

    E update(E entity);

    void delete(E entity);

    E findOne(K key);

    E getOne(K key);

    List<E> getAll();
}
