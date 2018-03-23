package com.yjy.service.impl;

import com.yjy.dao.AppointmentDao;
import com.yjy.dao.BookDao;
import com.yjy.dto.AppointExecution;
import com.yjy.entity.Appointment;
import com.yjy.entity.Book;
import com.yjy.enums.AppointStateEnum;
import com.yjy.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * {@link CacheEvict}的beforeInvocation属性默认是false，如果方法执行中抛出异常则不会清除缓存，
 * 当beforeInvocation=true时，在方法执行前就清除缓存。
 */
@Service
@CacheConfig(cacheNames = "book")
public class BookServiceImpl implements BookService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // 注入Service依赖
    @Autowired
    private BookDao bookDao;

    @Autowired
    private AppointmentDao appointmentDao;

    @Override
    @CacheEvict(allEntries = true, beforeInvocation = true)
    public boolean insert(Book book) {
        Integer result = bookDao.insert(book);
        return result > 0;
    }

    @Override
    @CachePut(key = "':'.concat(#book.bookId)")
    public Book update(Book book) {
        bookDao.update(book);
        return getById(book.getBookId());
    }

    @Override
    @CacheEvict(key = "':'.concat(#bookId)", beforeInvocation = true)
    public boolean deleteById(Long bookId) {
        Integer result = bookDao.deleteById(bookId);
        return result > 0;
    }

    @Override
    @Cacheable(key = "':'.concat(#bookId)")
    public Book getById(long bookId) {
        return bookDao.queryById(bookId);
    }

    @Override
    public List<Book> getList() {
        return bookDao.queryAll(0, 1000);
    }

    @Transactional
    @Override
    public AppointExecution appoint(long bookId, long studentId) {
        try {
            // 减库存
            int update = bookDao.reduceNumber(bookId);
            if (update <= 0) {// 库存不足
                return new AppointExecution(bookId, AppointStateEnum.NO_NUMBER);
            } else {
                // 执行预约操作
                int insert = appointmentDao.insertAppointment(bookId, studentId);
                if (insert <= 0) {// 重复预约
                    return new AppointExecution(bookId, AppointStateEnum.REPEAT_APPOINT);
                } else {// 预约成功
                    Appointment appointment = appointmentDao.queryByKeyWithBook(bookId, studentId);
                    return new AppointExecution(bookId, AppointStateEnum.SUCCESS, appointment);
                }
            }
        } catch (Exception e) {

            logger.error(e.getMessage(), e);
            // 所有编译期异常转换为运行期异常
            return new AppointExecution(bookId, AppointStateEnum.INNER_ERROR);
        }
    }

}
