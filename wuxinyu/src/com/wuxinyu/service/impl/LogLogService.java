package com.wuxinyu.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wuxinyu.entity.LogLog;
import com.wuxinyu.repository.LogLogRepository;
import com.wuxinyu.service.ILogLogService;

@Transactional //声明式事物
@Service
public class LogLogService implements ILogLogService{
	private static final Logger LOGGER = LogManager.getLogger(LogLogService.class);
	
	@Autowired
	private LogLogRepository logLogRepository;
	
	@Override
	public LogLog save(LogLog logLog){
		return logLogRepository.save(logLog);
	}
	
	@Override
	public void delete(LogLog logLog){
		logLogRepository.delete(logLog);
	}

	@Override
	public void delete(Integer id) {
		logLogRepository.delete(id);		
	}

	@Override
	public void update(LogLog user) {
		logLogRepository.saveAndFlush(user);
	}

	@Override
	public List<LogLog> findAll() {
		return logLogRepository.findAll();
	}

	@Override
	public LogLog findById(Integer id) {
		return logLogRepository.findOne(id);
	}

	@Override
	public Page<LogLog> findAll(Integer page, Integer size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<LogLog> findAll(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

}
