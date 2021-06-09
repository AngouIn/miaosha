package com.jingege.service.impl;

import com.jingege.mapper.ISeckillMapper;
import com.jingege.pojo.Goods;
import com.jingege.service.ISeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeckillServiceImpl implements ISeckillService {

    @Autowired
    private ISeckillMapper seckillMapper;

    @Override
    public List<Goods> queryAllGoodStock() {
        return this.seckillMapper.queryAllGoodStock();
    }
}
