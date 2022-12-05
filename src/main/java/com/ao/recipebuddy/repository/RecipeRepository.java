package com.ao.recipebuddy.repository;

import com.ao.recipebuddy.model.RecipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecipeRepository extends JpaRepository<RecipeEntity, Long> {

    Optional<RecipeEntity> findByName(String name);

    Optional<RecipeEntity> deleteByName(String name);

}
