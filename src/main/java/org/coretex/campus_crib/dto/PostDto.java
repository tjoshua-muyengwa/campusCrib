package org.coretex.campus_crib.dto;

import lombok.Data;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link org.coretex.campus_crib.entities.Post}
 */
@Value
@Data
public class PostDto implements Serializable {
    Double price;
    String location;
    String caption;

    List<String> pictureUrl;
}