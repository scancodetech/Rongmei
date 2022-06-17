package com.rongmei.dao.event;

import com.rongmei.entity.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventDao extends JpaRepository<Event, Integer> {

}
