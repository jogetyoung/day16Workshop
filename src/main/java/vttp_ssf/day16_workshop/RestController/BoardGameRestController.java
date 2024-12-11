package vttp_ssf.day16_workshop.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vttp_ssf.day16_workshop.Model.BoardGame;
import vttp_ssf.day16_workshop.Repository.BoardGameRepo;
import vttp_ssf.day16_workshop.Service.BoardGameService;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/boardgame")
public class BoardGameRestController {

    @Autowired
    private BoardGameService boardGameService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postBoardGame(
            @RequestBody String json) {

        JsonReader jReader = Json.createReader(new StringReader(json));

        boardGameService.saveBoardGame(jReader.readArray());

        JsonObject response = Json.createObjectBuilder()
                .add("insert_count", 1)
                .add("id", "gid")
                .build();

        return ResponseEntity.status(201).body(response.toString());
    }

    @GetMapping(value = "/{gid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getBoardGameById(@PathVariable("gid") int id) {

        BoardGame boardGame = boardGameService.getBoardGameById(id);

        if (boardGame != null) {
            return new ResponseEntity<>(boardGame, HttpStatus.OK);
        } else {

            JsonObject errorResponse = Json.createObjectBuilder()
                    .add("Error", "Boardgame not found")
                    .add("Message", "Board game with ID " + id + " does not exist")
                    .build();

            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body(errorResponse.toString());
        }
    }

    @PutMapping(value = "/{gid}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> putBoardGame(@PathVariable("gid") int id,
                                          @RequestBody String jsonPayload,
                                          @RequestParam(name = "upsert", required = false) boolean upsert) {


//        if (!boardGameService.boardGameExist(id)) {
//            return new ResponseEntity<>(HttpStatusCode.valueOf(400).toString(), HttpStatus.BAD_REQUEST);
//        } else {
//
//            boardGameService.updateBoardGame(boardGameService.getBoardGameById(id));
//
//            JsonObject response = Json.createObjectBuilder()
//                    .add("update_count", 1)
//                    .add("id", "gid")
//                    .build();
//
//            return ResponseEntity.status(200).body(response.toString());
//        }

        if (boardGameService.boardGameExist(id)){
            //if board game exists, then update it

            boardGameService.updateBoardGame(boardGameService.getBoardGameById(id));

            JsonObject response = Json.createObjectBuilder()
                    .add("update count", 1)
                    .add("id", "gid")
                    .build();

            return ResponseEntity.status(200).body(response.toString());
//        } else if (upsert){
//            //if board game does not exist and upsert is true, create a new board game
//
//            boardGameService.addBoardGame(boardGameService.getBoardGameById(id));
//
//            JsonObject response = Json.createObjectBuilder()
//                    .add("insert_count", 1)
//                    .add("id", id)
//                    .build();
//
//            return ResponseEntity.status(201).body(response.toString());

        } else {
            //if board game does not exist and upsert is false, return error message

            return ResponseEntity.status(HttpStatusCode.valueOf(400)).body("Boardgame with ID " + id + " does not exist");
        }

    }
}
