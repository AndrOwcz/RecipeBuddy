package com.ao.recipebuddy.service;

import com.ao.recipebuddy.dto.RecipeDTO;
import com.ao.recipebuddy.exceptions.ResourceNotFoundException;
import com.ao.recipebuddy.model.RecipeEntity;
import com.ao.recipebuddy.repository.RecipeRepository;
import com.ao.recipebuddy.utils.RecipeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeMapper recipeMapper;
    private static final String RECIPE_WAS_NOT_FOUND_MESSAGE = "Recipe was not found";

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeMapper recipeMapper) {
        this.recipeRepository = recipeRepository;
        this.recipeMapper = recipeMapper;
    }

    public List<RecipeDTO> findAll() {
        return recipeMapper.mapEntityToDtoLists(recipeRepository.findAll());
    }

    public RecipeDTO findByName(String name) throws ResourceNotFoundException {
        RecipeEntity recipeEntity = recipeRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException(RECIPE_WAS_NOT_FOUND_MESSAGE));
        return recipeMapper.mapEntityToDto(recipeEntity);
    }

    public void save(RecipeDTO recipeDTO) {
        recipeRepository.save(recipeMapper.mapDtoToEntity(recipeDTO));
    }

    public void deleteByName(String name) throws ResourceNotFoundException {
        RecipeEntity recipeEntity = recipeRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException(RECIPE_WAS_NOT_FOUND_MESSAGE));
        recipeRepository.deleteByName(recipeEntity.getName());
    }

    public void update(String name, RecipeDTO recipeDTO) throws ResourceNotFoundException {
        RecipeEntity recipeEntity = recipeRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException(RECIPE_WAS_NOT_FOUND_MESSAGE));
        recipeEntity.setName(name);
        recipeEntity.setInstructions(recipeDTO.getInstructions());
        recipeEntity.setAuthor(recipeDTO.getAuthor());
        recipeRepository.save(recipeEntity);
    }
}
