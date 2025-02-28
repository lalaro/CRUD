package edu.escuelaing.app.realestate.repository;

import edu.escuelaing.app.realestate.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property, Long> {
}
