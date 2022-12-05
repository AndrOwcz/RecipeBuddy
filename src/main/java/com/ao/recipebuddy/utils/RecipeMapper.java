package com.ao.recipebuddy.utils;

import com.ao.recipebuddy.dto.RecipeDTO;
import com.ao.recipebuddy.model.RecipeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RecipeMapper {

    @Mapping(source = "name", target = "name")
    @Mapping(source = "instructions", target = "instructions")
    @Mapping(source = "author", target = "author")
    @Mapping(ignore = true, target = "id")
    RecipeEntity mapDtoToEntity(RecipeDTO recipeDTO);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "instructions", target = "instructions")
    @Mapping(source = "author", target = "author")
    RecipeDTO mapEntityToDto(RecipeEntity recipeEntity);

    List<RecipeEntity> mapDtoToEntityLists(List<RecipeDTO> recipeDTOList);

    List<RecipeDTO> mapEntityToDtoLists(List<RecipeEntity> recipeEntityList);

}
