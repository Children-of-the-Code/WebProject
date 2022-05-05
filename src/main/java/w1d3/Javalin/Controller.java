package w1d3.Javalin;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;

public class Controller {
    public static void main(String[] args) {

        GroceryDataAccessObject repository = new GroceryDataAccessObject();
        Javalin app = Javalin.create();
        app.start(5000);

//        an endpoint.. a certain response is expected by reaching a particular url
        app.get("/", ctx->
            ctx.result("Hello server!")
        );
//        what the part with -> is is called a lambda expression
//        you can think of as alternative way of writing methods
//        in some language, like javascript, this is just one particular flavor of method
//        in java in particular, we use a lambda expression to pass a function as a parameter to a method
//        to pass behavior that should be performed in a particular instance

//        this endpoint should respond with a list of all grocery items
//        what the lambda function is saying: we're passing some behavior along that says
//        "you have something that we will call 'ctx', and we are defining what you should do to it
        app.get("/grocery/", ctx->
                {

//                    GET all grocery list items (when we run to the store)

//                    this line is saying: return the JSON representation of our list
/*
                    JSON: javascript object notation
                    a standardized way of storing an object information as a string that was inherited from javascript
                    {
                    "itemName":"banana"
                    }
                   ctx.json is saying 'wrap our thing into this format and put a little bow on it'
*/
//at this point, we are returning an array of grocery items which can then be turned into a JSON representation
                    GroceryItem[] groceryList = repository.getAllGroceryItems();
                    ctx.json(groceryList);
        }
        );

        app.put("/grocery/", ctx ->
        {
//            POST a new grocery list item (when we run out of milk)
//            we need to get a JSON out of the request body... this is surprisingly difficult
//            luckily, the jackson object mapper dependency
            ObjectMapper mapper = new ObjectMapper();
//            what's happening here: we use what we can see in the request body
//            to load things into the format that Jackson object mapper sees in GroceryItem.class
//            the .class portion means that Jackson is using something called reflection
//            to read the class file itself and determine the variables within,
//            then match those variables against the information in JSON
            GroceryItem item = mapper.readValue(ctx.body(), GroceryItem.class);
            repository.addGroceryItem(item);
        });
//HTTP verbs
//        verbs are an organizational tool and nothing else - so we can do multiple things to a single endpoint
//        eg CRUD a single item from a resource (like CRUD a website.com/user/208371948)
        app.delete("/grocery/", ctx ->
        {
//            DELETE all grocery items (when we checkout)
            repository.deleteGroceryList();
        });
        app.post("/grocery/", ctx -> {
            repository.createGroceryFile();
        });




//        the trick is, is that we'll be a real file to persist, and effectively this file functions as our database



    }


}
