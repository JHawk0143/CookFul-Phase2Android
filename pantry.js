document.addEventListener('DOMContentLoaded', function() {
    const apiKey = 'eac418e227d947d58d460f78c62c3c15'; // Replace 'YOUR_API_KEY' with your actual API key
    executeSearch('', apiKey); // Initial search with empty query
});

document.getElementById('searchButton').addEventListener('click', function() {
    console.log('Search button clicked'); // Log a message when the search button is clicked
    const query = document.getElementById('searchInput').value.trim();
    console.log('Query:', query); // Log the value of the query
    const apiKey = 'eac418e227d947d58d460f78c62c3c15'; // Replace 'YOUR_API_KEY' with your actual API key
    executeSearch(query, apiKey);
});


function executeSearch(query, apiKey) {
    const apiUrl = constructApiUrl(query, apiKey);
    console.log('API URL:', apiUrl);

    fetch(apiUrl)
        .then(response => response.json())
        .then(data => {
            displayResults(data);
        })
        .catch(error => {
            console.error('Error fetching data:', error);
        });
}

function constructApiUrl(query, apiKey) {
    const formattedQuery = encodeURIComponent(query);
    const baseUrl = 'https://api.spoonacular.com/food/ingredients/search?';
    const queryParams = `query=${formattedQuery}&number=3&apiKey=${apiKey}`;
    return `${baseUrl}${queryParams}`;
}

function displayResults(responseData) {
    const resultsContainer = document.getElementById('resultsContainer');
    resultsContainer.innerHTML = '';

    const results = responseData.results; // Extract the results array from the response data

    if (results && Array.isArray(results)) { // Check if results is an array
        results.forEach(ingredient => {
            const ingredientName = ingredient.name;

            // Check if the ingredient name is not already present in the results container
            if (!resultsContainer.querySelector(`.ingredient[data-name="${ingredientName}"]`)) {
                const ingredientItem = document.createElement('div');
                ingredientItem.classList.add('ingredient');

                // Create a span element for the ingredient name
                const ingredientNameSpan = document.createElement('span');
                ingredientNameSpan.textContent = ingredientName;

                // Set a data attribute to store the ingredient name
                ingredientItem.setAttribute('data-name', ingredientName);

                // Add a click event listener to add the ingredient to the pantry when clicked
                ingredientItem.addEventListener('click', () => {
                    addToPantry(ingredient.id, ingredientName);
                });

                ingredientItem.appendChild(ingredientNameSpan);

                resultsContainer.appendChild(ingredientItem);
            }
        });
    } else {
        console.error('Invalid results data:', responseData);
    }
}





