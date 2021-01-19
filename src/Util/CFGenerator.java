package Util;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class CFGenerator {


    private String firstName, lastName, municipality, gender;
    private int year, month, day;

    // Static arrays
    private final char[] evenList = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B',
            'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };


    private final int[] oddList = {1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 1, 0, 5, 7, 9, 13,
            15, 17, 19, 21, 2, 4, 18, 20, 11, 3, 6, 8, 12, 14, 16,
            10, 22, 25, 24, 23
    };

    private final HashMap<Integer, String> mesi = new HashMap<Integer, String>();

    /*
    private final String[][] months = {{"Gennaio", "A"},
            {"Febbraio", "B"},
            {"Marzo", "C"},
            {"Aprile", "D"},
            {"Maggio", "E"},
            {"Giugno", "H"},
            {"Luglio", "L"},
            {"Agosto", "M"},
            {"Settembre", "P"},
            {"Ottobre", "R"},
            {"Novembre", "S"},
            {"Dicembre", "T"}
    };
    */


    CFGenerator(String firstName, String lastName, String municipality, int month, int year, int day, String gender) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.municipality = municipality;
        this.month = month;
        this.year = year;
        this.day = day;
        this.gender = gender;

        mesi.put(1, "A");
        mesi.put(2, "B");
        mesi.put(3, "C");
        mesi.put(4, "D");
        mesi.put(5, "E");
        mesi.put(6, "H");
        mesi.put(7, "L");
        mesi.put(8, "M");
        mesi.put(9, "P");
        mesi.put(10, "R");
        mesi.put(11, "S");
        mesi.put(12, "T");

    }

    CFGenerator(String firstName, String lastName, String municipality, LocalDateTime date, String gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.municipality = municipality;
        this.month = date.getMonthValue();
        this.year = date.getYear();
        this.day = date.getDayOfMonth();
        this.gender = gender;

        mesi.put(1, "A");
        mesi.put(2, "B");
        mesi.put(3, "C");
        mesi.put(4, "D");
        mesi.put(5, "E");
        mesi.put(6, "H");
        mesi.put(7, "L");
        mesi.put(8, "M");
        mesi.put(9, "P");
        mesi.put(10, "R");
        mesi.put(11, "S");
        mesi.put(12, "T");

    }

    // Metogi getter per ottenere gli elementi della classe
    // Interfacce più comode ed ordinate per l'accesso alle funzionalità
    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------

    String getFirstName() {
        return modifyFirstAndLastName(firstName, true);
    }

    String getLastName() {
        return modifyFirstAndLastName(lastName, false);
    }

    String getNomeInserito() {
        return firstName;
    }

    String getCognomeInserito() {
        return lastName;
    }

    String getMonths() {
        return modifyMonth();
    }

    int getMeseInserito() {
        return month;
    }

    int getYear() {
        return (year % 100);
    }

    int getAnnoInserito() {
        return year;
    }

    int getDay() {
        return (gender.equals("M")) ? day : (day + 40);
    }

    int getGiornoInserito() {
        return day;
    }

    String getMunicipality() {
        return retrieveMunicipalityCode();
    }

    String getControlCode() {
        return calculateControlCode();
    }

    String getCodiceFiscale() {
        return toString();
    }
    // -----------------------------------------------------------------------------------------------------------------------------------------------------------


    // I seguenti metodi svolgono le operazioni specifiche sui dati

    /**
     * @param string Corrisponde al nome/cognome da modificare
     * @param cod     Se cod e' true, indica il nome; altrimenti il cognome
     * @return nuovaStringa       Restituisce la string modificata
     */
    // -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    private String modifyFirstAndLastName(String string, boolean cod) {
        String newString = "";
        string = string.replaceAll(" ", "");           // Rimuovo eventuali spazi
        string = string.toLowerCase();

        String consonants = getConsonants(string);      // Ottengo tutte le consonants e tutte le vowels della string
        String vowels = getVowels(string);

        // Controlla i possibili casi
        if (consonants.length() == 3) {                   // La string contiene solo 3 consonants, quindi ho gia' la modifica
            newString = consonants;
        }
        // Le consonants non sono sufficienti, e la stinga e' più lunga o
        // uguale a 3 caratteri [aggiungo le vowels mancanti]
        else if ((consonants.length() < 3) && (string.length() >= 3)) {
            newString = consonants;
            newString = addVowels(newString, vowels);
        }
        // Le consonants non sono sufficienti, e la string
        //contiene meno di 3 caratteri [aggiungo consonants e vowels, e le x]
        else if ((consonants.length() < 3) && (string.length() < 3)) {
            newString = consonants;
            newString += vowels;
            newString = addXs(newString);
        }
        // Le consonants sono in eccesso, prendo solo le
        //prime 3 nel caso del cognome; nel caso del nome la 0, 2, 3
        else if (consonants.length() > 3) {
            // true indica il nome e false il cognome
            if (!cod) newString = consonants.substring(0, 3);
            else newString = consonants.charAt(0) + "" + consonants.charAt(2) + "" + consonants.charAt(3);
        }

        return newString;
    }
    // -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    // Add Xs until a total number of 3 characters has been reached
    // -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    private String addXs(String string) {
        while (string.length() < 3) {
            string += "X";
        }
        return string;
    }
    // --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    // Aggiunge le vowels alla string passata per parametro
    // --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    private String addVowels(String string, String vowels) {
        int index = 0;
        while (string.length() < 3) {
            string += vowels.charAt(index);
            index++;
        }
        return string;
    }
    // --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    // Toglie dalla string tutte le consonanti
    // --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    private String getVowels(String string) {
        string = string.replaceAll("[^aeiou]", "");
        return string;
    }
    // --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    // Toglie dalla string tutte le vocali
    // --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    private String getConsonants(String string) {
        string = string.replaceAll("[aeiou]", "");
        return string;
    }
    // --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    // Restituisce il codice del mese
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
    private String modifyMonth() {
        for (int i = 0; i < months.length; i++) {
            if (months[i][0].equalsIgnoreCase(month))
                return months[i][1];
        }
        return null;
    }
    */

    private String modifyMonth(){

        String monthCode = mesi.get(this.month);
        return monthCode;

    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    // Elabora codice del comune
    // ------ --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    private String retrieveMunicipalityCode() {
        String municipalityCode = "";
        try {
            Scanner scanner = new Scanner(new File("Resources/municipalities.txt"));
            scanner.useDelimiter(":");

            while (scanner.hasNext()) {
                String s1 = scanner.nextLine();
                String s2 = s1.substring(0, s1.indexOf('-') - 1);
                System.out.println(s2);
                if (s2.equalsIgnoreCase(municipality)) {
                    municipalityCode = s1.substring(s1.lastIndexOf(' ') + 1);
                }
            }

            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return municipalityCode;
    }
    // ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    // Calcolo del Codice di Controllo
    // ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    private String calculateControlCode() {

        String string = getLastName().toUpperCase() + getFirstName().toUpperCase() + getYear() + getMonths() + getDay() + getMunicipality();

        int even = 0, odd = 0;

        for (int i = 0; i < string.length(); i++) {
            char ch = string.charAt(i);              // i-esimo carattere della stringa

            // Il primo carattere e' il numero 1 non 0
            if ((i + 1) % 2 == 0) {
                int index = Arrays.binarySearch(evenList, ch);
                even += (index >= 10) ? index - 10 : index;
            } else {
                int index = Arrays.binarySearch(evenList, ch);
                odd += oddList[index];
            }
        }

        int check = (even + odd) % 26;
        check += 10;

        return evenList[check] + "";
    }
    // ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    // Viene richiamato per una stampa
    public String toString() {
        return getLastName().toUpperCase() + getFirstName().toUpperCase() + getYear() + getMonths() + getDay() + getMunicipality() + getControlCode();
    }

}