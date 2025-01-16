package com.sdj.wu_back.dao;

import com.sdj.wu_back.entity.Audit;
import com.sdj.wu_back.entity.Book;
import com.sdj.wu_back.entity.Params;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


@Repository
public interface AuditDao extends Mapper<Audit> {

    List<Audit> findBySearch(@Param("params") Params params);

    void updateAudit(Audit audit);
    void deleteAudit(Integer id );

    int  addAudit(Audit audit);

}
