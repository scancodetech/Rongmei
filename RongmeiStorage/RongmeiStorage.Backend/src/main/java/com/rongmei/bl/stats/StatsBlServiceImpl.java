package com.rongmei.bl.stats;

import com.rongmei.blservice.stats.StatsBlService;
import com.rongmei.dao.access.AccessDao;
import com.rongmei.dao.stats.StatsDao;
import com.rongmei.dao.stats.UseStatsDao;
import com.rongmei.entity.access.Access;
import com.rongmei.entity.stats.Stats;
import com.rongmei.entity.stats.UseStats;
import com.rongmei.exception.UsernameDoesNotFoundException;
import com.rongmei.parameters.stats.StatsCreateParameters;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.stats.DataCreateResponse;
import com.rongmei.response.stats.DataGetResponse;
import com.rongmei.response.stats.Edge;
import com.rongmei.response.stats.EdgeData;
import com.rongmei.response.stats.HomeStatsGetResponse;
import com.rongmei.response.stats.Node;
import com.rongmei.response.stats.NodeData;
import com.rongmei.response.stats.StatsGetResponse;
import com.rongmei.response.stats.TimeStatsItem;
import com.rongmei.util.TimeUtil;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class StatsBlServiceImpl implements StatsBlService {

  private final StatsDao statsDao;
  private final UseStatsDao useStatsDao;
  private final AccessDao accessDao;

  @Autowired
  public StatsBlServiceImpl(StatsDao statsDao, UseStatsDao useStatsDao,
      AccessDao accessDao) {
    this.statsDao = statsDao;
    this.useStatsDao = useStatsDao;
    this.accessDao = accessDao;
  }

  @Override
  public DataGetResponse getData(String txHash) {
    String uri = "http://39.102.36.169:5000/get?txHash=" + txHash;
    JSONObject jsonObject = get(uri);
    useStatsDao.save(new UseStats("", "get", System.currentTimeMillis()));
    return new DataGetResponse((String) jsonObject.get("data"));
  }

  @Override
  public DataCreateResponse createStats(StatsCreateParameters parameters)
      throws UsernameDoesNotFoundException {
    Access access = accessDao.findFirstByAccessKeyAndAccessSecret(parameters.getAccessKey(),
        parameters.getAccessSecret());
    if (access == null) {
      throw new UsernameDoesNotFoundException();
    }
    String uri = "http://39.102.36.169:5000/set";
    String txHash = UUID.randomUUID().toString();
    JSONObject jsonObject = post(uri, txHash, parameters.getData());
    String transactionID = (String) jsonObject.get("transactionID");
    statsDao
        .save(new Stats(transactionID, txHash, parameters.getData(), System.currentTimeMillis(),
            access.getId()));
    useStatsDao.save(new UseStats(parameters.getAccessKey(), "set", System.currentTimeMillis()));
    return new DataCreateResponse(txHash);
  }

  private JSONObject get(String uri) {
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
    HttpEntity<String> entity = new HttpEntity<>(headers);
    String strBody = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class).getBody();
    return JSONObject.fromObject(strBody);
  }

  private JSONObject post(String uri, String txHash, String data) {
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
    HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(
        popHeaders(txHash, data), headers);
    String strBody = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class).getBody();
    return JSONObject.fromObject(strBody);
  }

  //组装请求体
  private MultiValueMap<String, String> popHeaders(String txHash, String data) {
    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    map.add("txHash", txHash);
    map.add("data", data);
    return map;
  }

  @Override
  public StatsGetResponse getStats() {
    List<Access> accesses = accessDao.findAll();
    List<Node> nodes = new ArrayList<>();
    List<Edge> edges = new ArrayList<>();
    String startNodeId = "Rongmei";
    String startNodeLabel = "genesis.block";
    NodeData nodeData = new NodeData(startNodeId, startNodeLabel, "company");
    Node node = new Node(startNodeId, nodeData, "CircleNode");
    nodes.add(node);
    for (Access access : accesses) {
      NodeData companyNodeData = new NodeData(access.getAccessKey(), access.getName(), "company");
      Node companyNode = new Node(access.getAccessKey(), companyNodeData, "CircleNode");
      Edge companyEdge = new Edge(startNodeId, access.getAccessKey(),
          new EdgeData(startNodeId, access.getAccessKey()));
      edges.add(companyEdge);
      List<Stats> statsList = statsDao.findAllByAccessId(access.getId());
      for (Stats stats : statsList) {
        try {
          MessageDigest md = MessageDigest.getInstance("MD5");
          byte[] data = stats.getExtra().getBytes();
          byte[] info = md.digest(data);
          String md5str = bytesToHex(info);
          Node dataNode = new Node(stats.getTransactionId(),
              new NodeData(stats.getTransactionId(), md5str, "company"), "CircleNode");
          nodes.add(dataNode);
          Edge dataEdge = new Edge(access.getAccessKey(), stats.getTransactionId(),
              new EdgeData(access.getAccessKey(), stats.getTransactionId()));
          edges.add(dataEdge);
        } catch (NoSuchAlgorithmException e) {
          e.printStackTrace();
        }
      }
      nodes.add(companyNode);
    }
    return new StatsGetResponse(nodes, edges);
  }

  private String bytesToHex(byte[] bytes) {
    StringBuffer md5str = new StringBuffer();
    // 把数组每一字节换成16进制连成md5字符串
    int digital;
    for (int i = 0; i < bytes.length; i++) {
      digital = bytes[i];

      if (digital < 0) {
        digital += 256;
      }
      if (digital < 16) {
        md5str.append("0");
      }
      md5str.append(Integer.toHexString(digital));
    }
    return md5str.toString().toUpperCase();
  }

  @Override
  public HomeStatsGetResponse getHomeStats() {
    List<TimeStatsItem> monthStatsCount = new ArrayList<>();
    List<TimeStatsItem> minuteStatsCount = new ArrayList<>();
    List<Long> twelveMonthStartTime = TimeUtil.getCurrentTwelveMonthStartTime();
    for (int i = 0; i < twelveMonthStartTime.size(); i++) {
      if (i + 1 < twelveMonthStartTime.size()) {
        int stats = useStatsDao.countAllByCreateTimeBetween(twelveMonthStartTime.get(i),
            twelveMonthStartTime.get(i + 1));
        monthStatsCount.add(
            new TimeStatsItem(twelveMonthStartTime.get(i), stats));
      } else {
        int stats = useStatsDao.countAllByCreateTimeBetween(twelveMonthStartTime.get(i),
            Timestamp.valueOf(LocalDateTime.now()).getTime());
        monthStatsCount.add(
            new TimeStatsItem(twelveMonthStartTime.get(i), stats));
      }
    }
    List<Long> someMinuteStartTime = TimeUtil.getCurrentSomeMinuteStartTime();
    for (int i = 0; i < someMinuteStartTime.size(); i++) {
      if (i + 1 < someMinuteStartTime.size()) {
        int stats = useStatsDao.countAllByCreateTimeBetween(someMinuteStartTime.get(i),
            someMinuteStartTime.get(i + 1));
        minuteStatsCount
            .add(new TimeStatsItem(someMinuteStartTime.get(i), stats));
      } else {
        int stats = useStatsDao.countAllByCreateTimeBetween(someMinuteStartTime.get(i),
            Timestamp.valueOf(LocalDateTime.now()).getTime());
        minuteStatsCount
            .add(new TimeStatsItem(someMinuteStartTime.get(i), stats));
      }
    }
    return new HomeStatsGetResponse(monthStatsCount, minuteStatsCount);
  }

}
