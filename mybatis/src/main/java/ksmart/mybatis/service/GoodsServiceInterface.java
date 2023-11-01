package ksmart.mybatis.service;

import java.util.List;

import ksmart.mybatis.dto.Goods;


public interface GoodsServiceInterface {
	
	//상품 목록 조회
	public List<Goods> getGoodsList();
}
