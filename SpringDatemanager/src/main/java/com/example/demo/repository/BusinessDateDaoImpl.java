package com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.BusinessDate;

@Repository
public class BusinessDateDaoImpl implements BusinessDateDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	// テーブルのデータ数取得
	@Override
	public int getCount() throws DataAccessException {
		String sql = "SELECT COUNT(*) FROM datetable";
		int count = jdbcTemplate.queryForObject(sql, Integer.class);
		return count;
	}

	// テーブルの日付名(date_name)をカウントする。
	@Override
	public int countDateName(String dateName) throws DataAccessException {

		String sql;

		if (dateName == "来年") {
			sql = "SELECT COUNT(date_name = '来年'OR NULL) FROM datetable";
		} else if (dateName == "来月") {
			sql = "SELECT COUNT(date_name = '来月'OR NULL) FROM datetable";
		} else if (dateName == "月末") {
			sql = "SELECT COUNT(date_name = '月末'OR NULL) FROM datetable";
		} else {
			sql = "";
		}
		int count = jdbcTemplate.queryForObject(sql, Integer.class);

		return count;
	}

	// 一件登録
	@Override
	public void insertDate(BusinessDate businessDate) throws DataAccessException {
		String sql = "INSERT INTO datetable (date_name, base_date, diff_year, diff_month, diff_day) VALUES(?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, businessDate.getDate_name(), businessDate.getBase_date(), businessDate.getDiff_year(),
				businessDate.getDiff_month(), businessDate.getDiff_day());
	}

	// 一件登録
	@Override
	public BusinessDate selectOne(int id) throws DataAccessException {
		String sql = "SELECT * FROM datetable WHERE id = ?";
		Map<String, Object> map = jdbcTemplate.queryForMap(sql, id);
		// 結果返却用のEntityクラスを用意
		BusinessDate businessDate = new BusinessDate();
		// Entityへ詰める。
		businessDate.setId((int) map.get("id"));
		businessDate.setDate_name((String) map.get("date_name"));
		businessDate.setBase_date((String) map.get("base_date"));
		businessDate.setDiff_year((int) map.get("diff_year"));
		businessDate.setDiff_month((int) map.get("diff_month"));
		businessDate.setDiff_day((int) map.get("diff_day"));

		return businessDate;
	}

	// 登録済みデータを編集
	@Override
	public void updateOne(BusinessDate businessDate) throws DataAccessException {
		String sql = "UPDATE datetable SET date_name= ?, base_date= ?, diff_year=?,  diff_month= ?, diff_day = ? WHERE id = ?";
		jdbcTemplate.update(sql, businessDate.getDate_name(), businessDate.getBase_date(), businessDate.getDiff_year(),
				businessDate.getDiff_month(), businessDate.getDiff_day(), businessDate.getId());
	}

	// 全件取得。Entityクラス(buisnessDate)のListを返す。
	@Override
	public List<BusinessDate> getAll() throws DataAccessException {

		// DBからMapのListを取得。1件のデータがMapに格納されている。
		List<Map<String, Object>> getList = jdbcTemplate.queryForList("SELECT * FROM datetable");

		// 結果返却用のEntityクラスのList<BusinessDate>を用意
		List<BusinessDate> dateList = new ArrayList<BusinessDate>();

		// List<Map>(DBから取得の)の中身を結果返却用のList<BusinessDate>に詰め替える。
		for (Map<String, Object> map : getList) {
			// BusinessDate型インスタンスを用意
			BusinessDate businessDate = new BusinessDate();

			// Entityへ詰める。
			businessDate.setId((int) map.get("id"));
			businessDate.setDate_name((String) map.get("date_name"));
			businessDate.setBase_date((String) map.get("base_date"));
			businessDate.setDiff_year((int) map.get("diff_year"));
			businessDate.setDiff_month((int) map.get("diff_month"));
			businessDate.setDiff_day((int) map.get("diff_day"));
			dateList.add(businessDate);
		}

		return dateList;
	}

	// データ一件DBから削除
	@Override
	public void deleteOne(int id) throws DataAccessException {
		String sql = "DELETE FROM datetable WHERE id = ?";
		jdbcTemplate.update(sql, id);

	}

}