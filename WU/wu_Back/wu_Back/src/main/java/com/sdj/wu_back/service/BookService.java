package com.sdj.wu_back.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sdj.wu_back.common.JwtTokenUtils;
import com.sdj.wu_back.dao.BookDao;
import com.sdj.wu_back.dao.TypeDao;
import com.sdj.wu_back.dao.UserDao;
import com.sdj.wu_back.entity.Book;
import com.sdj.wu_back.entity.Params;
import com.sdj.wu_back.entity.User;
import com.sdj.wu_back.exception.CustomException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;

@Service
public class BookService {

    @Resource
    private BookDao bookDao;

    @Resource
    private TypeDao typeDao;


    public PageInfo<Book> findBySearch(Params params) {
        PageHelper.startPage(params.getPageNum() ,params.getPageSize());
        List<Book> list = bookDao.findBySearch(params);
        return new PageInfo<>(list);
    }

    public void add(Book book) {
//        bookDao.insertSelective(book);
        bookDao.addBook(book);

    }
    public void  update(Book book ){bookDao.updateBook(book);
    }

    public void delete(Integer id){
        bookDao.deleteBook(id);
    }

    public List <Book> findAll(){return bookDao.findAll();}

}
