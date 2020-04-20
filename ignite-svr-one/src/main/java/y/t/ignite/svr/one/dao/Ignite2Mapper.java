package y.t.ignite.svr.one.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import y.t.ignite.svr.one.entity.dbeb.Ignite2;

@Mapper
public interface Ignite2Mapper {
    int deleteByPrimaryKey(String fieldOne);

	int insert(Ignite2 record);

	Ignite2 selectByPrimaryKey(String fieldOne);

	List<Ignite2> selectAll();

	int updateByPrimaryKey(Ignite2 record);
}