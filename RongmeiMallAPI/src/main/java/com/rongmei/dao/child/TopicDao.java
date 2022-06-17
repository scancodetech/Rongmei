package com.rongmei.dao.child;

import com.rongmei.entity.child.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicDao extends JpaRepository<Topic, Integer> {

  Topic findFirstByTopic(String topic);
}
