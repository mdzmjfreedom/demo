package com.example.demo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.database.TestDO;
import com.example.demo.mapper.TestMapper;
import com.example.demo.request.TestRequest;
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
    public void insert(TestRequest request) {
        testMapper.insert(BeanUtil.copyProperties(request, TestDO.class));
    }

    @Override
    public List<TestResponse> getList(String name) {
//        LambdaQueryWrapper<TestDO> wrapper = new LambdaQueryWrapper<>();
//        if (StrUtil.isNotEmpty(name)) {
//            wrapper.eq(TestDO::getName, name);
//        }
//
//        return BeanUtil.copyToList(testDao.list(wrapper), TestResponse.class);
        return BeanUtil.copyToList(testMapper.listByName(name), TestResponse.class);
    }
}
