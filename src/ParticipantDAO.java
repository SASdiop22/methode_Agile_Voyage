import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParticipantDAO {
    final static String URL = "jdbc:mysql://localhost:3306/stocks?serverTimezone=UTC";
    final static String LOGIN = "root";
    final static String PASS = "";

    public ParticipantDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Driver MySQL
        } catch (ClassNotFoundException e) {
            System.err.println("Impossible de charger le pilote MySQL !");
            e.printStackTrace();
        }
    }

    // Ajouter un participant à une activité
    public int ajouterParticipant(int utilisateurId, int activiteId) {
        Connection con = null;
        PreparedStatement ps = null;
        int retour = 0;

        try {
            con = DriverManager.getConnection(URL, LOGIN, PASS);
            ps = con.prepareStatement("INSERT INTO participants (utilisateur_id, activite_id) VALUES (?, ?)");
            ps.setInt(1, utilisateurId);
            ps.setInt(2, activiteId);

            retour = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception t) {}
            try { if (con != null) con.close(); } catch (Exception t) {}
        }
        return retour;
    }

    // Récupérer la liste des participants d'une activité
    public List<Utilisateur> getParticipants(int activiteId) {
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
            ps.setInt(1, activiteId);
            rs = ps.executeQuery();

            while (rs.next()) {
                participants.add(new Utilisateur(
                    rs.getInt("id"),
                    rs.getString("pseudo"),
                    rs.getString("email"),
                    "", // mot_de_passe non récupéré pour des raisons de sécurité
                    rs.getString("bio"),
                    rs.getString("photo_profil")
                ));
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

        // Ajouter un utilisateur à une activité
        int ajout = dao.ajouterParticipant(1, 2);
        System.out.println(ajout + " utilisateur ajouté à l'activité");

        // Récupérer la liste des participants d'une activité
        List<Utilisateur> liste = dao.getParticipants(2);
        for (Utilisateur u : liste) {
            System.out.println(u);
        }
    }
}
