package com.d2956987215.mow.rxjava.response;

/**
 * 作者：闫亚锋
 * 时间：2018/10/19 10:34
 * 说明：
 */
public class VipSubmitResponse extends BaseResponse{

    /**
     * data : {"payinfo":"alipay_sdk=alipay-sdk-php-20161101&app_id=2017100409124305&biz_content=%7B%22body%22%3A%221%5Cu5143%5Cu81ea%5Cu52a9%5Cu5347%5Cu7ea7%22%2C%22subject%22%3A%221%5Cu5143%5Cu81ea%5Cu52a9%5Cu5347%5Cu7ea7%22%2C%22out_trade_no%22%3A%22SH20181104110107606301%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A1%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Fmsapi.maishoumm.com%2Fapi%2Fv1%2FAliNotify&sign_type=RSA&timestamp=2018-11-04+11%3A01%3A09&version=1.0&sign=Bzc5HTqHocMIjv5QXSKgcBje%2F2uX%2Bj68zp2LcJZodkMcU7%2FP%2BB1TVps1yy5ee8Lun1unBK8%2FJrP7qk5ULCKCyOWa2Hnb80DWyGfcip88CiLGchg%2BOjCcfDDb3Aab%2FGV8v%2F84F7NaRlEp7gW2CZjC6kdyJDxBJZ6Qx97FGSJ3C0Q%3D"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * payinfo : alipay_sdk=alipay-sdk-php-20161101&app_id=2017100409124305&biz_content=%7B%22body%22%3A%221%5Cu5143%5Cu81ea%5Cu52a9%5Cu5347%5Cu7ea7%22%2C%22subject%22%3A%221%5Cu5143%5Cu81ea%5Cu52a9%5Cu5347%5Cu7ea7%22%2C%22out_trade_no%22%3A%22SH20181104110107606301%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A1%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Fmsapi.maishoumm.com%2Fapi%2Fv1%2FAliNotify&sign_type=RSA&timestamp=2018-11-04+11%3A01%3A09&version=1.0&sign=Bzc5HTqHocMIjv5QXSKgcBje%2F2uX%2Bj68zp2LcJZodkMcU7%2FP%2BB1TVps1yy5ee8Lun1unBK8%2FJrP7qk5ULCKCyOWa2Hnb80DWyGfcip88CiLGchg%2BOjCcfDDb3Aab%2FGV8v%2F84F7NaRlEp7gW2CZjC6kdyJDxBJZ6Qx97FGSJ3C0Q%3D
         */

        private String payinfo;

        public String getPayinfo() {
            return payinfo;
        }

        public void setPayinfo(String payinfo) {
            this.payinfo = payinfo;
        }
    }
}
