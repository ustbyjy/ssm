package com.yjy.web;

import com.mzlion.easyokhttp.HttpClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Administrator
 * Date: 2017-01-02
 * Time: 15:41
 */
@Controller
public class IndexController {

    @GetMapping(value = {"", "/"})
    public String home() {
        return "index";
    }

    @GetMapping(value = "/404")
    public String error404() {
        return "index";
    }

    @GetMapping("index")
    @ResponseBody
    public String index() {

        String cityInfo = HttpClient
                .get("http://www.weather.com.cn/data/cityinfo/101010100.html")
                .execute()
                .asString();

        return cityInfo;
    }
}
