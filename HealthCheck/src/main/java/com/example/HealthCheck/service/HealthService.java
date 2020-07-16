package com.example.HealthCheck.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.HealthCheck.entity.HealthCheck;
import com.example.HealthCheck.mapper.HealthMapper;

@Service
public class HealthService {
	@Autowired
	private HealthMapper healthMapper;

	@Transactional
	public List<HealthCheck> findAll() {
		return healthMapper.findAll();
	}

	@Transactional
	public HealthCheck findOne(int id) {
		return healthMapper.findOne(id);
	}

	@Transactional
	public void save(HealthCheck healthCheck) {
		healthMapper.save(healthCheck);
	}

	@Transactional
	public void update(HealthCheck healthCheck) {
		healthMapper.update(healthCheck);
	}

	@Transactional
	public void delete(int id) {
		healthMapper.delete(id);
	}

}
