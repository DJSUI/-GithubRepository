package com.sdj.wu_back.dao;

import com.sdj.wu_back.entity.Book;
import com.sdj.wu_back.entity.Params;
import com.sdj.wu_back.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


@Repository
public interface BookDao extends Mapper<Book> {
    List<Book> findBySearch(@Param("params")Params params);

    void updateBook(Book book);

    void deleteBook(Integer id );

    int  addBook(Book book);

    @Select("select book.*,type.name as typeName from book left join type on book.typeId = type.id")
    List<Book> findAll();

}
