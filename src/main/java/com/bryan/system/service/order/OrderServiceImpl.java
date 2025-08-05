package com.bryan.system.service.order;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bryan.system.common.enums.OrderStatusEnum;
import com.bryan.system.common.exception.BusinessException;
import com.bryan.system.common.exception.ResourceNotFoundException;
import com.bryan.system.mapper.OrderItemMapper;
import com.bryan.system.mapper.OrderMapper;
import com.bryan.system.model.converter.CartItemConverter;
import com.bryan.system.model.converter.OrderConverter;
import com.bryan.system.model.converter.OrderItemConverter;
import com.bryan.system.model.dto.CreateOrderDTO;
import com.bryan.system.model.entity.cart.CartItem;
import com.bryan.system.model.entity.order.Order;
import com.bryan.system.model.entity.order.OrderItem;
import com.bryan.system.model.request.order.OrderPageQuery;
import com.bryan.system.model.vo.OrderItemVO;
import com.bryan.system.model.vo.OrderVO;
import com.bryan.system.service.cart.CartService;
import com.bryan.system.service.product.SkuService;
import com.bryan.system.util.id.IdUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * OrderServiceImpl
 *
 * @author Bryan Long
 * @version 1.0
 * @since 2025/8/5
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;
    private final OrderItemMapper itemMapper;
    private final SkuService skuService;
    private final CartService cartService;

    @Override
    public String create(CreateOrderDTO dto) {
        List<CartItem> carts = CartItemConverter.toEntityList(
                cartService.listByUser(dto.getUserId()));
        if (carts.isEmpty()) throw new BusinessException("购物车为空");

        // 1. 扣库存
        carts.forEach(c -> skuService.deductStock(c.getSkuId(), c.getQuantity()));

        // 2. 生成订单
        String orderNo = IdUtils.getSnowflakeNextIdStr();
        Order order = Order.builder()
                .orderNo(orderNo)
                .userId(dto.getUserId())
                .totalAmount(calcTotal(carts))
                .status(OrderStatusEnum.PENDING_PAYMENT)
                .build();
        orderMapper.insert(order);

        // 3. 保存明细
        List<OrderItem> items = carts.stream()
                .map(c -> OrderItemConverter.from(c, order.getId(), orderNo))
                .collect(Collectors.toList());
        itemMapper.insertBatch(items);

        // 4. 清空购物车
        cartService.clear(dto.getUserId());
        return orderNo;
    }

    @Override
    public OrderVO getById(Long id) {
        return Optional.ofNullable(orderMapper.selectById(id))
                .map(OrderConverter::toVO)
                .orElseThrow(() -> new ResourceNotFoundException("订单不存在"));
    }

    @Override
    public OrderVO getByOrderNo(String orderNo) {
        return Optional.ofNullable(
                        orderMapper.selectOne(new QueryWrapper<Order>().eq("order_no", orderNo)))
                .map(OrderConverter::toVO)
                .orElseThrow(() -> new ResourceNotFoundException("订单不存在"));
    }

    @Override
    public void update(Order order) {
        orderMapper.updateById(order);
    }

    @Override
    public Page<OrderVO> pageOrders(OrderPageQuery q) {
        QueryWrapper<Order> qw = new QueryWrapper<>();
        qw.eq(q.getUserId() != null, "user_id", q.getUserId())
                .eq(StringUtils.hasText(q.getOrderNo()), "order_no", q.getOrderNo())
                .eq(q.getStatus() != null, "status", q.getStatus())
                .orderByDesc("create_time");

        Page<Order> page = orderMapper.selectPage(
                new Page<>(q.getPageNum(), q.getPageSize()), qw);

        // 1. 先拿到 order 转 VO
        Page<OrderVO> voPage = (Page<OrderVO>) page.convert(OrderConverter::toVO);

        // 2. 一次性查出明细并装配
        List<Long> orderIds = voPage.getRecords().stream()
                .map(OrderVO::getId)
                .collect(Collectors.toList());
        if (!orderIds.isEmpty()) {
            List<OrderItem> items = itemMapper.selectList(
                    new QueryWrapper<OrderItem>().in("order_id", orderIds));
            Map<Long, List<OrderItem>> itemMap = items.stream()
                    .collect(Collectors.groupingBy(OrderItem::getOrderId));

            voPage.getRecords().forEach(vo -> {
                List<OrderItemVO> itemVOs = itemMap.getOrDefault(vo.getId(), List.of())
                        .stream()
                        .map(OrderItemConverter::toVO)
                        .collect(Collectors.toList());
                vo.setItems(itemVOs);
            });
        }
        return voPage;
    }

    @Override
    public void cancel(String orderNo) {
        Order entity = orderMapper.selectOne(new QueryWrapper<Order>().eq("order_no", orderNo));
        if (entity == null || entity.getStatus() != OrderStatusEnum.PENDING_PAYMENT) {
            throw new BusinessException("订单无法取消");
        }
        entity.setStatus(OrderStatusEnum.CLOSED);
        orderMapper.updateById(entity);
    }

    @Override
    public void confirmReceive(String orderNo) {
        Order entity = orderMapper.selectOne(new QueryWrapper<Order>().eq("order_no", orderNo));
        if (entity == null || entity.getStatus() != OrderStatusEnum.SHIPPED) {
            throw new BusinessException("订单状态异常");
        }
        entity.setStatus(OrderStatusEnum.RECEIVED);
        entity.setReceiveTime(LocalDateTime.now());
        orderMapper.updateById(entity);
    }

    private BigDecimal calcTotal(List<CartItem> carts) {
        return carts.stream()
                .map(c -> c.getPrice().multiply(BigDecimal.valueOf(c.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
