package com.demo.receipescrape.controller;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.receipescrape.model.Receipe;
import com.demo.receipescrape.model.ReceipeResponse;

@RestController
public class SrapeController {
	
	
	@GetMapping("/getReceipe")
	public ResponseEntity<ReceipeResponse> getReceipe() {
		
		ReceipeResponse response = new ReceipeResponse();
		Receipe receipe = new Receipe();
		
		receipe.setIngredients(new ArrayList<String>());
		receipe.setInstructions(new ArrayList<String>());
		response.setReceipe(receipe);
		try {
			Document doc = Jsoup.connect("https://www.kingarthurbaking.com/recipes/chocolate-chip-cookies-recipe").get();
			Elements ingredients = doc.select(".ingredient-section").select(".list--bullets");
			ingredients.select("li").forEach(e -> {
				String ingredient = e.text();
				receipe.getIngredients().add(ingredient);
			});
			
			Elements instructions = doc.select(".field--recipe-steps").select(".list--numbers");
			instructions.select("li").forEach(e -> {
				String instruction = e.text();
				receipe.getInstructions().add(instruction);
			});
		} catch (IOException e) {
			return new ResponseEntity<ReceipeResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<ReceipeResponse>(response, HttpStatus.OK);
	}

}
