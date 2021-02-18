# Bienvenue dans notre Cookie Factory !

Projet réalisé par Loïc Bertolotto, Antoine Facq, Maëva Lecavelier, Alexandre Mazurier et Clément Poueyto dans le cadre du cours *Conception Logicielle* du Semestre 7 de SI4 à Polytech'Nice Sophia.


## Sommaire

 1. Epics choisies
 2. User Stories
 3. Les tests fonctionnels

Pour la suite, le terme *factory* désigne l'entreprise nationale qui regroupe tous les magasins.

# Epics choisies

Le choix de nos Epics a été défini par les fonctionnalités qui étaient demandées. Ainsi, le découpage de notre projet a été fait de la manière suivante : 

 - **Commander un cookie (personnalisé ou non)** : l'action de commander un cookie et que cette commande existe dans notre système.
 - **Cookies BestOf** : statistiques de commandes et identification des meilleures commandes à l'échelle d'un magasin ou nationale.
 - **Magasins** : gérer les stocks, les horaires, les pannes ou encore la position propre à un magasin
 - **Client** : gérer l'inscription, le nombre de cookies commandés ou encore la position d'un client enregistré ou non.
 - **Retrait de commande** : Vérifier que l'heure, le lieu, le magasin et la commande soient valides **afin de** pouvoir retirer une commande
 - **MarcelEat** : service de livraison **afin de** éviter aux clients de se rendre sur place **afin de** récupérer leur commande.

# User Stories

  

## Epic 1 : Commander un cookie (personnalisé ou non)

  
**En tant que** client, **je veux** créer ma recette personnalisée **afin de** profiter de ce service.

**En tant que** magasin/factory, **je veux** enregistrer cette recette personnalisée **afin de** trouver la recette bestOf

**En tant que** client, **je veux** savoir l’état de ma commande **afin de** m’organiser.

**En tant que** client, **je veux** indiquer l’heure et le lieu de retrait de ma commande, **afin de** m’organiser.

**En tant que** factory, **je veux** que le client paye **afin de** sécuriser et accélérer le paiement.

**En tant que** client, **je veux** connaître le prix de ma commande **afin de** savoir si j’ai eu des réductions.

**En tant que** client, **je veux** pouvoir choisir la taille de mon cookie (M, L, XL : dose) **afin de** profiter de ce service.

## Epic 2 : Cookies BestOf

 **En tant que** magasin/factory, **je veux**  savoir quel est mon meilleur cookie, **afin de** proposer une réduction de 10% sur ce cookie.

**En tant que** factory, **je veux**  récupérer les statistiques de tous les magasins pour les recettes prédéfinies **afin de** supprimer la recette la moins vendue.

## Epic 3 : Magasins

 **En tant que** manager, **je veux** rentrer les heures d'ouvertures de mon magasin, **afin que** les clients sachent à quelle heure ils peuvent venir.

**En tant que** magasin, **je veux**  voir la disponibilité des ingrédients, **afin de** ne pouvoir proposer que des cookies réalisables.

**En tant que** client, **je veux** obtenir la liste des magasins les plus proches de ma localisation, et leurs horaires, **afin de** choisir mon magasin de retrait

**En tant que** factory, **je veux** proposer au client une alternative de magasin **afin qu’** il trouve un magasin avec les ingrédients/horaire adéquat.

**En tant que** magasin, **je veux**  gérer mes stocks **afin de** proposer des produits à jour à mes clients.

**En tant que** magasin, **je veux**  connaître l’état de mes machines **afin d'** indiquer au client ma disponibilité.

**En tant que** magasin/factory, **je veux**  ajouter de nouvelles recettes **afin de** me renouveler pour les clients.

## Epic 4 : Clients

 **En tant que** client, **je veux**  m’inscrire **afin de** pouvoir m’abonner au loyalty program.

**En tant que** client, **je veux**  m’abonner **afin de** bénéficier des réductions du loyalty program.


## Epic 5 : Retrait de commande

**En tant qu'** employé, **je veux**  pouvoir scanner le ticket d'un client **afin de** pouvoir lui délivrer sa commande si celle-ci est prête.

## Epic 6 : MarcelEat

 **En tant que** client, **je veux**  demander une livraison de dernière minute **afin d'** être livré si je ne peux pas me rendre au magasin.

**En tant que** client, **je veux**  pouvoir préciser la livraison par MarceEat **afin d'** éviter de me déplacer.

# Les tests fonctionnels
Nous avons rédigé nos tests fonctionnels en plusieurs fichiers *feature* pour une meilleure lisibilité et orientation dans le code, mais aussi pour faciliter le travail en groupe. 
Ainsi, nos tests sont répartis de la façon suivante : 

 - *AddNewRecipe.feature* : permet de simuler l'ajout de nouvelles recettes au sein de la factory. Ce fichier est composé de trois scénarios : 
	- Un manager souhaite ajouter une nouvelle recette à la factory avec un seul topping
	- Un manager souhaite ajouter une nouvelle recette à la factory avec deux toppings
	- Un manager souhaite ajouter une nouvelle recette à la factory avec trois toppings
	
 - *OrderCookie.feature* : permet de simuler les différents cas d'utilisation lors d'une commande d'un cookie. Il n'est composé que d'un grand scénario qui est continu et de plus petits scénarios. Le grand scénario teste la commande d'un cookie en tant qu'invité ou client abonné au programme de fidélité, la commande de cookies personnalisés et vérifie que les stocks et les statistiques soient mis à jour au fur et à mesure. Ce fichier permet également les tests dans le cas d'un problème de panne ou de stock du magasin. Les autres scénarios se focussent sur la livraison ou encore sur la modification d'une recette classiques.

 - *ShopManaging.feature* : permet de simuler toutes les actions qui permettent de gérer le magasin : embauche d'un nouvel employé, regarder les statistiques, changer les horaires etc...
