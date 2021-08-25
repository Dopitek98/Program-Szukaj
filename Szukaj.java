import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Szukaj extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        TextField poletekstowe = new TextField();
        poletekstowe.setPromptText("Podaj slowo jakie chcesz znalesc w tekscie");
        poletekstowe.setMinSize(100,50);

        TextField sciezka = new TextField();
        sciezka.setPromptText("podaj sciezke do pliku");
        sciezka.setMinSize(100,50);


        ListView<String> listView = new ListView<>();

        Button restart = new Button("RESTART");
        Button otworz = new Button("otworz plik");
        Button szukajpliku = new Button("Szukaj pliku");

        restart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
              //poletekstowe.setText(null);
                poletekstowe.setText("");
              poletekstowe.setPromptText("Podaj slowo jakie chcesz znalesc w tekscie");
              //sciezka.setText(null);
              sciezka.setText("");
              sciezka.setPromptText("Podaj sciezke do pliku");
              listView.getItems().remove(0,listView.getItems().size());
            }
        });
        szukajpliku.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                JFileChooser znajdzplik = new JFileChooser();
                znajdzplik.setCurrentDirectory(new File("C:\\Users"));
                int udane = znajdzplik.showOpenDialog(null); // wybor pliku do otworzenia
                if(udane==JFileChooser.APPROVE_OPTION){
                    File file = new File(znajdzplik.getSelectedFile().getAbsolutePath());
                    String path = file.getPath();
                    sciezka.setText(path);
                }
            }
        });
        otworz.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String tekst = poletekstowe.getText();
                String file = sciezka.getText();

                try {
                    FileReader czytam = new FileReader(file);
                    BufferedReader buffer = new BufferedReader(czytam);
                    System.out.println("podaj tekst");

                    Boolean zawiera = false;
                    String linia;
                    List<String> tablica = new ArrayList<>();
                    List<Integer> tab = new ArrayList<>();
                    List<String> strings = new ArrayList<>();
                    String doWyniku = null;
                    int ilosc_znaków=0,numer_lini=1;
                    while((linia = buffer.readLine()) != null){
                        System.out.println(linia);
                        ilosc_znaków+=linia.length();
                        numer_lini++;
                        if(linia.contains(tekst))
                        {
                            tab.add(numer_lini);
                            tablica.add(linia);
                            zawiera = true;
                        }
                    }
                    if(zawiera == true){
                        System.out.println("\n zawiera dana fraze");
                    }else if(zawiera == false){
                        System.out.println("nie zawiera");
                        listView.getItems().add(0,"Nie zawiera takiego znaku");
                    }
                    for(int i=0;i<tab.size();i++){
                        System.out.println("Nr lini"+tab.get(i)+" "+tablica.get(i));
                        strings.add("Nr lini"+tab.get(i)+" "+tablica.get(i)+"\n");
                        listView.getItems().add(i,"Nr lini"+tab.get(i)+" "+tablica.get(i)+"\n");
                    }
                    buffer.close();

                } catch (FileNotFoundException e) {
                    //e.printStackTrace();
                    System.out.println("Nie znaleziono pliku");
                    listView.getItems().add(0,"Nie znaleziono pliku");
                }catch (IOException e){
                    System.out.println("Problem z IO");
                    listView.getItems().add(0,"Problem z IO");
                }catch (NullPointerException e){
                    System.out.println("Wymagany restart programu");
                    listView.getItems().add(0,"Wymagany restart programu");
                }
            }
        });

        VBox grupa = new VBox();
        grupa.getChildren().add(restart);
        grupa.getChildren().add(poletekstowe);
        grupa.getChildren().add(sciezka);
        grupa.getChildren().add(otworz);
        grupa.getChildren().add(szukajpliku);
        grupa.getChildren().add(listView);


        Scene scene = new Scene(grupa,1280,720);

        primaryStage.setTitle("Program Szukaj");
        primaryStage.setScene(scene);
        primaryStage.show();


    }
}
