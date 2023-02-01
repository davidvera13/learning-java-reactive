package com.reactive.orders.ws.mappers;

import com.reactive.orders.ws.dto.RequestContext;
import com.reactive.orders.ws.dto.enums.OrderStatus;
import com.reactive.orders.ws.dto.enums.TransactionStatus;
import com.reactive.orders.ws.dto.request.TransactionRequestDto;
import com.reactive.orders.ws.dto.response.PurchaseOrderResponseDto;
import com.reactive.orders.ws.io.entity.PurchaseOrder;
import org.springframework.beans.BeanUtils;

public class EntityDtoMappers {
    public static PurchaseOrderResponseDto getPurchaseOrderResponseDto(PurchaseOrder purchaseOrder){
        PurchaseOrderResponseDto dto = new PurchaseOrderResponseDto();
        BeanUtils.copyProperties(purchaseOrder, dto);
        dto.setOrderId(purchaseOrder.getId());
        return dto;
    }

    public static void setTransactionRequestDto(RequestContext requestContext){
        TransactionRequestDto dto = new TransactionRequestDto();
        dto.setUserId(requestContext.getPurchaseOrderRequestDto().getUserId());
        dto.setAmount(requestContext.getProductDto().getPrice());
        requestContext.setTransactionRequestDto(dto);
    }

    public static PurchaseOrder getPurchaseOrder(RequestContext requestContext){
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setUserId(requestContext.getPurchaseOrderRequestDto().getUserId());
        purchaseOrder.setProductId(requestContext.getPurchaseOrderRequestDto().getProductId());
        purchaseOrder.setAmount(requestContext.getProductDto().getPrice());
        TransactionStatus status = requestContext.getTransactionResponseDto().getStatus();
        OrderStatus orderStatus = TransactionStatus.APPROVED.equals(status) ? OrderStatus.COMPLETED : OrderStatus.FAILED;
        purchaseOrder.setStatus(orderStatus);
        return purchaseOrder;
    }
}
