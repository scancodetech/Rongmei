package com.rongmei.dao.auction;

import com.rongmei.entity.auction.Thing;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ThingDao extends JpaRepository<Thing, Integer> {

  List<Thing> findAllByOwner(String username);

  List<Thing> findAllByAuthor(String author);

  @Query(nativeQuery = true, value = "select t.author from thing as t group by t.author order by count(t.author) desc limit ?1 offset ?2")
  List<String> findArtistsUsername(int limit, int offset);

  @Query(nativeQuery = true, value = "select t.owner from thing as t group by t.owner order by count(t.owner) desc limit ?1 offset ?2")
  List<String> findCollectorsUsername(int limit, int offset);

  @Query(nativeQuery = true, value = "select t.owner from thing as t join thing_tags as tt on t.id = tt.thing_id where tt.tags = tag group by t.owner order by count(t.owner) desc limit ?1 offset ?2")
  List<String> findCollectorsUsernameByTags(int limit, int offset, String tag);

  Thing findFirstByTokenId(String tokenId);

  @Query(nativeQuery = true, value = "select t.* from thing as t join favorites as f on t.favorites_id=f.id where f.is_active = false")
  List<Thing> findAllByFavoritesDefault();

  List<Thing> findAllByFavoritesId(int favoritesId);

  List<Thing> findAllByIdIn(Set<Integer> thingIds);
}
