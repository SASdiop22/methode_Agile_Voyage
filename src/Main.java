import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static List<Activite> activities = new ArrayList<>();
    private static Utilisateur utilisateur = new Utilisateur(1, "JohnDoe", "john@example.com", "password123", "Bio de John", "photo.jpg");

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("Titre");
        frame.setSize(600, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(new EmptyBorder(10, 20, 10, 20));
        
        JLabel titleLabel = new JLabel("Titre");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton activitesBtn = new JButton("Activités");
        JButton connexionBtn = new JButton("Connexion");
        styleButton(activitesBtn);
        styleButton(connexionBtn);
        
        navPanel.add(activitesBtn);
        navPanel.add(connexionBtn);
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(navPanel, BorderLayout.EAST);

        // Activities Panel
        JPanel activityPanel = new JPanel();
        activityPanel.setLayout(new BoxLayout(activityPanel, BoxLayout.Y_AXIS));
        activityPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Scroll Pane
        JScrollPane scrollPane = new JScrollPane(activityPanel);
        scrollPane.setBorder(null);

        // Add sample activities
        for (int i = 1; i <= 3; i++) {
            Activite activite = new Activite(i, "Activité " + i, "En savoir plus", "image.jpg", "Catégorie " + i, utilisateur, "Ville " + i, i * 10);
            activities.add(activite);
            ajouterActiviteUI(activite, activityPanel);
        }

        // Add Activity Button
        JButton addButton = new JButton("Ajouter une activité");
        styleButton(addButton);
        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addButton.addActionListener(e -> ouvrirFormulaireAjout(activityPanel));

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(addButton, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private static void styleButton(JButton button) {
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private static void ajouterActiviteUI(Activite activite, JPanel activityPanel) {
        JPanel panel = new JPanel(new BorderLayout(10, 0));
        panel.setBorder(BorderFactory.createCompoundBorder(
            new MatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY),
            new EmptyBorder(15, 0, 15, 0)
        ));

        // Left side: Title and Description
        JPanel leftPanel = new JPanel(new GridLayout(2, 1));
        JLabel titleLabel = new JLabel(activite.getTitre());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JLabel descLabel = new JLabel(activite.getDescription());
        descLabel.setForeground(Color.GRAY);
        leftPanel.add(titleLabel);
        leftPanel.add(descLabel);

        // Right side: Price and City
        JPanel rightPanel = new JPanel(new GridLayout(2, 1));
        JLabel priceLabel = new JLabel(activite.getPrix() + " €");
        JPanel cityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        JLabel cityLabel = new JLabel(activite.getVille());
        
        // Création du bouton cœur
        JButton heartButton = new JButton("♥");
        styleLikeButton(heartButton);
        
        // Label pour afficher le nombre de likes
        JLabel likesCountLabel = new JLabel("0 like");
        likesCountLabel.setForeground(Color.GRAY);
        
        // Gestionnaire d'événements pour le bouton cœur
        heartButton.addActionListener(e -> {
            activite.incrementerLikes();
            heartButton.setForeground(Color.RED);
            int likes = activite.getNombresLikes();
            likesCountLabel.setText(likes + (likes > 1 ? " likes" : " like"));
        });
        
        cityPanel.add(cityLabel);
        cityPanel.add(heartButton);
        cityPanel.add(likesCountLabel);
        
        rightPanel.add(priceLabel);
        rightPanel.add(cityPanel);

        panel.add(leftPanel, BorderLayout.WEST);
        panel.add(rightPanel, BorderLayout.EAST);
        
        activityPanel.add(panel);
        activityPanel.revalidate();
        activityPanel.repaint();
    }

    // Ajoutez cette nouvelle méthode pour styler le bouton cœur
    private static void styleLikeButton(JButton button) {
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setForeground(Color.GRAY);
        
        // Ajouter un effet de survol
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (button.getForeground() != Color.RED) {
                    button.setForeground(new Color(255, 150, 150));
                }
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                if (button.getForeground() != Color.RED) {
                    button.setForeground(Color.GRAY);
                }
            }
        });
    }

    private static void ouvrirFormulaireAjout(JPanel activityPanel) {
        JDialog dialog = new JDialog((Frame)null, "Ajouter une activité", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 5, 10);

        // Form fields
        String[] labels = {"Titre:", "Description:", "Ville:", "Prix:"};
        JTextField[] fields = new JTextField[labels.length];
        
        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            formPanel.add(new JLabel(labels[i]), gbc);

            gbc.gridx = 1;
            fields[i] = new JTextField(20);
            formPanel.add(fields[i], gbc);
        }

        JButton saveButton = new JButton("Enregistrer");
        styleButton(saveButton);
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(saveButton, gbc);

        saveButton.addActionListener(e -> {
            if (validateFields(fields)) {
                Activite nouvelleActivite = new Activite(
                    activities.size() + 1,
                    fields[0].getText(),
                    fields[1].getText(),
                    "default.jpg",
                    "Général",
                    utilisateur,
                    fields[2].getText(),
                    Integer.parseInt(fields[3].getText())
                );
                activities.add(nouvelleActivite);
                ajouterActiviteUI(nouvelleActivite, activityPanel);
                dialog.dispose();
            }
        });

        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    private static boolean validateFields(JTextField[] fields) {
        for (JTextField field : fields) {
            if (field.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        try {
            Integer.parseInt(fields[3].getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Le prix doit être un nombre.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}

