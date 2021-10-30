package com.personalproject.ecommerce.config;

import com.personalproject.ecommerce.entity.Country;
import com.personalproject.ecommerce.entity.Product;
import com.personalproject.ecommerce.entity.ProductCategory;
import com.personalproject.ecommerce.entity.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class DataRestConfig implements RepositoryRestConfigurer {
    
    private EntityManager theEntitymanager;

    @Autowired
    public DataRestConfig(EntityManager theEntitymanager) {
        this.theEntitymanager = theEntitymanager;
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        HttpMethod[]  theUnsurportedMethod = {HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE};

//        disable product Method: PUT< POST< DELETE
        disableHttpMethods(Product.class, config, theUnsurportedMethod);
//         disable productCategory Method: PUT, POST,DELETE
        disableHttpMethods( ProductCategory.class, config, theUnsurportedMethod);
        disableHttpMethods( Country.class, config, theUnsurportedMethod);
        disableHttpMethods( State.class, config, theUnsurportedMethod);

//        call internal helper method

        exposedIds(config);

    }

    private void disableHttpMethods( Class theClass, RepositoryRestConfiguration config, HttpMethod[] theUnsurportedMethod) {
        config.getExposureConfiguration()
                .forDomainType(theClass)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(theUnsurportedMethod));
    }

    private void exposedIds(RepositoryRestConfiguration config) {
//        exposed entity ids

//        get a list of entity class from entity Manager

        Set<EntityType<?>> entityTypeSet = theEntitymanager.getMetamodel().getEntities();

//        create an Array of an entity class
        List<Class>  entityClasses = new ArrayList<>();

//        - get the entity type from the entities

        for (EntityType tempEntityType : entityTypeSet){
            entityClasses.add(tempEntityType.getJavaType());

        }
//        exposed the entity ids for the array of entity/domain type
        Class[] domainTypes = entityClasses.toArray(new Class[0]);
        config.exposeIdsFor(domainTypes);
    }
}
