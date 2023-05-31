package org.assignemnt;

import org.assignemnt.GUI.Gui;
import org.assignemnt.model.Directory;
import org.assignemnt.utility.Chrono;
import org.assignemnt.utility.Pair;
import org.assignemnt.utility.Printer;
import org.assignemnt.utility.analyzer.SourceAnalyzer;
import org.assignemnt.utility.analyzer.SourceAnalyzerImpl;

import java.io.File;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException {

        SourceAnalyzer sourceAnalyzer = new SourceAnalyzerImpl();
        Printer printer = new Printer();
        Chrono chrono = new Chrono();
        Scanner scan = new Scanner(System.in);

        System.out.println("Seleziona modalità: \n 1) Riga di comando \n 2) GUI \n");
        int choose = Integer.parseInt(scan.nextLine());

        if (choose == 1) {
            System.out.println("Selezionato: Riga di comando");

            System.out.println("Inserisci percorso: ");
            //final String D = "C:/Users/david/Desktop/TestFolder2"; //args[0] - Percorso iniziale
            String D = scan.nextLine();
            D = D.replace("\\", "/");

            System.out.println("Elementi da visualizzare della mappa: ");
            //final int N = 5; //N elementi da visualizzare della mappa
            int N = scan.nextInt();

            System.out.println("Limite massimo: ");
            //final int MAXL = 1000; //args[2] - Limite massimo
            int MAXL = scan.nextInt();

            System.out.println("Numero intervalli: ");
            //final int NI = 5; //args[1] - Numero intervalli
            int NI = scan.nextInt();

            System.out.println("Calcolo in corso...\n");

            chrono.start();

            CompletableFuture<Pair<TreeSet<Pair<File, Long>>, Map<Pair<Integer, Integer>, Integer>>> future = sourceAnalyzer.getReport(new Directory(D), MAXL, NI);

            Pair<TreeSet<Pair<File, Long>>, Map<Pair<Integer, Integer>, Integer>> res = future.get();

            chrono.stop();

            printer.printFileLength(res.getX(), N);
            printer.printInterval(res.getY());

            System.out.println("Tempo impiegato: " + chrono.getTime());

        } else if (choose == 2) {
            System.out.println("Selezionato: GUI");
            Gui gui = new Gui();
            gui.setVisible(true);
        } else {
            System.out.println("Errore: Input errato");
        }
    }
}
