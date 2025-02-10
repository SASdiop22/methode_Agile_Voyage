import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArticleDAO {

    // Paramètres de connexion MySQL
    final static String URL = "jdbc:mysql://localhost:3306/stocks";
    final static String LOGIN = "root"; // Change si nécessaire
    final static String PASS = ""; // Mot de passe MySQL (par défaut vide sur XAMPP/WAMP)

    // Constructeur : Charger le driver JDBC MySQL
    public ArticleDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Erreur de chargement du driver MySQL !");
            e.printStackTrace();
        }
    }

    // Ajouter un article
    public int ajouter(Article nouvArticle) {
        Connection con = null;
        PreparedStatement ps = null;
        int retour = 0;

        try {
            con = DriverManager.getConnection(URL, LOGIN, PASS);
            ps = con.prepareStatement("INSERT INTO article (reference, designation, pu_ht, qtestock) VALUES (?, ?, ?, ?)");
            ps.setInt(1, nouvArticle.getReference()); 
            ps.setString(2, nouvArticle.getDesignation());
            ps.setDouble(3, nouvArticle.getPuHt());
            ps.setInt(4, nouvArticle.getQteStock());

            retour = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
        return retour;
    }

    // Récupérer un article par sa référence
    public Article getArticle(int reference) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Article article = null;

        try {
            con = DriverManager.getConnection(URL, LOGIN, PASS);
            ps = con.prepareStatement("SELECT * FROM article WHERE reference = ?");
            ps.setInt(1, reference);
            rs = ps.executeQuery();

            if (rs.next()) {
                article = new Article(
                    rs.getInt("reference"),
                    rs.getString("designation"),
                    rs.getDouble("pu_ht"),
                    rs.getInt("qtestock")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
        return article;
    }

    // Récupérer tous les articles
    public List<Article> getListeArticles() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Article> liste = new ArrayList<>();

        try {
            con = DriverManager.getConnection(URL, LOGIN, PASS);
            ps = con.prepareStatement("SELECT * FROM article");
            rs = ps.executeQuery();

            while (rs.next()) {
                liste.add(new Article(
                    rs.getInt("reference"),
                    rs.getString("designation"),
                    rs.getDouble("pu_ht"),
                    rs.getInt("qtestock")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
        return liste;
    }

    // Test du DAO
    public static void main(String[] args) {
        ArticleDAO dao = new ArticleDAO();
        
        // Ajouter un article
        Article a = new Article("Set de 2 raquettes de ping-pong",149.9,10);
        int ajout = dao.ajouter(a);
        System.out.println(ajout + " ligne ajoutée.");

        // Récupérer un article
        Article art = dao.getArticle(1);
        System.out.println(art);

        // Afficher tous les articles
        List<Article> articles = dao.getListeArticles();
        for (Article article : articles) {
            System.out.println(article);
        }
    }
}
