<h1>
Routes APIs
</h1>

<div>
Toutes les requêtes demandant un token renverront une erreur 403 si le token est :
<ul><li>non rempli</li>
<li>invalide</li>
<li>expiré</li>
</ul>
</div>

<table>
<tr>
    <th>METHODE</th>
    <th>ROUTE</th>
    <th>DESC</th>
    <th>INPUT</th>
    <th>OUTPUT</th>
</tr>
<tr><td>POST</td><td>/user/login</td><td>Connexion</td><td>Avec connexion OAuth2 = 

```json
{
    // Voir info Google
}
```

Avec connexion Appli =

```json
{
    "name": string,
    "password": string,
} 
```
</td><td>En cas de succès

```json
status: 200
data : {
    "accessToken": string,
    "user": User,
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
<tr><td>POST</td><td>/user/register</td><td>Inscription</td><td>Avec connexion OAuth2 = 

```json
{
    String provider = Google, // ou autre
    // Voir info Google 
}
```

Avec connexion Appli =

```json
{
  "provider": string = LOCAL,
  "name":  string,
  "password": string,
  "phoneNumber": string,
  "mail": string
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
POST </td><td> /user/remove</td><td>Supprimer compte</td><td>

```json
{
    "token": string,
    "password": string,
}
```

</td><td>
En cas de succès

```json
status: 200
data : {
    boolean: true,
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
POST</td><td>/receipe/create</td><td>Créer une recette (quantités pour une personne)</td><td>

```json
{
    "nom": string,
    "categoryName": string,
    "image": string,
    "estimatedTime": int,
    "instructions": [string],
    "ri": [
      {
        "ingredient": {
          "idIngredient": int
        },
        "quantity": float,
        "unit": {
          "idUnit": int
        }
      }
    ]
}
```
</td><td>
En cas de succès

```json
status: 200
data : {
    boolean: hasBeenCreated
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
GET</td><td>/receipe/getAll</td><td>Récupére toutes les recette</td><td>
</td><td>
En cas de succès

```json
status: 200
data : {
    "receipes": [{
        "id": int,
        "name": string,
        "image": string,
        "category": {
            "id": int,
            "name": string
        },
        "rating": float,
        "estimatedTime": int,
    }]
}
```
</td></tr><tr><td>
GET</td><td>/receipe/{id}</td><td>Récupère une recette par son ID</td><td><td>En cas de succès

```json
status: 200
data : {
    "receipe": {
        "id": int,
        "name": string,
        "image": string,
        "category": {
            "id": int,
            "name": string
        },
        "rating": float,
        "estimatedTime": int,
    },
    "instructions": [string]
    "ri": [{
        "receipe": Receipe
        "ingredient": {
            "id": int,
            "name": string
        },
        "unit": {
            "id": int,
            "name": string,
        }, 
        "quantity": float
    }]
}
```

En cas d’erreur

```json
status: 404
data : {
    Object error, 
}
```

</td></tr><tr><td>
GET</td><td>/receipe/search?name=[nomReceipe]&ingredientIds=[idIngredient1],[idIngredient2]...</td><td>Récupère une recette par son nom et/ou ses ingrédients (si des ingrédients sont spécifiés, un seul ingrédient doit match pour passer)</td><td><td>En cas de succès

```json
status: 200
data : [{
    "id": int,
    "name": string,
    "image": string,
    "category": {
        "id": int,
        "name": string
    },
    "rating": float,
    "estimatedTime": int,
}]
```

En cas d’erreur

```json
status: 404
data : {
    Object error, 
}
```

</td></tr><tr><td>
POST</td><td>/ingredient/create</td><td>Crée un ingrédient.</td><td>

```json
{
    "nom": string,
}
```

</td><td>En cas de succès

```json
status: 200
data : {
    "id": string,
    "name": string,
}
```
</td></tr><tr><td>
POST</td><td>/ingredient/getAll</td><td>Récupère tous les ingrédients.</td><td>
</td><td>En cas de succès

```json
status: 200
[{
    "id": string,
    "name": string,
}]
```
</td></tr><tr><td>
GET</td><td>/ingredient/search?name=[nomIngredient]</td><td>Recherche un ingrédient.</td><td>

</td><td>En cas de succès

```json
status: 200
[{
    "id": string,
    "name": string,
}]
```

En cas d’erreur
```json
status: 404
```

</td></tr><tr><td>
POST</td><td>/favorite/add/</td><td>Ajouter un favori</td><td>

```json
{
    "token": string,
    "idReceipe": int,
}
```

</td><td>En cas de succès

```json
status: 200
{
    "users": User,
    "receipe": Receipe,
}
```

En cas d’erreur

```json
status: 400
```

</td></tr><tr><td>
POST</td><td>/favorite/getAll</td><td>Voir mes favoris</td><td>

```json
{
    "token": string
}
```

</td><td>En cas de succès

```json
status: 200
[{
"users": User,
"receipe": Receipe,
}]
```

</td></tr><tr><td>
POST</td><td>/favorite/isFavorite</td><td>Vérifie si une recette est en favori</td><td>

```json
{
    "token": string,
    "idReceipe": int,
}
```

</td><td>En cas de succès

```json
status: 200
data : {
    boolean: doesReceipeExists,
}
```

</td></tr><tr><td>
POST</td><td>/favorite/remove</td><td>Supprimer un favori</td><td>

```json
{
    "token": string,
    "idReceipe": int,
}
```

</td><td>En cas de succès

```json
status: 200
data : {
    boolean: true,
}
```

En cas d’erreur

```json
status: 400
```

</td></tr><tr><td>
POST</td><td>/friend/add</td><td>Ajouter un ami</td><td>

```json
{
  "token": string,
  "idUserToAdd": string
}
```

</td><td>En cas de succès

```json
status: 200
data : {
    boolean: true
}
```

En cas d’erreur

```json
status: 400
```

</td></tr><tr><td>
POST</td><td>/friend/getAll</td><td>Voir tes amis</td><td>

```json
{
  "token": string
}
```

</td><td>En cas de succès

```json
status: 200
[{
    "user1": User,
    "user2": User,
    "status": "PENDING" | "ACCEPTED"
}]
```

En cas d’erreur

```json
status: 400
```

</td></tr><tr><td>
POST</td><td>/friend/validate</td><td>Valide une demande d'ami</td><td>

```json
{
  "token": string,
  "idFriendInviteToRespond": int
}
```

</td><td>En cas de succès

```json
status: 200
data : {
    boolean: true,
}
```

En cas d’erreur

```json 
status : 400
```

</td></tr><tr><td>
POST</td><td>/friend/refuse</td><td>Refuse une demande d'ami</td><td>

```json
{
  "token": string,
  "idFriendInviteToRespond": int
}
```

</td><td>En cas de succès

```json
status: 200
data : {
    boolean: true,
}
```

En cas d’erreur

```json 
status : 400
```

</td></tr><tr><td>
POST</td><td>/friend/remove</td><td>Supprimer un ami</td><td>

```json
{
  "token": string,
  "idFriendToRemove": int
}
```

</td><td>En cas de succès

```json
status: 200
data : {
  boolean: true,
}
```

En cas d’erreur

```json
status: 400
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

</td></tr><tr><td><td></td><td><span style="color:red">
Envoie un rappel pour liste de course (côté serveur uniquement, ce n’est pas une route accessible)</span></td><td>

```json
{
    String phone,
    Int idShoppingList,
}
```

</td><td>
</tr><tr><td>
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

</td></tr>
</table>