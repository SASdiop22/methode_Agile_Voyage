import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static DefaultListModel<Activite> listModel = new DefaultListModel<>();
    private static List<Activite> activities = new ArrayList<>();
    private static Utilisateur utilisateur = new Utilisateur(1, "JohnDoe", "john@example.com", "password123", "Bio de John", "photo.jpg");
    
    public static void main(String[] args) {
        // Créer la fenêtre
        JFrame frame = new JFrame("Page des Activités");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Titre en haut
        JLabel titleLabel = new JLabel("Liste des Activités", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        
        // Créer un panel avec une disposition verticale
        JPanel activityPanel = new JPanel();
        activityPanel.setLayout(new BoxLayout(activityPanel, BoxLayout.Y_AXIS));
        JScrollPane listScroller = new JScrollPane(activityPanel);
        
        // Ajouter des activités de test au démarrage
        Activite activite1 = new Activite(1, "Randonnée", "Balade en montagne", "image.jpg", "Sport", utilisateur);
        Activite activite2 = new Activite(2, "Concert", "Concert en plein air", "image.jpg", "Musique", utilisateur);
        activities.add(activite1);
        activities.add(activite2);

        // Afficher les activités existantes
        for (Activite activite : activities) {
            ajouterActiviteUI(activite, activityPanel);
        }
        
        // Bouton pour ajouter une activité
        JButton addButton = new JButton("Ajouter Activité");
        addButton.addActionListener(e -> ouvrirFormulaireAjout(activityPanel));

        // Organiser les composants
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(listScroller, BorderLayout.CENTER);
        panel.add(addButton, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);
    }

    private static void ouvrirFormulaireAjout(JPanel activityPanel) {
        JFrame formFrame = new JFrame("Ajouter une Activité");
        formFrame.setSize(300, 250);
        formFrame.setLayout(new GridLayout(5, 2));
        
        JLabel labelTitre = new JLabel("Titre:");
        JTextField textTitre = new JTextField();
        JLabel labelDescription = new JLabel("Description:");
        JTextField textDescription = new JTextField();
        JLabel labelCategorie = new JLabel("Catégorie:");
        JTextField textCategorie = new JTextField();
        JButton saveButton = new JButton("Enregistrer");

        saveButton.addActionListener(e -> {
            String titre = textTitre.getText();
            String description = textDescription.getText();
            String categorie = textCategorie.getText();

            if (!titre.isEmpty() && !description.isEmpty() && !categorie.isEmpty()) {
                Activite nouvelleActivite = new Activite(activities.size() + 1, titre, description, "default.jpg", categorie, utilisateur);
                activities.add(nouvelleActivite);
                ajouterActiviteUI(nouvelleActivite, activityPanel);
                formFrame.dispose();
            } else {
                JOptionPane.showMessageDialog(formFrame, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        formFrame.add(labelTitre);
        formFrame.add(textTitre);
        formFrame.add(labelDescription);
        formFrame.add(textDescription);
        formFrame.add(labelCategorie);
        formFrame.add(textCategorie);
        formFrame.add(new JLabel()); // Espace
        formFrame.add(saveButton);
        
        formFrame.setVisible(true);
    }

    private static void ajouterActiviteUI(Activite activite, JPanel activityPanel) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel label = new JLabel(activite.getTitre() + " - Likes: " + activite.getNombresLikes());
        JButton likeButton = new JButton("+1 Like");
        
        likeButton.addActionListener(e -> {
            activite.incrementerLikes();
            label.setText(activite.getTitre() + " - Likes: " + activite.getNombresLikes());
        });
        
        panel.add(label);
        panel.add(likeButton);
        activityPanel.add(panel);
        activityPanel.revalidate();
        activityPanel.repaint();
    }
}
