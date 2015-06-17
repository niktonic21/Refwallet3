package sk.upjs.ics.refwallet;

import java.io.Serializable;
import java.util.Locale;

/**
 * Created by Maťo21 on 7. 6. 2015.
 */
public class Zapas implements Serializable{
    private Long id;
    private int cz;
    private String Datum;
    private String liga;
    private String stadion;
    private String domaci;
    private String hostia;
    private String hr1;
    private String hr2;
    private String cr1;
    private String cr2;
    private String instruktor;
    private String video;
    private String casPrichodu;
    private String casOdchodu;
    private double stravne;
    private String ZMesta;
    private String DoMesta;
    private double Kilometre;
    private double cenaKilom;
    private int pausal;
    private double celkovaSuma;

    public Zapas(){

    }

    public Zapas(int cz, String datum, String liga, String stadion, String domaci, String hostia,
                 String hr1, String hr2, String cr1, String cr2, String instruktor, String video,
                 String casPrichodu, String casOdchodu, String ZMesta,
                 String doMesta,int pausal) {
        this.cz = cz;
        this.Datum = datum;
        this.liga = liga;
        this.stadion = stadion;
        this.domaci = domaci;
        this.hostia = hostia;
        this.hr1 = hr1;
        this.hr2 = hr2;
        this.cr1 = cr1;
        this.cr2 = cr2;
        this.instruktor = instruktor;
        this.video = video;
        this.casPrichodu = casPrichodu;
        this.casOdchodu = casOdchodu;
        this.stravne = stravne;
        this.ZMesta = ZMesta;
        this.DoMesta = doMesta;
        this.pausal = pausal;
        this.celkovaSuma = celkovaSuma;
        this.Kilometre = Kilometre;
    }

    public Zapas(String stadion, String liga, String domaci, String hostia) {
        this.stadion = stadion;
        this.liga = liga;
        this.domaci = domaci;
        this.hostia = hostia;
    }

    public Zapas(int cz, String datum, double celkovaSuma, int cenaKilom, int pausal, double stravne) {
        this.cz = cz;
        Datum = datum;
        this.celkovaSuma = celkovaSuma;
        this.cenaKilom = cenaKilom;
        this.pausal = pausal;
        this.stravne = stravne;
    }

    public int getPausal() {
        return pausal;
    }

    public void setPausal(int pausal) {
        this.pausal = pausal;
    }

    public double getCelkovaSuma() {
        return celkovaSuma;
    }

    public void setCelkovaSuma(double celkovaSuma) {
        this.celkovaSuma = celkovaSuma;
    }

    public int getCz() {
        return cz;
    }

    public void setCz(int cz) {
        this.cz = cz;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDatum() {
        return Datum;
    }

    public void setDatum(String datum) {
        Datum = datum;
    }

    public String getStadion() {
        return stadion;
    }

    public void setStadion(String stadion) {
        this.stadion = stadion;
    }

    public String getLiga() {
        return liga;
    }

    public void setLiga(String liga) {
        this.liga = liga;
    }

    public String getDomaci() {
        return domaci;
    }

    public void setDomaci(String domaci) {
        this.domaci = domaci;
    }

    public String getHostia() {
        return hostia;
    }

    public void setHostia(String hostia) {
        this.hostia = hostia;
    }

    public String getHr1() {
        return hr1;
    }

    public void setHr1(String hr1) {
        this.hr1 = hr1;
    }

    public String getHr2() {
        return hr2;
    }

    public void setHr2(String hr2) {
        this.hr2 = hr2;
    }

    public String getCr1() {
        return cr1;
    }

    public void setCr1(String cr1) {
        this.cr1 = cr1;
    }

    public String getInstruktor() {
        return instruktor;
    }

    public void setInstruktor(String instruktor) {
        this.instruktor = instruktor;
    }

    public String getCr2() {
        return cr2;
    }

    public void setCr2(String cr2) {
        this.cr2 = cr2;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getCasPrichodu() {
        return casPrichodu;
    }

    public void setCasPrichodu(String casPrichodu) {
        this.casPrichodu = casPrichodu;
    }

    public String getCasOdchodu() {
        return casOdchodu;
    }

    public void setCasOdchodu(String casOdchodu) {
        this.casOdchodu = casOdchodu;
    }

    public double getStravne() {
        return stravne;
    }

    public void setStravne(double stravne) {this.stravne = stravne;}

    public double getKilometre() {
        return Kilometre;
    }

    public void setKilometre(double kilometre) {
        Kilometre = kilometre;
    }

    public double getCenaKilom() {return cenaKilom;}

    public void setCenaKilom(double cenaKilom) {
        this.cenaKilom = cenaKilom;
    }

    public String getZMesta() {
        return ZMesta;
    }

    public void setZMesta(String ZMesta) {
        this.ZMesta = ZMesta;
    }

    public String getDoMesta() {
        return DoMesta;
    }

    public void setDoMesta(String doMesta) {
        DoMesta = doMesta;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getDatum());
        stringBuilder.append(" Č. zápasu: ");
        stringBuilder.append(getCz());
        return stringBuilder.toString();
    }
}
