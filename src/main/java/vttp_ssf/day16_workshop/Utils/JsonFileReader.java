//package vttp_ssf.day16_workshop.Utils;
//
//import jakarta.json.Json;
//import jakarta.json.JsonArray;
//import jakarta.json.JsonReader;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//import vttp_ssf.day16_workshop.Repository.BoardGameRepo;
//
//import java.io.InputStream;
//
//@Component
//public class JsonFileReader implements CommandLineRunner {
//
//    private static final String GAME_JSON = "/static/game.json";
//
//    @Autowired
//    private BoardGameRepo boardGameRepo;
//
//    @Override
//    public void run(String... args) {
//
//        try{
//            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(GAME_JSON);
//
//            JsonReader reader = Json.createReader(inputStream);
//
//            JsonArray jsonArray = reader.readArray();
//
//            boardGameRepo.saveBoardGame(jsonArray);
//
//            System.out.println(boardGameRepo.getAllGid());
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("data saved");
//    }
//
//}
