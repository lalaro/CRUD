package edu.escuelaing.app;

import edu.escuelaing.app.realestate.model.Property;
import edu.escuelaing.app.realestate.service.PropertyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Optional;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import edu.escuelaing.app.realestate.controller.PropertyController;
import java.util.List;

public class CrudTest {

    private MockMvc mockMvc;

    @Mock
    private PropertyService propertyService;

    @InjectMocks
    private PropertyController propertyController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(propertyController).build();
    }

    @Test
    void testUpdateProperty_ReturnsUpdatedProperty() throws Exception {
        Property updatedProperty = new Property();
        updatedProperty.setId(1L);
        updatedProperty.setAddress("Nueva dirección");
        updatedProperty.setPrice(150000);
        updatedProperty.setSize(120);
        updatedProperty.setDescription("Propiedad actualizada");

        when(propertyService.updateProperty(eq(1L), any(Property.class))).thenReturn(updatedProperty);

        mockMvc.perform(put("/api/properties/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedProperty)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.address").value("Nueva dirección"))
                .andExpect(jsonPath("$.price").value(150000));

        verify(propertyService).updateProperty(eq(1L), any(Property.class));
    }

    @Test
    void testGetAllProperties() throws Exception {
        Property property1 = new Property();
        property1.setId(1L);
        property1.setAddress("123 Calle");
        property1.setPrice(200000);
        property1.setSize(120);
        property1.setDescription("Bonita casa");

        Property property2 = new Property();
        property2.setId(2L);
        property2.setAddress("456 Avenida");
        property2.setPrice(300000);
        property2.setSize(150);
        property2.setDescription("Casa moderna");

        when(propertyService.getAllProperties()).thenReturn(Arrays.asList(property1, property2));

        mockMvc.perform(get("/api/properties"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].address").value("123 Calle"))
                .andExpect(jsonPath("$[1].address").value("456 Avenida"));

        verify(propertyService, times(1)).getAllProperties();
    }


    @Test
    void testGetPropertyById_WhenFound() throws Exception {
        Property property = new Property();
        property.setId(1L);
        property.setAddress("123 Calle");
        property.setPrice(200000);
        property.setSize(120);
        property.setDescription("Bonita casa");

        when(propertyService.getPropertyById(1L)).thenReturn(Optional.of(property));

        mockMvc.perform(get("/api/properties/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.address").value("123 Calle"));

        verify(propertyService, times(1)).getPropertyById(1L);
    }

    @Test
    void testGetPropertyById_WhenNotExists() throws Exception {
        when(propertyService.getPropertyById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/properties/1"))
                .andExpect(status().isNotFound());

        verify(propertyService, times(1)).getPropertyById(1L);
    }

    @Test
    void testCreateProperty() throws Exception {
        Property property = new Property();
        property.setId(1L);
        property.setAddress("123 Calle");
        property.setPrice(200000);
        property.setSize(120);
        property.setDescription("Bonita casa");

        when(propertyService.createProperty(any(Property.class))).thenReturn(property);

        mockMvc.perform(post("/api/properties")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"address\":\"123 Calle\",\"price\":200000,\"size\":120,\"description\":\"Bonita casa\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.address").value("123 Calle"));

        verify(propertyService, times(1)).createProperty(any(Property.class));
    }

    @Test
    void testUpdateProperty() throws Exception {
        Property updatedProperty = new Property();
        updatedProperty.setId(1L);
        updatedProperty.setAddress("Nueva dirección");
        updatedProperty.setPrice(250000);
        updatedProperty.setSize(130);
        updatedProperty.setDescription("Casa renovada");

        when(propertyService.updateProperty(eq(1L), any(Property.class))).thenReturn(updatedProperty);

        mockMvc.perform(put("/api/properties/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"address\":\"Nueva dirección\",\"price\":250000,\"size\":130,\"description\":\"Casa renovada\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.address").value("Nueva dirección"));

        verify(propertyService, times(1)).updateProperty(eq(1L), any(Property.class));
    }

    @Test
    void testUpdateProperty_WhenNotExists() throws Exception {
        when(propertyService.updateProperty(eq(1L), any(Property.class))).thenThrow(new RuntimeException("Property not found"));

        mockMvc.perform(put("/api/properties/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"address\":\"Nueva dirección\",\"price\":250000,\"size\":130,\"description\":\"Casa renovada\"}"))
                .andExpect(status().isNotFound());

        verify(propertyService, times(1)).updateProperty(eq(1L), any(Property.class));
    }

    @Test
    void testDeleteProperty() throws Exception {
        doNothing().when(propertyService).deleteProperty(1L);

        mockMvc.perform(delete("/api/properties/1"))
                .andExpect(status().isNoContent());

        verify(propertyService, times(1)).deleteProperty(1L);
    }
}