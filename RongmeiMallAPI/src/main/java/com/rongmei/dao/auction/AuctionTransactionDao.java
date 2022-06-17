package com.rongmei.dao.auction;

import com.rongmei.entity.auction.AuctionTransaction;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AuctionTransactionDao extends JpaRepository<AuctionTransaction, Integer> {

  List<AuctionTransaction> findAllByUsername(String username);

  @Query(nativeQuery = true, value = "select a.* from auction_transaction as a order by a.create_time desc limit ?1 offset ?2")
  List<AuctionTransaction> findAllOrderByCreateTimeDesc(int limit, int offset);
}
