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
    private String list(Model model) {
        List<Book> list = bookService.getList();
        model.addAttribute("list", list);
        return "list";
    }

    // ajax json
    @RequestMapping(value = "/{bookId}/detail", method = RequestMethod.GET)
    @ResponseBody
    private String detail(@PathVariable("bookId") Long bookId, Model model) {
        if (bookId == null) {
            return "redirect:/book/list";
        }
        Book book = bookService.getById(bookId);
        if (book == null) {
            return "forward:/book/list";
        }
        model.addAttribute("book", book);
        return "detail";
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
