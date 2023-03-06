<table>
<tr>
    <th>METHODE</th>
    <th>ROUTE</th>
    <th>DESC</th>
    <th>INPUT</th>
    <th>OUTPUT</th>
</tr>
<tr><td>POST</td><td>/login</td><td>Connexion</td><td>Avec connexion OAuth2 = 

```json
{
    // Voir info Google
}
```

Avec connexion Appli = 

```json
{
    String name, 
    String password,
} 
```

</td><td>En cas de succès

```json
status: 200
data : {
    String success = "user connected",
    String token,
    String name,
}
```

En cas d’erreur

```json
status: 400
data : {
    Object error,
}
```

</td></tr>
<tr><td>POST</td><td>/register</td><td>Inscription</td><td>Avec connexion OAuth2 = 
  
```json
{
    String provider = Google, // ou autre
    // Voir info Google 
}
```

Avec connexion Appli = 

```json
{
    String provider = FetaList
    String name,
    String password,
    String phoneNumber,
}
```

</td><td>En cas de succès

```json
status: 200
data : {
    String success = "user registered",
    String token,
    String nom,
}
```

En cas d’erreur

```json
status: 400
data : {
    Object error,
}
```

</td></tr><tr><td>
DELETE </td><td> /user</td><td>Supprimer compte</td><td>

```json
{
    String token,
    String password,
}
```

</td><td>
En cas de succès

```json
status: 200
data : {
    String success = "user deleted",
}
```

En cas d’erreur

```json
status: 400
data : {
    Object error,
}
```

</td></tr><tr><td>
POST</td><td>/receipe/find</td><td>Rechercher une recette</td><td>

```json
{
    String search,
}
```
</td><td>
En cas de succès

```json
status: 200
data : {
    String success = "receipe found",
    Object receipes = [{
        Int idReceipe,
        String nameReceipe,
        String imageReceipe,
        Float rating,
        Float estimatedTime,
        String categoryName,
        Object ingredients: [{
            String ingredientName,
            Float quantity,
            String unity,
        }],
    }],
}
```

En cas d’erreur

```json
status: 400
data : {
    Object error,
}
```

</td></tr><tr><td>
GET</td><td>/receipe/{id}</td><td>Voir une recette</td></td><td><td>En cas de succès

```json
status: 200
data : {
    String success = "receipe found",
    Object receipes = [{
        Int idReceipe,
        String nameReceipe,
        String imageReceipe,
        Float rating,
        Float estimatedTime,
        String categoryName,
        Object ingredients: [{
            String ingredientName,
            Float quantity,
            String unity,
        }],
    }],
}
```

En cas d’erreur

```json
status: 400
data : {
    Object error, 
}
```

</td></tr><tr><td>
POST</td><td>/receipe/create</td><td>Partager une recette (sans rajouter dans la liste de course). Renvoie la recette créer dans la BDD</td><td>

```json
{
    String nom,
    String image,
    Float estimatedTime,
    Int idCategorie,
    Object ingredients = [{
        Int idIngredient,
        Float quantity,
        String unity,
    }],
}
```
</td><td>
En cas de succès

```json
status: 200
data : {
    String success = "receipe created",
    Object receipes = [{
        Int idReceipe,
        String nameReceipe,
        String imageReceipe,
        Float rating,
        Float estimatedTime,
        String categoryName,
        Object ingredients: [{
            String ingredientName,
            Float quantity,
            String unity,
        }],
    }],
}
```

En cas d’erreur

```json
status: 400
data : {
    Object error,
}
```

</td></tr><tr><td>
POST</td><td>/ingredient/create</td><td>Ajoute un ingrédient. Renvoie l’objet de la BDD correspondant (/!\ créer l’objet uniquement s’il n’existe pas)</td><td>

```json
{
    String nom,
}
```

</td><td>En cas de succès

```json
status: 200
data : {
    String success = "receipe created",
    Object ingredient : [{
        String name,
        Int idIngredient,
    }],
}
```

En cas d’erreur

```json
status: 400
data : {
    Object error,
}
```

</td></tr><tr><td>
POST</td><td>/favorite/add/</td><td>Ajouter un favoris</td><td>

```json
{
    Int idReceipe,
    Strring token,
}
```

</td><td>En cas de succès

```json
status: 200
data : {
    String success = "favorite aded",
    Object receipes = [{
        Int idReceipe,
        String nameReceipe,
        String imageReceipe,
        Float rating,
        Float estimatedTime,
        String categoryName,
        Object ingredients: [{
            String ingredientName,
            Float quantity,
            String unity,
        }],
    }],
}
```

En cas d’erreur

```json
status: 400
data : {
    Object error,
}
```

</td></tr><tr><td>
GET</td><td>/favorite/ls/{token}</td><td>Voir mes favoris</td><td></td><td>En cas de succès

```json
status: 200
data : {
    String success = "favorite aded",
    Object receipes = [{
        Int idReceipe,
        String nameReceipe,
        String imageReceipe,
        Float rating,
        Float estimatedTime,
        String categoryName,
        Object ingredients: [{
            String ingredientName,
            Float quantity,
            String unity,
        }],
    }],
}
```

En cas d’erreur

```json
status: 400
data : {
    Object error,
}
```

</td></tr><tr><td>
DELETE</td><td>/favorite</td><td>Supprimer un favoris</td><td>

```json
{
    Int idFavorite,
    String token,
}
```

</td><td>En cas de succès

```json
status: 200
data : {
    String success = "favorite removed",
}
```

En cas d’erreur

```json
status: 400
data : {
    Object error,
}
```

</td></tr><tr><td>
POST</td><td>/friend/add</td><td>Ajouter un amis</td><td>

```json
{
    String token,
    Int idUser,
}
```

</td><td>En cas de succès

```json
status: 200
data : {
    String success = "friend request sended",
}
```

En cas d’erreur

```json
status: 400
data : {
    Object error,
}
```

</td></tr><tr><td>
GET</td><td>/friend/ls/{token}</td><td>Voir tes amis</td><td></td><td>En cas de succès

```json
status: 200
data : {
    String success = "friend list",
    Object friends = [{
        Int idFriendship,
        String status,
        Object friend = [{
            Int idUser,
            String name,
        }],
    }],
}
```

En cas d’erreur

```json
status: 400
data : {
    Object error,
}
```

</td></tr><tr><td>
POST</td><td>/friend/validate</td><td>Valider une demande en ami</td><td>

```json
{
    String token,
    Int idFriendship,
}
```

</td><td>En cas de succès

```json
status: 200
data : {
    String success = "friendship validated",
}
```

En cas d’erreur

```json 
status : 400
data : {
    Object error,
}
```

</td></tr><tr><td>
POST</td><td>/friend/refuse</td><td>Refuser une demande en ami</td><td>

```json
{
    String token,
    Int idFriendship,
}
```

</td><td>En cas de succès

```json
status: 200
data : {
    String success = "friendship refused",
}
```

En cas d’erreur

```json
status : 400 
data : {
    Object error,
}
```

</td></tr><tr><td>
DELETE</td><td>/friend</td><td>Supprimer un ami</td><td>

```json
{
  String token,
  Int idFriendship,
}
```

</td><td>En cas de succès

```json
status: 200
data : {
  String success = "friendship deleted",
}
```

En cas d’erreur

```json
status: 400
data : {
  Object error,
}
```

</td></tr><tr><td>
GET</td><td>/msg/send/{idShoppingList}/{token}</td><td>Envoyer liste de course par sms à toi même</td><td></td><td>En cas de succès

```json
status: 200
data : {
  String success = "shopping list sended",
}
```

En cas d’erreur

```json
status: 400
data : {
  Object error,
}
```

</td></tr><tr><td>
POST</td><td>/msg/share</td><td>Partager liste de courses à un amis (via appli)</td><td>

```json
{
  String token,
  Int idShoppingList,
  Int idFriend,
}
```
</td><td>En cas de succès

```json
status: 200
data : { {
  String success = "shopping list sended to friend",
}
```
En cas d’erreur

```json
status: 400
data : { {
  Object error,
}
```

</td></tr><tr><td>
POST</td><td>/shop/add</td><td>Ajouter une recette à la liste de courses, et renvoie cette même liste</td><td>

```json
{
    String token,
    Int idReceipe,
    Date maxBuyDate,
}
```
</td><td>En cas de succès

```json
status: 200
data : {
    String success = "Receipe added in shopping list",
    Object shoppingList = [{
        Int idShoppingList,
        Date maxBuyDate,
        Object receipes = [{
            Int idReceipes,
            String nameReceipe,
        }],
        Object ingredients = [{
            String name,
            Float quantity,
            String unity,
        }],
    }],
}
```

En cas d’erreur

```json
status: 400
data : {
  Object error,
}
```

</td></tr><tr><td>
GET</td><td>/shop/ls/{token}</td><td>Voir mes listes de courses</td><td></td><td>En cas de succès

```json
status: 200
data : {
  String success = "List of all shopping list",
  Object shoppingList = [{
    Int idShoppingList,
    Date maxBuyDate,
    Object receipes = [{
      Int idReceipes,
      String nameReceipe,
    }],
    Object ingredients = [{
      String name,
      Float quantity,
      String unity,
    }],
  }],
}
```

En cas d’erreur

```json
status: 400
data : {
  Object error,
}
```

</td></tr><tr><td>
GET</td><td>/shop/{idShoppingList}/{token}</td><td>Voir le détail d’une liste de courses</td><td></td><td>En cas de succès

```json
status: 200
data : {
    String success = "Details of shopping list",
    Object shoppingList = [{
        Int idShoppingList,
        Date maxBuyDate,
        Object receipes = [{
            Int idReceipes,
            String nameReceipe,
        }],
        Object ingredients = [{
            String name,
            Float quantity,
            String unity,
        }],
    }],
}
```
En cas d’erreur

```json
status: 400
data : {
    Object error,
}
```

</td></tr><tr><td>
POST</td><td>/shop/edit</td><td>Modifier une liste de course et la renvoie</td><td>

```json
{
    String token,
    Int idShoppingList,
    Bool add,
    Object receipe = [{
        Int idReceipe,
    }],
    Object ingredients = [{
        Int idIngredient,
        Float quantity,
    }],
}
```

</td><td>En cas de succès

```json
status: 200
data : {
    String success = "shopping list updated",
    Object shoppingList = [{
        Int idShoppingList,
        Date maxBuyDate,
        Object receipes = [{
            Int idReceipes,
            String nameReceipe,
        }],
        Object ingredients = [{
            String name,
            Float quantity,
            String unity,
        }],
    }],
}
```

En cas d’erreur

```json
status: 400
data : {
    Object error,
}
```
</td></tr><tr><td>GET</td><td>/ingredients/ls</td><td>Récupère la liste des ingrédients</td><td></td><td>En cas de succès

```json
status: 200
data : {
    String success = "list of ingredients",
    Object ingredients = [{
        Int idIngredient,
        String name,
    }],
}
```

En cas d’erreur

```json
status: 400
data : {
    Object error,
}
```
</td></tr><tr><td>
GET</td><td>/unity/ls</td><td>Récupère la liste des unités</td><td></td><td>En cas de succès

```json
status: 200
data : {
    String success = "list of unity",
    Object unities = [{
        Int idUnity,
        String unity,
    }],
}
```

En cas d’erreur

```json
status: 400
data : {
    Object error,
}
```

</td></tr><tr></td><td><td></td><td><span style="color:red">
Envoie un rappel pour liste de course (côté serveur uniquement, ce n’est pas une route accessible)</span></td><td>

```json
{
    String phone,
    Int idShoppingList,
}
```

</td><td>
</tr>
</table>