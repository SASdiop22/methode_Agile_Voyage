import java.io.*;
import java.util.ArrayList;

public class Activite {
	int id;
	String titre;
	String description;
	String image;
	String categorie;
	Utilisateur auteur;
	int nbreLikes;
	ArrayList<Utilisateur> interesses;
	
	
	public Activite(String titre, String description, String image, String categorie, Utilisateur auteur) {
		super();
		this.titre = titre;
		this.description = description;
		this.image = image;
		this.categorie = categorie;
		this.auteur = auteur;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getCategorie() {
		return categorie;
	}
	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}
	public int getNbreLikes() {
		return nbreLikes;
	}
	public void setNbreLikes(int nbreLikes) {
		this.nbreLikes = nbreLikes;
	}
	
	public void ajouteInteresse(Utilisateur U)
	{
		this.interesses.add(U);
	}
	public void ajouteLike(Utilisateur U)
	{
		
	}
}
