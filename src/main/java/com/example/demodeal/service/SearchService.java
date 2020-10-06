package com.example.demodeal.service;

import com.example.demodeal.domain.Goods;
import java.util.List;

public interface SearchService {
    /**
     * goods idx
     * @param goodsId
     */
    void index(Long goodsId);

    /**
     * remove goods
     * @param goodsId
     */
    void remove(Long goodsId);

    /**
     * Find good by name
     */
    List<Goods> findByName(String name);

    /**
     *  Find good by id
     */
    List<Goods> findById(Long goodsId);


}
