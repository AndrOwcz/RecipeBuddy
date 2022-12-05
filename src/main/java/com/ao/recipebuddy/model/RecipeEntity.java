package com.ao.recipebuddy.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "recipes")
public class RecipeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", length = 128, unique = true)
    @NotBlank(message = "Recipe name cannot be blank or null")
    @Length(max = 128, message = "Recipe name cannot exceed 128 characters")
    private String name;

    @Column(name = "instructions", length = 1024)
    @NotBlank(message = "Instructions cannot be blank or null")
    @Length(max = 1024, message = "Author field cannot exceed 128 characters")
    private String instructions;

    @Column(name = "author", length = 128)
    @Email(message = "Author field should contain a proper email address")
    @Length(max = 128, message = "Author field cannot exceed 128 characters")
    private String author;

}