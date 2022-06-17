package com.rongmei.dao.account;

import com.rongmei.entity.account.Items;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemDao extends JpaRepository<Items, Integer> {
    Items findByName(String name);


}
