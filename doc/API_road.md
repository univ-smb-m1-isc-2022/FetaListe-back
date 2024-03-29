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

</td></tr><tr><td>GET</td><td>/user/getAll</td><td>Récupère tous les utilisateurs</td><td>
</td><td>

```json
status: 200
data : {
    "id": int,
    "name": string,
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
GET</td><td>/receipe/search?name=[nom]</td><td>Récupère une recette par son nom / le nom d'un de ses ingrédients</td><td><td>En cas de succès

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
  "idUserToAdd": int
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
POST</td><td>/shop/create</td><td>Crée une nouvelle liste de courses</td><td>

```json
{
    "token": string,
    "reminderDate": int
}
```
</td><td>En cas de succès

```json
status: 200
{
    "id": string,
    "user": User,
    "owner": User,
    "maxBuyDate": int,
}
```

En cas d’erreur

```json
status: 400
```

</td></tr><tr><td>
POST</td><td>/shop/getAll</td><td>Récupère mes listes de courses</td><td>

```json
{
  "token": string
}
```

</td><td>En cas de succès

```json
status: 200
[{
    "id": string,
    "user": User,
    "owner": User,
    "maxBuyDate": int,
}]
```

</td></tr><tr><td>
POST</td><td>/shop/getById</td><td>Récupère une de mes listes de courses par ID</td><td>

```json
{
  "token": string,
  "idShoppingList": int
}
```

</td><td>En cas de succès

```json
status: 200
{
    "sl": {
        "id": string,
        "user": User,
        "owner": User,
        "maxBuyDate": int,
    },
    "rsl": [{
        "receipe": Receipe,
        "shoppingList": ShoppingList
    }],
    "sli": [{
        "shoppingList": ShoppingList,
        "ingredient": Ingredient,
        "unit": {
            "id": int,
            "name": string
        },
        "quantity": float
    }]
}
```

En cas d’erreur

```json
status: 400
```

</td></tr><tr><td>
POST</td><td>/shop/edit</td><td>Modifie une liste de courses (ajoute / supprime des recettes pour tant de personnes)</td><td>

```json
{
  "token": string,
  "idShoppingList": int,
  "add": boolean,
  "idsReceipes": [int],
  "nbPersonnes": int
}
```

</td><td>En cas de succès

```json
status: 200
{
    "sl": {
        "id": string,
        "user": User,
        "owner": User,
        "maxBuyDate": int,
    },
    "rsl": [{
        "receipe": Receipe,
        "shoppingList": ShoppingList
    }],
    "sli": [{
        "shoppingList": ShoppingList,
        "ingredient": Ingredient,
        "unit": {
            "id": int,
            "name": string
        },
        "quantity": float
    }]
}
```

En cas d’erreur

```json
status: 400
```

</td></tr><tr><td>
POST</td><td>/shop/remove</td><td>Supprime une de mes listes de courses</td><td>

```json
{
  "token": string,
  "idShoppingList": int
}
```

</td><td>En cas de succès

```json
status: 200
{
    boolean: true
}
```

En cas d’erreur

```json
status: 400
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

</td></tr><tr><td>
POST</td><td>/msg/send</td><td>Envoyer une liste de course par sms à un ami ou à soi-même</td><td>

```json
{
  "token": string,
  "idSLToShare": int,
  "idUserToSend": int
}
```


</td><td>En cas de succès

```json
status: 200
data : {
  string: "OK",
}
```

En cas d’erreur

```json
status: 400
data : {
  string: ErrorResume
}
```

</td></tr><tr><td>
POST</td><td>/msg/share</td><td>Partager liste de courses à un amis (via appli)</td><td>

```json
{
  "token": string,
  "idSLToShare": int,
  "idUserToSend": int
}
```
</td><td>En cas de succès

```json
status: 200
data : {
  string: "OK",
}
```
En cas d’erreur

```json
status: 400
data : {
  string: ErrorResume
}
```

</td></tr><tr><td>
POST</td><td>/substitute/add</td><td>Crée un substitut pour un ingrédient</td><td>

```json
{
  "idIngredient": int,
  "idIngredientToSubtitute": int
}
```
</td><td>En cas de succès

```json
status: 200
data : {
  "baseIngredient": Ingredient,
  "substitute": Ingredient,
}
```
En cas d’erreur

```json
status: 400
```

</td></tr><tr><td>
GET</td><td>/substitute/getAllOf/{ingredientId}</td><td>Récupère tous les substituts pour un ingrédient donné</td><td>
</td><td>En cas de succès

```json
status: 200
data : [{
  "baseIngredient": Ingredient,
  "substitute": Ingredient,
}]
```

</td></tr><tr><td>
POST</td><td>/substitute/remove</td><td>Supprime un substitut pour un ingrédient</td><td>

```json
{
  "idIngredient": int,
  "idIngredientToSubtitute": int
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

</td></tr><tr><td><td></td><td><span style="color:red">
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