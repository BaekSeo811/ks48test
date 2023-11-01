package ksmart.mybatis.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ksmart.mybatis.dto.Goods;
import ksmart.mybatis.dto.Member;
import ksmart.mybatis.mapper.GoodsMapper;
import ksmart.mybatis.mapper.MemberMapper;

@Service
@Transactional
public class GoodsService {
	
	private static final Logger log = LoggerFactory.getLogger(GoodsService.class);

	private final GoodsMapper goodsMapper;
	private final MemberMapper memberMapper;
	
	public GoodsService(GoodsMapper goodsMapper, MemberMapper memberMapper) {
		this.goodsMapper = goodsMapper;
		this.memberMapper = memberMapper;
	}
	
	public void addGoods(Goods goods) {
		log.info("상품등록 전 {}:", goods);
		goodsMapper.addGoods(goods);
		log.info("상품등록 후 {}:", goods);
	}
	
	public List<Member> getSellerList(String column, String value){
		return memberMapper.getMemberListBySearch(column, value);
	}
	
	public List<Member> getSellerList(){
		return memberMapper.getSellerList();
	}
	
	public List<Goods> getGoodsList(){
		return goodsMapper.getGoodsList();
	}
}









