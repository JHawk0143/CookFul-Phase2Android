document.addEventListener('DOMContentLoaded', function() {
    // Get recipe ID from query parameter
    const urlParams = new URLSearchParams(window.location.search);
    const recipeId = urlParams.get('recipeId');

    // Fetch recipe information
    const apiKey = 'eac418e227d947d58d460f78c62c3c15'; // Replace 'YOUR_API_KEY' with your actual API key
    const apiUrl = `https://api.spoonacular.com/recipes/${recipeId}/information?includeNutrition=true&apiKey=${apiKey}`;

    fetch(apiUrl)
        .then(response => response.json())
        .then(data => {
            // Process recipe information and display
            displayRecipeInformation(data);
            // Fetch and display recipe instructions
            fetchRecipeInstructions(recipeId, apiKey);
            // Fetch and display nutrition label
            fetchNutritionLabel(recipeId, apiKey);
        })
        .catch(error => {
            console.error('Error fetching recipe information:', error);
        });
});

function displayRecipeInformation(recipe) {
    // Get elements to display recipe information
    const recipeTitle = document.getElementById('recipe-title');
    const recipeImage = document.getElementById('recipe-image');
    const recipeIngredients = document.getElementById('recipe-ingredients');

    // Set recipe title and image
    recipeTitle.textContent = recipe.title;
    recipeImage.src = recipe.image;

    // Display recipe ingredients
    recipeIngredients.innerHTML = ''; // Clear previous ingredients
    recipe.extendedIngredients.forEach(ingredient => {
        const ingredientItem = document.createElement('li');
        ingredientItem.textContent = ingredient.original;
        recipeIngredients.appendChild(ingredientItem);
    });
}

function fetchRecipeInstructions(recipeId, apiKey) {
    const apiUrl = `https://api.spoonacular.com/recipes/${recipeId}/analyzedInstructions?apiKey=${apiKey}`;

    fetch(apiUrl)
        .then(response => response.json())
        .then(data => {
            // Process and display recipe instructions
            displayRecipeInstructions(data);
        })
        .catch(error => {
            console.error('Error fetching recipe instructions:', error);
        });
}

function displayRecipeInstructions(recipeInstructions) {
    const instructionsList = document.getElementById('recipe-instructions');

    instructionsList.innerHTML = '';
    recipeInstructions.forEach(instruction => {
        instruction.steps.forEach(step => {
            const instructionItem = document.createElement('li');
            instructionItem.textContent = step.step;
            instructionsList.appendChild(instructionItem);
        });
    });
}


function fetchNutritionLabel(recipeId, apiKey) {
    const apiUrl = `https://api.spoonacular.com/recipes/${recipeId}/nutritionLabel?defaultCss=true&showOptionalNutrients=false&showZeroValues=false&showIngredients=false&apiKey=${apiKey}`;

    fetch(apiUrl)
        .then(response => response.text())
        .then(data => {
            document.getElementById('nutrition-label').innerHTML = data;
        })
        .catch(error => {
            console.error('Error fetching nutrition label:', error);
        });
}
