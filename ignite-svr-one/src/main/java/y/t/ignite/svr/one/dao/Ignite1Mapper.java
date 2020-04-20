package y.t.ignite.svr.one.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import y.t.ignite.svr.one.entity.dbeb.Ignite1;

@Mapper
public interface Ignite1Mapper {
    int deleteByPrimaryKey(String fieldOne);

	int insert(Ignite1 record);

	Ignite1 selectByPrimaryKey(String fieldOne);

	List<Ignite1> selectAll();

	int updateByPrimaryKey(Ignite1 record);

}