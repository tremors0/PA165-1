package cz.fi.muni.pa165.secretagency.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Service implementation for mapping between entities and DTOs taken from example project.
 */
@Service
public class BeanMappingServiceImpl implements BeanMappingService {

    @Autowired
    private ModelMapper modelMapper;

    public  <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass) {
        List<T> mappedCollection = new ArrayList<>();
        for (Object object : objects) {
            mappedCollection.add(modelMapper.map(object, mapToClass));
        }
        return mappedCollection;
    }

    public  <T> T mapTo(Object u, Class<T> mapToClass)
    {
        return modelMapper.map(u,mapToClass);
    }

    public ModelMapper getMapper(){
        return modelMapper;
    }
}
