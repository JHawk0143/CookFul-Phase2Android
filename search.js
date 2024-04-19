document.addEventListener('DOMContentLoaded', function() {
  const apiKey = 'eac418e227d947d58d460f78c62c3c15'; // Replace 'YOUR_API_KEY' with your actual API key
  executeSearch('', apiKey); // Initial search with empty query
});

document.getElementById('search-button').addEventListener('click', function(event) {
  event.preventDefault(); // Prevent form submission

  const query = document.getElementById('query').value.trim();
  const apiKey = 'eac418e227d947d58d460f78c62c3c15'; // Replace 'YOUR_API_KEY' with your actual API key
  executeSearch(query, apiKey);
});

function executeSearch(query, apiKey) {
  const ingredients = document.getElementById('query').value.trim();
  const apiUrl = constructApiUrl(ingredients, apiKey);

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

function constructApiUrl(ingredients, apiKey) {
  const formattedIngredients = encodeURIComponent(formatIngredients(ingredients));
  const baseUrl = 'https://api.spoonacular.com/recipes/findByIngredients?ingredients=';
  const queryParams = `&number=5&apiKey=${apiKey}`;
  return `${baseUrl}${formattedIngredients}${queryParams}`;
}

function formatIngredients(ingredients) {
  // Replace spaces with commas
  return ingredients.replace(/\s+/g, ',');
} 

function displayResults(results) {
  const resultsContainer = document.getElementById('results');
  resultsContainer.innerHTML = '';

  results.forEach(recipe => {
      const recipeCard = document.createElement('div');
      recipeCard.classList.add('recipe-card');

      const image = document.createElement('img');
      image.src = recipe.image;
      image.addEventListener('click', () => {
          window.location.href = `recipe.html?recipeId=${recipe.id}`;
      });

      const title = document.createElement('div');
      title.classList.add('recipe-title');
      title.textContent = recipe.title;

      recipeCard.appendChild(image);
      recipeCard.appendChild(title);

      resultsContainer.appendChild(recipeCard);
  });
}
