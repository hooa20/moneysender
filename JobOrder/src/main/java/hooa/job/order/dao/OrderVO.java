package hooa.job.order.dao;

public class OrderVO {
	
	private String token;
	private String giveId;
	private String giveRoom;
	private int takeSeq;
	private String takeId;
	private String takeAmount;
	private String openDtm;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getGiveId() {
		return giveId;
	}
	public void setGiveId(String giveId) {
		this.giveId = giveId;
	}
	public String getGiveRoom() {
		return giveRoom;
	}
	public void setGiveRoom(String giveRoom) {
		this.giveRoom = giveRoom;
	}
	public int getTakeSeq() {
		return takeSeq;
	}
	public void setTakeSeq(int takeSeq) {
		this.takeSeq = takeSeq;
	}
	public String getTakeId() {
		return takeId;
	}
	public void setTakeId(String takeId) {
		this.takeId = takeId;
	}
	public String getTakeAmount() {
		return takeAmount;
	}
	public void setTakeAmount(String takeAmount) {
		this.takeAmount = takeAmount;
	}
	public String getOpenDtm() {
		return openDtm;
	}
	public void setOpenDtm(String openDtm) {
		this.openDtm = openDtm;
	}
}
