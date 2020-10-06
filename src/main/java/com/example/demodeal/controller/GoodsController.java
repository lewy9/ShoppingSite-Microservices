package com.example.demodeal.controller;


import com.example.demodeal.domain.Goods;
import com.example.demodeal.domain.Result;
import com.example.demodeal.enums.ResultEnum;
import com.example.demodeal.repository.GoodsRepository;
import com.example.demodeal.service.GoodsService;
import com.example.demodeal.service.SearchService;
import com.example.demodeal.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@RestController
public class GoodsController {

    private final static Logger logger = LoggerFactory.getLogger(GoodsController.class);

    @Autowired
    private SearchService searchService;

    @Autowired
    private GoodsRepository goodsRepository;


    // find goods by name
    @GetMapping(value = "/goods/name/{name}")
    public Result<Goods> goodsListByName(@PathVariable("name") String name){
        return ResultUtil.success(ResultEnum.SUCCESS,searchService.findByName(name));
    }


    // search goods by id
    @GetMapping(value = "/goods/id/{id}")
    public  Result<Goods> goodsFindOne(@PathVariable("id") long id) {
        return  ResultUtil.success(ResultEnum.SUCCESS,searchService.findById(id));
    }

    /**
     * Get all the goods
     * @return
     */
    @GetMapping(value = "/goods")
    public Result<Goods> goodsList(){
        return ResultUtil.success(ResultEnum.SUCCESS,goodsRepository.findAll());
    }


    /**
     * Add a product
     * @return
     */
    @PostMapping(value = "/goods")
    public Result<Goods> goodsAdd(Goods goods, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultUtil.error(201, bindingResult.getFieldError().getDefaultMessage());
        }

        goods.setName(goods.getName());
        goods.setNumber(goods.getNumber());
        goods.setPrice(goods.getPrice());
        Goods newGoods =  goodsRepository.saveAndFlush(goods);
        searchService.index(newGoods.getId());

        return ResultUtil.success(ResultEnum.SUCCESS,newGoods);
    }

    // Update
    @PutMapping(value = "/goods/{id}")
    public Result<Goods> goodsUpdate(@PathVariable("id") long id,
                                   @RequestParam("name") String name,
                                   @RequestParam("number") int number,
                                     @RequestParam("price") double price ) {
        Goods goods = new Goods();
        goods.setId(id);
        goods.setName(name);
        goods.setNumber(number);
        goods.setPrice(price);
        searchService.index(id);

        return  ResultUtil.success(ResultEnum.SUCCESS,goodsRepository.save(goods));
    }

    // Delete
    @DeleteMapping(value = "/goods/{id}")
    public Result<Goods> goodsDelete(@PathVariable("id") long id) {
        goodsRepository.delete(id);
        searchService.remove(id);
        return ResultUtil.success(ResultEnum.SUCCESS,null);
    }


}
