package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.service.ReportService;
import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;

@RestController
@RequestMapping("/admin/report")
@Slf4j
@Api(tags = "数据统计相关接口")
public class ReportController {

    @Autowired
    private ReportService reportService;
    /**
     * 营业额统计
     * @param begin
     * @param end
     * @return
     */
    @GetMapping("/turnoverStatistics")
    @ApiOperation("营业额统计")
    public Result<TurnoverReportVO> turnoverReport(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                                                   @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){
        log.info("统计{}至{}的营业额", begin, end);
        TurnoverReportVO vo = reportService.turnoverReport(begin, end);
        return Result.success(vo);
    }

    /**
     * 用户统计
     * @param begin
     * @param end
     * @return
     */
    @GetMapping("/userStatistics")
    @ApiOperation("用户统计")
    public Result<UserReportVO> userReport(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                                           @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){
        UserReportVO userReportVO = reportService.userReport(begin, end);
        return Result.success(userReportVO);
    }

    /**
     * 订单统计
     * @param begin
     * @param end
     * @return
     */
    @GetMapping("/ordersStatistics")
    @ApiOperation("订单统计")
    public Result<OrderReportVO> orderReport(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                                            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){
        OrderReportVO orderReportVO = reportService.orderReport(begin, end);
        return Result.success(orderReportVO);
    }

    /**
     * 查询销量排名top10
     * @param begin
     * @param end
     * @return
     */
    @GetMapping("/top10")
    @ApiOperation("查询销量排名top10")
    public Result<SalesTop10ReportVO> top10Report(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                                                  @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){
        SalesTop10ReportVO salesTop10ReportVO = reportService.top10Report(begin, end);
        return Result.success(salesTop10ReportVO);
    }

    /**
     * 数据导出
     * @param response
     * @return
     */
    @GetMapping("/export")
    @ApiOperation("数据导出")
    public Result dataExport(HttpServletResponse response){
        reportService.dataExport(response);
        return Result.success();
    }
}
