import javax.swing.*;
import java.awt.*;

public class FormulaireAjoutActiviteDialog {

    private Main mainApp;  // Référence à Main

    // Constructor that takes the necessary objects
    public FormulaireAjoutActiviteDialog(Frame owner, Main mainApp, Utilisateur utilisateur, GestionnaireActivites gestionnaire, JPanel activityPanel) {
        this.mainApp = mainApp;  // Initialiser la référence à Main
        JDialog dialog = new JDialog(owner, "Ajouter une activité", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 5, 10);

        // Form fields
        String[] labels = {"Titre:", "Description:", "Ville:", "Prix:"};
        JTextField[] fields = new JTextField[labels.length];

        // Ajout des labels et des champs de texte
        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            formPanel.add(new JLabel(labels[i]), gbc);

            gbc.gridx = 1;
            fields[i] = new JTextField(20);
            formPanel.add(fields[i], gbc);
        }

        // Ajout du bouton "Enregistrer"
        JButton saveButton = new JButton("Enregistrer");
        styleButton(saveButton);
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(saveButton, gbc);

        // Action du bouton Enregistrer
        saveButton.addActionListener(e -> {
            if (validateFields(fields)) {
                // Créer une nouvelle activité à partir des données saisies
                Activite nouvelleActivite = new Activite(
                    gestionnaire.getActivites().size() + 1,  // ID de l'activité basé sur la taille de la liste
                    fields[0].getText(), // Titre
                    fields[1].getText(), // Description
                    "default.jpg", // Image par défaut
                    "Général", // Catégorie par défaut
                    utilisateur,  // L'utilisateur qui a ajouté l'activité
                    fields[2].getText(), // Ville
                    Integer.parseInt(fields[3].getText()) // Prix
                );
                
                gestionnaire.ajouterActivite(nouvelleActivite);  // Ajouter l'activité au gestionnaire
                mainApp.ajouterActiviteUI(nouvelleActivite, activityPanel);  // Appel à ajouterActiviteUI de Main
                dialog.dispose(); // Fermer la fenêtre après ajout
            }
        });

        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    private static void styleButton(JButton button) {
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private static boolean validateFields(JTextField[] fields) {
        for (JTextField field : fields) {
            if (field.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        try {
            Integer.parseInt(fields[3].getText());  // Vérifier que le prix est un entier valide
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Le prix doit être un nombre.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}
