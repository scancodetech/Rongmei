package com.rongmei.dao.order;

import com.rongmei.entity.order.Order;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface OrderDao extends JpaRepository<Order, Integer> , JpaSpecificationExecutor<Order> {

  List<Order> findAllByUserGroupIdOrRelationIdIn(int userGroupId, List<Integer> relationIds);

  List<Order> findAllByCustomerAndOrderType(String customer, String orderType);

  List<Order> findAllByCustomerAndStatusAndOrderType(String customer, String status,
      String orderType);

  Order findFirstByCustomerAndStatusAndOrderTypeAndRelationId(String customer, String status,
      String orderType, int relationId);

  List<Order> findAllByFavoritesIdAndCustomer(int favoritesId, String username);

  @Query(nativeQuery = true, value = "select o.* from user_group_order as o join favorites as f on o.favorites_id=f.id where f.is_active = false and o.customer = ?1")
  List<Order> findAllByFavoritesDefault(String username);

  List<Order> findAllByCustomer(String username);

  Order findFirstByCustomerAndRelationId(String username, int relationId);

  long countAllByRelationIdIn(List<Integer> relationIds);

  @Query(nativeQuery = true,value = "select * from user_group_order where create_time <= ?1")
    List<Order> findByCreatrTime(Long createTime);

}
