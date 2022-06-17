package com.rongmei.dao.event;

import com.rongmei.entity.draft.Draft;
import com.rongmei.entity.eventorder.EventOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventOrderDao extends JpaRepository<EventOrder, Long> {
}
