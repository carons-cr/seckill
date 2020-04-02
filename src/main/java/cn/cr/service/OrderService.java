package cn.cr.service;

import cn.cr.error.BusinessException;
import cn.cr.service.model.OrderModel;

public interface OrderService {
    OrderModel createOrder(Integer userId, Integer itemId, Integer amount) throws BusinessException;
    String generateOrderNo();
}
