package com.rongmei.dao.blindboxnft;

import com.rongmei.entity.blindboxnft.BlindBoxNFT;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BlindBoxNFTDao extends JpaRepository<BlindBoxNFT, Integer> {

  BlindBoxNFT findFirstByTokenId(String tokenId);

//  BlindBoxNFT findBy(int tokenId);

  List<BlindBoxNFT> findAllByThemeAndClassification(String theme, String classification);

  @Query(nativeQuery = true, value = "select b.* from blind_box_nft as b where b.owner = ?1 order by b.update_time desc limit ?2 offset ?3")
  List<BlindBoxNFT> findAllByOwnerLimitOffset(String owner, int limit, int offset);

  List<BlindBoxNFT> findAllByAuthorAndOwnerAndBoxEggId(String author,String owner,int boxEggId);

  List<BlindBoxNFT> findAllByOwner(String username);

    List<BlindBoxNFT> findAllByBoxEggId(int boxEggId);
}
