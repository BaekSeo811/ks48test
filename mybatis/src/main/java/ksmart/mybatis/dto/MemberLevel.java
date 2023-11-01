package ksmart.mybatis.dto;

public class MemberLevel {

	private int levelNum;
	private String levelName;
	private String levelRegDate;
	
	public int getLevelNum() {
		return levelNum;
	}
	public void setLevelNum(int levelNum) {
		this.levelNum = levelNum;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public String getLevelRegDate() {
		return levelRegDate;
	}
	public void setLevelRegDate(String levelRegDate) {
		this.levelRegDate = levelRegDate;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MemberLevel [levelNum=");
		builder.append(levelNum);
		builder.append(", levelName=");
		builder.append(levelName);
		builder.append(", levelRegDate=");
		builder.append(levelRegDate);
		builder.append(", getLevelNum()=");
		builder.append(getLevelNum());
		builder.append(", getLevelName()=");
		builder.append(getLevelName());
		builder.append(", getLevelRegDate()=");
		builder.append(getLevelRegDate());
		builder.append(", getClass()=");
		builder.append(getClass());
		builder.append(", hashCode()=");
		builder.append(hashCode());
		builder.append(", toString()=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}
}
