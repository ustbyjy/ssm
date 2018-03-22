package com.yjy.web;

import com.yjy.dto.AppointExecution;
import com.yjy.dto.Result;
import com.yjy.entity.Book;
import com.yjy.service.BookService;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BookService bookService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    private Result<List> list(Model model) {
        List<Book> list = bookService.getList();
        return new Result<List>(true, list);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    private Result add(Book book, Model model) {
        bookService.insert(book);
        return new Result(true, "");
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    private Result update(Book book, Model model) {
        bookService.update(book);
        return new Result(true, "");
    }

    @RequestMapping(value = "/delete/{bookId}", method = RequestMethod.GET)
    @ResponseBody
    private Result delete(@PathVariable("bookId") Long bookId, Model model) {
        boolean result = bookService.deleteById(bookId);
        return new Result(result, "");
    }

    // ajax json
    @RequestMapping(value = "/detail/{bookId}", method = RequestMethod.GET)
    @ResponseBody
    private Result<Book> detail(@PathVariable("bookId") Long bookId, Model model) {
        Book book = bookService.getById(bookId);
        return new Result<Book>(true, book);
    }

    @RequestMapping(value = "/{bookId}/appoint", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    private Result<AppointExecution> appoint(@PathVariable("bookId") Long bookId, @Param("studentId") Long studentId) {
        if (studentId == null || studentId.equals("")) {
            return new Result<AppointExecution>(false, "学号不能为空");
        }
        AppointExecution execution = bookService.appoint(bookId, studentId);
        return new Result<AppointExecution>(true, execution);
    }

}
