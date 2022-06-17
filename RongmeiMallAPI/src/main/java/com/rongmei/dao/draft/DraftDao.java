package com.rongmei.dao.draft;

import com.rongmei.entity.draft.Draft;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DraftDao extends JpaRepository<Draft, Integer> {

  Draft findFirstByRelationIdAndDraftType(long relationId, int draftType);
}
