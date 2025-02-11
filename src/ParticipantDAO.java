import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParticipantDAO {
    final static String URL = "jdbc:mysql://localhost:3306/stocks?serverTimezone=UTC";
    final static String LOGIN = "root";
    final static String PASS = "";

    public ParticipantDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Chargement du driver MySQL
        } catch (ClassNotFoundException e) {
            System.err.println("Impossible de charger le pilote MySQL !");
            e.printStackTrace();
        }
    }

    // Ajouter un utilisateur comme intéressé par une activité
    public int ajouterParticipant(Utilisateur utilisateur, Activite activite) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int retour = 0;

        try {
            con = DriverManager.getConnection(URL, LOGIN, PASS);

            // Vérifier si l'utilisateur est déjà inscrit à l'activité
            ps = con.prepareStatement("SELECT COUNT(*) FROM participants WHERE utilisateur_id = ? AND activite_id = ?");
            ps.setInt(1, utilisateur.getId());
            ps.setInt(2, activite.getId());
            rs = ps.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                System.out.println("⚠ L'utilisateur est déjà inscrit à cette activité.");
                return 0; // Aucun ajout effectué
            }

            // Inscrire l'utilisateur à l'activité
            ps = con.prepareStatement("INSERT INTO participants (utilisateur_id, activite_id) VALUES (?, ?)");
            ps.setInt(1, utilisateur.getId());
            ps.setInt(2, activite.getId());
            retour = ps.executeUpdate();

            if (retour > 0) {
                activite.ajoutInteresses(utilisateur); // Mise à jour de la liste des intéressés
                utilisateur.ajouterInteret(activite);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception t) {}
            try { if (ps != null) ps.close(); } catch (Exception t) {}
            try { if (con != null) con.close(); } catch (Exception t) {}
        }
        return retour;
    }

    // Retirer un utilisateur d'une activité
    public int retirerParticipant(Utilisateur utilisateur, Activite activite) {
        Connection con = null;
        PreparedStatement ps = null;
        int retour = 0;

        try {
            con = DriverManager.getConnection(URL, LOGIN, PASS);
            ps = con.prepareStatement("DELETE FROM participants WHERE utilisateur_id = ? AND activite_id = ?");
            ps.setInt(1, utilisateur.getId());
            ps.setInt(2, activite.getId());

            retour = ps.executeUpdate();

            if (retour > 0) {
                activite.retirerInteresses(utilisateur); // Mise à jour de la liste des intéressés
                utilisateur.supprimerInteret(activite);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception t) {}
            try { if (con != null) con.close(); } catch (Exception t) {}
        }
        return retour;
    }

    // Récupérer la liste des utilisateurs intéressés par une activité
    public List<Utilisateur> getParticipants(Activite activite) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Utilisateur> participants = new ArrayList<>();

        try {
            con = DriverManager.getConnection(URL, LOGIN, PASS);
            ps = con.prepareStatement(
                "SELECT u.id, u.pseudo, u.email, u.bio, u.photo_profil " +
                "FROM participants p JOIN utilisateurs u ON p.utilisateur_id = u.id " +
                "WHERE p.activite_id = ?"
            );
            ps.setInt(1, activite.getId());
            rs = ps.executeQuery();

            while (rs.next()) {
                Utilisateur utilisateur = new Utilisateur(
                    rs.getInt("id"),
                    rs.getString("pseudo"),
                    rs.getString("email"),
                    "", // On ne récupère pas le mot de passe pour des raisons de sécurité
                    rs.getString("bio"),
                    rs.getString("photo_profil")
                );
                participants.add(utilisateur);
                activite.ajoutInteresses(utilisateur); // Mise à jour de l'objet Activite
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception t) {}
            try { if (ps != null) ps.close(); } catch (Exception t) {}
            try { if (con != null) con.close(); } catch (Exception t) {}
        }
        return participants;
    }

    public static void main(String[] args) {
        ParticipantDAO dao = new ParticipantDAO();
        Utilisateur utilisateur = new Utilisateur(1, "JohnDoe", "john@example.com", "password", "Voyageur passionné", "john.jpg");
        Activite activite = new Activite(2, "Randonnée en Alsace", "Explorez les montagnes", "rando.jpg", "Sport", utilisateur);

        // Ajouter un participant
        int ajout = dao.ajouterParticipant(utilisateur, activite);
        System.out.println(ajout + " utilisateur ajouté à l'activité");

        // Essayer d'ajouter le même utilisateur à la même activité
        ajout = dao.ajouterParticipant(utilisateur, activite);
        System.out.println(ajout + " utilisateur ajouté à l'activité (test doublon)");

        // Lister les participants
        List<Utilisateur> participants = dao.getParticipants(activite);
        System.out.println("Participants inscrits :");
        for (Utilisateur u : participants) {
            System.out.println("- " + u.getPseudo());
        }

        // Retirer un utilisateur
        //int suppression = dao.retirerParticipant(utilisateur, activite);
        //System.out.println(suppression + " utilisateur retiré de l'activité");

        // Vérifier après suppression
        //participants = dao.getParticipants(activite);
        //System.out.println("Participants après suppression :");
        for (Utilisateur u : participants) {
            System.out.println("- " + u.getPseudo());
        }
    }
}
