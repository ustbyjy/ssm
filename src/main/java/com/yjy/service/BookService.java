package com.yjy.service;

import com.yjy.dto.AppointExecution;
import com.yjy.entity.Book;

import java.util.List;

/**
 * 业务接口：站在"使用者"角度设计接口 三个方面：方法定义粒度，参数，返回类型（return 类型/异常）
 */
public interface BookService {

    /**
     * 新增一本图书
     *
     * @param book
     * @return
     */
    boolean insert(Book book);

    /**
     * 更新一本图书
     *
     * @param book
     * @return
     */
    Book update(Book book);

    /**
     * 删除一本图书
     *
     * @param bookId
     * @return
     */
    boolean deleteById(Long bookId);

    /**
     * 查询一本图书
     *
     * @param bookId
     * @return
     */
    Book getById(long bookId);

    /**
     * 查询所有图书
     *
     * @return
     */
    List<Book> getList();

    /**
     * 预约图书
     *
     * @param bookId
     * @param studentId
     * @return
     */
    AppointExecution appoint(long bookId, long studentId);

}
