This is the final build of the Cooking Chef app; this document will give an overview of the code structure,
how to run the code, current issues, idiosyncrasies, and thoughts on future development.

    Build instructions:
        This repository can be directly imported into Android Studio and run with little to no setup.
        There is also a .apk file which is a snapshot of the app, and can be loaded into an android phone

    Code structure:

        The app consists of one activity, and multiple fragments which represent the different "pages" of the app.
        This home activity holds the buttons used to navigate around the app.
    
        Packages:
            The code is divided into packages depending on what aspect of the app they handle.
            
            Activities: This package is for app activities; there's only one, the home activity.
            API: All code used to interact with and manage data from the API is in this package. 
                -"ApiConnection" handles connecting and requests. 
                -"ApiBitmapHolder" manages and saves image responses. 
                -"ApiJSONArrayHolder" manages and saves any list provided by the API
                -"ApiJSONObjectHolder" manages and saves any single object provided by the API

            Database: All code that creates and interacts with the app database
                -"daos" all Data Access Objects, which are the interactions with specific database tables.
                -"models" setup the structure of database tables
                -"AppDatabase" main database class, which lists which daos the database implements
                -"DbHolder" holds the reference to the database, which can be accessed by all fragments.

            Fragments:
                - Home fragment: holds recently viewed recipes and is the first fragment shown
                - Favorites fragment: shows recipes flagged as favorites
                - Ingredient fragment: also referred to as pantry, interacts with the database ingredient table and is used to select ingredients to search for.
                - Search fragment: sends requests to the API and displays search results
                - Search fragment dialog: Allows viewing and interacting with a recipe selected from any view
                - Search options fragment: holds all search modification options.

            Recyclerviews: All "lists" seen in the fragments are recyclerviews, which are just efficient list containers. Each fragment with a list has it's own recycler view
                -"*RowData" defines what data each row can hold
                -"*RowController" functions to interact with the recycler view
                -"*Recycler" is the adapter/view, which handles initial startup of the view and sets row data.

    Current issues:
        - The key used to access the API is the "free" tier, which limits the daily use of the app.
            -This key is currently hard coded into the app, and before releasing as a product that must change.
        - Selecting favorites currently works when the recipe dialog is opened from the home fragment.
        - Returned API results are limited and not paginated
        - Not all database functionality is implemented.
        - Implementing units of measurement and pantry inventory levels is not implemented, but the hooks to do so exist.
        - Some layouts need additional formatting
        - Currently no support for tablets
        - No strings translation files
        
    Future development:
        - The API key should be retrieved from an external server, which the app would use to make requests.
            - Anonymous users should be limited, as requests costs money. Registered users can be free.
            - Registering users would require an external server; same one where the API key lives.
        - Data model for the database should be refactored.
        - Supporting tablets is a case of creating new layouts and some code in the fragments. 
        - The next phase of app development should focus on the infrastucture that supports the app, like authentication and user data.