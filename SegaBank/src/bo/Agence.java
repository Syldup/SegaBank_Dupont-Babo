package bo;

public class Agence {
    private int id;
    private int code;
    private String adresse;

    public Agence(int id, int code, String adresse) {
        this.id = id;
        this.code = code;
        this.adresse = adresse;
    }

    public int getCode() {
        return code;
    }

    public int getId() {
        return id;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Agence{");
        sb.append("id=").append(id);
        sb.append(", code=").append(code);
        sb.append(", adresse='").append(adresse).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
