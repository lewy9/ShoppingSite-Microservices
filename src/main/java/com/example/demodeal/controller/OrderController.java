package com.example.demodeal.controller;

import com.example.demodeal.domain.Order;
import com.example.demodeal.domain.Result;
import com.example.demodeal.enums.ResultEnum;
import com.example.demodeal.repository.OrderRepository;
import com.example.demodeal.service.OrderService;
import com.example.demodeal.service.OrderService;
import com.example.demodeal.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {

    private final static Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;



    // Get an order by id
    @GetMapping(value = "/order/id/{id}")
    public  Result<Order> orderFindOne(@PathVariable("id") long id) {
        return  ResultUtil.success(ResultEnum.SUCCESS,orderRepository.findOne(id));
    }

    /**
     * Get all the orders
     * @return
     */
    @GetMapping(value = "/order")
    public Result<Order> orderList(){
        return ResultUtil.success(ResultEnum.SUCCESS,orderRepository.findAll());
    }


    /**
     * Add an order
     * @return
     */
    @PostMapping(value = "/order")
    public Result<Order> orderAdd(Order order, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultUtil.error(201, bindingResult.getFieldError().getDefaultMessage());
        }

        order.setAddress(order.getAddress());
        order.setFinallyPrice(order.getFinallyPrice());

        return ResultUtil.success(ResultEnum.SUCCESS,orderRepository.save(order));
    }

    // Update an order by id
    @PutMapping(value = "/order/{id}")
    public Result<Order> orderUpdate(@PathVariable("id") long id,
                                     @RequestParam("address") String address,
                                     @RequestParam("price") double price ) {
        Order order = new Order();
        order.setId(id);
        order.setAddress(address);
        order.setFinallyPrice(price);

        return  ResultUtil.success(ResultEnum.SUCCESS,orderRepository.save(order));
    }

    // Delete an order by id
    @DeleteMapping(value = "/order/{id}")
    public Result<Order> orderDelete(@PathVariable("id") long id) {
        orderRepository.delete(id);
        return ResultUtil.success(ResultEnum.SUCCESS,null);
    }


}