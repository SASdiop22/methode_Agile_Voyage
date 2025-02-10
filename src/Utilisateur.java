import java.util.ArrayList;
import java.util.List;

public class Utilisateur {
    // Attributs privés et publics de l'utilisateur
    private int id;
    String pseudo;
    String email;
    private String motDePasse;
    String bio;
    String photoProfil;
    
    // Listes pour stocker les activités aimées et les intérêt
    List<Activite> likes;
    List<Activite> interets;
    
    // Constructeur avec identifiant
    public Utilisateur(int id, String pseudo, String email, String motDePasse, String bio, String photoProfil) {
        super();
        this.id = id;
        this.pseudo = pseudo;
        this.email = email;
        this.motDePasse = motDePasse;
        this.bio = bio;
        this.photoProfil = photoProfil;
        
        this.likes = new ArrayList<>();
        this.interets = new ArrayList<>();
    }
    
    // Constructeur sans identifiant (peut être généré plus tard)
    public Utilisateur(String pseudo, String email, String motDePasse, String bio, String photoProfil) {
        super();
        this.pseudo = pseudo;
        this.email = email;
        this.motDePasse = motDePasse;
        this.bio = bio;
        this.photoProfil = photoProfil;
        
        this.likes = new ArrayList<>();
        this.interets = new ArrayList<>();
    }

    // Getter pour l'identifiant (pas de setter car l'id ne doit pas être modifié)
    public int getId() {
        return id;
    }

    // Getters et setters pour les autres attributs
    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPhotoProfil() {
        return photoProfil;
    }

    public void setPhotoProfil(String photoProfil) {
        this.photoProfil = photoProfil;
    }
    
    // Méthode pour ajouter une activité aux likes
    public void ajouterLike(Activite activite) {
    	if (!this.likes.contains(activite)) {
    		this.likes.add(activite);
        }
    }

    // Méthode pour supprimer une activité des likes
    public void supprimerLike(Activite activite) {
    	if (this.likes.contains(activite)) {
	        this.likes.remove(activite);
	        activite.retirerLikes(this);
    	}
    }

    // Méthode pour ajouter une activité aux intérêts
    public void ajouterInteret(Activite activite) {
    	if (!this.interets.contains(activite)) {
    		this.interets.add(activite);
    	}
    }

    // Méthode pour supprimer une activité des intérêts
    public void supprimerInteret(Activite activite) {
    	if (this.interets.contains(activite)) {
	        this.interets.remove(activite);
	        activite.retirerInteresses(this);
    	}
    }

    // Méthode pour afficher les informations de l'utilisateur
    public void afficher() {
        System.out.println("Utilisateur{" +
                "id=" + id +
                ", pseudo='" + pseudo + '\'' +
                ", email='" + email + '\'' +
                ", bio='" + bio + '\'' +
                ", photoProfil='" + photoProfil + '\'' +
                '}');
    }
}
