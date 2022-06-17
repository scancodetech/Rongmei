package com.rongmei.bl.child;

import com.rongmei.blservice.child.SimpleChildBlService;
import com.rongmei.dao.child.SimpleChildDao;
import com.rongmei.dao.child.TopicDao;
import com.rongmei.entity.child.SimpleChild;
import com.rongmei.entity.child.Topic;
import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.parameters.child.SimpleChildUpdateParameters;
import com.rongmei.parameters.child.TopicUpdateParameters;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.child.SimpleChildGetResponse;
import com.rongmei.response.child.SimpleChildItem;
import com.rongmei.response.child.TopicDetailResponse;
import com.rongmei.response.child.TopicItem;
import com.rongmei.response.child.TopicsGetResponse;
import com.rongmei.util.UserInfoUtil;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SimpleChildBlServiceImpl implements SimpleChildBlService {

  private final SimpleChildDao simpleChildDao;
  private final TopicDao topicDao;

  @Autowired
  public SimpleChildBlServiceImpl(SimpleChildDao simpleChildDao,
      TopicDao topicDao) {
    this.simpleChildDao = simpleChildDao;
    this.topicDao = topicDao;
  }

  @Override
  public SuccessResponse updateChild(SimpleChildUpdateParameters parameters)
      throws ThingIdDoesNotExistException {
    SimpleChild simpleChild;
    if (parameters.getId() == 0) {
      simpleChild = new SimpleChild(parameters.getCoverUrl(), parameters.getContent(),
          parameters.getTopics(), System.currentTimeMillis(), System.currentTimeMillis(),
          UserInfoUtil.getUsername(), true, 0);
    } else {
      Optional<SimpleChild> optionalSimpleChild = simpleChildDao.findById(parameters.getId());
      if (optionalSimpleChild.isPresent()) {
        simpleChild = optionalSimpleChild.get();
        simpleChild.setCoverUrl(parameters.getCoverUrl());
        simpleChild.setContent(parameters.getContent());
        simpleChild.setUpdateTime(System.currentTimeMillis());
      } else {
        throw new ThingIdDoesNotExistException();
      }
    }
    simpleChildDao.save(simpleChild);
    return new SuccessResponse("update child success");
  }

  @Override
  public SuccessResponse deleteChild(int id) throws ThingIdDoesNotExistException {
    Optional<SimpleChild> optionalSimpleChild = simpleChildDao.findById(id);
    if (optionalSimpleChild.isPresent()) {
      SimpleChild simpleChild = optionalSimpleChild.get();
      simpleChild.setUpdateTime(System.currentTimeMillis());
      simpleChild.setActive(false);
      return new SuccessResponse("delete success");
    } else {
      throw new ThingIdDoesNotExistException();
    }
  }

  @Override
  public SimpleChildGetResponse getUserChild(int page, int limit, String username) {
    List<SimpleChild> simpleChildList = simpleChildDao
        .findAllByAuthorByPage(username, limit, (page - 1) * limit);
    long count = simpleChildDao.countAllByAuthor(username);
    return new SimpleChildGetResponse(packSimpleChildItems(simpleChildList), count);
  }

  @Override
  public SimpleChildGetResponse getChildren(int page, int limit) {
    List<SimpleChild> simpleChildList = simpleChildDao.findAllByPage(limit, (page - 1) * limit);
    long count = simpleChildDao.countAllActive();
    return new SimpleChildGetResponse(packSimpleChildItems(simpleChildList), count);
  }

  @Override
  public SimpleChildGetResponse getChildrenByTopic(String topic, int page, int limit) {
    List<SimpleChild> simpleChildren = simpleChildDao
        .findAllByTopicAndPageLimit(topic, limit, (page - 1) * limit);
    long count = simpleChildDao.countAllByTopic(topic);
    return new SimpleChildGetResponse(packSimpleChildItems(simpleChildren), count);
  }

  @Override
  public TopicsGetResponse getTopTopics(int page, int limit) {
    //TODO use redis to storage data later
    List<Object[]> topTopics = simpleChildDao.selectTopTopic(limit, (page - 1) * limit);
    long count = simpleChildDao.selectTopicCount();
    return new TopicsGetResponse(packTopicItems(topTopics), count);
  }

  @Override
  public SuccessResponse updateTopic(TopicUpdateParameters parameters) {
    Topic topic = topicDao.findFirstByTopic(parameters.getTopic());
    if (topic == null) {
      topic = new Topic(parameters.getTopic(), parameters.getCoverUrl(),
          parameters.getDescription(), System.currentTimeMillis(), System.currentTimeMillis());
    } else {
      topic.setCoverUrl(parameters.getCoverUrl());
      topic.setDescription(parameters.getDescription());
      topic.setUpdateTime(System.currentTimeMillis());
    }
    topicDao.save(topic);
    return new SuccessResponse("update success");
  }

  @Override
  public TopicDetailResponse getTopicDetail(String topic) {
    Topic topicEntity = topicDao.findFirstByTopic(topic);
    if (topicEntity == null) {
      return new TopicDetailResponse(0, topic, "", "", 0, 0);
    } else {
      return new TopicDetailResponse(topicEntity.getId(), topicEntity.getTopic(),
          topicEntity.getCoverUrl(), topicEntity.getDescription(), topicEntity.getCreateTime(),
          topicEntity.getUpdateTime());
    }
  }

  private List<TopicItem> packTopicItems(List<Object[]> topicObjects) {
    List<TopicItem> topicItems = new ArrayList<>();
    for (Object[] topicObject : topicObjects) {
      BigInteger count = (BigInteger) topicObject[1];
      topicItems.add(new TopicItem((String) topicObject[0], count.longValue()));
    }
    return topicItems;
  }

  public static List<SimpleChildItem> packSimpleChildItems(List<SimpleChild> simpleChildList) {
    List<SimpleChildItem> simpleChildItems = new ArrayList<>();
    for (SimpleChild simpleChild : simpleChildList) {
      simpleChildItems.add(new SimpleChildItem(simpleChild.getId(), simpleChild.getCoverUrl(),
          simpleChild.getContent(), simpleChild.getTopics(), simpleChild.getCreateTime(),
          simpleChild.getUpdateTime(), simpleChild.getAuthor(), simpleChild.isActive()));
    }
    return simpleChildItems;
  }
}
