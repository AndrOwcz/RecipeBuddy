package com.ao.recipebuddy.service;

import com.ao.recipebuddy.dto.RecipeDTO;
import com.ao.recipebuddy.exceptions.ResourceNotFoundException;

import java.util.List;

public interface RecipeService {

    public List<RecipeDTO> findAll();

    public RecipeDTO findByName(String name) throws ResourceNotFoundException;

    public void save(RecipeDTO recipeDTO);

    public void deleteByName(String name) throws ResourceNotFoundException;

    public void update(String name, RecipeDTO recipeDTO) throws ResourceNotFoundException;
}
