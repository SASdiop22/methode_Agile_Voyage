import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurDAO {

	final static String URL = "jdbc:mysql://localhost:3306/stocks?serverTimezone=UTC";
	final static String LOGIN = "root";  // phpMyAdmin utilise "root"
	final static String PASS = "";  // Pas de mot de passe par défaut sous WAMP

    public UtilisateurDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  // MySQL Driver
        } catch (ClassNotFoundException e) {
            System.err.println("Impossible de charger le pilote MySQL !");
            e.printStackTrace();
        }
    }

    // Ajouter un utilisateur
    public int ajouter(Utilisateur user) {
        Connection con = null;
        PreparedStatement ps = null;
        int retour = 0;

        try {
            con = DriverManager.getConnection(URL, LOGIN, PASS);
            ps = con.prepareStatement("INSERT INTO utilisateurs (pseudo, email, mot_de_passe, bio, photo_profil) VALUES (?, ?, ?, ?, ?)");
            ps.setString(1, user.getPseudo());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getMotDePasse());
            ps.setString(4, user.getBio());
            ps.setString(5, user.getPhotoProfil());

            retour = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception t) {}
            try { if (con != null) con.close(); } catch (Exception t) {}
        }
        return retour;
    }

    // Récupérer un utilisateur par son ID
    public Utilisateur getUtilisateur(int id) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Utilisateur retour = null;

        try {
            con = DriverManager.getConnection(URL, LOGIN, PASS);
            ps = con.prepareStatement("SELECT * FROM utilisateurs WHERE id = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                retour = new Utilisateur(
                    rs.getInt("id"),
                    rs.getString("pseudo"),
                    rs.getString("email"),
                    rs.getString("mot_de_passe"),
                    rs.getString("bio"),
                    rs.getString("photo_profil")
                );
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

    // Récupérer tous les utilisateurs
    public List<Utilisateur> getListeUtilisateurs() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Utilisateur> liste = new ArrayList<>();

        try {
            con = DriverManager.getConnection(URL, LOGIN, PASS);
            ps = con.prepareStatement("SELECT * FROM utilisateurs");
            rs = ps.executeQuery();

            while (rs.next()) {
                liste.add(new Utilisateur(
                    rs.getInt("id"),
                    rs.getString("pseudo"),
                    rs.getString("email"),
                    rs.getString("mot_de_passe"),
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
        return liste;
    }

    public static void main(String[] args) {
        UtilisateurDAO dao = new UtilisateurDAO();

        // Ajouter un utilisateur
        Utilisateur user = new Utilisateur("JohnDoe", "john@example.com", "password123", "Voyageur passionné", "john.jpg");
        int retour = dao.ajouter(user);
        System.out.println(retour + " utilisateur ajouté.");

        // Récupérer un utilisateur
        Utilisateur u = dao.getUtilisateur(1);
        System.out.println(u);

        // Lister tous les utilisateurs
        List<Utilisateur> utilisateurs = dao.getListeUtilisateurs();
        for (Utilisateur utilisateur : utilisateurs) {
            System.out.println(utilisateur);
        }
    }
}
