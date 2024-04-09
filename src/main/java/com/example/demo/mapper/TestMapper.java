package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.database.TestDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TestMapper extends BaseMapper<TestDO> {

    @Select("SELECT \"name\",\"age\" FROM TEST_ENC WHERE (\"name\" = #{name})")
    List<TestDO> listByName(String name);
}
