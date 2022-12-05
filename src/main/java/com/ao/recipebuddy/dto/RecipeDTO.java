package com.ao.recipebuddy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RecipeDTO {

    @NotBlank(message = "Recipe name cannot be blank or null")
    @Length(max = 128, message = "Recipe name cannot exceed 128 characters")
    @JsonProperty("name")
    private String name;

    @NotBlank(message = "Instructions cannot be blank or null")
    @Length(max = 1024, message = "Author field cannot exceed 128 characters")
    @JsonProperty("instructions")
    private String instructions;

    @Email(message = "Author field should contain a proper email address")
    @Length(max = 128, message = "Author field cannot exceed 128 characters")
    @JsonProperty("author")
    private String author;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RecipeDTO recipeDTO = (RecipeDTO) o;
        return Objects.equals(this.name, recipeDTO.name) &&
                Objects.equals(this.instructions, recipeDTO.instructions) &&
                Objects.equals(this.author, recipeDTO.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, instructions, author);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class RecipeDTO {\n");

        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    instructions: ").append(toIndentedString(instructions)).append("\n");
        sb.append("    author: ").append(toIndentedString(author)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}

