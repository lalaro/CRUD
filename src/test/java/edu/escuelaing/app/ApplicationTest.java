package edu.escuelaing.app;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import edu.escuelaing.app.realestate.model.Property;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import edu.escuelaing.app.realestate.repository.PropertyRepository;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = DemoApplication.class)
public class ApplicationTest {
/*
    @Autowired
    private PropertyRepository propertyRepository;

    @Test
    void testPropertyRepository() {
        Property property = new Property();
        property.setAddress("456 Oak Rd");
        property.setPrice(180000.0);
        property.setSize(100.0);
        property.setDescription("Cozy apartment");

        Property savedProperty = propertyRepository.save(property);

        assertThat(savedProperty).isNotNull();
        assertThat(savedProperty.getId()).isNotNull();
    }

    @Test
    void testFindPropertyById() {
        Property property = new Property();
        property.setAddress("789 Pine St");
        property.setPrice(350000.0);
        property.setSize(150.0);
        property.setDescription("Spacious villa");

        Property savedProperty = propertyRepository.save(property);

        Property foundProperty = propertyRepository.findById(savedProperty.getId()).orElse(null);

        assertThat(foundProperty).isNotNull();
        assertThat(foundProperty.getId()).isEqualTo(savedProperty.getId());
        assertThat(foundProperty.getAddress()).isEqualTo(savedProperty.getAddress());
    }*/
}
