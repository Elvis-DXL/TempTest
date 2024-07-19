package com.elvis.test.demopg.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.elvis.test.demopg.business.DemoBusiness;
import com.elvis.test.demopg.vo.cmd.DemoAddCmd;
import com.elvis.test.demopg.vo.cmd.DemoListCmd;
import com.elvis.test.demopg.vo.cmd.DemoModifyCmd;
import com.elvis.test.demopg.vo.dto.DemoVo;
import com.elvis.test.base.DSUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/5/27 13:58
 */
@RestController
@RequestMapping("/demo")
@Tag(name = "样例接口")
public class DemoController {
    @Autowired
    private DemoBusiness demoBusiness;

    //    @ZxOperaLog("新增样例")
    @PostMapping("/save")
    @Operation(summary = "新增样例")
    public DSUtil.R<DemoVo> save(@RequestBody @Validated DemoAddCmd cmd) {
        return DSUtil.R.data(demoBusiness.add(cmd));
    }

    //    @ZxOperaLog("删除样例")
    @GetMapping("/remove/{id}")
    @Operation(summary = "删除样例")
    @Parameters({@Parameter(name = "id", description = "样例ID")})
    public DSUtil.R<DemoVo> remove(@PathVariable Long id) {
        return DSUtil.R.data(demoBusiness.delete(id));
    }

    //    @ZxOperaLog("修改样例")
    @PostMapping("/modify")
    @Operation(summary = "修改样例")
    public DSUtil.R<DemoVo> modify(@RequestBody @Validated DemoModifyCmd cmd) {
        return DSUtil.R.data(demoBusiness.modify(cmd));
    }

    @GetMapping("/query/{id}")
    @Operation(summary = "查询样例")
    @Parameters({@Parameter(name = "id", description = "样例ID")})
    public DSUtil.R<DemoVo> query(@PathVariable Long id) {
        return DSUtil.R.data(demoBusiness.query(id));
    }

    @PostMapping("/list")
    @Operation(summary = "查询样例集合")
    public DSUtil.R<List<DemoVo>> list(@RequestBody DemoListCmd cmd) {
        return DSUtil.R.data(demoBusiness.list(cmd));
    }

    @PostMapping("/page")
    @Operation(summary = "分页查询样例")
    public DSUtil.R<DSUtil.PageResp<DemoVo>> page(@RequestBody DemoListCmd cmd) {
        DSUtil.trueThrow(null == cmd.getPageNum() || null == cmd.getPageSize(), new RuntimeException("缺少分页参数"));
        return DSUtil.R.data(demoBusiness.page(cmd));
    }

    @GetMapping("/template")
    @Operation(summary = "模板下载")
    public void template(HttpServletRequest request, HttpServletResponse response) {
        demoBusiness.template(request, response);
    }

    @GetMapping("/dataExport")
    @Operation(summary = "数据导出")
    public void dataExport(@RequestParam(required = false) String name,
                           HttpServletRequest request, HttpServletResponse response) {
        DemoListCmd cmd = new DemoListCmd();
        cmd.name(name);
        demoBusiness.dataExport(cmd, request, response);
    }

    @PostMapping("/dataImport")
    @Operation(summary = "数据导入")
    public DSUtil.R<Boolean> dataImport(@RequestParam(value = "file") MultipartFile file) {
        demoBusiness.dataImport(file);
        return DSUtil.R.data(Boolean.TRUE);
    }
}