package com.chqiuu.study.swagger.export.controller;

import com.chqiuu.study.swagger.export.dto.Table;
import com.chqiuu.study.swagger.export.service.ExportService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author chqiu
 */
@Controller
@AllArgsConstructor
public class ExportController {

    private final ExportService exportService;

    @RequestMapping("/")
    public String index(Model model) {
        return "index";
    }

    @ApiOperation(value = "根据Swagger api URL生成离线文档", notes = "根据Swagger api URL生成离线文档")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "url", value = "Swagger API URL", paramType = "form", required = true),
    })
    @RequestMapping(value = "/to-html", method = {RequestMethod.GET})
    public String toHtml(Model model, String url) {
        List<Table> tables = exportService.tableList(url);
        model.addAttribute("tables", tables);
        model.addAttribute("url", url);
        return "word.html";
    }
}
