package models;

public class Ingredients {

    private int idIng;
    private String nameIng;
    private String amount;
    private int idRec;

    public Ingredients() {
    }

    public Ingredients(int idIng, String nameIng, String amount, int idRec) {
        this.idIng = idIng;
        this.nameIng = nameIng;
        this.amount = amount;
        this.idRec = idRec;
    }

    public Ingredients(String nameIng, String amount, int idRec) {
        this.nameIng = nameIng;
        this.amount = amount;
        this.idRec = idRec;
    }
    public int getIdIng() {
        return idIng;
    }

    public void setIdIng(int idIng) {
        this.idIng = idIng;
    }

    public String getNameIng() {
        return nameIng;
    }

    public void setNameIng(String nameIng) {
        this.nameIng = nameIng;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getIdRec() {
        return idRec;
    }

    public void setIdRec(int idRec) {
        this.idRec = idRec;
    }

    @Override
    public String toString() {
        return "Ingredients{" +
                "idIng=" + idIng +
                ", nameIng='" + nameIng + '\'' +
                ", amount='" + amount + '\'' +
                ", idRec=" + idRec +
                '}';
    }
}