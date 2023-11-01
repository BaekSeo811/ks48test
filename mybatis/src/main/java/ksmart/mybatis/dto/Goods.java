package ksmart.mybatis.dto;

public class Goods {

	private String goodsCode;
	private String goodsName;
	private int goodsPrice;
	private String goodsSellerId;
	private String goodsRegDate;
	
	// 1대1 관계일 때 매핑할 수 있는 고급맵핑
	private Member memberInfo;
	
	public Member getMemberInfo() {
		return memberInfo;
	}
	public void setMemberInfo(Member memberInfo) {
		this.memberInfo = memberInfo;
	}
	
	
	public String getGoodsCode() {
		return goodsCode;
	}
	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public int getGoodsPrice() {
		return goodsPrice;
	}
	public void setGoodsPrice(int goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
	public String getGoodsSellerId() {
		return goodsSellerId;
	}
	public void setGoodsSellerId(String goodsSellerId) {
		this.goodsSellerId = goodsSellerId;
	}
	public String getGoodsRegDate() {
		return goodsRegDate;
	}
	public void setGoodsRegDate(String goodsRegDate) {
		this.goodsRegDate = goodsRegDate;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Goods [goodsCode=");
		builder.append(goodsCode);
		builder.append(", goodsName=");
		builder.append(goodsName);
		builder.append(", goodsPrice=");
		builder.append(goodsPrice);
		builder.append(", goodsSellerId=");
		builder.append(goodsSellerId);
		builder.append(", goodsRegDate=");
		builder.append(goodsRegDate);
		builder.append(", memberInfo=");
		builder.append(memberInfo);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}
