package edu.escuelaing.app.realestate.service;

import edu.escuelaing.app.realestate.model.Property;
import edu.escuelaing.app.realestate.repository.PropertyRepository;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private static final Logger logger = LoggerFactory.getLogger(PropertyService.class);
    public PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    public Optional<Property> getPropertyById(Long id) {
        return propertyRepository.findById(id);
    }

    public Property createProperty(Property property) {
        return propertyRepository.save(property);
    }

    public Property updateProperty(Long id, Property updatedProperty) {
        logger.info("Updating property with id: {}", id);
        Property existingProperty = propertyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Property not found with id: " + id));

        existingProperty.setAddress(updatedProperty.getAddress());
        existingProperty.setPrice(updatedProperty.getPrice());
        existingProperty.setSize(updatedProperty.getSize());
        existingProperty.setDescription(updatedProperty.getDescription());

        return propertyRepository.save(existingProperty);
    }

    public void deleteProperty(Long id) {
        propertyRepository.deleteById(id);
    }
}
