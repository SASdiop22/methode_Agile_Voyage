import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class ConnexionFenetre extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;

    private JTextField textFieldEmail;
    private JPasswordField textFieldMotDePasse;
    private JButton boutonSeConnecter, boutonCreerCompte;

    public ConnexionFenetre() {
        this.setTitle("Connexion");
        this.setSize(350, 250);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel labelEmail = new JLabel("Email :");
        JLabel labelMotDePasse = new JLabel("Mot de passe :");

        textFieldEmail = new JTextField();
        textFieldMotDePasse = new JPasswordField();

        boutonSeConnecter = new JButton("Se connecter");
        boutonCreerCompte = new JButton("Créer un compte");

        boutonSeConnecter.addActionListener(this);
        boutonCreerCompte.addActionListener(this);

        panel.add(labelEmail);
        panel.add(textFieldEmail);
        panel.add(labelMotDePasse);
        panel.add(textFieldMotDePasse);
        panel.add(boutonSeConnecter);
        panel.add(boutonCreerCompte);

        this.setContentPane(panel);
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == boutonSeConnecter) {
            verifierConnexion();
        } else if (ae.getSource() == boutonCreerCompte) {
            new CreationCompteFenetre();
        }
    }

    private void verifierConnexion() {
        String email = textFieldEmail.getText();
        String motDePasse = new String(textFieldMotDePasse.getPassword());

        try (Connection con = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM utilisateurs WHERE email = ? AND mot_de_passe = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, email);
            pst.setString(2, motDePasse);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Connexion réussie !");
                this.dispose(); // Fermer la fenêtre
            } else {
                JOptionPane.showMessageDialog(this, "Email ou mot de passe incorrect.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ConnexionFenetre();
    }
}
