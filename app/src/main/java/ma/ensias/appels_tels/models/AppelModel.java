package ma.ensias.appels_tels.models;

public class AppelModel {
    String nom;
    String numero;
    String date;
    String time;
    String type;
    String idlog;
    int duration;
    int id;
    String temps_appelle;

    String location;

    public String getNom() {
        return nom;
    }
    public String getIdlog() {
        return idlog;
    }

    public void setIdlog(String idlog) {
        this.idlog = idlog;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setType(String type) {
        this.type = type;
    }


    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTemps_appelle() {
        return temps_appelle;
    }

    public void setTemps_appelle(String temps_appelle) {
        this.temps_appelle = temps_appelle;
    }
}
