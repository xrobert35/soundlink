package soundlink.server.converter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;

/**
 * Abstract class used to convert object
 *
 * @author xrobert
 *
 * @param <E> entity object
 * @param <D> dto object
 */
public abstract class AbstractDtoConverter<E, D> {

    public Set<D> convertToDtoSet(Set<E> entites, Class<D> clazz) {
        Set<D> dtos = new HashSet<D>();
        for (E entity : entites) {
            dtos.add(convertToDto(entity, clazz));
        }
        return dtos;
    }

    public D convertToDto(E entity, Class<D> clazz) {
        D dto = BeanUtils.instantiate(clazz);
        List<String> excludeList = getExcludeProperties();
        addCollectionsExclusion(excludeList, clazz);
        BeanUtils.copyProperties(entity, dto, excludeList.toArray(new String[excludeList.size()]));
        subConvertToDto(entity, dto);
        return dto;
    }

    public Set<E> convertToEntitySet(Set<D> dtos, Class<E> clazz) {
        Set<E> entites = new HashSet<E>();
        for (D dto : dtos) {
            entites.add(convertToEntity(dto, clazz));
        }
        return entites;
    }

    public E convertToEntity(D dto, Class<E> clazz) {
        E entity = BeanUtils.instantiate(clazz);
        List<String> excludeList = getExcludeProperties();
        addCollectionsExclusion(excludeList, clazz);
        BeanUtils.copyProperties(dto, entity, excludeList.toArray(new String[excludeList.size()]));
        subConvertToEntity(dto, entity);
        return entity;
    }

    protected List<String> getExcludeProperties() {
        return new ArrayList<String>();
    }

    /**
     * This method is used to automaticaly ignore Collection from BeanUtils
     * convertion
     */
    private void addCollectionsExclusion(List<String> excludeList, Class<?> prodClass) {
        Field[] declaredFields = prodClass.getDeclaredFields();
        for (Field field : declaredFields) {
            if (Collection.class.isAssignableFrom(field.getType())) {
                excludeList.add(field.getName());
            }
        }
    }

    protected abstract void subConvertToDto(E entity, D dto);

    protected abstract void subConvertToEntity(D dto, E entity);
}
