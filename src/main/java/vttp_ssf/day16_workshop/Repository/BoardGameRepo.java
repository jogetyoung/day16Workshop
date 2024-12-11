package vttp_ssf.day16_workshop.Repository;

import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import vttp_ssf.day16_workshop.Model.BoardGame;

import java.util.Map;
import java.util.Set;

@Repository
public class BoardGameRepo {

    @Autowired
    @Qualifier("redis-0")
    private RedisTemplate<String, String> redisTemplate;

    public void saveBoardGame(JsonArray jsonArray) {

        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();

        for (int i = 0; i < jsonArray.size(); i++) {

            JsonObject jsonObject = jsonArray.getJsonObject(i);

            String gid = String.valueOf(jsonObject.getJsonNumber("gid"));

            // Store each attribute of the Board Game item in the Redis hash
            hashOps.put(gid, "name", jsonObject.getString("name"));
            hashOps.put(gid, "year", String.valueOf(jsonObject.getInt("year")));
            hashOps.put(gid, "ranking", String.valueOf(jsonObject.getInt("ranking")));
            hashOps.put(gid, "users_rated", String.valueOf(jsonObject.getInt("users_rated")));
            hashOps.put(gid, "url", jsonObject.getString("url"));
            hashOps.put(gid, "image", jsonObject.getString("image"));


        }
    }

    public BoardGame getBoardGameById(int gid) {
        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();

        if(!redisTemplate.hasKey(String.valueOf(gid))) {

            return null;

        }

        Map<String, String> info = hashOps.entries(String.valueOf(gid));

        Integer year = Integer.valueOf(info.get("year"));
        Integer ranking = Integer.valueOf(info.get("ranking"));
        Integer usersRated = Integer.valueOf(info.get("users_rated"));

        return new BoardGame(gid,
                info.get("name"),
                year,
                ranking,
                usersRated,
                info.get("url"),
                info.get("image")
                );
    }

    public Set<String> getAllGid() {

        return redisTemplate.keys("*");
    }

    public boolean boardGameExists(int gid) {

        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
        return redisTemplate.hasKey(String.valueOf(gid));

    }

    public void updateBoardGame(BoardGame boardGame) {

        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();

        String gid = String.valueOf(boardGame.getGid());

        hashOps.put(gid, "name", boardGame.getName());
        hashOps.put(gid, "year", String.valueOf(boardGame.getYear()));
        hashOps.put(gid, "ranking", String.valueOf(boardGame.getRanking()));
        hashOps.put(gid, "users_rated", String.valueOf(boardGame.getUsers_rated()));
        hashOps.put(gid, "url", boardGame.getUrl());
        hashOps.put(gid, "image", boardGame.getImage());
    }

    public void addBoardGame(BoardGame boardGame) {

        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();

        String gid = String.valueOf(boardGame.getGid());

        hashOps.put(gid, "name", boardGame.getName());
        hashOps.put(gid, "year", String.valueOf(boardGame.getYear()));
        hashOps.put(gid, "ranking", String.valueOf(boardGame.getRanking()));
        hashOps.put(gid, "users_rated", String.valueOf(boardGame.getUsers_rated()));
        hashOps.put(gid, "url", boardGame.getUrl());
        hashOps.put(gid, "image", boardGame.getImage());
    }
}
