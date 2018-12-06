package com.xl.web;

import com.xl.dto.Exposer;
import com.xl.dto.SeckillExecution;
import com.xl.dto.SeckillResult;
import com.xl.entity.Seckill;
import com.xl.enums.SeckillStatEnum;
import com.xl.exception.RepeatKillException;
import com.xl.exception.SeckillCloseException;
import com.xl.exception.SeckillException;
import com.xl.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.Date;
import java.util.List;


/**
 * @Description: 秒杀控制器
 * @Auther: X-Dragon
 * @Date: 2018/12/2 2:20
 * @Version: 1.0
 */
@Controller
@RequestMapping(value = "/seckill")
public class SeckillController {

    //Logger日志
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        List<Seckill> seckillList = seckillService.listAllSeckill();
        model.addAttribute("seckillList", seckillList);
        return "list";
    }

    @RequestMapping(value = "/{seckillId}/detail", method = RequestMethod.GET)
    public String detail(@PathVariable(value = "seckillId") Long seckillId, Model model) {

        if (null == seckillId) {
//            return "redirect/list";
            return "list";
        }
        Seckill seckill = seckillService.getById(seckillId);
        if (null == seckill) { // 如果为空那么跳转到展示页
            return "forward/list";
//            return "list";
        } else { //不为空
            model.addAttribute("seckill", seckill);
            return "detail";
        }
    }

    @RequestMapping(value = "/{seckillId}/exposer", method = RequestMethod.POST)
    @ResponseBody
    public SeckillResult<Exposer> exposer(@PathVariable("seckillId") Long seckillId) {

        if (null == seckillId) {
            return new SeckillResult<>(false, "参数传递不正确!");
        } else {
            Exposer exposer = seckillService.exportSeckillUrl(seckillId);
            if (exposer.isExposed() == false) {
                return new SeckillResult<Exposer>(false, "未找到秒杀信息");
            } else {
                return new SeckillResult<Exposer>(exposer.isExposed(), exposer);
            }
        }

    }


    @RequestMapping(value = "/{userPhone}/{md5}/execution", method = RequestMethod.POST)
    @ResponseBody
    public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") long seckillId,
            @PathVariable("md5") String md5, @CookieValue(value = "killPhone", required = false) Long userPhone) {

        if( null == userPhone){ // 取不到电话号码
         return new SeckillResult<>(false, "电话号码不能为空");
        }else{
            SeckillExecution seckillExecution;
            try {
                 seckillExecution = seckillService.executeSeckill(seckillId, userPhone, md5);
                return new SeckillResult<SeckillExecution>(true, seckillExecution);
            } catch (RepeatKillException e) {
                return new SeckillResult<SeckillExecution>(true, SeckillStatEnum.REPEAT_KILL.getStateInfo());
            } catch (SeckillCloseException e) {
                return new SeckillResult<SeckillExecution>(true, SeckillStatEnum.TIME_ERROR.getStateInfo());
            } catch (SeckillException e) {
                logger.info(SeckillStatEnum.INNER_ERROR.getStateInfo(), e);
                return new SeckillResult<SeckillExecution>(true, SeckillStatEnum.INNER_ERROR.getStateInfo());
            }

        }

    }

    @RequestMapping(value = "/time/now",method = RequestMethod.GET)
    @ResponseBody
    public SeckillResult now(Model model) {
        Date now = new Date();
        return new SeckillResult(true, now.getTime());
    }

}
