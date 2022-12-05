package com.ao.recipebuddy.controller;

import com.ao.recipebuddy.dto.RecipeDTO;
import com.ao.recipebuddy.exceptions.ResourceNotFoundException;
import com.ao.recipebuddy.service.RecipeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @ApiOperation(value = "List all recipes", nickname = "recipesGet", notes = "", response = RecipeDTO.class, responseContainer = "List", tags = {})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = RecipeDTO.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    @GetMapping(
            value = "",
            produces = {"application/json"}
    )
    public ResponseEntity<List<RecipeDTO>> getAllRecipes() {
        return new ResponseEntity<>(recipeService.findAll(), HttpStatus.OK);
    }

    @ApiOperation(value = "Create recipe", nickname = "recipesPost", notes = "", tags = {})
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    @PostMapping(
            value = "",
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    public ResponseEntity<Void> recipesPost(@ApiParam(value = "") @Valid @RequestBody(required = false) RecipeDTO recipeDTO) {
        recipeService.save(recipeDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation(value = "Get single recipe", nickname = "recipesNameGet", notes = "", response = RecipeDTO.class, tags = {})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    @GetMapping(
            value = "/{name}",
            produces = {"application/json"}
    )
    public ResponseEntity<RecipeDTO> recipesNameGet(@ApiParam(value = "Recipee name", required = true) @PathVariable("name") String name) throws ResourceNotFoundException {
        return new ResponseEntity<>(recipeService.findByName(name), HttpStatus.OK);
    }

    @ApiOperation(value = "Update single recipe", nickname = "recipesNamePut", notes = "", tags = {})
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    @PutMapping(
            value = "/{name}",
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    public ResponseEntity<Void> recipesNamePut(@ApiParam(value = "Recipee name", required = true) @PathVariable("name") String name,
                                               @ApiParam(value = "") @Valid @RequestBody(required = false) RecipeDTO recipeDTO) throws ResourceNotFoundException {
        recipeService.update(name, recipeDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "Remove single recipe", nickname = "recipesNameDelete", notes = "", tags = {})
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    @DeleteMapping(
            value = "/{name}",
            produces = {"application/json"}
    )
    public ResponseEntity<Void> recipesNameDelete(@ApiParam(value = "Recipee name", required = true) @PathVariable("name") String name) throws ResourceNotFoundException {
        recipeService.deleteByName(name);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}