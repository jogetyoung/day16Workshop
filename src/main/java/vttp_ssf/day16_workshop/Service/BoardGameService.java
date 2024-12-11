package vttp_ssf.day16_workshop.Service;

import jakarta.json.JsonArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vttp_ssf.day16_workshop.Model.BoardGame;
import vttp_ssf.day16_workshop.Repository.BoardGameRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class BoardGameService {

    @Autowired
    private BoardGameRepo boardGameRepo;

    public List<BoardGame> getAllBoardGames() {

        Set<String> ids = boardGameRepo.getAllGid();

        List<BoardGame> boardGamesList = new ArrayList<>();

        for (String id : ids) {
            BoardGame boardGame = boardGameRepo.getBoardGameById(Integer.parseInt(id));
            boardGamesList.add(boardGame);
        }

        return boardGamesList;
    }

    public BoardGame getBoardGameById(int id) {
        return boardGameRepo.getBoardGameById(id);
    }

    public void saveBoardGame(JsonArray boardGames) {
        boardGameRepo.saveBoardGame(boardGames);
    }

    public boolean boardGameExist(int id) {
        return boardGameRepo.boardGameExists(id);
    }

    public void updateBoardGame(BoardGame newBoardGame) {

        boardGameRepo.updateBoardGame(newBoardGame);
    }

    public void addBoardGame(BoardGame newBoardGame) {
        boardGameRepo.addBoardGame(newBoardGame);
    }
}
