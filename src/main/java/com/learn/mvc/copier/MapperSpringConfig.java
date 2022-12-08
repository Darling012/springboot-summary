package com.learn.mvc.copier;

import org.mapstruct.MapperConfig;
import org.mapstruct.extensions.spring.SpringMapperConfig;

@MapperConfig(componentModel = "spring")
@SpringMapperConfig(conversionServiceAdapterPackage = "com.learn.mvc.copier",
        conversionServiceAdapterClassName = "MapStructConversionServiceAdapter",
                   conversionServiceBeanName = "myConversionService")
public class MapperSpringConfig {
}
