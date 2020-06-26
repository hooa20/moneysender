package hooa.job.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hooa.job.order.dao.OrderVO;
import hooa.job.order.mapper.OrderMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class JobService {
	
	
	@Autowired(required = false)
	OrderMapper ordermapper;

	public ServiceRS SprinkleMoney(String xuserid, String xroomid, String money, String persons) {

		ServiceRS rs = new ServiceRS();
		String token = "";
		try {

			token = getToken();
			//System.out.println("token : " + token);

			int int_moeny = Integer.valueOf(money);
			int int_person = Integer.valueOf(persons);

			int onepay = int_moeny / int_person;

			OrderVO onevo = null;
			for (int pidx = 0; pidx < int_person; pidx++) {
				//System.out.println("pidx : " + (pidx + 1));
				
				onevo = new OrderVO();
				onevo.setToken(token);
				onevo.setGiveId(xuserid);
				onevo.setGiveRoom(xroomid);
				onevo.setTakeSeq(pidx + 1);
				onevo.setTakeId("");
				if (pidx + 1 == int_person) { // 짜투리 몰빵
					onevo.setTakeAmount(String.valueOf(int_moeny));
					//System.out.println("onepay : " + (int_moeny));
				} else {
					onevo.setTakeAmount(String.valueOf(onepay));
					//System.out.println("onepay : " + (onepay));
					int_moeny = int_moeny - onepay;
				}
				ordermapper.insertData(onevo);				
			}
			rs.setToken(token);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			rs.setReturnmsg("예외 발생 : 시스템 담당자에게 문의 하세요");		
		}

		return rs;
	}
	
	
	
	public ServiceRS TakeMoney(String xuserid, String xroomid, String token) {

		ServiceRS rs = new ServiceRS();
		
		try {
			OrderVO onevo = new OrderVO();
			onevo.setToken(token);
			onevo.setTakeId(xuserid);
			onevo.setGiveRoom(xroomid);
			
			List<OrderVO> orderlist = ordermapper.chkPossibleTake(onevo);
			
			if(orderlist != null && orderlist.size() > 0) {
				
				onevo.setGiveId(orderlist.get(0).getGiveId());
				onevo.setTakeSeq(orderlist.get(0).getTakeSeq());
				
				ordermapper.takeMoney(onevo);
				
				rs.setAmount(orderlist.get(0).getTakeAmount());
			
			} else {
				rs.setReturnmsg("받기 가능한 데이터 없음");			
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			rs.setReturnmsg("예외 발생 : 시스템 담당자에게 문의 하세요");		
		}

		return rs;
	}
	
	
	public ServiceRS SearchMyOrderList(String xuserid, String xroomid, String token) {

		ServiceRS rs = new ServiceRS();
		
		try {
			OrderVO onevo = new OrderVO();
			onevo.setToken(token);
			onevo.setGiveId(xuserid);
			onevo.setGiveRoom(xroomid);
			
			List<OrderVO> orderlist = ordermapper.searchMyOrderList(onevo);
			
			if(orderlist != null && orderlist.size() > 0) {
				
				long total = 0;
				long take = 0;
				
				List<OrderVO> takeinfos = new ArrayList<OrderVO>();
				OrderVO onetakeinfo = null;
				for(OrderVO one : orderlist) {
					total = total + Long.valueOf(one.getTakeAmount());
					if(!"".equals(one.getTakeId())){
						take = take + Long.valueOf(one.getTakeAmount());
					}
					
					onetakeinfo = new OrderVO();
					onetakeinfo.setTakeAmount(one.getTakeAmount());
					onetakeinfo.setTakeId(one.getTakeId());
					takeinfos.add(onetakeinfo);
				}
				
				rs.setOpendtm(orderlist.get(0).getOpenDtm());
				rs.setTotalamount(total);
				rs.setTakenamount(take);
				rs.setTakeinfos(takeinfos);
			
			} else {
				rs.setReturnmsg("조회가능한 데이터가 없음");			
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			rs.setReturnmsg("예외 발생 : 시스템 담당자에게 문의 하세요");		
			
		}

		return rs;
	}
	

	private String getToken() {

		Random rand = new Random();
		StringBuffer sb = new StringBuffer();
		
		for (int rc = 0; rc < 3; rc++) {
			int index = rand.nextInt(3);

			switch (index) {

			case 0:
				sb.append((char) (rand.nextInt(26) + 97));
				break;

			case 1:
				sb.append((char) (rand.nextInt(26) + 65));
				break;

			case 2:
				sb.append(rand.nextInt(10));
				break;

			}
		}
		return sb.toString();
	}

}
