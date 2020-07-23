package Streams;

import java.util.List;

public class Pelis  {

    private String titol, original, direccio, versio, idioma;
    private String any;
    private String dataEstrena;
    private List<String> interprets;

    @Override
    public String toString() {
        return "\nTitol: " + '\''+ getTitol() + '\''
                + "\nAny: " + getAny()
                + "\nDirecció: " + getDireccio()
                + "\nIdioma: " + getIdioma()
                + "\nInterprets: " + getInterprets();
    }

    public String getAny() {
        return any;
    }

    public void setAny(String any) {
        this.any = any;
    }

    public String getTitol() {
        return titol;
    }

    public void setTitol(String titol) {
        this.titol = titol;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getDireccio() {
        return direccio;
    }

    public void setDireccio(String direccio) {
        this.direccio = direccio;
    }

    public String getVersio() {
        return versio;
    }

    public void setVersio(String versio) {
        this.versio = versio;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getDataEstrena() {
        return dataEstrena;
    }

    public void setDataEstrena(String dataEstrena) {
        this.dataEstrena = dataEstrena;
    }

    public List<String> getInterprets() {
        return interprets;
    }

    public void setInterprets(List<String> interprets) {
        this.interprets = interprets;
    }


}
