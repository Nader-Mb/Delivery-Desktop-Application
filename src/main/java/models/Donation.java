package models;

import services.CampaignService;

import java.sql.SQLException;
import java.util.Date;


public class Donation {
    private int idDon, idCamp, idDonator;
    private float valeurDon;
    private Date history;

    public Donation() {

    }

    public Donation(int idDon, int idCamp, int idDonator, float valeurDon, Date history) {
        this.idDon = idDon;
        this.idCamp = idCamp;
        this.idDonator = idDonator;
        this.valeurDon = valeurDon;
        this.history = history;
    }

    public Donation(int idCamp, int idDonator, float valeurDon, Date history) {
        this.idCamp = idCamp;
        this.idDonator = idDonator;
        this.valeurDon = valeurDon;
        this.history = history;
    }


    public Date getHistory() {
        return history;
    }

    public void setHistory(Date history) {
        this.history = history;
    }

    public int getIdDon() {
        return idDon;
    }

    public int getIdCamp() {
        return idCamp;
    }

    public int getIdDonator() {
        return idDonator;
    }

    public float getValeurDon() {
        return valeurDon;
    }

    public void setIdDon(int idDon) {
        this.idDon = idDon;
    }

    public void setIdCamp(int idCamp) {
        this.idCamp = idCamp;
    }

    public void setIdDonator(int idDonator) {
        this.idDonator = idDonator;
    }

    public void setValeurDon(float valeurDon) {
        this.valeurDon = valeurDon;
    }

    CampaignService campaignService = new CampaignService();


    @Override
    public String toString() {
        try {
            return String.format("%-65s   %-72s  %-60s", campaignService.recupererTitre(idCamp), valeurDon, history);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
