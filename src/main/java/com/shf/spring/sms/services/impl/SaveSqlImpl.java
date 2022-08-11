package com.shf.spring.sms.services.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.toolkit.JdbcUtils;
import com.shf.spring.sms.services.inter.SaveSql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class SaveSqlImpl implements SaveSql {

    private final JdbcTemplate jdbcTemplate;

    public SaveSqlImpl() {
        jdbcTemplate = SpringUtil.getBean(JdbcTemplate.class);
    }

    @Override
    public int saveSql(String sql, int type) {
        return jdbcTemplate.update("INSERT INTO sys_sql (`id`, `sql`, `type`) values (null, ?, ?)", sql, type);
    }
}
