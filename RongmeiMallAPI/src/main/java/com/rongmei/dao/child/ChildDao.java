package com.rongmei.dao.child;

import com.rongmei.entity.child.Child;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChildDao extends JpaRepository<Child, Integer> {

  @Query(nativeQuery = true, value = "select * from child where is_active = 1 order by create_time desc limit ?1 offset ?2")
  List<Child> findAllByLimitAndOffset(int limit, int offset);

  @Query(nativeQuery = true, value = "select * from child where is_active = 1 and username=?3 order by create_time desc limit ?1 offset ?2")
  List<Child> findAllByUsernameAndLimitAndOffset(int limit, int offset, String username);
}
