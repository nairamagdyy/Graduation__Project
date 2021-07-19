package exportkit.xd.Controller;

import android.content.Context;

import java.util.ArrayList;
import java.util.Vector;

import exportkit.xd.DB.AppDBController;
import exportkit.xd.Model.Recipe;
import exportkit.xd.View.IAppViews;
import exportkit.xd.View.Recipe.Item;

public class recipeController {

    IAppViews view;
    AppDBController db;

    public recipeController(IAppViews view) {
        this.view = view;
        db = new AppDBController((Context) this.view);
    }

    public void addRecipe(Recipe recipe) {
        long id = db.insertRecipe(recipe);
        if(id>0) {
            view.onSuccess("Add Successfully");
        }
        else
            view.onError("FAILED");
    }

    public long addRecipeNutrients(ArrayList<Item> facts){
        return db.insertRecipeNutrients(facts);
    }

    public Vector<String> getRecipeNutrients(int id){
        return db.getRecipeNutrients(id);
    }

    public Vector<Integer> viewRecipeList(int userId){
        return db.getRecipeList(userId);
    }

    public Recipe getRecipe(int id){
        return  db.getRecipe(id);
    }

    public void deleteRecipe(int id){
        boolean remove= db.deleteRecipe(id);
        if(remove) {
            view.onSuccess("Delete Successfully");
        }
        else
            view.onError("FAILED");

    }

    public long addToFavList(int userID, int recipeID){
        return db.insertToFavList(userID, recipeID);
    }

    public Vector<Integer> viewFavList(int userId){
        return db.getFavList(userId);
    }

    public void unFavRecipe(int userId, int recipeId){
       db.deleteRecipeFromFavList(userId, recipeId);
    }


}
