package Streams;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class StreamXmlCinema {
    List<Pelis> sList = new ArrayList<>();
    private String busca;
    Scanner sc = new Scanner(System.in);

    public String getBusca() {
        return busca;
    }

    public void setBusca(String busca) {
        this.busca = busca;
    }

    public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {

        URL url = new URL("http://gencat.cat/llengua/cinema/provacin.xml");
        List<Pelis> pelis;

        LlegirXmlCinema llegirXmlCinema = new LlegirXmlCinema();
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        XMLReader myReader =  saxParser.getXMLReader();
        myReader.setContentHandler(llegirXmlCinema);
        myReader.parse(new InputSource(url.openStream()));
        pelis = llegirXmlCinema.getListPelis();

        StreamXmlCinema streamXml = new StreamXmlCinema();
        streamXml.menu(pelis);
    }
    void menu(List<Pelis> pelis){
        Scanner sc = new Scanner(System.in);
        while(true) {
            try {
                System.out.print("\n\n\t\t\t\t\t\tPer quins valors vols buscar: " +
                        "\n\t\t\t\t\t\t\t\t\tTitol (t)," +
                        "\n\t\t\t\t\t\t\t\t\tAny (a), " +
                        "\n\t\t\t\t\t\t\t\t\tDirecció (d), " +
                        "\n\t\t\t\t\t\t\t\t\tIdioma (i), " +
                        "\n\t\t\t\t\t\t\t\t\tInterprets (v), " +
                        "\n\t\t\t\t\t\t\t\t\tData estrena (e) " +
                        "\n\t\t\t\t\t\t\t\t\tSortir del menu (s)" +
                        "\n\n\t\t\t\t\t\t\t\t" +
                        "Opcio: ");
                String opcio = sc.nextLine();

                switch (opcio){
                    case "t":
                        menuTitol(pelis);
                        break;
                    case "a":
                        menuAny(pelis);
                        break;
                    case "d":
                        menuDireccio(pelis);
                        break;
                    case "i":
                        menuIdioma(pelis);
                        break;
                    case "v":
                        menuInterprets(pelis);
                        break;
                    case "e":
                        menuDataEstrena(pelis);
                        break;
                    default: return;
                }
            }catch (Exception e){
                System.out.println("Introdueix un valor vàlid");
            }
        }
    }
    void menuTitol(List<Pelis> pelis){

        while(true) {
            try {
                System.out.print("\n\n\t\t\t\t\t\tQuin tipus de búsqueda vols fer per Títol: " +
                        "\n\t\t\t\t\t\t\t\t\tUn en concret (1)        ....   (.contains, replace)" +
                        "\n\t\t\t\t\t\t\t\t\tTot els que hi ha (2)    ....  (.distinc . sorted)" +
                        "\n\t\t\t\t\t\t\t\t\tQue començi per (3)      ....   (.startsWith)" +
                        "\n\t\t\t\t\t\t\t\t\tQue contingui la paraula... (4)      ....   (.contains)" +
                        "\n\t\t\t\t\t\t\t\t\tSortir del menu (5)" +
                        "\n\n\t\t\t\t\t\t\tOpcio: ");
                String opcio = sc.nextLine();

                switch (opcio){
                    case "1":
                        System.out.print("Quina pel.licula estàs buscant? : ");
                        setBusca( sc.nextLine());
                        sList = pelis.stream().filter(x -> x.getTitol().contains(getBusca()))
                                .collect(Collectors.toList());
                        for(Pelis a: sList) {
                            System.out.println("\n\nPelicula: " + a.getTitol());
                            System.out.println(a.getAny());
                            System.out.println(a.getDireccio());
                            System.out.println(a.getIdioma());
                            System.out.println(a.getDataEstrena());
                            System.out.println(a.getOriginal());
                            System.out.print("Interprets: ");
                            Stream<String> bloques = Pattern.compile(",").splitAsStream(a.getInterprets().toString()
                                    .replace(",",";"));

                            bloques.map(cadena -> cadena.substring(1, cadena.length() - 1))

                                    .forEach(System.out::print);
                        }
                        if (sList.size()==0){
                            System.out.println("\n\nNo s'han trobat resultats");
                        }
                        break;
                    case "2":
                        sList = pelis.stream().filter(x -> !x.getTitol().isEmpty())
                                .distinct()
                                .sorted((o1, o2)->o1.getTitol().
                                        compareTo(o2.getTitol()))
                                .collect(Collectors.toList());
                        for(Pelis a: sList){
                            System.out.println(a.getTitol());
                        }
                        long quants = pelis.stream().filter(x -> !x.getTitol().isEmpty()).count();
                        System.out.println("\n\n\t\tHi ha " + quants + " pel.liculas en el XML");
                        break;
                    case "3":
                        System.out.print("Per quina lletra estàs buscant? : ");
                        setBusca( sc.nextLine());

                        sList = pelis.stream().filter(x -> x.getTitol().startsWith(getBusca())).collect(Collectors.toList());
                        for(Pelis a: sList){
                            System.out.println(a.getTitol());
                        }
                        if (sList.size()==0){
                            System.out.println("\n\nNo s'han trobat resultats");
                        }
                        break;
                    case "4":
                        System.out.print("Quina paraula buscas en el titol? : ");
                        setBusca( sc.nextLine());

                        sList = pelis.stream().filter(x -> x.getTitol().contains(getBusca())).collect(Collectors.toList());
                        for(Pelis a: sList){
                            System.out.println(a.toString());
                        }
                        if (sList.size()==0){
                            System.out.println("\n\nNo s'han trobat resultats");
                        }
                        break;
                    case "5":
                        return;
                    default: break;
                }

            }catch (Exception e){
                System.out.println("Introdueix un valor vàlid");
            }
        }
    }

    void menuAny(List<Pelis> pelis){

        while(true) {
            try {
                System.out.print("\n\n\t\t\t\t\t\tQuin tipus de búsqueda vols fer per Any: " +
                        "\n\t\t\t\t\t\t\t\t\tUn en concret (1)" +
                        "\n\t\t\t\t\t\t\t\t\tTot els que hi ha ordenats(2)" +
                        "\n\t\t\t\t\t\t\t\t\tQue acabi per (3)           .....(endsWith)" +
                        "\n\t\t\t\t\t\t\t\t\tpel.licula més recent (4)         ...... (max)(ifPresent)" +
                        "\n\t\t\t\t\t\t\t\t\tSortir del menu (5)" +
                        "\n\n\t\t\t\t\t\t\tOpcio: ");
                String opcio = sc.nextLine();

                switch (opcio){
                    case "1":
                        System.out.print("De quin any estàs buscant? : ");
                        setBusca( sc.nextLine());
                        sList = pelis.stream().filter(x -> x.getAny().equals(getBusca()))
                                .sorted((o1, o2)->o1.getTitol().
                                        compareTo(o2.getTitol()))
                                .collect(Collectors.toList());
                        for(Pelis a: sList){
                            System.out.println("\n" + a.getTitol());
                            System.out.println(a.getAny());
                            System.out.println(a.getDireccio());
                            System.out.println(a.getIdioma());
                            System.out.println(a.getDataEstrena());
                            System.out.println(a.getOriginal());
                            System.out.println(a.getInterprets());
                        }
                        if (sList.size()==0){
                            System.out.println("\n\nNo s'han trobat resultats");
                        }
                        break;
                    case "2":
                        sList = pelis.stream().filter(x -> !x.getAny().isEmpty())
                                .sorted((o1, o2)->o1.getAny().
                                        compareTo(o2.getAny()))
                                .collect(Collectors.toList());
                        for(Pelis a: sList){
                            System.out.println(a.getAny());
                        }
                        if (sList.size()==0){
                            System.out.println("\n\nNo s'han trobat resultats");
                        }
                        break;
                    case "3":
                        System.out.print("Per quin número buscas que acabi la data? : ");
                        setBusca( sc.nextLine());
                        sList = pelis.stream().filter(x -> x.getAny().endsWith(getBusca()))
                                .sorted((o1, o2)->o1.getAny().
                                        compareTo(o2.getAny()))
                                .collect(Collectors.toList());

                        for(Pelis a: sList){
                            System.out.println(a.toString());
                        }
                        if (sList.size()==0){
                            System.out.println("\n\nNo s'han trobat resultats");
                        }
                        break;

                    case "4":
                        pelis.stream().filter(x -> !x.getAny().isEmpty())
                                .sorted((o1, o2)->o1.getAny().
                                        compareTo(o2.getAny()))
                                .max((o1, o2)->o1.getAny().
                                        compareTo(o2.getAny()))
                                .ifPresent(System.out::println);
                        break;
                    case "5":
                        return;
                    default: break;
                }

            }catch (Exception e){
                System.out.println("No existeix");
            }
        }
    }

    void menuDireccio(List<Pelis> pelis){

        while(true) {
            try {
                System.out.print("\n\n\t\t\t\t\t\tQuin tipus de búsqueda vols fer per Direcció: " +
                        "\n\t\t\t\t\t\t\t\t\tUn Director en concret (1)" +
                        "\n\t\t\t\t\t\t\t\t\tTot els que hi ha (2)" +
                        "\n\t\t\t\t\t\t\t\t\tQue començi per (3) " +
                        "\n\t\t\t\t\t\t\t\t\tDirectors que coincideixen com actors (4) " +
                        "\n\t\t\t\t\t\t\t\t\tSortir del menu (5)" +
                        "\n\n\t\t\t\t\t\t\tOpcio: ");
                String opcio = sc.nextLine();

                switch (opcio){
                    case "1":
                        System.out.print("Quina director estàs buscant? : ");
                        setBusca( sc.nextLine());
                        sList = pelis.stream().filter(x -> x.getDireccio().equalsIgnoreCase(getBusca()))
                                .collect(Collectors.toList());
                        //.forEach(x-> System.out.println(x.getTitol()));
                        for(Pelis a: sList){
                            System.out.println(a.getTitol());
                            System.out.println(a.getAny());
                            System.out.println(a.getDireccio());
                            System.out.println(a.getIdioma());
                            System.out.println(a.getDataEstrena());
                            System.out.println(a.getOriginal());
                            System.out.println(a.getInterprets());
                        }
                        if (sList.size()==0){
                            System.out.println("\n\nNo s'han trobat resultats");
                        }
                        break;
                    case "2":
                        sList = pelis.stream().filter(x -> !x.getDireccio().isEmpty()).collect(Collectors.toList());
                        for(Pelis a: sList){
                            System.out.println(a.getDireccio());
                        }
                        if (sList.size()==0){
                            System.out.println("\n\nNo s'han trobat resultats");
                        }
                        break;
                    case "3":
                        System.out.print("Per quina lletra estàs buscant? : ");
                        setBusca( sc.nextLine());

                        sList = pelis.stream().filter(x -> x.getDireccio().startsWith(getBusca())).collect(Collectors.toList());
                        for(Pelis a: sList){
                            System.out.println(a.getDireccio());
                        }
                        if (sList.size()==0){
                            System.out.println("\n\nNo s'han trobat resultats");
                        }
                        break;
                    case "4":
                        List<Pelis> resultat = new ArrayList<>();
                        sList = pelis.stream().filter(x -> !x.getDireccio().isEmpty())
                                .collect(Collectors.toList());
                        for (int i = 0; i < sList.size() ; i++) {
                            int finalI = i;
                            resultat = pelis.stream().filter(x -> x.getInterprets().contains(sList.get(finalI).getDireccio()))
                                    .collect(Collectors.toList());
                        }
                        for(Pelis a: resultat){
                            System.out.println(a.getDireccio());
                        }
                        if (resultat.size()==0){
                            System.out.println("\n\nNo s'han trobat resultats");
                        }
                        break;
                    case "5":
                        return;
                    default: break;
                }

            }catch (Exception e){
                System.out.println("Introdueix un valor vàlid");
            }
        }
    }

    void menuIdioma(List<Pelis> pelis){

        while(true) {
            try {
                System.out.print("\n\n\t\t\t\t\t\tQuin tipus de búsqueda vols fer per Idioma: " +
                        "\n\t\t\t\t\t\t\t\t\tUn idioma en concret (1)" +
                        "\n\t\t\t\t\t\t\t\t\tTot els que hi ha (2)" +
                        "\n\t\t\t\t\t\t\t\t\tQue començi per (3) " +
                        "\n\t\t\t\t\t\t\t\t\tSortir del menu (4)" +
                        "\n\n\t\t\t\t\t\t\tOpcio: ");
                String opcio = sc.nextLine();

                switch (opcio){
                    case "1":
                        System.out.print("Quina idioma estàs buscant? : ");
                        setBusca( sc.nextLine());
                        sList = pelis.stream().filter(x -> x.getIdioma().equalsIgnoreCase(getBusca()))
                                .collect(Collectors.toList());
                        //.forEach(x-> System.out.println(x.getTitol()));
                        for(Pelis a: sList){
                            System.out.println(a.toString());
                        }
                        if (sList.size()==0){
                            System.out.println("\n\nNo s'han trobat resultats");
                        }
                        break;
                    case "2":

                        sList = pelis.stream().filter(x -> !x.getIdioma().isEmpty()).collect(Collectors.toList());
                        for(Pelis a: sList){
                            System.out.println(a.getIdioma());
                        }
                        if (sList.size()==0){
                            System.out.println("\n\nNo s'han trobat resultats");
                        }
                        break;
                    case "3":
                        System.out.print("Per quina lletra estàs buscant? : ");
                        setBusca( sc.nextLine());

                        sList = pelis.stream().filter(x -> x.getIdioma().startsWith(getBusca())).collect(Collectors.toList());
                        for(Pelis a: sList){
                            System.out.println(a.getIdioma());
                        }
                        if (sList.size()==0){
                            System.out.println("\n\nNo s'han trobat resultats");
                        }
                        break;
                    case "4":
                        return;
                    default: break;
                }

            }catch (Exception e){
                System.out.println("Introdueix un valor vàlid");
            }
        }
    }

    void menuDataEstrena(List<Pelis> pelis){

        while(true) {
            try {
                System.out.print("\n\n\t\t\t\t\t\tQuin tipus de búsqueda vols fer per Data Estrena: " +
                        "\n\t\t\t\t\t\t\t\t\tUn data en concret (1)" +
                        "\n\t\t\t\t\t\t\t\t\tTot els que hi ha (2)" +
                        "\n\t\t\t\t\t\t\t\t\tQue començi per (3) " +
                        "\n\t\t\t\t\t\t\t\t\tSortir del menu (4)" +
                        "\n\n\t\t\t\t\t\t\tOpcio: ");
                String opcio = sc.nextLine();

                switch (opcio){
                    case "1":
                        System.out.print("Quina data estrena estàs buscant? : ");
                        setBusca( sc.nextLine());
                        sList = pelis.stream().filter(x -> x.getDataEstrena().equalsIgnoreCase(getBusca()))
                                .collect(Collectors.toList());
                        //.forEach(x-> System.out.println(x.getTitol()));
                        for(Pelis a: sList){
                            System.out.println("\nData estrena: " + a.getTitol());
                            System.out.println(a.getDataEstrena());
                        }
                        if (sList.size()==0){
                            System.out.println("\n\nNo s'han trobat resultats");
                        }
                        break;
                    case "2":
                        sList = pelis.stream().filter(x -> !x.getDataEstrena().isEmpty()).collect(Collectors.toList());
                        for(Pelis a: sList){
                            System.out.println("\nData estrena: " + a.getTitol());
                            System.out.println(a.getDataEstrena());
                        }
                        if (sList.size()==0){
                            System.out.println("\n\nNo s'han trobat resultats");
                        }
                        break;
                    case "3":
                        System.out.print("Per quina lletra estàs buscant? : ");
                        setBusca( sc.nextLine());

                        sList = pelis.stream().filter(x -> x.getDataEstrena().startsWith(getBusca())).collect(Collectors.toList());
                        for(Pelis a: sList){
                            System.out.println(a.getDataEstrena());
                        }
                        if (sList.size()==0){
                            System.out.println("\n\nNo s'han trobat resultats");
                        }
                        break;
                    case "4":
                        return;
                    default: break;
                }

            }catch (Exception e){
                System.out.println("Introdueix un valor vàlid");
            }
        }
    }

    void menuInterprets(List<Pelis> pelis){

        while(true) {
            try {
                System.out.print("\n\n\t\t\t\t\t\tQuin tipus de búsqueda vols fer per Interprets: " +
                        "\n\t\t\t\t\t\t\t\t\tUn actor en concret (1)" +
                        "\n\t\t\t\t\t\t\t\t\tTot els que hi ha (2)" +
                        "\n\t\t\t\t\t\t\t\t\tSortir del menu (3)" +
                        "\n\n\t\t\t\t\t\t\tOpcio: ");
                String opcio = sc.nextLine();

                switch (opcio){
                    case "1":
                        Stream<String> bloques = null;
                        List<Pelis> llista = new ArrayList<>();
                        System.out.print("Quin actor estàs buscant? : ");
                        setBusca( sc.nextLine());

                        sList = pelis.stream().filter(x -> !x.getInterprets().isEmpty()).collect(Collectors.toList());
                        for(Pelis a: sList){
                            bloques= Pattern.compile(",").splitAsStream(a.getInterprets().toString());
                            bloques.map(cadena->cadena.substring(1, cadena.length()))
                                    .filter(x->x.contains(getBusca()))
                                    .forEach(System.out::println);
                        }
                        if (sList.size()==0){
                            System.out.println("\n\nNo s'han trobat resultats");
                        }
                        break;
                    case "2":
                        sList = pelis.stream().filter(x -> !x.getInterprets().isEmpty()).collect(Collectors.toList());
                        for(Pelis a: sList){
                            Stream<String> bloques2 = Pattern.compile(",").splitAsStream(a.getInterprets().toString()
                                    .replace("]"," "));
                            bloques2.map(cadena->cadena.substring(1, cadena.length()))
                                    .sorted((o1, o2)->o1.
                                            compareTo(o2))
                                    .forEach(System.out::println);
                        }
                        if (sList.size()==0){
                            System.out.println("\n\nNo s'han trobat resultats");
                        }
                        break;
                    case "3":
                        return;
                    default: break;
                }

            }catch (Exception e){
                System.out.println("Introdueix un valor vàlid");
            }
        }
    }
}
