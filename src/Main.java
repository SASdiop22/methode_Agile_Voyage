import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static DefaultListModel<String> listModel = new DefaultListModel<>();
    private static List<Activite> activities = new ArrayList<>();
    private static Utilisateur utilisateur = new Utilisateur(1, "JohnDoe", "john@example.com", "password123", "Bio de John", "photo.jpg");
    
    public static void main(String[] args) {
        // Créer la fenêtre
        JFrame frame = new JFrame("Page des Activités");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Titre en haut
        JLabel titleLabel = new JLabel("Liste des Activités", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        
        // Créer une JList pour afficher les titres des activités
        JList<String> activityList = new JList<>(listModel);
        JScrollPane listScroller = new JScrollPane(activityList);
        listScroller.setPreferredSize(new Dimension(300, 150));
        
        // Créer un bouton pour afficher les détails d'une activité
        JButton detailsButton = new JButton("Afficher Détails");
        detailsButton.addActionListener(e -> {
            int selectedIndex = activityList.getSelectedIndex();
            if (selectedIndex != -1) {
                Activite selectedActivite = activities.get(selectedIndex);
                String details = "Titre: " + selectedActivite.getTitre() +
                        "\nDescription: " + selectedActivite.getDescription() +
                        "\nCatégorie: " + selectedActivite.getCategorie() +
                        "\nAuteur: " + selectedActivite.getAuteur().getPseudo() +
                        "\nLikes: " + selectedActivite.getNombresLikes();
                JOptionPane.showMessageDialog(frame, details);
            } else {
                JOptionPane.showMessageDialog(frame, "Veuillez sélectionner une activité.");
            }
        });

        // Bouton pour ajouter une activité
        JButton addButton = new JButton("Ajouter Activité");
        addButton.addActionListener(e -> ouvrirFormulaireAjout(frame));

        // Organiser les composants
        JPanel panel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(detailsButton);
        buttonPanel.add(addButton);
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(listScroller, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);
    }

    private static void ouvrirFormulaireAjout(JFrame parentFrame) {
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

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String titre = textTitre.getText();
                String description = textDescription.getText();
                String categorie = textCategorie.getText();

                if (!titre.isEmpty() && !description.isEmpty() && !categorie.isEmpty()) {
                    Activite nouvelleActivite = new Activite(activities.size() + 1, titre, description, "default.jpg", categorie, utilisateur);
                    activities.add(nouvelleActivite);
                    listModel.addElement(nouvelleActivite.getTitre() + " - Likes: " + nouvelleActivite.getNombresLikes());
                    formFrame.dispose();
                } else {
                    JOptionPane.showMessageDialog(formFrame, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
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
}
