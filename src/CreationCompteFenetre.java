import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class CreationCompteFenetre extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;

    private JTextField textFieldPseudo, textFieldPrenom, textFieldEmail;
    private JPasswordField textFieldMotDePasse;
    private JButton boutonCreerCompte;

    public CreationCompteFenetre() {
        this.setTitle("Création de compte");
        this.setSize(400, 250);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel labelNom = new JLabel("Pseudo :");
        //JLabel labelbio = new JLabel("Bio :");
        JLabel labelEmail = new JLabel("Email :");
        JLabel labelMotDePasse = new JLabel("Mot de passe :");

        textFieldPseudo = new JTextField();
        //textFieldPrenom = new JTextField();
        textFieldEmail = new JTextField();
        textFieldMotDePasse = new JPasswordField();

        boutonCreerCompte = new JButton("Créer un compte");
        boutonCreerCompte.addActionListener(this);

        panel.add(labelNom);
        panel.add(textFieldPseudo);
        //panel.add(labelbio);
        //panel.add(textFieldPrenom);
        panel.add(labelEmail);
        panel.add(textFieldEmail);
        panel.add(labelMotDePasse);
        panel.add(textFieldMotDePasse);
        panel.add(boutonCreerCompte);

        this.setContentPane(panel);
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == boutonCreerCompte) {
            enregistrerUtilisateur();
        }
    }

    private void enregistrerUtilisateur() {
        String pseudo = textFieldPseudo.getText();
        //String prenom = textFieldPrenom.getText();
        String email = textFieldEmail.getText();
        String motDePasse = new String(textFieldMotDePasse.getPassword());

        try (Connection con = DatabaseConnection.getConnection()) {
            // Vérifier si l'email existe déjà
            String checkQuery = "SELECT * FROM utilisateurs WHERE email = ?";
            PreparedStatement checkPst = con.prepareStatement(checkQuery);
            checkPst.setString(1, email);
            ResultSet rs = checkPst.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Cet email est déjà utilisé !");
            } else {
                // Insérer le nouvel utilisateur (id est auto-incrémenté)
                String insertQuery = "INSERT INTO utilisateurs (pseudo, email, mot_de_passe) VALUES (?, ?, ?)";
                PreparedStatement pst = con.prepareStatement(insertQuery);
                pst.setString(1, pseudo);
               // pst.setString(2, prenom);
                pst.setString(2, email);
                pst.setString(3, motDePasse);
                pst.executeUpdate();

                JOptionPane.showMessageDialog(this, "Compte créé avec succès !");
                this.dispose();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
