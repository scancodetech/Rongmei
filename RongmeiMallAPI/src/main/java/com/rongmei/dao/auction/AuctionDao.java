package com.rongmei.dao.auction;

import com.rongmei.entity.auction.Auction;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface AuctionDao extends JpaRepository<Auction, Integer> ,JpaSpecificationExecutor<Auction> {

  Auction findFirstBySaleIdOrderByPriceDesc(int saleId);

  @Query(nativeQuery = true,value = "select * from auction order by create_time desc")
  Page<Auction> findAllDesc(Pageable pageable);

  @Query(nativeQuery = true,value = "select max(price) from auction where sale_id = ?1")

  Double findBySaleIdMax(Integer saleId);

  List<Auction> findBySaleId(int saleId);

  Auction findFirstBySaleIdAndUsernameOrderByPriceDesc(int saleId, String username);

  List<Auction> findAllBySaleIdOrderByCreateTimeDesc(int saleId);

  List<Auction> findAllByUsernameAndSaleIdInOrderByCreateTimeDesc(String username,
      List<Integer> saleIds);

  @Query(nativeQuery = true, value = "select count(*) from auction as a join thing as t on a.thing_id=t.id where t.owner = ?1 and a.create_time between ?2 and ?3")
  long countAllByAuctionSaleOwner(String username, long startTime, long endTime);
}
