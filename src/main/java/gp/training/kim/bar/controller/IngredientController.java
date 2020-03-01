package gp.training.kim.bar.controller;

import gp.training.kim.bar.dto.IngredientDTO;
import gp.training.kim.bar.dto.entity.StoreHouseReport;
import gp.training.kim.bar.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/ingredients")
public class IngredientController {

	private final IngredientService ingredientService;

	@GetMapping(value = "/report", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
	@ResponseStatus(HttpStatus.OK)
	public StoreHouseReport report(@RequestHeader final Integer admin) {
		return ingredientService.getIngredientsReport();
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/")
	public IngredientDTO createIngredient(@RequestBody final IngredientDTO ingredient) {
		return ingredientService.createIngredient(ingredient);
	}
}