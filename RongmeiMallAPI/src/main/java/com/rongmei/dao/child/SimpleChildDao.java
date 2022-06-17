package com.rongmei.dao.child;

import com.rongmei.entity.child.SimpleChild;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SimpleChildDao extends JpaRepository<SimpleChild, Integer> {

  @Query(nativeQuery = true, value = "select s.* from simple_chlid as s where s.author = ?1 and s.is_active = 1 order by s.create_time desc limit ?2 offset ?3")
  List<SimpleChild> findAllByAuthorByPage(String username, int limit, int offset);

  @Query(nativeQuery = true, value = "select count(s.id) from simple_chlid as s where s.author = ?1 and s.is_active = 1")
  long countAllByAuthor(String username);

  @Query(nativeQuery = true, value = "select s.* from simple_chlid as s where s.is_active = 1 order by s.create_time desc limit ?1 offset ?2")
  List<SimpleChild> findAllByPage(int limit, int offset);

  @Query(nativeQuery = true, value = "select count(s.id) from simple_chlid as s where s.is_active = 1")
  long countAllActive();

  List<SimpleChild> findAllByDraftStatusInAndIsActive(List<Integer> draftStatus, boolean isActive);

  @Query(nativeQuery = true, value = "select s.* from simple_chlid as s join simple_child_topics as sct on s.id = sct.simple_child_id where s.is_active = 1 and sct.topics = ?1 order by s.create_time desc limit ?2 offset ?3")
  List<SimpleChild> findAllByTopicAndPageLimit(String topic, int limit, int offset);

  @Query(nativeQuery = true, value = "select count(s.id) from simple_chlid as s join simple_child_topics as sct on s.id = sct.simple_child_id where s.is_active = 1 and sct.topics = ?1")
  long countAllByTopic(String topic);

  @Query(value = "select topics, count(*) from simple_child_topics group by topics order by count(*) desc limit ?1 offset ?2", nativeQuery = true)
  List<Object[]> selectTopTopic(int limit, int offset);

  @Query(value = "select count(*) from (select topics, count(*) from simple_child_topics group by topics) as topic_group", nativeQuery = true)
  long selectTopicCount();
}
