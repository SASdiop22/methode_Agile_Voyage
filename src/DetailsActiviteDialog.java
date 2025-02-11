import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

public class DetailsActiviteDialog {
    public static void afficherDetailsActivite(Activite activite, GestionnaireActivites gestionnaire, Utilisateur utilisateur) {
        JDialog dialog = new JDialog((Frame)null, "Détails de l'activité", true);
        dialog.setSize(600, 400);
        dialog.setLayout(new BorderLayout());

        // Panel principal avec deux colonnes
        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 20, 0));

        // Colonne de gauche
        JPanel leftColumn = new JPanel();
        leftColumn.setLayout(new BoxLayout(leftColumn, BoxLayout.Y_AXIS));
        leftColumn.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel(activite.getTitre());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        JLabel likesLabel = new JLabel("Likes: " + activite.getNombresLikes());
        JLabel cityLabel = new JLabel("Ville: " + activite.getVille());
        JLabel descLabel = new JLabel("<html><body>" + activite.getDescription() + "</body></html>");
        JLabel priceLabel = new JLabel("Prix: " + activite.getPrix() + " €");

        leftColumn.add(titleLabel);
        leftColumn.add(likesLabel);
        leftColumn.add(cityLabel);
        leftColumn.add(descLabel);
        leftColumn.add(priceLabel);

        // Colonne de droite : Liste des intéressés (participants)
        JPanel rightColumn = new JPanel(new BorderLayout());
        rightColumn.setBorder(new EmptyBorder(10, 10, 10, 10));

        JList<Utilisateur> interestedList = new JList<>(activite.getInteresses().toArray(new Utilisateur[0]));
        JScrollPane interestedScrollPane = new JScrollPane(interestedList);
        rightColumn.add(interestedScrollPane, BorderLayout.CENTER);

        mainPanel.add(leftColumn);
        mainPanel.add(rightColumn);

        // Ajouter les boutons Participer et Supprimer
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton participateButton = new JButton("Participer");
        JButton deleteButton = new JButton("Supprimer");

        participateButton.addActionListener(e -> {
            activite.ajoutInteresses(utilisateur);
            dialog.dispose();
        });

        deleteButton.addActionListener(e -> {
            // Appeler la méthode du gestionnaire pour supprimer l'activité
            gestionnaire.supprimerActivite(activite); // Supprime l'activité de la liste
            dialog.dispose(); // Ferme la fenêtre
            // Mettre à jour l'interface principale en supprimant l'activité de l'affichage
            // Main.updateActivityPanel(buttonPanel, gestionnaire);  // Rafraîchit l'affichage pour enlever l'activité supprimée
        });


        buttonPanel.add(participateButton);
        buttonPanel.add(deleteButton);

        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.add(mainPanel, BorderLayout.CENTER);

        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
}
