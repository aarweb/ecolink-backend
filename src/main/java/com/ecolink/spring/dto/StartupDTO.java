package com.ecolink.spring.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StartupDTO {
    Long id;
    String name;
    String imageUrl;
    Long level;
}