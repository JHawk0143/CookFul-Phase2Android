# CST8334 Software Development Project
## Client
Appy.Yo

## Authors
Julianna Hawkins
Wissam Kaadou
Aryan Sehgal
Lele Xiong
Nicholas Zhang

## Description
This project is an implementation of the Cookful app which is a web application that uses the Spoonacular API to be able to search for recipes as well as to keep track of ingredients that you may need to purchase for the recipes. You will require to sign up and login to your account to search for recipes as well as to add to an ingredient list. The goal with this Cookful app is to reduce food waste so it allows people to input certain ingredients that are about to go bad or they just need to use for other reasons to find recipes that use the ingredients. The ultimate aim is to reducing food waste.

## Prerequisites
- An application to develop and modify text files like an IDE as an example

## Setting up the local environment
- XAMPP localhost database. If using a different database, the signup and login files will likely need an update to an according database connection. Version 3.3.0 of the XAMPP Control Panel using up to date version of MySQL and Apache. Could use more recent version of XAMPP Control Panel as well.
- Spoonacular Account to switch the API key in the needed files as the key used in the code currently use a different key.
  
## Running the application
- Once the spoonacular api key is changed, it can be used to search for recipes and do other requests related with the API.
- The XAMPP Control Panel MySQL database was mainly used as a method for the demonstrations for account logging. If using XAMPP, you may need to adjust the appropriate variables relating to connection.php file to the appropriate parts in database configuration.

## Known Issues 
- The signup currently allows the same email to register more than once. If this is an intended feature for the app, no issues.

- The login currently only runs the Javascript properly but not the php to check if the users are actually registered. Currently lets everyone through to the app if it passes the Javascript functions.

- The signup AND login backend codes (javascript and php) likely may only work on the system of the user that wrote it. UPDATE accordingly.

- The search bar currently does not properly use any of the diet restrictions as part of the search for recipes.

- The search bar currently searches for recipes but does not contain a method to list the recipes with the most amount of ingredients in the listed search bar. (ex. "carrot, broccoli" shows recipes with only carrots instead of the ones with both first.)

- The pantry allows user to search for ingredients in the search bar, but no implementation to add to the pantry or picture of ingredient(s)

- There is no implementation of favouriting recipes.
