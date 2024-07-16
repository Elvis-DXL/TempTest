package project.base.demopg.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.base.base.DSUtil.PageResp;
import project.base.base.DSUtil.R;
import project.base.demopg.business.DemoBusiness;
import project.base.demopg.vo.cmd.DemoAddCmd;
import project.base.demopg.vo.cmd.DemoListCmd;
import project.base.demopg.vo.cmd.DemoModifyCmd;
import project.base.demopg.vo.dto.DemoVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static project.base.base.DSUtil.trueThrow;

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
    public R<DemoVo> save(@RequestBody @Validated DemoAddCmd cmd) {
        return R.data(demoBusiness.add(cmd));
    }

    //    @ZxOperaLog("删除样例")
    @GetMapping("/remove/{id}")
    @Operation(summary = "删除样例")
    @Parameters({@Parameter(name = "id", description = "样例ID")})
    public R<DemoVo> remove(@PathVariable Long id) {
        return R.data(demoBusiness.delete(id));
    }

    //    @ZxOperaLog("修改样例")
    @PostMapping("/modify")
    @Operation(summary = "修改样例")
    public R<DemoVo> modify(@RequestBody @Validated DemoModifyCmd cmd) {
        return R.data(demoBusiness.modify(cmd));
    }

    @GetMapping("/query/{id}")
    @Operation(summary = "查询样例")
    @Parameters({@Parameter(name = "id", description = "样例ID")})
    public R<DemoVo> query(@PathVariable Long id) {
        return R.data(demoBusiness.query(id));
    }

    @PostMapping("/list")
    @Operation(summary = "查询样例集合")
    public R<List<DemoVo>> list(@RequestBody DemoListCmd cmd) {
        return R.data(demoBusiness.list(cmd));
    }

    @PostMapping("/page")
    @Operation(summary = "分页查询样例")
    public R<PageResp<DemoVo>> page(@RequestBody DemoListCmd cmd) {
        trueThrow(null == cmd.getPageNum() || null == cmd.getPageSize(), new RuntimeException("缺少分页参数"));
        return R.data(demoBusiness.page(cmd));
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
        demoBusiness.dataExport(new DemoListCmd().name(name), request, response);
    }

    @PostMapping("/dataImport")
    @Operation(summary = "数据导入")
    public R<Boolean> dataImport(@RequestParam(value = "file") MultipartFile file) {
        demoBusiness.dataImport(file);
        return R.data(Boolean.TRUE);
    }
}