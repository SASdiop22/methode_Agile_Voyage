import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;

import java.sql.ResultSet;

public class GestionnaireActivites {
    // Liste pour stocker les activités gérées par ce gestionnaire
    private List<Activite> activites;

    private Utilisateur utilisateurConnecte;  // L'utilisateur connecté

    public GestionnaireActivites() {
        this.activites = new ArrayList<>();
        this.utilisateurConnecte = null; // Initialement, aucun utilisateur connecté
    }

    public List<Activite> getActivites() {
		return activites;
	}
   


    public void ajouterActivite(Activite activite) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            String sql = "INSERT INTO activites (titre, description, image_url, categorie, utilisateur_id, ville, prix) VALUES (?, ?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, activite.getTitre());
            stmt.setString(2, activite.getDescription());
            stmt.setString(3, activite.getImage());
            stmt.setString(4, activite.getCategorie());
            stmt.setInt(5, activite.getAuteur().getId());
            stmt.setString(6, activite.getVille());
            stmt.setDouble(7, activite.getPrix());

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                // R�cup�rer l'ID g�n�r�
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int newId = generatedKeys.getInt(1);
                    activite.setId(newId);  // Met � jour l'ID de l'objet
                }
                activites.add(activite);  // Ajoute l'activit� � la liste en m�moire
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.close(conn, stmt, null);
        }
    }


	

    // Méthode pour supprimer une activité de la liste des activités
    public void supprimeActivite(int activiteId) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            String sql = "DELETE FROM activites WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, activiteId);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                // Supprime l'activit� de la liste en m�moire
                activites.removeIf(activite -> activite.getId() == activiteId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.close(conn, stmt, null);
        }
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
    public List<Activite> filtrerActivites(String categorie) {
        List<Activite> resultats = new ArrayList<>(); // Liste pour stocker les résultats du filtrage
        for (Activite activite : activites) {
            // Vérifie si l'activité appartient à la catégorie spécifiée (comparaison insensible à la casse)
            if (activite.getCategorie().equalsIgnoreCase(categorie)) {
                resultats.add(activite); // Si oui, ajoute l'activité à la liste des résultats
            }
        }
        return resultats; // Retourne la liste des activités filtrées
    }
    public void chargerActivitesDepuisBD() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM activites"; 
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            PreparedStatement ps = null;
            while (rs.next()) {
                int id = rs.getInt("id");
                String titre = rs.getString("titre");
                String description = rs.getString("description");
                String image = rs.getString("image_url");
                String categorie = rs.getString("categorie");
                String ville = rs.getString("ville");
                int prix = rs.getInt("prix");
                int idUtilisateur = rs.getInt("utilisateur_id");

                // R�cup�rer l'utilisateur li� � l'activit�
                Utilisateur auteur = null; 
                ps = conn.prepareStatement("SELECT * FROM utilisateurs WHERE id = ?");
                ps.setInt(1, id);
                rs = ps.executeQuery();

                if (rs.next()) {
                	auteur = new Utilisateur(
                        rs.getInt("id"),
                        rs.getString("pseudo"),
                        rs.getString("email"),
                        rs.getString("mot_de_passe"),
                        rs.getString("bio"),
                        rs.getString("photo_profil")
                    );
                }

                // Cr�er l'activit� et l'ajouter � la liste
                Activite activite = new Activite(id, titre, description, image, categorie, auteur, ville, prix);
                this.ajouterActivite(activite);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<Utilisateur> getUtilisateursConnectes() {
        List<Utilisateur> utilisateursConnectes = new ArrayList<>();
        try (Connection con = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM utilisateurs WHERE isConnected = TRUE";
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Utilisateur utilisateur = new Utilisateur(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("email"),
                    rs.getString("mot_de_passe"),
                    rs.getString("bio"),
                    rs.getString("photo")
                );
                utilisateursConnectes.add(utilisateur);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return utilisateursConnectes;
    }

    public Utilisateur getUtilisateurConnecte() {
        return utilisateurConnecte;
    }

    // Méthode pour vérifier si un utilisateur est connecté
    public boolean isUtilisateurConnecte() {
        return utilisateurConnecte != null;
    }
    


    // Méthode pour définir l'utilisateur connecté
    public void setUserConnected(int utilisateurId, boolean isConnected) {
        if (isConnected) {
            this.utilisateurConnecte = findUtilisateurById(utilisateurId);
        } else {
            this.utilisateurConnecte = null; // Si l'utilisateur se déconnecte
        }
    }

    // Méthode pour trouver un utilisateur par ID
    private Utilisateur findUtilisateurById(int utilisateurId) {
        Utilisateur utilisateur = null;
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM utilisateurs WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, utilisateurId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                utilisateur = new Utilisateur(
                    rs.getInt("id"),
                    rs.getString("pseudo"),
                    rs.getString("email"),
                    rs.getString("mot_de_passe"),
                    rs.getString("bio"),
                    rs.getString("photo_profil")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return utilisateur;
    }

}
