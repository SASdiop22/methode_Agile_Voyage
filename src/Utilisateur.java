public class Utilisateur {
    private int id;
    private String pseudo;
    private String email;
    private String motDePasse;
    private String bio;
    private String photoProfil;

    public Utilisateur(int id, String pseudo, String email, String motDePasse, String bio, String photoProfil) {
        this.id = id;
        this.pseudo = pseudo;
        this.email = email;
        this.motDePasse = motDePasse;
        this.bio = bio;
        this.photoProfil = photoProfil;
    }

    public Utilisateur(String pseudo, String email, String motDePasse, String bio, String photoProfil) {
        this.pseudo = pseudo;
        this.email = email;
        this.motDePasse = motDePasse;
        this.bio = bio;
        this.photoProfil = photoProfil;
    }

    public int getId() { return id; }
    public String getPseudo() { return pseudo; }
    public String getEmail() { return email; }
    public String getMotDePasse() { return motDePasse; }
    public String getBio() { return bio; }
    public String getPhotoProfil() { return photoProfil; }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", pseudo='" + pseudo + '\'' +
                ", email='" + email + '\'' +
                ", bio='" + bio + '\'' +
                ", photoProfil='" + photoProfil + '\'' +
                '}';
    }
}
