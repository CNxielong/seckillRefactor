package com.xl.web;

import com.xl.dto.Exposer;
import com.xl.dto.SeckillExecution;
import com.xl.dto.SeckillResult;
import com.xl.entity.Seckill;
import com.xl.service.SeckillService;
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

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(Model model){
        List<Seckill> seckillList = seckillService.listAllSeckill();
        model.addAttribute("seckillList", seckillList);
        return "list";
    }

    @RequestMapping(value = "/{seckillId}/detail",method = RequestMethod.GET)
    public String detail(@PathVariable(value = "seckillId") Long seckillId, Model model){

        if(null == seckillId){
//            return "redirect/list";
            return "list";
        }
        Seckill seckill = seckillService.getById(seckillId);
        if (null == seckill){ // 如果为空那么跳转到展示页
            return "forward/list";
//            return "list";
        }else { //不为空
            model.addAttribute("seckill", seckill);
            return "detail";
        }
    }

    @RequestMapping(value = "/{seckillId}/exposer",method = RequestMethod.POST)
    @ResponseBody
    public SeckillResult<Exposer> exposer(@PathVariable("seckillId") Long seckillId){

        if (null == seckillId){
            return  new SeckillResult<>(false, "参数传递不正确!");
        }else{
            Exposer exposer = seckillService.exportSeckillUrl(seckillId);
            if(exposer.isExposed() == false){
                return new SeckillResult<Exposer>(false, "未找到秒杀信息");
            }else{
                return new SeckillResult<Exposer>(exposer.isExposed(), exposer);
            }
        }

    }


    public SeckillResult<SeckillExecution> execute(){
        return null;
    }

}
