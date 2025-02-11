import java.util.ArrayList;
import java.util.List;

public class GestionnaireActivites {
    // Liste pour stocker les activités gérées par ce gestionnaire
    private List<Activite> activites;

    // Constructeur de la classe, initialise la liste des activités
    public GestionnaireActivites() {
        this.activites = new ArrayList<>();
    }

    // Méthode pour ajouter une activité à la liste des activités
    public void ajouterActivite(Activite activite) {
        activites.add(activite); // Ajoute l'activité à la liste
    }

    // Méthode pour supprimer une activité de la liste des activités
    public void supprimerActivite(Activite activite) {
        activites.remove(activite); // Supprime l'activité de la liste
    }

    // Méthode pour rechercher des activités par mot-clé (dans le titre ou la description)
    public List<Activite> rechercherActivite(String motCle) {
        List<Activite> resultats = new ArrayList<>(); // Liste pour stocker les résultats de la recherche
        for (Activite activite : activites) {
            // Vérifie si le mot-clé est présent dans le titre ou la description de l'activité
            if (activite.getTitre().contains(motCle) || activite.getDescription().contains(motCle)) {
                resultats.add(activite); // Si oui, ajoute l'activité à la liste des résultats
            }
        }
        return resultats; // Retourne la liste des activités trouvées
    }

    // Méthode pour filtrer les activités par catégorie
    public List<Activite> filtrerActivitesParCategorie(String categorie) {
        List<Activite> resultats = new ArrayList<>(); // Liste pour stocker les résultats du filtrage
        for (Activite activite : activites) {
            // Vérifie si l'activité appartient à la catégorie spécifiée (comparaison insensible à la casse)
            if (activite.getCategorie().equalsIgnoreCase(categorie)) {
                resultats.add(activite); // Si oui, ajoute l'activité à la liste des résultats
            }
        }
        return resultats; // Retourne la liste des activités filtrées
    }

    // Méthode pour filtrer les activités par ville
    public List<Activite> filtrerActivitesParVille(String ville) {
        List<Activite> resultats = new ArrayList<>(); // Liste pour stocker les résultats du filtrage
        for (Activite activite : activites) {
            // Vérifie si l'activité a lieu dans la ville spécifiée
            if (activite.getVille().equalsIgnoreCase(ville)) {
                resultats.add(activite); // Si oui, ajoute l'activité à la liste des résultats
            }
        }
        return resultats; // Retourne la liste des activités filtrées
    }

    // Méthode pour filtrer les activités par prix (exact)
    public List<Activite> filtrerActivitesParPrix(double prix) {
        List<Activite> resultats = new ArrayList<>(); // Liste pour stocker les résultats du filtrage
        for (Activite activite : activites) {
            // Vérifie si le prix de l'activité correspond au prix spécifié
            if (activite.getPrix() == prix) {
                resultats.add(activite); // Si oui, ajoute l'activité à la liste des résultats
            }
        }
        return resultats; // Retourne la liste des activités filtrées
    }

    // Méthode pour filtrer les activités par plage de prix
    public List<Activite> filtrerActivitesParPlagePrix(double prixMin, double prixMax) {
        List<Activite> resultats = new ArrayList<>(); // Liste pour stocker les résultats du filtrage
        for (Activite activite : activites) {
            // Vérifie si le prix de l'activité est dans la plage spécifiée
            if (activite.getPrix() >= prixMin && activite.getPrix() <= prixMax) {
                resultats.add(activite); // Si oui, ajoute l'activité à la liste des résultats
            }
        }
        return resultats; // Retourne la liste des activités filtrées
    }
}
