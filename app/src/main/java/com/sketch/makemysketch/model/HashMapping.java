package com.sketch.makemysketch.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class HashMapping {
    private String save_user_card_hash;
    private String payment_related_details_for_mobile_sdk_hash;
    private String get_merchant_ibibo_codes_hash;
    private String get_user_cards_hash;
    private String vas_for_mobile_sdk_hash;
    private String edit_user_card_hash;
    private String payment_hash;

    public String getCheck_offer_status_hash() {
        return check_offer_status_hash;
    }

    public void setCheck_offer_status_hash(String check_offer_status_hash) {
        this.check_offer_status_hash = check_offer_status_hash;
    }

    @JsonIgnore
    private String check_offer_status_hash;

    public String getGet_merchant_ibibo_codes_hash() {
        return get_merchant_ibibo_codes_hash;
    }

    public void setGet_merchant_ibibo_codes_hash(String get_merchant_ibibo_codes_hash) {
        this.get_merchant_ibibo_codes_hash = get_merchant_ibibo_codes_hash;
    }

    public String getPayment_related_details_for_mobile_sdk_hash() {
        return payment_related_details_for_mobile_sdk_hash;
    }

    public void setPayment_related_details_for_mobile_sdk_hash(String payment_related_details_for_mobile_sdk_hash) {
        this.payment_related_details_for_mobile_sdk_hash = payment_related_details_for_mobile_sdk_hash;
    }

    public String getSave_user_card_hash() {
        return save_user_card_hash;
    }

    public void setSave_user_card_hash(String save_user_card_hash) {
        this.save_user_card_hash = save_user_card_hash;
    }

    public String getGet_user_cards_hash() {
        return get_user_cards_hash;
    }

    public void setGet_user_cards_hash(String get_user_cards_hash) {
        this.get_user_cards_hash = get_user_cards_hash;
    }

    public String getVas_for_mobile_sdk_hash() {
        return vas_for_mobile_sdk_hash;
    }

    public void setVas_for_mobile_sdk_hash(String vas_for_mobile_sdk_hash) {
        this.vas_for_mobile_sdk_hash = vas_for_mobile_sdk_hash;
    }

    public String getEdit_user_card_hash() {
        return edit_user_card_hash;
    }

    public void setEdit_user_card_hash(String edit_user_card_hash) {
        this.edit_user_card_hash = edit_user_card_hash;
    }

    public String getPayment_hash() {
        return payment_hash;
    }

    public void setPayment_hash(String payment_hash) {
        this.payment_hash = payment_hash;
    }

    public String getDelete_user_card_hash() {
        return delete_user_card_hash;
    }

    public void setDelete_user_card_hash(String delete_user_card_hash) {
        this.delete_user_card_hash = delete_user_card_hash;
    }

    public String getVerify_payment_hash() {
        return verify_payment_hash;
    }

    public void setVerify_payment_hash(String verify_payment_hash) {
        this.verify_payment_hash = verify_payment_hash;
    }

    private String delete_user_card_hash;
    private String verify_payment_hash;
}
