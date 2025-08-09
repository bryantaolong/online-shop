package com.bryan.system.service.order;

import com.bryan.system.domain.enums.OrderStatusEnum;
import com.bryan.system.exception.BusinessException;
import com.bryan.system.exception.ResourceNotFoundException;
import com.bryan.system.domain.converter.CartItemConverter;
import com.bryan.system.domain.converter.OrderConverter;
import com.bryan.system.domain.converter.OrderItemConverter;
import com.bryan.system.domain.dto.CreateOrderDTO;
import com.bryan.system.domain.entity.cart.CartItem;
import com.bryan.system.domain.entity.order.Order;
import com.bryan.system.domain.entity.order.OrderItem;
import com.bryan.system.domain.request.order.OrderSearchRequest;
import com.bryan.system.domain.vo.OrderItemVO;
import com.bryan.system.domain.vo.OrderVO;
import com.bryan.system.repository.order.OrderItemRepository;
import com.bryan.system.repository.order.OrderRepository;
import com.bryan.system.service.cart.CartService;
import com.bryan.system.service.product.SkuService;
import com.bryan.system.util.id.IdUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * OrderServiceImpl
 *
 * @author Bryan Long
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository itemRepository;
    private final SkuService skuService;
    private final CartService cartService;

    @Override
    public String create(CreateOrderDTO dto) {
        List<CartItem> carts = CartItemConverter.toEntityList(
                cartService.listByUser(dto.getUserId()));
        if (carts.isEmpty()) {
            throw new BusinessException("购物车为空");
        }

        // 1. 扣库存
        carts.forEach(c -> skuService.deductStock(c.getSkuId(), c.getQuantity()));

        // 2. 创建订单
        String orderNo = IdUtils.getSnowflakeNextIdStr();
        Order order = Order.builder()
                .orderNo(orderNo)
                .userId(dto.getUserId())
                .totalAmount(calcTotal(carts))
                .paymentAmount(calcTotal(carts)) // 示例：无运费
                .status(OrderStatusEnum.PENDING_PAYMENT)
                .build();
        order = orderRepository.save(order);   // JPA 生成主键

        // 3. 保存明细
        Order finalOrder = order;
        List<OrderItem> items = carts.stream()
                .map(c -> OrderItemConverter.from(c, finalOrder.getId(), orderNo))
                .collect(Collectors.toList());
        itemRepository.saveAll(items);

        // 4. 清空购物车
        cartService.clear(dto.getUserId());
        return orderNo;
    }

    @Override
    public OrderVO getById(Long id) {
        return orderRepository.findById(id)
                .map(OrderConverter::toVO)
                .orElseThrow(() -> new ResourceNotFoundException("订单不存在"));
    }

    @Override
    public OrderVO getByOrderNo(String orderNo) {
        return orderRepository.findByOrderNo(orderNo)
                .map(OrderConverter::toVO)
                .orElseThrow(() -> new ResourceNotFoundException("订单不存在"));
    }

    @Override
    public void update(Order order) {
        orderRepository.save(order);
    }

    @Override
    public Page<OrderVO> searchOrders(OrderSearchRequest req, Pageable pageable) {
        Page<Order> page = orderRepository.search(
                req.getUserId(),
                req.getOrderNo(),
                req.getStatus(),
                req.getCreateTimeStart(),
                req.getCreateTimeEnd(),
                pageable);

        // 一次性加载明细并填充
        List<Long> orderIds = page.map(Order::getId).getContent();
        if (!orderIds.isEmpty()) {
            List<OrderItem> items = itemRepository.findByOrderIdIn(orderIds);
            Map<Long, List<OrderItem>> map = items.stream()
                    .collect(Collectors.groupingBy(OrderItem::getOrderId));

            return page.map(o -> {
                OrderVO vo = OrderConverter.toVO(o);
                List<OrderItemVO> itemVOs = map.getOrDefault(o.getId(), List.of())
                        .stream().map(OrderItemConverter::toVO)
                        .collect(Collectors.toList());
                vo.setItems(itemVOs);
                return vo;
            });
        }
        return page.map(OrderConverter::toVO);
    }

    @Override
    @Transactional
    public void cancel(String orderNo) {
        Order order = orderRepository.findByOrderNo(orderNo)
                .orElseThrow(() -> new BusinessException("订单不存在"));
        if (order.getStatus() != OrderStatusEnum.PENDING_PAYMENT) {
            throw new BusinessException("订单无法取消");
        }
        order.setStatus(OrderStatusEnum.CLOSED);
        orderRepository.save(order);
    }

    @Override
    @Transactional
    public void confirmReceive(String orderNo) {
        Order order = orderRepository.findByOrderNo(orderNo)
                .orElseThrow(() -> new BusinessException("订单不存在"));
        if (order.getStatus() != OrderStatusEnum.SHIPPED) {
            throw new BusinessException("订单状态异常");
        }
        order.setStatus(OrderStatusEnum.RECEIVED);
        order.setReceiveTime(LocalDateTime.now());
        orderRepository.save(order);
    }

    private BigDecimal calcTotal(List<CartItem> carts) {
        return carts.stream()
                .map(c -> c.getPrice().multiply(BigDecimal.valueOf(c.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
