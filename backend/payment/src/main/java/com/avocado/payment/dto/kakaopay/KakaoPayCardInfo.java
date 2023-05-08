package com.avocado.payment.dto.kakaopay;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class KakaoPayCardInfo {
    private String purchase_corp;
    private String purchase_corp_code;
    private String issuer_corp;
    private String issuer_corp_code;
    private String kakaopay_purchase_corp;
    private String kakaopay_purchase_corp_code;
    private String kakaopay_issuer_corp;
    private String kakaopay_issuer_corp_code;
    private String bin;
    private String card_type;
    private String install_month;
    private String approved_id;
    private String card_min;
    private String interest_free_install;
    private String card_item_code;
}
