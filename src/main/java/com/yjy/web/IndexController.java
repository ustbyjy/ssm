package com.yjy.web;

import com.mzlion.easyokhttp.HttpClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Administrator
 * Date: 2017-01-02
 * Time: 15:41
 */
@RestController
public class IndexController {

    @GetMapping("index")
    public String index() {
        String cityInfo = HttpClient
                .get("http://www.weather.com.cn/data/cityinfo/101010100.html")
                .execute()
                .asString();
        return cityInfo;
    }

    @PostMapping("server-list")
    public String serverList(Long gameid, String project_alias, String filename, String contents) {
        System.out.println("gamid=" + gameid);
        System.out.println("project_alias=" + project_alias);
        System.out.println("filename=" + filename);
        System.out.println("contents=" + contents);

        return "success";
    }
}
