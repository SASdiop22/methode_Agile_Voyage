import java.util.ArrayList;
import java.util.List;

public class Activite {
    // Déclaration des variables d'instance (attributs) de la classe Activite
    private int id; // Identifiant unique pour l'activité
    String titre; // Titre de l'activité
    String description; // Description de l'activité
    String image; // URL ou chemin de l'image associée à l'activité
    String categorie; // Catégorie de l'activité (par exemple, sport, culture, etc.)
    Utilisateur auteur; // Auteur de l'activité (objet de type Utilisateur)
    private int nombresLikes; // Nombre de "likes" reçus par l'activité
    List<Utilisateur> interesses; // Liste des utilisateurs intéressés par l'activité
    List<Utilisateur> likes; // Liste des utilisateurs ayant aimé l'activité
    
    // Constructeur avec l'ID, titre, description, image, catégorie, et auteur
    public Activite(int id, String titre, String description, String image, String categorie, Utilisateur auteur) {
        super();
        this.id = id; // Initialisation de l'ID
        this.titre = titre; // Initialisation du titre
        this.description = description; // Initialisation de la description
        this.image = image; // Initialisation de l'image
        this.categorie = categorie; // Initialisation de la catégorie
        this.auteur = auteur; // Initialisation de l'auteur
        this.nombresLikes = 0; // Initialisation du nombre de likes à 0
        this.interesses = new ArrayList<>(); // Initialisation de la liste des utilisateurs intéressés
        this.likes = new ArrayList<>(); // Initialisation de la liste des utilisateurs ayant aimé
    }

    // Constructeur sans l'ID, mais avec les autres attributs
    public Activite(String titre, String description, String image, String categorie, Utilisateur auteur) {
        super();
        this.titre = titre;
        this.description = description;
        this.image = image;
        this.categorie = categorie;
        this.auteur = auteur;
        this.nombresLikes = 0; // Initialisation du nombre de likes à 0
        this.interesses = new ArrayList<>(); // Initialisation de la liste des utilisateurs intéressés
        this.likes = new ArrayList<>(); // Initialisation de la liste des utilisateurs ayant aimé
    }

    // Getter pour l'ID
    public int getId() {
        return id;
    }

    // Getter pour le titre
    public String getTitre() {
        return titre;
    }

    // Setter pour le titre
    public void setTitre(String titre) {
        this.titre = titre;
    }

    // Getter pour la description
    public String getDescription() {
        return description;
    }

    // Setter pour la description
    public void setDescription(String description) {
        this.description = description;
    }

    // Getter pour l'image
    public String getImage() {
        return image;
    }

    // Setter pour l'image
    public void setImage(String image) {
        this.image = image;
    }

    // Getter pour la catégorie
    public String getCategorie() {
        return categorie;
    }

    // Setter pour la catégorie
    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    // Getter pour l'auteur
    public Utilisateur getAuteur() {
        return auteur;
    }

    // Setter pour l'auteur
    public void setAuteur(Utilisateur auteur) {
        this.auteur = auteur;
    }

    // Getter pour le nombre de likes
    public int getNombresLikes() {
        return nombresLikes;
    }

    // Méthode pour incrémenter le nombre de likes
    public void incrementerLikes() {
        this.nombresLikes++;
    }

    // Méthode pour décrémenter le nombre de likes (uniquement si > 0)
    public void decrementerLikes() {
        if (this.nombresLikes > 0) {
            this.nombresLikes--;
        }
    }

    // Getter pour la liste des utilisateurs intéressés
    public List<Utilisateur> getInteresses() {
        return interesses;
    }

    // Getter pour la liste des utilisateurs ayant aimé
    public List<Utilisateur> getLikes() {
        return likes;
    }

    // Méthode pour ajouter un utilisateur à la liste des intéressés
    public void ajoutInteresses(Utilisateur U) {
        if (!this.interesses.contains(U)) { // Vérifie si l'utilisateur n'est pas déjà intéressé
            this.interesses.add(U); // Ajoute l'utilisateur à la liste des intéressés
            U.ajouterInteret(this); // Ajoute cette activité à la liste des intérêts de l'utilisateur
        }
    }

    // Méthode pour ajouter un utilisateur à la liste des likes
    public void ajoutLike(Utilisateur U) {
        if (!this.likes.contains(U)) { // Vérifie si l'utilisateur n'a pas déjà aimé l'activité
            this.likes.add(U); // Ajoute l'utilisateur à la liste des likes
            this.incrementerLikes(); // Incrémente le nombre de likes
            U.ajouterLike(this); // Ajoute cette activité à la liste des likes de l'utilisateur
        }
    }
    
 // Méthode pour retirer un utilisateur de la liste des intéressés
    public void retirerInteresses(Utilisateur U) {
        if (this.interesses.contains(U)) { // Vérifie si l'utilisateur est dans la liste des intéressés
            this.interesses.remove(U); // Retire l'utilisateur de la liste des intéressés
            U.supprimerInteret(this); // Retire cette activité de la liste des intérêts de l'utilisateur
        }
    }

    // Méthode pour retirer un utilisateur de la liste des likes
    public void retirerLikes(Utilisateur U) {
        if (this.likes.contains(U)) { // Vérifie si l'utilisateur est dans la liste des likes
            this.likes.remove(U); // Retire l'utilisateur de la liste des likes
            this.decrementerLikes(); // Décrémente le nombre de likes
            U.supprimerLike(this); // Retire cette activité de la liste des likes de l'utilisateur
        }
    }
}
