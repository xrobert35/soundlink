package soundlink.service.converter;

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

    private Class<D> clazzDto;

    private Class<E> clazzEntity;

    public Set<D> convertToDtoSet(Set<E> entites) {
        Set<D> dtos = new HashSet<D>();
        for (E entity : entites) {
            dtos.add(convertToDto(entity));
        }
        return dtos;
    }

    public List<D> convertToDtoList(List<E> entites) {
        List<D> dtos = new ArrayList<D>();
        for (E entity : entites) {
            dtos.add(convertToDto(entity));
        }
        return dtos;
    }

    public D convertToDto(E entity) {
        D dto = BeanUtils.instantiate(clazzDto);
        List<String> excludeList = getExcludeProperties();
        addCollectionsExclusion(excludeList, clazzDto);
        BeanUtils.copyProperties(entity, dto, excludeList.toArray(new String[excludeList.size()]));
        subConvertToDto(entity, dto);
        return dto;
    }

    public Set<E> convertToEntitySet(Set<D> dtos) {
        Set<E> entites = new HashSet<E>();
        for (D dto : dtos) {
            entites.add(convertToEntity(dto));
        }
        return entites;
    }

    public E convertToEntity(D dto) {
        E entity = BeanUtils.instantiate(clazzEntity);
        List<String> excludeList = getExcludeProperties();
        addCollectionsExclusion(excludeList, clazzEntity);
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

    public void setDtoClass(Class<D> dtoClass) {
        this.clazzDto = dtoClass;
    }

    public void setEntityClass(Class<E> entityClass) {
        this.clazzEntity = entityClass;
    }

    protected abstract void subConvertToDto(E entity, D dto);

    protected abstract void subConvertToEntity(D dto, E entity);
}
