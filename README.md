# Mixologist

## A simple game about mixing drinks

Mixologist is a simple game which lets users can mix drinks from provided ingredients. Each ingredient will have a different effect on the traits of the drink. Users will be able to select their glass size, amount of ice, and specific number of ingredients based on the size of the glass. This app is aimed at casual gamers, but it can be played by anyone. It is inspired by my inerset in mixology, and from my love of video games.

Ingredient Traits
- sweetness
- strength
- thickness

## User Stories
- As a user, I want to be able to save my drink recipes, and specific the name, ingredients, and directions
- As a user, I want to be able to add my drink to a menu
- As a user, I want to be able to shake or stir my drink
- As a user, I want to be able to edit my recipes
- As a user, I want to be able to remove a drink from my menu
- As a user, I want to be able to choose how much ice is in a drink
- As a user, I want to be able to delete a drink recipe
- As a user, I want to be able to choose the size of my drink
- As a user, I want to be able to list the names of all the drinks on my menu
- As a user, I want to be able to save my menu/drinks/ingredients
- As a user, I want to be able to load my menu/drinks/ingredients

# Instructions for Grader
- You can locate my visual component by running the program, or by mixing a drink
- You can generate the first required action of the user story "add a drink to a menu" by selecting mix a drink and successfully mixing a drink
- You can generate the second required action of the user story "save my drink recipes..." selecting mix a drink and successfully mixing a drink
- You can specifiy the size and ice amount of a drink by selecting the mix a drink option, and select
- You can shake or stir a drink by selecting the shake/stir button respectively after adding at all your ingredients to your drink
- You can view your menu by selecting the view menu option
- You can remove drink from the menu by selecting menu then selecting the drink you want to remove and pressing the remove drink button
- You can view your recipes by selecting the view recipes option
- You can remove a drink from recipes by selecting menu then selecting the drink you want to remove and pressing the remove drink button
- You can view the available ingredients by selecting the view ingredients option
- You can add an ingredient by selecting ingredients then selecting add ingredient, then specifying the name, sweetness, strength and thickness of the ingredient 
- You can remove an ingredient by selecting ingredients then selecting the ingredient you want to remove and pressing the remove drink button
- You can load your menu/drinks/ingredients by selecting view menu/drinks/ingredients respectively and selecting load my menu/drinks/ingredients
- You can save your menu/drinks/ingredients by selecting view menu/drinks/ingredients respectively and selecting save my menu/drinks/ingredients

# Phase 4: Task 2
Adding a new ingredient : 
Wed Aug 07 21:37:23 PDT 2024

Created a new ingredient:
Wed Aug 07 21:45:53 PDT 2024
Created a new ingredient: Soap

Removing an ingredient:
Wed Aug 07 22:08:20 PDT 2024
Removed ingredient: Cola

Adding a new Recipe
Wed Aug 07 22:09:13 PDT 2024
Added recipe for: Paper Plane

Removing a recipe:
Wed Aug 07 21:46:31 PDT 2024
Removed recipe for: Leagally Disinct Painkiller

Removing a drink from the menu:
Wed Aug 07 21:45:31 PDT 2024
Removed Potato Martini from the menu

Adding a drink to the menu;
Wed Aug 07 21:44:48 PDT 2024
Added Cranberry Soda to menu

# Phase 4: Task 3
If I had more time to work on the project, I might refactor extract some repetative code from the class that extend the SubView class to the SubView abstract class. Since all of my subview JPanels have a similar layout, extracting some of the repetitive code from methods like the createSideBar method would reduce a lot of the repetion in those classes. I might also refactor the ContentView class
to use the singleton pattern since all the subviews should use the same instance of ContentView. It also wouldn't make sense for there to be more than once instance of any class in view package, or for other classes to be able to instantiate a new instance of any of those classes. The last refactoring I might do it to make to recipes class iterable, so I don't have to rely on getters to the array list of drink stored in recipes.
