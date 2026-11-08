# Correspondances

Une petite API pour gérer des **réservations de train** : trains, voitures, places, voyageurs, tarifs et réservations.

## Ce qu'il faut installer

- Java 21
- PostgreSQL
- Postman (pour tester)

## Lancer le projet

**1. Créer la base de données** (dans pgAdmin, Query Tool) :

```sql
CREATE DATABASE correspondances;
```

**2. Mettre son mot de passe** dans `src/main/resources/application.properties` :

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/correspondances
spring.datasource.username=postgres
spring.datasource.password=VOTRE_MOT_DE_PASSE
server.port=8081
```

**3. Lancer l'application** : dans IntelliJ, lancer la classe `CorrespondancesApplication`.

**4. Vérifier** : attendre `Started CorrespondancesApplication` dans la console.
Au premier lancement, les tables sont créées toutes seules.

## Tester l'API

Dans Postman, faire un **GET** sur :

```
http://localhost:8081/correspondances/train
```

Si la réponse est **200**, c'est que tout fonctionne.

Les autres adresses sont construites de la même façon :
`/service-calendar`, `/service-exception`, `/train`, `/circulation`, `/traveler-profile`, `/traveler`, `/travel-class`, `/coach`, `/seat`, `/fare-period`, `/distance-band`, `/fare`, `/booking`.

| Action | Méthode | Exemple |
|---|---|---|
| Voir toutes les lignes | GET | `/train` |
| Voir une ligne | GET | `/train/1` |
| Créer | POST | `/traveler` avec un JSON dans le Body |
| Modifier | PUT | `/traveler/1` avec un JSON dans le Body |
| Supprimer | DELETE | `/traveler/1` |

Exemple de JSON pour `POST /traveler` :

```json
{
  "firstName": "Marie",
  "lastName": "Dupont",
  "email": "marie@mail.com",
  "phone": "0600000000"
}
```

## Les dossiers du projet

| Dossier | À quoi il sert |
|---|---|
| `Controller` | Reçoit les requêtes (les adresses de l'API) |
| `Service` | Contient la logique |
| `Repository` | Parle à la base de données |
| `Entity` | Une classe par table |
| `Dto` | Le contenu des JSON reçus |
| `resources/db/migration` | Les scripts SQL qui créent les tables |

Le chemin d'une requête : Controller, puis Service, puis Repository, puis la base.

## Si ça ne marche pas

| Problème | Solution |
|---|---|
| 404 dans Postman | Vérifier que l'adresse commence par `/correspondances` |
| `database does not exist` | Vérifier que la base s'appelle bien `correspondances`, en minuscules |
| Erreur de mot de passe | Vérifier le mot de passe dans `application.properties` |
| L'application ne démarre pas | Lire la dernière ligne `Caused by:` dans la console |
