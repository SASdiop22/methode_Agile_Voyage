import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import java.awt.*;
import java.awt.event.*;

public class Main {
    private JFrame frame;
    private Utilisateur utilisateur;
    private GestionnaireActivites gestionnaire;
    private JPanel activityPanel;

    public Main() {
        // Initialiser l'utilisateur, le gestionnaire et le panel
        utilisateur = new Utilisateur(1, "JohnDoe", "john@example.com", "password123", "Bio de John", "photo.jpg");
        gestionnaire = new GestionnaireActivites();
        activityPanel = new JPanel();
    }

    public void setupUI() {
        // Configurer le frame, les panels, les boutons, etc.
        frame = new JFrame("Titre");
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
        activityPanel.setLayout(new BoxLayout(activityPanel, BoxLayout.Y_AXIS));
        activityPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Scroll Pane
        JScrollPane scrollPane = new JScrollPane(activityPanel);
        scrollPane.setBorder(null);

        // Ajouter des activités d'exemple
        for (int i = 1; i <= 3; i++) {
            Activite activite = new Activite(i, "Activité " + i, "En savoir plus", "image.jpg", "Catégorie " + i, utilisateur, "Ville " + i, i * 10);
            gestionnaire.ajouterActivite(activite);
            ajouterActiviteUI(activite, activityPanel);
        }

        // Add Activity Button
        JButton addButton = new JButton("Ajouter une activité");
        styleButton(addButton);
        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addButton.addActionListener(e -> {
            new FormulaireAjoutActiviteDialog(frame, this, utilisateur, gestionnaire, activityPanel); // Passer l'instance de Main et autres données
        });

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(addButton, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private void styleButton(JButton button) {
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public void ajouterActiviteUI(Activite activite, JPanel activityPanel) {
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

        // Bouton En savoir plus
        JButton detailsButton = new JButton("En savoir plus");
        detailsButton.addActionListener(e -> afficherDetailsActivite(activite, gestionnaire));
        panel.add(detailsButton, BorderLayout.SOUTH);

        activityPanel.add(panel);
        activityPanel.revalidate();
        activityPanel.repaint();
    }


    private void afficherDetailsActivite(Activite activite, GestionnaireActivites gestionnaire) {
        JDialog dialog = new JDialog(frame, "Détails de l'activité", true);
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

        // Colonne de droite: Liste des intéressés
        JPanel rightColumn = new JPanel(new BorderLayout());
        rightColumn.setBorder(new EmptyBorder(10, 10, 10, 10));

        JList<Utilisateur> interestedList = new JList<>(activite.getInteresses().toArray(new Utilisateur[0]));
        JScrollPane interestedScrollPane = new JScrollPane(interestedList);
        rightColumn.add(interestedScrollPane, BorderLayout.CENTER);

        mainPanel.add(leftColumn);
        mainPanel.add(rightColumn);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton participateButton = new JButton("Participer");
        JButton deleteButton = new JButton("Supprimer");

        participateButton.addActionListener(e -> {
            activite.ajoutInteresses(utilisateur);
            dialog.dispose();
        });

        deleteButton.addActionListener(e -> {
            gestionnaire.supprimerActivite(activite);
            dialog.dispose();
            updateActivityPanel();
        });

        buttonPanel.add(participateButton);
        buttonPanel.add(deleteButton);

        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.add(mainPanel, BorderLayout.CENTER);

        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }

    private void updateActivityPanel() {
        activityPanel.removeAll();
        for (Activite activite : gestionnaire.getActivites()) {
            ajouterActiviteUI(activite, activityPanel);
        }
        activityPanel.revalidate();
        activityPanel.repaint();
    }

    private void styleLikeButton(JButton button) {
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setForeground(Color.GRAY);

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main mainApp = new Main();
            mainApp.setupUI();
        });
    }
}
