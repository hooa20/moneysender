package hooa.job.order.service;

import java.util.List;

import hooa.job.order.dao.OrderVO;

public class ServiceRS {
	
	private String returnmsg;
	private String token;
	private String amount;
	private String opendtm;
	private long totalamount;
	private long takenamount;
	private List<OrderVO> takeinfos;
	
	public String getReturnmsg() {
		return returnmsg;
	}
	public void setReturnmsg(String returnmsg) {
		this.returnmsg = returnmsg;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getOpendtm() {
		return opendtm;
	}
	public void setOpendtm(String opendtm) {
		this.opendtm = opendtm;
	}
	public long getTotalamount() {
		return totalamount;
	}
	public void setTotalamount(long totalamount) {
		this.totalamount = totalamount;
	}
	public long getTakenamount() {
		return takenamount;
	}
	public void setTakenamount(long takenamount) {
		this.takenamount = takenamount;
	}
	public List<OrderVO> getTakeinfos() {
		return takeinfos;
	}
	public void setTakeinfos(List<OrderVO> takeinfos) {
		this.takeinfos = takeinfos;
	}
}
