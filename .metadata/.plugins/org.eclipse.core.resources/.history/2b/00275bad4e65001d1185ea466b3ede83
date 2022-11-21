package finance.schedule.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import finance.common.mapper.CommonMapper;


public class SchedulerServiceImpl implements SchedulerService {
	
	@Resource(name="CommonMapper")
	private CommonMapper commonMapper;
	
	private String namespace = "schedulerMapper.";
	
	@Override
	public List<Map<String, Object>> getPortListForSchedule(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"getPortListForSchedule");
	    List<Map<String, Object>> resultList = commonMapper.getList(param);
	    return resultList;
	}
	
	@Override
	public Integer updatePortForSchedule(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"updatePortForSchedule");
	    Integer resultInt = (Integer) commonMapper.update(param);
	    return resultInt;
	}
}
