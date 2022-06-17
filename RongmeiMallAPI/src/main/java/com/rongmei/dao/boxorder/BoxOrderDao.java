package com.rongmei.dao.boxorder;

import com.rongmei.entity.boxorder.BoxOrder;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoxOrderDao extends JpaRepository<BoxOrder, Integer> {

  @Query(nativeQuery = true, value = "select * from box_order as bo where bo.status = ?1 order by bo.create_time desc limit ?2 offset ?3")
  List<BoxOrder> findBoxOrderByStatusAndPageLimit(int status, int limit, int offset);

  @Query(nativeQuery = true, value = "select count(*) from box_order as bo where bo.status = ?1")
  long countBoxOrderByStatus(int status);

  @Query(nativeQuery = true, value = "select * from box_order as bo where bo.status = ?1 and bo.accept_username = ?2 order by bo.create_time desc limit ?3 offset ?4")
  List<BoxOrder> findBoxOrderByStatusAndAcceptUsernameAndPageLimit(int status, String username,
      int limit,
      int offset);

  @Query(nativeQuery = true, value = "select count(*) from box_order as bo where bo.status = ?1 and bo.accept_username = ?2")
  long countBoxOrderByStatusAndAcceptUsername(int status, String username);

  @Query(nativeQuery = true, value = "select * from box_order as bo where bo.username = ?1 order by bo.create_time desc limit ?2 offset ?3")
  List<BoxOrder> findBoxOrderByUsernameAndPageLimit(String username, int limit,
      int offset);

  @Query(nativeQuery = true, value = "select count(*) from box_order as bo where bo.username = ?1")
  long countBoxOrderByUsernameAndPageLimit(String username);

  @Query(nativeQuery = true, value = "select * from box_order as bo where bo.username = ?1 and bo.pay_status = ?4 and bo.status = ?5 order by bo.create_time desc limit ?2 offset ?3")
  List<BoxOrder> findBoxByPayStatusAndStatusOrderByUsernameAndPageLimit(String username, int limit,
      int offset, int payStatus, int status);

  @Query(nativeQuery = true, value = "select count(*) from box_order as bo where bo.username = ?1 and bo.pay_status = ?2 and bo.status = ?3")
  long countBoxByPayStatusAndStatusOrderByUsername(String username, int payStatus, int status);

  @Query(nativeQuery = true, value = "select * from box_order as bo where bo.username = ?1 and bo.pay_status = ?4 order by bo.create_time desc limit ?2 offset ?3")
  List<BoxOrder> findBoxByPayStatusOrderByUsernameAndPageLimit(String username, int limit,
      int offset, int payStatus);

  @Query(nativeQuery = true, value = "select count(*) from box_order as bo where bo.username = ?1 and bo.pay_status = ?2")
  long countBoxByPayStatusOrderByUsername(String username, int payStatus);


  List<BoxOrder> findAllByUsernameIsNotAndShareUsernamesNotContains(String username1,
      String username2);

  List<BoxOrder> findAllByUsernameIsNotAndShareEndTimeAfter(String username, long time);

}
