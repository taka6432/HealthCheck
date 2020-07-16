package com.example.HealthCheck.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.HealthCheck.entity.HealthCheck;

@Mapper
public interface HealthMapper {

	@Select("select * from HealthCheck")
	List<HealthCheck> findAll();

	@Select("select * from HealthCheck where id = #{id}")
	HealthCheck findOne(int id);

	@Insert("insert into HealthCheck (bodytemperature,pulse,systolicbloodpressure,diastolicbloodpressure,aturation) values (#{bodytemperature}, #{pulse}, #{systolicbloodpressure}, #{diastolicbloodpressure}, #{aturation})")
//	@Options(useGeneratedKeys = true)
	void save(HealthCheck healthCheck);

	@Update("insert into HealthCheck (bodytemperature,pulse,systolicbloodpressure,diastolicbloodpressure,aturation) values (#{bodytemperature}, #{pulse}, #{systolicbloodpressure}, #{diastolicbloodpressure}, #{aturation})")
	void update(HealthCheck healthCheck);

	@Delete("delete from HealthCheck where id = #{id}")
	void delete(int id);

}
