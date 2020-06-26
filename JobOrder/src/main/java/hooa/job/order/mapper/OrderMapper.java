package hooa.job.order.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;
import hooa.job.order.dao.OrderVO;

@Repository
public interface OrderMapper {
	
	public Integer insertData(OrderVO onevo);
	
	public List<OrderVO> chkPossibleTake(OrderVO onevo);
	
	public Integer takeMoney(OrderVO onevo);
	
	public List<OrderVO> searchMyOrderList(OrderVO onevo);
}
