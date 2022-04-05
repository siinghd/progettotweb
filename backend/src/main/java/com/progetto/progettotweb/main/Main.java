package com.progetto.progettotweb.main;
import com.progetto.progettotweb.controllers.*;
import com.progetto.progettotweb.models.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        UtenteController utenteController = new UtenteController();
        RipetizioneController ripetizioneController = new RipetizioneController();
        DocenteController docenteController = new DocenteController();
        CorsoController corsoController = new CorsoController();
        CorsoDocenteController corsoDocenteController = new CorsoDocenteController();


        DAO d = DAO.Singleton();
        String exit = "si";
        while(exit.equalsIgnoreCase("si")){
            int selected = -1;
            while(selected==-1){
                System.out.println("Scegli un'opzione");

                System.out.println(" 2 - crea corso");
                System.out.println(" 3 - crea docente");
                System.out.println(" 4 - crea ripetizione");
                System.out.println(" 5 - Visualizza possibili ripetizioni");
                System.out.println(" 6 - Crea una ripetizioni");

                selected = scan.nextInt();
                scan.nextLine();
            }
            switch (selected){
                case 1:
                    System.out.println("Inserisci nome");
                    String accountname = scan.nextLine();
                    System.out.println("Inserisci la password");
                    String password = scan.nextLine();
                    System.out.println("Inserisci Ruolo");
                    byte ruolo = scan.nextByte();
                    scan.nextLine();
                    Utente u = new Utente(accountname,password,ruolo);
                    utenteController.insertUtente(u);
                    break;
                case 2:
                    System.out.println("Inserisci Titolo");
                    String titolo = scan.nextLine();
                    Corso c = new Corso(titolo,0);
                    corsoController.insertCorso(c);
                    break;
                case 3:
                    System.out.println("Inserisci nome");

                    String nome = scan.nextLine();
                    System.out.println("Inserisci cognome");
                    String cognome = scan.nextLine();
                    Docente docente = new Docente(nome,cognome,0);
                    docenteController.insertDocente(docente);
                    break;
                case 4:
                    System.out.println("Seleziona docente");
                    ArrayList<Docente> docen= docenteController.queryDBDocente();
                    int i = 0;
                    for (Docente doc:
                         docen) {
                        System.out.println(i++ +"-"+doc.getNome()+" "+doc.getCognome());
                    }

                    int iddoc = scan.nextInt();
                    System.out.println("Seleziona corso");
                    ArrayList<Corso> corsi= corsoController.queryDBCorso();
                    i = 0;
                    for (Corso cor:
                            corsi) {
                        System.out.println(i++ +"-"+cor.getTitolo());
                    }
                    int idcorso = scan.nextInt();
                    scan.nextLine();
                    CorsoDocente cd = new CorsoDocente(corsi.get(idcorso).getId(),docen.get(iddoc).getId(),corsi.get(idcorso).getSoftdelete());
                    corsoDocenteController.insertCorsoDocente(cd);
                case 5:
                    ArrayList<Ripetizione> rip= ripetizioneController.queryDBRipetizioniDisponibili();
                    for (int j = 0; j < rip.size(); j++) {
                        System.out.println(j + "-> "+rip.get(j).toString());
                    }
                    break;
                case 6:
                    System.out.println("Scegli una ripetizione inserento il numero");
                    ArrayList<Ripetizione> rip2= ripetizioneController.queryDBRipetizioniDisponibili();
                    for (int j = 0; j < rip2.size(); j++) {
                        System.out.println(j + "-> "+rip2.get(j).toString());
                    }
                    int selectd = scan.nextInt();
                    System.out.println("Scegli un utente");
                    ArrayList<Utente> user1 = utenteController.queryDBUtente("SELECT * FROM utente");
                    for (int k = 0; k < user1.size(); k++) {
                        System.out.println(k + "-> "+user1.get(k).toString());
                    }
                    int userselected = scan.nextInt();
                    scan.nextLine();
                    rip2.get(selectd).setIdutente(user1.get(userselected).getId());
                    ripetizioneController.insertRipetizione(rip2.get(selectd));

            }
            String exitask="";
            while (!exitask.equalsIgnoreCase("si") && !exitask.equalsIgnoreCase("no")){
                System.out.println("Vuoi continuare ad usare il programma? " +
                        "Inserisci si se intendi continuare  altrimenti inserisci no");
                exitask = scan.nextLine();

            }
            exit = exitask;


        }
        scan.close();
        d.closeConnection();

    }
}
