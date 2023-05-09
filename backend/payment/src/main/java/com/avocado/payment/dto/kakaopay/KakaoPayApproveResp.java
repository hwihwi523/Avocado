package com.avocado.payment.dto.kakaopay;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class KakaoPayApproveResp {
    private String aid;
    private String tid;
    private String cid;
    private String sid;
    private String partner_order_id;
    private String partner_user_id;
    private String payment_method_type;
    private KakaoPayAmount amount;
    private KakaoPayCardInfo card_info;
    private String item_name;
    private String item_code;
    private Integer quantity;
    private String created_at;
    private String approved_at;
    private String payload;
}
