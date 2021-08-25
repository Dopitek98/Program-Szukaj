import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
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
        poletekstowe.setMinSize(400,50);
        poletekstowe.setLayoutY(70);
        poletekstowe.setLayoutX(10);

        TextField sciezka = new TextField();
        sciezka.setPromptText("Podaj sciezke do pliku");
        sciezka.setMinSize(400,50);
        sciezka.setLayoutY(10);
        sciezka.setLayoutX(10);

        ListView<String> listView = new ListView<>();
        listView.setMinSize(730,500);
        listView.setLayoutY(130);
        listView.setLayoutX(10);

        Button restart = new Button("RESTART");
        restart.setMinSize(100,110);
        restart.setLayoutY(10);
        restart.setLayoutX(640);
        Button otworz = new Button("Otworz plik");
        otworz.setMinSize(100,110);
        otworz.setLayoutY(10);
        otworz.setLayoutX(420);
        Button szukajpliku = new Button("Szukaj pliku");
        szukajpliku.setMinSize(100,110);
        szukajpliku.setLayoutY(10);
        szukajpliku.setLayoutX(530);

        restart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                poletekstowe.setText("");
              poletekstowe.setPromptText("Podaj slowo jakie chcesz znalesc w tekscie");
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
                int udane = znajdzplik.showOpenDialog(null);
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

                    Boolean zawiera = false;
                    String linia;
                    List<String> tablica = new ArrayList<>();
                    List<Integer> tab = new ArrayList<>();
                    List<String> strings = new ArrayList<>();
                    int ilosc_znaków=0,numer_lini=1;
                    while((linia = buffer.readLine()) != null){
                        ilosc_znaków+=linia.length();
                        numer_lini++;
                        if(linia.contains(tekst))
                        {
                            tab.add(numer_lini);
                            tablica.add(linia);
                            zawiera = true;
                        }
                    }
                    if(zawiera == false){
                        listView.getItems().add(0,"Nie zawiera takiego znaku");
                    }
                    for(int i=0;i<tab.size();i++){
                        strings.add("Nr lini"+tab.get(i)+" "+tablica.get(i)+"\n");
                        listView.getItems().add(i,"Nr lini"+tab.get(i)+" "+tablica.get(i)+"\n");
                    }
                    buffer.close();

                } catch (FileNotFoundException e) {
                    listView.getItems().add(0,"Nie znaleziono pliku");
                }catch (IOException e){
                    listView.getItems().add(0,"Problem z IO");
                }catch (NullPointerException e){
                    listView.getItems().add(0,"Wymagany restart programu");
                }
            }
        });

        Group grupa = new Group();

        grupa.getChildren().add(restart);
        grupa.getChildren().add(poletekstowe);
        grupa.getChildren().add(sciezka);
        grupa.getChildren().add(otworz);
        grupa.getChildren().add(szukajpliku);
        grupa.getChildren().add(listView);

        Scene scene = new Scene(grupa,750,640, Color.valueOf("4876FF"));

        primaryStage.setTitle("Program Szukaj");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
