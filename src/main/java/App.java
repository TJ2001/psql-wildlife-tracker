
import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;
import java.util.Arrays;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("animals", Animal.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/animals/edit", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("animals", Animal.all());
      model.put("template", "templates/edit-animal.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/animal/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/new-animal.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/animal/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      String type = request.queryParams("type");
      Animal newAnimal = new Animal(name, type);
      newAnimal.save();
      response.redirect("/");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/animal/:id/remove", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Animal animal = Animal.find(Integer.parseInt(request.params(":id")));
      animal.delete();
      response.redirect("/sighting/edit");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/animal/:id/edit", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Animal animal = Animal.find(Integer.parseInt(request.params(":id")));
      String name = request.queryParams("name");
      String type = request.queryParams("type");
      animal.update(name, type);
      response.redirect("/animals/edit");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/animals/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Animal animal = Animal.find(Integer.parseInt(request.params(":id")));
      model.put("animal", animal);
      model.put("template", "templates/animal.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/sighting/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("animals", Animal.all());
      model.put("template", "templates/new-sighting.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/sighting/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Animal animal = Animal.find(Integer.parseInt(request.queryParams("animalId")));
      int animalId = animal.getId();
      String location = request.queryParams("location");
      String ranger = request.queryParams("ranger");
      Sighting sighting = new Sighting (animalId, location, ranger);
      sighting.save();
      model.put("animal", animal);
      response.redirect("/");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());


//so far done


  //

  //
  //   get("/recipe", (request, response) -> {
  //     Map<String, Object> model = new HashMap<String, Object>();
  //     model.put("recipes", Recipes.all());
  //     model.put("template", "templates/list-recipes.vtl");
  //     return new ModelAndView(model, layout);
  //   }, new VelocityTemplateEngine());
  //
  //   get("/recipe/:id/edit", (request, response) -> {
  //     Map<String, Object> model = new HashMap<String, Object>();
  //     Recipes repcipes = Recipes.find(Integer.parseInt(request.params(":id")));
  //     model.put("recipes", repcipes);
  //     model.put("categories", Category.all());
  //     model.put("template", "templates/recipe-edit.vtl");
  //     return new ModelAndView(model, layout);
  //   }, new VelocityTemplateEngine());
  //

  //
  //
  //   get("/recipe/:id", (request, response) -> {
  //     Map<String, Object> model = new HashMap<String, Object>();
  //     Recipes repcipes = Recipes.find(Integer.parseInt(request.params(":id")));
  //     model.put("recipes", repcipes);
  //     model.put("template", "templates/recipe.vtl");
  //     return new ModelAndView(model, layout);
  //   }, new VelocityTemplateEngine());
  //

  //
  //
  //   post("/recipe/:id/remove", (request, response) -> {
  //     Map<String, Object> model = new HashMap<String, Object>();
  //     Recipes recipe = Recipes.find(Integer.parseInt(request.params(":id")));
  //     recipe.delete();
  //     response.redirect("/recipe");
  //     return new ModelAndView(model, layout);
  //   }, new VelocityTemplateEngine());
  //
  //   post("/recipe/:id/edit", (request, response) -> {
  //     Map<String, Object> model = new HashMap<String, Object>();
  //     String name = request.queryParams("name");
  //     String ingredients = request.queryParams("ingredients");
  //     String instructions = request.queryParams("instructions");
  //     Recipes recipe = Recipes.find(Integer.parseInt(request.params(":id")));
  //     recipe.update(name, ingredients, instructions);
  //     String[] tags = request.queryParamsValues("tags");
  //     for (int i = 0; i < tags.length; i++) {
  //       Category foundCategory = Category.find(Integer.parseInt(tags[i]));
  //       foundCategory.addRecipe(recipe);
  //     }
  //     response.redirect("/recipe");
  //     return new ModelAndView(model, layout);
  //   }, new VelocityTemplateEngine());
  //
  }
}
