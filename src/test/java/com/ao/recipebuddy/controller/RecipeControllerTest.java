package com.ao.recipebuddy.controller;

import com.ao.recipebuddy.dto.RecipeDTO;
import com.ao.recipebuddy.exceptions.ResourceNotFoundException;
import com.ao.recipebuddy.service.RecipeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = RecipeController.class)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class RecipeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecipeService recipeService;

    private RecipeDTO RECIPE_1;
    private RecipeDTO RECIPE_2;
    private RecipeDTO RECIPE_3;
    private RecipeDTO RECIPE_4;
    private RecipeDTO UPDATED_RECIPE_2;

    @Before
    public void setUp() {
        RECIPE_1 = new RecipeDTO("Pancake", "Prepare a lot and eat", "A@gmail.com");
        RECIPE_2 = new RecipeDTO("Burger", "Beef, bun, salad, tomato and sauce", "B@gmail.com");
        RECIPE_3 = new RecipeDTO("Dumpling", "If you've already eaten N dumplings, you can eat N+1 for sure", "A@gmail.com");
        RECIPE_4 = new RecipeDTO("Sandwich", "Bread + toppings", "C@gmail.com");
        UPDATED_RECIPE_2 = new RecipeDTO("Burger", "Edited instructions", "C@gmail.com");
    }

    @Test
    public void givenRecipes_whenGetRecipes_thenReturnJsonArray() throws Exception {

        List<RecipeDTO> recipeDTOList = new ArrayList<>(Arrays.asList(RECIPE_1, RECIPE_2, RECIPE_3));

        when(recipeService.findAll()).thenReturn(recipeDTOList);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/recipes")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name", equalTo(RECIPE_1.getName())));
    }

    @Test
    public void givenRecipes_whenGetRecipeByName_thenReturnJsonArray() throws Exception {

        when(recipeService.findByName(RECIPE_2.getName())).thenReturn(RECIPE_2);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/recipes/" + RECIPE_2.getName())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(RECIPE_2.getName())));
    }

    @Test
    public void givenRecipes_whenGetRecipeByName_thenReturnNotFound() throws Exception {

        when(recipeService.findByName(RECIPE_4.getName())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/recipes/" + RECIPE_4.getName())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException));
    }

    @Test
    public void givenRecipe_whenSaveRecipe_thenReturnCreated() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .post("/recipes")
                .content(asJsonString(RECIPE_4))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void givenRecipe_whenUpdateRecipe_thenReturnNoContent() throws Exception {

        when(recipeService.findByName(UPDATED_RECIPE_2.getName())).thenReturn(RECIPE_2);

        mockMvc.perform(MockMvcRequestBuilders
                .put("/recipes/" + UPDATED_RECIPE_2.getName())
                .content(asJsonString(UPDATED_RECIPE_2))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void givenNotPresentRecipeName_whenUpdateRecipe_thenReturnNotFound() throws Exception {

        doThrow(new ResourceNotFoundException()).when(recipeService).update(RECIPE_4.getName(), RECIPE_4);

        mockMvc.perform(MockMvcRequestBuilders
                .put("/recipes/" + RECIPE_4.getName())
                .content(asJsonString(RECIPE_4))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException));
    }


    @Test
    public void givenRecipeName_whenDeleteRecipe_thenReturnNoContent() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/recipes/" + RECIPE_1.getName())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        then(recipeService).should().deleteByName(anyString());
    }

    @Test
    public void givenNotPresentRecipeName_whenDeleteRecipe_thenReturnNotFound() throws Exception {

        doThrow(new ResourceNotFoundException()).when(recipeService).deleteByName(RECIPE_4.getName());

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/recipes/" + RECIPE_4.getName())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException));
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}