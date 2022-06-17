package com.rongmei.dao.relation;

import com.rongmei.entity.relation.Favorites;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoritesDao extends JpaRepository<Favorites, Integer> {

  List<Favorites> findAllByIsActiveAndUsername(boolean active, String username);
}
