package com.example.demo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.database.TestDO;
import com.example.demo.mapper.TestMapper;
import com.example.demo.response.TestResponse;
import com.example.demo.service.TestService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TestServiceImpl extends ServiceImpl<TestMapper, TestDO> implements TestService {

    @Resource
    private TestMapper testMapper;

    @Override
    public List<TestResponse> queryList() {
        return BeanUtil.copyToList(testMapper.selectList(null), TestResponse.class);
    }
}
